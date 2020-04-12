package com.example.codesign;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ParticipantsActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants);

        Button pButton = findViewById(R.id.afegirPart);
        Button bButton = findViewById(R.id.tornarProj);
        editText = findViewById(R.id.newParticipants);
        pButton.setOnClickListener(this);
        bButton.setOnClickListener(this);
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
                //SEGÜENT ENTREGA DE FORMA REAL
                Toast.makeText(this, R.string.toastAdd, Toast.LENGTH_SHORT).show();
                editText.setText("");
                break;
            case R.id.tornarProj:
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
}
