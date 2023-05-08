package com.bmr.conapesca.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bmr.conapesca.AsignaTicket;
import com.bmr.conapesca.Datos.Datos;
import com.bmr.conapesca.Entidades.Ticket;
import com.bmr.conapesca.Entidades.Users;
import com.bmr.conapesca.R;
import com.bmr.conapesca.ReporteInstalacion;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdapterUsuarios extends RecyclerView.Adapter<AdapterUsuarios.UsuariosViewHolder> {
    public FirebaseStorage storage = FirebaseStorage.getInstance();
    public StorageReference storageReference = storage.getReferenceFromUrl("gs://conapesca-fc179.appspot.com");
    ArrayList<Users> listaUsuarios;
    ArrayList<Users> listaOriginal;

    public AdapterUsuarios(ArrayList<Users> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaUsuarios);
    }

    @NonNull
    @Override
    public AdapterUsuarios.UsuariosViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuarios, null, false);
        return new UsuariosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull AdapterUsuarios.UsuariosViewHolder holder, int position) {
        String[] Datos;
        //StorageReference Credref = Credref = storageReference.child("Credenciales").child("Seguritech").child(listaUsuarios.get(position).getNombreCompleto()+".jpg");
        holder.setOnClickListener();
        Datos = new String[listaUsuarios.get(position).getDatos().length];
        for (int i = 0; i < listaUsuarios.get(position).getDatos().length; i++) {
            Datos[i] = listaUsuarios.get(position).getDatos()[i];
        }

        holder.uid = listaUsuarios.get(position).getUid();
        holder.Favoritos.setVisibility(View.GONE);
        holder.Edita.setVisibility(View.GONE);
        holder.Nombre.setText(listaUsuarios.get(position).getNombreCompleto());
        holder.Puesto.setText(listaUsuarios.get(position).getPuesto());
        holder.Correo.setText(listaUsuarios.get(position).getCorreo());
        holder.Telefono.setText(listaUsuarios.get(position).getNumero());
        holder.Datos = Datos;
        holder.Cargafoto.setVisibility(View.GONE);
        holder.Credencial.setVisibility(View.GONE);

        if (Datos == null){
            System.out.println("Datos null");
        }



            /*try {
                File localFile;
                if (Datos[1].equals("Favoritos") || Datos[1].equals("General") || Datos[1].equals("PreventivoANAM") || Datos[1].equals("CorrectivoANAM")) {
                    localFile = File.createTempFile(listaUsuarios.get(position).getNombreCompleto2() + " " + "Frontal", "jpg");
                } else {
                    localFile = File.createTempFile(listaUsuarios.get(position).getNombreCompleto(), "jpg");
                }

                Credref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        holder.Cargafoto.setVisibility(View.GONE);
                        holder.Credencial.setVisibility(View.GONE);
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        holder.Credencial.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(Usuarios.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException ioException) {
                //ioException.printStackTrace();
            }*/

    }
    public void Filtrados(String txtBuscar){
        int longitud = txtBuscar.length();
        if (longitud==0){
            listaUsuarios.clear();
            listaUsuarios.addAll(listaOriginal);

        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                List<Users> collection = listaUsuarios.stream()
                        .filter(i -> i.getNombreCompleto().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaUsuarios.clear();
                listaUsuarios.addAll(collection);
            }else{
                for (Users c :listaOriginal){
                    if (c.getNombreCompleto().toLowerCase().contains(txtBuscar.toLowerCase())){
                        listaUsuarios.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public class UsuariosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Nombre,Correo,Telefono,Puesto;
        Switch Favoritos;
        ImageButton Edita;
        ImageView Credencial;
        ProgressBar Cargafoto;
        String [] Datos;
        String uid;
        public UsuariosViewHolder(@NonNull View itemView) {
            super(itemView);
            Nombre = itemView.findViewById(R.id.NameView);
            Correo = itemView.findViewById(R.id.CorreoView);
            Telefono = itemView.findViewById(R.id.NumeroView);
            Puesto = itemView.findViewById(R.id.PuestoView);
            Credencial = itemView.findViewById(R.id.CredencialView);
            Favoritos = itemView.findViewById(R.id.Favorito);
            Edita = itemView.findViewById(R.id.Edita);
            Cargafoto = itemView.findViewById(R.id.Cargafoto);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(Datos[8]);
                   if (Datos[8].equals("AsignaTicket")||Datos[8].equals("ListaBarcos")||Datos[8].equals("ListaUsuarios")){
                       Datos[0]=uid;
                       Datos[8] = ("ListaUsuarios");
                       Datos[1]=Nombre.getText().toString();
                       Datos[2]=Puesto.getText().toString();
                       Context context = v.getContext();
                       Intent intent = new Intent(context, AsignaTicket.class);
                       intent.putExtra("Datos",Datos);
                       context.startActivity(intent);
                   }else if (Datos[8].equals("Ticket")){
                       Datos[0]=uid;
                       Datos[8] = ("ListaUsuarios");
                       Datos[1]=Nombre.getText().toString();
                       Datos[2]=Puesto.getText().toString();
                       Context context = v.getContext();
                       Intent intent = new Intent(context, ReporteInstalacion.class);
                       intent.putExtra("Datos",Datos);
                       context.startActivity(intent);
                   }
                }

            });

        }

        void setOnClickListener(){
            Favoritos.setOnClickListener(this);
            Edita.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.Edita:

                    break;
                case R.id.Favorito:

                    break;

            }
        }
    }

    }

