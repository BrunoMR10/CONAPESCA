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
import android.widget.Toast;

import com.bmr.conapesca.Adapters.TicketAdapter;
import com.bmr.conapesca.Datos.Datos;
import com.bmr.conapesca.Entidades.Ticket;
import com.bmr.conapesca.Herramientas.Firebase;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Collections;

public class ListaServicios extends AppCompatActivity implements SearchView.OnQueryTextListener{
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference refUsuarios = database.getReference("Usuarios");
    public DatabaseReference refTickets = database.getReference("Tickets");
    Datos dt = new Datos();
    String []Datos,Actualiza;
    TicketAdapter adapterTickets;
    RecyclerView Ticketsview;
    SearchView buscarTickets;
    ProgressBar Actualizando;
    String Clave;
    int Size1=0,Size2=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_servicios);
        buscarTickets = findViewById(R.id.buscarTickets);
        Actualizando = (ProgressBar)findViewById(R.id.Actualizando);
        Ticketsview = (RecyclerView) findViewById(R.id.Tickets_View);
        Ticketsview.setLayoutManager(new LinearLayoutManager(this));
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
        if (Datos[8].equals("Correctivo")) Clave= "C-";
        else if (Datos[8].equals("Interno")) Clave= "I-";
        else if (Datos[8].equals("Instalacion")) Clave= "S-";
        else Clave = "P-";

        MisTickets();

    }

    private void MisTickets(){
        refUsuarios.child(Datos[0]).child("Tickets").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String [] Tickets = new String[(int) snapshot.getChildrenCount()];
                //String [] Fechas = new String[(int) snapshot.getChildrenCount()];
                //String [] Barcos = new String[(int) snapshot.getChildrenCount()];
                //String [] RNP = new String[(int) snapshot.getChildrenCount()];
                int i = 0;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Tickets[i] = postSnapshot.getKey();
                        //Fechas[i] = postSnapshot.child("FechaInicio").getValue().toString();
                        //Barcos[i] = postSnapshot.child("NombreBarco").getValue().toString();
                        //RNP[i] = postSnapshot.child("Barco").getValue().toString();
                        i++;
                        //Toast.makeText(ListaServicios.this,postSnapshot.getKey(),Toast.LENGTH_SHORT).show();
                        System.out.println(postSnapshot.getKey());
                }
                ObtenDatosTickets(Tickets);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void ObtenDatosTickets(String[]Tickets){
        ArrayList<Ticket> listatickets = new ArrayList<>();
         for (int i = 0;i<Tickets.length;i++){
             if (Tickets[i].contains(Clave)) {
                 Ticket ticket = null;
                 ticket = new Ticket();
                 ticket.setTicket(Tickets[i]);
                 //ticket.setNombreBarco(Barcos[i]);
                 //ticket.setFechaInicio(Fechas[i]);
                 ticket.setTipoServicio(Datos[8]);
                 listatickets.add(ticket);
             }
             else {
                 System.out.println("Ticket Pertenece a otra categoría");
             }
         }
        //Collections.sort(listatickets, Collections.reverseOrder());
         adapterTickets = new TicketAdapter(listatickets);
         Ticketsview.setAdapter(adapterTickets);
         buscarTickets.setOnQueryTextListener(this);
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        Size1 = newText.length();
        //System.out.println("Cambio de texto");
        adapterTickets.Filtrados(newText,Size1,Size2);
        Size2 = Size1;
        return false;
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,Servicios.class);
        i.putExtra("Datos",Datos);
        startActivity(i);
    }



}