package com.example.task_manager_backend.dto;

import com.example.task_manager_backend.model.enums.TimeStatus;
import com.example.task_manager_backend.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskDetailsDTO {
    private Long taskId;
    private String title;
    private UserStatus userStatus;
    private TimeStatus timeStatus;

}

