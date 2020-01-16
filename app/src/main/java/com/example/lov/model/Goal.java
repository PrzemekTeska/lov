package com.example.lov.model;

import java.util.Date;

public class Goal {

    private String goalName;
    private Date startDate;
    private Date endDate;

    public Goal(String goalName, Date startDate, Date endDate, String activityName) {
        this.goalName = goalName;
        this.startDate = startDate;
        this.endDate = endDate;
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
