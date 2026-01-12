package com.example.demo.repository;


import com.example.demo.model.Role;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
    User findByRole(Role role);
    Optional<User> findByVerificationToken(String token);

}
