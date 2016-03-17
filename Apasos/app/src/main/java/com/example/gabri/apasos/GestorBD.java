package com.example.gabri.apasos;

/**
 * Created by Ixone on 17/03/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class GestorBD {


    private SQLiteDatabase bd = null;
    private BDHelper helper = null;

    public GestorBD(Context context) {
        helper =new BDHelper(context,"Coordenadas.sqlite",null,1);
    }

    public void abrirBD() {
        if (bd == null)
            bd = helper.getWritableDatabase();
    }

    public void cerrarBD() {
        if (bd != null)
            bd.close();
    }

    public void guardarCoordenada(Coordenada c) {
        if (bd.isOpen() && c != null) {

            ContentValues values = new ContentValues();

            values.put("latitud",c.getLatitud());
            values.put("longitud", c.getLongitud());

            bd.insert("coordenda", null, values);
        }
    }

    public List<Coordenada> leerCoordenadas() {

        List<Coordenada> listaCoordenadas = new ArrayList<>();

        /*
            CÓDIGO PARA RECUPERAR TODAS LAS COORDENADAS DE SQLITE
         */

        return listaCoordenadas;
    }

    private class BDHelper extends SQLiteOpenHelper {

        private String tablaCoordenadas = "CREATE TABLE coordenada ( id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, latitud REAL NOT NULL , longitud REAL NOT NULL);";

        public BDHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            if (!db.isReadOnly()) {
                // Enable foreign key constraints
                db.execSQL("PRAGMA foreign_keys=ON;");
                db.execSQL(tablaCoordenadas);
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            if (!db.isReadOnly()) {
                db.execSQL("DROP TABLE IF EXISTS coordenada");

                // Enable foreign key constraints
                db.execSQL("PRAGMA foreign_keys=ON;");
                db.execSQL(tablaCoordenadas);
            }

        }

    }


}
