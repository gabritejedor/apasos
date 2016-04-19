package com.example.gabri.apasos;

/**
 * Created by 8fprogmm09 on 7/4/16.
 */

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Context;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import static android.widget.Toast.*;

public class ServicioGPS extends Service {


    //coordenadas
    private Coordenada coordenada;

    // Referencia para el gestorbd que nos comunica con el SQLite
    private GestorBD gestorbd = null;

    // Acquire a reference to the system Location Manager
    private LocationManager locationManager;
    // Define a listener that responds to location updates
    private LocationListener locationListener;

    @Override
    public void onCreate() {
     gestorbd = new GestorBD(this);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
       gestorbd.abrirBD();
       makeText(this, "servicio creado", LENGTH_SHORT).show();
        leerCoordenada();
       Toast.makeText(this, String.valueOf(gestorbd.numero_sesiones()), LENGTH_SHORT).show();
        Toast.makeText(this, String.valueOf("numero de coordenadas"+gestorbd.numero_coordenadas()), LENGTH_SHORT).show();
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        gestorbd.guardarSesion(sqlDate);
    }

    @Override
    public IBinder onBind(Intent intent) {
       return  null;
    }

    @Override
    public void onDestroy() {
       makeText(this, "servicio destruido", LENGTH_SHORT).show();

        gestorbd.cerrarBD();
        super.onDestroy();
    }

    // Metodo que lee cordenadas y las guarda en la BD
    private void leerCoordenada() {
         locationListener = new LocationListener() {

            //LOCATION CHANGE
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.

                //LEEMOS COORDENADAS
                System.out.println(location.getLatitude());
                System.out.println(location.getLongitude());

                Coordenada c = new Coordenada(location.getLatitude(), location.getLongitude());


                //GUARDAMOS EN LA BD
            gestorbd.guardarCoordenada(c);

            }



            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
                System.out.println("GPS habilitado");
            }

            public void onProviderDisabled(String provider) {
                System.out.println("GPS inhabilitado");
            }
        };

        // Register the listener with the Location Manager to receive location updates

        // http://developer.android.com/intl/es/training/permissions/requesting.html
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListener);
        }
    }

}