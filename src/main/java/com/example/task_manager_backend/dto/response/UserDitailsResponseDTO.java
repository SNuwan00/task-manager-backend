package com.example.task_manager_backend.dto.response;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserDitailsResponseDTO {

    private String username;
    private String email;
    private String profilePhotoPath;
    private String endDate;
}
