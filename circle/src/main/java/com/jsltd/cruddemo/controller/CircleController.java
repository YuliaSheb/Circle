package com.jsltd.cruddemo.controller;

import com.jsltd.cruddemo.dao.CircleRepository;
import com.jsltd.cruddemo.entity.Circle;
import com.jsltd.cruddemo.service.CircleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/circles")
public class CircleController {
    private final CircleRepository circleRepository;

    @Autowired
    public CircleController(CircleRepository circleRepository) {
        this.circleRepository = circleRepository;
    }

    @GetMapping("/{circleId}")
    public List<Circle> getAllCircles(@PathVariable("circleId") int circleId) {
        return circleRepository.getCircles(circleId);
    }
}