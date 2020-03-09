package com.example.lov.gui.arrayadapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.lov.DB.DataBaseHandler;
import com.example.lov.gui.fragments.activity.ActivitiesFragment;
import com.example.lov.gui.fragments.activity.EditActivityFragment;
import com.example.lov.gui.mainActivities.MainActivity;
import com.example.lov.model.Activity;
import com.example.lov.R;
import com.example.lov.model.User;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ArrayAdapterActivity extends BaseAdapter implements ListAdapter {

    private ArrayList<Activity> list = new ArrayList<Activity>();
    private Context context;
    private Activity newActivity;
    private DataBaseHandler dataBaseHandler;

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
        dataBaseHandler=new DataBaseHandler(context);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_list_item,null);
        }

        TextView activityWeight = view.findViewById(R.id.activity_weight_text);
        TextView activityUnit =  view.findViewById(R.id.activity_unit_text);
        TextView activityText = view.findViewById(R.id.activity_text);
        TextView activityAmount = view.findViewById(R.id.activity_amount_text);


        newActivity = list.get(position);
        activityWeight.setText(Integer.toString(newActivity.getActivityPoints()));
        activityUnit.setText(newActivity.getActivityUnit());
        activityText.setText(newActivity.getActivityName());
        activityAmount.setText(Integer.toString(newActivity.getActivityAmount()));

        newActivity = list.get(position);

        return view;
    }


    public void replaceFragment(Fragment someFragment){
        FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
