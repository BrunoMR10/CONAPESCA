package com.bmr.conapesca.Herramientas;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class SubeFotoService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();

        Log.i( "msg", "Servicio creado");
    }
    @Override
    public int onStartCommand(Intent intencion, int flags, int idArranque) {
        Log.i( "msg", "Servicio reiniciado");

        //----- Aquí tu codigo--------
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
