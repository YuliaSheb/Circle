package com.jsltd.cruddemo.service;

import com.jsltd.cruddemo.entity.Circle;
import com.jsltd.cruddemo.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long theId);

    User save(User theUser);

    void deleteById(Long theId);
}
