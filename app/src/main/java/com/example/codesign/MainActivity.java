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

        //INSTANCIEM EL FRAGMENT AMB LA LLISTA DE PROJECTES
        ProjectListFragment frgList = (ProjectListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.FrgListOld);
        //INCORPOREM UN LISTENER PER PODER CANVIAR D'ACTIVITY AL SELECCIONAR UN PROJECTE
        frgList.setProjecteListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //UTILITZEM EL OPTIONSMENU PER A DONAR L'OPCIO DE TANCAR LA SESSIO
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
                //REDIRECCIO A LA PANTALLA DE CREACIO DE PROJECTES
                Intent intent = new Intent(MainActivity.this, NewProjectActivity.class);
                startActivity(intent);
                break;

            case R.id.Invitacions:
                //REDIRECCIO A LA PANTALLA AMB LA LLISTA D'INVITACIONS
                Intent intent1 = new Intent(MainActivity.this, InvitationsActivity.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void onProjecteSeleccionat(String id) {
        //REDIRECCIO A LA PANTALLA DEL PROJECTE SELECCIONAT I ENVIEM LA ID DEL PROJECTE
        Intent i = new Intent(this, ProjectActivity.class);
        i.putExtra(getString(R.string.id_key), id);
        startActivity(i);
    }
}
