package com.jsltd.cruddemo.entity;

import jakarta.persistence.*;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="circles")
public class Circle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="parent_id")
    private Long parentId;
    @Column(name="type")
    private String type;
    @Column(name="circle_path")
    private String circlePath;

    @OneToMany(targetEntity = User.class, mappedBy = "circle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;


    public Circle(){}

    public Circle(String name, Long parentId, String type) {
        this.name = name;
        this.parentId = parentId;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parent_id='" + parentId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
