package com.bmr.conapesca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bmr.conapesca.Datos.Datos;
import com.bmr.conapesca.Documentos.InstalacionOficial;
import com.bmr.conapesca.Herramientas.Firebase;
import com.bmr.conapesca.Herramientas.Permisos;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    Permisos permisos = new Permisos();
    Firebase fb = new Firebase();
    EditText correo,contreseña;
    Datos dt = new Datos();
    InstalacionOficial instalacionOficial = new InstalacionOficial();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        correo = (EditText)findViewById(R.id.Email_set);
        contreseña = (EditText)findViewById(R.id.Password_set);
        if(permisos.Permisos(this)){
        }
        else{
            Toast.makeText(this, "Concede permisos",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,permisos.PERMISSIONS_STORAGE,permisos.REQUEST_PERMISSION);
        }
    }
    public void CrearArchiv(View view){
        try {

            /*Bitmap bitmap;
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.seguritech);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapData = stream.toByteArray();
            ImageData imageData = ImageDataFactory.create(bitmapData);
            //instalacionOficial.CreaArchivo2(dt.DatosTicketAReporte[3], imageData);*/
            Intent i = new Intent(this, Notificaciones.class);
            //i.putExtra("Iden", "NI");
           // i.putExtra("Datos", dt.DatosTicketAReporte);
            startActivity(i);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean ValidaDatos(){
        String CorreoElectronico = correo.getText().toString();
        String Contraseña = contreseña.getText().toString();
        if (TextUtils.isEmpty(CorreoElectronico)){
            correo.setError("Escribe un Email");
            correo.requestFocus();
            return false;
        }else if (TextUtils.isEmpty(Contraseña)){
            contreseña.setError("Escribe una Contreseña");
            contreseña.requestFocus();
            return false;
        }else {
            return IngresoOnline(CorreoElectronico,Contraseña);
        }

    }
    private boolean IngresoOnline(String CorreoElectronico,String Contraseña){
        fb.Ingresa(this,CorreoElectronico,Contraseña,this);
        return true;
    }

    public void ToPantallaRegistra(View v){
        Intent i =  new Intent(this,RegistraUsuario.class);
        startActivity(i);
    }
    public void ToPantallaPrincipal(View v){
        //ValidaDatos();
        if (!ValidaDatos()){
            Toast.makeText(this,"No se puedo acceder",Toast.LENGTH_SHORT).show();
        }else{

        }
    }
}
