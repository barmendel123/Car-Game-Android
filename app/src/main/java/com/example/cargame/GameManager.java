package com.example.cargame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;
import android.os.Handler;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.logging.LogRecord;

public class GameManager extends SurfaceView implements Runnable
{
    public static final String SHARED = "SHARED";
    public static final String RECORDARRAY = "RECORD_ARRAY";
    private boolean isPlaying;
    Context context;
    private Car car;
    private Obstacle obstacle1, obstacle2, obstacle3;
    private Heart heart;
    private Handler handler;
    private Backround backround;
    private Paint paint, paintText;
    private Thread thread;
    private Coin coin;
    int xPixel, yPixel;
    int isTouch = 0; // 0 is no press// 1 is left // 2 is right
    int low1 = 0, height1 =100 , low2 = 1100, height2 = 1400, low3 = 200, height3 = 500;
    int lowCoin = 200, highCoin = 1200;
    Random random;
    int velocity;
    int velocityFast;
    int lives = 3;
    int point = 0;
    MediaPlayer mediaPlayer;
    int maxRecord;
    String psikim;
    boolean isSensor;
    private Gyroscope gyroscope;
    boolean starGame = true;
    float currentZrot;
    float thresHoldZ = (float) (30*Math.PI/180);
    boolean velocityRegular = true;
    boolean isFast;
    Record record;
    float xAngle = 0, zAngle = 0;

    private static final String TAG = "GameManager";




    public GameManager(Context context, int xLength, int yLength, int maxRecord, String psikim, boolean isSensor, boolean isFast, Record record) {
        super(context);
        this.xPixel = xLength;
        this.yPixel = yLength;
        this.context = context;
        car = new Car(xPixel, yPixel, getResources());
        random = new Random();
        obstacle1 = new Obstacle(xPixel, yPixel, functionXRandom(), getResources());
        obstacle2 = new Obstacle(xPixel, yPixel, functionXRandom(), getResources());
        obstacle3 = new Obstacle(xPixel, yPixel, functionXRandom(), getResources());
        velocity = yPixel/(3*60); // is the speed of the obstacles
        velocityFast = yPixel/(2*60);
        obstacle1.setY(functionRandom(1));
        obstacle2.setY(functionRandom(2));
        heart = new Heart(getResources());
        inItHandler();
        backround = new Backround(getResources());
        paintText = new Paint();
        paintText.setColor(Color.BLACK);
        paintText.setTextSize(50);
        mediaPlayer = MediaPlayer.create(context, R.raw.soundcar);
        coin = new Coin(functionXRandom(), xPixel, yPixel, getResources());
        coin.setY(functionRandom(4));
        this.maxRecord = maxRecord;
        this.psikim = psikim;
        this.isSensor = isSensor;
        this.isFast = isFast;
        this.record = record;
        gyroscope = new Gyroscope(context);
        gyroscope.setLisener(new Gyroscope.Lisener() {
            @Override
            public void onRotation(float xRot, float yRot, float zRot) {
                xAngle+= xRot;
                zAngle += zRot;
                if(isSensor){
                    if(starGame){
                        currentZrot = zRot;
                        starGame = false;
                    }
                    else{
                        if(zAngle >= currentZrot+thresHoldZ){
                            currentZrot = zAngle;
                            isTouch = 1;
                        }
                        else if(zAngle <= currentZrot-thresHoldZ){
                            currentZrot = zAngle;
                            isTouch = 2;
                        }
                    }
                }
                if(xAngle <= 0){
                    velocityRegular = false;
                }
                else{
                    velocityRegular = true;
                }
            }
        });

    }

    private  int functionXRandom(){
        int randomNumber = random.nextInt(5);
        return (randomNumber*xPixel)/4;

    }



    private int functionRandom(int index){
        if(index == 1 ){
            return (random.nextInt(height1 - low1) + low1) * (-1) ;

        }
        else if( index == 2 ){
            return (random.nextInt(height2 - low2) + low2)*(-1) ;
        }
        else if(index == 3){
            return (random.nextInt(height3 - low3) + low3)* (-1) ;


        }
        else
            return (random.nextInt(highCoin - lowCoin) + lowCoin)* (-1) ;


    }

    @Override
    public void run() {
        while(isPlaying){
            update();
            draw();
            sleep();

        }
    }


