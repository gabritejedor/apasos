package com.example.gabri.apasos;

/**
 * Created by 8fprogmm09 on 7/4/16.
 */

import android.Manifest;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

public class ServicioGPS extends IntentService {

    //coordenadas
    private Coordenada coordenada;

    // Referencia para el gestorbd que nos comunica con el SQLite
    private GestorBD gestorbd = null;

    // Acquire a reference to the system Location Manager
    private LocationManager locationManager= (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    // Define a listener that responds to location updates
    private LocationListener locationListener;

    // Constructor
    public ServicioGPS() {
        super("GPS");
        gestorbd=new GestorBD(this);
        gestorbd.abrirBD();
    }


    // Metodo que lee cordenadas y las guarda en la BD
    private void leerCoordenada() {


        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.

                //LEEMOS COORDENADAS
                System.out.println(location.getLatitude());
                System.out.println(location.getLongitude());

                Coordenada c = new Coordenada(location.getLatitude(),location.getLongitude());
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
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }




    @Override
    protected void onHandleIntent(Intent intent) {

            leerCoordenada();
            Log.i("EMPEZADO","servicio empezado");

    }


}
