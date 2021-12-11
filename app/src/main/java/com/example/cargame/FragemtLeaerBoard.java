package com.example.cargame;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FragemtLeaerBoard extends Fragment {
    private ListView listView;
    private ArrayList<Integer> pointsArrayList;
    private ArrayList<String> stringArrayList;

    private static final String TAG = "FragemtLeaerBoard";

    private FragmentTouchListener listener;

    public interface FragmentTouchListener {
        void onChoose(int index);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_leader_board, container, false);
         listView = view.findViewById(R.id.listView);
         pointsArrayList = getArguments().getIntegerArrayList(LeaderBoardMap.POINTS);
         stringArrayList = new ArrayList<>();
         for( Integer integer: pointsArrayList){
             stringArrayList.add(String.valueOf(integer));
             Log.d(TAG, "onCreateView: "+ integer);
         }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, stringArrayList);
         listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getContext(), "The position is: " + position, Toast.LENGTH_SHORT).show();

                listener.onChoose(position);
            }
        });

         return view;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (FragmentTouchListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        listener = null;
    }
}
