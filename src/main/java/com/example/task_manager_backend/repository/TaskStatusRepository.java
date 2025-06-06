package com.example.task_manager_backend.repository;

import com.example.task_manager_backend.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {
    Optional<TaskStatus> findByTaskTaskId(Long taskId);
}
