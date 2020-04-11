package com.example.codesign.Projecte;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import com.example.codesign.ParticipantsActivity;
import com.example.codesign.R;

public class ProjectActivity extends AppCompatActivity implements View.OnClickListener  {

    static final int IMAGE_REQUEST = 1;

    private Bitmap imatgeBackground;
    //private ImageView imageCanvas;
    private LinearLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        Button eButton = findViewById(R.id.edicio_pissarra);
        Button anButton = findViewById(R.id.afegir_nota);
        //imageCanvas = findViewById(R.id.imageCanvas);
        background = findViewById(R.id.backgroundLayout);

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
                Intent intent1 = new Intent(ProjectActivity.this, ParticipantsActivity.class);
                startActivity(intent1);

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
                Drawable backImage = new BitmapDrawable(getResources(), imatgeBackground);
                background.setBackground(backImage);
                //imageCanvas.setImageBitmap(imatgeBackground);
            }
        }
    }
}
