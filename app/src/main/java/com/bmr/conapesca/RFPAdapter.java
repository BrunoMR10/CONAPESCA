package com.bmr.conapesca;

import static android.content.Context.MODE_PRIVATE;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bmr.conapesca.Entidades.RFP;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RFPAdapter extends RecyclerView.Adapter<RFPAdapter.RFPViewHolder> {
    ArrayList<RFP> listafotos;
    ArrayList<RFP> listaOriginal;
    public RFPAdapter(ArrayList<RFP>listafotos){
        this.listafotos= listafotos;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listafotos);
    }
    @NonNull
    @Override
    public RFPViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rfp, null, false);
        return new RFPViewHolder(view);
    }




    private Boolean[] ObtenSharedPreference(String Ticket,int ID){
        Boolean []EstatusFoto = new Boolean[2];
        System.out.println(Ticket);
        SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket, MODE_PRIVATE);
        EstatusFoto[0]=sh.getBoolean("ChangeFoto"+(ID),false);
        System.out.println("ChangeFoto"+(ID));
        EstatusFoto[1]=sh.getBoolean("Up"+(ID),false);
        return EstatusFoto;
    }
    @Override
    public void onBindViewHolder(@NonNull RFPViewHolder holder, int position) {
        String[] Datos;
        Datos = new String[listafotos.get(position).getDatos().length];
        for (int i=0 ; i<listafotos.get(position).getDatos().length;i++){
            Datos[i] = listafotos.get(position).getDatos()[i];
        }
        holder.Datos = Datos;
        holder.setOnClickListener();
        holder.Descripcion1.setText(listafotos.get(position).getComentario1());
        holder.Descripcion2.setText(listafotos.get(position).getComentario2());
        if (listafotos.get(position).getFoto1() != null){
            holder.Foto1.setImageBitmap(listafotos.get(position).getFoto1());
            holder.Sube.setVisibility(View.VISIBLE);
        }
        if (listafotos.get(position).getFoto2() != null){
            holder.Foto2.setImageBitmap(listafotos.get(position).getFoto2());
            holder.Sube2.setVisibility(View.VISIBLE);
        }
        holder.ID1= listafotos.get(position).getID1();
        holder.ID2= listafotos.get(position).getID2();
        listafotos.get(position).getFoto2();

        if ( ObtenSharedPreference(Datos[3],listafotos.get(position).getID1())[0]) {
            holder.Subiendo1.setVisibility(View.VISIBLE);
        }else holder.Subiendo1.setVisibility(View.GONE);
        if ( ObtenSharedPreference(Datos[3],listafotos.get(position).getID2())[0]) {
            holder.Subiendo2.setVisibility(View.VISIBLE);
        }else holder.Subiendo2.setVisibility(View.GONE);

        if ( ObtenSharedPreference(Datos[3],listafotos.get(position).getID1())[1]) {
            holder.Up1.setVisibility(View.VISIBLE);
            holder.Down1.setVisibility(View.GONE);
        }else {
            holder.Up1.setVisibility(View.GONE);
            holder.Down1.setVisibility(View.VISIBLE);
        }
        if ( ObtenSharedPreference(Datos[3],listafotos.get(position).getID2())[1]) {
            holder.Up2.setVisibility(View.VISIBLE);
            holder.Down2.setVisibility(View.GONE);
        }else {
            holder.Up2.setVisibility(View.GONE);
            holder.Down2.setVisibility(View.VISIBLE);
        }

        /*holder.Up1.setVisibility(View.GONE);
        holder.Up2.setVisibility(View.GONE);
        holder.Down1.setVisibility(View.GONE);
        holder.Down2.setVisibility(View.GONE);
        holder.Subiendo1.setVisibility(View.GONE);
        holder.Subiendo2.setVisibility(View.GONE);*/
    }

    @Override
    public int getItemCount() {
        return listafotos.size();
    }

    public class RFPViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Descripcion1,Descripcion2;
        ImageView Foto1,Foto2;
        String []Datos;
        ImageView Down1,Down2,Up1,Up2;
        ProgressBar Subiendo2,Subiendo1;
        ImageButton Sube,Sube2;
        int ID1,ID2;
        Context context;
        public RFPViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            Descripcion1 = itemView.findViewById(R.id.Comen1);
            Foto1 = itemView.findViewById(R.id.Foto1);
            Descripcion2 = itemView.findViewById(R.id.Comen2);
            Foto2 = itemView.findViewById(R.id.Foto2);
            Down1= itemView.findViewById(R.id.Down1);
            Down2= itemView.findViewById(R.id.Down2);
            Up1= itemView.findViewById(R.id.Up1);
            Up2= itemView.findViewById(R.id.Up2);
            Subiendo2= itemView.findViewById(R.id.Subiendo2);
            Subiendo1= itemView.findViewById(R.id.Subiendo1);
            Sube= itemView.findViewById(R.id.Sube);
            Sube2= itemView.findViewById(R.id.Sube2);
        }
        void setOnClickListener(){
            Foto1.setOnClickListener(this);
            Foto2.setOnClickListener(this);
            Sube.setOnClickListener(this);
            Sube2.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.Foto1:
                    Context context = v.getContext();
                    Intent intent = new Intent(context, SelectFoto.class);
                    intent.putExtra("Datos",Datos);
                    intent.putExtra("ID",String.valueOf(ID1));
                    intent.putExtra("Sube",false);
                    context.startActivity(intent);
                    break;
                case R.id.Foto2:
                    context = v.getContext();
                    intent = new Intent(context, SelectFoto.class);
                    intent.putExtra("Datos",Datos);
                    intent.putExtra("ID",String.valueOf(ID2));
                    intent.putExtra("Sube",false);
                    context.startActivity(intent);
                    break;

                case R.id.Sube:
                    context = v.getContext();
                    intent = new Intent(context, SelectFoto.class);
                    intent.putExtra("Datos",Datos);
                    intent.putExtra("ID",String.valueOf(ID1));
                    intent.putExtra("Sube",true);
                    context.startActivity(intent);
                    break;
                case R.id.Sube2:
                    context = v.getContext();
                    intent = new Intent(context, SelectFoto.class);
                    intent.putExtra("Datos",Datos);
                    intent.putExtra("ID",String.valueOf(ID2));
                    intent.putExtra("Sube",true);
                    context.startActivity(intent);
                    break;


            }
        }
    }
}
