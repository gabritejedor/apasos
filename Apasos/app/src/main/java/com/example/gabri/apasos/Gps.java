package com.example.gabri.apasos;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import android.widget.Toast;

public class Gps extends Service {

    private LocationManager locationManager;
    private Double alt;
    private LocationListener locationListener;

    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"servicio creado",Toast.LENGTH_SHORT).show();
        System.out.println("servicio iniciado");
        // Acquire a reference to the system Location Manager
         locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // Register the listener with the Location Manager to receive location updates
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

             //   Toast.makeText(this,"" +location.getAltitude(),Toast.LENGTH_SHORT).show();

            System.out.println(location.getLatitude());
                System.out.println(location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                System.out.println("gps activado");
            }

            @Override
            public void onProviderDisabled(String provider) {
                System.out.println("gps desactivado");

            }
        };
        // http://developer.android.com/intl/es/training/permissions/requesting.html
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }


    public void onDestroy(){
        Toast.makeText(this,"servicio detenido",Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    public Double getAlt() {
        return alt;
    }

}
