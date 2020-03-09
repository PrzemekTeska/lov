package com.example.lov.model;

public class Prize {
    private long id;
    private String prizeName;
    private String goalName;
    private String userName;
    private String achieved;

    public Prize(long id, String prizeName,String achieved, String goalName, String userName) {
        this.id = id;
        this.prizeName = prizeName;
        this.achieved = achieved;
        this.goalName = goalName;
        this.userName = userName;
    }

    public Prize( String prizeName,String achieved, String goalName, String userName) {
        this.prizeName = prizeName;
        this.achieved = achieved;
        this.goalName = goalName;
        this.userName = userName;
    }

    public String getAchieved() {
        return achieved;
    }

    public void setAchieved(String achieved) {
        this.achieved = achieved;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
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
        return "Prize{" +
                "id=" + id +
                ", prizeName='" + prizeName + '\'' +
                ", goalName='" + goalName + '\'' +
                ", userName='" + userName + '\'' +
                ", achieved=" + achieved +
                '}';
    }
}
