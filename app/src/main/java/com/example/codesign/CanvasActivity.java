package com.example.codesign;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CanvasActivity extends AppCompatActivity implements View.OnClickListener {

    private Button midaUp, midaDown, changeColor, eraser, back;
    private MyCanvas myCanvas;

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
                myCanvas.setErase(true);
                myCanvas.setBrushSize(myCanvas.getBrushSize());
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
                break;
            case R.id.tornarProject:
                finish();
                break;
        }
    }
}
