package com.example.lov.gui.fragments.goals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lov.DB.DataBaseHandler;
import com.example.lov.R;
import com.example.lov.gui.fragments.DateFragment;
import com.example.lov.model.Goal;
import com.example.lov.service.DateStringConverter;

import java.text.ParseException;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class EditGoalFragment extends Fragment implements View.OnClickListener {

    private Button editStartDateButton;
    private Button editEndDateButton;
    private Button applyEditGoalButton;
    private TextView editedStartDateTextView;
    private TextView editedEndDateTextView;
    private TextView editNameEditText;
    private DataBaseHandler dataBaseHandler;
    private Goal goal;
    private DateStringConverter dateStringConverter=new DateStringConverter();

    public EditGoalFragment(Goal goal) {
        this.goal = goal;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_goal, container, false);
        dataBaseHandler=new DataBaseHandler(getContext());

        editStartDateButton = rootView.findViewById(R.id.edit_start_date_button);
        editEndDateButton = rootView.findViewById(R.id.edit_end_date_button);
        applyEditGoalButton = rootView.findViewById(R.id.apply_edit_goal_button);
        editedStartDateTextView = rootView.findViewById(R.id.edited_start_date_view);
        editedEndDateTextView = rootView.findViewById(R.id.edited_end_date_view);
        editNameEditText = rootView.findViewById(R.id.edit_goal_name);


        editedStartDateTextView.setText(dateStringConverter.getString(goal.getStartDate()));
        editedEndDateTextView.setText(dateStringConverter.getString(goal.getEndDate()));
        editNameEditText.setText(goal.getGoalName());

        editStartDateButton.setOnClickListener(this);
        editEndDateButton.setOnClickListener(this);
        applyEditGoalButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_start_date_button: {
                DialogFragment startDateFragment = new DateFragment(editedStartDateTextView);
                startDateFragment.show(getFragmentManager(), "StartDateSelect");
                break;
            }
            case R.id.edit_end_date_button: {
                DialogFragment endDateFragment = new DateFragment(editedEndDateTextView);
                endDateFragment.show(getFragmentManager(), "EndDateSelect");
                break;
            }
            case R.id.apply_edit_goal_button:{
                try {
                Date startDate = dateStringConverter.getDate(editedStartDateTextView.getText().toString().replaceAll("/", "-"));
                Date endDate = dateStringConverter.getDate(editedEndDateTextView.getText().toString().replaceAll("/", "-"));
                if(startDate.before(endDate)){

                    goal.setStartDate(startDate);
                    goal.setEndDate(endDate);
                    boolean update= dataBaseHandler.updateGoal(goal);

                    if(update){Toast.makeText(getContext(), "Goal updated", Toast.LENGTH_LONG).show();
                        Fragment fragment = new GoalsFragment();
                        replaceFragment(fragment);
                    }
                    else Toast.makeText(getContext(), "Goal failed to update", Toast.LENGTH_LONG).show();

                }else Toast.makeText(getContext(), "End date cant be before start date", Toast.LENGTH_LONG).show();
            }catch(ParseException e){
                Toast.makeText(getContext(), "Something went wrong please try again", Toast.LENGTH_LONG).show();
            }
                break;
            }


        }
    }

    private void replaceFragment(Fragment someFragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
