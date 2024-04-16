package com.jsltd.cruddemo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsltd.cruddemo.dao.CRepository;
import com.jsltd.cruddemo.dao.URepository;
import com.jsltd.cruddemo.dto.CirclesDto;
import com.jsltd.cruddemo.entity.Circle;
import com.jsltd.cruddemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CirclesDtoServiceImpl implements CirclesDtoService{
    private CRepository circleRepository;
    private URepository userRepository;
    JedisPoolConfig poolConfig = new JedisPoolConfig();
    JedisPool jedisPool = new JedisPool(poolConfig, "localhost", 6379);
    private ObjectMapper mapper;

    @Autowired
    private MappingUtils mappingUtils;

    @Autowired
    public CirclesDtoServiceImpl(CRepository theCircleRepository, URepository userRepository, ObjectMapper mapper){
        circleRepository = theCircleRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public CirclesDto findById(Long id) {
        return mappingUtils.mapToCirclesDto(
                circleRepository.findById(id)
                        .orElse(new Circle())
        );
    }

    @Override
    public CirclesDto filterCirclesByStatus(CirclesDto circle, List<String> statuses) {
        if (statuses != null && !statuses.isEmpty()) {
            String circleStatus = circle.getStatus();
            if (!statuses.contains(circleStatus)) {
                String errorMessage = "Invalid status for the requested circle.";
                CirclesDto errorDto = new CirclesDto();
                errorDto.setStatus(errorMessage);
                errorDto.setChildCircles(new ArrayList<>());
                return errorDto;
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

    @Override
    public CirclesDto findByIdAndUser(Long id, Long userId) {
        Circle circle = circleRepository.findById(id)
                .orElse(new Circle());

        if (circle != null) {
            User currentUser = userRepository.findById(userId)
                    .orElse(new User());

            if (currentUser != null && isUserInCircle(currentUser, circle)) {
                CirclesDto circlesDto = mappingUtils.mapToCirclesDto(circle);
                List<String> statuses = Arrays.asList("DRAFT", "ACTIVE", "COMPLETED");
                return filterCirclesByStatus(circlesDto, statuses);
            } else {
                List<String> status = Arrays.asList("ACTIVE");
                CirclesDto circleDto = mappingUtils.mapToCirclesDto(circle);
                return filterCirclesByStatus(circleDto, status);
            }
        }

        return null;
    }

    private boolean isUserInCircle(User currentUser, Circle circle) {
        for (User user : circle.getUsers()) {
            if (user.getEmployeeId().equals(currentUser.getEmployeeId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public CirclesDto getCachedCircle(Long circleId,List<String> statuses) {
        try (Jedis jedis = jedisPool.getResource()) {
            String key = "circle:%d".formatted(circleId);
            String raw = jedis.get(key);
            if (raw != null) {
                CirclesDto cachedCircle = mapper.readValue(raw, CirclesDto.class);
                return filterCirclesByStatus(cachedCircle, statuses);
            }

            CirclesDto circlesDto = findById(circleId);
            try {
                String json = mapper.writeValueAsString(circlesDto);
                jedis.setex(key, 60, json);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return filterCirclesByStatus(circlesDto, statuses);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
