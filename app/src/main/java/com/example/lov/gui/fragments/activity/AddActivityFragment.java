package com.example.lov.gui.fragments.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lov.DB.DataBaseHandler;
import com.example.lov.R;
import com.example.lov.gui.fragments.goals.AddGoalFragment;
import com.example.lov.gui.fragments.goals.ChooseGoalFragment;

import java.text.ParseException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class AddActivityFragment extends Fragment implements View.OnClickListener , AdapterView.OnItemSelectedListener {
    private Spinner ActivityUnitSpinner;
    private Spinner ActivityWeightSpinner;
    private Spinner activityTemplateSpinner;
    private EditText activityName;
    private EditText activityAmount;
    private DataBaseHandler dataBaseHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_activity, container, false);

        dataBaseHandler=new DataBaseHandler(getContext());
        Button addActivityButton = rootView.findViewById(R.id.add_activity_button);
        ActivityUnitSpinner = rootView.findViewById(R.id.activity_unit_spinner);
        ActivityWeightSpinner = rootView.findViewById(R.id.activity_weight_spinner);

        activityTemplateSpinner = rootView.findViewById(R.id.activity_template_spinner);
        activityName = rootView.findViewById(R.id.activity_name);
        activityAmount=rootView.findViewById(R.id.activity_amount);

        String[] weightSpinner = new String[]{"1", "2", "3", "4", "5"};
        String[] unitSpinner = new String[]{"km", "kg", "h", "min", "pages"};
        String[] templateSpinner = new String[]{"-","Reading", "Running", "Workout", "Swimming", "Studying"};

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, unitSpinner);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ActivityUnitSpinner.setAdapter(unitAdapter);

        ArrayAdapter<String> weightAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, weightSpinner);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ActivityWeightSpinner.setAdapter(weightAdapter);

        ArrayAdapter<String> templateAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, templateSpinner);
        templateAdapter.setDropDownViewResource((android.R.layout.simple_spinner_dropdown_item));
        activityTemplateSpinner.setAdapter(templateAdapter);

        addActivityButton.setOnClickListener(this);
        activityTemplateSpinner.setOnItemSelectedListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        String name = activityName.getText().toString();
        String unit = ActivityUnitSpinner.getSelectedItem().toString();
        int amount= Integer.parseInt(activityAmount.getText().toString());
        int points = Integer.parseInt(ActivityWeightSpinner.getSelectedItem().toString());

        try {
            if(!dataBaseHandler.getAllGoals().isEmpty()) {
                fragment = new ChooseGoalFragment(name, unit, points, amount);
                replaceFragment(fragment);
            }else{Toast.makeText(getContext(), "Please add a goal first!", Toast.LENGTH_SHORT).show();
                fragment = new AddGoalFragment();
                replaceFragment(fragment);
            }

        } catch (ParseException e) {
            Toast.makeText(getContext(), "Something went wrong try again!", Toast.LENGTH_SHORT).show();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(!activityTemplateSpinner.getSelectedItem().toString().equals("-")){
            String name=activityTemplateSpinner.getSelectedItem().toString();
            activityName.setText(name);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
