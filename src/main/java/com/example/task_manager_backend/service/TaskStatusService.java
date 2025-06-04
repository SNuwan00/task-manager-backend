package com.example.task_manager_backend.service;

import com.example.task_manager_backend.dto.TaskStatusDTO;
import com.example.task_manager_backend.model.Task;
import com.example.task_manager_backend.model.TaskStatus;
import com.example.task_manager_backend.model.enums.TimeStatus;
import com.example.task_manager_backend.model.enums.UserStatus;
import com.example.task_manager_backend.repository.TaskRepository;
import com.example.task_manager_backend.repository.TaskStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class TaskStatusService {

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private TaskRepository taskRepository;

    public TaskStatusDTO createTaskStatus(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        TaskStatus taskStatus = new TaskStatus();
        taskStatus.setTask(task);
        taskStatus.setUserStatus(UserStatus.NOT_STARTED);

        // Calculate time status
        TimeStatus timeStatus = calculateTimeStatus(task);
        taskStatus.setTimeStatus(timeStatus);

        TaskStatus savedTaskStatus = taskStatusRepository.save(taskStatus);
        return mapToDTO(savedTaskStatus);
    }

    public TaskStatusDTO updateTaskStatus(Long taskId, UserStatus userStatus) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        TaskStatus taskStatus = taskStatusRepository.findByTaskTaskId(taskId)
                .orElseThrow(() -> new RuntimeException("Task Status not found"));

        taskStatus.setUserStatus(userStatus);

        // Recalculate time status
        TimeStatus timeStatus = calculateTimeStatus(task);
        taskStatus.setTimeStatus(timeStatus);

        TaskStatus savedTaskStatus = taskStatusRepository.save(taskStatus);
        return mapToDTO(savedTaskStatus);
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

    private TaskStatusDTO mapToDTO(TaskStatus taskStatus) {
        TaskStatusDTO dto = new TaskStatusDTO();
        dto.setTaskId(taskStatus.getTask().getTaskId());
        dto.setTimeStatus(taskStatus.getTimeStatus());
        dto.setUserStatus(taskStatus.getUserStatus());
        dto.setLastUpdated(taskStatus.getLastUpdated());
        return dto;
    }
}