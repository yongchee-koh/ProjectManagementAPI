package com.ProjectManagement.ProjectManagementAPI.controllers;

import com.ProjectManagement.ProjectManagementAPI.dtos.CreateProjectDto;
import com.ProjectManagement.ProjectManagementAPI.dtos.ProjectDto;
import com.ProjectManagement.ProjectManagementAPI.dtos.UpdateProjectDto;
import com.ProjectManagement.ProjectManagementAPI.enums.Role;
import com.ProjectManagement.ProjectManagementAPI.services.ProjectService;
import com.ProjectManagement.ProjectManagementAPI.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping
    public List<ProjectDto> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public ProjectDto getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @PostMapping("/users/{userID}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createProject(@PathVariable Long userID, @Valid @RequestBody CreateProjectDto dto) {
        Role identifyRole = userService.getUserById(userID).getRole();

        if (!identifyRole.equals(Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admin can create projects");
        }
        ProjectDto projectDto = projectService.createProject(dto);
        return new ResponseEntity<>(projectDto, HttpStatus.CREATED);
    }

    @PutMapping("/users/{userID}/{projectID}")
    public ResponseEntity<?> updateProject(@PathVariable Long userID, @PathVariable Long projectID, @Valid @RequestBody UpdateProjectDto dto) {
        Role identifyRole = userService.getUserById(userID).getRole();

        if (!identifyRole.equals(Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admin can update project");
        }
        ProjectDto projectDto = projectService.updateProject(projectID, dto);
        return new ResponseEntity<>(projectDto, HttpStatus.OK);
    }

    @DeleteMapping("/users/{userID}/{projectID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteProject(@PathVariable Long userID, @PathVariable Long projectID) {
        Role identifyRole = userService.getUserById(userID).getRole();

        if (!identifyRole.equals(Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admin can delete projects");
        } else {
            projectService.deleteProject(projectID);
            return new ResponseEntity<>("Project deleted",HttpStatus.OK);
        }
    }

}
