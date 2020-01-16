package com.example.lov.gui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.lov.DB.DataBaseHandler;
import com.example.lov.gui.arrayadapters.ArrayAdapterActivity;
import com.example.lov.model.Activity;
import com.example.lov.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import java.util.ArrayList;

public class ActivitiesFragment extends Fragment implements View.OnClickListener{

    private DataBaseHandler dataBaseHandler;
    private ListView listViewActivities;
    private ArrayList<Activity> activities = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_activities, container, false);

        Button goToAddActivityButton = rootView.findViewById(R.id.go_to_add_activity_button);

        goToAddActivityButton.setOnClickListener(this);

        listViewActivities = rootView.findViewById(R.id.list_view_activities);


        dataBaseHandler= new DataBaseHandler(getContext());
            for(Activity item :dataBaseHandler.getAllActivities()) activities.add(item);

        ArrayAdapterActivity arrayAdapterActivity = new ArrayAdapterActivity(activities, getActivity());

        listViewActivities.setAdapter(arrayAdapterActivity);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment;
        switch(v.getId()){
            case R.id.go_to_add_activity_button:
                fragment = new AddActivityFragment();
                replaceFragment(fragment);
                break;
        }
    }

    public void replaceFragment(Fragment someFragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
