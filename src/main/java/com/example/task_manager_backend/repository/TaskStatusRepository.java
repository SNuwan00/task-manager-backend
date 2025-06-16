package com.example.task_manager_backend.repository;

import com.example.task_manager_backend.model.TaskStatus;
import com.example.task_manager_backend.model.enums.TimeStatus;
import com.example.task_manager_backend.model.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {
    Optional<TaskStatus> findByTaskTaskId(Long taskId);

    @Transactional
    @Modifying
    @Query("UPDATE TaskStatus ts SET ts.userStatus = :userStatus, ts.timeStatus = :timeStatus WHERE ts.task.taskId = :taskId")
    int updateStatusByTaskId(@Param("taskId") Long taskId,
                             @Param("userStatus") UserStatus userStatus,
                             @Param("timeStatus") TimeStatus timeStatus);
}
