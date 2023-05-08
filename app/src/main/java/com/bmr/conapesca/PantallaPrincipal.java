package com.bmr.conapesca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bmr.conapesca.Datos.Cache;
import com.bmr.conapesca.Datos.Datos;
import com.bmr.conapesca.Documentos.Convenio;
import com.bmr.conapesca.Documentos.Convenios;
import com.bmr.conapesca.Documentos.PruebaMetododocumentos;
import com.bmr.conapesca.Herramientas.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PantallaPrincipal extends AppCompatActivity {
    public FirebaseStorage storage = FirebaseStorage.getInstance();
    public StorageReference storageReference = storage.getReferenceFromUrl("gs://conapesca-fc179.appspot.com");
    Firebase fb = new Firebase();
    Datos dt = new Datos();
    Button ToServicios,ToUsuarios,ToANAM;
    TextView Usuario;
    String[]Datos;
    Convenio convenio = new Convenio();
    PruebaMetododocumentos pruebaMetododocumentos = new PruebaMetododocumentos();
    Convenios convenios = new Convenios();

    Cache cache = new Cache();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        Usuario = (TextView) findViewById(R.id.Usuario);
        ToServicios = (Button) findViewById(R.id.ToServicios);
        ToUsuarios = (Button) findViewById(R.id.toUsuarios);
        ToANAM = (Button) findViewById(R.id.toANAM);
        ToServicios.setVisibility(View.GONE);
        ToUsuarios.setVisibility(View.GONE);
        ToANAM.setVisibility(View.GONE);

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
        if (Datos[8].equals("Acceso")) GuardaDatosSP(Datos);
        ObtenCredencial(Datos[1]);
        ToServicios.setVisibility(View.VISIBLE);
        //ToANAM.setVisibility(View.VISIBLE);
        ToUsuarios.setVisibility(View.VISIBLE);
        Usuario.setText(Datos[1]);
        Cache.deleteCache(this);
    }
    private void GuardaDatosSP(String [] Datos){
        SharedPreferences sharedPreferences = getSharedPreferences("Datos",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        for(int i=0;i<8;i++){
            myEdit.putString("Datos"+i, Datos[i]);
        }
        myEdit.commit();
    }
    public void ToUsuarios(View view){
        Datos[8]="PantallaPrincipal";
        Intent intent = new Intent(this,ListaUsuarios.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    public void ToEditaUsuarios(View view){
        Datos[8]="PantallaPrincipal";
        Intent intent = new Intent(this,EditaUsuario.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    public void ToServicios(View view){
        Datos[8]="PantallaPrincipal";
        Intent intent = new Intent(this,Servicios.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    public void ToBarcos(View view){
        Datos[8]="PantallaPrincipal";
        Intent intent = new Intent(this,ListaBarcos.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    public void ToNotificaciones(View view){
        Datos[8]="PantallaPrincipal";
        Intent intent = new Intent(this,Notificaciones.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    public void ToPruebaDocumentos(View view) throws IOException {
        //convenio.CuerpoDocumento("Prueba");
        //pruebaMetododocumentos.CuerpoDocumento("Prueba");
        convenios.CreaDocumento("Prueba");
        Datos[8] = "PantallaPrincipal";
        Intent intent = new Intent(this, PruebaDocumentos.class);
        intent.putExtra("Nombre", "Prueba");
        startActivity(intent);
    }
    public void CerrarSesion(View view){
        fb.CerrarSesion(this);

    }
    @Override
    public void onBackPressed() {
    }
    public void ObtenCredencial(String NombreCompleto){
        String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
        File directorioImagenes = new File(ExternalStorageDirectory + "CONAPESCA/Credenciales/Seguritech");
        if (!directorioImagenes.exists()){
            directorioImagenes.mkdirs();

        }
        else{
        }
        File Credencial = new File(ExternalStorageDirectory + "CONAPESCA/Credenciales/Seguritech/"+NombreCompleto+".png");
        if (!Credencial.exists()){
            StorageReference Credref = storageReference.child("Credenciales").child("Seguritech").child(NombreCompleto+".jpg");
            try {
                File localFile = File.createTempFile(NombreCompleto, "jpg");
                Credref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PantallaPrincipal.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException ioException) {
                Toast.makeText(PantallaPrincipal.this, ioException.toString(), Toast.LENGTH_SHORT).show();
            }

        }
        else{

        }
    }



}