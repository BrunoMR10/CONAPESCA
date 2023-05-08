package com.bmr.conapesca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.bmr.conapesca.Adapters.AdapterActividades;
import com.bmr.conapesca.Adapters.TicketAdapter;
import com.bmr.conapesca.Datos.Datos;
import com.bmr.conapesca.Entidades.Ticket;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetalleBitacora extends AppCompatActivity {
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference refUsuarios = database.getReference("Usuarios");
    public DatabaseReference refTickets = database.getReference("Tickets");
    String TipoServicio;
    String []Datos,Actualiza;
    RecyclerView ActividadesView;
    AdapterActividades adapterActividades;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_bitacora);
        ActividadesView = (RecyclerView) findViewById(R.id.ActividadesView);
        ActividadesView.setLayoutManager(new LinearLayoutManager(this));
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
        ListadeActividades();
    }

    private void ListadeActividades(){
        refTickets.child(Datos[4]).child(Datos[3]).child("Bitacora").addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList<com.bmr.conapesca.Entidades.DetalleBitacora> listaActividades = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String Descripcion = postSnapshot.child("Descripcion").getValue(String.class);
                    String Fecha = postSnapshot.child("Fecha").getValue(String.class);
                    String Hora = postSnapshot.child("Hora").getValue(String.class);
                    String Link = postSnapshot.child("Link").getValue(String.class);
                    String Modificacion = postSnapshot.child("Modificacion").getValue(String.class);
                    String Actividad2 = postSnapshot.child("Actividad").getValue(String.class);
                    String Actividad;
                    if (Modificacion.equals("true")) Actividad = "Modificacion de "+Actividad2;
                    else Actividad = Actividad2;
                    //Toast.makeText(ListaServicios.this,postSnapshot.getKey(),Toast.LENGTH_SHORT).show();
                    System.out.println(postSnapshot.getKey());
                    System.out.println(Descripcion);
                    com.bmr.conapesca.Entidades.DetalleBitacora actividad = null;
                    actividad = new com.bmr.conapesca.Entidades.DetalleBitacora();
                    actividad.setDescripcion(Descripcion);
                    actividad.setHora(Hora);
                    actividad.setFecha(Fecha);
                    actividad.setLink(Link);
                    actividad.setActividad(Actividad);
                    listaActividades.add(actividad);
                }
                adapterActividades = new AdapterActividades(listaActividades);
                ActividadesView.setAdapter(adapterActividades);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}