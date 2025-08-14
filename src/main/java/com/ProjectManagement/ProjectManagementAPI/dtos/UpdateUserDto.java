package com.ProjectManagement.ProjectManagementAPI.dtos;

import com.ProjectManagement.ProjectManagementAPI.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
    private String name;

    @Email(message = "Email format is invaid")
    private String email;

    private Role role;
}
