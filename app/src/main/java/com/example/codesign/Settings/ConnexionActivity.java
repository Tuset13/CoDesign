package com.example.codesign.Settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.codesign.R;

public class ConnexionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alertconexion);

        Intent dataIntent = getIntent();
        boolean isOn = dataIntent.getBooleanExtra(getString(R.string.isConnected),false);
        String result = dataIntent.getStringExtra(getString(R.string.ConRes));

        final AlertDialog.Builder connexionDialog = new AlertDialog.Builder(this);
        connexionDialog.setTitle(R.string.NetworkIssues);
        connexionDialog.setMessage(result);
        if(isOn) {
            connexionDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SharedPreferences sharedPrefs = getSharedPreferences("settings", MODE_PRIVATE);
                    SharedPreferences.Editor editor =  sharedPrefs.edit();
                    editor.putString("ConType", "Any");
                    editor.apply();
                    finish();
                }
            });
            connexionDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finishAffinity();
                    System.exit(0);
                }
            });
        } else {
            connexionDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finishAffinity();
                    System.exit(0);
                }
            });
        }
        connexionDialog.show();
    }
}
