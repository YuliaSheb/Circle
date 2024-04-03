package com.jsltd.cruddemo.service;

import com.jsltd.cruddemo.dao.CRepository;
import com.jsltd.cruddemo.dao.CircleRepository;
import com.jsltd.cruddemo.entity.Circle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CircleServiceImpl implements CircleService {

    private CRepository circleRepository;

    @Autowired
    public CircleServiceImpl(CRepository theCircleRepository){
        circleRepository = theCircleRepository;
    }

    @Override
    public List<Circle> findAll() {
        return circleRepository.findAll();
    }

    @Override
    public Circle findById(int theId) {
        Optional<Circle> result = circleRepository.findById(theId);

        Circle theCircle = null;

        if(result.isPresent()){
            theCircle = result.get();
        }
        else{
            throw new RuntimeException("Did not find Circle id - "+theId);
        }

        return theCircle;
    }

    @Override
    public Circle save(Circle theCircle) {
        return circleRepository.save(theCircle);
    }

    @Transactional
    @Override
    public void deleteById(int theId) {
        circleRepository.deleteById(theId);
    }
}
