package com.bmr.conapesca;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bmr.conapesca.Datos.Datos;
import com.bmr.conapesca.Documentos.Instalacion;
import com.bmr.conapesca.Documentos.InstalacionOficial;
import com.bmr.conapesca.Entidades.RFP;
import com.bmr.conapesca.Herramientas.Firebase;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class RInstalacion extends AppCompatActivity {
    Firebase fb = new Firebase();
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference refUsuarios = database.getReference("Usuarios");
    public DatabaseReference refTickets = database.getReference("Tickets");
    public DatabaseReference refBarcos = database.getReference("Barcos");
    Datos dt = new Datos();
    RecyclerView FotosView;
    String []Datos,DatosBarco;
    String Ticket,NombreBarco,Noficio;
    RFPAdapter adapterFotos;
    Instalacion instalacion = new Instalacion();
    InstalacionOficial instalacionOficial = new InstalacionOficial();
    TextView NSerieIridium,NParteIridium,IMEIIridium,NSerieTransreceptor,NParteTransreceptor,NParteConBox,NSerieConBox;
    TextView NoOficio,NoReporte,FechaOficio,FechaInstalacion;
    TextView PermisionarioNombre,Razonsocial,RNPA,Responsable;
    TextView Nombreembarcación,Puertobaseview,Litoralview,Matriculaview;
    TextView FolioCamarón,FolioTunidos,FolioTiburon,FolioEscama,FolioSardina,FolioOtros;
    TextView NombreSeguritech,nombrebarco;
    LinearLayout DatosView,EquiposInstalados,Entraslado;
    EditText NoSelloTrans,NoSelloConBox;
    RadioButton DatosVer,EquiposIns,FotosVer;
    Boolean Fotos;
    RadioButton Comercial,PescaDidactica,Fomento,Camarón,Tunidos,TiburonRaya,Escama,Sardina,Otros;
    ImageView FirmaSeguritech,FirmaBarco;
    ProgressBar Charging;
    private DriveServiceHelper mDriveServiceHelper;
    private SheetsServiceHelper mSheetServiceHelper;
    private static final int REQUEST_CODE_SIGN_IN = 1;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinstalacion);
        //Charging = (ProgressBar)findViewById(R.id.Charging);
        FirmaBarco = (ImageView) findViewById(R.id.FirmaBarco);
        FirmaSeguritech = (ImageView)findViewById(R.id.FirmaSeguritech) ;
        NombreSeguritech = (TextView) findViewById(R.id.NombreSeguritech);
        nombrebarco = (TextView) findViewById(R.id.nombrebarco);
        FotosView = (RecyclerView) findViewById(R.id.FotosRFP);
        FotosView.setLayoutManager(new LinearLayoutManager(this));
        NSerieIridium = (TextView) findViewById(R.id.NSerieIridium);
        NParteConBox = (TextView) findViewById(R.id.NParteConBox);
        NParteIridium= (TextView) findViewById(R.id.NParteIridium);
        IMEIIridium = (TextView) findViewById(R.id.IMEIIridium);
        NSerieTransreceptor = (TextView) findViewById(R.id.NSerieTransreceptor);
        NParteTransreceptor = (TextView) findViewById(R.id.NParteTransreceptor);
        NSerieConBox = (TextView) findViewById(R.id.NSerieConBox);

        NoOficio = (TextView) findViewById(R.id.NoOficio);
        NoReporte = (TextView) findViewById(R.id.NoReporte);
        FechaOficio = (TextView) findViewById(R.id.FechaInstalacion);
        FechaInstalacion = (TextView) findViewById(R.id.LocalidadPuerto);


        PermisionarioNombre = (TextView) findViewById(R.id.PermisionarioNombre);
        RNPA = (TextView) findViewById(R.id.RNPA);
        Razonsocial = (TextView) findViewById(R.id.Razonsocial);
        Responsable = (TextView) findViewById(R.id.Responsable);

        Nombreembarcación = (TextView) findViewById(R.id.Nombreembarcación);
        Puertobaseview = (TextView) findViewById(R.id.Puertobaseview);
        Litoralview = (TextView) findViewById(R.id.Litoralview);
        Matriculaview = (TextView) findViewById(R.id.Matriculaview);

        NoSelloTrans = (EditText) findViewById(R.id.NoSelloTrans);
        NoSelloConBox = (EditText) findViewById(R.id.NoSelloConBox);


        DatosView = (LinearLayout)findViewById(R.id.DatosView);
        EquiposInstalados = (LinearLayout)findViewById(R.id.EquiposInstalados);
        Entraslado = (LinearLayout)findViewById(R.id.Entraslado);

        DatosVer = (RadioButton) findViewById(R.id.DatosVer) ;
        EquiposIns = (RadioButton) findViewById(R.id.EquiposIns) ;
        FotosVer = (RadioButton) findViewById(R.id.FotosVer) ;

        FolioCamarón = (TextView) findViewById(R.id.FolioCamarón);
        FolioTunidos = (TextView) findViewById(R.id.FolioTunidos);
        FolioTiburon = (TextView) findViewById(R.id.FolioTiburon);
        FolioEscama = (TextView) findViewById(R.id.FolioEscama);
        FolioSardina = (TextView) findViewById(R.id.FolioSardina);
        FolioOtros = (TextView) findViewById(R.id.FolioOtros);

        Camarón = (RadioButton) findViewById(R.id.Camarón);
        Tunidos= (RadioButton) findViewById(R.id.Tunidos);
        TiburonRaya = (RadioButton) findViewById(R.id.TiburonRaya);
        Escama = (RadioButton) findViewById(R.id.Escama);
        Sardina = (RadioButton) findViewById(R.id.PelagiosM);
        Otros = (RadioButton) findViewById(R.id.Otros);

        Fotos = false;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                Datos = extras.getStringArray("Datos");
                DatosBarco = extras.getStringArray("DatosBarco");
            }
        }
        else {
            Datos = (String[]) savedInstanceState.getSerializable("Datos");
            DatosBarco = (String[]) savedInstanceState.getSerializable("DatosBarco");
        }
        if (Datos!=null)Ticket = Datos[3];
        else {
            SharedPreferences sh = getSharedPreferences("TempShared", MODE_PRIVATE);
            Ticket = sh.getString("Ticket","");
        }

        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        NombreBarco = sh.getString("DatoBarco0","");
        Noficio =sh.getString("DatosTicket1","");
        if (Noficio.equals(""))GuardaDatosTicket();


        MostrarFotos();
        requestSignIn();

    }
    private String[] ObtenDatosBarcosH(){
            Datos = new String[dt.DatosBarco.length];
            SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
            for (int i = 0;i<dt.DatosBarco.length;i++){
                Datos[i]=sh.getString("DatoBarco"+String.valueOf(i), "");
            }
            return Datos;

    }
    private String[] ObtenDatosTicket(){
        Datos = new String[14];
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        for (int i = 0;i<14;i++){
            Datos[i]=sh.getString("DatosTicket"+String.valueOf(i), "");
        }
        return Datos;

    }
    ////FIRMAS
    public void ToFirmas(View view){
        String Datos[] = new String[]{Ticket,"Seguritech"};
        Intent intent = new Intent(this,Firmas.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);

    }
    public void ToFirmas2(View view){
        String Datos[] = new String[]{Ticket,"Barco"};
        Intent intent = new Intent(this,Firmas.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    //////ACTUALIZABARCO
    public void ActualizaBarco(View view){
        Intent i = new Intent(this,RegistroBarco.class);
        i.putExtra("Datos",Datos);
        i.putExtra("DatosBarco",ObtenDatosBarcosH());
        startActivity(i);
    }
    /////BOTONES
    public void VerDatos(View view){
        RutinaDatos();
    }
    public void VerEquipos(View view){
        RutinaEquipos();
    }
    public void VerFotos(View view){
        RutinaFotos();
    }
    private void RutinaFotos(){
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        DatosVer.setChecked(false);
        EquiposIns.setChecked(false);
        FotosVer.setChecked(true);
        DatosView.setVisibility(View.GONE);
        EquiposInstalados.setVisibility(View.GONE);
        FotosView.setVisibility(View.VISIBLE);
        myEdit.putString("Pantalla","Fotos");
        myEdit.commit();
    }
    private void RutinaEquipos(){
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        DatosVer.setChecked(false);
        EquiposIns.setChecked(true);
        FotosVer.setChecked(false);
        DatosView.setVisibility(View.GONE);
        EquiposInstalados.setVisibility(View.VISIBLE);
        FotosView.setVisibility(View.GONE);
        myEdit.putString("Pantalla","Equipos");
        myEdit.commit();
    }
    private void RutinaDatos(){
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        DatosVer.setChecked(true);
        EquiposIns.setChecked(false);
        FotosVer.setChecked(false);
        DatosView.setVisibility(View.VISIBLE);
        EquiposInstalados.setVisibility(View.GONE);
        FotosView.setVisibility(View.GONE);
        myEdit.putString("Pantalla","Datos");
        myEdit.commit();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void ObtenDatosBarco(){
        System.out.println(Datos[11]);
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm");
        myEdit.putString("FechaTraslado",String.valueOf(dtf.format(LocalDateTime.now())));
        myEdit.putString("HoraTraslado",String.valueOf(dtf2.format(LocalDateTime.now())));
        myEdit.commit();
        String [] HoraFecha = new String[]{
                sh.getString("HoraTraslado",""),
                sh.getString("FechaTraslado",""),
                "Instalacion"
        };
        fb.ObtenDatosBarco(ObtenDatosTicket()[11],this,ObtenDatosTicket(),HoraFecha);
    }
    private void GuardaDatosTicket(){
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        for (int i =0; i<14; i++){
            myEdit.putString("DatosTicket"+i,Datos[i]);
            System.out.println(Datos[i]);
            myEdit.commit();
        }
        Toast.makeText(this, "Datos de Ticket guardados",Toast.LENGTH_SHORT).show();
    }
    public void Guarda(View view){
        GuardaDatosSellos();
    }
    private void GuardaDatosSellos(){
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        NoSelloTrans = (EditText) findViewById(R.id.NoSelloTrans);
        NoSelloConBox = (EditText) findViewById(R.id.NoSelloConBox);
        myEdit.putString("SelloTrans",NoSelloTrans.getText().toString());
        myEdit.putString("SelloConBox",NoSelloConBox.getText().toString());
        myEdit.commit();

        Toast.makeText(this, "Datos guardados",Toast.LENGTH_SHORT).show();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void GuardaDatosBarco(){
        System.out.println("GuardaDatosBarco");
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        for (int i =0; i<35; i++){
            myEdit.putString("DatoBarco"+i,DatosBarco[i]);
            System.out.println("Saving :"+DatosBarco[i]);
            myEdit.commit();
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm");
        myEdit.putString("FechaTraslado",String.valueOf(dtf.format(LocalDateTime.now())));
        myEdit.putString("HoraTraslado",String.valueOf(dtf2.format(LocalDateTime.now())));
        myEdit.putString("DatosTicket7","En traslado");
        myEdit.commit();
        String [] HoraFecha = new String[]{
                sh.getString("HoraTraslado",""),
                sh.getString("FechaTraslado",""),
                "Instalacion"
        };
        System.out.println(sh.getString("DatoBarco4",""));
        refBarcos.child(sh.getString("DatoBarco4","")).child("Permisos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int NumeroPermisos = (int) snapshot.getChildrenCount();
                myEdit.putInt("NumeroPermisos",NumeroPermisos);
                myEdit.commit();
                System.out.println(NumeroPermisos);
                int i=1;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    System.out.println(postSnapshot.child("Datopermiso1").getValue(String.class));
                    myEdit.putString(i+"DatoPermiso1",postSnapshot.child("Datopermiso1").getValue(String.class));
                    myEdit.putString(i+"DatoPermiso2",postSnapshot.child("Datopermiso2").getValue(String.class));
                    myEdit.putString(i+"DatoPermiso3",postSnapshot.child("Datopermiso3").getValue(String.class));
                    myEdit.putString(i+"DatoPermiso4",postSnapshot.child("Datopermiso4").getValue(String.class));
                    myEdit.commit();
                    i++;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        SharedPreferences sh2 = getSharedPreferences("Datos", MODE_PRIVATE);
        refUsuarios.child(sh2.getString("Datos0","")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myEdit.putString("DatosUsuario1",snapshot.child("NombreCompleto").getValue(String.class));
                myEdit.putString("DatosUsuario2",snapshot.child("NElector").getValue(String.class));
                myEdit.putString("DatosUsuario3",snapshot.child("CorreoElectronico").getValue(String.class));
                myEdit.putString("DatosUsuario4",snapshot.child("Puesto").getValue(String.class));
                myEdit.putString("DatosUsuario5",snapshot.child("Numero").getValue(String.class));
                myEdit.putString("DatosUsuario6",snapshot.child("Direccion1").getValue(String.class));
                myEdit.putString("DatosUsuario7",snapshot.child("Direccion2").getValue(String.class));
                myEdit.putString("DatosUsuario8",snapshot.child("Direccion3").getValue(String.class));
                myEdit.putString("DatosUsuario9",snapshot.child("Direccion4").getValue(String.class));
                myEdit.putString("DatosUsuario10",snapshot.child("Direccion5").getValue(String.class));
                myEdit.putString("DatosUsuario11",snapshot.child("Direccion1").getValue(String.class)+" "+
                        snapshot.child("Direccion2").getValue(String.class)+" "+
                        snapshot.child("Direccion3").getValue(String.class)+" "+
                        snapshot.child("Direccion4").getValue(String.class)+" "+
                        snapshot.child("Direccion5").getValue(String.class)+" ");
                myEdit.commit();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mSheetServiceHelper.Escribeentabla2(HoraFecha,Ticket)
                .addOnSuccessListener(ok ->mSheetServiceHelper.ActualizaBitacora(ObtenDatosTicket()[10],"Ticket en Traslado",HoraFecha[0],HoraFecha[1],"-",0)
                        .addOnSuccessListener(OK -> System.out.println(ok))
                        .addOnFailureListener(Fail -> System.out.println(Fail)))
                .addOnFailureListener(Fail -> System.out.println(Fail));
        Toast.makeText(this, "Datos de barco obtenidos",Toast.LENGTH_SHORT).show();

        //GuardaDatosTicket();
    }
    private void ConfiguraIncio(){
        DatosBarco = ObtenDatosBarcosH();
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        NSerieTransreceptor.setText(sh.getString("NSerieTransreceptor",""));
        NParteTransreceptor.setText(sh.getString("NParteTransreceptor",""));
        NParteIridium.setText(sh.getString("NParteIridium",""));
        NSerieIridium.setText(sh.getString("NSerieIridium",""));
        IMEIIridium.setText(sh.getString("IMEIIridium",""));
        NSerieConBox.setText(sh.getString("NSerieConBox",""));
        NParteConBox.setText(sh.getString("NParteConBox",""));
        NoOficio.setText(sh.getString("DatosTicket12",""));
        NoReporte.setText(sh.getString("DatosTicket3",""));
        FechaOficio.setText(sh.getString("DatosTicket13",""));
        FechaInstalacion.setText(sh.getString("DatosTicket5",""));
        PermisionarioNombre.setText(sh.getString("DatoBarco6",""));
        RNPA.setText(sh.getString("DatoBarco14",""));
        Razonsocial .setText(sh.getString("DatoBarco7",""));
        Responsable.setText(sh.getString("DatoBarco38",""));
        Nombreembarcación.setText(sh.getString("DatoBarco0",""));
        Puertobaseview .setText(sh.getString("DatoBarco37",""));
        Litoralview .setText(sh.getString("DatoBarco36",""));
        Matriculaview .setText(sh.getString("DatoBarco1",""));
        NombreSeguritech.setText(sh.getString("DatosTicket1",""));
        nombrebarco.setText(sh.getString("DatoBarco38",""));
        FolioCamarón.setText(DatosBarco[21]);
        FolioTunidos.setText(DatosBarco[22]);
        FolioTiburon.setText(DatosBarco[23]);
        FolioEscama.setText(DatosBarco[24]);
        FolioSardina.setText(DatosBarco[25]);
        FolioOtros.setText(DatosBarco[26]);


        if (DatosBarco[30].equals("true")){Camarón.setChecked(true);}
        else Camarón.setChecked(false);
        if (DatosBarco[31].equals("true")){Tunidos.setChecked(true);}
        else Tunidos.setChecked(false);
        if (DatosBarco[32].equals("true")){TiburonRaya.setChecked(true);}
        else TiburonRaya.setChecked(false);
        if (DatosBarco[33].equals("true")){Escama.setChecked(true);}
        else Escama.setChecked(false);
        if (DatosBarco[34].equals("true")){Sardina.setChecked(true);}
        else Sardina.setChecked(false);
        if (DatosBarco[35].equals("true")){Otros.setChecked(true);}
        else Otros.setChecked(false);
        NoSelloTrans.setText(sh.getString("SelloTrans",""));
        NoSelloConBox.setText(sh.getString("SelloConBox",""));
        MuestraFirmas();
        if (sh.getString("DatosTicket7","").equals("En traslado")){
            DatosVer.setVisibility(View.GONE);
            FotosVer.setVisibility(View.GONE);
            EquiposIns.setVisibility(View.GONE);
            Entraslado.setVisibility(View.VISIBLE);
            Charging.setVisibility(View.GONE);
            DatosView.setVisibility(View.GONE);

        }else{
            DatosVer.setVisibility(View.VISIBLE);
            FotosVer.setVisibility(View.VISIBLE);
            EquiposIns.setVisibility(View.VISIBLE);
            Entraslado.setVisibility(View.GONE);
            Charging.setVisibility(View.GONE);
            DatosView.setVisibility(View.VISIBLE);
            if (sh.getString("Pantalla","").equals("Fotos"))RutinaFotos();
            else if (sh.getString("Pantalla","").equals("Datos"))RutinaDatos();
            else RutinaEquipos();

        }

    }
    private void MuestraFirmas(){
        if (ObtenImagen2(Ticket,"FirmaBarco")!=null) FirmaBarco.setImageBitmap(ObtenImagen2(Ticket,"FirmaBarco"));
        if (ObtenImagen2(Ticket,"FirmaSeguritech")!=null) FirmaSeguritech.setImageBitmap(ObtenImagen2(Ticket,"FirmaSeguritech"));
    }
    private void GuardaComentariosFotos(){
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        for (int i =0; i<10; i++){
            myEdit.putString("ComentarioFoto"+(i+1),dt.FotosReporteInstalacion[i]);
            myEdit.commit();
        }
    }
    private Bitmap ObtenImagen(String Ticket,int i){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(Ticket, Context.MODE_PRIVATE);
        File file = new File(directory, "Foto"+i+ ".png");
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        if (!file.exists())return null;
        return bitmap;
    }
    private Bitmap ObtenImagen2(String Ticket,String Nombre){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(Ticket, Context.MODE_PRIVATE);
        File file = new File(directory, Nombre+ ".png");
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        if (!file.exists())return null;
        return bitmap;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void ActualizaEstatus(String sh1,String sh2,String Estatus, int ID){
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm");
        myEdit.putString(sh1,String.valueOf(dtf.format(LocalDateTime.now())));
        myEdit.putString(sh2,String.valueOf(dtf2.format(LocalDateTime.now())));
        myEdit.commit();
        String [] HoraFecha = new String[]{
                sh.getString(sh1,""),
                sh.getString(sh2,""),
                "Instalacion",
                ObtenDatosTicket()[7]
        };
        mSheetServiceHelper.Escribeentabla3(HoraFecha,Ticket)
                .addOnSuccessListener(ok ->mSheetServiceHelper.ActualizaBitacora(ObtenDatosTicket()[10],"Ticket"+Estatus,HoraFecha[1],HoraFecha[0],"-",0)
                        .addOnSuccessListener(OK -> fb.ActualizaEstatus(ObtenDatosTicket(),this,HoraFecha,Estatus,ID)  )
                        .addOnFailureListener(Fail -> System.out.println(Fail)))
                .addOnFailureListener(Fail -> System.out.println(Fail));
         }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public  void CrearArchivo(View view){
        SharedPreferences sh2 = getSharedPreferences(Ticket, MODE_PRIVATE);
        if (sh2.getString("DatosTicket7","").equals("En traslado")){
            Toast.makeText(this, "Cambiando Estatus",Toast.LENGTH_SHORT).show();
            ActualizaEstatus("HoraCurso","FechaCurso","En curso",3);
        }
        else {
            //if (ValidaDatos()) {
                try {

                    Bitmap bitmap;
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.seguritech);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] bitmapData = stream.toByteArray();
                    ImageData imageData = ImageDataFactory.create(bitmapData);
                    SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sh.edit();
                    int ID = sh.getInt("ID", 1);
                    //instalacion.CreaArchivo(ObtenDatosTicket(), imageData);
                    instalacionOficial.CreaArchivo(Ticket, imageData);
                    if (ObtenDatosTicket()[7].equals("Cerrado")) {
                        Intent i = new Intent(this, PDFViewer.class);
                        i.putExtra("Iden", "RFD");
                        i.putExtra("Datos", ObtenDatosTicket());
                        startActivity(i);
                    }
                    else{ ActualizaEstatus("FechaCerrado","HoraCerrado","Cerrado",3);
                        Toast.makeText(this, "Cambiando Estatus",Toast.LENGTH_SHORT).show();}

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            //} else System.out.println("Datos erroneos");
        }
    }
    private boolean ValidaDatos(){
        if (NSerieTransreceptor.getText().toString().equals("")||
                NParteTransreceptor.getText().toString().equals("")||
                NParteIridium.getText().toString().equals("")||
                NSerieIridium.getText().toString().equals("")||
                IMEIIridium.getText().toString().equals("")||
                NSerieTransreceptor.getText().toString().equals("")||
                NSerieConBox.getText().toString().equals(""))
        {
            Toast.makeText(this,"Favor complete los numeros de serie",Toast.LENGTH_SHORT).show();
            RutinaEquipos();
            return false;
        }
        else if (ValidaFotos()){
            Toast.makeText(this,"Favor complete las fotos",Toast.LENGTH_SHORT).show();
            RutinaFotos();
            return false;
        }
        else return true;


    }
    private boolean ValidaFotos(){
        for (int i=0;i<10;i++){
            if (ObtenImagen(Ticket,(i+1))==null){System.out.println("Foto"+i);return true;}
        }
        return false;
    }
    private void MostrarFotos() {
        int NumeroFotos = 10;
        ArrayList<RFP> ListaFotos = new ArrayList<>();
        String Comen1;
        String Comen2;
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        String Desc;
        Desc = sh.getString("ComentarioFoto1","");
        if (Desc.equals("")) GuardaComentariosFotos();
        for (int i=0;i<NumeroFotos;i=i+2){
            Bitmap bitmapi,bitmapi2 = null;
            Comen1 =dt.FotosReporteInstalacion[i];
            Comen2 =dt.FotosReporteInstalacion[i+1];
            bitmapi = ObtenImagen(Ticket,i+1);
            bitmapi2 = ObtenImagen(Ticket,i+2);
            RFP fotos = null;
            fotos = new RFP();
            fotos.setComentario1(Comen1);
            fotos.setComentario2(Comen2);
            fotos.setFoto1(bitmapi);
            fotos.setFoto2(bitmapi2);
            fotos.setID1(i+1);
            fotos.setID2(i+2);
            fotos.setDatos(ObtenDatosTicket());
            ListaFotos.add(fotos);
            adapterFotos = new RFPAdapter(ListaFotos);
            FotosView.setAdapter(adapterFotos);

        }

    }
    ////SCAN
    public void Escaneo (String Lectura){
        IntentIntegrator intentIntegrator = new IntentIntegrator(RInstalacion.this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Lector - "+Lectura);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.initiateScan();
    }
    public void Scan1(View view){
        SharedPreferences sh = getSharedPreferences("TempShared", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.putString("Ticket",Ticket);
        myEdit.commit();
        SharedPreferences sh2 = getSharedPreferences(Ticket, MODE_PRIVATE);
        myEdit = sh2.edit();
        myEdit.putBoolean("Scan1",true);
        myEdit.commit();
        Escaneo ("NSerieIridium");
    }
    public void Scan2(View view){
        SharedPreferences sh = getSharedPreferences("TempShared", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.putString("Ticket",Ticket);
        myEdit.commit();
        SharedPreferences sh2 = getSharedPreferences(Ticket, MODE_PRIVATE);
        myEdit = sh2.edit();
        myEdit.putBoolean("Scan2",true);
        myEdit.commit();
        Escaneo ("NParteIridium");
    }
    public void Scan3(View view){
        SharedPreferences sh = getSharedPreferences("TempShared", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.putString("Ticket",Ticket);
        myEdit.commit();
        SharedPreferences sh2 = getSharedPreferences(Ticket, MODE_PRIVATE);
        myEdit = sh2.edit();
        myEdit.putBoolean("Scan3",true);
        myEdit.commit();
        Escaneo ("IMEIIridium");

    }
    public void Scan4(View view){
        SharedPreferences sh = getSharedPreferences("TempShared", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.putString("Ticket",Ticket);
        myEdit.commit();
        SharedPreferences sh2 = getSharedPreferences(Ticket, MODE_PRIVATE);
        myEdit = sh2.edit();
        myEdit.putBoolean("Scan4",true);
        myEdit.commit();
        Escaneo ("NSerieTransreceptor");

    }
    public void Scan5(View view){
        SharedPreferences sh = getSharedPreferences("TempShared", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.putString("Ticket",Ticket);
        myEdit.commit();
        SharedPreferences sh2 = getSharedPreferences(Ticket, MODE_PRIVATE);
        myEdit = sh2.edit();
        myEdit.putBoolean("Scan5",true);
        myEdit.commit();
        Escaneo ("NParteTransreceptor");
    }
    public void Scan6(View view){
        SharedPreferences sh = getSharedPreferences("TempShared", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.putString("Ticket",Ticket);
        myEdit.commit();
        SharedPreferences sh2 = getSharedPreferences(Ticket, MODE_PRIVATE);
        myEdit = sh2.edit();
        myEdit.putBoolean("Scan6",true);
        myEdit.commit();
        Escaneo ("NSerieConBox");

    }
    public void Scan7(View view){
        SharedPreferences sh = getSharedPreferences("TempShared", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.putString("Ticket",Ticket);
        myEdit.commit();
        SharedPreferences sh2 = getSharedPreferences(Ticket, MODE_PRIVATE);
        myEdit = sh2.edit();
        myEdit.putBoolean("Scan7",true);
        myEdit.commit();
        Escaneo ("NParteConBox");

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
    ///////////////////////////SERVICIOS GOOGLE//////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    handleSignInResult(data);
                }
                break;

        }
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(this,"Cancelado",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,result.getContents(),Toast.LENGTH_SHORT).show();
                SharedPreferences sh = getSharedPreferences("TempShared", MODE_PRIVATE);
                Ticket = sh.getString("Ticket","");
                SharedPreferences sh2 = getSharedPreferences(Ticket, MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh2.edit();

                if (sh2.getBoolean("Scan1",false)){
                    myEdit.putBoolean("Scan1",false);
                    myEdit.putString("NSerieIridium",result.getContents());
                    myEdit.commit();
                }
                else if (sh2.getBoolean("Scan2",false)){
                    myEdit.putBoolean("Scan2",false);
                    myEdit.putString("NParteIridium",result.getContents());
                    myEdit.commit();
                }
                else if (sh2.getBoolean("Scan3",false)){
                    myEdit.putBoolean("Scan3",false);
                    myEdit.putString("IMEIIridium",result.getContents());
                    myEdit.commit();
                }
                else if (sh2.getBoolean("Scan4",false)){
                    myEdit.putBoolean("Scan4",false);
                    myEdit.putString("NSerieTransreceptor",result.getContents());
                    myEdit.commit();
                }
                else if (sh2.getBoolean("Scan5",false)){
                    myEdit.putBoolean("Scan5",false);
                    myEdit.putString("NParteTransreceptor",result.getContents());
                    myEdit.commit();
                }
                else if (sh2.getBoolean("Scan6",false)){
                    myEdit.putBoolean("Scan6",false);
                    myEdit.putString("NParteConBox",result.getContents());
                    myEdit.commit();
                }
                else {
                    myEdit.putBoolean("Scan7",false);
                    myEdit.putString("NSerieConBox",result.getContents());
                    myEdit.commit();
                }
                ConfiguraIncio();

            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }

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
                    SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
                    System.out.println("Nombre Barco"+sh.getString("DatoBarco0",""));
                    System.out.println(Ticket);
                    if (DatosBarco!=null) {
                        if(NombreBarco.equals(""))GuardaDatosBarco();
                        ConfiguraIncio();}
                    else {
                        if (NombreBarco.equals("")) ObtenDatosBarco();
                        else ConfiguraIncio();
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