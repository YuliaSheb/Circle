package com.jsltd.cruddemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsltd.cruddemo.dto.CirclesDto;
import com.jsltd.cruddemo.service.CirclesDtoService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@RestController
@RequestMapping("/circles")
public class CircleController {
//    private CircleService circleService;
//    public CircleController(CircleService theCircleService){
//        circleService = theCircleService;
//    }

    private CirclesDtoService circlesService;
    //private JedisPool jedisPool;
    private ObjectMapper mapper;

    JedisPoolConfig poolConfig = new JedisPoolConfig();
    JedisPool jedisPool = new JedisPool(poolConfig, "localhost", 6379);

    @Autowired
    public CircleController(CirclesDtoService circlesService, ObjectMapper mapper) {
        this.circlesService = circlesService;
        this.mapper = mapper;
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

//    @GetMapping("/en")
//    public String getenumById(@RequestParam(name = "statuses", required = false) Status statuses) {
//        return statuses.name();
//    }

    @GetMapping("/rand")
    public CirclesDto getCirclesById() {
        Random random = new Random();
        long randomId = random.nextInt(4) + 1;
        CirclesDto circlesDto = circlesService.findById(randomId);

        return circlesDto;
    }

    @GetMapping("/filter/{id}")
    public Object getCircleByIdAndFilter(
            @PathVariable Long id,
            @RequestParam(required = false) List<String> statuses
    ) {
        CirclesDto circle = circlesService.findById(id);
        if (statuses != null && !statuses.isEmpty()) {
            String circleStatus = circle.getStatus();
            if (!statuses.contains(circleStatus)) {
                System.out.println(statuses);
                System.out.println(circleStatus);
                String errorMessage = "Invalid status for the requested circle.";
                return errorMessage;
            }
            circle.setChildCircles(filterChildrenByStatus(circle.getChildCircles(), statuses));
        }
        return circle;
    }

    private List<CirclesDto> filterChildrenByStatus(List<CirclesDto> children, List<String> statuses) {
        List<CirclesDto> filteredChildren = new ArrayList<>();
        for (CirclesDto child : children) {
            if (statuses.contains(child.getStatus())) {
                filteredChildren.add(child);
                child.setChildCircles(filterChildrenByStatus(child.getChildCircles(), statuses));
            }
        }
        return filteredChildren;
    }

    @GetMapping("/random")
    @Cacheable(value = "randomCircle")
    public CirclesDto getRandomCircle() {
        Random random = new Random();
        long randomId = random.nextInt(4) + 1;
        return circlesService.findById(randomId);
    }

    @GetMapping("/cache")
    public CirclesDto getCachedRandomCircle(){
        Random random = new Random();
        long randomId = random.nextInt(4) + 1;
        try (Jedis jedis = jedisPool.getResource()){
            String key = "circle:%d".formatted(randomId);
            String raw = jedis.get(key);
            if (raw != null) {
                return mapper.readValue(raw, CirclesDto.class);
            }
            CirclesDto circlesDto = circlesService.findById(randomId);
            try {
                String json = mapper.writeValueAsString(circlesDto);
                jedis.setex(key, 60, json);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return circlesDto;
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

//    @GetMapping("/{userId}")
//    public String listUsers(Model theModel, @PathVariable("userId") Long userId){
//        User user = userService.findById(userId);
//        theModel.addAttribute("user", user);
//        return "users";
//    }
}