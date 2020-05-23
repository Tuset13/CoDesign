package com.example.codesign;

import android.content.ContentValues;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.codesign.Classes.Projectes;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;


public class NewProjectActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<String> partList = new ArrayList<>();
    private FirebaseFirestore mFirestore;
    private ArrayList<String> notesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newproject);

        Button buttonCrear = findViewById(R.id.crear);
        Button buttonTornar = findViewById(R.id.tornar);
        Button buttonAdd = findViewById(R.id.addPart);

        buttonTornar.setOnClickListener(this);
        buttonCrear.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        EditText editNom = findViewById(R.id.editText1);
        EditText editParticipants = findViewById(R.id.editText2);
        CheckBox admin = findViewById(R.id.admin);

        switch(view.getId()){
            case R.id.crear:
                //COMPROVAR SI EDITTEXT BUIT
                if(isEmpty(editNom)){
                    break;
                }
                //GUARDAR PROJECTE NOU EN DB I TORNAR A LA LLISTA DE PROJECTES
                mFirestore = FirebaseFirestore.getInstance();
                insertinDB(mFirestore, String.valueOf(editNom.getText()), admin.isChecked());
                finish();
                break;

            case R.id.tornar:
                finish();
                break;

            case R.id.addPart:
                //COMPROVAR SI EDITTEXT BUIT
                if(isEmpty(editParticipants)){
                    break;
                }
                //GUARDAR PARTICIPANT NOU EN ARRAY
                String newPart = String.valueOf(editParticipants.getText());
                partList.add(newPart);
                editParticipants.setText("");
                Toast.makeText(this, R.string.toastAdd, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void insertinDB(FirebaseFirestore mFirestore, String projName, boolean admin){

        //INTRODUIM ELS VALORS A LA DB

        Projectes newProject = new Projectes(admin, partList, notesList, projName, null);

        mFirestore.collection("projectes").add(newProject);

    }

    public boolean isEmpty(EditText editText){
        String strUserName = editText.getText().toString();

        if(TextUtils.isEmpty(strUserName)) {
            editText.setError(getString(R.string.empty));
            return true;
        }
        return false;
    }
}
