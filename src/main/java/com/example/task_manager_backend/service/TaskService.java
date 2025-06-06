package com.example.task_manager_backend.service;

import com.example.task_manager_backend.dto.TaskDetailsDTO;
import com.example.task_manager_backend.dto.request.TaskRequestDTO;
import com.example.task_manager_backend.dto.response.TaskResponseDTO;
import com.example.task_manager_backend.dto.update.TaskUpdateDTO;
import com.example.task_manager_backend.model.Task;
import com.example.task_manager_backend.model.TaskStatus;
import com.example.task_manager_backend.model.User;
import com.example.task_manager_backend.model.enums.UserStatus;
import com.example.task_manager_backend.repository.TaskRepository;
import com.example.task_manager_backend.repository.TaskStatusRepository;
import com.example.task_manager_backend.repository.UserRepository;
import com.example.task_manager_backend.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.task_manager_backend.util.DateTimeUtil.convertToDateTime;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private UserRepository userRepository;

    private DateTimeUtil dateTimeUtil;

    @Transactional
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

        // Save task first to generate taskId
        Task savedTask = taskRepository.save(task);

        TaskStatus status = new TaskStatus();
        status.setTask(savedTask);
        status.setTimeStatus(
                dateTimeUtil.getTimeStatus(
                        convertToDateTime(savedTask.getStartDate(),savedTask.getStartTime()),
                        convertToDateTime(savedTask.getEndDate(),savedTask.getEndTime()))
        ); // Set to null initially, will be updated later
        status.setUserStatus(UserStatus.NOT_STARTED);
        status.setLastUpdated(null);

        TaskStatus savedTaskStatus = taskStatusRepository.save(status);

        // Set the status back to the task
        //savedTask.setTaskStatus(status);

        return mapToResponseDTO(savedTask);
    }

    public List<TaskResponseDTO> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
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



        taskStatusRepository.save(status);
        Task updatedTask = taskRepository.save(task);
        return mapToResponseDTO(updatedTask);
    }

    @Transactional
    public void deleteTask(Long id) {
        // The task status will be deleted automatically due to the cascade relationship
        taskRepository.deleteById(id);
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

    public List<TaskDetailsDTO> getTaskDetailsByUserId(Long userId) {
        return taskRepository.findTaskDetailsByUserId(userId);
    }
}