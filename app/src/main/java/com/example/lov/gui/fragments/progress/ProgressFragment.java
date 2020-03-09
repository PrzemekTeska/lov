package com.example.lov.gui.fragments.progress;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lov.DB.DataBaseHandler;
import com.example.lov.R;
import com.example.lov.gui.arrayadapters.ArrayAdapterPenalty;
import com.example.lov.gui.arrayadapters.ArrayAdapterPrize;
import com.example.lov.gui.fragments.activity.EditActivityFragment;
import com.example.lov.model.Penalty;
import com.example.lov.model.Prize;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ProgressFragment extends Fragment implements View.OnClickListener , AdapterView.OnItemClickListener{

    private Button goToAddProgressButton;
    private ArrayList<Prize> prizeList=new ArrayList<Prize>();
    private ArrayList<Penalty>penaltyList=new ArrayList<Penalty>();
    private ListView prizeListView;
    private ListView penaltyListView;
    private DataBaseHandler dataBaseHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_progress, container, false);
        goToAddProgressButton=rootView.findViewById(R.id.go_to_add_progress_button);
        goToAddProgressButton.setOnClickListener(this);
        dataBaseHandler= new DataBaseHandler(getContext());
        prizeListView=rootView.findViewById(R.id.prizes_listview);
        penaltyListView=rootView.findViewById(R.id.penalties_listview);

        for(Prize item :dataBaseHandler.getAllPrizes()) prizeList.add(item);
        for(Penalty item :dataBaseHandler.getAllPenalties()) penaltyList.add(item);

        ArrayAdapterPrize arrayAdapterPrize = new ArrayAdapterPrize(prizeList, getActivity());
        ArrayAdapterPenalty arrayAdapterPenalty = new ArrayAdapterPenalty(penaltyList, getActivity());

        prizeListView.setAdapter(arrayAdapterPrize);
        penaltyListView.setAdapter(arrayAdapterPenalty);
        prizeListView.setOnItemClickListener(this);
        penaltyListView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        Fragment fragment=new AddProgressFragment();
        replaceFragment(fragment);

    }

    public void replaceFragment(Fragment someFragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Fragment fragment=null;

        switch (adapterView.getId()){
            case R.id.prizes_listview:
                Prize prize =(Prize) prizeListView.getItemAtPosition(i);
                fragment=new EditProgressFragment(prize);
                replaceFragment(fragment);
                break;
            case R.id.penalties_listview:
                Penalty penalty=(Penalty)penaltyListView.getItemAtPosition(i);
                fragment=new EditProgressFragment(penalty);
                replaceFragment(fragment);
                break;
        }

    }
}
