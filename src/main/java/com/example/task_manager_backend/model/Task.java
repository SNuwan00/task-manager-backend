package com.example.task_manager_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tasks")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String startDate;
    private String startTime;

    private String endDate;
    private String endTime;

    @JsonIgnore
    @JsonManagedReference
    @OneToOne(mappedBy = "task", cascade = CascadeType.ALL)
    private TaskStatus taskStatus;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}