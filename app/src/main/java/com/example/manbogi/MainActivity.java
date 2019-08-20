package com.example.manbogi;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private Sensor stepCountSensor;
    TextView tvStepCount;
    boolean isRunning =false;
    SensorEvent tmpEvent = null;
    float Minus = 0.f;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvStepCount = (TextView)findViewById(R.id.tvStepCount);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(stepCountSensor == null) {
            Toast.makeText(this, "No Step Detect Sensor", Toast.LENGTH_SHORT).show();
        }



    }


    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, stepCountSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected  void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType()==Sensor.TYPE_STEP_COUNTER && isRunning == true){
            if(tmpEvent == null)
                tmpEvent = event;

            tvStepCount.setText("kcal : "+String.valueOf((event.values[0] - Minus)/30));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

    public void onClick(View view) {
        switch  (view.getId()){
            case R.id.startbtn:
                isRunning = true;
                Toast.makeText(getApplicationContext(), "select Start", Toast.LENGTH_LONG).show();
                break;

            case R.id.endbtn:
                isRunning = false;
                Toast.makeText(getApplicationContext(), "select end", Toast.LENGTH_LONG).show();
                tvStepCount.setText("kcal : "+ 0);
                Minus = tmpEvent.values[0];
                break;
        }

    }
}