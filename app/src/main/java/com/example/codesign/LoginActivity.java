package com.example.codesign;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText mEditText;
    EditText pEditText;
    ConnectivityManager cm;
    NetworkInfo activeNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditText = findViewById(R.id.mail);
        pEditText = findViewById(R.id.pass);
        Button buttonLogin = findViewById(R.id.Login);

        buttonLogin.setOnClickListener(this);

        cm =(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(cm != null)
        {
            activeNetwork = cm.getActiveNetworkInfo();

            new CheckConnectivityTask().execute();
        }
    }

    @Override
    public void onClick(View view) {
        /*//COMPROVAR SI EDITTEXTS BUITS, DESHABILITAT DE MOMENT PER COMODITAT
        if(isEmpty(mEditText) || isEmpty(pEditText)){
            return;
        }*/

        //REDIRECCIO A LA PANTALLA D'INICI
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
}
