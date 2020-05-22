package com.example.codesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.codesign.Classes.Meeting;
import com.example.codesign.Settings.SettingsActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.GeoPoint;

import java.sql.Timestamp;

public class MeetingsActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMapClickListener {

    Button editAndSave, back;
    EditText nameEdit,timeEdit,descEdit;
    TextView nameText,timeText,descText;
    Timestamp time;
    private GoogleMap mMap;
    private GeoPoint location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);

        editAndSave = findViewById(R.id.editAndSave);
        back = findViewById(R.id.TornarReunio);

        editAndSave.setOnClickListener(this);
        back.setOnClickListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.FrgMap);
        if (mapFragment != null) {
            mapFragment.getMapAsync( this);
        }

        //SI EXISTEIXEN DADES PREVIES EXECUTA FUNCIO
        //getMeetingFromDataBase();
    }

    //IMPLEMENTACIONS DE MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

            case R.id.exit:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                finish();
                            }
                        });
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
        if(!timeEdit.getText().toString().equals("")) {
            time = Timestamp.valueOf(timeEdit.getText().toString());
        }
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
        Meeting meeting = new Meeting(nameEdit.getText().toString(), descEdit.getText().toString(), time, location);
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

        mMap.setOnMapClickListener(this);
        //TODO
    }

    @Override
    public void onMapClick(LatLng latLng) {
        // Creating a marker
        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(latLng);

        markerOptions.title(latLng.latitude + " : " + latLng.longitude);

        location = new GeoPoint(latLng.latitude, latLng.longitude);

        mMap.clear();

        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        mMap.addMarker(markerOptions);
    }
}
