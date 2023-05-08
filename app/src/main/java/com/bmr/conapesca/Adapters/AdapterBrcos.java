package com.bmr.conapesca.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bmr.conapesca.AsignaTicket;
import com.bmr.conapesca.DetalleBarco;
import com.bmr.conapesca.Entidades.Barcos;
import com.bmr.conapesca.Entidades.DetalleBitacora;
import com.bmr.conapesca.Entidades.Users;
import com.bmr.conapesca.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdapterBrcos extends RecyclerView.Adapter<AdapterBrcos.BarcosViewHolder> {

    ArrayList<Barcos> listaActividades;
    ArrayList<Barcos> listaOriginal;

    public AdapterBrcos(ArrayList<Barcos> listaActividades){
        this.listaActividades= listaActividades;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaActividades);
    }
    @NonNull
    @Override
    public AdapterBrcos.BarcosViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barco, null, false);
        return new AdapterBrcos.BarcosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull AdapterBrcos.BarcosViewHolder holder, int position) {
        holder.Datos = listaActividades.get(position).getDatos();
        holder.Nombrebarco.setText(listaActividades.get(position).getNombreBarco());
        holder.MaterialCasco.setText(listaActividades.get(position).getMaterialcasco());
        holder.matricula.setText(listaActividades.get(position).getMatriculaBarco());
        holder.Permisionari.setText(listaActividades.get(position).getPermisionario());
        holder.Where = listaActividades.get(position).getWhere();
    }
    public void Filtrados(String txtBuscar,int Size1, int Size2){
        int longitud = txtBuscar.length();
        if (longitud==0){
            listaActividades.clear();
            listaActividades.addAll(listaOriginal);

        }else {
            if (Size2 >= Size1 && Size2 != 0){
                listaActividades.clear();
                listaActividades.addAll(listaOriginal);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                List<Barcos> collection = listaActividades.stream()
                        .filter(i -> i.getNombreBarco().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaActividades.clear();
                listaActividades.addAll(collection);
            }else{
                for (Barcos c :listaOriginal){
                    if (c.getNombreBarco().toLowerCase().contains(txtBuscar.toLowerCase())){
                        listaActividades.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaActividades.size();
    }

    public static class BarcosViewHolder extends RecyclerView.ViewHolder {
        TextView Nombrebarco, matricula,MaterialCasco,Permisionari;
        String [] Datos,DatosBarco;
        String Where;
        public BarcosViewHolder(@NonNull View itemView) {
            super(itemView);
            Nombrebarco = itemView.findViewById(R.id.Nombrebarco);
            matricula = itemView.findViewById(R.id.matricula);
            MaterialCasco= itemView.findViewById(R.id.MaterialCasco);
            Permisionari= itemView.findViewById(R.id.Permisionari);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Where.equals("AsignaTicket")) {
                        try {
                            System.out.println(Datos[8]);
                            if (Datos[8].equals("AsignaTicket") || Datos[8].equals("ListaBarcos") || Datos[8].equals("ListaUsuarios")) {
                                Datos[8] = ("ListaBarcos");
                                Datos[15] = Nombrebarco.getText().toString();
                                Datos[11] = matricula.getText().toString();
                                Datos[16] = Permisionari.getText().toString();
                                Datos[14] = MaterialCasco.getText().toString();
                                Context context = v.getContext();
                                Intent intent = new Intent(context, AsignaTicket.class);
                                intent.putExtra("Datos", Datos);
                                intent.putExtra("NombreBarco", Nombrebarco.getText().toString());
                                context.startActivity(intent);
                            } else {
                                System.out.println("Nada");
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }

                    }
                    else{
                        Datos[8] = ("ListaBarcos");
                        Datos[1] = Nombrebarco.getText().toString();
                        Datos[2] = matricula.getText().toString();
                        Datos[3] = Permisionari.getText().toString();
                        Datos[4] = MaterialCasco.getText().toString();
                        Context context = v.getContext();
                        Intent intent = new Intent(context, DetalleBarco.class);
                        intent.putExtra("Datos", Datos);
                        intent.putExtra("NombreBarco", Nombrebarco.getText().toString());
                        context.startActivity(intent);
                    }
                }

            });
        }
    }
}
