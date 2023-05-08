package com.bmr.conapesca.Datos;

import android.content.Context;

import java.io.File;

public class Cache {

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            System.out.println("Borrando Cache");
            deleteDir(dir);
        } catch (Exception e) {}
    }
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            System.out.println("Directorio cache existe");
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    System.out.println("No se pudo borrar cache");
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
}
