package com.example.myfinalproject.models;

import java.util.ArrayList;

public class User {

    private String UserId;
    private String Name;
    private String email;
    private String Password;

    public User(String userId, String name, String email, String password) {
        UserId = userId;
        Name = name;
        this.email = email;
        Password = password;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
