package com.jsltd.cruddemo.dto;

import com.jsltd.cruddemo.entity.User;
import com.jsltd.cruddemo.service.CircleService;
import com.jsltd.cruddemo.service.UserService;

import java.util.List;

public class finsUsers {
    private UserService userService;
    public User[] getUsersWithCircleId(Long id) {

        List<User> userList = userService.findById(id);

        // Создаем массив и копируем найденных пользователей в него
        User[] usersArray = new User[userList.size()];
        usersArray = userList.toArray(usersArray);

        return usersArray;
    }
}
