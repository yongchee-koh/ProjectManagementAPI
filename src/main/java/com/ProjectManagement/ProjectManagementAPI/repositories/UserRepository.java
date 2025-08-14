package com.ProjectManagement.ProjectManagementAPI.repositories;

import com.ProjectManagement.ProjectManagementAPI.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Check if email exist
    boolean existsByEmail(String email);

    // Get full user details by email
    // Use Option<User> when email does not exist,
    // return Optional.empty() - println: "Optional is empty",
    // rather than return null
    Optional<User> findByEmail(String email);
}
