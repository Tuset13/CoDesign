package com.example.codesign.Settings;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import com.example.codesign.R;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    String color_code, color, conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        RadioGroup groupColor = (RadioGroup)findViewById(R.id.group_color);
        RadioGroup groupCons = (RadioGroup)findViewById(R.id.connect_type);
        Button save = findViewById(R.id.save);

        groupColor.setOnCheckedChangeListener(this);
        groupCons.setOnCheckedChangeListener(this);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        SharedPreferences mypreferences = getSharedPreferences("settings",MODE_PRIVATE);

        SharedPreferences.Editor editor =  mypreferences.edit();

        editor.putString("color_code", color_code);
        editor.putString("color", color);
        editor.putString("ConType",conexion);
        editor.apply();
        finish();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

        switch (checkedId)
        {
            case R.id.Red:
                color_code = "#FF0000";
                color = getString(R.string.color_red);
                break;

            case R.id.Green:
                color_code = "#008000";
                color = getString(R.string.color_green);
                break;

            case R.id.Blue:
                color_code = "#0000FF";
                color = getString(R.string.color_blue);
                break;

            case R.id.wifi:
                conexion ="Wi-Fi";
                break;

            case R.id.any:
                conexion ="Any";
                break;
        }
    }
}
