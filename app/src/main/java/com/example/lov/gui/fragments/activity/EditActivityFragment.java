package com.example.lov.gui.fragments.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lov.DB.DataBaseHandler;
import com.example.lov.R;
import com.example.lov.gui.fragments.goals.ChooseGoalFragment;
import com.example.lov.gui.mainActivities.MainActivity;
import com.example.lov.model.Activity;
import com.example.lov.model.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class EditActivityFragment extends Fragment implements View.OnClickListener {
    private Spinner activityWeightSpinner;
    private Button applyChangesOnActivityButton;
    private Button deleteActivity;
    private Button activityCompleteButton;
    private Button activityFailedButton;
    private EditText editActivityNameEditText;
    private EditText editActivityAmountEditText;
    private EditText editActivityUnitEditText;
    private DataBaseHandler dataBaseHandler;
    private Activity activity;
    private String oldAN;
    private String oldGN;

    public EditActivityFragment(Activity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_activity, container, false);

        dataBaseHandler=new DataBaseHandler(getContext());

        deleteActivity=rootView.findViewById(R.id.delete_activity_button);
        applyChangesOnActivityButton = rootView.findViewById(R.id.apply_edit_activity_button);
        activityWeightSpinner = rootView.findViewById(R.id.edit_weight_spinner);
        editActivityNameEditText = rootView.findViewById(R.id.edit_activity_name);
        editActivityAmountEditText = rootView.findViewById(R.id.edit_activity_amount);
        editActivityUnitEditText = rootView.findViewById(R.id.edit_activity_unit);
        activityCompleteButton=rootView.findViewById(R.id.complete_activity_button);
        activityFailedButton=rootView.findViewById(R.id.failed_activity_button);

        String[] weightSpinner = new String[]{"1","2","3","4","5"};

        ArrayAdapter<String> weightAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, weightSpinner);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityWeightSpinner.setAdapter(weightAdapter);
        editActivityNameEditText.setText(activity.getActivityName());
        editActivityUnitEditText.setText(activity.getActivityUnit());
        editActivityAmountEditText.setText(Integer.toString(activity.getActivityAmount()));
        oldGN = activity.getGoalNameFK();
        oldAN=editActivityNameEditText.getText().toString();

        applyChangesOnActivityButton.setOnClickListener(this);
        deleteActivity.setOnClickListener(this);
        activityCompleteButton.setOnClickListener(this);
        activityFailedButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.apply_edit_activity_button:{
                if(!editActivityNameEditText.getText().toString().isEmpty()) {
                    activity.setActivityName(editActivityNameEditText.getText().toString());
                    activity.setActivityPoints(Integer.parseInt(activityWeightSpinner.getSelectedItem().toString()));
                }
                if(!editActivityUnitEditText.getText().toString().isEmpty()) activity.setActivityUnit(editActivityUnitEditText.getText().toString());
                if(!editActivityAmountEditText.getText().toString().isEmpty() && editActivityAmountEditText.getText().toString().matches("[0-9]+"))
                { activity.setActivityAmount(Integer.parseInt(editActivityAmountEditText.getText().toString()));
                activity.setActivityPoints(Integer.parseInt(activityWeightSpinner.getSelectedItem().toString()));}
                else if(!editActivityAmountEditText.getText().toString().matches("[0-9]+")) Toast.makeText(getContext(), "Its not a number", Toast.LENGTH_LONG).show();
                Fragment fragment = null;
                fragment = new ChooseGoalFragment(activity,oldAN,oldGN);
                replaceFragment(fragment);
                break;
            }
            case R.id.delete_activity_button:{
                boolean deleteStatus=  dataBaseHandler.deleteActivity(activity);
                if(deleteStatus){
                    Toast.makeText(getContext(), "Activity successfully deleted", Toast.LENGTH_LONG).show();
                    Fragment fragment = null;
                    fragment = new ActivitiesFragment();
                    replaceFragment(fragment);
                }
                else Toast.makeText(getContext(), "Something went wrong try again", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.complete_activity_button:{
                int points= activity.getActivityPoints();
                User user=dataBaseHandler.getActiveUser();
                int userPoints =user.getPoints();
                userPoints+=points;
                user.setPoints(userPoints);
                dataBaseHandler.updateUser(user);
                dataBaseHandler.deleteActivity(activity);
                Intent intent = new Intent(getContext(), MainActivity.class);
                getContext().startActivity(intent);
                break;
            }

            case R.id.failed_activity_button:{
                int points= activity.getActivityPoints();
                User user=dataBaseHandler.getActiveUser();
                int userPoints =user.getPoints();
                userPoints-=points;
                if(userPoints<0)userPoints=0;
                user.setPoints(userPoints);
                dataBaseHandler.updateUser(user);
                dataBaseHandler.deleteActivity(activity);
                Intent intent = new Intent(getContext(), MainActivity.class);
                getContext().startActivity(intent);
            }
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

}
