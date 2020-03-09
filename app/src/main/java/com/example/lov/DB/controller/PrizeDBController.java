package com.example.lov.DB.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lov.model.Prize;

import java.util.ArrayList;
import java.util.List;

public class PrizeDBController {
    List<Prize> prizeList = new ArrayList<>();

    public boolean insertPrizeIntoDB(SQLiteDatabase database, String prizeName, String achieved, String prizeGoalName, String prizeUsername, final String COLUMN_PRIZE_NAME,
                                     final String COLUMN_PRIZE_ACHIEVED, final String COLUMN_PRIZE_GOAL_NAME, final String COLUMN_PRIZE_USERNAME, final String PRIZE_TABLE_NAME){

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PRIZE_NAME, prizeName);
        contentValues.put(COLUMN_PRIZE_ACHIEVED, achieved);
        contentValues.put(COLUMN_PRIZE_GOAL_NAME, prizeGoalName);
        contentValues.put(COLUMN_PRIZE_USERNAME, prizeUsername);
        long ins = database.insert(PRIZE_TABLE_NAME, null, contentValues);
        return !(ins == -1);
    }

    public List<Prize> getAllPrizes(SQLiteDatabase database,String activeUser){
        prizeList=new ArrayList<>();
        String query = "Select*FROM prizes WHERE username LIKE '"+activeUser+"'";
        Cursor cursor = database.rawQuery(query, null);
        while (cursor.moveToNext()) {
            long result_0 = cursor.getLong(0);
            String result_1 = cursor.getString(1);
            String result_2 = cursor.getString(2);
            String result_3 = cursor.getString(3);
            String result_4 = cursor.getString(4);
            Prize prize= new Prize(result_0, result_1, result_2,result_3,result_4);
            prizeList.add(prize);
        }
        return prizeList;
    }

    public boolean updatePrize(SQLiteDatabase database,Prize prize){
        try{
            Cursor cursor = database.rawQuery("UPDATE prizes SET prize_name = ?, prize_achieved =? WHERE id=?",
                    new String[]{prize.getPrizeName(),prize.getAchieved(),String.valueOf(prize.getId())});
            return !(cursor.getCount() > 0);

        }catch (Exception e){return false;}
    }

    public boolean deletePrize(SQLiteDatabase database,long id){
        return database.delete("prizes",  "id=?", new String[]{String.valueOf(id)}) > 0;

    }

}
