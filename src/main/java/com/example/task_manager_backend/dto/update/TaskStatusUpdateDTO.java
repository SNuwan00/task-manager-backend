package com.example.task_manager_backend.dto.update;

import com.example.task_manager_backend.model.enums.UserStatus;
import lombok.Data;

@Data
public class TaskStatusUpdateDTO {
    private UserStatus userStatus; // NOT_STARTED, STARTED, DONE
}
