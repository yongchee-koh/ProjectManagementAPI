package com.ProjectManagement.ProjectManagementAPI.dtos;

import com.ProjectManagement.ProjectManagementAPI.enums.Role;
// import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data // Generates getter, setter, equals, hashCode, toString & constructor - Only for final & @NotNull
@NoArgsConstructor // Needed for JSON deserialization
@AllArgsConstructor // Easy object creation

// Only Used for create user
public class CreateUserDto {
    @NotBlank(message = "User name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotNull(message = "Role is required")
    private Role role;

//    // Getter & Setter
//    public String getName() {
//        return name;
//    }
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public Role getRole() {
//        return role;
//    }
//    public void setRole(Role role) {
//        this.role = role;
//    }
}
