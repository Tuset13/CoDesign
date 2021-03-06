package com.example.codesign.Projecte;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class MyCanvas extends View {

    private Path drawPath;
    private boolean erase = false;
    private Paint drawPaint, canvasPaint;
    private Canvas drawCanvas;
    private int paintColor = Color.parseColor("#000000");
    private Bitmap canvasBitmap;
    private float brushSize, lastBrushSize;

    public MyCanvas(Context context, AttributeSet attrs){
        super(context, attrs);
        setupDrawing();

    }

    public void startNew(){
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    //SUBSTITUIR EL PINZELL PER LA GOMA
    public void setErase(boolean isErase){
        erase = isErase;
        if(erase) drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        else drawPaint.setXfermode(null);
    }

    //SETTER DE LA MIDA ACTUAL DEL PINZELL
    public void setBrushSize(float newSize) {
        float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newSize, getResources().getDisplayMetrics());
        brushSize = pixelAmount;
        drawPaint.setStrokeWidth(brushSize);
    }

    //SETTER PER RECORDAR LA ULTIMA MIDA
    public void setLastBrushSize(float lastSize){
        lastBrushSize = lastSize;
    }

    //GETTER DE LA MIDA DEL PINZELL
    public float getBrushSize(){
        return lastBrushSize;
    }

    //SETTER DEL COLOR
    public void setColor(String newColor){
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }

    //GETTER DEL COLOR
    public int getColor(){
        return paintColor;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float xPos = event.getX();
        float yPos = event.getY();

        //CONTROL DEL CURSOR QUAN TOQUEM LA PANTALLA
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(xPos,yPos);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(xPos,yPos);
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    public void setupDrawing(){
        //INSTANCIACIO DELS VALORS INICIALS
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
        brushSize = 8;
        lastBrushSize = brushSize;
        drawPaint.setStrokeWidth(brushSize);
    }
}
