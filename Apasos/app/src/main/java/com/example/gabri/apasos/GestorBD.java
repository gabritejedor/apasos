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

import java.lang.reflect.Array;
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

            int id_ruta = ultimaRuta();
            values.put("id_ruta",id_ruta);

            values.put("latitud",c.getLatitud());
            values.put("longitud", c.getLongitud());

            bd.insert("coordenda", null, values);
        }
    }


    public void guardarSesion(String fecha){
        if (bd.isOpen() && fecha.length() != 0) {

            ContentValues values = new ContentValues();

            values.put("fecha",fecha);
            bd.insert("ruta", null, values);
        }

    }


    /*
        METODOS PARA RECUPERAR SESION
     */

    private int ultimaRuta(){


        int ultimaRuta=0;

        if (bd.isOpen()){

            String tabla = "ruta";
            String[] columnas = new String[]{"id"};
            String where = null;
            String[] argumentoswhere = null;
            String groupby = null;
            String having = null;
            String orderby = "id DESC";
            String limit = null;

            Cursor c1 = bd.query(tabla, columnas, where, argumentoswhere,
                    groupby, having, orderby, limit);

            if (c1.moveToFirst()) {
                ultimaRuta=Integer.parseInt(c1.getString(0));
            }
        }
        return ultimaRuta;
    }

    public List<Ruta> leerRutas(){
        List<Ruta> rutas = new ArrayList<>();

        if (bd.isOpen()) {

            String tabla = "ruta";
            String[] columnas = new String[]{"id", "fecha"};
            String where = null;
            String[] argumentoswhere = null;
            String groupby = null;
            String having = null;
            String orderby = null;
            String limit = null;

            Cursor c1 = bd.query(tabla, columnas, where, argumentoswhere,
                    groupby, having, orderby, limit);

            if (c1.moveToFirst()) {

                // Recorremos el cursor hasta que no haya más registros
                do {
                    int id = Integer.parseInt(c1.getString(0));

                    Ruta r = new Ruta(id, c1.getString(1));
                    rutas.add(r);
                } while (c1.moveToNext());
            }
        }

        return rutas;
    }
    public List<Coordenada> leerCoordenadas(String ruta) {

        List<Coordenada> listaCoordenadas = new ArrayList<>();

        if (bd.isOpen()) {

            String tabla = "coordenada";
            String[] columnas = new String[]{"latitud", "longitud"};
            String where = "id_ruta=?";
            String[] argumentoswhere = new String[]{ruta};
            String groupby = null;
            String having = null;
            String orderby = null;
            String limit = null;

            Cursor c1 = bd.query(tabla, columnas, where, argumentoswhere,
                    groupby, having, orderby, limit);

            if (c1.moveToFirst()) {

                // Recorremos el cursor hasta que no haya más registros
                do {

                    Double latitud = Double.parseDouble(c1.getString(0));

                    Double longitud = Double.parseDouble(c1.getString(1));

                    Coordenada c = new Coordenada(latitud,longitud);
                    listaCoordenadas.add(c);
                } while (c1.moveToNext());
            }
        }


        return listaCoordenadas;
    }

    private class BDHelper extends SQLiteOpenHelper {

        private String tablaSesiones = "CREATE TABLE ruta (id INTEGER PRIMARY KEY AUTOINCREMENT, fecha TEXT NOT NULL );";
        private String tablaCoordenadas = "CREATE TABLE coordenada ( id INTEGER PRIMARY KEY AUTOINCREMENT, id_ruta INTEGER,latitud REAL NOT NULL , longitud REAL NOT NULL, FOREIGN KEY(id_ruts) REFERENCES ruta(id));";

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
