package com.example.lov.model;

import java.util.Date;

public class Goal {

    private String goalName;
    private Date startDate;
    private Date endDate;
    private String userName;

    public Goal(String goalName, Date startDate, Date endDate, String userName) {
        this.goalName = goalName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userName=userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
