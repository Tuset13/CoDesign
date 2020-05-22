package com.example.codesign.Projecte;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.codesign.Classes.Projectes;
import com.example.codesign.MainActivity;
import com.example.codesign.R;
import com.example.codesign.Settings.SettingsActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;

public class CanvasActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "FireLog";
    private Button midaUp, midaDown, changeColor, eraser, back;
    private static MyCanvas myCanvas;
    private Bitmap imatgeBackground;
    SharedPreferences mypreferences;
    private String idProjecte;

    //private FirebaseFirestore mFirestore;

    private String buttonEraserText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);

        myCanvas = findViewById(R.id.drawing);
        midaUp = findViewById(R.id.sizeUp);
        midaDown = findViewById(R.id.sizeDown);
        changeColor = findViewById(R.id.color);
        eraser = findViewById(R.id.eraser);
        back = findViewById(R.id.tornarProject);

        buttonEraserText = (String) eraser.getText();
        mypreferences = getSharedPreferences("settings",MODE_PRIVATE);
        changeColor.setText(mypreferences.getString("color","Custom"));

        midaUp.setOnClickListener(this);
        midaDown.setOnClickListener(this);
        changeColor.setOnClickListener(this);
        eraser.setOnClickListener(this);
        back.setOnClickListener(this);

        //mFirestore = FirebaseFirestore.getInstance();
        Intent dataIntent = getIntent();
        idProjecte = dataIntent.getStringExtra(getString(R.string.id_key));

        //comprovarCanvasExistent();
    }

    //IMPLEMENTACIONS DE MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
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
        switch(view.getId()){

            // BOTO DE LA GOMA
            case R.id.eraser:
                String buttonText = (String) eraser.getText();
                if(buttonText == buttonEraserText){
                    myCanvas.setErase(true);
                    myCanvas.setBrushSize(20);
                    myCanvas.setLastBrushSize(20);
                    eraser.setText(R.string.brush);
                }else{
                    myCanvas.setBrushSize(8);
                    myCanvas.setLastBrushSize(8);
                    myCanvas.setupDrawing();
                    eraser.setText(R.string.eraser);
                }
                break;

            // BOTONS PER CONTROLAR MIDA
            case R.id.sizeUp:
                float newBigBrushSize = myCanvas.getBrushSize();
                newBigBrushSize += 2;
                myCanvas.setBrushSize(newBigBrushSize);
                myCanvas.setLastBrushSize(newBigBrushSize);
                break;
            case R.id.sizeDown:
                float newLowBrushSize = myCanvas.getBrushSize();
                newLowBrushSize -= 2;
                myCanvas.setBrushSize(newLowBrushSize);
                myCanvas.setLastBrushSize(newLowBrushSize);
                break;

             // BOTO PER CANVIAR COLOR
            case R.id.color:
                if(eraser.getText() == buttonEraserText){
                    if(changeColor.getText() == getString(R.string.color_black)){
                        myCanvas.setColor("#000000");
                        changeColor.setText(mypreferences.getString("color","Custom"));
                    }else{
                        myCanvas.setColor(mypreferences.getString("color_code","#000000"));
                        changeColor.setText(R.string.color_black);
                    }
                }
                break;

            //BOTO PER TORNAR AL PROJECTE I RETORNAR LA IMATGE RESULTANT
            case R.id.tornarProject:
                retornarImatge();
                //guardarCanvas();
                finish();
                break;
        }
    }

    public void retornarImatge(){
        myCanvas.setDrawingCacheEnabled(true);
        Bitmap result = myCanvas.getDrawingCache();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        result.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        Intent intent=new Intent();
        intent.putExtra("result",byteArray);
        setResult(RESULT_OK, intent);
    }

    /*private void guardarCanvas(){
        DocumentReference docRef = mFirestore.collection("projectes").document(idProjecte);

        docRef.update("canvas", myCanvas).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshot successfully updated!");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }

    private void comprovarCanvasExistent(){
        DocumentReference docRef = mFirestore.collection("projectes").document(idProjecte);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        Projectes projecte = document.toObject(Projectes.class);
                        comprovarEquals(projecte);
                    }else{
                        Log.d(TAG, "No such document");
                    }
                } else{
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void comprovarEquals(Projectes projecte){
        if(projecte.getCanvas() != null){
            myCanvas = projecte.getCanvas();
        }
    }*/
}
