package com.ProjectManagement.ProjectManagementAPI.repositories;

import com.ProjectManagement.ProjectManagementAPI.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // Get Task with project id
    List<Task> findByProjectId(Long projectId);
    List<Task> findByUserId(Long userId);
}
