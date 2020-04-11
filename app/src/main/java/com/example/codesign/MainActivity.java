package com.example.codesign;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.codesign.Projecte.ProjectActivity;

public class MainActivity extends AppCompatActivity implements ProjectListFragment.ProjecteListener, View.OnClickListener {

    Button npButton;
    Button iButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        npButton = findViewById(R.id.NouProjecte);
        iButton = findViewById(R.id.Invitacions);

        npButton.setOnClickListener(this);
        iButton.setOnClickListener(this);

        ProjectListFragment frgList = (ProjectListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.FrgListOld);
        frgList.setProjecteListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

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
            case R.id.NouProjecte:
                Intent intent = new Intent(MainActivity.this, NewProjectActivity.class);
                startActivity(intent);
                break;

            case R.id.Invitacions:
                Intent intent1 = new Intent(MainActivity.this, InvitationsActivity.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void onProjecteSeleccionat(String id) {
        Intent i = new Intent(this, ProjectActivity.class);
        i.putExtra(getString(R.string.id_key), id);
        startActivity(i);
    }
}
