package com.helpmycity.payload;

import com.helpmycity.model.Role;

import java.util.HashSet;
import java.util.Set;

public class UserProfile {
    private Long id;
    private String username;
    private String name;
    private String lastName;
    private String email;
    private String accessToken;
    private String tokenType = "Bearer";
    private Set<Role> roles = new HashSet<>();

    public UserProfile(Long id, String email, String username, String name, String lastName, String accessToken) {

        this.id = id;
        this.email = email;
        this.lastName = lastName;
        this.username = username;
        this.name = name;
        this.accessToken = accessToken;
    }

    public UserProfile(Long id, String email, String username, String name, String lastName, String accessToken, Set<Role> roles) {

        this.id = id;
        this.email = email;
        this.lastName = lastName;
        this.username = username;
        this.name = name;
        this.accessToken = accessToken;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}