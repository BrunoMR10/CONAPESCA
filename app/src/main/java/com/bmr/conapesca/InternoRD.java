package com.bmr.conapesca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.bmr.conapesca.Datos.Datos;

public class InternoRD extends AppCompatActivity {
    Datos dt = new Datos();
    String []Datos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interno_rd);
    }
    private String[] ObtenSharedPreference(){
        Datos = new String[dt.Datos.length];
        SharedPreferences sh = getSharedPreferences("Datos", MODE_PRIVATE);
        for (int i = 0;i<dt.Datos.length;i++){
            Datos[i]=sh.getString("Datos"+String.valueOf(i), "");
        }
        return Datos;
    }
    public void onBackPressed() {
        Datos = ObtenSharedPreference();
        Intent i = new Intent(this,Servicios.class);
        i.putExtra("Datos",Datos);
        startActivity(i);
    }
}