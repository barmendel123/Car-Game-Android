package com.example.cargame;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentMap extends Fragment {
    private double[] latitudeArrayList, longTitudeArrayList;
    boolean isMapReady = false;
    GoogleMap googleMapStore;

    private static final String TAG = "FragmentMap";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        latitudeArrayList = getArguments().getDoubleArray(LeaderBoardMap.LATITUDE);
        longTitudeArrayList = getArguments().getDoubleArray(LeaderBoardMap.LONGTITUDE);
/*
        latitudeArrayList[0] = 38.64;
        longTitudeArrayList[0] = -103.49;
        latitudeArrayList[1] = -14.329;
        longTitudeArrayList[1] = -51.67;
        latitudeArrayList[2] = 32.645;
        longTitudeArrayList[2] = 35.3;

 */

        Log.d(TAG, "onCreateView: " + latitudeArrayList[0]);
        Log.d(TAG, "onCreateView: " + longTitudeArrayList[0]);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googleMap);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                isMapReady = true;
                googleMapStore = googleMap;

                for (int i = 0 ; i < latitudeArrayList.length ; i++){
                    Log.d(TAG, "onMapReady: lat: " + latitudeArrayList[i] + " long: " + longTitudeArrayList[i]);
                    googleMap.addMarker(
                            new MarkerOptions()
                            .position(new LatLng(latitudeArrayList[i] , longTitudeArrayList[i]))
                            .title(String.valueOf(i)));
                }

                moveFocusMap(0);
            }
        });


        return view;


    }

    public void moveFocusMap(int index){
        if(isMapReady){
            googleMapStore.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(latitudeArrayList[index], longTitudeArrayList[index])));
        }
    }


}
