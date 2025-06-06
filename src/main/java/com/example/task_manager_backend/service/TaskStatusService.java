package com.example.task_manager_backend.service;

import com.example.task_manager_backend.dto.update.TaskStatusUpdateDTO;
import com.example.task_manager_backend.model.TaskStatus;
import com.example.task_manager_backend.repository.TaskStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskStatusService {

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    public TaskStatus updateUserStatus(Long taskId, TaskStatusUpdateDTO taskStatusUpdateDTO) {
        TaskStatus taskStatus = taskStatusRepository.findByTaskTaskId(taskId)
                .orElseThrow(() -> new RuntimeException("TaskStatus not found"));

        taskStatus.setUserStatus(taskStatusUpdateDTO.getUserStatus());
        return taskStatusRepository.save(taskStatus);
    }
}
