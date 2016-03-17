package com.example.gabri.apasos;

import java.sql.Timestamp;

/**
 * Created by Ixone on 17/03/2016.
 */
public class Coordenada {

    private Timestamp time;
    private double latitud;    //coordenada Y
    private double longitud;   //coordenada X

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

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
