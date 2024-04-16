package com.jsltd.cruddemo.dao;

import com.jsltd.cruddemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface URepository extends JpaRepository<User, Long> {
    Optional<User> findByEmployeeId(Long employeeId);
}
