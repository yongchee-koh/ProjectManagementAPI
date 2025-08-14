package com.ProjectManagement.ProjectManagementAPI.dtos;

import com.ProjectManagement.ProjectManagementAPI.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private Role role;

}
