package com.example.codesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.codesign.Projecte.ProjectActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProjectListFragment.ProjecteListener, View.OnClickListener {

    private static final int RC_SIGN_IN = 5;

    ConnectivityManager cm;
    NetworkInfo activeNetwork;
    Button npButton;
    Button iButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Autenticació a través de FirebaseUI
        firebaseUIAuth();

        npButton = findViewById(R.id.NouProjecte);
        iButton = findViewById(R.id.Invitacions);

        npButton.setOnClickListener(this);
        iButton.setOnClickListener(this);

        cm =(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(cm != null)
        {
            activeNetwork = cm.getActiveNetworkInfo();

            new MainActivity.CheckConnectivityTask().execute();
        }

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

            case R.id.settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;

            case R.id.exit:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                finish();
                            }
                        });
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

    private class CheckConnectivityTask extends AsyncTask<Activity, Void, String> {

        @Override
        protected String doInBackground(Activity... activity) {

            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting())
            {
                if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                {
                    return (String) getText(R.string.wifiCon);
                }
                else if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                {
                    return (String) getText(R.string.mobCon);
                }
            }
            return (String) getText(R.string.noCon);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast toast = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void firebaseUIAuth(){
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.logo)
                        .build(), RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if(resultCode == RESULT_OK){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(MainActivity.this, "Authentication success", Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(MainActivity.this, "Authentication failed", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
