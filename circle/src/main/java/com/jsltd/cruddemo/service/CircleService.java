package com.jsltd.cruddemo.service;

import com.jsltd.cruddemo.entity.Circle;

import java.util.List;

public interface CircleService {

    List<Circle> findAll();

    Circle findById(int theId);

    Circle save(Circle theEmployee);

    void deleteById(int theId);
}
