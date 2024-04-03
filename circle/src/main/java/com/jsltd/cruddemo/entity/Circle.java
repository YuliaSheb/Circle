package com.jsltd.cruddemo.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="circles")
public class Circle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="parent_id")
    private Integer parent_id;
    @Column(name="type")
    private String type;
    @Column(name="circle_path")
    private String circle_path;

    @OneToMany(targetEntity = User.class, mappedBy = "circle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;


    public Circle(){}

    public Circle(String name, Integer parent_id, String type) {
        this.name = name;
        this.parent_id = parent_id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }


    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCircle_path() {
        return circle_path;
    }

    public void setCircle_path(String circle_path) {
        this.circle_path = circle_path;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parent_id='" + parent_id + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
