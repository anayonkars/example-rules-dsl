package com.example.rules.dsl.employee;

import java.time.LocalDateTime;

public class Employee {

    private String id;
    private LocalDateTime doj;

    public Employee(String id, LocalDateTime doj) {
        this.id = id;
        this.doj = doj;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getDoj() {
        return doj;
    }
}
