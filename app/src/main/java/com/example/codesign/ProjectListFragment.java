package com.example.codesign;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.ListFragment;


import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.codesign.DDBB.ProjectesSQLiteHelper;


public class ProjectListFragment extends ListFragment {


    private ProjecteListener listener;

    public interface ProjecteListener{
        void onProjecteSeleccionat(String id);
    }

    public void setProjecteListener(ProjecteListener listener){
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        //OBTENIM LES DADES DE LA BASE DE DADES
        getBBDDData();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        //EN SELECCIONAR UN ITEM DE LA LLISTA, CRIDEM AL LISTENER AGREGAT DESDE LA MAINACTIVITY
        //I LI PASSEM LA SEVA ID
        if(listener!=null){
            String stringId = String.valueOf(id);
            listener.onProjecteSeleccionat(stringId);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getBBDDData();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            listener = (ProjecteListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString() +
                    " must implement OnProjecteSeleccionat()");
        }
    }


    public void getBBDDData(){
        ProjectesSQLiteHelper usdbh = new ProjectesSQLiteHelper(
                getContext(), "Projectes",null, 1);
        SQLiteDatabase db = usdbh.getReadableDatabase();

        String[] campos = new String[]{"_id", "projectName"};
        Cursor c = db.query(
                "Projectes", campos, null, null, null,null,null);

        String[] from = new String[]{"projectName"};
        int[] to = new int[]{R.id.gamedata1};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                getContext(), R.layout.fragment_project_data, c, from, to, 0);

        //AFEGIM LA LLISTA EXTRETA DE LA BASE DE DADES AL LISTVIEW
        this.setListAdapter(adapter);
    }
}
