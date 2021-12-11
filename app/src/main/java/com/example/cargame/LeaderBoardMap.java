package com.example.cargame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LeaderBoardMap extends AppCompatActivity implements FragemtLeaerBoard.FragmentTouchListener {
    public static final String POINTS = " POINTS";
    public static final String LATITUDE = " LATITUDE";
    public static final String LONGTITUDE = "LONGTITUDE";
    FragemtLeaerBoard fragemtLeaerBoard;
    FragmentMap fragmentMap;
    ArrayList <Record> recordArrayList;
    ArrayList<Integer> pointsArrayList;
    double[] latitudeArrayList, longTitudeArrayList;

    private static final String TAG = "LeaderBoardMap";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board_map);
        loadData();
        sortData();
        seperateDate();
        Bundle bundle1 = new Bundle();
        Bundle bundle2 = new Bundle();
        bundle1.putIntegerArrayList(POINTS, pointsArrayList);
        bundle2.putDoubleArray(LATITUDE, latitudeArrayList);
        bundle2.putDoubleArray(LONGTITUDE, longTitudeArrayList);
        fragemtLeaerBoard = new FragemtLeaerBoard();
        fragemtLeaerBoard.setArguments(bundle1);
        fragmentMap = new FragmentMap();
        fragmentMap.setArguments(bundle2);


        getSupportFragmentManager().beginTransaction().replace(R.id.leader_points, fragemtLeaerBoard).replace(R.id.map, fragmentMap).commit();


    }

    private void seperateDate() {
        pointsArrayList = new ArrayList<>();
        latitudeArrayList = new double[recordArrayList.size()];
        longTitudeArrayList = new double[recordArrayList.size()];
        for(int i = 0 ; i < recordArrayList.size() ; i++){
            pointsArrayList.add(recordArrayList.get(i).getPoints());
            latitudeArrayList[i] = recordArrayList.get(i).getAltTitude();
            longTitudeArrayList[i] = recordArrayList.get(i).getLongTitude();
        }
    }


    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(GameManager.SHARED, MODE_PRIVATE);
        String jSon = sharedPreferences.getString(GameManager.RECORDARRAY, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Record>>(){}.getType();
        recordArrayList = gson.fromJson(jSon, type);
        if(recordArrayList == null){
            recordArrayList = new ArrayList<>();
        }

        Log.d(TAG, "loadData: " + recordArrayList.get(0));


    }

    private void sortData() {
        Collections.sort(recordArrayList, new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return o1.getPoints() - o2.getPoints();
            }
        });
    }

    @Override
    public void onChoose(int index) {
        fragmentMap.moveFocusMap(index);
    }
}