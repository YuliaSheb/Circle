package com.jsltd.cruddemo.dto;

import lombok.Data;

@Data
public class CirclesDto {
    Long id;
    Long parentId;
    String type;
    Object[] users;
    Object[] childCircles;
}