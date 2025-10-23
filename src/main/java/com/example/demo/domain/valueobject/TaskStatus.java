package com.example.demo.domain.valueobject;

public enum TaskStatus {
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");
    
    private final String displayName;
    
    TaskStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public boolean isCompleted() {
        return this == COMPLETED;
    }
    
    public boolean isActive() {
        return this == PENDING || this == IN_PROGRESS;
    }
}
