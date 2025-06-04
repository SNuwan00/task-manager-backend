package com.example.task_manager_backend.model;

import com.example.task_manager_backend.model.enums.TimeStatus;
import com.example.task_manager_backend.model.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "task_status")
@Data
public class TaskStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    @OneToOne
    @JoinColumn(name = "task_id", referencedColumnName = "taskId")
    private Task task;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TimeStatus timeStatus;  // UPCOMING, IN_PROGRESS, ENDED

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UserStatus userStatus;  // NOT_STARTED, STARTED, DONE

    private LocalDateTime lastUpdated;

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        this.lastUpdated = LocalDateTime.now();
    }
}
