package com.bmr.conapesca;

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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bmr.conapesca.Adapters.AdapterFotos;
import com.bmr.conapesca.Datos.Datos;
import com.bmr.conapesca.Documentos.Correctivos2;
import com.bmr.conapesca.Entidades.Fotos;
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

public class CorrectivosRD extends AppCompatActivity {
    Datos dt = new Datos();
    AdapterFotos adapterFotos2;
    RecyclerView FotosView;
    Correctivos2 correctivos2 = new Correctivos2();
    Firebase fb = new Firebase();
    String[] Datos;
    EditText Servicio, Tecnologia, FallaReportada, Diagnosticco, Soluciones, Refacciones,Citronella,FechaCerrado,Horacerrado;
    String Ticket, Noficio;
    LinearLayout Entransito, Correctivo,CambiaEstatus,Controles,FotosCorrectivo;
    ProgressBar Charging;
    RadioButton DatosVer,EquiposIns,FotosVer;
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference refUsuarios = database.getReference("Usuarios");
    public DatabaseReference refTickets = database.getReference("Tickets");

    private static final int REQUEST_CODE_SIGN_IN = 1;
    private DriveServiceHelper mDriveServiceHelper;
    private SheetsServiceHelper mSheetServiceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correctivos_rd);
        FotosView = (RecyclerView) findViewById(R.id.FotosRFC);
        FotosView.setLayoutManager(new LinearLayoutManager(this));
        Servicio = (EditText) findViewById(R.id.Servicio);
        Tecnologia = (EditText) findViewById(R.id.Tecnologia);
        FallaReportada = (EditText) findViewById(R.id.FallaReportada);
        Diagnosticco = (EditText) findViewById(R.id.Diagnosticco);
        Soluciones = (EditText) findViewById(R.id.Soluciones);
        Refacciones = (EditText) findViewById(R.id.Refacciones);
        Citronella = (EditText) findViewById(R.id.Citronella);
        FechaCerrado = (EditText) findViewById(R.id.FechaCerrado);
        Horacerrado = (EditText) findViewById(R.id.Horacerrado);
        Entransito = (LinearLayout) findViewById(R.id.Entransito);
        Correctivo = (LinearLayout) findViewById(R.id.Correctivo);
        CambiaEstatus = (LinearLayout) findViewById(R.id.CambiaEstatus);
        FotosCorrectivo = (LinearLayout) findViewById(R.id.FotosCorrectivo);
        Controles = (LinearLayout) findViewById(R.id.Controles);

        Charging = (ProgressBar) findViewById(R.id.Charging);
        EquiposIns = (RadioButton) findViewById(R.id.EquiposIns) ;
        FotosVer = (RadioButton) findViewById(R.id.FotosVer) ;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                Datos = extras.getStringArray("Datos");
            }
        } else {
            Datos = (String[]) savedInstanceState.getSerializable("Datos");
        }
        if (Datos != null) {
            Ticket = Datos[3];
            System.out.println(Datos[7]);
            System.out.println(Datos[8]);
        } else {
            SharedPreferences sh = getSharedPreferences("TempShared", MODE_PRIVATE);
            Ticket = sh.getString("Ticket", "");
            System.out.println("Ticket" + Ticket);
        }
        SharedPreferences sh3 = getSharedPreferences("TempShared", MODE_PRIVATE);
        SharedPreferences.Editor myEdit3 = sh3.edit();
        myEdit3.putString("Ticket", Ticket);
        myEdit3.commit();
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        Noficio = sh.getString("DatosTicket1", "");

        if (Noficio.equals("") || Datos[7].equals("Actualizacion") || Datos[7].equals("Re-asignacion")) GuardaDatosTicket();
        requestSignIn();


    }

    private void GuardaDatosTicket() {
        System.out.println("Guardando Datos Ticket");
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        for (int i = 0; i < 15; i++) {
            myEdit.putString("DatosTicket" + i, Datos[i]);
            System.out.println(Datos[i]);
            myEdit.commit();
        }
        refTickets.child(Datos[4]).child(Datos[3]).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    System.out.println(dataSnapshot.child("Contacto").getValue(String.class));
                    System.out.println(dataSnapshot.child("NSerieIridium").getValue(String.class));


                    myEdit.putString("CredResponsable", dataSnapshot.child("CredResponsable").getValue(String.class));
                    myEdit.putString("CorreoResposable", dataSnapshot.child("CorreoResposable").getValue(String.class));
                    myEdit.putString("DireccionResponsable", dataSnapshot.child("DireccionResponsable").getValue(String.class));

                    myEdit.putString("Contacto", dataSnapshot.child("Contacto").getValue(String.class));
                    myEdit.putString("NombreResponable", dataSnapshot.child("NombreResponable").getValue(String.class));
                    myEdit.putString("CargoResponsable", dataSnapshot.child("CargoResponsable").getValue(String.class));
                    myEdit.putString("Observaciones", dataSnapshot.child("Observaciones").getValue(String.class));
                    myEdit.putString("VoltajeBateria", dataSnapshot.child("VBateria").getValue(String.class));
                    myEdit.putString("SelloConBox", dataSnapshot.child("NoSelloConBox").getValue(String.class));
                    myEdit.putString("SelloBlueTraker", dataSnapshot.child("NoSelloTrans").getValue(String.class));
                    myEdit.putString("Justificacion", dataSnapshot.child("Justificacion").getValue(String.class));
                    myEdit.putString("NSerieIridium", dataSnapshot.child("NSerieIridium").getValue(String.class));
                    myEdit.putString("IMEIIridium", dataSnapshot.child("IMEIIridium").getValue(String.class));
                    myEdit.putString("NSerieTransreceptor", dataSnapshot.child("NSerieTransreceptor").getValue(String.class));
                    myEdit.putString("NSerieConBox", dataSnapshot.child("NSerieConBox").getValue(String.class));
                    myEdit.putString("NombreSeguritech", dataSnapshot.child("NombreIngeniero").getValue(String.class));
                    myEdit.putString("NombreSeguritech", dataSnapshot.child("NombreIngeniero").getValue(String.class));
                    myEdit.putString("HoraCerrado", dataSnapshot.child("HoraCerrado").getValue(String.class));
                    myEdit.putString("FechaCerrado", dataSnapshot.child("FechaCerrado").getValue(String.class));
                    myEdit.putString("UID", dataSnapshot.child("UID").getValue(String.class));
                    myEdit.commit();



                    /*for (int i=0; i<ObtenCheck().length-4;i++){
                        myEdit.putBoolean("CheckSec"+i,dataSnapshot.child("Check"+i).getValue(Boolean.class));
                        myEdit.commit();
                    }
                    SetCheckSec();*/

                } else {
                    System.out.println(dataSnapshot.child("No se encontraron Datos").getValue(String.class));
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        Toast.makeText(this, "Datos de Ticket guardados", Toast.LENGTH_SHORT).show();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void ConfiguraIncio() {

        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        System.out.println("DatosTicket 7: " + sh.getString("DatosTicket7", ""));
        setDatos();
        if (sh.getString("DatosTicket7", "").equals("Asignado")) {
            RutinaActualiza();
        }else if ((sh.getString("DatosTicket7", "").equals("En traslado"))){
            CambiaEstatus.setVisibility(View.VISIBLE);
            Entransito.setVisibility(View.VISIBLE);
            Charging.setVisibility(View.GONE);


        }
        else {
            Controles.setVisibility(View.VISIBLE);
            CambiaEstatus.setVisibility(View.VISIBLE);
           //Correctivo.setVisibility(View.VISIBLE);
            if (sh.getString("Pantalla", "").equals("Fotos")) RutinaFotos();
            else RutinaDatos();
            Charging.setVisibility(View.GONE);
        }


    }

    public void VerDatos(View view){
        GuardaDatosInstalacion();
        RutinaDatos();
    }
    public void VerFotos(View view){
        GuardaDatosInstalacion();
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        MuestraFotosCorrectivo();
        RutinaFotos();
    }

    private void GuardaDatosInstalacion(){
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();

        String [] DatosTicket = new String[] {
                "",
                "",
                "",
               "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                FallaReportada.getText().toString(),
                Diagnosticco.getText().toString(),
                Soluciones.getText().toString(),
                Refacciones.getText().toString(),
                FechaCerrado.getText().toString(),
                Horacerrado.getText().toString()
        };


        myEdit.putString("Fallareportada",FallaReportada.getText().toString());
        myEdit.putString("Diagnostico",Diagnosticco.getText().toString());
        myEdit.putString("Solucion",Soluciones.getText().toString());
        myEdit.putString("Reemplazodeequipos",Refacciones.getText().toString());
        myEdit.putString("FechaCerrado",FechaCerrado.getText().toString());
        myEdit.commit();






        //myEdit.putString("DatosTicket13",FechaOficio.getText().toString());

        fb.GuardaInformacionTicket(DatosTicket,null,ObtenDatosTicket(),this);

    }

    private void setDatos(){
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);

        FallaReportada.setText(sh.getString("Fallareportada",""));
        Diagnosticco.setText(sh.getString("Diagnostico",""));
        Soluciones.setText(sh.getString("Solucion",""));
        Refacciones.setText(sh.getString("Reemplazodeequipos",""));
        Servicio.setText(sh.getString("DatosTicket3",""));
        Tecnologia.setText(sh.getString("DatosTicket11",""));

        Citronella.setText(sh.getString("DatosTicket12",""));
        FechaCerrado .setText(sh.getString("FechaCerrado",""));
        Horacerrado.setText(sh.getString("HoraCerrado",""));;

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

    private Bitmap ObtenImagen(String Ticket,int i){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(Ticket, Context.MODE_PRIVATE);
        File file = new File(directory, "Foto"+i+ ".png");
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        if (!file.exists())return null;
        return bitmap;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void ClickActualiza(View view){
        GuardaDatosInstalacion();
        AlertDialog.Builder builder = new AlertDialog.Builder( CorrectivosRD.this);
        builder.setTitle("Cambio de Estatus");
        builder.setMessage("¿Esta seguro de cambiar el estatus?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CambiaEstatus.setVisibility(View.GONE);
                Entransito.setVisibility(View.GONE);
                Correctivo.setVisibility(View.GONE);
                Charging.setVisibility(View.VISIBLE);
                RutinaActualiza();
            }
        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();

            }


        }).show();

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void RutinaActualiza() {
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        System.out.println("Estatus actual: "+sh.getString("DatosTicket7", ""));
        if (sh.getString("DatosTicket7", "").equals("Asignado")) {
            ActualizaEstatus("HoraTraslado","FechaTraslado","En traslado",2);
        }
        else if (sh.getString("DatosTicket7", "").equals("En traslado")) {
            ActualizaEstatus("FechaCurso", "HoraCurso", "En curso", 3);
        }
        else if (sh.getString("DatosTicket7", "").equals("En curso")&&validaCierre()) {
            ActualizaEstatus("FechaCerrado", "HoraCerrado", "Cerrado", 4);
        }
        else if (sh.getString("DatosTicket7", "").equals("Cerrado")) {
            RutinaCreaArchivosCorrectivo();
            Intent i = new Intent(this, PDFViewer.class);
            i.putExtra("Iden", "RD");
            i.putExtra("Datos", ObtenDatosTicket());
            startActivity(i);

        }else{
            Toast.makeText(this,"No se ha podido realizar el cambio de estatus",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,CorrectivosRD.class);
            intent.putExtra("Datos",ObtenDatosTicket());
            startActivity(intent);
        }
    }
    private boolean validaCierre(){
        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void ActualizaEstatus(String sh1,String sh2,String Estatus, int ID) {
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm");
        myEdit.putString(sh1, String.valueOf(dtf.format(LocalDateTime.now())));
        myEdit.putString(sh2, String.valueOf(dtf2.format(LocalDateTime.now())));
        myEdit.putString("DatosTicket7", Estatus);
        myEdit.commit();
        String[] HoraFecha = new String[]{
                sh.getString(sh1, ""),
                sh.getString(sh2, ""),
                sh.getString("DatosTicket4",""),
                Estatus,
        };
        if ((Estatus).equals("En traslado")){
            System.out.println("Poniendo ticket en traslado");
            mSheetServiceHelper.Escribeentabla2(HoraFecha,Ticket)
                    .addOnSuccessListener(ok ->mSheetServiceHelper.ActualizaBitacora(ObtenDatosTicket()[10],"Ticket" + Estatus,HoraFecha[0],HoraFecha[1],"-",0)
                            .addOnSuccessListener(OK -> fb.ActualizaEstatus(ObtenDatosTicket(),this,HoraFecha,Estatus,ID) )
                            .addOnFailureListener(Fail -> System.out.println(Fail)))
                    .addOnFailureListener(Fail -> System.out.println("Esribe en tabla 2"+Fail));
        }
        else if ((Estatus).equals("En curso")){
            System.out.println("Poniendo ticket en curso");
            mSheetServiceHelper.Escribeentabla3(HoraFecha,Ticket)
                    .addOnSuccessListener(ok -> mSheetServiceHelper.ActualizaBitacora(ObtenDatosTicket()[10], "Ticket" + Estatus, HoraFecha[1], HoraFecha[0], "-", 0)
                            .addOnSuccessListener(OK -> fb.ActualizaEstatus(ObtenDatosTicket(), this, HoraFecha, Estatus, ID))
                            .addOnFailureListener(Fail -> System.out.println(Fail)))
                    .addOnFailureListener(Fail -> System.out.println(Fail));
        }else if((Estatus).equals("Cerrado")){
            System.out.println("Cerrando Ticket");
            RutinaCreaArchivosCorrectivo();
            mSheetServiceHelper.Escribeentabla4(HoraFecha,Ticket)
                    .addOnSuccessListener(ok ->mSheetServiceHelper.ActualizaBitacora(ObtenDatosTicket()[10],"Ticket"+Estatus,HoraFecha[1],HoraFecha[0],"-",0)
                            .addOnSuccessListener(OK -> fb.ActualizaEstatus(ObtenDatosTicket(),this,HoraFecha,Estatus,ID)  )
                            .addOnFailureListener(Fail -> System.out.println(Fail)))
                    .addOnFailureListener(Fail -> System.out.println(Fail));

        }else{
            System.out.println("Nada");
        }
    }
    private void RutinaCreaArchivosCorrectivo(){
        try {
            Bitmap bitmap;
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.seguritech);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            SharedPreferences sh = getSharedPreferences(Datos[3], MODE_PRIVATE);
            byte[] bitmapData = stream.toByteArray();
            ImageData imageData = ImageDataFactory.create(bitmapData);
            correctivos2.CreaArchivo2(Ticket,imageData);
            int ID = sh.getInt("ID", 1);
            correctivos2.CreaRF(Datos,imageData,ID-1,ObtenComentariosCorrectivo());
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
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

    private String[] ObtenDatosTicket(){
        Datos = new String[15];
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        for (int i = 0;i<15;i++){
            Datos[i]=sh.getString("DatosTicket"+String.valueOf(i), "");
        }
        return Datos;

    }

    private void RutinaDatos(){
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        Correctivo.setVisibility(View.VISIBLE);
        EquiposIns.setChecked(true);
        FotosVer.setChecked(false);
        FotosCorrectivo.setVisibility(View.GONE);
        myEdit.putString("Pantalla","Equipos");
        myEdit.commit();
    }
    private void RutinaFotos(){
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        MuestraFotosCorrectivo();
        System.out.println("Datos 3 : "+sh.getString("DatosTicket3",""));
        FotosCorrectivo.setVisibility(View.VISIBLE);
        FotosVer.setChecked(true);
        EquiposIns.setChecked(false);
        Correctivo.setVisibility(View.GONE);
        myEdit.putString("Pantalla","Fotos");
        myEdit.commit();
    }

    public void AñadeFotoCorrectivo(View view){
        GuardaDatosInstalacion();
        Intent i = new Intent(this,SelectFoto.class);
        SharedPreferences sh = getSharedPreferences(Datos[3], MODE_PRIVATE);

        SharedPreferences.Editor myEdit = sh.edit();
        int ID = sh.getInt("ID", 1);
        i.putExtra("Datos",Datos);
        i.putExtra("ID",String.valueOf(ID));
        i.putExtra("Where","CorrectivoT-");
        System.out.println(Datos[3]+String.valueOf(ID));
        startActivity(i);
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
                    ConfiguraIncio();

                    // The DriveServiceHelper encapsulates all REST API and SAF functionality.
                    // Its instantiation is required before handling any onClick actions
                });
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