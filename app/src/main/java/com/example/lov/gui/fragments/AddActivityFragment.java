package com.example.lov.gui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.lov.R;
import com.example.lov.model.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class AddActivityFragment extends Fragment implements View.OnClickListener{
    Spinner ActivityUnitSpinner;
    Spinner ActivityWeightSpinner;
    EditText activityName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_activity, container, false);

        Button addActivityButton = rootView.findViewById(R.id.add_activity_button);
        ActivityUnitSpinner = rootView.findViewById(R.id.activity_unit_spinner);
        ActivityWeightSpinner = rootView.findViewById(R.id.activity_weight_spinner);
        activityName=rootView.findViewById(R.id.activity_name);

        String[] weightSpinner = new String[]{"1","2","3","4","5"};

        String[] unitSpinner = new String[]{"km", "kg", "h", "min", "strony"};

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, unitSpinner);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ActivityUnitSpinner.setAdapter(unitAdapter);

        ArrayAdapter<String> weightAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, weightSpinner);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ActivityWeightSpinner.setAdapter(weightAdapter);

        addActivityButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        String name= activityName.getText().toString();
        String unit = ActivityUnitSpinner.getSelectedItem().toString();
        int points =Integer.parseInt(ActivityWeightSpinner.getSelectedItem().toString());
        fragment = new ChooseGoalFragment(name,unit,points);
        replaceFragment(fragment);

    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
