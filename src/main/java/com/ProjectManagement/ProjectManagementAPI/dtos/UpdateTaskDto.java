package com.ProjectManagement.ProjectManagementAPI.dtos;

import com.ProjectManagement.ProjectManagementAPI.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskDto {
    private String title;
    private String description;
    private TaskStatus status;
}
