package com.example.lov.gui.mainActivities;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lov.R;
import com.example.lov.service.NotificationJobService;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationsActivity extends AppCompatActivity {

    private Button notificationButton;
    private Button backButton;
    private RadioGroup radioGroupNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        notificationButton=findViewById(R.id.apply_edit_notifications_button);
        radioGroupNotifications=findViewById(R.id.notifications_radio_group);
        backButton=findViewById(R.id.back);


        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                goToMain();
            }});

        notificationButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int selectedId=radioGroupNotifications.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(selectedId);
                switch (radioButton.getText().toString()){
                    case "None":
                        closeScheduler(123);
                        closeScheduler(111);
                        closeScheduler(100);
                        Toast.makeText(getApplicationContext(), "Scheduler turned off", Toast.LENGTH_SHORT).show();
                        goToMain();
                        break;

                    case "Rare":
                        Toast.makeText(getApplicationContext(), "Scheduler turned to pop rarely during a day", Toast.LENGTH_SHORT).show();
                        goToMain();
                        scheduler(24*60*60*1000,123);
                        scheduler(24*60*60*1000,111);
                        scheduler(24*60*60*1000,100);
                        break;

                    case "Often":
                        Toast.makeText(getApplicationContext(), "Scheduler turned to pop often during a day", Toast.LENGTH_SHORT).show();
                        goToMain();
                        scheduler(12*60*60*1000,123);
                        scheduler(12*60*60*1000,111);
                        scheduler(12*60*60*1000,100);
                        break;
                }
            }
        });

    }

    private void goToMain(){
        startActivity(new Intent(NotificationsActivity.this, MainActivity.class));
    }

    public void scheduler(long milisecs,int jobId){
        ComponentName componentName= new ComponentName(this, NotificationJobService.class);
        JobInfo jobInfo=new JobInfo.Builder(jobId,componentName)
                .setPersisted(true)
                // .setMinimumLatency(1*1000)
                // .setOverrideDeadline(3*1000)
                .setPeriodic(milisecs)
                .build();
        JobScheduler scheduler=(JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.schedule(jobInfo);

    }
    public void closeScheduler(int jobId){
        JobScheduler scheduler=(JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(jobId);
    }
    @Override
    public void onBackPressed(){
    }
}
