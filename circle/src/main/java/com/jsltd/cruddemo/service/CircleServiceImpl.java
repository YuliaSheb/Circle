package com.jsltd.cruddemo.service;

import com.jsltd.cruddemo.dao.CRepository;
import com.jsltd.cruddemo.dto.CirclesDto;
import com.jsltd.cruddemo.entity.Circle;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CircleServiceImpl implements CircleService {

    private CRepository circleRepository;

    @Autowired
    private MappingUtils mappingUtils;

    @Autowired
    public CircleServiceImpl(CRepository theCircleRepository){
        circleRepository = theCircleRepository;
    }

    @Override
    public List<Circle> findAll() {
        return circleRepository.findAll();
    }

    @Override
    public Circle findById(Long theId) {
        Optional<Circle> result = circleRepository.findById(theId);

        Circle theCircle = null;

        if(result.isPresent()){
            theCircle = result.get();
        }
        else{
            throw new EntityNotFoundException("Did not find Circle id - "+theId);
        }

        return theCircle;
    }


    @Override
    public Circle save(Circle theCircle) {
        return circleRepository.save(theCircle);
    }

    @Transactional
    @Override
    public void deleteById(Long theId) {
        circleRepository.deleteById(theId);
    }
}
