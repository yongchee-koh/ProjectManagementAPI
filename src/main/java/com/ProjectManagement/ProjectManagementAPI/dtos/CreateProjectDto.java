package com.ProjectManagement.ProjectManagementAPI.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectDto {
    @NotBlank(message = "Project name is required")
    private String name;
    private String description;
}
