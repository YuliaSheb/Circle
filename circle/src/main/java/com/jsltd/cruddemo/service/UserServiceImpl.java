package com.jsltd.cruddemo.service;

import com.jsltd.cruddemo.dao.CRepository;
import com.jsltd.cruddemo.dao.URepository;
import com.jsltd.cruddemo.entity.Circle;
import com.jsltd.cruddemo.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private URepository userRepository;

    @Autowired
    public UserServiceImpl(URepository theUserRepository){
        userRepository = theUserRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long theId) {
        Optional<User> result = userRepository.findById(theId);

        User theUser = null;

        if(result.isPresent()){
            theUser = result.get();
        }
        else{
            throw new EntityNotFoundException("Did not find User id - "+theId);
        }

        return theUser;
    }

    @Override
    public User save(User theUser) {
        return userRepository.save(theUser);
    }

    @Transactional
    @Override
    public void deleteById(Long theId) {
        userRepository.deleteById(theId);
    }
}
