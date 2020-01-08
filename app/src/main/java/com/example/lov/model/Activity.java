package com.example.lov.model;

public class Activity {

    private String activityName;
    private String activityDescription;

    public Activity(String activityName, String activityDescription) {
        this.activityName = activityName;
        this.activityDescription = activityDescription;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "activityName='" + activityName + '\'' +
                ", activityDescription='" + activityDescription + '\'' +
                '}';
    }
}
