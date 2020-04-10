package com.example.codesign;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ProjectActivity extends AppCompatActivity implements View.OnClickListener  {

    static final int IMAGE_REQUEST = 1;

    private Bitmap imatgeBackground;
    private ImageView imageCanvas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        Button eButton = findViewById(R.id.edicio_pissarra);
        Button anButton = findViewById(R.id.afegir_nota);
        imageCanvas = findViewById(R.id.imageCanvas);

        eButton.setOnClickListener(this);
        anButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.project_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.exit:
                finish();
                return true;

            case R.id.participants:
                //TODO

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edicio_pissarra:
                Intent intent = new Intent(ProjectActivity.this, CanvasActivity.class);
                startActivityForResult(intent, IMAGE_REQUEST);
                break;

            case R.id.afegir_nota:
                //TODO
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {
                byte[] byteArray = data.getByteArrayExtra("result");
                imatgeBackground = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                imageCanvas.setImageBitmap(imatgeBackground);
            }
        }
    }
}
