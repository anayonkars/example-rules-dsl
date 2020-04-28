package com.example.rules.dsl.employee;

public enum RoleValue {
    JUNIOR_ENGG(1),
    ENGG(2),
    SENIOR_ENGG(3),
    TECH_LEAD(4),
    TEAM_LEAD(5),
    UNIT_LEAD(6),
    REGION_LEAD(7),
    AVP(8),
    VP(9),
    CXO(10);

    private final int roleValue;

    RoleValue(int roleValue) {
        this.roleValue = roleValue;
    }

    public int getRoleValue() {
        return roleValue;
    }
}
