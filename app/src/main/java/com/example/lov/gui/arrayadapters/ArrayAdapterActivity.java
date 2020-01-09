package com.example.lov.gui.arrayadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.lov.model.Activity;
import com.example.lov.R;

import java.util.ArrayList;

public class ArrayAdapterActivity extends BaseAdapter implements ListAdapter {

    private ArrayList<Activity> list = new ArrayList<Activity>();
    private Context context;

    public ArrayAdapterActivity(ArrayList<Activity> list, Context context) {
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
            view = inflater.inflate(R.layout.activity_list_item,null);
        }

        TextView activityWeight = view.findViewById(R.id.activity_weight_text);
        TextView activityUnit =  view.findViewById(R.id.activity_unit_text);
        TextView activityText = view.findViewById(R.id.activity_text);

        Activity newActivity = list.get(position);
        activityWeight.setText(Integer.toString(newActivity.getActivityPoints()));
        activityUnit.setText(newActivity.getActivityUnit());
        activityText.setText(newActivity.getActivityName());

        Button completeActivityButton =  view.findViewById(R.id.activity_completed);
        Button failedActivityButton =  view.findViewById(R.id.activity_failed);
        Button editActivityButton =  view.findViewById(R.id.modify_activity);

        return view;
    }
}
