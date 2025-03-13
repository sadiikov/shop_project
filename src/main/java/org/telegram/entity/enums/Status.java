package org.telegram.entity.enums;

public enum Status {
    ACTIVE("Active"),
    BLOCKED("Blocked"),
    DELETED("Deleted");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}