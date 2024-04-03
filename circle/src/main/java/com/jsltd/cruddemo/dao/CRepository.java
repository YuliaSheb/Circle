package com.jsltd.cruddemo.dao;

import com.jsltd.cruddemo.entity.Circle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CRepository extends JpaRepository<Circle, Integer> {
}
