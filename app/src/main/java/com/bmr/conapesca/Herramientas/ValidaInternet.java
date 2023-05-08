package com.bmr.conapesca.Herramientas;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class ValidaInternet {
    @RequiresApi(api = Build.VERSION_CODES.M)
    public Boolean ValidaInternet(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    //Toast.makeText(context,"NetworkCapabilities.TRANSPORT_CELLULAR",Toast.LENGTH_SHORT).show();
                    return true;
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    //Toast.makeText(context,"NetworkCapabilities.TRANSPORT_WIFI",Toast.LENGTH_SHORT).show();
                    return true;
                }  else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)){
                    //Toast.makeText(context,"NetworkCapabilities.TRANSPORT_ETHERNET",Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        }


        return false;

    }

}
