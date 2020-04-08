package com.example.codesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ProjectActivity extends AppCompatActivity implements View.OnClickListener  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        Button eButton = findViewById(R.id.edicio_pissarra);
        Button anButton = findViewById(R.id.afegir_nota);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.project_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.exit:
                finish();
                return true;

            case R.id.participants:
                //TODO

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edicio_pissarra:
                //TODO
                break;

            case R.id.afegir_nota:
                //TODO
                break;
        }
    }
}
