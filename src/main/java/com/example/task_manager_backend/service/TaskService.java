package com.example.task_manager_backend.service;

import com.example.task_manager_backend.dto.TaskRequestDTO;
import com.example.task_manager_backend.dto.TaskResponseDTO;
import com.example.task_manager_backend.dto.TaskUpdateDTO;
import com.example.task_manager_backend.model.Task;
import com.example.task_manager_backend.model.TaskStatus;
import com.example.task_manager_backend.model.User;
import com.example.task_manager_backend.model.enums.TimeStatus;
import com.example.task_manager_backend.repository.TaskRepository;
import com.example.task_manager_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public TaskResponseDTO createTask(TaskRequestDTO requestDTO) {
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task();
        task.setTitle(requestDTO.getTitle());
        task.setDescription(requestDTO.getDescription());
        task.setStartDate(requestDTO.getStartDate());
        task.setStartTime(requestDTO.getStartTime());
        task.setEndDate(requestDTO.getEndDate());
        task.setEndTime(requestDTO.getEndTime());
        task.setUser(user);

        // Create and set task status
        TaskStatus status = new TaskStatus();
        status.setTimeStatus(calculateTimeStatus(task));
        status.setUserStatus(com.example.task_manager_backend.model.enums.UserStatus.NOT_STARTED);
        task.setTaskStatus(status);
        status.setTask(task);

        Task savedTask = taskRepository.save(task);
        return mapToResponseDTO(savedTask);
    }

    public List<TaskResponseDTO> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public TaskResponseDTO updateTask(Long id, TaskUpdateDTO updateDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (updateDTO.getTitle() != null) {
            task.setTitle(updateDTO.getTitle());
        }
        if (updateDTO.getDescription() != null) {
            task.setDescription(updateDTO.getDescription());
        }
        if (updateDTO.getStartDate() != null) {
            task.setStartDate(updateDTO.getStartDate());
        }
        if (updateDTO.getStartTime() != null) {
            task.setStartTime(updateDTO.getStartTime());
        }
        if (updateDTO.getEndDate() != null) {
            task.setEndDate(updateDTO.getEndDate());
        }
        if (updateDTO.getEndTime() != null) {
            task.setEndTime(updateDTO.getEndTime());
        }

        // Update task status
        TaskStatus status = task.getTaskStatus();
        if (status == null) {
            status = new TaskStatus();
            status.setTask(task);
            task.setTaskStatus(status);
        }

        status.setTimeStatus(calculateTimeStatus(task));
        if (updateDTO.getUserStatus() != null) {
            status.setUserStatus(updateDTO.getUserStatus());
        }

        Task updatedTask = taskRepository.save(task);
        return mapToResponseDTO(updatedTask);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    private TimeStatus calculateTimeStatus(Task task) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDateTime = LocalDateTime.of(task.getStartDate(), task.getStartTime());
        LocalDateTime endDateTime = LocalDateTime.of(task.getEndDate(), task.getEndTime());

        if (now.isBefore(startDateTime)) {
            return TimeStatus.UPCOMING;
        } else if (now.isAfter(endDateTime)) {
            return TimeStatus.ENDED;
        } else {
            return TimeStatus.IN_PROGRESS;
        }
    }

    private TaskResponseDTO mapToResponseDTO(Task task) {
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setTaskId(task.getTaskId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStartDate(task.getStartDate());
        dto.setStartTime(task.getStartTime());
        dto.setEndDate(task.getEndDate());
        dto.setEndTime(task.getEndTime());

        if (task.getTaskStatus() != null) {
            dto.setTimeStatus(task.getTaskStatus().getTimeStatus());
            dto.setUserStatus(task.getTaskStatus().getUserStatus());
            dto.setLastUpdated(task.getTaskStatus().getLastUpdated());
        }

        if (task.getUser() != null) {
            dto.setUserId(task.getUser().getId());
            dto.setUsername(task.getUser().getUsername());
        }

        return dto;
    }
}