    public void update(){
        point++;
        int currentLane  = car.getxLane();
        if(isTouch == 1 ){
            isTouch = 0;
            if(currentLane > 0 ){
                car.setxLane(currentLane - xPixel/4);
            }
        }
        else if(isTouch == 2 ){
            isTouch = 0;
            if(currentLane < xPixel ){
                car.setxLane(currentLane + xPixel/4);
            }
        }
        // now is obsicales
        if(!isFast) {
            if(velocityRegular) {
                obstacle1.setY(obstacle1.getY() + velocity);
                obstacle2.setY(obstacle2.getY() + velocity);
                obstacle3.setY(obstacle3.getY() + velocity);
                coin.setY(coin.getY() + velocity);
            }
            else{
                obstacle1.setY(obstacle1.getY() + velocity*2);
                obstacle2.setY(obstacle2.getY() + velocity*2);
                obstacle3.setY(obstacle3.getY() + velocity*2);
                coin.setY(coin.getY() + velocity*2);
            }
        }
        else{
            if(velocityRegular) {
                obstacle1.setY(obstacle1.getY() + velocityFast);
                obstacle2.setY(obstacle2.getY() + velocityFast);
                obstacle3.setY(obstacle3.getY() + velocityFast);
                coin.setY(coin.getY() + velocityFast);
            }
            else{
                obstacle1.setY(obstacle1.getY() + velocityFast*2);
                obstacle2.setY(obstacle2.getY() + velocityFast*2);
                obstacle3.setY(obstacle3.getY() + velocityFast*2);
                coin.setY(coin.getY() + velocityFast*2);
            }
        }
        if(obstacle1.getY() >= yPixel){
            obstacle1.setY(functionRandom(1));
            obstacle1.setxLane(functionXRandom());

        }
        if(obstacle2.getY() >= yPixel){
            obstacle2.setY(functionRandom(2));
            obstacle2.setxLane(functionXRandom());
        }
        if(obstacle3.getY() >= yPixel){
            obstacle3.setY(functionRandom(3));
            obstacle3.setxLane(functionXRandom());
        }
        if(coin.getY() >= yPixel){
            coin.setY((functionRandom(4)));
            coin.setxLane(functionXRandom());
        }

        // now we check colitions

        Rect rectCar = car.getRect();
        Rect rectObstacle1 = obstacle1.getRect();
        Rect rectObstacle2 = obstacle2.getRect();
        Rect rectObstacle3 = obstacle3.getRect();
        Rect rectCoin = coin.getRect();

        if(Rect.intersects(rectCar, rectObstacle1)){
            lives--;
            obstacle1.setY(functionRandom(1));
            toastAndVibret("Damage");
            mediaPlayer.start();
            obstacle1.setxLane(functionXRandom());
        }
        if(Rect.intersects(rectCar, rectObstacle2)){
            lives--;
            obstacle2.setY(functionRandom(2));
            toastAndVibret("Damage");
            mediaPlayer.start();
            obstacle2.setxLane(functionXRandom());
        }
        if(Rect.intersects(rectCar, rectObstacle3)){
            lives--;
            obstacle3.setY(functionRandom(3));
            toastAndVibret("Damage");
            mediaPlayer.start();
            obstacle3.setxLane(functionXRandom());
        }
        if(Rect.intersects(rectCar, rectCoin)){
            coin.setY(functionRandom(4));
            point += 500;
            toastAndVibret(" 500 Bonus Points");
            coin.setxLane(functionXRandom());
        }

        if(lives == 0 ){
            isPlaying = false;
            Intent intent = new Intent(context, MainActivity.class);
            record.setPoints(point);

            Log.d(TAG, "update: " + record.toString());
            takeCareOfRecordAray();
            context.startActivity(intent);

        }
    }

    private void takeCareOfRecordAray() {
        //load current record array
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED, Context.MODE_PRIVATE);
        String jSon = sharedPreferences.getString(RECORDARRAY, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Record>>(){}.getType();
        ArrayList<Record> recordArrayList = gson.fromJson(jSon, type);
        if(recordArrayList == null){
            recordArrayList = new ArrayList<>();
        }
        if(recordArrayList.size() < 10 ){
            recordArrayList.add(record);
        }else{
            for (int i = 0 ; i < recordArrayList.size() ; i++){
                if(recordArrayList.get(i).getPoints() < record.getPoints()){
                    recordArrayList.remove(i);
                    recordArrayList.add(record);
                    break;
                }
            }
        }


        SharedPreferences sharedPreferences2 = context.getSharedPreferences(SHARED, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences2.edit();
        String jSonSave = gson.toJson(recordArrayList);
        editor.putString(RECORDARRAY, jSonSave);
        editor.apply();
    }

    public void draw(){
        if(getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();
           // canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(backround.getImage(), 0, 0, paint);
            canvas.drawBitmap(car.getImage(), car.getDrawX(), car.getDrawY(), paint);
            canvas.drawBitmap(obstacle1.getImage(), obstacle1.getDrawX(), obstacle1.getDrawY(), paint);
            canvas.drawBitmap(obstacle2.getImage(), obstacle2.getDrawX(), obstacle2.getDrawY(), paint);
            canvas.drawBitmap(obstacle3.getImage(), obstacle3.getDrawX(), obstacle3.getDrawY(), paint);
            canvas.drawBitmap(coin.getImage(), coin.getDrawX(), coin.getDrawY(), paint);
            for (int i =1 ; i <= lives ; i++){
                int xLeft = xPixel - i * heart.getWidth();
                int yTop = 0;
                canvas.drawBitmap(heart.getImage(), xLeft, yTop, paint);
            }
            if(maxRecord == -1)
                canvas.drawText("Your distance is: " + point, 10, 50, paintText);
            else
                canvas.drawText("Your distance is: " + point + "\nMax Score: " + maxRecord,  10, 50, paintText);

            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    public void sleep(){
        int sleepTime = (int)1000/60;
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void toastAndVibret(String text){
        handler.post(new Runnable(){
            @Override
            public void run() {
                Toast.makeText(context , text, Toast.LENGTH_SHORT).show();

            }
        });

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500);

    }

    public void resume(){
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
        gyroscope.register();
    }
    public void pause(){
        try {
            thread.join();
            isPlaying = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mediaPlayer.stop();
        mediaPlayer.release();
        gyroscope.unRegister();
    }

    public void inItHandler(){
        handler = new Handler();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!isSensor) {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                if (event.getX() < xPixel / 2) {
                    isTouch = 1;
                } else {
                    isTouch = 2;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public void saveRecordData(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MainActivity.SHARERECORDS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String putString;
        if(maxRecord == -1)
            putString = String.valueOf(point);
        else
            putString = psikim + "," + String.valueOf(point);

        editor.putString(MainActivity.TEXT, putString);
        editor.apply();

    }
}
