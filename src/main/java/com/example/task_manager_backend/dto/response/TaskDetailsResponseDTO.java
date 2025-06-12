package com.example.task_manager_backend.dto.response;

import com.example.task_manager_backend.model.enums.TimeStatus;
import com.example.task_manager_backend.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskDetailsResponseDTO {
    private Long taskId;
    private String title;
    private String description;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private TimeStatus timeStatus;
    private UserStatus userStatus;

    public TaskDetailsResponseDTO(Long taskId, String title, String description, String startDate, String startTime,
                                  String endDate, String endTime, UserStatus userStatus, TimeStatus timeStatus) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.userStatus = userStatus;
        this.timeStatus = timeStatus;
    }

}