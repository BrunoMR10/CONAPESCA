package com.bmr.conapesca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SearchView;

import com.bmr.conapesca.Adapters.TicketAdapter;
import com.bmr.conapesca.Entidades.Ticket;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class VerBitacora extends AppCompatActivity implements SearchView.OnQueryTextListener{
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference refUsuarios = database.getReference("Usuarios");
    public DatabaseReference refTickets = database.getReference("Tickets");
    String []Datos,Actualiza;
    TicketAdapter adapterTickets;
    RecyclerView Ticketsview;
    SearchView buscarTickets;
    ProgressBar Actualizando;
    RadioButton InternoButton,CorrectivoButton,PreventivoButton,InstalacionButton,NoinstallButton;
    String TipoServicio;
    int Size1=0,Size2=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_bitacora);
        buscarTickets = findViewById(R.id.SearchTicket);
        Actualizando = (ProgressBar)findViewById(R.id.Actualizando);
        Ticketsview = (RecyclerView) findViewById(R.id.Tickets_View);
        InternoButton = (RadioButton)findViewById(R.id.InternoButton);
        CorrectivoButton = (RadioButton)findViewById(R.id.CorrectivoButton);
        PreventivoButton = (RadioButton)findViewById(R.id.PreventivoButton);
        InstalacionButton = (RadioButton)findViewById(R.id.InstalacionButton);
        NoinstallButton= (RadioButton)findViewById(R.id.NoinstallButton);
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
        ConfiguraInicio();


    }
    private void ConfiguraInicio(){
        TipoServicio="Instalacion";
        InstalacionButton.setChecked(true);
        ListadeTickets();
    }

    private void ListadeTickets(){
        refTickets.child(TipoServicio).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String [] Tickets = new String[(int) snapshot.getChildrenCount()];
                int i = 0;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Tickets[i] = postSnapshot.getKey();
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
    public void VerInternos(View view){
        TipoServicio="Interno";
        InternoButton.setChecked(true);
        PreventivoButton.setChecked(false);
        CorrectivoButton.setChecked(false);
        InstalacionButton.setChecked(false);
        NoinstallButton.setChecked(false);
        ListadeTickets();
    }
    public void VerCorrectivos(View view){
        TipoServicio="Correctivo";
        InternoButton.setChecked(false);
        PreventivoButton.setChecked(false);
        CorrectivoButton.setChecked(true);
        InstalacionButton.setChecked(false);
        NoinstallButton.setChecked(false);
        ListadeTickets();
    }
    public void VerPreventivos(View view){
        TipoServicio="Preventivo";
        InternoButton.setChecked(false);
        PreventivoButton.setChecked(true);
        CorrectivoButton.setChecked(false);
        InstalacionButton.setChecked(false);
        NoinstallButton.setChecked(false);
        ListadeTickets();
    }
    public void VerInstalacion(View view){
        TipoServicio="Instalacion";
        InternoButton.setChecked(false);
        PreventivoButton.setChecked(false);
        CorrectivoButton.setChecked(false);
        InstalacionButton.setChecked(true);
        NoinstallButton.setChecked(false);
        ListadeTickets();
    }
    public void NoInstalacion(View view){
        TipoServicio="Cancelado";
        InternoButton.setChecked(false);
        PreventivoButton.setChecked(false);
        CorrectivoButton.setChecked(false);
        InstalacionButton.setChecked(false);
        NoinstallButton.setChecked(true);
        ListadeTickets();
    }
    private void ObtenDatosTickets(String[]Tickets){
        ArrayList<Ticket> listatickets = new ArrayList<>();
        for (int i = 0;i<Tickets.length;i++){
                if (Tickets[i].equals("Consecutivo Ticket vigente"))System.out.println("No");
                else {
                    Ticket ticket = null;
                    ticket = new Ticket();
                    ticket.setTicket(Tickets[i]);
                    ticket.setTipoServicio(TipoServicio);
                    ticket.setWhere("Bitacora");
                    listatickets.add(ticket);
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
}