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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.codesign.Classes.Projectes;
import com.example.codesign.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.List;

public class NewNoteActivity extends AppCompatActivity implements View.OnClickListener, SensorEventListener {

    private SensorManager sensorManager;
    private EditText editNote;
    private TextView textAcc;
    private long lastUpdate;
    private List<String> notesList;
    private String result;

    private static final String TAG = "FireLog";
    private FirebaseFirestore mFirestore;
    private String idProjecte;

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

        //CREEM ELS LISTENERS PER AL ACCELEROMETRE
        createListeners();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.acceptarNote:

                //RETORNEM EL STRING RESULTANT COM A VALOR
                result = String.valueOf(editNote.getText());

                operarNota();

                break;
            case R.id.tornarNote:
                finish();
                break;
        }
    }

    private void createListeners() {
        if (sensorManager != null) {

            Sensor sensorAcc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            //COMPROVEM QUE DISPOSA D'UN ACCELEROMETRE
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
        // COMPROVEM QUE HI HA HAGUT UN MOVIMENT CONSIDERABLE
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = System.currentTimeMillis();
        if (accelationSquareRoot >= 2) {
            // ESTABLIM UN TEMPS DE MARGE ENTRE CADA POSSIBLE INTERACCIO
            if (actualTime - lastUpdate < 200) {
                return;
            }

            lastUpdate = actualTime;
            // BORREM EL TEXT
            editNote.setText("");
            Toast.makeText(this, R.string.sacsat, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    private void operarNota(){
        mFirestore = FirebaseFirestore.getInstance();

        Intent dataIntent = getIntent();
        idProjecte = dataIntent.getStringExtra(getString(R.string.id_key));

        DocumentReference docRef = mFirestore.collection(getString(R.string.ColProj)).document(idProjecte);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Projectes projecte = documentSnapshot.toObject(Projectes.class);
                guardarNota(projecte);
            }
        });
    }

    private void guardarNota(Projectes projecte){
        notesList = projecte.getNotes();
        notesList.add(result);

        DocumentReference docRef = mFirestore.collection(getString(R.string.ColProj)).document(idProjecte);
        docRef.update("notes", notesList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent=new Intent();
                intent.putExtra("result", result);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
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