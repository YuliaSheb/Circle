package com.jsltd.cruddemo.service;

import com.jsltd.cruddemo.dao.CRepository;
import com.jsltd.cruddemo.dto.CirclesDto;
import com.jsltd.cruddemo.entity.Circle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CirclesDtoServiceImpl implements CirclesDtoService{
    private CRepository circleRepository;

    @Autowired
    private MappingUtils mappingUtils;

    @Autowired
    public CirclesDtoServiceImpl(CRepository theCircleRepository){
        circleRepository = theCircleRepository;
    }

    @Override
    public CirclesDto findById(Long id) {
        return mappingUtils.mapToCirclesDto(
                circleRepository.findById(id)
                        .orElse(new Circle())
        );
    }
}
