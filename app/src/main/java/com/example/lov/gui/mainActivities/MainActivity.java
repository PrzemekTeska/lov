package com.example.lov.gui.mainActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lov.DB.DataBaseHandler;
import com.example.lov.gui.fragments.activity.ActivitiesFragment;
import com.example.lov.gui.fragments.goals.GoalsFragment;
import com.example.lov.R;
import com.example.lov.gui.fragments.profile.ProfileFragment;
import com.example.lov.gui.fragments.progress.ProgressFragment;
import com.example.lov.service.NotificationJobService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {

    private ImageView userProfilePictureImageView;
    private TextView userStarsTextView;
    private TextView userNameTop;
    private Fragment fragment;
    private DataBaseHandler dataBaseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBaseHandler=new DataBaseHandler(this);
        userProfilePictureImageView = findViewById(R.id.user_profile_image_main);
        userStarsTextView = findViewById(R.id.user_points);
        userNameTop = findViewById(R.id.userName);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GoalsFragment()).commit();
        userNameTop.setText(dataBaseHandler.getActiveUser().getUserName());
        userStarsTextView.setText(String.valueOf(dataBaseHandler.getActiveUser().getPoints()));
        imageForUs();
    }

    private void imageForUs(){
        if(dataBaseHandler.getActiveUser().getAvatar().equals("drawable/profile2.jpg")){
            Picasso.get().load(R.drawable.profile2).resize(50,50).into(userProfilePictureImageView);
        }else if(dataBaseHandler.getActiveUser().getAvatar().equals("drawable/profile1.jpg")){
            Picasso.get().load(R.drawable.profile1).resize(50,50).into(userProfilePictureImageView);
        }
          else{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED || this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, 1001);
            } else {
                setImages();
            }
        }
    }
    }

    private void setImages() {
        String[] permisions={Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(ContextCompat.checkSelfPermission(this,permisions[0])== PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this,permisions[1])==PackageManager.PERMISSION_GRANTED) {
            Picasso.get().load(dataBaseHandler.getActiveUser().getAvatar()).resize(50,50).into(userProfilePictureImageView);
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch(menuItem.getItemId()){
                        case R.id.nav_goal:
                            selectedFragment = new GoalsFragment();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                        case R.id.nav_activity:
                            selectedFragment = new ActivitiesFragment();
                            break;
                        case R.id.nav_progress:
                            selectedFragment = new ProgressFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };


    public void replaceFragment(Fragment someFragment){
        FragmentManager manager = this.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed(){
    }

}