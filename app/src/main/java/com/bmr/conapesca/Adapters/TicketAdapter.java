package com.bmr.conapesca.Adapters;

import static android.content.Context.MODE_PRIVATE;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.Size;
import androidx.recyclerview.widget.RecyclerView;


import com.bmr.conapesca.AsignaTicket;
import com.bmr.conapesca.CorrectivosRD;
import com.bmr.conapesca.CorrectivosRF;
import com.bmr.conapesca.Datos.Datos;
import com.bmr.conapesca.DetalleBitacora;
import com.bmr.conapesca.Entidades.Barcos;
import com.bmr.conapesca.Entidades.Ticket;
import com.bmr.conapesca.InternoRD;
import com.bmr.conapesca.InternoRF;
import com.bmr.conapesca.PreventivoRD;
import com.bmr.conapesca.PreventivoRF;
import com.bmr.conapesca.R;
import com.bmr.conapesca.RInstalacion;
import com.bmr.conapesca.ReporteInstalacion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketsViewHolder>{
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference refBarcos = database.getReference("Barcos");
    ArrayList<Ticket> listaTickets;
    ArrayList<Ticket> listaOriginal;
    Datos dt = new Datos();
    public TicketAdapter(ArrayList<Ticket> listaTickets){
        this.listaTickets= listaTickets;
        listaOriginal = new ArrayList<>();
        //Collections.sort(listaOriginal, Collections.reverseOrder());
        listaOriginal.addAll(listaTickets);
    }

    @NonNull
    @Override
    public TicketsViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tickets, null, false);
        return new TicketsViewHolder(view);
    }
    private String[] ObtenSharedPreference(){
        String [] Datos = new String[dt.Datos.length];
        SharedPreferences sh = getApplicationContext().getSharedPreferences("Datos", MODE_PRIVATE);
        for (int i = 0;i<dt.Datos.length;i++){
            Datos[i]=sh.getString("Datos"+String.valueOf(i), "");
        }
        return Datos;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull TicketAdapter.TicketsViewHolder holder, int position) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refTickets = database.getReference("Tickets");
        holder.FechaHora1Edit.setVisibility(View.GONE);
        holder.FechaHora2Edit.setVisibility(View.GONE);
        holder.Estatus.setVisibility(View.GONE);
        holder.Cargandoinformacion.setVisibility(View.VISIBLE);
        holder.setOnClickListener();
        holder.Ticket.setText(listaTickets.get(position).getTicket());
        holder.TipoServicio = listaTickets.get(position).getTipoServicio();

        //holder.Barco = listaTickets.get(position).getNombreBarco();
        //holder.FechaInicio = listaTickets.get(position).getFechaInicio();

        if (listaTickets.get(position).getTipoServicio().equals("Instalacion")) holder.RDigital.setVisibility(View.GONE);
        try{
        refTickets.child(listaTickets.get(position).getTipoServicio()).child(listaTickets.get(position).getTicket()).addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String DatosTicket[];
                String FechaInicio= snapshot.child("FechaInicio").getValue(String.class);
                String HoraInicio = snapshot.child("HoraInicio").getValue(String.class);
                String Estatus = snapshot.child("Estatus").getValue(String.class);
                String NombreIngeniero = snapshot.child("NombreIngeniero").getValue(String.class);
                String PuestoIngeniero = snapshot.child("PuestoIngeniero").getValue(String.class);
                String Link1 = snapshot.child("Link").getValue(String.class);
                String Link2 = snapshot.child("Link2").getValue(String.class);
                String Link3 = snapshot.child("Link3").getValue(String.class);
                String NombreBarco = snapshot.child("NombreBarco").getValue(String.class);
                String RNPBarco = snapshot.child("Barco").getValue(String.class);
                String NumeroOficio = snapshot.child("Noficio").getValue(String.class);
                String FechaOficio = snapshot.child("FechaOficio").getValue(String.class);
                String LocalidadPuerto = snapshot.child("LocalidadPuerto").getValue(String.class);
                String Permisonario = snapshot.child("Permisionario").getValue(String.class);
                String UID = snapshot.child("UID").getValue(String.class);

                holder.FechaHora1Edit.setVisibility(View.VISIBLE);
                holder.FechaHora2Edit.setVisibility(View.VISIBLE);
                holder.Estatus.setVisibility(View.VISIBLE);
                holder.Cargandoinformacion.setVisibility(View.GONE);
                holder.Estatus.setText(Estatus);
                holder.FechaHora1Edit.setText(FechaInicio);
                holder.FechaHora2Edit.setText(HoraInicio);
                holder.RNBarco.setText(RNPBarco);
                holder.NBarco.setText(NombreBarco);




                if (listaTickets.get(position).getWhere()!=null){
                    holder.Ingeniero.setVisibility(View.VISIBLE);
                    holder.IngView.setVisibility(View.VISIBLE);
                    holder.Ingeniero.setText(NombreIngeniero);
                    holder.Actividad.setVisibility(View.VISIBLE);
                    if (ObtenSharedPreference()[5].equals("Coordinador operativo")||
                            ObtenSharedPreference()[5].equals("Gerente operativo")||
                            ObtenSharedPreference()[1].equals("Bruno Mendoza  Ruiz ") ||
                            ObtenSharedPreference()[1].equals("Rodrigo Rafael Castro Rodríguez")){
                             holder.Edit.setVisibility(View.VISIBLE);
                    }
                    else {
                        holder.Edit.setVisibility(View.GONE);
                    }

                }else{
                    holder.Ingeniero.setVisibility(View.GONE);
                    holder.IngView.setVisibility(View.GONE);
                    holder.Buttons.setVisibility(View.VISIBLE);
                    holder.Edit.setVisibility(View.GONE);

                }
                holder.ticket = listaTickets.get(position).getTicket();
                holder.Datos = new String[]{
                        "",
                        NombreIngeniero,
                        PuestoIngeniero,
                        listaTickets.get(position).getTicket(),
                        listaTickets.get(position).getTipoServicio(),
                        FechaInicio,
                        HoraInicio,
                        Estatus,
                        Link1,
                        Link2,
                        Link3,
                        RNPBarco,
                        NumeroOficio,
                        FechaOficio,
                        LocalidadPuerto,
                        NombreBarco,
                        Permisonario,
                        UID

                };
                holder.RNPA = RNPBarco;

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }catch (Exception e){
       System.out.println(e);
    }
       // holder.FechaHora1Edit.setText(listaTickets.get(position).getFechaInicio());//
        //holder.FechaHora2Edit.setText(listaTickets.get(position).getHoraInicio());
    }
    public void Filtrados(String txtBuscar, int Size1, int Size2){
        try {
            int longitud = txtBuscar.length();
            if (Size1 == 0) {
                listaTickets.clear();
                listaTickets.addAll(listaOriginal);

            } else {
                if (Size2 >= Size1 && Size2 != 0) {
                    listaTickets.clear();
                    listaTickets.addAll(listaOriginal);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    List<Ticket> collection = listaTickets.stream()
                            .filter(i -> i.getTicket().toLowerCase().contains(txtBuscar.toLowerCase()))
                            .collect(Collectors.toList());
                    System.out.println(collection.size());
                    /*if (collection.size()==0) collection = listaTickets.stream()
                            .filter(i -> i.getNombreBarco().toLowerCase().contains(txtBuscar.toLowerCase()))
                            .collect(Collectors.toList());*/
                    listaTickets.clear();
                    listaTickets.addAll(collection);
                } else {
                    for (Ticket c : listaOriginal) {
                        if (c.getTicket().toLowerCase().contains(txtBuscar.toLowerCase())) {
                            listaTickets.add(c);
                        }
                    }
                }
            }
            notifyDataSetChanged();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    @Override
    public int getItemCount() {
        return listaTickets.size();
    }

    public class TicketsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView FechaHora1view, FechaHora2view,
                FechaHora1Edit, FechaHora2Edit, Ticket, Estatus,Ingeniero,IngView,RNBarco,NBarco;
        Button RDigital, RFotografico,Actividad;
        String[] Datos;
        ProgressBar Cargandoinformacion;
        String TipoServicio,Where,ticket,FechaInicio,Barco;
        LinearLayout Buttons;
        String RNPA;
        ImageButton Edit;


        public TicketsViewHolder(@NonNull View itemView) {
            super(itemView);
            FechaHora1Edit = itemView.findViewById(R.id.FechaHora1Edit);
            FechaHora2Edit = itemView.findViewById(R.id.FechaHora2Edit);
            Ticket = itemView.findViewById(R.id.TicketName_View);
            Estatus = itemView.findViewById(R.id.Estatus);
            RDigital = itemView.findViewById(R.id.RDigital);
            RFotografico = itemView.findViewById(R.id.RFotografico);
            Cargandoinformacion = itemView.findViewById(R.id.Cargandoinformacion);
            Buttons = itemView.findViewById(R.id.Buttons);
            Ingeniero = itemView.findViewById(R.id.Ingeniero);
            IngView = itemView.findViewById(R.id.IngView);
            Actividad= itemView.findViewById(R.id.Actividad);
            RNBarco = itemView.findViewById(R.id.RNBarco);
            NBarco = itemView.findViewById(R.id.NBarco);
            Edit = itemView.findViewById(R.id.Edit);
        }

        void setOnClickListener() {
            RDigital.setOnClickListener(this);
            RFotografico.setOnClickListener(this);
            Actividad.setOnClickListener(this);
            Edit.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.RDigital:
                    Intent intent;
                    Context context = v.getContext();
                    if (TipoServicio.equals("Correctivo")) intent = new Intent(context, CorrectivosRD.class);
                    else if (TipoServicio.equals("Interno")) intent = new Intent(context, InternoRD.class);
                    else intent = new Intent(context, PreventivoRD.class);
                    intent.putExtra("Datos",Datos);
                    context.startActivity(intent);
                    break;
                case R.id.RFotografico:
                    refBarcos.child(RNPA).addListenerForSingleValueEvent(new ValueEventListener() {
                        ArrayList<Barcos> listaActividades = new ArrayList<>();
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String[]DatosBarco = new String[67];
                             for (int i = 0;i<67;i++){
                                  DatosBarco[i]=snapshot.child("DatoBarco"+i).getValue(String.class);
                                 }
                            Intent intent2;
                            Context context2 = v.getContext();
                            if (TipoServicio.equals("Correctivo")) intent2 = new Intent(context2, CorrectivosRF.class);
                            else if (TipoServicio.equals("Interno")) intent2 = new Intent(context2, ReporteInstalacion.class);
                            else if (TipoServicio.equals("Instalacion")) intent2 = new Intent(context2, ReporteInstalacion.class);
                            else intent2 = new Intent(context2, PreventivoRF.class);
                            intent2.putExtra("Datos",Datos);
                            intent2.putExtra("DatosBarco",DatosBarco);
                            context2.startActivity(intent2);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    break;
                case R.id.Actividad:
                    Intent intent3;
                    Context context3 = v.getContext();
                    intent3 = new Intent(context3, DetalleBitacora.class);
                    intent3.putExtra("Datos",Datos);
                    context3.startActivity(intent3);
                    break;
                case R.id.Edit:
                    Datos[0] = ticket;
                    Intent intent4;
                    Context context4 = v.getContext();
                    intent4= new Intent(context4, AsignaTicket.class);
                    intent4.putExtra("DatosTickets",Datos);
                    context4.startActivity(intent4);
                    break;

            }
        }
    }
}
