package com.example.lov.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lov.DB.controller.ActivitiesDBController;
import com.example.lov.DB.controller.GoalDBController;
import com.example.lov.DB.controller.PenaltyDBController;
import com.example.lov.DB.controller.PrizeDBController;
import com.example.lov.DB.controller.UserDBController;
import com.example.lov.model.Activity;
import com.example.lov.model.Goal;
import com.example.lov.model.Penalty;
import com.example.lov.model.Prize;
import com.example.lov.model.User;
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
    private PenaltyDBController penaltyDBController=new PenaltyDBController();
    private PrizeDBController prizeDBController=new PrizeDBController();

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
    private static final String COLUMN_ACTIVITY_AMOUNT = "activity_amount";
    private static final String COLUMN_ACTIVITY_USERNAME = "username";
    private static final String COLUMN_ACTIVITY_GOAL_NAME = "goal_name_FK";

    private static final String GOAL_TABLE_NAME = "goals";
    private static final String COLUMN_GOAL_NAME = "goal_name";
    private static final String COLUMN_GOAL_START_DATE = "goal_start_date";
    private static final String COLUMN_GOAL_END_DATE= "goal_end_date";
    private static final String COLUMN_GOAL_USERNAME = "username";

    private static final String PENALTY_TABLE_NAME = "penalties";
    private static final String COLUMN_PENALTY_ID = "id";
    private static final String COLUMN_PENALTY_NAME = "penalty_name";
    private static final String COLUMN_PENALTY_ACTIVE = "penalty_active";
    private static final String COLUMN_PENALTY_GOAL_NAME= "penalty_goal_name";
    private static final String COLUMN_PENALTY_USERNAME = "username";

    private static final String PRIZE_TABLE_NAME = "prizes";
    private static final String COLUMN_PRIZE_ID = "id";
    private static final String COLUMN_PRIZE_NAME = "prize_name";
    private static final String COLUMN_PRIZE_ACHIEVED = "prize_achieved";
    private static final String COLUMN_PRIZE_GOAL_NAME= "pize_goal_name";
    private static final String COLUMN_PRIZE_USERNAME = "username";

    public DataBaseHandler(Context context) {
        super(context, DBName, null, 1);
        this.database = this.getWritableDatabase();
        getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + USER_TABLE_NAME + "(" + COLUMN_USER_NAME + " TEXT," + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT,"
                +COLUMN_USER_AVATAR_PATH+" TEXT,"+COLUMN_USER_POINTS+" INT)");

        db.execSQL("CREATE TABLE " + ACTIVITY_TABLE_NAME + "(" + COLUMN_ACTIVITY_NAME + " TEXT," + COLUMN_ACTIVITY_UNIT + " TEXT," + COLUMN_ACTIVITY_POINTS + " INT," + COLUMN_ACTIVITY_AMOUNT + " INT,"+ COLUMN_ACTIVITY_USERNAME + " TEXT,"
                + COLUMN_ACTIVITY_GOAL_NAME+ " TEXT, PRIMARY KEY("+COLUMN_ACTIVITY_NAME+","+COLUMN_ACTIVITY_GOAL_NAME+"),"
                + " FOREIGN KEY(" + COLUMN_ACTIVITY_USERNAME + ")REFERENCES " + USER_TABLE_NAME + "(" + COLUMN_USER_NAME + "),"
                + " FOREIGN KEY(" + COLUMN_ACTIVITY_GOAL_NAME + ")REFERENCES " + GOAL_TABLE_NAME + "(" + COLUMN_GOAL_USERNAME + "));");

        db.execSQL("CREATE TABLE " + GOAL_TABLE_NAME + "(" + COLUMN_GOAL_NAME + " TEXT," + COLUMN_GOAL_START_DATE + " TEXT," + COLUMN_GOAL_END_DATE + " TEXT," + COLUMN_GOAL_USERNAME + " TEXT, " +
                "PRIMARY KEY("+COLUMN_GOAL_NAME+","+COLUMN_GOAL_USERNAME+"),"
                + " FOREIGN KEY(" + COLUMN_GOAL_USERNAME + ")REFERENCES " + USER_TABLE_NAME + "(" + COLUMN_USER_NAME + "));");

        db.execSQL("CREATE TABLE " + PENALTY_TABLE_NAME + "("+ COLUMN_PENALTY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + COLUMN_PENALTY_NAME + " TEXT,"+ COLUMN_PENALTY_ACTIVE + " TEXT," + COLUMN_PENALTY_GOAL_NAME + " TEXT,"
                + COLUMN_PENALTY_USERNAME + " TEXT"+","
                + " FOREIGN KEY(" + COLUMN_PENALTY_USERNAME + ")REFERENCES " + USER_TABLE_NAME + "(" + COLUMN_USER_NAME + "),"
                + " FOREIGN KEY(" + COLUMN_PENALTY_GOAL_NAME + ")REFERENCES " + GOAL_TABLE_NAME + "(" + COLUMN_GOAL_USERNAME + "));");

        db.execSQL("CREATE TABLE " + PRIZE_TABLE_NAME + "("+ COLUMN_PRIZE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + COLUMN_PRIZE_NAME + " TEXT,"+ COLUMN_PRIZE_ACHIEVED + " TEXT," + COLUMN_PRIZE_GOAL_NAME + " TEXT,"
                + COLUMN_PRIZE_USERNAME + " TEXT"+","
                + " FOREIGN KEY(" + COLUMN_PRIZE_USERNAME + ")REFERENCES " + USER_TABLE_NAME + "(" + COLUMN_USER_NAME + "),"
                + " FOREIGN KEY(" + COLUMN_PRIZE_GOAL_NAME + ")REFERENCES " + GOAL_TABLE_NAME + "(" + COLUMN_GOAL_USERNAME + "));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists user");
        sqLiteDatabase.execSQL("drop table if exists activities");
        sqLiteDatabase.execSQL("drop table if exists goals");
        sqLiteDatabase.execSQL("drop table if exists penalties");
        sqLiteDatabase.execSQL("drop table if exists prizes");
    }


    public boolean insertPenaltyIntoDataBase(String penaltyName, String active,String penaltyGoalName){
        return penaltyDBController.insertPenaltyIntoDB(database,penaltyName,active,penaltyGoalName,userDBController.getActiveUserName(database),COLUMN_PENALTY_NAME,COLUMN_PENALTY_ACTIVE,
                COLUMN_PENALTY_GOAL_NAME, COLUMN_PENALTY_USERNAME, PENALTY_TABLE_NAME);
    }
    public boolean insertPrizeIntoDataBase(String prizeName, String achived,String prizeGoalName){
        return prizeDBController.insertPrizeIntoDB( database,prizeName,  achived, prizeGoalName,  userDBController.getActiveUserName(database), COLUMN_PRIZE_NAME,
         COLUMN_PRIZE_ACHIEVED, COLUMN_PRIZE_GOAL_NAME, COLUMN_PRIZE_USERNAME,PRIZE_TABLE_NAME);
    }

    public boolean insertUserIntoDataBase(String userName, String email, String password, String avatarPath, int userPoints) {
        return userDBController.insertUserIntoDataBaseImpl(database, userName, email, password, avatarPath,userPoints,COLUMN_USER_NAME, COLUMN_USER_EMAIL, COLUMN_USER_PASSWORD,
                COLUMN_USER_AVATAR_PATH,COLUMN_USER_POINTS,USER_TABLE_NAME);
    }

    public boolean insertActivityIntoDataBase(String activityName, String activityUnit, int activityPoints,int activityAmount ,String userSelectedGoal) throws ParseException{
        return activitiesDBController.insertActivitiesIntoDataBaseImpl(database, activityName, activityUnit, activityPoints, activityAmount,userDBController.getActiveUserName(database),
                getSingleGoalName(database,userDBController.getActiveUserName(database),userSelectedGoal),
                COLUMN_ACTIVITY_NAME, COLUMN_ACTIVITY_UNIT, COLUMN_ACTIVITY_POINTS,COLUMN_ACTIVITY_AMOUNT,COLUMN_ACTIVITY_USERNAME, COLUMN_ACTIVITY_GOAL_NAME ,ACTIVITY_TABLE_NAME);
    }

    public boolean insertGoalIntoDataBase(String goalName, Date startDate, Date endDate ) {
         return goalDBController.insertGoalIntoDataBaseImpl(database, goalName, dateStringConverter.getString(startDate), dateStringConverter.getString(endDate),
                 userDBController.getActiveUserName(database), COLUMN_GOAL_NAME, COLUMN_GOAL_START_DATE,COLUMN_GOAL_END_DATE, COLUMN_GOAL_USERNAME,GOAL_TABLE_NAME);
      }

      private String getSingleGoalName(SQLiteDatabase dataBase,String user,String userSelectedGoal)throws ParseException {
       Optional<Goal> first= goalDBController.getAllGoals(dataBase,user).stream().filter(element->element.getGoalName().equals(userSelectedGoal)).findFirst();
        return first.get().getGoalName();
      }

      public List<Penalty>getAllPenalties(){
        return penaltyDBController.getAllPenalties(database,userDBController.getActiveUserName(database));
      }

      public List<Prize>getAllPrizes(){
        return prizeDBController.getAllPrizes(database,userDBController.getActiveUserName(database));
      }

      public List<Activity> getAllActivities() {
          return activitiesDBController.getAllActivities(database, userDBController.getActiveUserName(database));
      }

    public List<Goal> getAllGoals() throws ParseException{
        return goalDBController.getAllGoals(database, userDBController.getActiveUserName(database));
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

    public User getActiveUser(){
        String userName = userDBController.getActiveUserName(database);
        return userDBController.getActiveUser(database,userName);
    }

    public void createActiveUserTable(String userName){
        database.execSQL("DROP TABLE if EXISTS active_user");
        database.execSQL("CREATE TABLE active_user(active_user_name TEXT PRIMARY KEY)");
        ContentValues contentValues = new ContentValues();
        contentValues.put("active_user_name", userName);
        database.insert("active_user", null, contentValues);
    }
    public boolean updateUser(User insertUser){
        if(userDBController.updateUser(database,insertUser))return true;
        return false;
    }

    public boolean updateGoal(Goal insertGoal){
        if(goalDBController.updateGoal(database,insertGoal))return true;
        return false;
    }
    public boolean updateActivity(Activity insertActivity, String oldAN, String oldGN){
        if(activitiesDBController.updateActivity(database,insertActivity,getActiveUser().getUserName(),oldAN,oldGN))return true;
        return false;
    }

    public boolean updatePrize(Prize prize){
        if(prizeDBController.updatePrize(database,prize))return true;
        return false;
    }
    public boolean updatePenalty(Penalty penalty){
        if(penaltyDBController.updatePenalty(database,penalty))return true;
        return false;
    }

    public boolean deleteActivity(Activity deleteActivity){
        if(activitiesDBController.deleteActivity(database,deleteActivity,getActiveUser().getUserName()))return true;
        return false;
    }

    public boolean deletePrize(long id){
        if(prizeDBController.deletePrize(database,id))return true;
        return false;
    }

    public boolean deletePenalty(long id){
        if(penaltyDBController.deletePenalty(database,id))return true;
        return false;

    }

}
