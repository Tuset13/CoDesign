package com.example.codesign;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.codesign.DDBB.ProjectesSQLiteHelper;


import java.util.ArrayList;

public class NewProjectActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<String> partList = new ArrayList<>();

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
                //GUARDAR PROJECTE NOU EN DB
                ProjectesSQLiteHelper usdbh = new ProjectesSQLiteHelper(this, "Projectes",null, 1);
                SQLiteDatabase db = usdbh.getWritableDatabase();
                if(db != null){
                    insertinDB(db, String.valueOf(editNom.getText()), admin.isChecked());
                }
                db.close();
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

    private void insertinDB(SQLiteDatabase db, String projName, boolean admin){

        ContentValues newRegister = new ContentValues();

        //PASSEM L'ARRAY DE PARTICIPANTS A UN SOL STRING
        StringBuilder str = new StringBuilder();
        for(int i=0;i<partList.size();i++) {
            str.append(partList.get(i) + "|");
        }
        String participants = str.toString();

        //INTRODUIM ELS VALORS A LA DB
        newRegister.put("projectName", projName);
        newRegister.put("participants", participants);
        newRegister.put("administrator", admin);
        db.insert("Projectes", null, newRegister);
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
