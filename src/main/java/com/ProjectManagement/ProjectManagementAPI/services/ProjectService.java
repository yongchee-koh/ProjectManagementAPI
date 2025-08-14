package com.ProjectManagement.ProjectManagementAPI.services;

import com.ProjectManagement.ProjectManagementAPI.dtos.CreateProjectDto;
import com.ProjectManagement.ProjectManagementAPI.dtos.ProjectDto;
import com.ProjectManagement.ProjectManagementAPI.dtos.UpdateProjectDto;
import com.ProjectManagement.ProjectManagementAPI.entities.Project;
import com.ProjectManagement.ProjectManagementAPI.repositories.ProjectRepository;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Transactional
public class ProjectService {
    private final ProjectRepository projectRepository;
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // Role : ADMIN - Create Project
    public ProjectDto createProject(CreateProjectDto dto) {
        Project project = new Project(dto.getName(), dto.getDescription());
        projectRepository.save(project);
        return toDto(project);
    }

    // Role : ADMIN - Update Project
    public ProjectDto updateProject(Long id, UpdateProjectDto dto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Project not found"));
        if (dto.getName() != null) {
            project.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            project.setDescription(dto.getDescription());
        }
        projectRepository.save(project);
        return toDto(project);
    }

    // Role : ADMIN - Delete Project
    public void deleteProject(Long id) {
        Project p =  projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Project not found"));
        projectRepository.delete(p);
    }

    // Convert Entity to DTO
    private ProjectDto toDto(Project p) {
        ProjectDto d = new ProjectDto();
        d.setId(p.getId());
        d.setName(p.getName());
        d.setDescription(p.getDescription());
        return d;
    }


    // Get data
    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll()
                                .stream()
                                .map(this::toDto)
                                .collect(Collectors.toList());
    }

    public ProjectDto getProjectById(Long id) {
        return projectRepository.findById(id).map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Project not found"));
    }

    public Project findProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Project not found"));
    }



}
