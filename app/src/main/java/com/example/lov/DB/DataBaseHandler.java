package com.example.lov.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lov.DB.controller.ActivitiesDBController;
import com.example.lov.DB.controller.GoalDBController;
import com.example.lov.DB.controller.UserDBController;
import com.example.lov.model.Activity;
import com.example.lov.model.Goal;
import com.example.lov.service.DateStringConverter;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public class DataBaseHandler extends SQLiteOpenHelper {
    private GoalDBController goalDBController = new GoalDBController();
    private UserDBController userDBController = new UserDBController();
    private ActivitiesDBController activitiesDBController = new ActivitiesDBController();
    private DateStringConverter dateStringConverter = new DateStringConverter();

    private SQLiteDatabase database;

    private static final String DBName = "LovDB.db";

    private static final String USER_TABLE_NAME = "user";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_USER_AVATAR_PATH = "avatar_path";
    private static final String COLUMN_USER_POINTS = "user_points";

    private static final String ACTIVITY_TABLE_NAME = "activities";
    private static final String COLUMN_ACTIVITY_NAME = "activity_name";
    private static final String COLUMN_ACTIVITY_UNIT = "activity_unit";
    private static final String COLUMN_ACTIVITY_POINTS = "activity_points";
    private static final String COLUMN_ACTIVITY_USERNAME = "username";
    private static final String COLUMN_ACTIVITY_GOAL_NAME = "goal_name_FK";

    private static final String GOAL_TABLE_NAME = "goals";
    private static final String COLUMN_GOAL_NAME = "goal_name";
    private static final String COLUMN_GOAL_START_DATE = "goal_start_date";
    private static final String COLUMN_GOAL_END_DATE= "goal_end_date";
    private static final String COLUMN_GOAL_USERNAME = "username";

    public DataBaseHandler(Context context) {
        super(context, DBName, null, 1);
        this.database = this.getWritableDatabase();
        getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + USER_TABLE_NAME + "(" + COLUMN_USER_NAME + " TEXT PRIMARY KEY," + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT,"
                +COLUMN_USER_AVATAR_PATH+" TEXT,"+COLUMN_USER_POINTS+" INT)");

        db.execSQL("CREATE TABLE " + ACTIVITY_TABLE_NAME + "(" + COLUMN_ACTIVITY_NAME + " TEXT PRIMARY KEY," + COLUMN_ACTIVITY_UNIT + " TEXT," + COLUMN_ACTIVITY_POINTS + " INT," + COLUMN_ACTIVITY_USERNAME + " TEXT,"
                + COLUMN_ACTIVITY_GOAL_NAME+ " TEXT,"
                + " FOREIGN KEY(" + COLUMN_ACTIVITY_USERNAME + ")REFERENCES " + USER_TABLE_NAME + "(" + COLUMN_USER_NAME + "),"
                + " FOREIGN KEY(" + COLUMN_ACTIVITY_GOAL_NAME + ")REFERENCES " + GOAL_TABLE_NAME + "(" + COLUMN_GOAL_USERNAME + "));");

        db.execSQL("CREATE TABLE " + GOAL_TABLE_NAME + "(" + COLUMN_GOAL_NAME + " TEXT PRIMARY KEY," + COLUMN_GOAL_START_DATE + " TEXT," + COLUMN_GOAL_END_DATE + " TEXT," + COLUMN_GOAL_USERNAME + " TEXT,"
                + " FOREIGN KEY(" + COLUMN_GOAL_USERNAME + ")REFERENCES " + USER_TABLE_NAME + "(" + COLUMN_USER_NAME + "));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists user");
        sqLiteDatabase.execSQL("drop table if exists activities");
        sqLiteDatabase.execSQL("drop table if exists goals");
    }


    public boolean insertUserIntoDataBase(String userName, String email, String password, String avatarPath, int userPoints) {
        return userDBController.insertUserIntoDataBaseImpl(database, userName, email, password, avatarPath,userPoints,COLUMN_USER_NAME, COLUMN_USER_EMAIL, COLUMN_USER_PASSWORD,
                COLUMN_USER_AVATAR_PATH,COLUMN_USER_POINTS,USER_TABLE_NAME);
    }

    public boolean insertActivityIntoDataBase(String activityName, String activityUnit, int activityPoints,String userSelectedGoal) throws ParseException{
        return activitiesDBController.insertActivitiesIntoDataBaseImpl(database, activityName, activityUnit, activityPoints, userDBController.getActiveUserName(),
                getSingleGoalName(database,userDBController.getActiveUserName(),userSelectedGoal),
                COLUMN_ACTIVITY_NAME, COLUMN_ACTIVITY_UNIT, COLUMN_ACTIVITY_POINTS,COLUMN_ACTIVITY_USERNAME, COLUMN_ACTIVITY_GOAL_NAME ,ACTIVITY_TABLE_NAME);
    }

    public boolean insertGoalIntoDataBase(String goalName, Date startDate, Date endDate ) {
         return goalDBController.insertGoalIntoDataBaseImpl(database, goalName, dateStringConverter.getString(startDate), dateStringConverter.getString(endDate),
                 userDBController.getActiveUserName(), COLUMN_GOAL_NAME, COLUMN_GOAL_START_DATE,COLUMN_GOAL_END_DATE, COLUMN_GOAL_USERNAME,GOAL_TABLE_NAME);
      }

      private String getSingleGoalName(SQLiteDatabase dataBase,String user,String userSelectedGoal)throws ParseException {
       Optional<Goal> first= goalDBController.getAllGoals(dataBase,user).stream().filter(element->element.getGoalName().equals(userSelectedGoal)).findFirst();
        return first.get().getGoalName();
      }

      public List<Activity> getAllActivities() {
          return activitiesDBController.getAllActivities(database, userDBController.getActiveUserName());
      }

    public boolean checkUserName(String userName) {
        return userDBController.checkUserNameImpl(database, userName);
    }

    public boolean checkEmail(String emailCheck) {
        return userDBController.checkEmailImpl(database, emailCheck);
    }

    public boolean checkPassword(String passwordCheck, String userName) {
        return userDBController.checkPasswordImpl(database, passwordCheck, userName);
    }

    public void goToMain(){

    }

}
