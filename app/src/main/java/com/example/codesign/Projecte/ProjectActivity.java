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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.codesign.Classes.Projectes;
import com.example.codesign.MeetingsActivity;
import com.example.codesign.PropertiesActivity;
import com.example.codesign.R;
import com.example.codesign.Settings.SettingsActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.List;

public class ProjectActivity extends AppCompatActivity implements View.OnClickListener  {

    static final int IMAGE_REQUEST = 1;
    static final int TEXT_REQUEST = 2;

    private static final String TAG = "FireLog";

    private Bitmap imatgeBackground;
    private LinearLayout background;

    private List<String> llistaNotes;

    private String idProjecte;
    private FirebaseFirestore mFirestore;

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

        Intent dataIntent = getIntent();
        idProjecte = dataIntent.getStringExtra(getString(R.string.id_key));

        instanciesHardcodeGridiNotes();

        llistarNotes();
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
                properties.putExtra(getString(R.string.id_key), idProjecte);
                startActivity(properties);
                return true;

            case R.id.settings:
                Intent intent = new Intent(ProjectActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
                
            case R.id.exit:
                finish();
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
                intent1.putExtra(getString(R.string.id_key), idProjecte);
                startActivityForResult(intent1, TEXT_REQUEST);
                break;

            case R.id.anar_reunio:
                Intent meetIntent = new Intent(ProjectActivity.this, MeetingsActivity.class);
                meetIntent.putExtra(getString(R.string.id_key), idProjecte);
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

                //REPRESENTACIO DE LES NOTES SOBRE EL CANVAS
                llistarNotes();
            }
        }
    }

    public void enviarIdProjecte(){
        Intent intent = new Intent(ProjectActivity.this, CanvasActivity.class);
        intent.putExtra(getString(R.string.id_key), idProjecte);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private void llistarNotes(){

        mFirestore = FirebaseFirestore.getInstance();

        DocumentReference docRef = mFirestore.collection(getString(R.string.ColProj)).document(idProjecte);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Projectes projecte = documentSnapshot.toObject(Projectes.class);

                hardcodeGridiNotes(projecte);

            }
        });

    }

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

    public void hardcodeGridiNotes(Projectes projecte){

        llistaNotes = projecte.getNotes();

        for(int i = 0; i < llistaNotes.size() && i < 12; i++){
            textGrid[i].setText(llistaNotes.get(i));
            textGrid[i].setBackgroundColor(getResources().getColor(R.color.notesColor));
        }
    }
}
