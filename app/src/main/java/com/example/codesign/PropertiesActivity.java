package com.example.codesign;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codesign.Classes.Projectes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class PropertiesActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "FireLog";
    private FirebaseFirestore mFirestore;
    private EditText editText;
    private TextView llistaPart;
    private TextView titleProperties;
    private List<String> partList;


    private String idProjecte;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties);

        titleProperties = findViewById(R.id.ProjectTitle);
        llistaPart = findViewById(R.id.participantsList);
        editText = findViewById(R.id.newParticipants);
        Button back = findViewById(R.id.tornarProperties);
        Button pButton = findViewById(R.id.afegirPart);

        back.setOnClickListener(this);
        pButton.setOnClickListener(this);

        mFirestore = FirebaseFirestore.getInstance();

        Intent dataIntent = getIntent();
        idProjecte = dataIntent.getStringExtra(getString(R.string.id_key));

        llegirDades();

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.afegirPart:
                //COMPROVAR SI EDITTEXT BUIT
                if(isEmpty(editText)){
                    break;
                }

                //AFEGIR PARTICIPANT AL PROJECTE
                afegirParticipant();
                Toast.makeText(this, R.string.toastAdd, Toast.LENGTH_SHORT).show();
                editText.setText("");
                break;

            case R.id.tornarProperties:
                finish();
                break;
        }
    }

    public boolean isEmpty(EditText editText){
        String strUserName = editText.getText().toString();

        if(TextUtils.isEmpty(strUserName)) {
            editText.setError(getString(R.string.empty));
            return true;
        }
        return false;
    }

    private void llegirDades(){
        DocumentReference docRef = mFirestore.collection("projectes").document(idProjecte);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Projectes projecte = documentSnapshot.toObject(Projectes.class);
                mostrarTitol(projecte);
                llistarParticipants(projecte);
            }
        });
    }

    private void mostrarTitol(Projectes projecte){
        String projectName = projecte.getProjectName();
        titleProperties.setText(projectName);
    }

    private void llistarParticipants(Projectes projecte){
        partList = projecte.getParticipants();
        String textPart = "";
        for(int i = 0; i < partList.size(); i++){
            if(i == (partList.size() - 1)){
                textPart = textPart + partList.get(i);
            }else{
                textPart = (textPart + partList.get(i) + "\n\n");
            }
        }
        llistaPart.setText(textPart);
    }

    private void afegirParticipant(){
        partList.add(editText.getText().toString());
        DocumentReference docRef = mFirestore.collection("projectes").document(idProjecte);
        docRef.update("participants", partList);
        llegirDades();
    }

}

