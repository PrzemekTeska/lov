package com.example.lov.DB.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lov.model.Penalty;

import java.util.ArrayList;
import java.util.List;

public class PenaltyDBController {
    List<Penalty> penaltyList;

    public boolean insertPenaltyIntoDB(SQLiteDatabase database, String penaltyName, String active, String penaltyGoalName, String penaltyUsername, final String COLUMN_PENALTY_NAME,
                                       final String COLUMN_PENALTY_ACTIVE, final String COLUMN_PENALTY_GOAL_NAME, final String COLUMN_PENALTY_USERNAME, final String PENALTY_TABLE_NAME){

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PENALTY_NAME, penaltyName);
        contentValues.put(COLUMN_PENALTY_ACTIVE, active);
        contentValues.put(COLUMN_PENALTY_GOAL_NAME, penaltyGoalName);
        contentValues.put(COLUMN_PENALTY_USERNAME, penaltyUsername);
        long ins = database.insert(PENALTY_TABLE_NAME, null, contentValues);
        return !(ins == -1);
    }

    public List<Penalty> getAllPenalties(SQLiteDatabase database,String activeUser){
        penaltyList=new ArrayList<>();
        String query = "Select*FROM penalties WHERE username LIKE '"+activeUser+"'";
        Cursor cursor = database.rawQuery(query, null);
        while (cursor.moveToNext()) {
            long result_0 = cursor.getLong(0);
            String result_1 = cursor.getString(1);
            String result_2 = cursor.getString(2);
            String result_3 = cursor.getString(3);
            String result_4 = cursor.getString(4);
            Penalty penalty= new Penalty(result_0, result_1, result_2,result_3,result_4);
            penaltyList.add(penalty);
        }
        return penaltyList;
    }

    public boolean updatePenalty(SQLiteDatabase database,Penalty penalty){
        try{
            Cursor cursor = database.rawQuery("UPDATE penalties SET penalty_name = ?, penalty_active =? WHERE id=?",
                    new String[]{penalty.getPenaltyName(),penalty.getActive(),String.valueOf(penalty.getId())});
            return !(cursor.getCount() > 0);

        }catch (Exception e){return false;}
    }
    public boolean deletePenalty(SQLiteDatabase database,long id){
        return database.delete("penalties",  "id=?", new String[]{String.valueOf(id)}) > 0;

    }
}
