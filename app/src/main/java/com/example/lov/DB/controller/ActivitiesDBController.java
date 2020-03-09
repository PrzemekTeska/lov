package com.example.lov.DB.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.lov.model.Activity;
import java.util.ArrayList;
import java.util.List;

public class ActivitiesDBController {

    private List<Activity> activityList;

    public boolean insertActivitiesIntoDataBaseImpl(SQLiteDatabase database, String activityName, String activityUnit, int activityPoints,int activityAmount, String userName, String goalNameFK,final String COLUMN_ACTIVITY_NAME,
                                                    final String COLUMN_ACTIVITY_UNIT,final String COLUMN_ACTIVITY_POINTS,final String COLUMN_ACTIVITY_AMOUNT ,final String COLUMN_ACTIVITY_USERNAME,final String COLUMN_ACTIVITY_GOAL_NAME ,final String ACTIVITY_TABLE_NAME) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ACTIVITY_NAME, activityName);
        contentValues.put(COLUMN_ACTIVITY_UNIT, activityUnit);
        contentValues.put(COLUMN_ACTIVITY_POINTS, activityPoints);
        contentValues.put(COLUMN_ACTIVITY_AMOUNT, activityAmount);
        contentValues.put(COLUMN_ACTIVITY_USERNAME, userName);
        contentValues.put(COLUMN_ACTIVITY_GOAL_NAME, goalNameFK);
        long ins = database.insert(ACTIVITY_TABLE_NAME, null, contentValues);
        return !(ins == -1);
    }

    public List<Activity> getAllActivities(SQLiteDatabase db,String username){
        activityList=new ArrayList<>();
        String query = "Select*FROM activities WHERE username LIKE '"+username+"'";
            Cursor cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                String result_0 = cursor.getString(0);
                String result_1 = cursor.getString(1);
                int result_2 = cursor.getInt(2);
                int result_21 = cursor.getInt(3);
                String result_3 = cursor.getString(5);
                Activity activity= new Activity(result_0,result_1,result_2,result_21,result_3);
                activityList.add(activity);
           }
            return activityList;
    }

    public boolean updateActivity(SQLiteDatabase database,Activity insertActivity,String activeUserName,String oldAN,String oldGN){
        try{
            Cursor cursor = database.rawQuery("UPDATE activities SET activity_name = ?,activity_unit =?,activity_points=?,activity_amount=?,goal_name_FK=?" +
                            " WHERE activity_name=? AND username =? AND goal_name_FK=?",
                    new String[]{insertActivity.getActivityName(),insertActivity.getActivityUnit(),String.valueOf(insertActivity.getActivityPoints()),
                            String.valueOf(insertActivity.getActivityAmount()),insertActivity.getGoalNameFK(),oldAN,activeUserName,oldGN});
            return !(cursor.getCount() > 0);
        }catch (Exception e){return false;}
    }


    public boolean deleteActivity(SQLiteDatabase database,Activity deleteActivity,String activeUserName){
        return database.delete("activities",  "activity_name=? AND username=? AND goal_name_FK=?", new String[]{deleteActivity.getActivityName(),
        activeUserName,deleteActivity.getGoalNameFK()}) > 0;
    }


    //public String loadHandler() {
    //    String result = "";
    //    String query = "Select*FROM" + TABLE_NAME;
    //   SQLiteDatabase db = this.getWritableDatabase();
    //   Cursor cursor = db.rawQuery(query, null);
    //    while (cursor.moveToNext()) {
    //        int result_0 = cursor.getInt(0);
    //        String result_1 = cursor.getString(1);
    //       result += String.valueOf(result_0) + " " + result_1 +
    //                System.getProperty("line.separator");
    //   }
    //    cursor.close();
    //    db.close();
    //     return result;
    //  }


}
