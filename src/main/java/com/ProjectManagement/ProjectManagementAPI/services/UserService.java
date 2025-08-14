package com.ProjectManagement.ProjectManagementAPI.services;

import com.ProjectManagement.ProjectManagementAPI.dtos.CreateUserDto;
import com.ProjectManagement.ProjectManagementAPI.dtos.UpdateUserDto;
import com.ProjectManagement.ProjectManagementAPI.dtos.UserDto;
import com.ProjectManagement.ProjectManagementAPI.entities.Project;
import com.ProjectManagement.ProjectManagementAPI.entities.User;
import com.ProjectManagement.ProjectManagementAPI.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create New User
    public UserDto createUser(CreateUserDto dto) {
        // Check the email exist or not
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ResponseStatusException(BAD_REQUEST, "Email already exists");
        }

        User user = new User(dto.getName(), dto.getEmail(), dto.getRole());
        // save data into database through JPA
        userRepository.save(user);
        // convert entity to DTO before sending
        return toDto(user);
    }

    // Update User data
    public UserDto updateUser(Long id, UpdateUserDto dto) {
        User existinguser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
        if(dto.getEmail() != null) {
            existinguser.setEmail(dto.getEmail());
        }
        if(dto.getName() != null) {
            existinguser.setName(dto.getName());
        }
        if(dto.getRole() != null) {
            existinguser.setRole(dto.getRole());
        }

        userRepository.save(existinguser);
        return toDto(existinguser);
    }

    // Convert Entity to DTO
    private UserDto toDto(User u) {
        UserDto d = new UserDto();
        d.setId(u.getId());
        d.setName(u.getName());
        d.setEmail(u.getEmail());
        d.setRole(u.getRole());
        return d;
    }

    // Get data
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                             .stream()
                             .map(this::toDto)
                             .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id).map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
    }

    // Delete user data
    public void deleteUserById(Long id) {
        User user =  userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
        userRepository.deleteById(id);
    }

}
