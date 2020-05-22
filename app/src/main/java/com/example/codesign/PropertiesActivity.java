package com.example.codesign;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codesign.Classes.Projectes;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class PropertiesActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView participantsList;
    private FirebaseFirestore mFirestore;
    private List<Projectes> list;
    private ProjectesListAdapter participantsListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties);

        TextView titleProperties= findViewById(R.id.ProjectTitle);
        Button back = findViewById(R.id.tornarProperties);

        back.setOnClickListener(this);

        //Cargar el proyecto, que se lo pasaran
        //titleProperties.setText(project.getProjectName);
        //firebaseListParticipants()
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.tornarProperties:
                finish();
                break;
        }
    }

    private void firebaseListParticipants(){
        list = new ArrayList<>();
        participantsListAdapter = new ProjectesListAdapter(getApplicationContext(), list);

        //participantsList = findViewById(R.id.participants_list);
        participantsList.setHasFixedSize(true);
        participantsList.setLayoutManager(new LinearLayoutManager(this));
        participantsList.setAdapter(participantsListAdapter);

        //carregar
    }

}

