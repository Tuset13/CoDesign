package com.example.codesign.Projecte;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.codesign.R;

import java.io.ByteArrayOutputStream;

public class CanvasActivity extends AppCompatActivity implements View.OnClickListener {

    private Button midaUp, midaDown, changeColor, eraser, back;
    private MyCanvas myCanvas;

    private String buttonEraserText, buttonColorText;

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
        buttonColorText = (String) changeColor.getText();

        midaUp.setOnClickListener(this);
        midaDown.setOnClickListener(this);
        changeColor.setOnClickListener(this);
        eraser.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
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
            case R.id.color:
                if(eraser.getText() == buttonEraserText){
                    if(changeColor.getText() == buttonColorText){
                        myCanvas.setColor("#FF0000");
                        changeColor.setText(R.string.changeToBlack);
                    }else{
                        myCanvas.setColor("#000000");
                        changeColor.setText(R.string.changeToRed);
                    }
                }
                break;
            case R.id.tornarProject:
                myCanvas.setDrawingCacheEnabled(true);
                Bitmap result = myCanvas.getDrawingCache();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                result.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                Intent intent=new Intent();
                intent.putExtra("result",byteArray);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
