package com.example.task_manager_backend.dto;

import lombok.Data;

@Data
public class TaskRequestDTO {
    private String title;
    private String description;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private Long userId;

}