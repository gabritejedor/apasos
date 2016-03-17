package com.example.gabri.apasos;


import android.Manifest;
import android.Manifest.permission;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.os.IBinder;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.sql.Timestamp;

public class GpsChapuza extends IntentService {

    // Acquire a reference to the system Location Manager
    private LocationManager locationManager;
    private LocationListener locationListener;
    // referencia al main
    private Principal main = new Principal();

    //coordenadas
   private Coordenada coordenada;

    // Referencia para el gestorbd que nos comunica con el SQLite
    private GestorBD gestorbd = null;

    // constructor
    public GpsChapuza() {
        super("GPS");
    }


    public void onCreate()
    {
        super.onCreate();
        System.out.println("CREADO");
        Toast.makeText(this,"servicio creado",Toast.LENGTH_SHORT).show();

        // Arrancamos gestor de BD
        gestorbd = new GestorBD(this);
        gestorbd.abrirBD();

        // nueva sesion

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
         locationListener = new LocationListener(){
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.

                //Objeto coordenada
                coordenada= new Coordenada();


                // leemos y guardamos latitud y longitud

                System.out.println(location.getLatitude());
                System.out.println(location.getLongitude());
                String s = "Longitud: "+location.getLongitude()+" Lati: "+ location.getLatitude();
         //       Toast.makeText(this,"servicio creado",Toast.LENGTH_SHORT).show();



                coordenada.setLatitud(location.getLatitude());
                coordenada.setLongitud(location.getLongitude());

                //guardar objeto Coordenada en SQLite
                gestorbd.guardarCoordenada(coordenada);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
                System.out.println("GPS inhabilitado");
            }

            public void onProviderDisabled(String provider) {
                System.out.println("GPS inhabilitado");
            }


        };


        // Register the listener with the Location Manager to receive location updates

        // http://developer.android.com/intl/es/training/permissions/requesting.html
        int permissionCheck = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
        //main.getApplicationContext()

        //    (this,
        //      Manifest.permission.ACCESS_FINE_LOCATION);


        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

    }

    public void onDestroy(){
        Toast.makeText(this,"servicio detenido",Toast.LENGTH_SHORT).show();
        System.out.println("DETENIDO");

        // Cerramos la base de datos
        gestorbd.cerrarBD();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

}
