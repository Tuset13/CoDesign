package com.example.codesign.Projecte;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.codesign.MeetingsActivity;
import com.example.codesign.ParticipantsActivity;
import com.example.codesign.PropertiesActivity;
import com.example.codesign.R;
import com.example.codesign.Settings.SettingsActivity;

import java.io.ByteArrayOutputStream;

public class ProjectActivity extends AppCompatActivity implements View.OnClickListener  {

    static final int IMAGE_REQUEST = 1;
    static final int TEXT_REQUEST = 2;

    private String newNote;
    private Bitmap imatgeBackground;
    private LinearLayout background;

    private String[] llistaNotes = new String[12];
    private int numNotes = 0;

    //REFERENCIES DE LA GRID HARDCODEJADA
    TextView textGrid0;
    TextView textGrid1;
    TextView textGrid2;
    TextView textGrid3;
    TextView textGrid4;
    TextView textGrid5;
    TextView textGrid6;
    TextView textGrid7;
    TextView textGrid8;
    TextView textGrid9;
    TextView textGrid10;
    TextView textGrid11;
    TextView[] textGrid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        ImageButton eButton = findViewById(R.id.edicio_pissarra);
        ImageButton anButton = findViewById(R.id.afegir_nota);
        ImageButton mButton = findViewById(R.id.anar_reunio);
        background = findViewById(R.id.backgroundLayout);

        eButton.setOnClickListener(this);
        anButton.setOnClickListener(this);
        mButton.setOnClickListener(this);

        instanciesHardcodeGridiNotes();
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

            case R.id.propietats:
                Intent properties = new Intent(this, PropertiesActivity.class);
                startActivity(properties);
                return true;

            case R.id.settings:
                Intent intent = new Intent(ProjectActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
                
            case R.id.exit:
                finish();
                return true;

            /*case R.id.participants:
                Intent intent1 = new Intent(ProjectActivity.this, ParticipantsActivity.class);
                startActivity(intent1);
                return true;
*/
            case R.id.borrar:
                //INSTANCIEM UN ALERT DIALOG
                final AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
                newDialog.setTitle(R.string.adTitle);
                newDialog.setMessage(R.string.adMessage);
                newDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // EN CAS DE DIR QUE SI, BORREM EL PROJECTE
                        //borrarProjecte();
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
                // ENVIEM LA ID DEL PROJECTE ACTUAL PER A PROSEGUIR AMB LA SEVA EDICIO
                enviarIdProjecte();
                break;

            case R.id.afegir_nota:
                Intent intent1 = new Intent(ProjectActivity.this, NewNoteActivity.class);
                startActivityForResult(intent1, TEXT_REQUEST);
                break;

            case R.id.anar_reunio:
                Intent meetIntent = new Intent(ProjectActivity.this, MeetingsActivity.class);
                startActivity(meetIntent);
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
                Drawable backImage = new BitmapDrawable(getResources(), imatgeBackground);
                background.setBackground(backImage);
            }else if(requestCode == TEXT_REQUEST){
                // TEXT DE LA NOVA NOTA
                newNote = data.getStringExtra("result");
                llistaNotes[numNotes] = newNote;

                //PART HARCODEJADA PER A SIMULAR LA REPRESENTACIO DE LES NOTES SOBRE EL CANVAS
                //EL RESULTAT FINAL EN LA SEGONA ENTREGA NO ES VEURE AIXI NI ESTARA FET AIXI
                hardcodeGridiNotes();
            }
        }
    }

    public void enviarIdProjecte(){
        Intent dataIntent = getIntent();
        String idProjecte = dataIntent.getStringExtra(getString(R.string.id_key));
        Intent intent = new Intent(ProjectActivity.this, CanvasActivity.class);
        intent.putExtra(getString(R.string.id_key), idProjecte);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    /*public void borrarProjecte(){
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
    }*/

    public void instanciesHardcodeGridiNotes(){
        textGrid0 = findViewById(R.id.textGrid0);
        textGrid1 = findViewById(R.id.textGrid1);
        textGrid2 = findViewById(R.id.textGrid2);
        textGrid3 = findViewById(R.id.textGrid3);
        textGrid4 = findViewById(R.id.textGrid4);
        textGrid5 = findViewById(R.id.textGrid5);
        textGrid6 = findViewById(R.id.textGrid6);
        textGrid7 = findViewById(R.id.textGrid7);
        textGrid8 = findViewById(R.id.textGrid8);
        textGrid9 = findViewById(R.id.textGrid9);
        textGrid10 = findViewById(R.id.textGrid10);
        textGrid11 = findViewById(R.id.textGrid11);
        textGrid = new TextView[]{textGrid0, textGrid1, textGrid2, textGrid3, textGrid4, textGrid5,
                                                textGrid6, textGrid7, textGrid8, textGrid9, textGrid10, textGrid11};
    }

    public void hardcodeGridiNotes(){
        if(numNotes < 9){
            textGrid[numNotes].setText(newNote);
            textGrid[numNotes].setBackgroundColor(getResources().getColor(R.color.notesColor));
            numNotes++;
        }
    }
}
