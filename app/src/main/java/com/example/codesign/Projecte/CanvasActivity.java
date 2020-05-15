package com.example.codesign.Projecte;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.codesign.R;

import java.io.ByteArrayOutputStream;

public class CanvasActivity extends AppCompatActivity implements View.OnClickListener {

    private Button midaUp, midaDown, changeColor, eraser, back;
    private MyCanvas myCanvas;
    private Bitmap imatgeBackground;
    SharedPreferences mypreferences;

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

    //TODO
    //EN LA SEGÜENT ENTREGA
    public void comprovarImatge(){
        byte[] byteArray = getIntent().getByteArrayExtra("result");
        if(byteArray != null){
            imatgeBackground = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            myCanvas.setImage(imatgeBackground);
        }
    }
}
