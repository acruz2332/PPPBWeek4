package com.ppab.pppbweek4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private Sensor proximitySensor, tempSensor, presSensor, humSensor;

    private TextView tvlightSensor, tvtempSensor, tvPresSensor, tvHumSensor;
    private TextView tvproximitySensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        StringBuilder sensorText = new StringBuilder();

        for (Sensor currentSensor : sensorList){
            sensorText.append(currentSensor.getName()).append(System.getProperty("line.separator"));
        }

//        TextView sensorTextView = findViewById(R.id.sensor_list);
//        sensorTextView.setText(sensorText);

        tvlightSensor = findViewById(R.id.label_light);
        tvproximitySensor = findViewById(R.id.label_proximity);
        tvtempSensor = findViewById(R.id.label_temp);
        tvPresSensor = findViewById(R.id.label_pressure);
        tvHumSensor = findViewById(R.id.label_humidity);

        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        presSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        humSensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        String sensor_error = "No sensor";

        if (lightSensor == null){
            tvlightSensor.setText(sensor_error);
        }
        if (proximitySensor == null){
            tvproximitySensor.setText(sensor_error);
        }
        if (tempSensor == null){
            tvtempSensor.setText(sensor_error);
        }
        if (presSensor == null){
            tvPresSensor.setText(sensor_error);
        }
        if (humSensor == null){
            tvHumSensor.setText(sensor_error);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (proximitySensor != null) {
            sensorManager.registerListener(this, proximitySensor, sensorManager.SENSOR_DELAY_NORMAL);
        }
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, sensorManager.SENSOR_DELAY_NORMAL);
        }
        if (tempSensor != null) {
            sensorManager.registerListener(this, tempSensor, sensorManager.SENSOR_DELAY_NORMAL);
        }
        if (presSensor != null) {
            sensorManager.registerListener(this, presSensor, sensorManager.SENSOR_DELAY_NORMAL);
        }
        if (humSensor != null) {
            sensorManager.registerListener(this, humSensor, sensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        float currentValue = event.values[0];

        switch (sensorType){
            case Sensor.TYPE_LIGHT:
                    tvlightSensor.setText(String.format("Light sensor : %1$.2f", currentValue));
                    changebackgroundcolor(currentValue);
                break;
            case Sensor.TYPE_PROXIMITY:
                    tvproximitySensor.setText(String.format("Proximity sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                tvtempSensor.setText(String.format("Temperature sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_PRESSURE:
                tvPresSensor.setText(String.format("Pressure sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                tvHumSensor.setText(String.format("Humidity sensor : %1$.2f", currentValue));
                break;
            default:
        }
    }

    public void changebackgroundcolor (float currentValue){
        ConstraintLayout layout = findViewById(R.id.layout_background);
        if(currentValue <= 40000 && currentValue >= 20000) layout.setBackgroundColor(Color.RED);
        else if (currentValue < 20000 && currentValue >= 10) layout.setBackgroundColor(Color.BLUE);
        if (currentValue == 0.0) layout.setBackgroundColor(Color.WHITE);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}