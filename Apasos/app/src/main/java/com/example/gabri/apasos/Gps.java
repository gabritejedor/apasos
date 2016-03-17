package com.example.gabri.apasos;

import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GPS extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_PROGRESO = "com.example.gabri.apasos.action.PROGRESO";
    private static final String ACTION_FIN = "com.example.gabri.apasos.action.FIN";

    // Acquire a reference to the system Location Manager
    private LocationManager locationManager;
    private LocationListener locationListener;
    // referencia al main
    private Principal main = new Principal();

    //coordenadas
    private Coordenada coordenada;


    // Referencia para el gestorbd que nos comunica con el SQLite
    private GestorBD gestorbd = null;


    public GPS() {
        super("GPS");
    }

 private void leerCoordenadas() {
     System.out.println("LEER COORD");
     locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
     // Define a listener that responds to location updates
     locationListener = new LocationListener() {
         public void onLocationChanged(Location location) {
             // Called when a new location is found by the network location provider.
             System.out.println("onlocation....");
             //Objeto coordenada
     //        coordenada = new Coordenada();


             // leemos y guardamos latitud y longitud

             System.out.println(location.getLatitude());
             System.out.println(location.getLongitude());
             String s = "Longitud: " + location.getLongitude() + " Lati: " + location.getLatitude();
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
    @Override
    protected void onHandleIntent(Intent intent) {

     /*   Intent intento = new Intent();
        intento.setAction(ACTION_PROGRESO);
        sendBroadcast(intento);

*/
        System.out.println("CREADO");
     //   Toast.makeText(this, "servicio creado", Toast.LENGTH_SHORT).show();

        // Arrancamos gestor de BD
        gestorbd = new GestorBD(this);
        gestorbd.abrirBD();

        // nueva sesion

            leerCoordenadas();



    }

 /*   @Override
    public void onCreate() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(GPS.ACTION_PROGRESO);
        filter.addAction(GPS.ACTION_FIN);
        Progreso rcv = new Progreso();
        registerReceiver(rcv, filter);
    }*/
}
