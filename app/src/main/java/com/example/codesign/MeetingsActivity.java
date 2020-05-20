package com.example.codesign;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.sql.Timestamp;

public class MeetingsActivity extends AppCompatActivity implements View.OnClickListener {

    Button editAndSave;
    Button back;
    EditText nameEdit,timeEdit,descEdit;
    TextView nameText,timeText,descText;
    Timestamp time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);

        editAndSave = findViewById(R.id.editAndSave);
        back = findViewById(R.id.TornarReunio);

        editAndSave.setOnClickListener(this);
        back.setOnClickListener(this);

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
                }
                break;

            case R.id.TornarReunio:
                //TORNAR PANTALLA ANTERIOR
                finish();
                break;
        }
    }

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

        //ES GUARDEN LES DADES DE LA REUNIO A LA BASE DE DADES
        saveOnDataBase();

    }

    private void edit() {
        nameEdit = findViewById(R.id.nameEdit);
        timeEdit = findViewById(R.id.timeEdit);
        descEdit = findViewById(R.id.descEdit);

        //Posa visible els edit Texts
        nameEdit.setVisibility(View.VISIBLE);
        timeEdit.setVisibility(View.VISIBLE);
        descEdit.setVisibility(View.VISIBLE);

    }

    private void saveOnDataBase() {
    }
}
