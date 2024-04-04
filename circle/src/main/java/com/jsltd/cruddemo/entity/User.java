package com.jsltd.cruddemo.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
@Entity
@Table(name="circle_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="circle_role")
    private String circleRole;
    @Column(name="capacity")
    private double capacity;
    @Column(name="employee_id")
    private Long employeeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="circle_id", nullable=true)
    @Fetch(FetchMode.JOIN)
    private Circle circle;

    public User(){}

    public User(String circleRole, double capacity, Long employeeId) {
        this.circleRole = circleRole;
        this.capacity = capacity;
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", circle_role='" + circleRole + '\'' +
                ", capacity='" + capacity + '\'' +
                ", employee_id='" + employeeId + '\'' +
                '}';
    }
}
