package com.example.lov.model;

public class Activity {

    private String activityName;
    private String activityUnit;
    private String goalNameFK;
    private int activityPoints;
    private int activityAmount;

    public Activity(String activityName, String activityUnit, int activityPoints,int activityAmount ,String goalNameFK) {
        this.activityName = activityName;
        this.activityUnit = activityUnit;
        this.activityPoints = activityPoints;
        this.activityAmount=activityAmount;
        this.goalNameFK = goalNameFK;
    }

    public int getActivityAmount() {
        return activityAmount;
    }

    public void setActivityAmount(int activityAmount) {
        this.activityAmount = activityAmount;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityUnit() {
        return activityUnit;
    }

    public void setActivityUnit(String activityUnit) {
        this.activityUnit = activityUnit;
    }

    public String getGoalNameFK() {
        return goalNameFK;
    }

    public void setGoalNameFK(String goalNameFK) {
        this.goalNameFK = goalNameFK;
    }

    public int getActivityPoints() {
        return activityPoints;
    }

    public void setActivityPoints(int activityPoints) {
        this.activityPoints = activityPoints;
    }
}
