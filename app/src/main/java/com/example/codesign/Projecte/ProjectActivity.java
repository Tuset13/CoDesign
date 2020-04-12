package com.example.codesign.Projecte;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.codesign.DDBB.ProjectesSQLiteHelper;
import com.example.codesign.ParticipantsActivity;
import com.example.codesign.R;

import java.io.ByteArrayOutputStream;

public class ProjectActivity extends AppCompatActivity implements View.OnClickListener  {

    static final int IMAGE_REQUEST = 1;
    static final int TEXT_REQUEST = 2;

    private String newNote;
    private Bitmap imatgeBackground;
    private ImageView imageCanvas;
    //private LinearLayout background;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        ImageButton eButton = findViewById(R.id.edicio_pissarra);
        ImageButton anButton = findViewById(R.id.afegir_nota);
        imageCanvas = findViewById(R.id.imageCanvas);
        //background = findViewById(R.id.backgroundLayout);

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
                return true;

            case R.id.borrar:

                //INSTANCIEM UN ALERT DIALOG
                final AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
                newDialog.setTitle(R.string.adTitle);
                newDialog.setMessage(R.string.adMessage);
                newDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // EN CAS DE DIR QUE SI, BORREM EL PROJECTE
                        borrarProjecte();
                        finish();
                    }
                });
                newDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                newDialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edicio_pissarra:
                // ENVIEM LA IMATGE ACTUAL PER A PROSEGUIR AMB LA SEVA EDICIO
                enviarImatge();
                break;

            case R.id.afegir_nota:
                Intent intent1 = new Intent(ProjectActivity.this, NewNoteActivity.class);
                startActivityForResult(intent1, TEXT_REQUEST);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_REQUEST) {

                // INPORPORACIO DE LA IMATGE REBUDA
                byte[] byteArray = data.getByteArrayExtra("result");
                imatgeBackground = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                //Drawable backImage = new BitmapDrawable(getResources(), imatgeBackground);
                //background.setBackground(backImage);
                imageCanvas.setImageBitmap(imatgeBackground);
            }else{

                // TEXT DE LA NOVA NOTA
                newNote = data.getStringExtra("result");
            }
        }
    }

    public void enviarImatge(){
        //TODO
        //DE MOMENT EN DESUS, PREPARAT PER A LA SEGÜENT ENTREGA
        Intent intent = new Intent(ProjectActivity.this, CanvasActivity.class);
        if(imatgeBackground != null){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imatgeBackground.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            intent.putExtra("result",byteArray);
        }
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    public void borrarProjecte(){
        // INSTANCIEM LA DB
        ProjectesSQLiteHelper usdbh = new ProjectesSQLiteHelper(this, "Projectes",null, 1);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        if(db != null){
            // BORREM EL PROJECTE RECUPERANT LA ID A TRAVES DEL INTENT
            String id = getIntent().getStringExtra(getString(R.string.id_key));
            db.delete("Projectes", "_Id=?", new String[]{id});
            Toast.makeText(this, R.string.borrarToast, Toast.LENGTH_LONG).show();
        }
        db.close();
    }
}
