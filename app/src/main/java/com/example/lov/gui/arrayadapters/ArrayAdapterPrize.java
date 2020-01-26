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
import com.example.lov.model.Prize;

import java.util.ArrayList;

public class ArrayAdapterPrize extends BaseAdapter implements ListAdapter {

    private ArrayList<Prize> list = new ArrayList<>();
    private Context context;
    private Prize prize;
    private DataBaseHandler dataBaseHandler;

    public ArrayAdapterPrize(ArrayList<Prize> list, Context context) {
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
            view = inflater.inflate(R.layout.prize_list_item,null);
        }

        TextView prizeStatusTextView = view.findViewById(R.id.prize_status_text_view);
        TextView prizeNameTextView = view.findViewById(R.id.prize_name);
        TextView goalNamePrizeTextView = view.findViewById(R.id.goal_name_prize);

        prize = list.get(i);

        prizeStatusTextView.setText(prize.getAchieved());
        prizeNameTextView.setText(prize.getPrizeName());
        goalNamePrizeTextView.setText(prize.getGoalName());

        prize = list.get(i);

        return view;
    }



}
