package com.example.demo.domain.valueobject;

public enum UserRole {
    USER("User"),
    MANAGER("Manager"),
    ADMIN("Administrator");
    
    private final String displayName;
    
    UserRole(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
}
