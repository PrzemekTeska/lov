package com.example.lov.gui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

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

public class GoalsFragment extends Fragment implements View.OnClickListener{

    private ListView listViewGoals;
    private ArrayList<Goal> goals = new ArrayList<>();
    private DataBaseHandler dataBaseHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_goals, container, false);

        Button goToAddGoalButton = rootView.findViewById(R.id.go_to_add_goal_button);

        goToAddGoalButton.setOnClickListener(this);

        listViewGoals = rootView.findViewById(R.id.list_view_goals);
        dataBaseHandler= new DataBaseHandler(getContext());
        try {
            for(Goal item :dataBaseHandler.getAllGoals()) goals.add(item);
        }catch (ParseException e){}

        ArrayAdapterGoal arrayAdapterGoal = new ArrayAdapterGoal(goals, getActivity());

        listViewGoals.setAdapter(arrayAdapterGoal);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()){
            case R.id.go_to_add_goal_button:
                fragment = new AddGoalFragment();
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
