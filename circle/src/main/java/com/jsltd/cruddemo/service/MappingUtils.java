package com.jsltd.cruddemo.service;

import com.jsltd.cruddemo.dto.CirclesDto;
import com.jsltd.cruddemo.dto.UserDto;
import com.jsltd.cruddemo.entity.Circle;
import com.jsltd.cruddemo.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MappingUtils {

    public CirclesDto mapToCirclesDto(Circle circle) {
        CirclesDto dto = new CirclesDto();
        dto.setId(circle.getId());
        dto.setName(circle.getName());
        dto.setParentId((circle.getParent() != null) ? circle.getParent().getId() : null);
        dto.setType(circle.getType());
        dto.setStatus(circle.getStatus());
        dto.setUsers(mapUsersToArray(circle.getUsers()));
        dto.setChildCircles(mapChildCirclesToArray(circle.getChildCircles()));
        return dto;
    }

    private Object[] mapUsersToArray(List<User> users) {
        List<Object> userArray = new ArrayList<>();
        for (User user : users) {
            String userString = "id: " + user.getId() + ", circle_role: " + user.getCircleRole() + ", capacity: "
                    + user.getCapacity() + ", employee_id: " + user.getEmployeeId();
            userArray.add(userString);
        }
        return userArray.toArray();
    }

    private List<CirclesDto> mapChildCirclesToArray(List<Circle> childCircles) {
        List<CirclesDto> circleArray = new ArrayList<>();
        for (Circle childCircle : childCircles) {
            CirclesDto childCircleDto = mapToCirclesDto(childCircle);
            circleArray.add(childCircleDto);
        }
        return circleArray;
    }

//    private List<UserDto> mapUsersDtoList(List<User> users) {
//        List<UserDto> userArray = new ArrayList<>();
//        for (User user : users) {
//            UserDto userDto = mapUserDto(user);
//            userArray.add(userDto);
//        }
//        return userArray;
//    }
//
//    private UserDto mapUserDto(User user) {
//        UserDto userDto = new UserDto();
//        return userDto;
//    }
}