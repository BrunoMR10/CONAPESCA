package com.bmr.conapesca;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bmr.conapesca.Herramientas.Firebase;
import com.bmr.conapesca.Herramientas.Permisos;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class EditaUsuario extends AppCompatActivity {
    public FirebaseStorage storage = FirebaseStorage.getInstance();
    public StorageReference storageReference = storage.getReferenceFromUrl("gs://conapesca-fc179.appspot.com");
    Firebase fb = new Firebase();
    EditText Puestoedit,Correoedit,Numeroedit;
    TextView Nombresedit,correocomen;
    ImageView Frontal;
    ProgressBar CargaFoto1,CargaFoto2;
    String[]Datos;
    Permisos permisos =  new Permisos();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_usuario);
        Nombresedit = (TextView) findViewById(R.id.Nombresedit);
        Puestoedit = (EditText) findViewById(R.id.Puestoedit);
        Correoedit = (EditText) findViewById(R.id.Correoedit);
        Numeroedit = (EditText) findViewById(R.id.Numeroedit);
        correocomen = (TextView) findViewById(R.id.correocomen);
        Frontal = (ImageView) findViewById(R.id.Frontal);
        CargaFoto1 = (ProgressBar) findViewById(R.id.CargaFoto1);
        CargaFoto2 = (ProgressBar) findViewById(R.id.CargaFoto2);
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
        Toast.makeText(this,Datos[1],Toast.LENGTH_SHORT).show();
        ConfiguraInicio();
    }
    private void ConfiguraInicio() {
        SharedPreferences sh = getSharedPreferences("Datos", MODE_PRIVATE);
        Correoedit .setText(Datos[6]);
        Numeroedit.setText(Datos[7]);
        Nombresedit .setText(Datos[1]);
        Puestoedit.setText(Datos[5]);
        CargaImagen(Datos[1],Frontal,"Seguritech",CargaFoto1);

    }
    private void CargaImagen(String Nombre,ImageView Foto,String Where,ProgressBar Cargando){
        Cargando.setVisibility(View.VISIBLE);
        Foto.setVisibility(View.GONE);
        StorageReference Credref = storageReference.child("Credenciales").child(Where).child(Nombre+".jpg");
        try {
            File localFile = File.createTempFile(Nombre, "jpg");
            Credref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    Foto.setImageBitmap(bitmap);
                    Cargando.setVisibility(View.GONE);
                    Foto.setVisibility(View.VISIBLE);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Cargando.setVisibility(View.GONE);
                    Foto.setVisibility(View.VISIBLE);
                    Toast.makeText(EditaUsuario.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException ioException) {
            Cargando.setVisibility(View.GONE);
            Foto.setVisibility(View.VISIBLE);
            Toast.makeText(EditaUsuario.this, ioException.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    public void FotoFrontal(View view){
        if(permisos.Permisos(this)){
            Intent intent2 = new Intent(Intent.ACTION_PICK);
            intent2.setType("image/*");
            anotheractivityResult.launch(intent2);
        }
        else{
            Toast.makeText(this, "Concede permisos",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,permisos.PERMISSIONS_STORAGE,permisos.REQUEST_PERMISSION);
        }
    }
    public void Actualizando(View view){
        CargaFoto1.setVisibility(View.VISIBLE);
        String[]DatosUsuario = new String[]{
                Datos[0],
                Nombresedit.getText().toString(),
                Datos[2],
                Datos[3],
                Datos[4],
                Puestoedit.getText().toString(),
                Correoedit.getText().toString(),
                Numeroedit.getText().toString()
        };
        Frontal.setDrawingCacheEnabled(true);
        Frontal.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) Frontal.getDrawable()).getBitmap();
        //GuardarenGaleria(bitmap,"ProyectoX/Credenciales/"+"Seguritech",Datos[4]);
        fb.SubeCred(Frontal,DatosUsuario,"Seguritech",this);
    }
    ActivityResultLauncher<Intent> anotheractivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Bitmap bitmap, bitmap2;
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            int ancho = 800;
                            float proporcion = ancho / (float) bitmap.getWidth();
                            bitmap2 = Bitmap.createScaledBitmap(bitmap,ancho,(int) (bitmap.getHeight() * proporcion),false);

                        } catch (IOException e) {
                            bitmap2 = null;
                            e.printStackTrace();
                        }

                        Frontal.setImageBitmap(bitmap2);

                        Toast.makeText(EditaUsuario.this,"Foto seleccionada con exito",Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(EditaUsuario.this,"Error al seleccionar foto",Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
