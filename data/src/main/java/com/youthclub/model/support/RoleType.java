package com.youthclub.model.support;

/**
 * @author Frank
 */
public enum RoleType implements RestfulEnum {
    ALL("All"),
    ADMIN("Admin"),
    USER("User"),
    GUEST("Guest");

    private final String label;

    private RoleType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
