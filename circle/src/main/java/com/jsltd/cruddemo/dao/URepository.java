package com.jsltd.cruddemo.dao;

import com.jsltd.cruddemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface URepository extends JpaRepository<User, Long> {
}
