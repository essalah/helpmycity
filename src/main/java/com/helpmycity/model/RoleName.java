package com.helpmycity.model;

public enum RoleName {
    ROLE_USER,
    ROLE_AGENT,
    ROLE_ADMIN;

    public static RoleName getRole(String in) {

        switch (in) {
            case "ROLE_USER":
                return ROLE_USER;
            case "ROLE_AGENT":
                return ROLE_AGENT;
            case "ROLE_ADMIN":
                return ROLE_ADMIN;

            default:
                return ROLE_USER;
        }
    }
}