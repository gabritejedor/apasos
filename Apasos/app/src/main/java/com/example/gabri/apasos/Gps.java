package com.example.gabri.apasos;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class Gps extends Service {


    public void onCreate()
    {
        super.onCreate();
        Toast.makeText(this,"servicio creado",Toast.LENGTH_SHORT).show();

    }
    public void onDestroy(){
        Toast.makeText(this,"servicio detenido",Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
