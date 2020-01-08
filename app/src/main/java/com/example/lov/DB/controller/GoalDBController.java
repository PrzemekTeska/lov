package com.example.lov.DB.controller;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class GoalDBController {

    public boolean insertGoalIntoDataBaseImpl(SQLiteDatabase database, String goalName, String goalDescription, String userName, final String COLUMN_GOAL_NAME,
                                              final String COLUMN_GOAL_DESCRIPTION, final String COLUMN_GOAL_USERNAME, final String GOAL_TABLE_NAME) {

       ContentValues contentValues = new ContentValues();
       contentValues.put(COLUMN_GOAL_NAME, goalName);
       contentValues.put(COLUMN_GOAL_DESCRIPTION, goalDescription);
       contentValues.put(COLUMN_GOAL_USERNAME, userName);
       long ins = database.insert(GOAL_TABLE_NAME, null, contentValues);
       return !(ins == -1);
    }


}
