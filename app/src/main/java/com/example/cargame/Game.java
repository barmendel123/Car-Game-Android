package com.example.cargame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;

public class Game extends AppCompatActivity {
    GameManager gameManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        Intent intent = getIntent();
        int maxScore = intent.getIntExtra(MainActivity.MAX, -1);
        String psikim = intent.getStringExtra(MainActivity.PSIKIM);
        boolean isSensor = intent.getBooleanExtra(MainActivity.SENSOR,false);
        boolean isFast = intent.getBooleanExtra(MainActivity.VELOCITY, false);
        Record record = (Record) intent.getSerializableExtra(MainActivity.RECORD);
        gameManager = new GameManager(this, point.x, point.y, maxScore, psikim, isSensor, isFast, record);
        setContentView(gameManager);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameManager.pause();


    }

    @Override
    protected void onResume() {
        super.onResume();
        gameManager.resume();
    }
}