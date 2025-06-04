package com.example.task_manager_backend.dto;

import com.example.task_manager_backend.model.enums.UserStatus;
import lombok.Data;

@Data
public class TaskUpdateDTO {
    private String title;
    private String description;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private UserStatus userStatus;


}
