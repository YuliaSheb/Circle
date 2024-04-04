package com.jsltd.cruddemo.controller;

import com.jsltd.cruddemo.dto.CirclesDto;
import com.jsltd.cruddemo.entity.Circle;
import com.jsltd.cruddemo.entity.User;
import com.jsltd.cruddemo.service.CircleService;
import com.jsltd.cruddemo.service.UserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/circles")
public class CircleController {
    private CircleService circleService;
    public CircleController(CircleService theCircleService){
        circleService = theCircleService;
    }

    private UserService userService;
    public CircleController(UserService theUserService){
        userService = theUserService;
    }

    @GetMapping("/{circleId}")
    public String listCircle(Model theModel, @PathVariable("circleId") Long circleId){
        Circle circle = circleService.findById(circleId);
        theModel.addAttribute("circle", circle);
        return "circles";
    }

    @GetMapping("/{userId}")
    public String listUsers(Model theModel, @PathVariable("userId") Long userId){
        User user = userService.findById(userId);
        theModel.addAttribute("user", user);
        return "users";
    }
}