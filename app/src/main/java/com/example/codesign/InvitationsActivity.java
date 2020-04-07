package com.example.codesign;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class InvitationsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitations);

        Button buttonTornar = findViewById(R.id.tornar);

        buttonTornar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
                finish();
    }
}
