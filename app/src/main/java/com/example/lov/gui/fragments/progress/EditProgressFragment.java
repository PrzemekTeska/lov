package com.example.lov.gui.fragments.progress;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lov.DB.DataBaseHandler;
import com.example.lov.R;
import com.example.lov.model.Penalty;
import com.example.lov.model.Prize;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class EditProgressFragment extends Fragment implements View.OnClickListener {

    private EditText progressNameEditText;
    private Button applyEditProgressButton;
    private Button disableProgressButton;
    private Prize prize;
    private Penalty penalty;
    private DataBaseHandler dataBaseHandler;

    public EditProgressFragment(Prize prize) {
        this.prize = prize;
    }

    public EditProgressFragment(Penalty penalty) {
        this.penalty = penalty;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_progress, container, false);
        dataBaseHandler = new DataBaseHandler(getContext());
        progressNameEditText = rootView.findViewById(R.id.progress_name_edit_text);
        applyEditProgressButton = rootView.findViewById(R.id.apply_edit_progress_button);
        disableProgressButton = rootView.findViewById(R.id.disable_progress_button);
        applyEditProgressButton.setOnClickListener(this);
        disableProgressButton.setOnClickListener(this);

        if (penalty == null) progressNameEditText.setText(prize.getPrizeName());
        else progressNameEditText.setText(penalty.getPenaltyName());

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.apply_edit_progress_button:
                if (progressNameEditText.getText().toString().trim().equals(""))
                    Toast.makeText(getContext(), "Field name cant be empty", Toast.LENGTH_SHORT).show();
                else {
                    if (penalty == null) {
                        prize.setPrizeName(progressNameEditText.getText().toString());
                        if (dataBaseHandler.updatePrize(prize)) {
                            Toast.makeText(getContext(), "Prize updated", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        penalty.setPenaltyName(progressNameEditText.getText().toString());
                        if (dataBaseHandler.updatePenalty(penalty)) {
                            Toast.makeText(getContext(), "Penalty updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Fragment fragment = new ProgressFragment();
                    replaceFragment(fragment);
                }
                break;

            case R.id.disable_progress_button:
                if (penalty == null) {
                    if (dataBaseHandler.deletePrize(prize.getId())) {
                        Toast.makeText(getContext(), "Prize deleted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (dataBaseHandler.deletePenalty(penalty.getId())) {
                        Toast.makeText(getContext(), "Penalty deleted", Toast.LENGTH_SHORT).show();
                    }
                }
                Fragment fragment = new ProgressFragment();
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
