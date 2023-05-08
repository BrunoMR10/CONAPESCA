package com.bmr.conapesca.Herramientas;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

public class Permisos {
    public static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE,
    };
    public static final int REQUEST_PERMISSION = 100;
    public Boolean Permisos(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if ((ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED)&&
                    (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)&&
                    (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
                return true;
                //AbreCamara(Foto);
            }else{
                //ActivityCompat.requestPermissions(activity,PERMISSIONS_STORAGE,REQUEST_PERMISSION);
                return false;
            }

        }else{
            return true;// AbreCamara(Foto);
        }
    }


}
