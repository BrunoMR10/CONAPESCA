package com.bmr.conapesca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bmr.conapesca.Datos.Datos;

public class Servicios extends AppCompatActivity {
    String[]Datos;
    Datos dt = new Datos();
    ImageButton AsignaTicket;
    TextView AsignaTickettext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);
        AsignaTicket = (ImageButton) findViewById(R.id.AsignaTicket);
        AsignaTickettext = (TextView) findViewById(R.id.AsignaTickettext);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                Datos = extras.getStringArray("Datos");
            }
        }
        else{
            Datos = (String[]) savedInstanceState.getSerializable("Datos");
        }
        if (ObtenSharedPreference()[5].equals("Coordinador operativo")||
                ObtenSharedPreference()[5].equals("Gerente operativo")||
        ObtenSharedPreference()[1].equals("Bruno Mendoza  Ruiz ")||
        ObtenSharedPreference()[1].equals("Rodrigo Rafael Castro Rodríguez")){
            AsignaTicket.setVisibility(View.VISIBLE);
            AsignaTickettext.setVisibility(View.VISIBLE);
        }
        else {
            AsignaTicket.setVisibility(View.GONE);
            AsignaTickettext.setVisibility(View.GONE);
        }

    }



    public void ToTicketListCorrect(View view){
        Intent i = new Intent(this,ListaServicios.class);
        Datos[8]="Correctivo";
        i.putExtra("Datos",Datos);
        startActivity(i);
    }
    public void ToTicketListInterno(View view){
        Intent i = new Intent(this,ListaServicios.class);
        Datos[8]="Interno";
        i.putExtra("Datos",Datos);
        startActivity(i);
    }
    public void ToTicketListPreventivo(View view){
        Intent i = new Intent(this,ListaServicios.class);
        Datos[8]="Preventivo";
        i.putExtra("Datos",Datos);
        startActivity(i);
    }
    public void ToTicketListInstalacion(View view){
        Intent i = new Intent(this,ListaServicios.class);
        Datos[8]="Instalacion";
        i.putExtra("Datos",Datos);
        startActivity(i);
    }
    public void AsignaTicket(View view){
        Intent i = new Intent(this,AsignaTicket.class);
        Datos[8]="Servicios";
        i.putExtra("Datos",Datos);
        startActivity(i);
    }
    public void Bitacora(View view){
        Intent i = new Intent(this,VerBitacora.class);
        Datos[8]="Servicios";
        i.putExtra("Datos",Datos);
        startActivity(i);
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
        Intent i = new Intent(this,PantallaPrincipal.class);
        i.putExtra("Datos",Datos);
        startActivity(i);
    }

}