package com.example.codesign;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    Integer color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        RadioGroup groupColor = (RadioGroup)findViewById(R.id.group_color);
        Button save = findViewById(R.id.save);

        groupColor.setOnCheckedChangeListener(this);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        SharedPreferences mypreferences = getSharedPreferences("settings",MODE_PRIVATE);

        SharedPreferences.Editor editor =  mypreferences.edit();

        editor.putInt("color", color);
        finish();

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

        switch (checkedId)
        {
            case R.id.Black:
                color = (R.color.black);

            case R.id.Red:
                color = (R.color.red);

            case R.id.Green:
                color = (R.color.green);

            case R.id.Blue:
                color = (R.color.blue);
        }
    }
}
