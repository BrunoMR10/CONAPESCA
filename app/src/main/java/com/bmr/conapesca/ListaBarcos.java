package com.bmr.conapesca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.bmr.conapesca.Adapters.AdapterActividades;
import com.bmr.conapesca.Adapters.AdapterBrcos;
import com.bmr.conapesca.Entidades.Barcos;
import com.bmr.conapesca.Entidades.DetalleBitacora;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ListaBarcos extends AppCompatActivity implements SearchView.OnQueryTextListener{
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference refBarcos = database.getReference("Barcos");
    AdapterBrcos adapterBrcos;
    RecyclerView ListaBarcos;
    SearchView BuscaBarcos;
    ProgressBar CargandoBarcos;
    String []Datos;
    int Size1=0,Size2=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_barcos);
        BuscaBarcos = findViewById(R.id.BuscaBarcos);
        ListaBarcos = (RecyclerView) findViewById(R.id.ListaBarcos);
        CargandoBarcos = (ProgressBar) findViewById(R.id.CargandoBarcos);
        ListaBarcos.setLayoutManager(new LinearLayoutManager(this));
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
        Barcos2();
    }
    private void Barcos(){
        refBarcos.addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList<Barcos> listaActividades = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String AñoContr = postSnapshot.child("AñoContr").getValue(String.class);
                    String MaterialCasco = postSnapshot.child("MaterialCasco").getValue(String.class);
                    String MatriculaBarco = postSnapshot.child("MatriculaBarco").getValue(String.class);
                    String NombreBarco = postSnapshot.child("NombreBarco").getValue(String.class);
                    String TonBruto = postSnapshot.child("TonBruto").getValue(String.class);
                    String TonNeto = postSnapshot.child("TonNeto").getValue(String.class);
                    String[]DatosBarco = new String[]{
                           postSnapshot.getKey(),
                            postSnapshot.child("MatriculaBarco").getValue(String.class),
                            postSnapshot.child("AñoContr").getValue(String.class),
                            postSnapshot.child("TonBruto").getValue(String.class),
                            postSnapshot.child("TonNeto").getValue(String.class),
                            postSnapshot.child("MaterialCasco").getValue(String.class),
                            postSnapshot.child("Permisionario").getValue(String.class),
                            postSnapshot.child("Rlegal").getValue(String.class),
                            postSnapshot.child("TFijo").getValue(String.class),
                            postSnapshot.child("Tmovil").getValue(String.class),
                            postSnapshot.child("Ciudad").getValue(String.class),
                            postSnapshot.child("Municipio").getValue(String.class),
                            postSnapshot.child("Colonia").getValue(String.class),
                            postSnapshot.child("CP").getValue(String.class),
                            postSnapshot.child("RNPA").getValue(String.class),
                            postSnapshot.child("EntidadFederativa").getValue(String.class),
                            postSnapshot.child("CorreoElectronico").getValue(String.class),
                            postSnapshot.child("TipoPersona").getValue(String.class),
                            postSnapshot.child("MarcaMotor").getValue(String.class),
                            postSnapshot.child("PotenciaMotor").getValue(String.class),
                            postSnapshot.child("SerieMotor").getValue(String.class),
                            postSnapshot.child("FolioCamaron").getValue(String.class),
                            postSnapshot.child("FolioTunidos").getValue(String.class),
                            postSnapshot.child("FolioTiburon").getValue(String.class),
                            postSnapshot.child("FolioEscamas").getValue(String.class),
                            postSnapshot.child("FolioSardia").getValue(String.class),
                            postSnapshot.child("FolioOtros").getValue(String.class),
                            postSnapshot.child("Comercial").getValue(String.class),
                            postSnapshot.child("PescaDidactica").getValue(String.class),
                            postSnapshot.child("Fomento").getValue(String.class),
                            postSnapshot.child("Camaron").getValue(String.class),
                            postSnapshot.child("Tunidos").getValue(String.class),
                            postSnapshot.child("Escama").getValue(String.class),
                            postSnapshot.child("Tiburon").getValue(String.class),
                            postSnapshot.child("Sardina").getValue(String.class),
                            postSnapshot.child("Otros").getValue(String.class),
                    };
                    //Toast.makeText(ListaServicios.this,postSnapshot.getKey(),Toast.LENGTH_SHORT).show();
                    Barcos actividad = null;
                    actividad = new Barcos();
                    actividad.setMaterialcasco(AñoContr);
                    actividad.setMatriculaBarco(MatriculaBarco);
                    actividad.setMaterialcasco(MaterialCasco);
                    actividad.setNombreBarco(NombreBarco);
                    actividad.setTonBruto(TonBruto);
                    actividad.setTonNeto(TonNeto);
                    actividad.setDatosBarco(DatosBarco);
                    actividad.setDatos(Datos);
                    listaActividades.add(actividad);
                }
                adapterBrcos = new AdapterBrcos(listaActividades);
                ListaBarcos.setAdapter(adapterBrcos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Barcos2(){
        CargandoBarcos.setVisibility(View.VISIBLE);
        ListaBarcos.setVisibility(View.GONE);
        BuscaBarcos.setVisibility(View.GONE);
        refBarcos.addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList<Barcos> listaActividades = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //String[]DatosBarco = new String[35];
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                   /* for (int i = 0;i<35;i++){
                        if (i>28 && i<33){
                            DatosBarco[i]="";
                        }
                        else{
                            DatosBarco[i]=postSnapshot.child("DatoBarco"+i).getValue(String.class);
                        }
                    }*/
                    String Matricula = postSnapshot.child("DatoBarco4").getValue(String.class);
                    String NombreBarco = postSnapshot.child("DatoBarco3").getValue(String.class);
                    String PueroBase = postSnapshot.child("DatoBarco2").getValue(String.class);
                    String Permisionario = postSnapshot.child("DatoBarco21").getValue(String.class);
                    Barcos actividad = null;
                    actividad = new Barcos();
                    actividad.setMatriculaBarco(Matricula);
                    actividad.setNombreBarco(NombreBarco);
                    actividad.setMaterialcasco(PueroBase);
                    actividad.setPermisionario(Permisionario);
                    actividad.setDatos(Datos);
                    actividad.setWhere(Datos[8]);
                    listaActividades.add(actividad);
                }
                //Collections.sort(listaActividades, Collections.reverseOrder());
                adapterBrcos = new AdapterBrcos(listaActividades);
                ListaBarcos.setAdapter(adapterBrcos);
                CargandoBarcos.setVisibility(View.GONE);
                BuscaBarcos.setVisibility(View.VISIBLE);
                ListaBarcos.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        BuscaBarcos.setOnQueryTextListener(this);
    }


    public void AñadeBarco(View view){
        Intent i = new Intent(this,RegistroBarco.class);
        startActivity(i);
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        Size1 = newText.length();
        //System.out.println("Cambio de texto");
        adapterBrcos.Filtrados(newText,Size1,Size2);
        Size2 = Size1;
        return false;
    }
}