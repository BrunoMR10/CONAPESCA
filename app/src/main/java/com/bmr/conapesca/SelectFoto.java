package com.bmr.conapesca;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bmr.conapesca.Datos.Datos;
import com.bmr.conapesca.Entidades.RFP;
import com.bmr.conapesca.Herramientas.Firebase;
import com.bmr.conapesca.Herramientas.Permisos;
import com.bmr.conapesca.Herramientas.SacaleListener;
import com.bmr.conapesca.ServiceHelper.DriveServiceHelper;
import com.bmr.conapesca.ServiceHelper.SheetsServiceHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SelectFoto extends AppCompatActivity {
    Permisos permisos = new Permisos();
    RFPAdapter adapterFotos;
    String rutaImagen,rutacarpeta,rutacarpeta2,Where;
    ImageView FotoPreview;
    EditText Descripcion;
    Bitmap bitmap,bitmap2;
    ProgressBar SubiendoFoto;
    com.bmr.conapesca.Datos.Datos dt = new Datos();
    ReporteInstalacion reporteInstalacion = new ReporteInstalacion();
    String Datos[];
    String ID,Modificacion;
    LinearLayout IRIDIUM,Transreceptor,Conbox,SelloConbox,SelloBlue;
    EditText SerieConboxV,SerieIridium,IMEIIridiumV,SerieTransreceptor,SelloConboxV,SelloBlueV;
    String Ticket,LinkCarpetaFotografica,LinkBitacora;
    Boolean CambioFoto,Sube;
    Firebase fb = new Firebase();
    private DriveServiceHelper mDriveServiceHelper;
    private SheetsServiceHelper mSheetServiceHelper;
    private static final int REQUEST_CODE_SIGN_IN = 1;
    private ScaleGestureDetector detector;
    private float xBeging =0;
    private  float yBeging =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_foto);
        FotoPreview = (ImageView) findViewById(R.id.FotoPreview);
        Descripcion = (EditText) findViewById(R.id.DescripcionView);
        SubiendoFoto = (ProgressBar) findViewById(R.id.SubiendoFoto);
        IRIDIUM = (LinearLayout) findViewById(R.id.IRIDIUM);
        Transreceptor = (LinearLayout) findViewById(R.id.Transreceptor);
        Conbox = (LinearLayout) findViewById(R.id.Conbox);
        IRIDIUM = (LinearLayout) findViewById(R.id.IRIDIUM);
        SelloConbox = (LinearLayout) findViewById(R.id.SelloConbox);
        SelloBlue = (LinearLayout) findViewById(R.id.SelloBlue);
        SerieConboxV = (EditText) findViewById(R.id.SerieConboxV);
        SerieIridium = (EditText) findViewById(R.id.SerieIridium);
        IMEIIridiumV = (EditText) findViewById(R.id.IMEIIridiumV);
        SerieTransreceptor = (EditText) findViewById(R.id.SerieTransreceptor);
        SelloConboxV = (EditText) findViewById(R.id.SelloConboxV);
        SelloBlueV = (EditText) findViewById(R.id.SelloBlueV);



        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                Datos = extras.getStringArray("Datos");
                ID = extras.getString("ID");
                Sube = extras.getBoolean("Sube");
                Where = extras.getString("Where");

            }
        }
        else{
            Datos = (String[]) savedInstanceState.getSerializable("Datos");
            ID = (String) savedInstanceState.getSerializable("ID");
            Sube = (Boolean) savedInstanceState.getSerializable("Sube");
            Where = (String) savedInstanceState.getSerializable("Where");
        }

        if (Datos!=null){
            Ticket = Datos[3];
            LinkCarpetaFotografica =Datos[9];
            LinkBitacora = Datos[10];
        }
        else {
            SharedPreferences sh = getSharedPreferences("TempShared", MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sh.edit();
            Ticket = sh.getString("Ticket","");
            LinkCarpetaFotografica = sh.getString("LinkCarpetaFotografica","");
            LinkBitacora = sh.getString("LinkBitacora","");
            ID = sh.getString("ID","");
            bitmap2 = ObtenImagen("Temp",-1);
            if (bitmap2!= null) System.out.println("Se encontro");
        }
        CambioFoto = false;
        System.out.println(ID);
        rutacarpeta2 = Ticket;
        ConfiguraInicio();
        requestSignIn();

        //Toast.makeText(this,ID,Toast.LENGTH_SHORT).show();
    }
    private void ConfiguraInicio(){

        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        int IDint;
        if (ID == null) {IDint = 0;Modificacion = "false";}
        else{ IDint = Integer.parseInt(ID);Modificacion = "true";}
        if (Ticket.contains("C-")){
            if (ID.equals("4")) Transreceptor.setVisibility(View.GONE);
            else  if (ID.equals("5")) IRIDIUM.setVisibility(View.GONE);
            else  if (ID.equals("7")) Conbox.setVisibility(View.GONE);
            else  if (ID.equals("9")) SelloConbox.setVisibility(View.GONE);
            else  if (ID.equals("10")) SelloBlue.setVisibility(View.GONE);
        }else{
            if (ID.equals("4")) Transreceptor.setVisibility(View.VISIBLE);
            else  if (ID.equals("5")) IRIDIUM.setVisibility(View.VISIBLE);
            else  if (ID.equals("7")) Conbox.setVisibility(View.VISIBLE);
            else  if (ID.equals("9")) SelloConbox.setVisibility(View.VISIBLE);
            else  if (ID.equals("10")) SelloBlue.setVisibility(View.VISIBLE);
        }


        String Desc;
        Desc = sh.getString("ComentarioFoto"+(IDint),"");
        Descripcion.setText(Desc);
        SelloConboxV.setText(sh.getString("SelloConBox",""));
        SelloBlueV.setText(sh.getString("SelloBlueTraker",""));
        SerieIridium.setText(sh.getString("NSerieIridium",""));
        IMEIIridiumV.setText(sh.getString("IMEIIridium",""));
        SerieTransreceptor.setText(sh.getString("NSerieTransreceptor",""));
        SerieConboxV.setText(sh.getString("NSerieConBox",""));
        CargaFoto(IDint);



    }
    private void CargaFoto(int IDint){
        try{
            if (IDint>0){
                bitmap2 = ObtenImagen(rutacarpeta2, IDint);
                if (bitmap2!=null){
                    FotoPreview.setImageBitmap(bitmap2);
                    xBeging = FotoPreview.getScaleX();
                    yBeging = FotoPreview.getScaleY();
                    detector = new ScaleGestureDetector(this,new SacaleListener(FotoPreview));
                }

            }

        }
        catch (Exception e){
            System.out.println(e);
        }

    }
    public boolean onTouchEvent(MotionEvent event){
        try {
            detector.onTouchEvent(event);
            return super.onTouchEvent(event);
        }catch (Exception e){
            return false;
        }
    }

    private Bitmap ObtenImagen(String Ticket,int i){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(Ticket, Context.MODE_PRIVATE);
        File file;
        if (i == -1)  {file = new File(directory, "FotoTemp"+ ".png");
                        System.out.println("Obteniendo Temp");}
        else  file = new File(directory, "Foto"+i+ ".png");

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        return bitmap;
    }
    private boolean ValidaFoto(){
        Drawable drawable = FotoPreview.getDrawable();
        boolean hasImage = (drawable != null);
        if (hasImage && (drawable instanceof BitmapDrawable)){
            return true;
        }
        else{
            return false;
        }


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void ActualizaBitacora(String Comentario,int ID){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm");
        String Fecha= String.valueOf(dtf.format(LocalDateTime.now()));
        String Hora = String.valueOf(dtf2.format(LocalDateTime.now()));
        mDriveServiceHelper.SubirFoto(LinkCarpetaFotografica,Ticket,ID )
                .addOnSuccessListener(link->   mSheetServiceHelper.ActualizaBitacora(LinkBitacora,Comentario,Hora,Fecha,link,ID)
                        .addOnSuccessListener(ok ->  RegistraenFirebase(Comentario,Hora,Fecha,link,ok,ID))
                        .addOnFailureListener(Fail -> System.out.println(Fail)))
                .addOnFailureListener(Fail -> System.out.println(Fail));

      System.out.println(LinkBitacora);

    }
    private void RegistraenFirebase(String Comentario,String Hora,String Fecha,String Link, int ID3,int ID2){
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        Toast.makeText(SelectFoto.this, "Foto"+(ID)+" en Drive", Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.putBoolean("Up"+(ID),true);
        myEdit.putBoolean("ChangeFoto"+(ID),false);
        myEdit.commit();

        String[] DatosBitacora = new String[]{
                Comentario,
                Hora,
                Fecha,
                Link,
                Modificacion,
                "Actividad "+ID2
        };

        fb.RegistraBitacoraenTicket(Datos,this,ID3,DatosBitacora);
        //MostrarFotos(Datos[3]);
        //Regresa();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void Dones () {

        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        int IDint;
        if (ID == null) IDint = sh.getInt("ID", 1);
        else IDint = Integer.parseInt(ID);
        System.out.println(IDint);
        String Desc;
        Desc = Descripcion.getText().toString();
        if (!ValidaFoto()){
            Toast.makeText(this,"Porfavor toma o selecciona una foto",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(Desc)){
            Descripcion.setError("Añade una descripción");
            Descripcion.requestFocus();
        }else{
            FotoPreview.setVisibility(View.GONE);
            SubiendoFoto.setVisibility(View.VISIBLE);
            if (CambioFoto){
                //Drawable d = FotoPreview.getDrawable();
                //bitmap2 = FotoPreview.getDrawingCache();
                if (bitmap2== null)bitmap2 = ObtenImagen("Temp",-1);
                GuardaenInternal(bitmap2,rutacarpeta2,"Foto"+IDint);
                ActualizaBitacora(Desc,IDint);
            }

            ActualizaShared(Ticket,Desc,IDint);
            Regresa();

        }
    }
    private void ActualizaShared(String Ticket,String Desc,int IDint){
        System.out.println(Ticket);
        System.out.println(ID);
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.putString("ComentarioFoto"+(IDint),Desc);
        myEdit.putBoolean("ReporteFoto"+(IDint), true);
        myEdit.putString("SelloConBox",SelloConboxV.getText().toString());
        myEdit.putString("SelloBlueTraker", SelloBlueV.getText().toString());
        myEdit.putString("NSerieIridium", SerieIridium.getText().toString());
        myEdit.putString("IMEIIridium", IMEIIridiumV.getText().toString());
        myEdit.putString("NSerieTransreceptor", SerieTransreceptor.getText().toString());
        myEdit.putString("NSerieConBox", SerieConboxV.getText().toString());
        myEdit.commit();
        if (Where != null){
            IDint++;
            myEdit.putInt("ID", IDint);
            myEdit.commit();
        }
        System.out.println("---------DONES------");
        System.out.println(sh.getString("SelloConBox",""));
        System.out.println(sh.getString("SelloBlueTraker",""));
        System.out.println(sh.getString("NSerieIridium",""));
        System.out.println(sh.getString("IMEIIridium",""));
        System.out.println(sh.getString("NSerieTransreceptor",""));
        System.out.println(sh.getString("NSerieConBox",""));
        System.out.println("---------DONES------");
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Done (View view){
        Dones();

    }
    public void SeleccionarFoto(View view){
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
    public void AbreCamara(View view){
        if(permisos.Permisos(this)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File imagenarchivo = null;
            try{
                imagenarchivo = crearImagen();
            }catch (IOException ex){
                Log.e("Error",ex.toString());
            }

            if (imagenarchivo != null) {
                Uri fotoUri = FileProvider.getUriForFile(this,"com.bmr.conapesca.fileprovider",imagenarchivo);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,fotoUri);
                someActivityResultLauncher.launch(intent);
            }
        }
        else{
            Toast.makeText(this, "Concede permisos",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,permisos.PERMISSIONS_STORAGE,permisos.REQUEST_PERMISSION);
        }

    }
    private File crearImagen() throws IOException {
        String nombeImagen = "foto_";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_DCIM);
        File imagen = File.createTempFile(nombeImagen,".jpg",directorio);
        rutaImagen=imagen.getAbsolutePath();
        return imagen;
    }
    private void GuardaenInternal(Bitmap bitmap, String rutacarpeta, String nombre){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(rutacarpeta, Context.MODE_PRIVATE);
        File file = new File(directory, nombre+ ".png");
            Log.d("path", file.toString());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }

    }
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes

                        try {
                            CambioFoto = true;
                            bitmap = BitmapFactory.decodeFile(rutaImagen);
                            int ancho = 800;
                            float proporcion = ancho / (float) bitmap.getWidth();
                            bitmap2 = Bitmap.createScaledBitmap(bitmap,ancho,(int) (bitmap.getHeight() * proporcion),false);
                            FotoPreview.setImageBitmap(bitmap2);
                            xBeging = FotoPreview.getScaleX();
                            yBeging = FotoPreview.getScaleY();
                            detector = new ScaleGestureDetector(SelectFoto.this,new SacaleListener(FotoPreview));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(SelectFoto.this,"Foto tomada con exito",Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(SelectFoto.this,"Error al tomar foto",Toast.LENGTH_SHORT).show();
                    }
                }
            });
    ActivityResultLauncher<Intent> anotheractivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        try {
                            CambioFoto = true;
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            int ancho = 800;
                            float proporcion = ancho / (float) bitmap.getWidth();
                            bitmap2 = Bitmap.createScaledBitmap(bitmap, ancho, (int) (bitmap.getHeight() * proporcion), false);
                            FotoPreview.setImageBitmap(bitmap2);
                            xBeging = FotoPreview.getScaleX();
                            yBeging = FotoPreview.getScaleY();
                            detector = new ScaleGestureDetector(SelectFoto.this,new SacaleListener(FotoPreview));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
                        Toast.makeText(SelectFoto.this, "Foto seleccionada con exito", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor myEdit = sh.edit();
                        myEdit.putBoolean("ChangeFoto"+(ID),true);
                        myEdit.commit();

                    } else {
                        Toast.makeText(SelectFoto.this, "Error al seleccionar foto", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private void Regresa(){
        Intent i;
        if (Datos[4].equals("Correctivo")){
            if (Where.equals("CorrectivoT-")) i = new Intent(this,CorrectivosRD.class);
            else i = new Intent(this,ReporteInstalacion.class);
        }
        else if (Datos[4].equals("Preventivo")) i = new Intent(this,PreventivoRF.class);
        else if (Datos[4].equals("Instalacion")) i = new Intent(this,ReporteInstalacion.class);
        else i = new Intent(this,ReporteInstalacion.class);
        i.putExtra("Datos",Datos);
        startActivity(i);
    }

    //// ESCANEO

    public void Scan1(View view){
        SharedPreferences sh = getSharedPreferences("TempShared", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        if (bitmap2!= null) GuardaenInternal(bitmap2,"Temp","FotoTemp");
        if (Datos != null){
            myEdit.putString("Ticket",Datos[3]);
            myEdit.putString("LinkCarpetaFotografica",Datos[9]);
            myEdit.putString("LinkBitacora",Datos[10]);
            myEdit.putString("ID",ID);
            myEdit.commit();
        }
        else{
            myEdit.putString("Ticket",Ticket);
            myEdit.putString("LinkCarpetaFotografica",LinkCarpetaFotografica);
            myEdit.putString("LinkBitacora",LinkBitacora);
            myEdit.putString("ID",ID);
            myEdit.commit();
        }



        SharedPreferences sh2 = getSharedPreferences(Ticket, MODE_PRIVATE);
        myEdit = sh2.edit();
        myEdit.putBoolean("Scan1",true);
        myEdit.putString("SelloConBox",SelloConboxV.getText().toString());
        myEdit.putString("SelloBlueTraker", SelloBlueV.getText().toString());
        myEdit.putString("NSerieIridium", SerieIridium.getText().toString());
        myEdit.putString("IMEIIridium", IMEIIridiumV.getText().toString());
        myEdit.putString("NSerieTransreceptor", SerieTransreceptor.getText().toString());
        myEdit.putString("NSerieConBox", SerieConboxV.getText().toString());
        myEdit.commit();
        Escaneo ("No. Serie Iridium");
    }
    public void Scan2(View view){
        SharedPreferences sh = getSharedPreferences("TempShared", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        if (bitmap2!= null) GuardaenInternal(bitmap2,"Temp","FotoTemp");
        if (Datos != null){
            myEdit.putString("Ticket",Datos[3]);
            myEdit.putString("LinkCarpetaFotografica",Datos[9]);
            myEdit.putString("LinkBitacora",Datos[10]);
            myEdit.putString("ID",ID);
            myEdit.commit();
        }
        else{
            myEdit.putString("Ticket",Ticket);
            myEdit.putString("LinkCarpetaFotografica",LinkCarpetaFotografica);
            myEdit.putString("LinkBitacora",LinkBitacora);
            myEdit.putString("ID",ID);
            myEdit.commit();
        }

        SharedPreferences sh2 = getSharedPreferences(Ticket, MODE_PRIVATE);
        myEdit = sh2.edit();
        myEdit.putBoolean("Scan2",true);
        myEdit.putString("SelloConBox",SelloConboxV.getText().toString());
        myEdit.putString("SelloBlueTraker", SelloBlueV.getText().toString());
        myEdit.putString("NSerieIridium", SerieIridium.getText().toString());
        myEdit.putString("IMEIIridium", IMEIIridiumV.getText().toString());
        myEdit.putString("NSerieTransreceptor", SerieTransreceptor.getText().toString());
        myEdit.putString("NSerieConBox", SerieConboxV.getText().toString());
        myEdit.commit();
        Escaneo ("IMEI Iridium");
    }
    public void Scan3(View view){
        SharedPreferences sh = getSharedPreferences("TempShared", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        if (bitmap2!= null) GuardaenInternal(bitmap2,"Temp","FotoTemp");

        if (Datos != null){
            myEdit.putString("Ticket",Datos[3]);
            myEdit.putString("LinkCarpetaFotografica",Datos[9]);
            myEdit.putString("LinkBitacora",Datos[10]);
            myEdit.putString("ID",ID);
            myEdit.commit();
        }
        else{
            myEdit.putString("Ticket",Ticket);
            myEdit.putString("LinkCarpetaFotografica",LinkCarpetaFotografica);
            myEdit.putString("LinkBitacora",LinkBitacora);
            myEdit.putString("ID",ID);
            myEdit.commit();
        }
        SharedPreferences sh2 = getSharedPreferences(Ticket, MODE_PRIVATE);
        myEdit = sh2.edit();
        myEdit.putBoolean("Scan3",true);
        myEdit.putString("SelloConBox",SelloConboxV.getText().toString());
        myEdit.putString("SelloBlueTraker", SelloBlueV.getText().toString());
        myEdit.putString("NSerieIridium", SerieIridium.getText().toString());
        myEdit.putString("IMEIIridium", IMEIIridiumV.getText().toString());
        myEdit.putString("NSerieTransreceptor", SerieTransreceptor.getText().toString());
        myEdit.putString("NSerieConBox", SerieConboxV.getText().toString());
        myEdit.commit();
        Escaneo ("No. Serie Transreceptor");

    }
    public void Scan4(View view){
        SharedPreferences sh = getSharedPreferences("TempShared", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        if (bitmap2!= null) GuardaenInternal(bitmap2,"Temp","FotoTemp");
        if (Datos != null){
            myEdit.putString("Ticket",Datos[3]);
            myEdit.putString("LinkCarpetaFotografica",Datos[9]);
            myEdit.putString("LinkBitacora",Datos[10]);
            myEdit.putString("ID",ID);
            myEdit.commit();
        }
        else{
            myEdit.putString("Ticket",Ticket);
            myEdit.putString("LinkCarpetaFotografica",LinkCarpetaFotografica);
            myEdit.putString("LinkBitacora",LinkBitacora);
            myEdit.putString("ID",ID);
            myEdit.commit();
        }

        SharedPreferences sh2 = getSharedPreferences(Ticket, MODE_PRIVATE);
        myEdit.putString("SelloConBox",SelloConboxV.getText().toString());
        myEdit.putString("SelloBlueTraker", SelloBlueV.getText().toString());
        myEdit.putString("NSerieIridium", SerieIridium.getText().toString());
        myEdit.putString("IMEIIridium", IMEIIridiumV.getText().toString());
        myEdit.putString("NSerieTransreceptor", SerieTransreceptor.getText().toString());
        myEdit.putString("NSerieConBox", SerieConboxV.getText().toString());
        myEdit = sh2.edit();
        myEdit.putBoolean("Scan4",true);
        myEdit.commit();
        Escaneo ("No. Serie Conbox");

    }
    public void Escaneo (String Lectura){
        IntentIntegrator intentIntegrator = new IntentIntegrator(SelectFoto.this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Lector - "+Lectura);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.initiateScan();
    }


    @Override
    public void onBackPressed() {
          Regresa();
    }
    ///////////////////////////SERVICIOS GOOGLE//////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,resultData);
        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:
                if (resultCode == Activity.RESULT_OK && resultData != null) {
                    handleSignInResult(resultData);
                }
                break;
        }
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(this,"Cancelado",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,result.getContents(),Toast.LENGTH_SHORT).show();
                SharedPreferences sh = getSharedPreferences("TempShared", MODE_PRIVATE);
                String Ticket = sh.getString("Ticket","");
                SharedPreferences sh2 = getSharedPreferences(Ticket, MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh2.edit();
                bitmap2 = ObtenImagen("Temp",-1);
                if (bitmap2!= null) System.out.println("Se encontro");

                if (sh2.getBoolean("Scan1",false)){
                    myEdit.putBoolean("Scan1",false);
                    myEdit.putString("NSerieIridium",result.getContents());
                    myEdit.commit();
                }
                else if (sh2.getBoolean("Scan2",false)){
                    myEdit.putBoolean("Scan2",false);
                    myEdit.putString("IMEIIridium",result.getContents());
                    myEdit.commit();
                }
                else if (sh2.getBoolean("Scan3",false)){
                    myEdit.putBoolean("Scan3",false);
                    myEdit.putString("NSerieTransreceptor",result.getContents());
                    myEdit.commit();
                }
                else if (sh2.getBoolean("Scan4",false)){
                    myEdit.putBoolean("Scan4",false);
                    myEdit.putString("NSerieConBox",result.getContents());
                    myEdit.commit();
                }
               ConfiguraInicio();
            }
        }

        super.onActivityResult(requestCode, resultCode, resultData);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleSignInResult(Intent result) {
        GoogleSignIn.getSignedInAccountFromIntent(result)
                .addOnSuccessListener(googleAccount -> {
                    // Use the authenticated account to sign in to the Drive service.
                    GoogleAccountCredential credential =
                            GoogleAccountCredential.usingOAuth2(
                                    this, Collections.singleton(DriveScopes.DRIVE_FILE));
                    credential.setSelectedAccount(googleAccount.getAccount());
                    Drive googleDriveService =
                            new Drive.Builder(
                                    AndroidHttp.newCompatibleTransport(),
                                    new GsonFactory(),
                                    credential)
                                    .setApplicationName("Drive API Migration")
                                    .build();

                    mDriveServiceHelper = new DriveServiceHelper(googleDriveService);
                    GoogleAccountCredential credential2 = GoogleAccountCredential.usingOAuth2(
                            this, Arrays.asList(SheetsScopes.SPREADSHEETS)).setBackOff(new ExponentialBackOff());
                    credential2.setSelectedAccount(googleAccount.getAccount());
                    Sheets googleSheetsService = new Sheets.Builder(
                            AndroidHttp.newCompatibleTransport(),
                            JacksonFactory.getDefaultInstance(), credential2)
                            .setApplicationName("Sheets API Migration")
                            .build();
                    mSheetServiceHelper = new SheetsServiceHelper(googleSheetsService);

                    if (Sube){
                        CambioFoto = true;
                        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sh.edit();
                        myEdit.putBoolean("ChangeFoto"+(ID),true);
                        myEdit.commit();
                        Dones();
                        Toast.makeText(this,"Subiendo Foto a Carpeta",Toast.LENGTH_SHORT).show();
                    }
                    // The DriveServiceHelper encapsulates all REST API and SAF functionality.
                    // Its instantiation is required before handling any onClick actions
                });
    }
    private void requestSignIn() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                        .requestScopes(new Scope(SheetsScopes.SPREADSHEETS))
                        .requestScopes(new Scope(SheetsScopes.SPREADSHEETS_READONLY))
                        .build();
        GoogleSignInClient client = GoogleSignIn.getClient(this, signInOptions);

        // The result of the sign-in Intent is handled in onActivityResult.
        startActivityForResult(client.getSignInIntent(), REQUEST_CODE_SIGN_IN);
    }
}