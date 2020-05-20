package com.example.codesign;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import com.example.codesign.Classes.Meeting;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.sql.Timestamp;

public class MeetingsActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    Button editAndSave, back;
    EditText nameEdit,timeEdit,descEdit;
    TextView nameText,timeText,descText;
    Timestamp time;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);

        editAndSave = findViewById(R.id.editAndSave);
        back = findViewById(R.id.TornarReunio);

        editAndSave.setOnClickListener(this);
        back.setOnClickListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.FrgMap);
        mapFragment.getMapAsync( this);

        //SI EXISTEIXEN DADES PREVIES EXECUTA FUNCIO
        //getMeetingFromDataBase();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editAndSave:
                if(editAndSave.getText() == getString(R.string.EditMeeting)) {
                    editAndSave.setText(R.string.SaveMeeting);
                    edit();
                } else {
                    editAndSave.setText(R.string.EditMeeting);
                    save();
                    saveOnDataBase();
                }
                break;
            case R.id.TornarReunio:
                //TORNAR PANTALLA ANTERIOR
                finish();
                break;
        }
    }

    //FEM CANVI ALS TEXTS I GUARDEM
    private void save() {
        //Get the edit texts
        nameEdit = findViewById(R.id.nameEdit);
        timeEdit = findViewById(R.id.timeEdit);
        descEdit = findViewById(R.id.descEdit);
        //Get the TextsViews
        nameText = findViewById(R.id.nameText);
        timeText = findViewById(R.id.timeText);
        descText = findViewById(R.id.descText);

        //ACTUALITZEM ELS TEXTS
        nameText.setText(nameEdit.getText());
        timeText.setText(timeEdit.getText());
        descText.setText(descEdit.getText());

        time = Timestamp.valueOf(timeEdit.getText().toString());

        nameEdit.setVisibility(View.INVISIBLE);
        timeEdit.setVisibility(View.INVISIBLE);
        descEdit.setVisibility(View.INVISIBLE);
    }

    //ACTIVEM EL MODE EDICIO
    private void edit() {
        nameEdit = findViewById(R.id.nameEdit);
        timeEdit = findViewById(R.id.timeEdit);
        descEdit = findViewById(R.id.descEdit);

        //Posa visible els edit Texts
        nameEdit.setVisibility(View.VISIBLE);
        timeEdit.setVisibility(View.VISIBLE);
        descEdit.setVisibility(View.VISIBLE);
    }

    //FUNCIO PER GUARDAR A LA BASE DE DADES
    private void saveOnDataBase() {

        //CREEM UNA REUNIO
        Meeting meeting = new Meeting(nameEdit.getText().toString(), descEdit.getText().toString(), time);
        //GUARDAR A LA BASE DE DADES
    }

    //FUNCIO PER RECUPERAR DE LA BASE DE DADES
    private void getMeetingFromDataBase() {
        //HEM DE RECUPERAR LES DADES DE LA REUNIO
    }

    //FUNCIONS DEL MAPA
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //TODO
    }
}
