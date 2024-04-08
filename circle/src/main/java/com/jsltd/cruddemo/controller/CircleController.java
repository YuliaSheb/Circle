package com.jsltd.cruddemo.controller;

import com.jsltd.cruddemo.dto.CirclesDto;
import com.jsltd.cruddemo.entity.Circle;
import com.jsltd.cruddemo.entity.User;
import com.jsltd.cruddemo.service.CircleService;
import com.jsltd.cruddemo.service.CirclesDtoService;
import com.jsltd.cruddemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/circles")
public class CircleController {
//    private CircleService circleService;
//    public CircleController(CircleService theCircleService){
//        circleService = theCircleService;
//    }

    private CirclesDtoService circlesService;

    @Autowired
    public CircleController(CirclesDtoService circlesService) {
        this.circlesService = circlesService;
    }

//    private UserService userService;
//    public CircleController(UserService theUserService){
//        userService = theUserService;
//    }

//    @GetMapping("/{circleId}")
//    public String listCircle(Model theModel, @PathVariable("circleId") Long circleId){
//        Circle circle = circleService.findById(circleId);
//        theModel.addAttribute("circle", circle);
//        return "circles";
//    }


    @GetMapping("/{id}")
    public CirclesDto getCirclesById(@PathVariable Long id) {
        return circlesService.findById(id);
    }

//    @GetMapping("/{userId}")
//    public String listUsers(Model theModel, @PathVariable("userId") Long userId){
//        User user = userService.findById(userId);
//        theModel.addAttribute("user", user);
//        return "users";
//    }
}