package com.jsltd.cruddemo.dto;

import com.jsltd.cruddemo.entity.Circle;
import com.jsltd.cruddemo.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class CirclesDto {
    private Long id;
    private String name;
    private Long parentId;
    private String type;
    private String status;
    private Object[] users;
    private List<CirclesDto> childCircles;

    public void setUsers(Object[] users) {
        this.users = users;
    }

    public void setChildCircles(List<CirclesDto> childCircles) {
        this.childCircles = childCircles;
    }
}