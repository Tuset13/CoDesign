package com.example.codesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText mEditText;
    EditText pEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditText = findViewById(R.id.mail);
        pEditText = findViewById(R.id.pass);
        Button buttonLogin = findViewById(R.id.Login);

        buttonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        /*//COMPROVAR SI EDITTEXTS BUITS
        if(isEmpty(mEditText) || isEmpty(pEditText)){
            return;
        }*/
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
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
