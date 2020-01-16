package com.example.lov.gui.arrayadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.lov.R;
import com.example.lov.model.Goal;
import com.example.lov.service.DateStringConverter;

import java.util.ArrayList;

public class ArrayAdapterGoal extends BaseAdapter implements ListAdapter {

    private ArrayList<Goal> list = new ArrayList<Goal>();
    private Context context;
    private DateStringConverter dateStringConverter=new DateStringConverter();

    public ArrayAdapterGoal(ArrayList<Goal> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.goals_list_item,null);
        }

        TextView goalText = view.findViewById(R.id.goal_name);
        TextView goalStartDate = view.findViewById(R.id.goal_start_date);
        TextView goalEndtDate = view.findViewById(R.id.goal_end_date);

        Goal newGoal = list.get(position);
        goalText.setText(newGoal.getGoalName());


        goalStartDate.setText( dateStringConverter.getString(newGoal.getStartDate()));
        goalEndtDate.setText(dateStringConverter.getString(newGoal.getEndDate()));

        return view;
    }
}
