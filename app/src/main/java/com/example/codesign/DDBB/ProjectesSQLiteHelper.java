package com.example.codesign.DDBB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProjectesSQLiteHelper extends SQLiteOpenHelper {

    String sqlCreate = "CREATE TABLE Projectes " +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "projectName TEXT, " +
            "participants TEXT, " +
            "administrator BOOLEAN)";
    public ProjectesSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Projectes");
        db.execSQL(sqlCreate);
    }
}
