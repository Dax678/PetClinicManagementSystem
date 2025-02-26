package org.example.petclinicmanagementsystem.Data.Entity;

import lombok.Getter;

@Getter
public enum UserRole {
    VETERINARIAN("VETERINARIAN"),
    OWNER("OWNER"),
    ADMIN("ADMIN");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRoleWithPrefix() {
        return "ROLE_" + role;
    }

    public String getRole() {
        return role;
    }
}
