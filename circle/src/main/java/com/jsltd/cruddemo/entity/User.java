package com.jsltd.cruddemo.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Table(name="circle_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="circle_role")
    private String circle_role;
    @Column(name="capacity")
    private double capacity;
    @Column(name="employee_id")
    private int employee_id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="circle_id", nullable=true)
    @Fetch(FetchMode.JOIN)
    private Circle circle;

    public User(){}

    public User(String circle_role, double capacity, int employee_id) {
        this.circle_role = circle_role;
        this.capacity = capacity;
        this.employee_id = employee_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCircle_role() {
        return circle_role;
    }

    public void setCircle_role(String circle_role) {
        this.circle_role = circle_role;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", circle_role='" + circle_role + '\'' +
                ", capacity='" + capacity + '\'' +
                ", employee_id='" + employee_id + '\'' +
                '}';
    }
}
