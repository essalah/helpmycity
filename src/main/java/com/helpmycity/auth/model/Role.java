package com.helpmycity.auth.model;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @Column(name="role_id")
    private int id;
    @Column(name="role")
    private String role;

    @PrePersist
    private void defaultValues(){
        id = 2;
        role = "User";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}