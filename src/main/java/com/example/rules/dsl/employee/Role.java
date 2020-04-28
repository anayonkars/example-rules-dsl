package com.example.rules.dsl.employee;

import java.time.LocalDateTime;

public class Role {
    private final RoleValue roleValue;
    private final LocalDateTime roleDate;

    public Role(RoleValue roleValue, LocalDateTime roleDate) {
        this.roleValue = roleValue;
        this.roleDate = roleDate;
    }

    public RoleValue getRoleValue() {
        return roleValue;
    }

    public LocalDateTime getRoleDate() {
        return roleDate;
    }
}
