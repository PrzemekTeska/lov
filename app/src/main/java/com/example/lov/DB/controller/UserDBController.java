package com.example.lov.DB.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDBController{

    private String user;

    public boolean insertUserIntoDataBaseImpl(SQLiteDatabase database, String userName, String email, String password, final String COLUMN_USER_NAME,
                                              final String COLUMN_USER_EMAIL, final String COLUMN_USER_PASSWORD, final String USER_TABLE_NAME) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME, userName);
        contentValues.put(COLUMN_USER_EMAIL, email);
        contentValues.put(COLUMN_USER_PASSWORD, password);
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
        Cursor cursor = database.rawQuery("SELECT password FROM user WHERE user_name = ? and password=?", new String[]{userName, passwordCheck});
        setUser(userName);
        return !(cursor.getCount() <= 0);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
