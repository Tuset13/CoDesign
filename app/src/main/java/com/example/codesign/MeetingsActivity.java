package com.example.codesign;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.codesign.Classes.Meeting;
import com.example.codesign.Classes.Projectes;
import com.example.codesign.Settings.SettingsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MeetingsActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private static final String TAG = "FireLog";

    Button editAndSave, back;
    EditText nameEdit,timeEdit,descEdit;
    TextView nameText,timeText,descText;
    com.google.firebase.Timestamp time;
    private GoogleMap mMap;
    private GeoPoint location;

    private FirebaseFirestore mFirestore;
    private String idProjecte;

    private Meeting meeting;

    private Projectes projecteReunio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);

        editAndSave = findViewById(R.id.editAndSave);
        back = findViewById(R.id.TornarReunio);

        editAndSave.setOnClickListener(this);
        back.setOnClickListener(this);

        //Get the edit texts
        nameEdit = findViewById(R.id.nameEdit);
        timeEdit = findViewById(R.id.timeEdit);
        descEdit = findViewById(R.id.descEdit);
        //Get the TextsViews
        nameText = findViewById(R.id.nameText);
        timeText = findViewById(R.id.timeText);
        descText = findViewById(R.id.descText);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.FrgMap);
        if (mapFragment != null) {
            mapFragment.getMapAsync( this);
        }

        mFirestore = FirebaseFirestore.getInstance();

        Intent dataIntent = getIntent();
        idProjecte = dataIntent.getStringExtra(getString(R.string.id_key));

        //AGAFEM DADES PREVIES DE LA BASE DE DADES
        getMeetingFromDataBase();

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
                finish();
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
                    try {
                        save();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
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
    private void save() throws ParseException {

        //ACTUALITZEM ELS TEXTS
        nameText.setText(nameEdit.getText());
        timeText.setText(timeEdit.getText());
        descText.setText(descEdit.getText());
        if(!timeEdit.getText().toString().equals("")) {
            SimpleDateFormat format = new SimpleDateFormat(getString(R.string.dateFormat));
            Date date = format.parse(timeEdit.getText().toString());
            time = new Timestamp(date);
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

        //CREEM LA REUNIO
        meeting = new Meeting(nameEdit.getText().toString(), descEdit.getText().toString(), time, location);
        //GUARDAR A LA BASE DE DADES
        mFirestore.collection(getString(R.string.ColMeet)).add(meeting).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                DocumentReference docRef = task.getResult();
                mFirestore.collection(getString(R.string.ColProj)).document(idProjecte).update("reunio", docRef)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d(TAG, "Reunió updated successfully");
                            }
                        });
            }
        });
    }

    //FUNCIO PER RECUPERAR DE LA BASE DE DADES
    private void getMeetingFromDataBase() {

        mFirestore.collection(getString(R.string.ColProj)).document(idProjecte)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    //AGAFEM LA REFERENCIA A LA REUNIO DEL PROJECTE I LA BUSQUEM DINS LA BASE DE DADES
                    DocumentSnapshot documentSnapshot = task.getResult();
                    projecteReunio = documentSnapshot.toObject(Projectes.class);

                    agafarReunio();
                }
            }
        });
    }

    private void agafarReunio(){

        if(projecteReunio.getReunio() != null){
            DocumentReference docRef2 = projecteReunio.getReunio();

            docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        meeting = task.getResult().toObject(Meeting.class);

                        //SI EXISTEIXEN AQUESTES DADES, LES REPRESENTEM
                        if(meeting != null){
                            showMeeting();
                        }

                    }else{
                        Log.d(TAG, "No existing Reunion");
                    }
                }
            });
        }
    }

    private void showMeeting(){
        nameText.setText(meeting.getTitle());
        timeText.setText(meeting.getTime().toDate().toString());
        descText.setText(meeting.getDescription());
        location = meeting.getLocation();
        addMapMarker();
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

        markerOptions.title(getString(R.string.Marker));

        location = new GeoPoint(latLng.latitude, latLng.longitude);

        mMap.clear();

        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        mMap.addMarker(markerOptions);
    }

    private void addMapMarker(){

        double lat = location.getLatitude();
        double lon = location.getLongitude();

        mMap.addMarker(new MarkerOptions()
        .position(new LatLng(lat,lon))
        .title(getString(R.string.Marker)));
    }
}
