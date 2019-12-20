package com.example.lov;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(@Nullable Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create table user(user_name text PRIMARY KEY, email text ,password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists user");
    }

    public boolean insertIntoDataBase(String userName,String email,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_name",userName);
        contentValues.put("email",email);
        contentValues.put("password",password);
        long ins = db.insert("user",null, contentValues);
        if(ins==-1)return false;
        else {
            return true;
        }
    }

    public boolean checkUserName(String userName){
        SQLiteDatabase database =this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM user WHERE user_name=?",new String[]{userName});
        if(cursor.getCount()>0)return false;
        else {
            return true;
        }
    }

    public boolean checkEmail(String emailCheck){
        SQLiteDatabase database =this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM user WHERE email=?",new String[]{emailCheck});
        if(cursor.getCount()>0)return false;
        else {
            return true;
        }
    }

    public boolean checkPassword(String passwordCheck, String userName){
        SQLiteDatabase database =this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT password FROM user WHERE user_name = ? and password=?",new String[]{userName,passwordCheck});
        if(cursor.getCount()>0)return false;
        else {
            return true;
        }
    }
}
