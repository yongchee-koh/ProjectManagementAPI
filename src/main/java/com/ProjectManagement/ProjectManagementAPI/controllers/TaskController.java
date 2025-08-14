package com.ProjectManagement.ProjectManagementAPI.controllers;

import com.ProjectManagement.ProjectManagementAPI.dtos.CreateTaskDto;
import com.ProjectManagement.ProjectManagementAPI.dtos.TaskDto;
import com.ProjectManagement.ProjectManagementAPI.dtos.UpdateTaskDto;
import com.ProjectManagement.ProjectManagementAPI.enums.Role;
import com.ProjectManagement.ProjectManagementAPI.services.TaskService;
import com.ProjectManagement.ProjectManagementAPI.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/api/projects/{projectID}/tasks")
    public List<TaskDto> getTasksByProjectId(@PathVariable Long projectID) {
        return taskService.getTasksByProjectId(projectID);
    }

    @GetMapping("/api/users/{userID}/tasks")
    public List<TaskDto> getTasksByUserId(@PathVariable Long userID) {
        return taskService.getTasksByUserId(userID);
    }

    @GetMapping("/api/tasks/{id}")
    public TaskDto getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/api/tasks")
    public List<TaskDto> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping("/api/projects/{projectID}/user/{userID}/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(@PathVariable Long projectID, @PathVariable Long userID, @Valid @RequestBody CreateTaskDto dto) {
        return taskService.createTask(projectID, userID, dto);
    }

    @PutMapping("/api/users/{userID}/tasks/{taskID}")
    public ResponseEntity<?> updateTask(@PathVariable Long userID, @PathVariable Long taskID, @Valid @RequestBody UpdateTaskDto dto) {
        Role identifyRole = userService.getUserById(userID).getRole();

        // Only Admin and Task Owner can edit the task
        if(!identifyRole.equals(Role.ADMIN) && !taskService.getTaskById(taskID).getUserId().equals(userID)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admin and Task Owner can update task");
        }
        TaskDto taskdto = taskService.updateTask(taskID, dto);
        return new ResponseEntity<>(taskdto, HttpStatus.OK);
    }

    @DeleteMapping("/api/user/{userID}/tasks/{taskID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteTask(@PathVariable Long userID, @PathVariable Long taskID) {
        Role identifyRole = userService.getUserById(userID).getRole();

        // Only Admin & Owner user can delete the task
        if (!identifyRole.equals(Role.ADMIN) && !taskService.getTaskById(taskID).getUserId().equals(userID)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admin and Task Owner can delete task");
        } else {
            taskService.deleteTask(taskID);
            return new ResponseEntity<>("Task deleted",HttpStatus.OK);
        }
    }
}
