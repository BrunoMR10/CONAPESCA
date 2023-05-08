package com.bmr.conapesca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.bmr.conapesca.Adapters.TicketAdapter;
import com.bmr.conapesca.Entidades.Barcos;
import com.bmr.conapesca.Entidades.Ticket;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetalleBarco extends AppCompatActivity {
    TextView BarcoName,RNPName,PermisionarioName,PuertobaseName,NoSerieConBox,NoSerieBlueTraker,
            NoSerieIridium,IMEIIridium,SelloConBox,SelloBlueTrajer;
    RecyclerView BitacoraView;
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference refTickets = database.getReference("Tickets");
    public DatabaseReference refBarcos = database.getReference("Barcos");
    TicketAdapter adapterTickets;
    String []Datos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_barco);
        BitacoraView = (RecyclerView) findViewById(R.id.BitacoraView);
        BitacoraView.setLayoutManager(new LinearLayoutManager(this));
        BarcoName = (TextView) findViewById(R.id.BarcoName);
        RNPName = (TextView) findViewById(R.id.RNPName);
        PermisionarioName = (TextView) findViewById(R.id.PermisionarioName);
        PuertobaseName = (TextView) findViewById(R.id.PuertobaseName);
        NoSerieConBox = (TextView) findViewById(R.id.NoSerieConBox);
        NoSerieBlueTraker = (TextView) findViewById(R.id.NoSerieBlueTraker);
        NoSerieIridium = (TextView) findViewById(R.id.NoSerieIridium);
        IMEIIridium = (TextView) findViewById(R.id.IMEIIridium);
        SelloConBox = (TextView) findViewById(R.id.SelloConBox);
        SelloBlueTrajer = (TextView) findViewById(R.id.SelloBlueTrajer);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                Datos = extras.getStringArray("Datos");
            }
        }
        else {
            Datos = (String[]) savedInstanceState.getSerializable("Datos");
        }
        ListadeTickets();
    }

    private void ListadeTickets(){
        BarcoName .setText(Datos[1]);
        RNPName.setText(Datos[2]);
        PermisionarioName .setText(Datos[3]);
        PuertobaseName .setText(Datos[4]);
        refBarcos.child(Datos[2]).child("Tickets").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String [] Tickets = new String[(int) snapshot.getChildrenCount()];
                String [] TipoServicio = new String[(int) snapshot.getChildrenCount()];
                int i = 0;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    Tickets[i] = postSnapshot.getKey();
                    TipoServicio[i] = postSnapshot.getValue().toString();
                    if (postSnapshot.getKey().contains("S-")){
                        ObtenNumerosdeSerie(postSnapshot.getKey(),postSnapshot.getValue().toString());
                    }
                    System.out.println(postSnapshot.getValue());
                    i++;
                    //Toast.makeText(ListaServicios.this,postSnapshot.getKey(),Toast.LENGTH_SHORT).show();
                    System.out.println(postSnapshot.getKey());
                }
                ObtenDatosTickets(Tickets,TipoServicio);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void ObtenNumerosdeSerie(String Ticket,String TipoServicio){

        System.out.println("Tipo de servicio"+TipoServicio);
        System.out.println("Ticket"+Ticket);
        refTickets.child(TipoServicio).child(Ticket).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    System.out.println("Ticket encontrado");
                    String NumerodeSerieBlueTraker = snapshot.child("NSerieTransreceptor").getValue(String.class);
                    String NumerodeSerieConBox = snapshot.child("NSerieConBox").getValue(String.class);
                    String NumerodeSerieIridium = snapshot.child("NSerieIridium").getValue(String.class);
                    String IMEIiridium = snapshot.child("IMEIIridium").getValue(String.class);
                    String SelloconBox = snapshot.child("NoSelloConBox").getValue(String.class);
                    String SelloBlueTraker = snapshot.child("NoSelloTrans").getValue(String.class);
                    NoSerieConBox .setText(NumerodeSerieConBox);
                    NoSerieBlueTraker.setText(NumerodeSerieBlueTraker);
                    NoSerieIridium.setText(NumerodeSerieIridium);
                    IMEIIridium.setText(IMEIiridium);
                    SelloConBox.setText(SelloconBox);
                    SelloBlueTrajer.setText(SelloBlueTraker);
                }
                else{
                    System.out.println("Ticket NO encontrado");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void ObtenDatosTickets(String[]Tickets,String[]TipoServicio){
        ArrayList<Ticket> listatickets = new ArrayList<>();
        for (int i = 0;i<Tickets.length;i++){
            if (Tickets[i].equals("Consecutivo Ticket vigente"))System.out.println("No");
            else {
                Ticket ticket = null;
                ticket = new Ticket();
                ticket.setTicket(Tickets[i]);
                ticket.setTipoServicio(TipoServicio[i]);
                ticket.setWhere("Bitacora");
                listatickets.add(ticket);
            }
        }
        adapterTickets = new TicketAdapter(listatickets);
        BitacoraView.setAdapter(adapterTickets);
        //buscarTickets.setOnQueryTextListener(this);
    }


}