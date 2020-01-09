package com.example.lov.gui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.lov.DB.DataBaseHandler;
import com.example.lov.R;
import com.example.lov.service.DateStringConverter;
import java.text.ParseException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class AddGoalFragment extends Fragment implements View.OnClickListener {

    Button selectStartDateButton;
    Button selectEndDateButton;
    Button addButton;
    TextView startDateTextView;
    TextView endDateTextView;
    TextView nameTextView;
    private DataBaseHandler dataBaseHandler;
    private DateStringConverter dateStringConverter=new DateStringConverter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_goal, container, false);

        selectStartDateButton = rootView.findViewById(R.id.start_date_button);
        selectEndDateButton =  rootView.findViewById(R.id.end_date_button);
        startDateTextView =  rootView.findViewById(R.id.start_date_view);
        endDateTextView = rootView.findViewById(R.id.end_date_view);

        addButton = rootView.findViewById(R.id.add_goal_button);
        nameTextView=rootView.findViewById(R.id.goal_name);
        selectStartDateButton.setOnClickListener(this);
        selectEndDateButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        dataBaseHandler= new DataBaseHandler(getContext());

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.start_date_button: {
                DialogFragment startDateFragment = new DateFragment(startDateTextView);
                startDateFragment.show(getFragmentManager(), "StartDateSelect");
                break;
            }
            case R.id.end_date_button:{
                DialogFragment endDateFragment = new DateFragment(endDateTextView);
                endDateFragment.show(getFragmentManager(), "EndDateSelect");
                break;}
            case R.id.add_goal_button:{
                try {
                    dataBaseHandler.insertGoalIntoDataBase(nameTextView.getText().toString(),
                            dateStringConverter.getDate(startDateTextView.getText().toString().replaceAll("/","-")),
                            dateStringConverter.getDate(endDateTextView.getText().toString().replaceAll("/","-")));
                }catch (ParseException e){}
                break;
            }
        }
    }

}
