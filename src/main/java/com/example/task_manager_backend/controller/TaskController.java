package com.example.task_manager_backend.controller;

import com.example.task_manager_backend.dto.TaskDetailsDTO;
import com.example.task_manager_backend.dto.request.TaskRequestDTO;
import com.example.task_manager_backend.dto.response.TaskDetailsResponseDTO;
import com.example.task_manager_backend.dto.response.TaskResponseDTO;
import com.example.task_manager_backend.dto.update.TaskUpdateDTO;
import com.example.task_manager_backend.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(summary = "Create a new task")
    @PostMapping("/save")
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskRequestDTO taskRequest) {
        TaskResponseDTO createdTask = taskService.createTask(taskRequest);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all tasks for a user")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByUserId(@PathVariable Long userId) {
        List<TaskResponseDTO> tasks = taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }

    @Operation(summary = "Update a task")
    @PutMapping("/edit/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @RequestBody TaskUpdateDTO taskUpdate) {
        TaskResponseDTO updatedTask = taskService.updateTask(id, taskUpdate);
        return ResponseEntity.ok(updatedTask);
    }

    @Operation(summary = "Delete a task")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}/all")
    public ResponseEntity<List<TaskDetailsDTO>> getTaskDetailsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size) {
        List<TaskDetailsDTO> taskDetails = taskService.getTaskDetailsByUserId(userId, page, size);
        return ResponseEntity.ok(taskDetails);
    }

    @GetMapping("/user/{userId}/all-done")
    public ResponseEntity<List<TaskDetailsDTO>> getDoneTaskDetailsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size) {
        List<TaskDetailsDTO> taskDetails = taskService.getDoneTaskDetailsByUserId(userId, page, size);
        return ResponseEntity.ok(taskDetails);
    }

    @Operation(summary = "Get detailed task information by ID")
    @GetMapping("/{taskId}/details")
    public ResponseEntity<TaskDetailsResponseDTO> getTaskDetailsById(@PathVariable Long taskId) {
        try {
            TaskDetailsResponseDTO taskDetails = taskService.getTaskDetailsById(taskId);
            return ResponseEntity.ok(taskDetails);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }
    }

    @Operation(summary = "Update detailed task information by ID")
    @PutMapping("/{taskId}/detailsUpdate")
    public ResponseEntity<TaskDetailsResponseDTO> updateTaskDetailsById(@PathVariable Long taskId, @RequestBody TaskDetailsResponseDTO updateDTO){
        try {
            TaskDetailsResponseDTO taskDetails = taskService.updateTaskDetailsById(
                    taskId,
                    updateDTO.getTitle(),
                    updateDTO.getDescription(),
                    updateDTO.getStartDate(),
                    updateDTO.getStartTime(),
                    updateDTO.getEndDate(),
                    updateDTO.getEndTime(),
                    updateDTO.getTimeStatus(),
                    updateDTO.getUserStatus()
                    );
            return ResponseEntity.ok(taskDetails);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}