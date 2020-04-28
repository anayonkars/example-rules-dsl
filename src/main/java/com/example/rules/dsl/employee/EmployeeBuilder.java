package com.example.rules.dsl.employee;

import java.time.LocalDateTime;

public final class EmployeeBuilder {
    private String id;
    private LocalDateTime doj;
    private PerformanceRating performanceRating;
    private Role role;

    private EmployeeBuilder() {
    }

    public static EmployeeBuilder anEmployee() {
        return new EmployeeBuilder();
    }

    public EmployeeBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public EmployeeBuilder withDoj(LocalDateTime doj) {
        this.doj = doj;
        return this;
    }

    public EmployeeBuilder withPerformanceRating(PerformanceRating performanceRating) {
        this.performanceRating = performanceRating;
        return this;
    }

    public EmployeeBuilder withRole(Role role) {
        this.role = role;
        return this;
    }

    public Employee build() {
        return new Employee(id, doj, performanceRating, role);
    }
}
