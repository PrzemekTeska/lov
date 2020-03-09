package com.example.lov.gui.fragments.goals;

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
import com.example.lov.gui.fragments.activity.ActivitiesFragment;
import com.example.lov.gui.fragments.progress.ProgressFragment;
import com.example.lov.model.Activity;
import com.example.lov.model.Goal;
import com.example.lov.model.Prize;

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
    private int amount;
    private Activity activity;
    private String oldAN;
    private String oldGN;
    private String progress="";
    private String progressName;

    public ChooseGoalFragment(String progress,String progressName) {
        this.progress = progress;
        this.progressName=progressName;
    }

    public ChooseGoalFragment(String activityName, String unit, int points, int amount) {
        this.activityName = activityName;
        this.unit = unit;
        this.points = points;
        this.amount=amount;
    }

    public ChooseGoalFragment(Activity activity,String oldAN,String oldGN) {
        this.oldGN=oldGN;
        this.activity = activity;
        this.oldAN=oldAN;
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
        Goal item = (Goal) parent.getAdapter().getItem(position);
            if (!progress.equals("")) {
                if (progress.equalsIgnoreCase("prize")) {
                    dataBaseHandler.insertPrizeIntoDataBase(progressName, "In Progress", item.getGoalName());
                    Toast.makeText(getContext(), "Prize added to the data base", Toast.LENGTH_SHORT).show();
                } else {
                    dataBaseHandler.insertPenaltyIntoDataBase(progressName, "Inactive", item.getGoalName());
                    Toast.makeText(getContext(), "Penalty added to the data base", Toast.LENGTH_SHORT).show();
                }
                Fragment fragment = new ProgressFragment();
                replaceFragment(fragment);
            }
        else if(activity!=null) {
            activity.setGoalNameFK(item.getGoalName());
            dataBaseHandler.updateActivity(activity,oldAN,oldGN);
                Fragment fragment = new ActivitiesFragment();
                replaceFragment(fragment);
        }
        else{
            try {
                boolean result = dataBaseHandler.insertActivityIntoDataBase(activityName, unit, points, amount, item.getGoalName());
                if (result)
                    Toast.makeText(getContext(), "Activity added to the data base", Toast.LENGTH_SHORT).show();
            } catch (ParseException e) {
                Toast.makeText(getContext(), "Something went wrong try again!", Toast.LENGTH_SHORT).show();
            }
                Fragment fragment = new ActivitiesFragment();
                replaceFragment(fragment);
        }

    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
