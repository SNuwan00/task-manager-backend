package com.example.task_manager_backend.repository;

import com.example.task_manager_backend.dto.TaskDetailsDTO;
import com.example.task_manager_backend.dto.response.TaskDetailsResponseDTO;
import com.example.task_manager_backend.model.Task;
import com.example.task_manager_backend.model.enums.TimeStatus;
import com.example.task_manager_backend.model.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);

    @Query("SELECT new com.example.task_manager_backend.dto.TaskDetailsDTO(t.taskId, t.title, ts.userStatus, ts.timeStatus) " +
            "FROM Task t " +
            "JOIN t.taskStatus ts " +
            "WHERE t.user.id = :userId AND ts.userStatus <> com.example.task_manager_backend.model.enums.UserStatus.DONE " +
            "ORDER BY t.startDate ASC")
    Page<TaskDetailsDTO> findTaskDetailsByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT new com.example.task_manager_backend.dto.TaskDetailsDTO(t.taskId, t.title, ts.userStatus, ts.timeStatus) " +
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

    @Modifying
    @Query("UPDATE Task t SET t.title = :title, t.description = :description, t.startDate = :startDate, " +
            "t.startTime = :startTime, t.endDate = :endDate, t.endTime = :endTime WHERE t.taskId = :taskId")
    int updateDetailsById(@Param("taskId") Long taskId,
                          @Param("title") String title,
                          @Param("description") String description,
                          @Param("startDate") String startDate,
                          @Param("startTime") String startTime,
                          @Param("endDate") String endDate,
                          @Param("endTime") String endTime);

}