package com.example.videostreamingapp;

public class user {
    String Id;
    String username;
    String email;
    public user() {
    }
    public user(String Id,String username,String email) {
        this.Id=Id;
        this.username=username;
        this.email=email;
    }
    public String getId() {
        return Id;
    }
    public String getUsername() {
        return this.username;
    }
    public String getEmail() {
        return this.email;
    }
}
