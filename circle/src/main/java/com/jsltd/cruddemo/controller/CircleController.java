package com.jsltd.cruddemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsltd.cruddemo.dto.CirclesDto;
import com.jsltd.cruddemo.entity.Circle;
import com.jsltd.cruddemo.entity.User;
import com.jsltd.cruddemo.service.CircleService;
import com.jsltd.cruddemo.service.CirclesDtoService;
import com.jsltd.cruddemo.service.UserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Random;

import static redis.clients.jedis.Protocol.Command.TTL;


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

    @GetMapping("/rand")
    public CirclesDto getCirclesById() {
        Random random = new Random();
        long randomId = random.nextInt(4) + 1;
        return circlesService.findById(randomId);
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