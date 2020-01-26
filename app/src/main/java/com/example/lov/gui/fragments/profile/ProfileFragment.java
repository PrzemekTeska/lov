package com.example.lov.gui.fragments.profile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.lov.DB.DataBaseHandler;
import com.example.lov.R;
import com.example.lov.gui.mainActivities.MainActivity;
import com.example.lov.gui.mainActivities.NotificationsActivity;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private Button editProfileButton;
    private ImageView imageView;
    private DataBaseHandler dataBaseHandler;
    private Button editNotificationsButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        imageView=rootView.findViewById(R.id.user_profile_image_new);
        editProfileButton = rootView.findViewById(R.id.edit_profile_button);
        dataBaseHandler=new DataBaseHandler(getContext());
        editNotificationsButton= rootView.findViewById(R.id.edit_notifications_button);
        editTextSet();
        editProfileButton.setOnClickListener(this);
        editNotificationsButton.setOnClickListener(this);
        return rootView;
    }

    private void editTextSet() {
        if(dataBaseHandler.getActiveUser().getAvatar().equals("drawable/profile2.jpg")){
            Picasso.get().load(R.drawable.profile2).resize(50,50).into(imageView);
        }else if(dataBaseHandler.getActiveUser().getAvatar().equals("drawable/profile1.jpg")){
            Picasso.get().load(R.drawable.profile1).resize(50,50).into(imageView);
        }
        else {
            String[] permisions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (ContextCompat.checkSelfPermission(this.getContext(), permisions[0]) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this.getContext(), permisions[1]) == PackageManager.PERMISSION_GRANTED)
                Picasso.get().load(dataBaseHandler.getActiveUser().getAvatar()).resize(200, 200).into(imageView);
        }
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()){
            case R.id.edit_profile_button:
                fragment = new EditProfileFragment();
                replaceFragment(fragment);
                break;
            case R.id.edit_notifications_button:{
                startActivity(new Intent(getContext(), NotificationsActivity.class));
            }
        }
    }

    public void replaceFragment(Fragment someFragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
