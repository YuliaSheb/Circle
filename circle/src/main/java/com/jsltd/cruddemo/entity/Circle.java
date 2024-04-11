package com.jsltd.cruddemo.entity;

import jakarta.persistence.*;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "circles")
public class Circle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "circle_path")
    private String circlePath;

    @Column(name = "status")
    private String status;

    @OneToMany(targetEntity = User.class, mappedBy = "circle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;

    @OneToMany(targetEntity = Circle.class, mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Circle> childCircles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Circle parent;


    public Circle() {
    }

    public Circle(String name, String type, String status) {
        this.name = name;
        this.type = type;
        this.status = status;
    }

    @Override
    public String toString() {
        String parentIdString = (parent != null) ? String.valueOf(parent.getId()) : "null";
        return "Circle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentIdString +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}