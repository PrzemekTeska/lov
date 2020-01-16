package com.example.lov.DB.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lov.model.Goal;
import com.example.lov.service.DateStringConverter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class GoalDBController {
    private List<Goal> goalList;
    private DateStringConverter dateStringConverter=new DateStringConverter();

    public boolean insertGoalIntoDataBaseImpl(SQLiteDatabase database, String goalName, String startDate, String endDate,String userName, final String COLUMN_GOAL_NAME,
                                              final String COLUMN_GOAL_START_DATE,final String COLUMN_GOAL_END_DATE, final String COLUMN_GOAL_USERNAME, final String GOAL_TABLE_NAME) {

       ContentValues contentValues = new ContentValues();
       contentValues.put(COLUMN_GOAL_NAME, goalName);
       contentValues.put(COLUMN_GOAL_START_DATE, startDate);
       contentValues.put(COLUMN_GOAL_END_DATE, endDate);
        contentValues.put(COLUMN_GOAL_USERNAME, userName);
       long ins = database.insert(GOAL_TABLE_NAME, null, contentValues);
       return !(ins == -1);
    }

    public List<Goal> getAllGoals(SQLiteDatabase db, String username)throws ParseException {
        goalList=new ArrayList<>();
        String result = "";
        String query = "Select*FROM goals WHERE username LIKE '"+username+"'";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String result_0 = cursor.getString(0);
            String result_1 = cursor.getString(1);
            String result_2 = cursor.getString(2);
            String result_3 = cursor.getString(3);
            Goal goal= new Goal(result_0, dateStringConverter.getDate(result_1), dateStringConverter.getDate(result_2),result_3);
            goalList.add(goal);
        }
        return goalList;
    }

}
