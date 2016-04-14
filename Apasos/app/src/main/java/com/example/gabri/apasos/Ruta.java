package com.example.gabri.apasos;

import java.util.Date;

/**
 * Created by 8fprogmm09 on 7/4/16.
 */
public class Ruta {

    private int id_ruta;
    private String fecha;

    public Ruta(int id,String f){
        id_ruta=id;
        fecha=f;
    }


    public int getId_ruta() {
        return id_ruta;
    }

    public void setId_ruta(int id_ruta) {
        this.id_ruta = id_ruta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
