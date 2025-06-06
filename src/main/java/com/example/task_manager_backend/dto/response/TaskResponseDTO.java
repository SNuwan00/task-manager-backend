package com.example.task_manager_backend.dto.response;

import com.example.task_manager_backend.model.enums.TimeStatus;
import com.example.task_manager_backend.model.enums.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponseDTO {
    private Long taskId;
    private String title;
    private String description;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private TimeStatus timeStatus;
    private UserStatus userStatus;
    private LocalDateTime lastUpdated;
    private Long userId;
    private String username;

}
