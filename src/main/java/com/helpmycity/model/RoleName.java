package com.helpmycity.model;

public enum RoleName {
    ROLE_USER("USER"),
    ROLE_AGENT("AGENT"),
    ROLE_ADMIN("ADMIN");

    private String value;

    RoleName(String n) {
        this.value = n;
    }

    public static RoleName getRole(String in) {

        switch (in) {
            case "USER":
                return ROLE_USER;
            case "AGENT":
                return ROLE_AGENT;
            case "ADMIN":
                return ROLE_ADMIN;

            default:
                return ROLE_USER;
        }
    }
}