package com.ProjectManagement.ProjectManagementAPI.repositories;

import com.ProjectManagement.ProjectManagementAPI.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
