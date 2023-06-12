package com.bmr.conapesca;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bmr.conapesca.Adapters.AdapterFotos;
import com.bmr.conapesca.Datos.Datos;
import com.bmr.conapesca.Documentos.Convenios;
import com.bmr.conapesca.Documentos.Correctivo;
import com.bmr.conapesca.Documentos.Correctivos;
import com.bmr.conapesca.Documentos.InstalacionOficial;
import com.bmr.conapesca.Documentos.InstalacionOficialCarta;
import com.bmr.conapesca.Entidades.Fotos;
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
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ReporteInstalacion extends AppCompatActivity {
    Firebase fb = new Firebase();
    InstalacionOficial instalacionOficial = new InstalacionOficial();
    Convenios convenios = new Convenios();
    Correctivo correctivo = new Correctivo();
    Correctivos correctivos = new Correctivos();
    InstalacionOficialCarta instalacionOficialCarta  = new InstalacionOficialCarta();
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference refUsuarios = database.getReference("Usuarios");
    public DatabaseReference refTickets = database.getReference("Tickets");
    public DatabaseReference refBarcos = database.getReference("Barcos");
    private DriveServiceHelper mDriveServiceHelper;
    private SheetsServiceHelper mSheetServiceHelper;
    private static final int REQUEST_CODE_SIGN_IN = 1;
    RadioButton DatosVer,EquiposIns,FotosVer;

    LinearLayout Controles,DatosView,Entraslado,EquiposInstalados,CambioEs,DatosPermisionario,FotosCorrectivo,
            SolucionCorrectivo,Seriesconbox,Seriesblue;
    ProgressBar Charging;
    RecyclerView FotosRFP,FotosView;
    TextView CancelaV;
    ImageButton Cancela,Save2;
    RFPAdapter adapterFotos;
    AdapterFotos adapterFotos2;
    String []Datos,DatosBarco;
    String Ticket,NombreBarco,Noficio,CrearArchivo;
    Datos dt = new Datos();
    TextView CambioEstatus;
    EditText VBateria,NoSelloTrans,NoSelloConBox,Justificacion,OtrosPermiso,Domicilio,NombrePermisionariov,INEResponsable,DireccionResponsable,CargoResponsable,CorreoResponsable,Observaciones,Contacto,
            Fallareportada,Diagnostico,Solucion,Reemplazodeequipos,
            SerieAnteriorConbox,SerieNuevaConbox,SerieAnteriorBlue,SerieNuevaBlue,
            Selloanteriorconbox,Sellonuevoconbox,Selloanteriorblue,Sellonuevoblue,
            SerieAnteriorIridium,SerieNuevaIridium,IMEIanterior,IMEINuevo,Horacerrado;
    CheckBox Button1,Button2,Button3,Button4,Button5,Button6,Button7,Button8,Button9,Button10,Button11,
            Button12,Button13,Button14,Button15,Button16,Button17,Button18,Button19,
            SiConbox,NoConbox,SiBlue,NoBlue;

    TextView NSerieIridium,IMEIIridium,NSerieTransreceptor,NSerieConBox;
    TextView FechaOficio,NoOficio,NoReporte,FechaInstalacion,LocalidadPuerto;
    TextView Permisionariov,RLegalV,RNPATV,CorreoEV,TelefonoFijoV,TelefonoMovilV,Estado,Municipio,Localidad;
    TextView Nombreembarcaciónv,PuertobaseV,MatriculaV,MarcaMotorV,RNPV,PotenciaMotorV,EsloraV,TonNetoV,TonBrutoV;
    TextView Permiso1,Permiso2,Permiso3,Permiso4,Permiso5,FeInicial1,FeInicial2,FeInicial3,FeInicial4,FeInicial5,FeFinal1,FeFinal2,FeFinal3,FeFinal4,FeFinal5,Pesqueria;
    TextView NombreSeguritechVi;
    TextView permiso1,permiso2,permiso3,permiso4,permiso5;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_instalacion);
        FotosView = (RecyclerView) findViewById(R.id.FotosRFC);
        FotosView.setLayoutManager(new LinearLayoutManager(this));
        DatosView = (LinearLayout)findViewById(R.id.DatosView);
        SolucionCorrectivo = (LinearLayout)findViewById(R.id.SolucionCorrectivo);
        EquiposInstalados = (LinearLayout)findViewById(R.id.EquiposInstalados);
        Entraslado = (LinearLayout)findViewById(R.id.Entraslado);
        CambioEs = (LinearLayout)findViewById(R.id.CambioEs);
        Controles = (LinearLayout)findViewById(R.id.Controles);
        FotosCorrectivo = (LinearLayout)findViewById(R.id.FotosCorrectivo);
        DatosPermisionario = (LinearLayout)findViewById(R.id.DatosPermisionario);
        Seriesconbox = (LinearLayout)findViewById(R.id.Seriesconbox);
        Seriesblue = (LinearLayout)findViewById(R.id.Seriesblue);
        FotosRFP = (RecyclerView) findViewById(R.id.FotosRFP);
        DatosVer = (RadioButton) findViewById(R.id.DatosVer) ;
        EquiposIns = (RadioButton) findViewById(R.id.EquiposIns) ;
        FotosVer = (RadioButton) findViewById(R.id.FotosVer) ;
        FotosRFP.setLayoutManager(new LinearLayoutManager(this));
        CambioEstatus = (TextView) findViewById(R.id.AsignaTickettext);
        CancelaV = (TextView) findViewById(R.id.CancelaV);
        NombreSeguritechVi = (TextView) findViewById(R.id.NombreSeguritechVi);
        Cancela = (ImageButton) findViewById(R.id.Cancela) ;
        Save2 = (ImageButton) findViewById(R.id.Save2) ;
        Charging = (ProgressBar)findViewById(R.id.charging);
        Button1 = (CheckBox) findViewById(R.id.Button1);
        Button2 = (CheckBox) findViewById(R.id.Button2);
        Button3 = (CheckBox) findViewById(R.id.Button3);
        Button4 = (CheckBox) findViewById(R.id.Button4);
        Button5 = (CheckBox) findViewById(R.id.Button5);
        Button6 = (CheckBox) findViewById(R.id.Button6);
        Button7 = (CheckBox) findViewById(R.id.Button7);
        Button8 = (CheckBox) findViewById(R.id.Button8);
        Button9 = (CheckBox) findViewById(R.id.Button9);
        Button10 = (CheckBox) findViewById(R.id.Button10);
        Button11= (CheckBox) findViewById(R.id.Button11);
        Button12= (CheckBox) findViewById(R.id.Button12);
        Button13= (CheckBox) findViewById(R.id.Button13);
        Button14= (CheckBox) findViewById(R.id.Button14);
        Button15= (CheckBox) findViewById(R.id.Button15);
        Button16= (CheckBox) findViewById(R.id.Button16);
        Button17= (CheckBox) findViewById(R.id.Button17);
        Button18= (CheckBox) findViewById(R.id.Button18);
        Button19= (CheckBox) findViewById(R.id.Button19);

        SiConbox= (CheckBox) findViewById(R.id.SiConbox);
        NoConbox= (CheckBox) findViewById(R.id.NoConbox);
        SiBlue= (CheckBox) findViewById(R.id.SiBlue);
        NoBlue= (CheckBox) findViewById(R.id.NoBlue);

        VBateria = (EditText) findViewById(R.id.VBateria);
        Contacto = (EditText) findViewById(R.id.Contacto);
        Fallareportada = (EditText) findViewById(R.id.Fallareportada);
        Diagnostico = (EditText) findViewById(R.id.Diagnostico);
        Solucion = (EditText) findViewById(R.id.Solucion);
        Reemplazodeequipos = (EditText) findViewById(R.id.Reemplazodeequipos);
        Horacerrado = (EditText) findViewById(R.id.Horacerrado);

        NombrePermisionariov = (EditText) findViewById(R.id.NombrePermisionariov);

        INEResponsable = (EditText) findViewById(R.id.INEResponsable);
        DireccionResponsable = (EditText) findViewById(R.id.DireccionResponsable);
        CorreoResponsable = (EditText) findViewById(R.id.CorreoResponsable);

        SerieAnteriorConbox = (EditText) findViewById(R.id.SerieAnteriorConbox);
        SerieNuevaConbox = (EditText) findViewById(R.id.SerieNuevaConbox);
        SerieAnteriorBlue = (EditText) findViewById(R.id.SerieAnteriorBlue);
        SerieNuevaBlue = (EditText) findViewById(R.id.SerieNuevaBlue);

        Selloanteriorconbox = (EditText) findViewById(R.id.Selloanteriorconbox);
        Sellonuevoconbox = (EditText) findViewById(R.id.Sellonuevoconbox);
        Selloanteriorblue = (EditText) findViewById(R.id.Selloanteriorblue);
        Sellonuevoblue = (EditText) findViewById(R.id.Sellonuevoblue);
        SerieAnteriorIridium = (EditText) findViewById(R.id.SerieAnteriorIridium);
        SerieNuevaIridium = (EditText) findViewById(R.id.SerieNuevaIridium);
        IMEIanterior = (EditText) findViewById(R.id.IMEIanterior);
        IMEINuevo = (EditText) findViewById(R.id.IMEINuevo);


        CargoResponsable = (EditText) findViewById(R.id.CargoResponsable);
        NoSelloTrans = (EditText) findViewById(R.id.NoSelloTrans);
        NoSelloConBox = (EditText) findViewById(R.id.NoSelloConBox);
        Justificacion = (EditText) findViewById(R.id.Justificacion);
        OtrosPermiso = (EditText) findViewById(R.id.OtrosPermiso);
        NSerieIridium = (TextView) findViewById(R.id.NSerieIridium);
        IMEIIridium = (TextView) findViewById(R.id.IMEIIridium);
        NSerieTransreceptor = (TextView) findViewById(R.id.NSerieTransreceptor);
        NSerieConBox = (TextView) findViewById(R.id.NSerieConBox);

        FechaOficio = (TextView) findViewById(R.id.FechaOficio);
        NoOficio = (TextView) findViewById(R.id.NoOficio);
        NoReporte = (TextView) findViewById(R.id.NoReporte);
        FechaInstalacion = (TextView) findViewById(R.id.FechaInstalacion);
        LocalidadPuerto = (TextView) findViewById(R.id.LocalidadPuerto);

        Permisionariov = (TextView) findViewById(R.id.Permisionariov);
        RLegalV = (TextView) findViewById(R.id.RLegalV);
        RNPATV = (TextView) findViewById(R.id.RNPATV);
        CorreoEV = (TextView) findViewById(R.id.CorreoEV);
        TelefonoFijoV = (TextView) findViewById(R.id.TelefonoFijoV);
        TelefonoMovilV = (TextView) findViewById(R.id.TelefonoMovilV);
        Estado = (TextView) findViewById(R.id.Estado);
        Municipio = (TextView) findViewById(R.id.Municipio);
        Localidad = (TextView) findViewById(R.id.Localidad);
        Domicilio = (EditText) findViewById(R.id.DomicilioV);
        Observaciones = (EditText) findViewById(R.id.Observaciones);

        Nombreembarcaciónv = (TextView) findViewById(R.id.Nombreembarcaciónv);
        PuertobaseV = (TextView) findViewById(R.id.PuertobaseV);
        MatriculaV = (TextView) findViewById(R.id.MatriculaV);
        MarcaMotorV = (TextView) findViewById(R.id.MarcaMotorV);
        RNPV = (TextView) findViewById(R.id.RNPV);
        PotenciaMotorV = (TextView) findViewById(R.id.PotenciaMotorV);
        EsloraV = (TextView) findViewById(R.id.EsloraV);
        TonNetoV = (TextView) findViewById(R.id.TonNetoV);
        TonBrutoV = (TextView) findViewById(R.id.TonBrutoV);


        Permiso1 = (TextView) findViewById(R.id.Permiso1);
        Permiso2 = (TextView) findViewById(R.id.Permiso2);
        Permiso3 = (TextView) findViewById(R.id.Permiso3);
        Permiso4 = (TextView) findViewById(R.id.Permiso4);
        Permiso5 = (TextView) findViewById(R.id.Permiso5);

        permiso1 = (TextView) findViewById(R.id.permiso1);
        permiso2 = (TextView) findViewById(R.id.permiso2);
        permiso3 = (TextView) findViewById(R.id.permiso3);
        permiso4 = (TextView) findViewById(R.id.permiso4);
        permiso5 = (TextView) findViewById(R.id.permiso5);

        FeInicial1 = (TextView) findViewById(R.id.FeInicial1);
        FeInicial2 = (TextView) findViewById(R.id.FeInicial2);
        FeInicial3 = (TextView) findViewById(R.id.FeInicial3);
        FeInicial4 = (TextView) findViewById(R.id.FeInicial4);
        FeInicial5 = (TextView) findViewById(R.id.FeInicial5);
        FeFinal1 = (TextView) findViewById(R.id.FeFinal1);
        FeFinal2 = (TextView) findViewById(R.id.FeFinal2);
        FeFinal3 = (TextView) findViewById(R.id.FeFinal3);
        FeFinal4 = (TextView) findViewById(R.id.FeFinal4);
        FeFinal5 = (TextView) findViewById(R.id.FeFinal5);
        Pesqueria = (TextView) findViewById(R.id.Pesqueria);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                Datos = extras.getStringArray("Datos");
                DatosBarco = extras.getStringArray("DatosBarco");
                CrearArchivo= extras.getString("CrearArchivo");
            }
        }
        else {
            Datos = (String[]) savedInstanceState.getSerializable("Datos");
            DatosBarco = (String[]) savedInstanceState.getSerializable("DatosBarco");
            CrearArchivo = (String) savedInstanceState.getSerializable("CrearArchivo");
        }

            if (Datos != null) {
                Ticket = Datos[3];
                System.out.println(Datos[7]);
                System.out.println(Datos[8]);
            } else {
                SharedPreferences sh = getSharedPreferences("TempShared", MODE_PRIVATE);
                Ticket = sh.getString("Ticket", "");
            }

            SharedPreferences sh3 = getSharedPreferences("TempShared", MODE_PRIVATE);
            SharedPreferences.Editor myEdit3 = sh3.edit();
            myEdit3.putString("Ticket", Ticket);
            myEdit3.commit();

            SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
            NombreBarco = sh.getString("DatoBarco0", "");
            Noficio = sh.getString("DatosTicket1", "");
            System.out.println("---------INICIO REPORTE INSTALACION------");
            System.out.println(sh.getString("SelloConBox", ""));
            System.out.println(sh.getString("SelloBlueTraker", ""));
            System.out.println(sh.getString("NSerieIridium", ""));
            System.out.println(sh.getString("IMEIIridium", ""));
            System.out.println(sh.getString("NSerieTransreceptor", ""));
            System.out.println(sh.getString("NSerieConBox", ""));
            System.out.println(Noficio);
            System.out.println("---------INICIO REPORTE INSTALACION------");
            if (Datos != null) {
                if (Datos[7].equals("Asignado") || Datos[7].equals("Re-asignacion") || Datos[7].equals("Actualizacion")) {
                    System.out.println(Datos[7]);
                    NombreBarco = "";
                    Noficio = "";
                }
                if (Datos[8].equals("ListaUsuarios")) {
                    SharedPreferences.Editor myEdit = sh.edit();
                    refUsuarios.child(Datos[0]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            myEdit.putString("NombreSeguritech", snapshot.child("NombreCompleto").getValue(String.class));
                            myEdit.putString("DatosUsuario1", snapshot.child("NombreCompleto").getValue(String.class));
                            myEdit.putString("DatosUsuario2", snapshot.child("NElector").getValue(String.class));
                            myEdit.putString("DatosUsuario3", snapshot.child("CorreoElectronico").getValue(String.class));
                            myEdit.putString("DatosUsuario4", snapshot.child("Puesto").getValue(String.class));
                            myEdit.putString("DatosUsuario5", snapshot.child("Numero").getValue(String.class));
                            myEdit.putString("DatosUsuario6", snapshot.child("Direccion1").getValue(String.class));
                            myEdit.putString("DatosUsuario7", snapshot.child("Direccion2").getValue(String.class));
                            myEdit.putString("DatosUsuario8", snapshot.child("Direccion3").getValue(String.class));
                            myEdit.putString("DatosUsuario9", snapshot.child("Direccion4").getValue(String.class));
                            myEdit.putString("DatosUsuario10", snapshot.child("Direccion5").getValue(String.class));
                            myEdit.putString("DatosUsuario11", snapshot.child("Direccion1").getValue(String.class) + ", " +
                                    snapshot.child("Direccion2").getValue(String.class) + ", " +
                                    snapshot.child("Direccion3").getValue(String.class) + ", " +
                                    snapshot.child("Direccion4").getValue(String.class) + ", " +
                                    snapshot.child("Direccion5").getValue(String.class) + ", CP." +
                                    snapshot.child("Direccion6").getValue(String.class) + " ");
                            myEdit.commit();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    //GuardaDatosTicket();
                }
            }
        if (CrearArchivo!=null){
            RutinaCreaArchivoInstalacion(true);
        }else {
            //NombreBarco = "";
            // Noficio ="";
            //GuardaDatosTicket();
            if (Noficio.equals("")||Datos[7].equals("Actualizacion")||Datos[7].equals("Re-asignacion")) GuardaDatosTicket();
            requestSignIn();
        }
    }
    ////Arranque
    private void ConfiguraIncio(){
        DatosBarco = ObtenDatosBarcosH();
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        MostrarFotos(Ticket);
        SetCheckSec();
        SetDatos();
        if (sh.getString("DatosTicket7","").equals("En traslado")){
            Entraslado.setVisibility(View.VISIBLE);
            Charging.setVisibility(View.GONE);
            DatosView.setVisibility(View.GONE);
            SolucionCorrectivo.setVisibility(View.GONE);
            FotosCorrectivo.setVisibility(View.GONE);
            FotosRFP.setVisibility(View.GONE);
            EquiposInstalados.setVisibility(View.GONE);
            CambioEstatus.setText("Poner en curso");
            if (sh.getString("DatosTicket3", "").contains("C-")) {
                ListadeTickets();}

        }
        else if (sh.getString("DatosTicket7","").equals("En curso")){
            if (sh.getString("DatosTicket4", "").equals("Cancelado")){
                CambioEstatus.setText("Genera Documento No Instalacion");
                Entraslado.setVisibility(View.GONE);
                Charging.setVisibility(View.GONE);
                Controles.setVisibility(View.VISIBLE);
                Cancela.setVisibility(View.GONE);
                CancelaV.setVisibility(View.GONE);
                Justificacion.setVisibility(View.VISIBLE);
                Save2.setVisibility(View.VISIBLE);
                DatosPermisionario.setVisibility(View.GONE);
                RutinaCancelado();
            }
            else if (sh.getString("DatosTicket3", "").contains("C-")) {
                Controles.setVisibility(View.VISIBLE);
                Charging.setVisibility(View.GONE);
                MuestraFotosCorrectivo();

            }else {

                CambioEstatus.setText("Cerrar Servicio");
                Entraslado.setVisibility(View.GONE);
                Charging.setVisibility(View.GONE);
                Controles.setVisibility(View.VISIBLE);
            }
            if (sh.getString("Pantalla", "").equals("Fotos")) RutinaFotos();
            else if (sh.getString("Pantalla", "").equals("Equipos")) RutinaEquipos();
            else RutinaDatos();
        }
        else if (sh.getString("DatosTicket7","").equals("Cerrado")){
            if (sh.getString("DatosTicket4", "").equals("Cancelado")){
                CambioEstatus.setText("Genera Documento No Instalacion");
                Entraslado.setVisibility(View.GONE);
                Charging.setVisibility(View.GONE);
                Controles.setVisibility(View.VISIBLE);
                Cancela.setVisibility(View.GONE);
                CancelaV.setVisibility(View.GONE);
                Justificacion.setVisibility(View.VISIBLE);
                Save2.setVisibility(View.VISIBLE);
                DatosPermisionario.setVisibility(View.GONE);
                RutinaCancelado();
            }    else if (sh.getString("DatosTicket3", "").contains("C-")) {
                Controles.setVisibility(View.VISIBLE);
                //EquiposIns.setVisibility(View.GONE);
                Charging.setVisibility(View.GONE);
                MuestraFotosCorrectivo();
            }

            else {
                CambioEstatus.setText("Genera Documento Instalacion");
                Entraslado.setVisibility(View.GONE);
                Charging.setVisibility(View.GONE);
                Controles.setVisibility(View.VISIBLE);
                Cancela.setVisibility(View.GONE);
                CancelaV.setVisibility(View.GONE);
                if (sh.getString("Pantalla", "").equals("Fotos")) RutinaFotos();
                else if (sh.getString("Pantalla", "").equals("Equipos")) RutinaEquipos();
                else RutinaDatos();
            }
        }
        else if (sh.getString("DatosTicket7","").equals("Cancelado")){
            CambioEstatus.setText("Genera Documento No Instalacion");
            Entraslado.setVisibility(View.GONE);
            Charging.setVisibility(View.GONE);
            Controles.setVisibility(View.VISIBLE);
            Cancela.setVisibility(View.GONE);
            CancelaV.setVisibility(View.GONE);
            Justificacion.setVisibility(View.VISIBLE);
            Save2.setVisibility(View.VISIBLE);
            DatosPermisionario.setVisibility(View.GONE);
            RutinaCancelado();
        }
        else{
            Entraslado.setVisibility(View.GONE);
            Charging.setVisibility(View.GONE);
            Controles.setVisibility(View.VISIBLE);

        }
        if (sh.getString("Pantalla","").equals("Fotos"))RutinaFotos();
        else if (sh.getString("Pantalla","").equals("Equipos"))RutinaEquipos();
        else RutinaDatos();
        CambioEs.setVisibility(View.VISIBLE);
    }
    private String[]  ObtenComentarios(){
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        String [] Descripciones = new String[4];
        String Desc;
        for (int i = 0;i<4;i++){
            Desc = sh.getString("ComentarioFoto"+(i+1),"");
            Descripciones[i] = Desc;
            System.out.println("Desc:"+Descripciones[i]);
        }
        return Descripciones;
    }
    private void GuardaDatosTicket(){
        System.out.println("Guardando Datos Ticket");
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        for (int i =0; i<15; i++){
            myEdit.putString("DatosTicket"+i,Datos[i]);
            System.out.println(Datos[i]);
            myEdit.commit();
        }
        refTickets.child(Datos[4]).child(Datos[3]).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    System.out.println(dataSnapshot.child("Contacto").getValue(String.class));
                    System.out.println(dataSnapshot.child("NSerieIridium").getValue(String.class));



                    myEdit.putString("CredResponsable",dataSnapshot.child("CredResponsable").getValue(String.class));
                    myEdit.putString("CorreoResposable",dataSnapshot.child("CorreoResposable").getValue(String.class));
                    myEdit.putString("DireccionResponsable",dataSnapshot.child("DireccionResponsable").getValue(String.class));

                    myEdit.putString("Contacto",dataSnapshot.child("Contacto").getValue(String.class));
                    myEdit.putString("NombreResponable",dataSnapshot.child("NombreResponable").getValue(String.class));
                    myEdit.putString("CargoResponsable",dataSnapshot.child("CargoResponsable").getValue(String.class));
                    myEdit.putString("Observaciones",dataSnapshot.child("Observaciones").getValue(String.class));
                    myEdit.putString("VoltajeBateria",dataSnapshot.child("VBateria").getValue(String.class));
                    myEdit.putString("SelloConBox",dataSnapshot.child("NoSelloConBox").getValue(String.class));
                    myEdit.putString("SelloBlueTraker",dataSnapshot.child("NoSelloTrans").getValue(String.class));
                    myEdit.putString("Justificacion",dataSnapshot.child("Justificacion").getValue(String.class));
                    myEdit.putString("NSerieIridium",dataSnapshot.child("NSerieIridium").getValue(String.class));
                    myEdit.putString("IMEIIridium",dataSnapshot.child("IMEIIridium").getValue(String.class));
                    myEdit.putString("NSerieTransreceptor",dataSnapshot.child("NSerieTransreceptor").getValue(String.class));
                    myEdit.putString("NSerieConBox",dataSnapshot.child("NSerieConBox").getValue(String.class));
                    myEdit.putString("NombreSeguritech",dataSnapshot.child("NombreIngeniero").getValue(String.class));
                    myEdit.putString("NombreSeguritech",dataSnapshot.child("NombreIngeniero").getValue(String.class));
                    myEdit.putString("HoraCerrado",dataSnapshot.child("HoraCerrado").getValue(String.class));
                    myEdit.putString("FechaCerrado",dataSnapshot.child("FechaCerrado").getValue(String.class));
                    myEdit.putString("UID",dataSnapshot.child("UID").getValue(String.class));
                    myEdit.commit();

                    VBateria.setText(sh.getString("VoltajeBateria",""));
                    NoSelloConBox.setText(sh.getString("SelloConBox",""));
                    NoSelloTrans.setText(sh.getString("SelloBlueTraker",""));


                    for (int i=0; i<ObtenCheck().length-4;i++){
                        myEdit.putBoolean("CheckSec"+i,dataSnapshot.child("Check"+i).getValue(Boolean.class));
                        myEdit.commit();
                    }
                    SetCheckSec();

                } else {
                    System.out.println(dataSnapshot.child("No se encontraron Datos").getValue(String.class));
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        Toast.makeText(this, "Datos de Ticket guardados",Toast.LENGTH_SHORT).show();

    }
    private void SetDatos(){
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);

        Fallareportada.setText(sh.getString("Fallareportada",""));
        Diagnostico.setText(sh.getString("Diagnostico",""));
        Solucion.setText(sh.getString("Solucion",""));
        Reemplazodeequipos.setText(sh.getString("Reemplazodeequipos",""));

        Contacto.setText(sh.getString("Contacto",""));
        VBateria.setText(sh.getString("VoltajeBateria",""));
        NoSelloConBox.setText(sh.getString("SelloConBox",""));
        NoSelloTrans.setText(sh.getString("SelloBlueTraker",""));

        NSerieIridium.setText(sh.getString("NSerieIridium",""));
        IMEIIridium.setText(sh.getString("IMEIIridium",""));
        NSerieTransreceptor.setText(sh.getString("NSerieTransreceptor",""));
        NSerieConBox.setText(sh.getString("NSerieConBox",""));
        Justificacion.setText(sh.getString("Justificacion",""));
        OtrosPermiso.setText(sh.getString("OtrosPermiso",""));

        SerieAnteriorConbox.setText(sh.getString("NSerieConBoxAnterior",""));
        SerieNuevaConbox.setText(sh.getString("NSerieConBoxNuevo",""));
        SerieAnteriorBlue.setText(sh.getString("NSerieTransreceptorAnterior",""));
        SerieNuevaBlue.setText(sh.getString("NSerieTransreceptorNuevo",""));
        Selloanteriorconbox.setText(sh.getString("NoSelloConBoxAnterior",""));
        Sellonuevoconbox.setText(sh.getString("NoSelloConBoxNuevo",""));
        Selloanteriorblue.setText(sh.getString("NoSelloTransAnterior",""));
        Sellonuevoblue.setText(sh.getString("NoSelloTransNuevo",""));
        IMEINuevo.setText(sh.getString("IMEIIridiumNuevo",""));
        IMEIanterior.setText(sh.getString("IMEIIridiumAnterior",""));
        SerieNuevaIridium.setText(sh.getString("NSerieIridiumNuevo",""));
        SerieAnteriorIridium.setText(sh.getString("NSerieIridiumAnterior",""));




        Selloanteriorblue = (EditText) findViewById(R.id.Selloanteriorblue);
        Sellonuevoblue = (EditText) findViewById(R.id.Sellonuevoblue);
        SerieAnteriorIridium = (EditText) findViewById(R.id.SerieAnteriorIridium);
        SerieNuevaIridium = (EditText) findViewById(R.id.SerieNuevaIridium);
        IMEIanterior = (EditText) findViewById(R.id.IMEIanterior);
        IMEINuevo = (EditText) findViewById(R.id.IMEINuevo);


        NombrePermisionariov.setText(sh.getString("NombreResponable",""));
        CargoResponsable.setText(sh.getString("CargoResponsable",""));
        Observaciones.setText(sh.getString("Observaciones",""));
        CorreoResponsable.setText(sh.getString("CorreoResposable",""));
        DireccionResponsable.setText(sh.getString("DireccionResponsable",""));
        INEResponsable.setText(sh.getString("CredResponsable",""));


        FechaOficio.setText(sh.getString("DatosTicket13",""));
        NoOficio.setText(sh.getString("DatosTicket12",""));
        NoReporte.setText(sh.getString("DatosTicket3",""));
        FechaInstalacion.setText(sh.getString("FechaCerrado",""));
        Horacerrado.setText(sh.getString("HoraCerrado",""));
        NombreSeguritechVi.setText(sh.getString("DatosUsuario1",""));

        LocalidadPuerto .setText(sh.getString("DatoBarco25",""));

        Permisionariov.setText(sh.getString("DatoBarco21",""));
        RLegalV .setText(sh.getString("DatoBarco28",""));
        RNPATV.setText(sh.getString("DatoBarco20",""));
        CorreoEV .setText(sh.getString("DatoBarco29",""));
        TelefonoFijoV.setText(sh.getString("DatoBarco27",""));
        TelefonoMovilV.setText("");
        Estado.setText(sh.getString("DatoBarco23",""));
        Municipio.setText(sh.getString("DatoBarco24",""));
        Localidad .setText(sh.getString("DatoBarco25",""));
        Domicilio.setText(sh.getString("DatoBarco26",""));


        Nombreembarcaciónv.setText(sh.getString("DatoBarco3",""));
        PuertobaseV.setText(sh.getString("DatoBarco2",""));
        MatriculaV .setText(sh.getString("DatoBarco5",""));
        MarcaMotorV.setText(sh.getString("DatoBarco18",""));
        RNPV.setText(sh.getString("DatoBarco4",""));
        PotenciaMotorV .setText(sh.getString("DatoBarco19",""));
        EsloraV.setText(sh.getString("DatoBarco15",""));
        TonNetoV.setText(sh.getString("DatoBarco12",""));
        TonBrutoV.setText(sh.getString("DatoBarco11",""));

        Permiso1 .setText(sh.getString("DatoBarco36",""));
        Permiso2 .setText(sh.getString("DatoBarco43",""));
        Permiso3 .setText(sh.getString("DatoBarco50",""));
        Permiso4 .setText(sh.getString("DatoBarco57",""));
        Permiso5 .setText(sh.getString("DatoBarco64",""));

        FeInicial1 .setText(sh.getString("DatoBarco34",""));
        FeInicial2 .setText(sh.getString("DatoBarco41",""));
        FeInicial3 .setText(sh.getString("DatoBarco48",""));
        FeInicial4 .setText(sh.getString("DatoBarco55",""));
        FeInicial5 .setText(sh.getString("DatoBarco62",""));
        FeFinal1 .setText(sh.getString("DatoBarco35",""));
        FeFinal2 .setText(sh.getString("DatoBarco42",""));
        FeFinal3 .setText(sh.getString("DatoBarco49",""));
        FeFinal4 .setText(sh.getString("DatoBarco56",""));
        FeFinal5 .setText(sh.getString("DatoBarco63",""));




        Pesqueria.setText(sh.getString("DatoBarco6","")+", "+
                sh.getString("DatoBarco7","")+", "+
                sh.getString("DatoBarco8","")+", "+
                sh.getString("DatoBarco9","")+", "+
                sh.getString("DatoBarco10","")+", ");

        String Permiso1 = sh.getString("DatoBarco6","");
        String Permiso2 = sh.getString("DatoBarco7","");
        String Permiso3 = sh.getString("DatoBarco8","");
        String Permiso4 = sh.getString("DatoBarco9","");
        String Permiso5 = sh.getString("DatoBarco10","");
        if (!Permiso1.equals("")&&!Permiso1.equals("NA")&&!Permiso1.equals("N/A")){
            permiso1 .setText("Permiso 1 : "+Permiso1);
        }
        if (!Permiso2.equals("")&&!Permiso2.equals("NA")&&!Permiso2.equals("N/A")){
            permiso2 .setText("Permiso 2 : "+Permiso2);
        }
        if (!Permiso3.equals("")&&!Permiso3.equals("NA")&&!Permiso2.equals("N/A")){
            permiso3 .setText("Permiso 3 : "+Permiso3);
        }
        if (!Permiso4.equals("")&&!Permiso4.equals("NA")&&!Permiso2.equals("N/A")){
            permiso4 .setText("Permiso 4 : "+Permiso4);
        }
        if (!Permiso5.equals("")&&!Permiso5.equals("NA")&&!Permiso2.equals("N/A")){
            permiso5 .setText("Permiso 5 : "+Permiso5);
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void GuardaDatosBarco(){
        System.out.println("GuardaDatosBarco");
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        for (int i =0; i<67; i++){
            if (Datos[7].equals("Re-asignacion")){
                System.out.println("Conservando Estatus");
            }else{
                myEdit.putString("DatoBarco"+i,DatosBarco[i]);
                System.out.println("Saving :"+DatosBarco[i]);
                myEdit.commit();
            }

        }
        System.out.println(Datos[7]);
        if (Datos[7].equals("Asignado")||Datos[7].equals("Re-asignacion")||Datos[7].equals("Actualizacion")){

            if ((Datos[7].equals("Re-asignacion")||Datos[7].equals("Actualizacion"))&&(!sh.getString("DatosTicket7","").equals(""))){
                Toast.makeText(this,sh.getString("DatosTicket7",""),Toast.LENGTH_SHORT).show();
                RutinaCharging();
                ActualizaEstatus("FechaCurso", "HoraCurso", "En curso", 3);
                Toast.makeText(this, "Cambiando Estatus", Toast.LENGTH_SHORT).show();
                myEdit.putString("DatosTicket7","En curso");
                myEdit.commit();
            }
            else{
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm");
                myEdit.putString("FechaTraslado",String.valueOf(dtf.format(LocalDateTime.now())));
                myEdit.putString("HoraTraslado",String.valueOf(dtf2.format(LocalDateTime.now())));
                myEdit.putString("DatosTicket7","En traslado");
                myEdit.commit();
                String [] HoraFecha = new String[]{
                        sh.getString("HoraTraslado",""),
                        sh.getString("FechaTraslado",""),
                        sh.getString("DatosTicket4",""),
                };
                ActualizaEstatus("HoraTraslado","FechaTraslado","En traslado",2);
           /* mSheetServiceHelper.Escribeentabla2(HoraFecha,Ticket)
                    .addOnSuccessListener(ok ->mSheetServiceHelper.ActualizaBitacora(ObtenDatosTicket()[10],"Ticket en Traslado",HoraFecha[0],HoraFecha[1],"-",0)
                            .addOnSuccessListener(OK -> )
                            .addOnFailureListener(Fail -> System.out.println(Fail)))
                    .addOnFailureListener(Fail -> System.out.println(Fail));*/
                Toast.makeText(this, "Datos de barco obtenidos",Toast.LENGTH_SHORT).show();
            }

        }

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
                myEdit.putString("DatosUsuario11",snapshot.child("Direccion1").getValue(String.class)+", "+
                        snapshot.child("Direccion2").getValue(String.class)+", "+
                        snapshot.child("Direccion3").getValue(String.class)+", "+
                        snapshot.child("Direccion4").getValue(String.class)+", "+
                        snapshot.child("Direccion5").getValue(String.class)+", CP."+
                        snapshot.child("Direccion6").getValue(String.class)+" ");
                myEdit.commit();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    ////SharedPreference
    private String[] ObtenSharedPreference(){
        Datos = new String[dt.Datos.length];
        SharedPreferences sh = getSharedPreferences("Datos", MODE_PRIVATE);
        for (int i = 0;i<dt.Datos.length;i++){
            Datos[i]=sh.getString("Datos"+String.valueOf(i), "");
        }
        return Datos;
    }
    private void GuardaDatosInstalacion(){
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();

        String [] DatosTicket = new String[] {
                Contacto.getText().toString(),
                NombrePermisionariov.getText().toString(),
                CargoResponsable.getText().toString(),
                Observaciones.getText().toString(),
                VBateria.getText().toString(),
                NoSelloConBox.getText().toString(),
                NoSelloTrans.getText().toString(),
                Justificacion.getText().toString(),
                NSerieIridium.getText().toString(),
                IMEIIridium.getText().toString(),
                NSerieTransreceptor.getText().toString(),
                NSerieConBox.getText().toString(),
                INEResponsable.getText().toString(),
                DireccionResponsable.getText().toString(),
                CorreoResponsable.getText().toString(),
                Fallareportada.getText().toString(),
                Diagnostico.getText().toString(),
                Solucion.getText().toString(),
                Reemplazodeequipos.getText().toString(),
                FechaInstalacion.getText().toString(),
                Horacerrado.getText().toString()
        };


        myEdit.putString("Fallareportada",Fallareportada.getText().toString());
        myEdit.putString("Diagnostico",Diagnostico.getText().toString());
        myEdit.putString("Solucion",Solucion.getText().toString());
        myEdit.putString("Reemplazodeequipos",Reemplazodeequipos.getText().toString());


        myEdit.putString("CredResponsable",INEResponsable.getText().toString());
        myEdit.putString("DireccionResponsable",DireccionResponsable.getText().toString());
        myEdit.putString("CorreoResposable",CorreoResponsable.getText().toString());

        myEdit.putString("FechaCerrado",FechaInstalacion.getText().toString());
        myEdit.putString("Contacto",Contacto.getText().toString());
        myEdit.putString("NombreResponable",NombrePermisionariov.getText().toString());
        myEdit.putString("CargoResponsable",CargoResponsable.getText().toString());
        myEdit.putString("Observaciones",Observaciones.getText().toString());

        myEdit.putString("VoltajeBateria",VBateria.getText().toString());
        myEdit.putString("SelloConBox",NoSelloConBox.getText().toString());
        myEdit.putString("SelloBlueTraker",NoSelloTrans.getText().toString());
        myEdit.putString("Justificacion",Justificacion.getText().toString());

        myEdit.putString("NSerieIridium",NSerieIridium.getText().toString());
        myEdit.putString("IMEIIridium",IMEIIridium.getText().toString());
        myEdit.putString("NSerieTransreceptor",NSerieTransreceptor.getText().toString());
        myEdit.putString("NSerieConBox",NSerieConBox.getText().toString());


        myEdit.putString("NoSelloConBoxAnterior", Selloanteriorconbox .getText().toString());
        myEdit.putString("NoSelloTransAnterior", Selloanteriorblue .getText().toString());
        myEdit.putString("NSerieIridiumAnterior", SerieAnteriorIridium .getText().toString());
        myEdit.putString("IMEIIridiumAnterior", IMEIanterior .getText().toString());
        myEdit.putString("NSerieTransreceptorAnterior", SerieAnteriorBlue.getText().toString());
        myEdit.putString("NSerieConBoxAnterior", SerieAnteriorConbox.getText().toString());


        myEdit.putString("NoSelloConBoxNuevo", Sellonuevoconbox .getText().toString());
        myEdit.putString("NoSelloTransNuevo", Sellonuevoblue .getText().toString());
        myEdit.putString("NSerieIridiumNuevo", SerieNuevaIridium.getText().toString());
        myEdit.putString("IMEIIridiumNuevo", IMEINuevo .getText().toString());
        myEdit.putString("NSerieTransreceptorNuevo", SerieNuevaBlue.getText().toString());
        myEdit.putString("NSerieConBoxNuevo", SerieNuevaConbox.getText().toString());


        myEdit.putString("OtrosPermiso",OtrosPermiso.getText().toString());
        myEdit.putString("DatosTicket26",Domicilio.getText().toString());
        myEdit.putString("DatosTicket13",FechaOficio.getText().toString());
        myEdit.putString("DatosTicket12",NoOficio.getText().toString());
        myEdit.putString("DatosTicket3",NoReporte.getText().toString());
        myEdit.putString("FechaCerrado",FechaInstalacion.getText().toString());
        myEdit.putString("DatoBarco25",LocalidadPuerto.getText().toString());
        myEdit.putString("DatoBarco21",Permisionariov.getText().toString());
        myEdit.putString("DatoBarco28",RLegalV.getText().toString());
        myEdit.putString("DatoBarco20",RNPATV.getText().toString());
        myEdit.putString("DatoBarco29",CorreoEV.getText().toString());
        myEdit.putString("DatoBarco27",TelefonoFijoV.getText().toString());
        myEdit.putString("DatoBarco67",TelefonoMovilV.getText().toString());
        myEdit.putString("DatoBarco23",Estado.getText().toString());
        myEdit.putString("DatoBarco24",Municipio.getText().toString());
        myEdit.putString("DatoBarco25",Localidad.getText().toString());
        myEdit.putString("DatoBarco26",Domicilio.getText().toString());


        myEdit.putString("DatoBarco3",Nombreembarcaciónv.getText().toString());
        myEdit.putString("DatoBarco2",PuertobaseV.getText().toString());
        myEdit.putString("DatoBarco5",MatriculaV.getText().toString());
        myEdit.putString("DatoBarco18",MarcaMotorV.getText().toString());
        myEdit.putString("DatoBarco4",RNPV.getText().toString());
        myEdit.putString("DatoBarco19",PotenciaMotorV.getText().toString());
        myEdit.putString("DatoBarco15",EsloraV.getText().toString());
        myEdit.putString("DatoBarco12",TonNetoV.getText().toString());
        myEdit.putString("DatoBarco11",TonBrutoV.getText().toString());

        myEdit.putString("DatoBarco36",Permiso1.getText().toString());
        myEdit.putString("DatoBarco43",Permiso2.getText().toString());
        myEdit.putString("DatoBarco50",Permiso3.getText().toString());
        myEdit.putString("DatoBarco57",Permiso4.getText().toString());
        myEdit.putString("DatoBarco64",Permiso5.getText().toString());
        myEdit.putString("DatoBarco34",FeInicial1.getText().toString());
        myEdit.putString("DatoBarco41",FeInicial2.getText().toString());
        myEdit.putString("DatoBarco48",FeInicial3.getText().toString());
        myEdit.putString("DatoBarco55",FeInicial4.getText().toString());
        myEdit.putString("DatoBarco62",FeInicial5.getText().toString());
        myEdit.putString("DatoBarco35",FeFinal1.getText().toString());
        myEdit.putString("DatoBarco42",FeFinal2.getText().toString());
        myEdit.putString("DatoBarco49",FeFinal3.getText().toString());
        myEdit.putString("DatoBarco56",FeFinal4.getText().toString());
        myEdit.putString("DatoBarco63",FeFinal5.getText().toString());
        myEdit.commit();
        fb.GuardaInformacionTicket(DatosTicket,ObtenCheck(),ObtenDatosTicket(),this);

    }
    private String[] ObtenDatosBarcosH(){
        Datos = new String[dt.DatosBarcoDepurado.length];
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        for (int i = 0;i<dt.DatosBarcoDepurado.length;i++){
            Datos[i]=sh.getString("DatoBarco"+String.valueOf(i), "");
        }
        return Datos;

    }
    private String[] ObtenDatosTicket(){
        Datos = new String[15];
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        for (int i = 0;i<15;i++){
            Datos[i]=sh.getString("DatosTicket"+String.valueOf(i), "");
        }
        return Datos;

    }
    private void GuardaComentariosFotos(String Cancelado){
        int NumeroFotos;
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        if (Cancelado.equals("Cancelado")){
            NumeroFotos = 2;
        }else{
            NumeroFotos = 10;

        }
        SharedPreferences.Editor myEdit = sh.edit();
        for (int i =0; i<NumeroFotos; i++){
            if (Cancelado.equals("Cancelado")){
            myEdit.putString("ComentarioFoto"+(i+1),dt.FotosNoInstalacion[i]);
            myEdit.commit();}
            else {
                myEdit.putString("ComentarioFoto"+(i+1),dt.FotosReporteInstalacion[i]);
                myEdit.commit();}
            }
        }
    ////Rutinas
    public void MostrarFotos(String Ticket) {
        int NumeroFotos;
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        System.out.println(sh.getString("DatosTicket7",""));
        if (sh.getString("DatosTicket7","").equals("Cancelado")||(sh.getString("DatosTicket4", "").equals("Cancelado"))){
            NumeroFotos = 2;
        }else{
            NumeroFotos = 10;

        }
        ArrayList<RFP> ListaFotos = new ArrayList<>();
        String Comen1;
        String Comen2;
        String Desc;
        Desc = sh.getString("ComentarioFoto1","");
        if (Desc.equals("")) GuardaComentariosFotos("Instalacion");
        for (int i=0;i<NumeroFotos;i=i+2){
            Bitmap bitmapi,bitmapi2 = null;
            if (sh.getString("DatosTicket7","").equals("Cancelado")||(sh.getString("DatosTicket4", "").equals("Cancelado"))){
                Comen1 =ObtenComentarios()[i];
                Comen2 =ObtenComentarios()[i+1];
                System.out.println("Cancelado"+Comen1+Comen2);
            }else{
                Comen1 =dt.FotosReporteInstalacion[i];
                Comen2 =dt.FotosReporteInstalacion[i+1];
                System.out.println("En curso"+Comen1+Comen2);
            }
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
            FotosRFP.setAdapter(adapterFotos);

        }

    }
    private void RutinaCancelado(){
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        DatosVer.setChecked(false);
        EquiposIns.setVisibility(View.GONE);
        FotosVer.setChecked(true);
        DatosView.setVisibility(View.GONE);
        EquiposInstalados.setVisibility(View.GONE);
        FotosRFP.setVisibility(View.VISIBLE);
        myEdit.putString("Pantalla","Datos");
        myEdit.commit();
    }
    private void RutinaFotos(){
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
       System.out.println("Datos 3 : "+sh.getString("DatosTicket3",""));
        if (sh.getString("DatosTicket3","").contains("C-")){
            System.out.println("Correctivo");
            FotosRFP.setVisibility(View.GONE);
            FotosCorrectivo.setVisibility(View.VISIBLE);
        }else{
            FotosRFP.setVisibility(View.VISIBLE);
            FotosCorrectivo.setVisibility(View.GONE);
        }
        FotosVer.setChecked(true);
        DatosVer.setChecked(false);
        EquiposIns.setChecked(false);
        FotosVer.setChecked(true);
        DatosView.setVisibility(View.GONE);
        EquiposInstalados.setVisibility(View.GONE);
        SolucionCorrectivo.setVisibility(View.GONE);
        myEdit.putString("Pantalla","Fotos");
        myEdit.commit();
    }
    public void AñadeFotoCorrectivo(View view){
        Intent i = new Intent(this,SelectFoto.class);
        SharedPreferences sh = getSharedPreferences(Datos[3], MODE_PRIVATE);

        SharedPreferences.Editor myEdit = sh.edit();
        int ID = sh.getInt("ID", 1);
        i.putExtra("Datos",Datos);
        i.putExtra("ID",String.valueOf(ID));
        i.putExtra("Where","Correctivo");
        System.out.println(Datos[3]+String.valueOf(ID));
        startActivity(i);
    }
    private  void CrearArchivoCorrectivo(){
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
            //correctivos.CreaDocumento(Ticket);
            correctivo.CreaArchivo2(Ticket,imageData);
            correctivo.CreaRF(Datos,imageData,ID-1,ObtenComentariosCorrectivo());
            //else interno.CreaRF(Datos,imageData,ID-1,ObtenComentarios());
            /*¿Intent intent = new Intent(this,PDFViewer.class);
            intent.putExtra("Iden","RFD");
            intent.putExtra("Datos",Datos);
            startActivity(intent);*/

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    private String[]  ObtenComentariosCorrectivo(){
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
    private void MuestraFotosCorrectivo() {
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
                adapterFotos2 = new AdapterFotos(ListaFotos);
                FotosView.setAdapter(adapterFotos2);
                System.out.println(Desc);

            }
        }

    }
    private void RutinaEquipos(){
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        if (sh.getString("DatosTicket3","").contains("C-")){
            System.out.println("Correctivo");
            EquiposInstalados.setVisibility(View.GONE);
            SolucionCorrectivo.setVisibility(View.VISIBLE);
            if (ObtenCheckGuardadas()[19]){
                Seriesconbox.setVisibility(View.VISIBLE);
            }else{
                Seriesconbox.setVisibility(View.GONE);
            }
            if (ObtenCheckGuardadas()[21]){
                Seriesblue.setVisibility(View.VISIBLE);
            }else{
                Seriesblue.setVisibility(View.GONE);
            }

        }else{
            EquiposInstalados.setVisibility(View.VISIBLE);
            SolucionCorrectivo.setVisibility(View.GONE);
        }
        DatosVer.setChecked(false);
        EquiposIns.setChecked(true);
        FotosVer.setChecked(false);
        DatosView.setVisibility(View.GONE);

        FotosRFP.setVisibility(View.GONE);
        FotosCorrectivo.setVisibility(View.GONE);
        myEdit.putString("Pantalla","Equipos");
        myEdit.commit();
    }
    private void RutinaDatos(){
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();

        DatosView.setVisibility(View.VISIBLE);
        DatosVer.setChecked(true);
        EquiposIns.setChecked(false);
        FotosVer.setChecked(false);
        EquiposInstalados.setVisibility(View.GONE);
        FotosRFP.setVisibility(View.GONE);
        FotosCorrectivo.setVisibility(View.GONE);
        SolucionCorrectivo.setVisibility(View.GONE);
        myEdit.putString("Pantalla","Datos");
        myEdit.commit();
    }
    private void RutinaCharging() {
        Entraslado.setVisibility(View.GONE);
        Charging.setVisibility(View.VISIBLE);
        DatosView.setVisibility(View.GONE);
        FotosRFP.setVisibility(View.GONE);
        EquiposInstalados.setVisibility(View.GONE);
        FotosCorrectivo.setVisibility(View.GONE);
    }
    private void RutinaCreaArchivoInstalacion(boolean first){
        try{
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.seguritech);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapData = stream.toByteArray();
        ImageData imageData = ImageDataFactory.create(bitmapData);
        if (first){
            instalacionOficialCarta.CreaArchivo(Ticket, imageData);
            instalacionOficialCarta.CreaArchivo3(Ticket,imageData);
            convenios.CreaDocumento(Ticket);
            Intent i = new Intent(this, PDFViewer.class);
            i.putExtra("Iden", "RD");
            i.putExtra("Datos", ObtenDatosTicket());
            startActivity(i);
        }
        else{
            //instalacionOficialCarta.CreaArchivo(Ticket, imageData);
            //instalacionOficialCarta.CreaArchivo3(Ticket,imageData);
            convenios.CreaDocumento(Ticket);
            Intent i = new Intent(this, ReporteInstalacion.class);
            i.putExtra("CrearArchivo", "RD");
            startActivity(i);
        }

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    private void RutinaCreaArchivoNOInstalacion(){
        try{
            Bitmap bitmap;
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.seguritech);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapData = stream.toByteArray();
            ImageData imageData = ImageDataFactory.create(bitmapData);
            SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sh.edit();
            instalacionOficialCarta.CreaArchivo2(Ticket, imageData);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
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
    private boolean ValidaFotos(){
        int NumeroFotos;
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        if (sh.getString("DatosTicket7","").equals("Cancelado")||(sh.getString("DatosTicket4", "").equals("Cancelado"))){
            NumeroFotos = 2;
        }else{
            NumeroFotos = 10;

        }
        for (int i=0;i<NumeroFotos;i++){
            if (ObtenImagen(Ticket,(i+1))==null){System.out.println("Foto"+i);return true;}
        }
        return false;
    }
    private boolean ValidaJuatificacion(){
        if (Justificacion.getText().toString().equals("")) return false;
        return true;
    }
    private boolean ValidaInstalacion(){
        if (VBateria.getText().toString().equals("")) return false;
        if (NoSelloTrans.getText().toString().equals("")) return false;
        if (NoSelloConBox.getText().toString().equals("")) return false;
        if (NSerieIridium.getText().toString().equals("")) return false;
        if (NSerieConBox.getText().toString().equals("")) return false;
        if (NSerieTransreceptor.getText().toString().equals("")) return false;
        if (Contacto.getText().toString().equals("")) return false;

        return true;
    }
    private boolean ValidaCorrectivo(){

        return true;
    }
    /////CHECK
    private Boolean[] ObtenCheck(){
     Boolean [] Check = new Boolean[]{
             Button1.isChecked(),///Principal1
             Button2.isChecked(),///Sec1
             Button3 .isChecked(),///Sec2
             Button4.isChecked(),///////Sec3
             Button5.isChecked(),///Principal2
             Button6 .isChecked(),///Sec4
             Button7 .isChecked(),///Sec5
             Button8 .isChecked(),///Sec6
             Button9 .isChecked(),///Principal3
             Button10 .isChecked(),//Sec7
             Button11 .isChecked(),///Sec8
             Button12 .isChecked(),///Sec8
             Button13 .isChecked(),///Sec8
             Button14 .isChecked(),///Sec8
             Button15 .isChecked(),///Sec8
             Button16 .isChecked(),///Sec8
             Button17 .isChecked(),///Sec8
             Button18 .isChecked(),///Sec8
             Button19 .isChecked(),///Sec8
             SiConbox.isChecked(),
             NoConbox.isChecked(),
             SiBlue.isChecked(),
             NoBlue.isChecked()

     };
     return Check;

 }
    private void ListadeTickets(){
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        System.out.println("Buscando ticket en barco: "+sh.getString("DatoBarco4",""));
        refBarcos.child(sh.getString("DatoBarco4","")).child("Tickets").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String [] Tickets = new String[(int) snapshot.getChildrenCount()];
                String [] TipoServicio = new String[(int) snapshot.getChildrenCount()];
                int i = 0;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    Tickets[i] = postSnapshot.getKey();
                    TipoServicio[i] = postSnapshot.getValue().toString();
                    if (postSnapshot.getKey().contains("S-")){
                        ObtenNumerosdeSerie(postSnapshot.getKey(),postSnapshot.getValue().toString());
                    }
                    else if (postSnapshot.getKey().contains("C-")){
                        ObtenNumerosdeSerie(postSnapshot.getKey(),postSnapshot.getValue().toString());
                    }
                    System.out.println("Ticket encontrado:"+postSnapshot.getValue());
                    i++;
                    //Toast.makeText(ListaServicios.this,postSnapshot.getKey(),Toast.LENGTH_SHORT).show();
                    System.out.println(postSnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void ObtenNumerosdeSerie(String Ticket,String TipoServicio){
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        System.out.println("Tipo de servicio"+TipoServicio);
        System.out.println("Ticket"+Ticket);
        refTickets.child(TipoServicio).child(Ticket).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    System.out.println("Ticket encontrado");

                    if (Ticket.contains("S-")) {
                        System.out.println("Ticket" + Ticket);
                        String NumerodeSerieBlueTraker = snapshot.child("NSerieTransreceptor").getValue(String.class);
                        String NumerodeSerieConBox = snapshot.child("NSerieConBox").getValue(String.class);
                        String NumerodeSerieIridium = snapshot.child("NSerieIridium").getValue(String.class);
                        String IMEIiridium = snapshot.child("IMEIIridium").getValue(String.class);
                        String SelloconBox = snapshot.child("NoSelloConBox").getValue(String.class);
                        String SelloBlueTraker = snapshot.child("NoSelloTrans").getValue(String.class);

                        myEdit.putString("NoSelloConBoxAnterior", SelloconBox);
                        myEdit.putString("NoSelloTransAnterior", SelloBlueTraker);
                        myEdit.putString("NSerieIridiumAnterior", NumerodeSerieIridium);
                        myEdit.putString("IMEIIridiumAnterior", IMEIiridium);
                        myEdit.putString("NSerieTransreceptorAnterior", NumerodeSerieBlueTraker);
                        myEdit.putString("NSerieConBoxAnterior", NumerodeSerieConBox);


                        Selloanteriorconbox .setText(SelloconBox);
                        //Sellonuevoconbox = (EditText) findViewById(R.id.Sellonuevoconbox);
                        Selloanteriorblue.setText(SelloBlueTraker);
                        //Sellonuevoblue = (EditText) findViewById(R.id.Sellonuevoblue);
                        SerieAnteriorIridium.setText(NumerodeSerieIridium);
                        //SerieNuevaIridium = (EditText) findViewById(R.id.SerieNuevaIridium);
                        IMEIanterior.setText(IMEIiridium);
                        //IMEINuevo = (EditText) findViewById(R.id.IMEINuevo);
                        SerieAnteriorConbox.setText(NumerodeSerieConBox);
                        SerieAnteriorBlue.setText(NumerodeSerieBlueTraker);

                        /*NSerieIridium.setText(IMEIiridium);
                        IMEIIridium.setText(NumerodeSerieIridium);
                        NSerieTransreceptor.setText(NumerodeSerieBlueTraker);
                        NSerieConBox.setText(NumerodeSerieConBox);*/



                        System.out.println("Serie Transreceptor: " + NumerodeSerieBlueTraker);
                        System.out.println("Serie Conboc: " + NumerodeSerieConBox);
                    }
                    if (Ticket.contains("C-")&&!Ticket.equals(sh.getString("DatosTicket3",""))){

                        System.out.println("Ticket" + Ticket);

                        String NumerodeSerieBlueTraker = snapshot.child("NSerieTransreceptorAnterior").getValue(String.class);
                        String NumerodeSerieConBox = snapshot.child("NSerieConBoxAnterior").getValue(String.class);
                        String NumerodeSerieIridium = snapshot.child("NSerieIridiumAnterior").getValue(String.class);
                        String IMEIiridium = snapshot.child("NSerieIridiumAnterior").getValue(String.class);
                        String SelloconBox = snapshot.child("NoSelloConBoxAnterior").getValue(String.class);
                        String SelloBlueTraker = snapshot.child("NoSelloTransAnterior").getValue(String.class);



                        myEdit.putString("NoSelloConBoxAnterior", SelloconBox);
                        myEdit.putString("NoSelloTransAnterior", SelloBlueTraker);
                        myEdit.putString("NSerieIridiumAnterior", NumerodeSerieIridium);
                        myEdit.putString("IMEIIridiumAnterior", IMEIiridium);
                        myEdit.putString("NSerieTransreceptorAnterior", NumerodeSerieBlueTraker);
                        myEdit.putString("NSerieConBoxAnterior", NumerodeSerieConBox);

                        Selloanteriorconbox .setText(SelloconBox);
                        //Sellonuevoconbox = (EditText) findViewById(R.id.Sellonuevoconbox);
                        Selloanteriorblue.setText(SelloBlueTraker);
                        //Sellonuevoblue = (EditText) findViewById(R.id.Sellonuevoblue);
                        SerieAnteriorIridium.setText(NumerodeSerieIridium);
                        //SerieNuevaIridium = (EditText) findViewById(R.id.SerieNuevaIridium);
                        IMEIanterior.setText(IMEIiridium);
                        //IMEINuevo = (EditText) findViewById(R.id.IMEINuevo);
                        SerieAnteriorConbox.setText(NumerodeSerieConBox);
                        SerieAnteriorBlue.setText(NumerodeSerieBlueTraker);

                        System.out.println("Serie Transreceptor: " + NumerodeSerieBlueTraker);
                        System.out.println("Serie Conboc: " + NumerodeSerieConBox);
                    }
                }
                else{
                    System.out.println("Ticket NO encontrado");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void GuardaCheck(){
        SharedPreferences sharedPreferences = getSharedPreferences(Ticket,MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        for (int i=0;i<ObtenCheck().length;i++){
                myEdit.putBoolean("CheckSec"+(i), ObtenCheck()[i]);

        }
        myEdit.commit();
    }
    private void SetCheckSec() {
        Button1.setChecked(ObtenCheckGuardadas()[0]);///Sec1
        Button2.setChecked(ObtenCheckGuardadas()[1]);///Sec2
        Button3.setChecked(ObtenCheckGuardadas()[2]);///////Sec3
        Button4.setChecked(ObtenCheckGuardadas()[3]);///Sec4
        Button5.setChecked(ObtenCheckGuardadas()[4]);///Sec5
        Button6.setChecked(ObtenCheckGuardadas()[5]);///Sec6
        Button7.setChecked(ObtenCheckGuardadas()[6]);///Sec7
        Button8.setChecked(ObtenCheckGuardadas()[7]);///Sec8
        Button9.setChecked(ObtenCheckGuardadas()[8]);///Sec9
        Button10.setChecked(ObtenCheckGuardadas()[9]);///Sec10
        Button11.setChecked(ObtenCheckGuardadas()[10]);///Sec11
        Button12.setChecked(ObtenCheckGuardadas()[11]);///Sec11
        Button13.setChecked(ObtenCheckGuardadas()[12]);///Sec11
        Button14.setChecked(ObtenCheckGuardadas()[13]);///Sec11
        Button15.setChecked(ObtenCheckGuardadas()[14]);///Sec11
        Button16.setChecked(ObtenCheckGuardadas()[15]);///Sec11
        Button17.setChecked(ObtenCheckGuardadas()[16]);///Sec11
        Button18.setChecked(ObtenCheckGuardadas()[17]);///Sec11
        Button19.setChecked(ObtenCheckGuardadas()[18]);///Sec11

        SiConbox.setChecked(ObtenCheckGuardadas()[19]);
        NoConbox.setChecked(ObtenCheckGuardadas()[20]);
        SiBlue.setChecked(ObtenCheckGuardadas()[21]);
        NoBlue.setChecked(ObtenCheckGuardadas()[22]);
    }
    private Boolean[] ObtenCheckGuardadas(){
        Boolean [] Check = new Boolean[ObtenCheck().length];

        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);

        for (int i=0;i<ObtenCheck().length;i++){
            if (i==20||i==22)Check[i] = sh.getBoolean("CheckSec"+(i), true);
            else Check[i] = sh.getBoolean("CheckSec"+(i), false);

        }

        return Check;

    }
    public void rutinasiconbox(View view){
        NoConbox.setChecked(false);
        SiConbox.setChecked(true);
        GuardaCheck();
        Seriesconbox.setVisibility(View.VISIBLE);

    }
    public void rutinanoconbox(View view){
        SiConbox.setChecked(false);
        NoConbox.setChecked(true);
        GuardaCheck();
        Seriesconbox.setVisibility(View.GONE);
    }
    public void rutinasibluetraker(View view){
        Seriesblue.setVisibility(View.VISIBLE);
        NoBlue.setChecked(false);
        SiBlue.setChecked(true);
        GuardaCheck();
    }
    public void rutinanobluetraker(View view){
        Seriesblue.setVisibility(View.GONE);
        SiBlue.setChecked(false);
        NoBlue.setChecked(true);
        GuardaCheck();
    }
    private String[] ObtenDatosTickets(){
        String [] DatosTickets = new String[dt.DatosTicketAReporte.length];
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        for (int i = 0;i<dt.DatosTicketAReporte.length;i++){
            DatosTickets[i]=sh.getString("DatosTicket"+String.valueOf(i), "");
        }
        return DatosTickets;
    }
    ///Acciones
    public void SelectIngeniero(View view){
        Intent intent = new Intent(this,ListaUsuarios.class);
        Datos = ObtenDatosTickets();
        Datos [8] ="Ticket";
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    public void Save(View view){
        GuardaDatosInstalacion();
    }
    public void ModificaCheck(View view){
        GuardaCheck();
        GuardaDatosInstalacion();
        //SetCheckSec();
    }
    public void VerDatos(View view){
        GuardaDatosInstalacion();
        RutinaDatos();
    }
    public void VerEquipos(View view){
        GuardaDatosInstalacion();
        RutinaEquipos();
    }
    public void VerFotos(View view){
        GuardaDatosInstalacion();
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        if (sh.getString("DatosTicket3", "").contains("C-")) MuestraFotosCorrectivo();
        else MostrarFotos(Ticket);
        RutinaFotos();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void CancelaServicio(View view){
        GuardaDatosInstalacion();

        AlertDialog.Builder builder = new AlertDialog.Builder(ReporteInstalacion.this);
        builder.setTitle("Cancelacion de Ticket");
        builder.setMessage("¿Esta seguro de cancelar?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ReporteInstalacion.this,"Cerrando Documento",Toast.LENGTH_SHORT).show();
                RutinaCharging();
                ActualizaEstatus("FechaCancelado","HoraCancelado","Cancelado",3);
                Toast.makeText(ReporteInstalacion.this, "Cambiando Estatus",Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();

            }


        }).show();


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void CambiaEstatus(View view) {
        GuardaDatosInstalacion();
        SharedPreferences sh2 = getSharedPreferences(Ticket, MODE_PRIVATE);


            if (sh2.getString("DatosTicket7", "").equals("Cerrado") || sh2.getString("DatosTicket7", "").equals("Cancelado")) {
                Toast.makeText(this, "Generando Archivo", Toast.LENGTH_SHORT).show();
                if (sh2.getString("DatosTicket7", "").equals("Cerrado")) {
                    if (sh2.getString("DatosTicket4", "").equals("Cancelado")){
                        RutinaCreaArchivoNOInstalacion();
                        Intent i = new Intent(this, PDFViewer.class);
                        i.putExtra("Iden", "NI");
                        i.putExtra("Datos", ObtenDatosTicket());
                        startActivity(i);
                    }else if (sh2.getString("DatosTicket3","").contains("C-")){
                        CrearArchivoCorrectivo();
                        Intent i = new Intent(this, PDFViewer.class);
                        i.putExtra("Iden", "RD");
                        i.putExtra("Datos", ObtenDatosTicket());
                        startActivity(i);
                    }
                    else {
                        RutinaCreaArchivoInstalacion(false);
                    }
                }
                else {
                    if (!ValidaJuatificacion()) {
                        Toast.makeText(this, "Añada justificacion", Toast.LENGTH_SHORT).show();
                        RutinaFotos();
                    } else if (ValidaFotos()) {
                        Toast.makeText(this, "Complete las Fotos", Toast.LENGTH_SHORT).show();
                        RutinaFotos();
                    } else {

                    }
                }
            }
            else {

                AlertDialog.Builder builder = new AlertDialog.Builder(ReporteInstalacion.this);
                builder.setTitle("Cambio de Estatus");
                builder.setMessage("¿Esta seguro de cambiar el estatus?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RutinaActualiza();
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();

                    }


                }).show();


            }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void RutinaActualiza(){
        SharedPreferences sh2 = getSharedPreferences(Ticket, MODE_PRIVATE);
        System.out.println(sh2.getString("DatosTicket7", ""));
        if (sh2.getString("DatosTicket7", "").equals("En traslado")) {
            RutinaCharging();
            ActualizaEstatus("FechaCurso", "HoraCurso", "En curso", 3);
            Toast.makeText(this, "Cambiando Estatus", Toast.LENGTH_SHORT).show();
        } else if (sh2.getString("DatosTicket7", "").equals("En curso")) {
            if(sh2.getString("DatosTicket4","").equals("Cancelado")){
                if (!ValidaJuatificacion()) {
                    Toast.makeText(this, "Añada justificacion", Toast.LENGTH_SHORT).show();
                    RutinaEquipos();
                } else if (ValidaFotos()) {
                    Toast.makeText(this, "Complete las Fotos", Toast.LENGTH_SHORT).show();
                    RutinaFotos();
                } else {
                    RutinaCharging();
                    ActualizaEstatus("FechaCerrado", "HoraCerrado", "Cerrado", 4);
                    Toast.makeText(this, "Cambiando Estatus", Toast.LENGTH_SHORT).show();
                }
            }
            else if (sh2.getString("DatosTicket3", "").contains("C-")){
                if (!ValidaCorrectivo()) {
                    Toast.makeText(this, "Complete los campos requeridos", Toast.LENGTH_SHORT).show();
                    RutinaFotos();
                }
                else {
                    RutinaCharging();
                    ActualizaEstatus("FechaCerrado", "HoraCerrado", "Cerrado", 4);
                    Toast.makeText(this, "Cambiando Estatus", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                if (!ValidaInstalacion()) {
                    Toast.makeText(this, "Complete los campos requeridos", Toast.LENGTH_SHORT).show();
                    RutinaEquipos();
                } else if (ValidaFotos()) {
                    Toast.makeText(this, "Complete las Fotos", Toast.LENGTH_SHORT).show();
                    RutinaFotos();
                } else {
                    RutinaCharging();
                    ActualizaEstatus("FechaCerrado", "HoraCerrado", "Cerrado", 4);
                    Toast.makeText(this, "Cambiando Estatus", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if (sh2.getString("DatosTicket7", "").equals("Cerrado")) {

            System.out.println("Intentanto generar archivo");
            if(sh2.getString("DatosTicket4","").equals("Cancelado")){
                RutinaCreaArchivoNOInstalacion();
                Intent i = new Intent(this, PDFViewer.class);
                i.putExtra("Iden", "NI");
                i.putExtra("Datos", ObtenDatosTicket());
                startActivity(i);
            }else if (sh2.getString("DatosTicket3","").contains("C-")){
                CrearArchivoCorrectivo();
                Intent i = new Intent(this, PDFViewer.class);
                i.putExtra("Iden", "RD");
                i.putExtra("Datos", ObtenDatosTicket());
                startActivity(i);
            }
            else {
                RutinaCreaArchivoInstalacion(false);
            }
        } else {
            if (!ValidaJuatificacion()) {
                Toast.makeText(this, "Añada justificacion", Toast.LENGTH_SHORT).show();
                RutinaEquipos();
            } else if (ValidaFotos()) {
                Toast.makeText(this, "Complete las Fotos", Toast.LENGTH_SHORT).show();
                RutinaFotos();
            } else {
                RutinaCreaArchivoNOInstalacion();
                Intent i = new Intent(this, PDFViewer.class);
                i.putExtra("Iden", "NI");
                i.putExtra("Datos", ObtenDatosTicket());
                startActivity(i);
            }

        }
    }
    private String [] RegistraNumerosdeserieCorrect(){
         String [] NumerosdeSerie = new String[]{
                 "Bluetraker antes",
                 "Bluetraker despues",
                 "Conbox antes",
                 "Conbox despues",
                 "IMEI antes",
                 "IMEI despues",
                 "Serie Iridium antes",
                 "Serie Iridium despues",
                 "Sello antes",
                 "Sello despues"

         };





        //SerieAnteriorConbox,SerieNuevaConbox,SerieAnteriorBlue,SerieNuevaBlue;

         return NumerosdeSerie;
    }
    //Estatus
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void ActualizaEstatus(String sh1,String sh2,String Estatus, int ID) {
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm");
        myEdit.putString(sh1, String.valueOf(dtf.format(LocalDateTime.now())));
        myEdit.putString(sh2, String.valueOf(dtf2.format(LocalDateTime.now())));
        myEdit.commit();
        String[] HoraFecha = new String[]{
                sh.getString(sh1, ""),
                sh.getString(sh2, ""),
                sh.getString("DatosTicket4",""),
                Estatus,

        };
        if ((Estatus).equals("Cerrado")){

            ///Rutina de actualizacion en firebase
            System.out.println("Cerrando servicio");
            if(sh.getString("DatosTicket4","").equals("Cancelado")){
                RutinaCreaArchivoNOInstalacion();
            }else if (sh.getString("DatosTicket3","").contains("C-")){
                CrearArchivoCorrectivo();
                Intent i = new Intent(this, PDFViewer.class);
                i.putExtra("Where", "Correctivo");
                i.putExtra("Iden", "RD");
                i.putExtra("Datos", ObtenDatosTicket());
                startActivity(i);
            }
            else{RutinaCreaArchivoInstalacion(false); }
            mSheetServiceHelper.Escribeentabla4(HoraFecha,Ticket)
                    .addOnSuccessListener(ok ->mSheetServiceHelper.ActualizaBitacora(ObtenDatosTicket()[10],"Ticket"+Estatus,HoraFecha[1],HoraFecha[0],"-",0)
                            .addOnSuccessListener(OK -> fb.ActualizaEstatus(ObtenDatosTicket(),this,HoraFecha,Estatus,ID)  )
                            .addOnFailureListener(Fail -> System.out.println(Fail)))
                    .addOnFailureListener(Fail -> System.out.println(Fail));
        }
        else if ((Estatus).equals("Cancelado")){
            GuardaComentariosFotos("Cancelado");
            mSheetServiceHelper.Escribeentabla5(HoraFecha,Ticket)
                    .addOnSuccessListener(ok ->mSheetServiceHelper.ActualizaBitacora(ObtenDatosTicket()[10],"Ticket"+Estatus,HoraFecha[1],HoraFecha[0],"-",0)
                            .addOnSuccessListener(OK -> fb.ActualizaEstatus(ObtenDatosTicket(),this,HoraFecha,Estatus,ID)  )
                            .addOnFailureListener(Fail -> System.out.println(Fail)))
                    .addOnFailureListener(Fail -> System.out.println(Fail));
        }
        else if ((Estatus).equals("En traslado")){
                       mSheetServiceHelper.Escribeentabla2(HoraFecha,Ticket)
                    .addOnSuccessListener(ok ->mSheetServiceHelper.ActualizaBitacora(ObtenDatosTicket()[10],"Ticket en Traslado",HoraFecha[0],HoraFecha[1],"-",0)
                            .addOnSuccessListener(OK -> fb.ActualizaEstatus(ObtenDatosTicket(),this,HoraFecha,Estatus,ID) )
                            .addOnFailureListener(Fail -> System.out.println(Fail)))
                    .addOnFailureListener(Fail -> System.out.println("Esribe en tabla 2"+Fail));
        }
        else {
            if(sh.getString("DatosTicket4","").equals("Cancelado")){
                GuardaComentariosFotos("Cancelado");
            }
            mSheetServiceHelper.Escribeentabla3(HoraFecha,Ticket)
                    .addOnSuccessListener(ok -> mSheetServiceHelper.ActualizaBitacora(ObtenDatosTicket()[10], "Ticket" + Estatus, HoraFecha[1], HoraFecha[0], "-", 0)
                            .addOnSuccessListener(OK -> fb.ActualizaEstatus(ObtenDatosTicket(), this, HoraFecha, Estatus, ID))
                            .addOnFailureListener(Fail -> System.out.println(Fail)))
                    .addOnFailureListener(Fail -> System.out.println(Fail));
        }
    }
    ////SCAN
    public void Scan1(View view){
        SharedPreferences sh = getSharedPreferences("TempShared", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.putString("Ticket",Ticket);
        myEdit.commit();
        SharedPreferences sh2 = getSharedPreferences(Ticket, MODE_PRIVATE);
        myEdit = sh2.edit();
        myEdit.putBoolean("Scan1",true);
        myEdit.commit();
        GuardaDatosInstalacion();
        Escaneo ("No. Serie Iridium");
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
        GuardaDatosInstalacion();
        Escaneo ("IMEI Iridium");
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
        GuardaDatosInstalacion();
        Escaneo ("No. Serie Transreceptor");

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
        GuardaDatosInstalacion();
        Escaneo ("No. Serie Conbox");

    }
    public void Escaneo (String Lectura){
        IntentIntegrator intentIntegrator = new IntentIntegrator(ReporteInstalacion.this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Lector - "+Lectura);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.initiateScan();
    }
    @Override
    public void onBackPressed() {
        GuardaDatosInstalacion();
        Datos = ObtenSharedPreference();
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        Datos[8]= sh.getString("DatosTicket4","");
        Intent i = new Intent(this,ListaServicios.class);
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
                        if(NombreBarco.equals("")||Datos[7].equals("Actualizacion")||Datos[7].equals("Re-asignacion"))GuardaDatosBarco();
                        ConfiguraIncio();
                    }
                    else {
                        ConfiguraIncio();
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