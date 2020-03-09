package com.example.lov.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.os.Build;

import com.example.lov.DB.DataBaseHandler;
import com.example.lov.R;
import com.example.lov.model.Goal;
import com.example.lov.model.Penalty;
import com.example.lov.model.Prize;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static java.time.temporal.ChronoUnit.DAYS;

public class NotificationJobService extends JobService {

    private NotificationManagerCompat notificationManager;
    private DataBaseHandler dataBaseHandler;
    private boolean cancel;

    public NotificationJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        dataBaseHandler = new DataBaseHandler(getApplicationContext());
        if (cancel) return false;
        switch (jobParameters.getJobId()) {
            case 123:
                try {
                    setPrizesAndPenalties();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(getFailedGoals()!=null)showNotification("You have failed the following goals: ", getFailedGoals());
                break;

            case 111:
                try {
                    if(getReminder()!=null) showNotification("Reminder ", getReminder());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;

            case 100:
                try {
                    if(getMotivation()!=null) showNotification("You still can do it! Those goals end today: ", getMotivation());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
        return true;
    }

    private String getReminder() throws ParseException {
        List<Prize> prizeList = dataBaseHandler.getAllPrizes();
        List<Goal> goalList = dataBaseHandler.getAllGoals();
        String reminder = "";
        LocalDate now = LocalDate.now();
        for (Prize prize : prizeList) {
            if (!prize.getAchieved().equalsIgnoreCase("Achieved")) {
                for (Goal goal : goalList) {
                    if (goal.getGoalName().equals(prize.getGoalName())&& !reminder.contains(prize.getGoalName())) {
                        long remain = DAYS.between(now, goal.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        if (remain <8 && remain>0) reminder = reminder + goal.getGoalName() + " ";
                    }
                }
            }
        }
        return reminder;
    }

    private String getMotivation() throws ParseException {
        List<Prize> prizeList = dataBaseHandler.getAllPrizes();
        List<Goal> goalList = dataBaseHandler.getAllGoals();
        String motivation = "";
        LocalDate now = LocalDate.now();
        for (Prize prize : prizeList) {
            if (!prize.getAchieved().equalsIgnoreCase("Achieved")) {
                for (Goal goal : goalList) {
                    if (goal.getGoalName().equals(prize.getGoalName())&& !motivation.contains(prize.getGoalName())) {
                        long remain = DAYS.between(now, goal.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        if (remain == 0) motivation = motivation + goal.getGoalName() + " ";
                    }

                }
            }

        }
        return motivation;
    }

    private String getFailedGoals() {
        List<Penalty> penaltyList = dataBaseHandler.getAllPenalties();
        String failedGoals = "";
        for (Penalty penalty : penaltyList) {
            if (penalty.getActive().equalsIgnoreCase("ACTIVE") && !failedGoals.contains(penalty.getGoalName()))
                failedGoals = failedGoals + penalty.getGoalName() + "\n";
        }
        return failedGoals;
    }

    private void setPrizesAndPenalties() throws ParseException {
        List<Penalty> penaltyList = dataBaseHandler.getAllPenalties();
        List<Prize> prizeList = dataBaseHandler.getAllPrizes();
        List<Goal> goalList = dataBaseHandler.getAllGoals();
        LocalDate now = LocalDate.now();
        for (Goal goal : goalList) {
            long remain = DAYS.between(now, goal.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            Penalty penalty = null;
            for (Penalty element : penaltyList) {
                if (element.getGoalName().equals(goal.getGoalName())) {
                    penalty = element;
                    if (remain < 0) {
                        penalty.setActive("Active");
                        dataBaseHandler.updatePenalty(penalty);
                    }
                }
            }
            Prize prize = null;
            for (Prize element : prizeList) {
                if (element.getGoalName().equals(goal.getGoalName())) {
                    prize = element;
                    if (remain < 0) {
                        prize.setAchieved("Failed");
                        dataBaseHandler.updatePrize(prize);
                    }
                }

            }
        }
    }


    public void showNotification(String title, String task) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("inducesmile", "inducesmile", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "inducesmile")
                .setContentTitle(title)
                .setContentText(task)
                .setSmallIcon(R.drawable.lovapplogo);
        notificationManager.notify(1, notification.build());
    }


    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        cancel = true;
        return true;
    }
}
