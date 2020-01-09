package com.example.lov.model;

public class User {
    private String userName;
    private String email;
    private String password;
    private String avatar;
    private int points;

    public User(String userName, String email, String password, String avatar, int points) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.points = points;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
