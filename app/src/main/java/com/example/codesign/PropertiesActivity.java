package com.example.codesign;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.codesign.Classes.Projectes;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

public class PropertiesActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "FireLog";
    private FirebaseFirestore mFirestore;
    private EditText editText;
    private TextView titleProperties;
    private List<String> partList;

    private String idProjecte;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties);

        titleProperties = findViewById(R.id.ProjectTitle);
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
        DocumentReference docRef = mFirestore.collection(getString(R.string.ColProj)).document(idProjecte);
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, partList);
        ListView lv = (ListView) findViewById(R.id.partList);
        lv.setAdapter(adapter);
    }

    private void afegirParticipant(){
        partList.add(editText.getText().toString());
        DocumentReference docRef = mFirestore.collection(getString(R.string.ColProj)).document(idProjecte);
        docRef.update("participants", partList);
        llegirDades();
    }

}

