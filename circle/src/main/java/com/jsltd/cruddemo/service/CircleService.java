package com.jsltd.cruddemo.service;

import com.jsltd.cruddemo.dto.CirclesDto;
import com.jsltd.cruddemo.entity.Circle;

import java.util.List;

public interface CircleService {

    List<Circle> findAll();

    Circle findById(Long theId);

    Circle save(Circle theCircle);

    void deleteById(Long theId);
}
