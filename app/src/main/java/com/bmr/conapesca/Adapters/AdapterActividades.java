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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bmr.conapesca.CorrectivosRD;
import com.bmr.conapesca.CorrectivosRF;
import com.bmr.conapesca.DetalleBitacora;
import com.bmr.conapesca.Entidades.Ticket;
import com.bmr.conapesca.InternoRD;
import com.bmr.conapesca.InternoRF;
import com.bmr.conapesca.PreventivoRD;
import com.bmr.conapesca.PreventivoRF;
import com.bmr.conapesca.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdapterActividades extends RecyclerView.Adapter<AdapterActividades.ActividadesViewHolder> {
    ArrayList<com.bmr.conapesca.Entidades.DetalleBitacora> listaActividades;
    ArrayList<com.bmr.conapesca.Entidades.DetalleBitacora> listaOriginal;
    public AdapterActividades(ArrayList<com.bmr.conapesca.Entidades.DetalleBitacora>listaActividades){
        this.listaActividades= listaActividades;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaActividades);
    }
    @NonNull
    @Override
    public AdapterActividades.ActividadesViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detallebitacora, null, false);
        return new AdapterActividades.ActividadesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull AdapterActividades.ActividadesViewHolder holder, int position) {
        holder.Actividad.setText(listaActividades.get(position).getDescripcion());
        holder.HoraFecha.setText(listaActividades.get(position).getHora()+" "+listaActividades.get(position).getFecha());
        holder.TipoActividad.setText(listaActividades.get(position).getActividad());
    }


    @Override
    public int getItemCount() {
        return listaActividades.size();
    }

    public class ActividadesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Actividad, HoraFecha,VerFoto,TipoActividad;

        public ActividadesViewHolder(@NonNull View itemView) {
            super(itemView);
            Actividad = itemView.findViewById(R.id.Actividad);
            VerFoto = itemView.findViewById(R.id.VerFoto);
            HoraFecha = itemView.findViewById(R.id.HoraFecha);
            TipoActividad= itemView.findViewById(R.id.TipoActividad);

        }
        void setOnClickListener() {
            VerFoto.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.VerFoto:

                    break;


            }
        }
    }

    }

