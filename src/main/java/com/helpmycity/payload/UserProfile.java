package com.helpmycity.payload;

public class UserProfile {
    private Long id;
    private String username;
    private String name;
    private  String lastNname;
    private String email;
    private String accessToken;
    private String tokenType = "Bearer";

    public UserProfile(Long id, String email, String username, String name, String lastNname, String accessToken) {

        this.id = id;
        this.email = email;
        this.lastNname = lastNname;
        this.username = username;
        this.name = name;
        this.accessToken = accessToken;
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

    public String getLastNname() {
        return lastNname;
    }

    public void setLastNname(String lastNname) {
        this.lastNname = lastNname;
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
}