package com.example.lov.DB.controller;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class ActivitiesDBController {

    public boolean insertActivitiesIntoDataBaseImpl(SQLiteDatabase database, String activityName, String activityDescription, String userName, final String COLUMN_ACTIVITY_NAME,
                                                    final String COLUMN_ACTIVITY_DESCRIPTION, final String COLUMN_ACTIVITY_USERNAME, final String ACTIVITY_TABLE_NAME) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ACTIVITY_NAME, activityName);
        contentValues.put(COLUMN_ACTIVITY_DESCRIPTION, activityDescription);
        contentValues.put(COLUMN_ACTIVITY_USERNAME, userName);
        long ins = database.insert(ACTIVITY_TABLE_NAME, null, contentValues);
        return !(ins == -1);
    }
}
