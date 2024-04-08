package com.jsltd.cruddemo.dao;

import com.jsltd.cruddemo.entity.Circle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CRepository extends JpaRepository<Circle, Long> {
}
