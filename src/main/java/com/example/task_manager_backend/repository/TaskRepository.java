package com.example.task_manager_backend.repository;

import com.example.task_manager_backend.dto.TaskDetailsDTO;
import com.example.task_manager_backend.dto.response.TaskDetailsResponseDTO;
import com.example.task_manager_backend.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);

    @Query("SELECT new com.example.task_manager_backend.dto.TaskDetailsDTO(t.title, ts.userStatus, ts.timeStatus) " +
            "FROM Task t " +
            "JOIN t.taskStatus ts " +
            "WHERE t.user.id = :userId AND ts.userStatus <> com.example.task_manager_backend.model.enums.UserStatus.DONE " +
            "ORDER BY t.startDate ASC")
    Page<TaskDetailsDTO> findTaskDetailsByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT new com.example.task_manager_backend.dto.TaskDetailsDTO(t.title, ts.userStatus, ts.timeStatus) " +
            "FROM Task t " +
            "JOIN t.taskStatus ts " +
            "WHERE t.user.id = :userId AND ts.userStatus = com.example.task_manager_backend.model.enums.UserStatus.DONE " +
            "ORDER BY t.startDate ASC")
    Page<TaskDetailsDTO> findDoneTaskDetailsByUserId(Long userId, Pageable pageable);

    @Query("SELECT new com.example.task_manager_backend.dto.response.TaskDetailsResponseDTO(" +
            "t.taskId, t.title, t.description, t.startDate, t.startTime, t.endDate, t.endTime, " +
            "ts.userStatus, ts.timeStatus) " +
            "FROM Task t " +
            "JOIN t.taskStatus ts " +
            "WHERE t.taskId = :taskId")
    Optional<TaskDetailsResponseDTO> findAllDetailsById(Long taskId);
}