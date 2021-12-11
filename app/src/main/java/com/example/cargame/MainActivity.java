package com.example.cargame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    public static final String SHARERECORDS = "S";
    public static final String TEXT = "text";
    public static final String MAX = "max";
    public static final String PSIKIM = "psikim";
    public static final String SENSOR = "sensor";
    public static final String VELOCITY = "velocity";
    public static final int permitionCode = 1;
    public static final String RECORD = "record";
    int maxScore = -1;
    ImageView imageView;
    boolean isSensor;
    boolean isFast;
   Switch aSwitch;
   Switch switchVelocity;
    String psikim;
    Record record;
    Button button;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        getAccess();
        aSwitch = findViewById(R.id.switch1);
        switchVelocity = findViewById(R.id.switch2);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LeaderBoardMap.class);
                startActivity(intent);
            }
        });
        switchVelocity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isFast = isChecked;
                if(isFast){
                    switchVelocity.setText("Fast Mode");
                }
                else
                    switchVelocity.setText("Slow Mode");
            }
        });
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isSensor = isChecked;
                if(isChecked){
                   aSwitch.setText("Sensor Mode");
                }
                else{
                    aSwitch.setText("Tap Mode");
                }
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(), Game.class);
                intent.putExtra(MAX, maxScore);
                intent.putExtra(PSIKIM, psikim);
                intent.putExtra(SENSOR, isSensor);
                intent.putExtra(VELOCITY, isFast );
                intent.putExtra(RECORD, record);
                startActivity(intent);


            }
        });
    }


    @AfterPermissionGranted(permitionCode)
    private void getAccess(){
        String[] strings = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if(EasyPermissions.hasPermissions(this, strings)){
            getLocation();
        }
        else{
            EasyPermissions.requestPermissions(this, "We need to get your location", permitionCode,strings );
        }
    }
    @SuppressLint("MissingPermission")
    private void getLocation(){
        Random r = new Random();
        int longT = r.nextInt((50 - (-50)) + 1) + (-50);
        int latT = r.nextInt((50 - (-50)) + 1) + (-50);

        record = new Record(longT, latT);
        /*
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    Random r = new Random();
                    int longT = r.nextInt((50 - (-50)) + 1) + (-50);
                    int latT = r.nextInt((50 - (-50)) + 1) + (-50);

                    record = new Record(longT, latT);


                    //record = new Record(location.getLongitude(), location.getAltitude());
                    Log.d(TAG, "onSuccess: " + record.toString());
                }
            }
        }); */
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


}