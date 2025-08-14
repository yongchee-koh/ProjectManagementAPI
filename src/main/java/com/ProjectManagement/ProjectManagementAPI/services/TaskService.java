package com.ProjectManagement.ProjectManagementAPI.services;

import com.ProjectManagement.ProjectManagementAPI.dtos.CreateTaskDto;
import com.ProjectManagement.ProjectManagementAPI.dtos.TaskDto;
import com.ProjectManagement.ProjectManagementAPI.dtos.UpdateTaskDto;
import com.ProjectManagement.ProjectManagementAPI.entities.Project;
import com.ProjectManagement.ProjectManagementAPI.entities.Task;
import com.ProjectManagement.ProjectManagementAPI.entities.User;
import com.ProjectManagement.ProjectManagementAPI.enums.Role;
import com.ProjectManagement.ProjectManagementAPI.repositories.TaskRepository;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Service
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ProjectService projectService;

    public TaskService(TaskRepository taskRepository, UserService userService, ProjectService projectService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.projectService = projectService;
    }

    // Get Task data by project id
    public List<TaskDto> getTasksByProjectId(Long projectId) {
        return taskRepository.findByProjectId(projectId).stream().map(this::toDto).collect(Collectors.toList());
    }

    // Get Task data by user id
    public List<TaskDto> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId).stream().map(this::toDto).collect(Collectors.toList());
    }

    public TaskDto getTaskById(Long id) {
        return taskRepository.findById(id).map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Task not found"));
    }

    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll()
                             .stream()
                             .map(this::toDto)
                             .collect(Collectors.toList());
    }

    // Create Task
    public TaskDto createTask(Long projectID, Long userID, CreateTaskDto dto) {
        Project project = projectService.findProjectById(projectID);
        User user = userService.findUserById(userID);
        Task task = new Task(dto.getTitle(), dto.getDescription(), dto.getStatus(), project, user);
        taskRepository.save(task);
        return toDto(task);
    }

    // Update Task
    public TaskDto updateTask(Long taskId, UpdateTaskDto dto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Task not found"));
        // Authorization: Only current task user or ADMIN can update the task
//        User currentUser = currentUserCheck();
//        if(!currentUser.getRole().equals(Role.ADMIN) && !task.getUser().getId().equals(currentUser.getId())) {
//            throw new ResponseStatusException(FORBIDDEN, "Not allowed to update this task");
//        }
        if (dto.getTitle() != null) {
            task.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            task.setDescription(dto.getDescription());
        }
        if (dto.getStatus() != null) {
            task.setStatus(dto.getStatus());
        }

        taskRepository.save(task);
        return toDto(task);

    }

    // Delete Task
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Task not found"));
//        User currentUser = currentUserCheck();
//        if(!currentUser.getRole().equals(Role.ADMIN) && !task.getUser().getId().equals(currentUser.getId())) {
//            throw new ResponseStatusException(FORBIDDEN, "Not allowed to delete this task");
//        }
        taskRepository.delete(task);
    }

    // Convert Entity to DTO
    private TaskDto toDto(Task t) {
        TaskDto d = new TaskDto();
        d.setId(t.getId());
        d.setTitle(t.getTitle());
        d.setDescription(t.getDescription());
        d.setStatus(t.getStatus());
        d.setProjectId(t.getProject().getId());
        d.setUserId(t.getUser().getId());
        return d;
    }

    // Authorization: Check current user or ADMIN
//    private User currentUserCheck() {
//        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
//        if (auth == null) {
//            throw new ResponseStatusException(UNAUTHORIZED, "Not authenticated");
//        }
//        String email = auth.getName();
//        return userService.findUserByEmail(email);
//    }
}
