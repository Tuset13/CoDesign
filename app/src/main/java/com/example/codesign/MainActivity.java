package com.example.codesign;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.codesign.Classes.Projectes;
import com.example.codesign.Settings.SettingsActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 5;
    private NetworkReceiver receiver = new NetworkReceiver();
    private static final String TAG = "FireLog";

    Button npButton;
    Button iButton;
    String ConType;

    private RecyclerView mMainList;
    private FirebaseFirestore mFirestore;
    private List<Projectes> projectesList;
    private ProjectesListAdapter projectesListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Registra el BroadcastReceiver
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkReceiver();
        this.registerReceiver(receiver, filter);

        //Autenticació a través de FirebaseUI
        firebaseUIAuth();

        //Llistar les dades al RecyclerView
        firebaseListData();

        npButton = findViewById(R.id.NouProjecte);
        iButton = findViewById(R.id.Invitacions);

        npButton.setOnClickListener(this);
        iButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //UTILITZEM EL OPTIONS MENU PER A DONAR L'OPCIO DE TANCAR LA SESSIO
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

    private void firebaseListData(){
        projectesList = new ArrayList<>();
        projectesListAdapter = new ProjectesListAdapter(getApplicationContext(), projectesList);

        mMainList = findViewById(R.id.main_list);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(projectesListAdapter);

        mFirestore = FirebaseFirestore.getInstance();

        mFirestore.collection("projectes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.d(TAG, "Error : " + e.getMessage());
                }

                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){

                    if(doc.getType() == DocumentChange.Type.ADDED){

                        String project_id = doc.getDocument().getId();

                        Projectes projectes = doc.getDocument().toObject(Projectes.class).withId(project_id);
                        projectesList.add(projectes);

                        projectesListAdapter.notifyDataSetChanged();
                    }

                }
            }
        });
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

    //TEMAS DEL RECEIVER
    public class NetworkReceiver extends BroadcastReceiver {

        NetworkInfo activeNetwork;

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager conn = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);

            activeNetwork = conn.getActiveNetworkInfo();
            new CheckConnectivityTask().execute();

        }

        private class CheckConnectivityTask extends AsyncTask<Activity, Void, String> {

            private final String WIFI = "Wi-Fi";
            private final String ANY = "Any";

            @Override
            protected String doInBackground(Activity... activity) {

                if (WIFI.equals(ConType) && activeNetwork != null
                        && activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {

                    //MAKE SOMETHING
                    return "WIFI";


                } else if (ANY.equals(ConType) && activeNetwork != null) {

                    //MAKE SOMETHING
                    return "OTRA";

                } else {

                    //MAKE SOMETHING
                    return "FAIL";

                }
            }

            @Override
            protected void onPostExecute(String result) {
                Toast toast = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // UNregistra el receiver
        if (receiver != null) {
            this.unregisterReceiver(receiver);
        }
    }

    @Override
    public void onStart () {
        super.onStart();

        SharedPreferences sharedPrefs = getSharedPreferences("settings",MODE_PRIVATE);

        ConType = sharedPrefs.getString("ConType", "Wi-Fi");
    }
}
