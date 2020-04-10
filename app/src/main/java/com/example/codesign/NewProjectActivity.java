package com.example.codesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NewProjectActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newproject);

        Button buttonCrear = findViewById(R.id.crear);
        Button buttonTornar = findViewById(R.id.tornar);

        buttonTornar.setOnClickListener(this);
        buttonCrear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {


        EditText editNom = findViewById(R.id.editText1);
        EditText editParticipants = findViewById(R.id.editText2);


        switch(view.getId()){
            case R.id.crear:
                Intent intent = new Intent(NewProjectActivity.this, ProjectActivity.class);
                startActivity(intent);
                break;

            case R.id.tornar:
                finish();
                break;
        }
    }
}
