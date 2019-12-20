package com.example.lov;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DBName = "Login.db";
    private static final String COLUMN_name = "user_name";
    private static final String COLUMN_mail = "email";
    private static final String COLUMN_pass = "password";
    private static final String TABLE_NAME = "user";

    public DataBaseHelper(Context context) {
        super(context, DBName, null, 1);
        getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + COLUMN_name + " TEXT PRIMARY KEY," + COLUMN_mail + " TEXT," + COLUMN_pass + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists user");
    }

    public boolean insertIntoDataBase(String userName, String email, String password) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_name, userName);
        contentValues.put(COLUMN_mail, email);
        contentValues.put(COLUMN_pass, password);
        long ins = database.insert(TABLE_NAME, null, contentValues);
        if (ins == -1) return false;
        else {
            return true;
        }
    }

    public boolean checkUserName(String userName) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM user WHERE user_name=?", new String[]{userName});
        if (cursor.getCount() > 0) return false;
        else {
            return true;
        }
    }

    public boolean passwordCheck(String password) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM user WHERE password=?", new String[]{password});
        if (cursor.getCount() > 0) return false;
        else {
            return true;
        }
    }

    public boolean checkEmail(String emailCheck) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM user WHERE email=?", new String[]{emailCheck});
        if (cursor.getCount() > 0) return false;
        else {
            return true;
        }
    }

    public boolean checkPassword(String passwordCheck, String userName) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT password FROM user WHERE user_name = ? and password=?", new String[]{userName, passwordCheck});
        if (cursor.getCount() <= 0) return false;
        else {
            return true;
        }
    }
}
