package com.example.lov.model;

public class Goal {

    private String goalName;
    private String goalDescription;

    public Goal(String goalName, String goalDescription) {
        this.goalName = goalName;
        this.goalDescription = goalDescription;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public String getGoalDescription() {
        return goalDescription;
    }

    public void setGoalDescription(String goalDescription) {
        this.goalDescription = goalDescription;
    }

    @Override
    public String toString() {
        return "Goal{" +
                "goalName='" + goalName + '\'' +
                ", goalDescription='" + goalDescription + '\'' +
                '}';
    }
}
