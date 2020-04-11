package com.example.codesign.Projecte;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.codesign.R;

public class NewNoteActivity extends AppCompatActivity implements View.OnClickListener, SensorEventListener {

    private SensorManager sensorManager;
    private EditText editNote;
    private TextView textAcc;
    private long lastUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newnote);

        Button tButton = findViewById(R.id.tornarNote);
        Button aButton = findViewById(R.id.acceptarNote);
        editNote = findViewById(R.id.editNote);
        textAcc = findViewById(R.id.textAcc);

        tButton.setOnClickListener(this);
        aButton.setOnClickListener(this);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        createListeners();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.acceptarNote:
                String result = String.valueOf(editNote.getText());
                Intent intent=new Intent();
                intent.putExtra("result", result);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.tornarNote:
                finish();
                break;
        }
    }

    private void createListeners() {
        if (sensorManager != null) {

            Sensor sensorAcc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            if (sensorAcc != null) {
                sensorManager.registerListener(this, sensorAcc, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                textAcc.setText(R.string.changeText);
            }
            lastUpdate = System.currentTimeMillis();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = System.currentTimeMillis();
        if (accelationSquareRoot >= 2) {
            if (actualTime - lastUpdate < 200) {
                return;
            }

            lastUpdate = actualTime;

            editNote.setText("");
            Toast.makeText(this, R.string.sacsat, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    protected void onPause() {
        //Unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        //Register the listener again
        super.onResume();
        createListeners();
    }
}