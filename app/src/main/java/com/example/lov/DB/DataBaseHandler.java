package com.example.lov.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lov.DB.controller.ActivitiesDBController;
import com.example.lov.DB.controller.GoalDBController;
import com.example.lov.DB.controller.UserDBController;


public class DataBaseHandler extends SQLiteOpenHelper {
    private GoalDBController goalDBController=new GoalDBController();
    private UserDBController userDBController=new UserDBController();
    private ActivitiesDBController activitiesDBController= new ActivitiesDBController();

    private static final String DBName = "LovDB.db";
    private static final String USER_TABLE_NAME = "user";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";

    private static final String ACTIVITY_TABLE_NAME = "activities";
    private static final String COLUMN_ACTIVITY_NAME = "activity_name";
    private static final String COLUMN_ACTIVITY_DESCRIPTION = "activity_description";
    private static final String COLUMN_ACTIVITY_USERNAME = "username";

    private static final String GOAL_TABLE_NAME = "goals";
    private static final String COLUMN_GOAL_NAME = "goal_name";
    private static final String COLUMN_GOAL_DESCRIPTION = "goal_description";
    private static final String COLUMN_GOAL_USERNAME = "username";

    public DataBaseHandler(Context context) {
        super(context, DBName, null, 1);
        getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + USER_TABLE_NAME + "(" + COLUMN_USER_NAME + " TEXT PRIMARY KEY," + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT)");
        db.execSQL("CREATE TABLE " + ACTIVITY_TABLE_NAME + "(" + COLUMN_ACTIVITY_NAME + " TEXT PRIMARY KEY," + COLUMN_ACTIVITY_DESCRIPTION + " TEXT," + COLUMN_ACTIVITY_USERNAME + " TEXT,"
                +" FOREIGN KEY("+COLUMN_ACTIVITY_USERNAME+")REFERENCES "+USER_TABLE_NAME+"("+COLUMN_USER_NAME+"));");
        db.execSQL("CREATE TABLE " + GOAL_TABLE_NAME + "(" + COLUMN_GOAL_NAME + " TEXT PRIMARY KEY," + COLUMN_GOAL_DESCRIPTION + " TEXT," + COLUMN_GOAL_USERNAME + " TEXT,"
                +" FOREIGN KEY("+COLUMN_GOAL_USERNAME+")REFERENCES "+USER_TABLE_NAME+"("+COLUMN_USER_NAME+"));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists user");
        sqLiteDatabase.execSQL("drop table if exists activities");
        sqLiteDatabase.execSQL("drop table if exists goals");
    }



    public boolean insertUserIntoDataBase(String userName, String email, String password) {
        SQLiteDatabase database = this.getWritableDatabase();
        return userDBController.insertUserIntoDataBaseImpl(database,userName,email,password,COLUMN_USER_NAME,COLUMN_USER_EMAIL,COLUMN_USER_PASSWORD,USER_TABLE_NAME);
    }

    public boolean insertGoalIntoDataBase(String goalName, String goalDescription) {
        SQLiteDatabase database = this.getWritableDatabase();
        return goalDBController.insertGoalIntoDataBaseImpl(database,goalName,goalDescription,userDBController.getUser(),COLUMN_GOAL_NAME,
                COLUMN_GOAL_DESCRIPTION,COLUMN_GOAL_USERNAME,GOAL_TABLE_NAME);
    }

    public boolean insertActivityIntoDataBase(String activityName, String activityDescription) {
        SQLiteDatabase database = this.getWritableDatabase();
        return activitiesDBController.insertActivitiesIntoDataBaseImpl(database,activityName,activityDescription,userDBController.getUser()
                ,COLUMN_ACTIVITY_NAME,COLUMN_ACTIVITY_DESCRIPTION,COLUMN_ACTIVITY_USERNAME,ACTIVITY_TABLE_NAME);
    }


    public boolean checkUserName(String userName) {
        SQLiteDatabase database = this.getReadableDatabase();
        return userDBController.checkUserNameImpl(database,userName);

    }

    public boolean checkEmail(String emailCheck) {
        SQLiteDatabase database = this.getReadableDatabase();
        return userDBController.checkEmailImpl(database,emailCheck);
    }

    public boolean checkPassword(String passwordCheck, String userName) {
        SQLiteDatabase database = this.getReadableDatabase();
        return userDBController.checkPasswordImpl(database,passwordCheck,userName);
    }



}
