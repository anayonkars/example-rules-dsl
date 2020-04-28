package com.example.rules.dsl.employee;

import java.time.LocalDateTime;

public class Employee {

    private String id;
    private LocalDateTime doj;
    private PerformanceRating performanceRating;
    private Role role;

    public Employee(String id,
                    LocalDateTime doj,
                    PerformanceRating performanceRating,
                    Role role) {
        this.id = id;
        this.doj = doj;
        this.performanceRating = performanceRating;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getDoj() {
        return doj;
    }

    public PerformanceRating getPerformanceRating() {
        return performanceRating;
    }

    public Role getRole() {
        return role;
    }


}
