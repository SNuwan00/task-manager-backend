package com.example.task_manager_backend.controller;

import com.example.task_manager_backend.dto.update.TaskStatusUpdateDTO;
import com.example.task_manager_backend.model.TaskStatus;
import com.example.task_manager_backend.service.TaskStatusService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task-status")
public class TaskStatusController {
    @Autowired
    private TaskStatusService taskStatusService;

    @Operation(summary = "Update user status in task status")
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskStatus> updateUserStatus(
            @PathVariable Long taskId,
            @RequestBody TaskStatusUpdateDTO taskStatusUpdateDTO) {
        TaskStatus updatedTaskStatus = taskStatusService.updateUserStatus(taskId, taskStatusUpdateDTO);
        return ResponseEntity.ok(updatedTaskStatus);
    }
}
