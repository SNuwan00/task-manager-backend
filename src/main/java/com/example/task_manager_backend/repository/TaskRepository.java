package com.example.task_manager_backend.repository;

import com.example.task_manager_backend.dto.TaskDetailsDTO;
import com.example.task_manager_backend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);

    @Query("SELECT new com.example.task_manager_backend.dto.TaskDetailsDTO(t.title, ts.userStatus, ts.timeStatus) " +
            "FROM Task t " +
            "JOIN t.taskStatus ts " +
            "WHERE t.user.id = :userId")
    List<TaskDetailsDTO> findTaskDetailsByUserId(@Param("userId") Long userId);
}