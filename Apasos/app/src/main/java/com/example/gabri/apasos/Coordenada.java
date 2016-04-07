package com.example.gabri.apasos;

import java.sql.Timestamp;

/**
 * Created by Ixone on 17/03/2016.
 */
public class Coordenada {


    private double latitud;    //coordenada Y
    private double longitud;   //coordenada X


    //Constructores
    public Coordenada(){

    }

    public Coordenada(double latitud,double longitud){
        this.latitud=latitud;
        this.longitud=longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

}
