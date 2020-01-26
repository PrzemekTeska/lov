package com.example.lov.gui.fragments.progress;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lov.DB.DataBaseHandler;
import com.example.lov.R;
import com.example.lov.gui.fragments.goals.ChooseGoalFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class AddProgressFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private EditText editTextProgressName;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private Button button;
    private DataBaseHandler dataBaseHandler;
    private String progress;
    private Spinner spinnerPrizes;
    private Spinner spinnerPenalties;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_progress, container, false);
        dataBaseHandler = new DataBaseHandler(getContext());
        editTextProgressName = rootView.findViewById(R.id.add_progress_name);
        radioGroup = rootView.findViewById(R.id.add_progress_radio_group);
        radioButton1 = rootView.findViewById(R.id.add_prize_radio_button);
        radioButton2 = rootView.findViewById(R.id.add_penalty_radio_button);
        button = rootView.findViewById(R.id.add_progress_button);
        spinnerPrizes=rootView.findViewById(R.id.prizes_spinner);
        spinnerPenalties=rootView.findViewById(R.id.penalties_spinner);

        String[] spinnerPrizesString = new String[]{"-", "cheat meal", "beer", "day off"};
        String[] spinnerPenaltiesString = new String[]{"-", "10km run", "gym", "read 40 extra pages"};

        ArrayAdapter<String> prizesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinnerPrizesString);
        prizesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrizes.setAdapter(prizesAdapter);

        ArrayAdapter<String> penaltyAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinnerPenaltiesString);
        penaltyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPenalties.setAdapter(penaltyAdapter);

        spinnerPrizes.setOnItemSelectedListener(this);
        spinnerPenalties.setOnItemSelectedListener(this);

        button.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.add_prize_radio_button:
                        progress="prize";
                        break;
                    case R.id.add_penalty_radio_button:
                        progress="penalty";
                        break;
                    default:

                }
            }
        });


        return rootView;
    }

    @Override
    public void onClick(View view) {
        Fragment fragment=this;
        if(!editTextProgressName.getText().toString().trim().equals("")) {
            if (radioButton1.isChecked() || radioButton2.isChecked()) {
                fragment = new ChooseGoalFragment(progress, editTextProgressName.getText().toString());
            }
            replaceFragment(fragment);
        }else  Toast.makeText(getContext(), "Enter a name", Toast.LENGTH_SHORT).show();
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.prizes_spinner:
                if(!spinnerPrizes.getSelectedItem().toString().equals("-")) editTextProgressName.setText(spinnerPrizes.getSelectedItem().toString());
                break;

            case R.id.penalties_spinner:
                if(!spinnerPenalties.getSelectedItem().toString().equals("-")) editTextProgressName.setText(spinnerPenalties.getSelectedItem().toString());
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
