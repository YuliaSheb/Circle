package com.jsltd.cruddemo.dto;

import com.jsltd.cruddemo.entity.User;
import com.jsltd.cruddemo.service.UserService;

import java.util.Collections;
import java.util.List;

public class findUsers {
    private UserService userService;

    public findUsers(UserService userService) {
        this.userService = userService;
    }

    public void getUsersWithCircleId(CirclesDto circlesDto, Long id) {
        User user = userService.findById(id);

        List<User> userList = Collections.singletonList(user);

        User[] usersArray = new User[userList.size()];
        usersArray = userList.toArray(usersArray);

        circlesDto.setUsers(usersArray);
    }
}