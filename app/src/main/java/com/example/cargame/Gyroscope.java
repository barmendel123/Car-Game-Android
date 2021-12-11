package com.example.cargame;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Gyroscope {
    public interface Lisener {void onRotation(float xRot, float yRot, float zRot);}
    private Lisener lisener;
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;

    public void setLisener(Lisener lisener) {
        this.lisener = lisener;
    }
    public Gyroscope(Context context  ){
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        this.sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(lisener != null){
                    lisener.onRotation(event.values[0], event.values[1], event.values[2]);

                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    public void register(){
        sensorManager.registerListener(sensorEventListener,sensor, sensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unRegister(){
        sensorManager.unregisterListener(sensorEventListener);
    }
}
