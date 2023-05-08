package com.bmr.conapesca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bmr.conapesca.Adapters.AdapterFotos;
import com.bmr.conapesca.Datos.Datos;
import com.bmr.conapesca.Documentos.Correctivo;
import com.bmr.conapesca.Documentos.Interno;
import com.bmr.conapesca.Entidades.DatosFoto;
import com.bmr.conapesca.Entidades.Fotos;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class InternoRF extends AppCompatActivity implements View.OnClickListener {
    Datos dt = new Datos();
    Interno interno = new Interno();
    String []Datos;
    RecyclerView FotosView;
    TextView TrimestreRFC,TicketRFC,FechaRFC,
            AduanaRFC,EquipoRFC,SerieRFC,UbicacionRFC;
    AdapterFotos adapterFotos;
    DatosFoto Data;
    boolean Reporte;
    String ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interno_rf);
        TrimestreRFC = (TextView) findViewById(R.id.TrimestreRFC);
        TicketRFC = (TextView) findViewById(R.id.TicketRFC);
        FechaRFC = (TextView) findViewById(R.id.FechaRFC);
        AduanaRFC = (TextView) findViewById(R.id.AduanaRFC);
        EquipoRFC = (TextView) findViewById(R.id.EquipoRFC);
        SerieRFC = (TextView) findViewById(R.id.SerieRFC);
        UbicacionRFC = (TextView) findViewById(R.id.UbicacionRFC);
        FotosView = (RecyclerView) findViewById(R.id.FotosRFC);
        FotosView.setLayoutManager(new LinearLayoutManager(this));

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                Datos = extras.getStringArray("Datos");
                ID = extras.getString("ID");
                Reporte = extras.getBoolean("Reporte");
            }
        }
        else{
            Datos = (String[]) savedInstanceState.getSerializable("Datos");
            ID = (String) savedInstanceState.getSerializable("ID");
            Reporte = (boolean) savedInstanceState.getSerializable("Reporte");
        }
        if (ID!=null)ActualizaEstadoReporte();

        View AñadeFoto = findViewById(R.id.añadefoto);
        AñadeFoto.setOnClickListener(this);
        View SubeFotos = findViewById(R.id.Subefotos);
        SubeFotos.setOnClickListener(this);
        View CreaDocumento = findViewById(R.id.CreaDocumento);
        CreaDocumento.setOnClickListener(this);
        View RFC= findViewById(R.id.RFC);
        RFC.setOnClickListener(this);
        ConfiguraIncio();
        MuestraFotos();
    }
    private void ConfiguraIncio(){
        FechaRFC.setText(Datos[5]+" "+Datos[6]);
        TicketRFC.setText(Datos[3]);
        TrimestreRFC.setText(Datos[4]);
    }
    private void ActualizaEstadoReporte(){
        SharedPreferences sh = getSharedPreferences(Datos[3], MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.putBoolean("ReporteFoto"+(ID), Reporte);
        myEdit.commit();
    }
    private void MuestraFotos() {
        ArrayList<Fotos> ListaFotos = new ArrayList<>();
        String Desc;
        Boolean ReporteFoto;
        SharedPreferences sh = getSharedPreferences(Datos[3], MODE_PRIVATE);
        int ID = sh.getInt("ID", 1);
        if (ID > 1) {
            for (int i =1;i<ID;i++){
                ReporteFoto=sh.getBoolean("ReporteFoto"+(i),true);
                Desc = sh.getString("ComentarioFoto"+(i),"");
                Bitmap bitmapi;
                bitmapi = ObtenImagen(Datos[3],i);
                Fotos fotos = null;
                fotos = new Fotos();
                fotos.setDescripcion2(Desc);
                fotos.setId2((i));
                fotos.setFoto2(bitmapi);
                fotos.setDatos(Datos);
                fotos.setReporteFotos(ReporteFoto);
                ListaFotos.add(fotos);
                adapterFotos = new AdapterFotos(ListaFotos);
                FotosView.setAdapter(adapterFotos);
                System.out.println(Desc);

            }
        }

    }
    private Bitmap ObtenImagen(String Ticket,int i){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(Ticket, Context.MODE_PRIVATE);
        File file = new File(directory, "Foto"+i+ ".png");
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        return bitmap;
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.añadefoto:
                AñadeFoto();
                break;
            case R.id.Subefotos:
                //SubeFotosC();
                break;
            case R.id.CreaDocumento:
                CrearArchivo();
                break;
            case R.id.RFC:
                //ToRDC();
                break;
        }
    }
    private void AñadeFoto(){
        Intent i = new Intent(this,SelectFoto.class);
        String Where ="Interno";
        i.putExtra("Where",Where);
        i.putExtra("Datos",Datos);
        startActivity(i);
    }

    private  void CrearArchivo(){
        try {
            Bitmap bitmap;
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.seguritech);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapData = stream.toByteArray();
            ImageData imageData = ImageDataFactory.create(bitmapData);
            SharedPreferences sh = getSharedPreferences(Datos[3], MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sh.edit();
            int ID = sh.getInt("ID", 1);
            interno.CreaRF(Datos,imageData,ID-1,ObtenComentarios());
            Intent intent = new Intent(this,PDFViewer.class);
            intent.putExtra("Iden","RFD");
            intent.putExtra("Datos",Datos);
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    private String[]  ObtenComentarios(){
        SharedPreferences sh = getSharedPreferences(Datos[3], MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        int ID = sh.getInt("ID", 1);
        String [] Descripciones = new String[ID-1];
        String Desc;
        Boolean ReporteFoto;
        for (int i = 1;i<ID;i++){
            ReporteFoto=sh.getBoolean("ReporteFoto"+(i),true);
            Desc = sh.getString("ComentarioFoto"+(i),"");
            if (ReporteFoto) Descripciones[i-1] = Desc;
            else Descripciones[i-1] = "";
            System.out.println("Desc:"+Descripciones[i-1]);
        }
        return Descripciones;
    }
    private String[] ObtenSharedPreference(){
        Datos = new String[dt.Datos.length];
        SharedPreferences sh = getSharedPreferences("Datos", MODE_PRIVATE);
        for (int i = 0;i<dt.Datos.length;i++){
            Datos[i]=sh.getString("Datos"+String.valueOf(i), "");
        }
        return Datos;
    }
    public void onBackPressed() {
        Datos = ObtenSharedPreference();
        Intent i = new Intent(this,Servicios.class);
        i.putExtra("Datos",Datos);
        startActivity(i);
    }
}