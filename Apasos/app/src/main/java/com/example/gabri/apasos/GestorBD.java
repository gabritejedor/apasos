package com.example.gabri.apasos;

/**
 * Created by Ixone on 17/03/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.SyncStatusObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestorBD {


    private SQLiteDatabase bd = null;
    private BDHelper helper = null;

   public GestorBD(Context context) {
        helper =new BDHelper(context,"Coordenadas.sqlite",null,1);
    }

    public void abrirBD() {

            bd = helper.getWritableDatabase();


    }

    public void cerrarBD() {

        if (bd != null) {
            bd.close();
            System.out.println("bd cerrada");
        }
    }
    //preparamos posicion para pasar al mapa


    public PolylineOptions linea(int id){


        ArrayList<Coordenada> ListaCoordenadas =leerCoordenadas(id);
        Iterator<Coordenada> i = ListaCoordenadas.iterator();
        PolylineOptions linea = new PolylineOptions();
        while (i.hasNext()){
            Coordenada aux = i.next();
            linea.add(new LatLng(aux.getLatitud(),aux.getLongitud()));
        }

    return linea;


    }

   public void guardarCoordenada(Coordenada c) {
        if (bd.isOpen() && c != null) {
            System.out.print("esta dentro de guardar coordenadas");
            int id_ruta = ultimaRuta();
            String sql = "insert into coordenada values(null," + id_ruta + "," + c.getLatitud() + "," + c.getLongitud() + ")";
            bd.execSQL(sql);
        }
    }


    public void guardarSesion(java.sql.Date fecha){
        if (bd.isOpen()) {

            ContentValues values = new ContentValues();



            bd.execSQL("insert into ruta values (null," + fecha + ")");
        }

    }
    public int numero_sesiones()
    {
        Cursor cur = bd.rawQuery("select * from ruta",null);
        return cur.getCount();
    }
    public int numero_coordenadas()
    {
        Cursor cursor = bd.rawQuery("select * from coordenada",null);
        return cursor.getCount();
    }



      //  METODOS PARA RECUPERAR SESION


   private int  ultimaRuta(){


        int ultimaRuta=0;

        if (bd.isOpen()){



            Cursor c1 = bd.rawQuery("select * from ruta order by id DESC", null);

            if (c1.moveToFirst()) {
                ultimaRuta=(c1.getInt(0));
            }
        }
       System.out.print(ultimaRuta);
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
    public ArrayList<Coordenada> leerCoordenadas(int ruta) {

        ArrayList<Coordenada> listaCoordenadas = new ArrayList<>();
        String sql = "select * from coordenada where id_ruta="+ruta;
        Cursor c1 = bd.rawQuery(sql,null);
        // Recorremos el cursor hasta que no haya más registros

                while (c1.moveToNext()) {

                    Double latitud = c1.getDouble(0);
                    Double longitud = c1.getDouble(1);
                    Coordenada c = new Coordenada(latitud,longitud);
                    listaCoordenadas.add(c);
                }
        return listaCoordenadas;
    }


    private class BDHelper extends SQLiteOpenHelper {

       private String tablaSesiones = "CREATE TABLE IF NOT EXISTS ruta (id INTEGER PRIMARY KEY AUTOINCREMENT, fecha date NOT NULL );";
        private String tablaCoordenadas = "CREATE TABLE IF NOT EXISTS coordenada ( id INTEGER PRIMARY KEY AUTOINCREMENT, id_ruta INTEGER,latitud REAL NOT NULL , longitud REAL NOT NULL, FOREIGN KEY(id_ruta) REFERENCES ruta(id));";

        public BDHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            if (!db.isReadOnly()) {
                // Enable foreign key constraints
               db.execSQL("PRAGMA foreign_keys=ON;");
                db.execSQL(tablaSesiones);
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
                db.execSQL(tablaSesiones);
            }

        }

    }


}
