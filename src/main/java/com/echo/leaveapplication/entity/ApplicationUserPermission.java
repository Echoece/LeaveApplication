package com.echo.leaveapplication.entity;

public enum ApplicationUserPermission {
    EMPLOYEE("employee"),
    MANAGER("manager"),
    ADMIN("admin");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
