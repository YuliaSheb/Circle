package com.jsltd.cruddemo.service;

import com.jsltd.cruddemo.dto.CirclesDto;

import java.util.List;

public interface CirclesDtoService {
    CirclesDto findById(Long theId);
    CirclesDto filterCirclesByStatus(CirclesDto circle, List<String> statuses);
    CirclesDto getCachedCircle(Long theId, List<String> statuses);
    CirclesDto findByIdAndUser(Long theId, Long userId);
}
