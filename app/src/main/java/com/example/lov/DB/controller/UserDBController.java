package com.example.lov.DB.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.lov.model.User;


public class UserDBController{

    private User user;

    public boolean insertUserIntoDataBaseImpl(SQLiteDatabase database, String userName ,String email, String password,String avatarPath,int points,
                                              final String COLUMN_USER_NAME, final String COLUMN_USER_EMAIL, final String COLUMN_USER_PASSWORD,
                                              final String COLUMN_USER_AVATAR_PATH, final String COLUMN_USER_POINTS,final String USER_TABLE_NAME) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME, userName);
        contentValues.put(COLUMN_USER_EMAIL, email);
        contentValues.put(COLUMN_USER_PASSWORD, password);
        contentValues.put(COLUMN_USER_AVATAR_PATH, avatarPath);
        contentValues.put(COLUMN_USER_POINTS, points);
        long ins = database.insert(USER_TABLE_NAME, null, contentValues);
        return !(ins == -1);
    }

    public boolean checkUserNameImpl(SQLiteDatabase database,String userName) {
        Cursor cursor = database.rawQuery("SELECT * FROM user WHERE user_name=?", new String[]{userName});
        return !(cursor.getCount() > 0);
    }

    public boolean checkEmailImpl(SQLiteDatabase database,String emailCheck) {
        Cursor cursor = database.rawQuery("SELECT * FROM user WHERE email=?", new String[]{emailCheck});
        return !(cursor.getCount() > 0);
    }

    public boolean checkPasswordImpl(SQLiteDatabase database,String passwordCheck, String userName) {
        Cursor cursor = database.rawQuery("SELECT * FROM user WHERE user_name = ? and password=?", new String[]{userName, passwordCheck});
        if(cursor.getCount() >= 0){
            while (cursor.moveToNext()) {
                String result_0 = cursor.getString(0);
                String result_1 = cursor.getString(1);
                String result_2 = cursor.getString(2);
                String result_3 = cursor.getString(3);
                int result_4 = cursor.getInt(4);
                this.user = new User(result_0, result_1, result_2, result_3, result_4);
                return true;
            }
        }
        return false;

    }

    public String getActiveUserName(){
        return user.getUserName();
    }
}
