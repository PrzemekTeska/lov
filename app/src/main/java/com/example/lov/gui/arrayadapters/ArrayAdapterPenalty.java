package com.example.lov.gui.arrayadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.lov.DB.DataBaseHandler;
import com.example.lov.R;
import com.example.lov.model.Penalty;

import java.util.ArrayList;

public class ArrayAdapterPenalty extends BaseAdapter implements ListAdapter {

    private ArrayList<Penalty> list = new ArrayList<>();
    private Context context;
    private Penalty penalty;
    private DataBaseHandler dataBaseHandler;

    public ArrayAdapterPenalty(ArrayList<Penalty> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        dataBaseHandler=new DataBaseHandler(context);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.penalty_list_item,null);
        }

        TextView penaltyStatusTextView = view.findViewById(R.id.penalty_status_text_view);
        TextView penaltyNameTextView = view.findViewById(R.id.penalty_name);
        TextView goalNamePenalties = view.findViewById(R.id.goal_name_penalties);

        penalty = list.get(i);

        penaltyStatusTextView.setText(penalty.getActive());
        penaltyNameTextView.setText(penalty.getPenaltyName());
        goalNamePenalties.setText(penalty.getGoalName());
        penalty = list.get(i);

        return view;
    }




}
