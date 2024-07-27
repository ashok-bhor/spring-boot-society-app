package com.abpoint.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abpoint.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
