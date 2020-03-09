package com.example.lov.model;

public class Penalty {

    private long id;
    private String penaltyName;
    private String goalName;
    private String userName;
    private String active;

    public Penalty(long id, String penaltyName, String active, String goalName, String userName) {
        this.id = id;
        this.penaltyName = penaltyName;
        this.active=active;
        this.goalName = goalName;
        this.userName = userName;
    }

    public Penalty(String penaltyName,String active, String goalName, String userName) {
        this.penaltyName = penaltyName;
        this.active=active;
        this.goalName = goalName;
        this.userName = userName;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPenaltyName() {
        return penaltyName;
    }

    public void setPenaltyName(String penaltyName) {
        this.penaltyName = penaltyName;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Penalty{" +
                "id=" + id +
                ", penaltyName='" + penaltyName + '\'' +
                ", goalName='" + goalName + '\'' +
                ", userName='" + userName + '\'' +
                ", active='" + active + '\'' +
                '}';
    }
}
