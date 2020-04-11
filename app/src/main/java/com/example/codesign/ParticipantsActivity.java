package com.example.codesign;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ParticipantsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants);

        Button pButton = findViewById(R.id.afegirPart);
        Button bButton = findViewById(R.id.tornarProj);

        pButton.setOnClickListener(this);
        bButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.afegirPart:
                Toast.makeText(this, R.string.toastAdd, Toast.LENGTH_SHORT).show();
                break;
            case R.id.tornarProj:
                finish();
                break;
        }
    }
}