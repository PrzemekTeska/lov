package com.example.lov.gui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lov.DB.DataBaseHandler;
import com.example.lov.R;
import com.example.lov.gui.arrayadapters.ArrayAdapterGoal;
import com.example.lov.model.Goal;

import java.text.ParseException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ChooseGoalFragment extends Fragment implements  ListView.OnItemClickListener {

    private DataBaseHandler dataBaseHandler;
    private ArrayList<Goal> goals = new ArrayList<>();
    private ListView listViewChooseGoal;
    private String activityName;
    private String unit;
    private int points;

    public ChooseGoalFragment(String activityName, String unit, int points) {
        this.activityName = activityName;
        this.unit = unit;
        this.points = points;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_choose_goal_list, container, false);

        listViewChooseGoal = rootView.findViewById(R.id.choose_goal_list);
        dataBaseHandler= new DataBaseHandler(getContext());

        try {
            for(Goal item :dataBaseHandler.getAllGoals()) goals.add(item);
        }catch (ParseException e){}

        ArrayAdapterGoal arrayAdapterGoal = new ArrayAdapterGoal(goals, getActivity());

        listViewChooseGoal.setAdapter(arrayAdapterGoal);
        listViewChooseGoal.setOnItemClickListener(this);

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Goal item =(Goal) parent.getAdapter().getItem(position);
        try {
            boolean result = dataBaseHandler.insertActivityIntoDataBase(activityName,unit,points,item.getGoalName());
            if(result) Toast.makeText(getContext(), "Activity added to the data base", Toast.LENGTH_SHORT).show();
        }catch (ParseException e){
            Toast.makeText(getContext(), "Something went wrong try again!", Toast.LENGTH_SHORT).show();
        }

        Fragment fragment = new ActivitiesFragment();
        replaceFragment(fragment);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
