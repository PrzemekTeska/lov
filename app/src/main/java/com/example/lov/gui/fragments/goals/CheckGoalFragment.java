package com.example.lov.gui.fragments.goals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lov.DB.DataBaseHandler;
import com.example.lov.R;
import com.example.lov.model.Goal;
import com.example.lov.service.DateStringConverter;

import java.time.LocalDate;
import java.time.ZoneId;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import static java.time.LocalDate.now;
import static java.time.temporal.ChronoUnit.DAYS;

public class CheckGoalFragment extends Fragment implements View.OnClickListener {

    private Button editGoalButton;
    private DataBaseHandler dataBaseHandler;
    private TextView checkGoalName;
    private TextView checkGoalStartDate;
    private TextView checkGoalEndDate;
    private TextView checkGoalRemainingDay;
    private Goal goal;

    private DateStringConverter dateStringConverter=new DateStringConverter();

    public CheckGoalFragment(Goal goal) {
        this.goal = goal;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_check_goals, container, false);
        dataBaseHandler=new DataBaseHandler(getContext());

        checkGoalName=rootView.findViewById(R.id.check_goal_name);
        checkGoalStartDate=rootView.findViewById(R.id.check_goal_start_date);
        checkGoalEndDate=rootView.findViewById(R.id.check_goal_end_date);
        checkGoalRemainingDay=rootView.findViewById(R.id.check_days_remaining);

        editGoalButton = rootView.findViewById(R.id.edit_goal_button);

        checkGoalName.setText(goal.getGoalName());
        checkGoalStartDate.setText(dateStringConverter.getString(goal.getStartDate()));
        checkGoalEndDate.setText(dateStringConverter.getString(goal.getEndDate()));

        LocalDate start = LocalDate.now();
        LocalDate end = goal.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if(DAYS.between(start, end)<0)checkGoalRemainingDay.setText("Goal failed");
        else checkGoalRemainingDay.setText(String.valueOf(DAYS.between(start, end)));

        //TODO DODAWAC PUNKTY LUB KARY

        editGoalButton.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment;
        switch(v.getId()){
            case R.id.edit_goal_button:
                fragment = new EditGoalFragment(goal);
                replaceFragment(fragment);
                break;
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
