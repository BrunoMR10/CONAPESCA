package com.bmr.conapesca;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bmr.conapesca.Datos.Datos;
import com.bmr.conapesca.Herramientas.Firebase;
import com.bmr.conapesca.ServiceHelper.DriveServiceHelper;
import com.bmr.conapesca.ServiceHelper.SheetsServiceHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

public class AsignaTicket extends AppCompatActivity {
    public FirebaseStorage storage = FirebaseStorage.getInstance();
    public StorageReference storageReference = storage.getReferenceFromUrl("gs://conapesca-fc179.appspot.com");
    ImageView FotoIngeniero;
    TextView NombreIngeniero,PuestoIngeniero,NombreBarcoView,FechaOficiov,TipoServicio,RNPView,PuertoBase,PermisionarioV,Ticket,UID;
    Spinner Tipodeservicio;
    ProgressBar CargandoTicket;
    EditText NOficio;
    private DriveServiceHelper mDriveServiceHelper;
    private SheetsServiceHelper mSheetServiceHelper;
    Datos dt = new Datos();
    String [] Datos;
    String [] DatosTicket,DatosTickets;
    Firebase fb = new Firebase();
    String NombreBarco,RNPBarco,puertoBase,Permisionario,Uid;
    LinearLayout DatosTicketAsigna;
    private static final int REQUEST_CODE_SIGN_IN = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asigna_ticket);
        CargandoTicket = (ProgressBar) findViewById(R.id.CargandoTicket);
        NombreIngeniero = (TextView) findViewById(R.id.NombreIngeniero);
        PuestoIngeniero = (TextView) findViewById(R.id.PuestoIngeniero);
        NombreBarcoView = (TextView) findViewById(R.id.NombreBarcoView);
        UID = (TextView) findViewById(R.id.UID);
        Ticket = (TextView) findViewById(R.id.Ticket);
        TipoServicio = (TextView) findViewById(R.id.TipoServicio);
        RNPView = (TextView) findViewById(R.id.RNPView);
        NOficio = (EditText) findViewById(R.id.NOficio);
        FechaOficiov = (TextView) findViewById(R.id.FechaOficiov);
        FotoIngeniero = (ImageView) findViewById(R.id.FotoIngeniero );
        Tipodeservicio = (Spinner) findViewById(R.id.Tipodeservicio);
        PuertoBase  = (TextView) findViewById(R.id.PuertoBase);
        PermisionarioV = (TextView) findViewById(R.id.PermisionarioV);
        DatosTicketAsigna = (LinearLayout) findViewById(R.id.DatosTicketAsigna);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                Datos = extras.getStringArray("Datos");
                DatosTicket = extras.getStringArray("DatosTicket");
                DatosTickets = extras.getStringArray("DatosTickets");
                NombreBarco = extras.getString("NombreBarco");
            }
        }
        else {
            Datos = (String[]) savedInstanceState.getSerializable("Datos");
            DatosTicket = (String[]) savedInstanceState.getSerializable("DatosTicket");
            DatosTickets = (String[]) savedInstanceState.getSerializable("DatosTickets");
            NombreBarco =(String)savedInstanceState.getSerializable("NombreBarco");
        }
        if (Datos==null)System.out.println("TicketAsignado");
        else if (Datos[8].equals("Servicios")){reiniciash();Datos = new String[18];}
        else MostrarIngenieroBarco();
        if (DatosTickets != null){Datos = DatosTickets;}
        ConfiguraInicio();
        requestSignIn();
    }
    private void ConfiguraInicio(){
        SharedPreferences sh = getSharedPreferences("AsignaTicket", MODE_PRIVATE);
        if (DatosTickets != null){
            SharedPreferences.Editor myEdit = sh.edit();
            myEdit.putString("UID",DatosTickets[17]);
            myEdit.commit();
            Ticket.setText(DatosTickets[0]);
            TipoServicio.setText(DatosTickets[4]);
            NOficio.setText(DatosTickets[12]);
            FechaOficiov.setText(DatosTickets[13]);
            NombreBarcoView.setText(DatosTickets[15]);
            NombreIngeniero.setText(DatosTickets[1]);
            PuestoIngeniero.setText(DatosTickets[2]);
            RNPView.setText(DatosTickets[11]);
            PuertoBase.setText(DatosTickets[14]);
            PermisionarioV.setText(DatosTickets[16]);
            GuardaDatosTickets();
        }else {
            String[] opciones = {"", "Correctivo", "Preventivo", "Interno", "Instalacion"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones);
            Tipodeservicio.setAdapter(adapter);
            TipoServicio.setText(sh.getString("TipoServicio", ""));
            NOficio.setText(sh.getString("NoOficio", ""));
            FechaOficiov.setText(sh.getString("FechaOficio", ""));
            NombreBarcoView.setText(sh.getString("NombreBarco", ""));
            RNPView.setText(sh.getString("RNPBarco", ""));
            NombreIngeniero.setText(sh.getString("NombreIngeniero", ""));
            PuestoIngeniero.setText(sh.getString("PuestoIngeniero", ""));
            PuertoBase.setText(sh.getString("PuertoBase", ""));
            PermisionarioV.setText(sh.getString("Permisionario", ""));
        }
    }
    private void GuardaDatosTickets(){
        SharedPreferences sh = getSharedPreferences("AsignaTicket", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        for (int i = 0; i<dt.DatosTicketAReporte.length-1;i++ ){
            myEdit.putString("DatosTickets"+i,DatosTickets[i]);
            myEdit.commit();
        }
    }
    private void reiniciash(){
        SharedPreferences sh = getSharedPreferences("AsignaTicket", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.putString("TipoServicio","");
        myEdit.putString("NoOficio","");
        myEdit.putString("FechaOficio","");
        myEdit.putString("NombreBarco","");
        myEdit.putString("NombreIngeniero","");
        myEdit.putString("PuestoIngeniero","");
        myEdit.putString("RNPBarco","");
        myEdit.putString("PuertoBase","");
        myEdit.putString("Permisionario","");
        myEdit.commit();
        for (int i = 0; i<dt.DatosTicketAReporte.length;i++ ){
            myEdit.putString("DatosTickets"+i,"");
            myEdit.commit();
        }
    }
    private String[] ObtenDatosTickets(){
        DatosTickets = new String[dt.DatosTicketAReporte.length];
        SharedPreferences sh = getSharedPreferences("AsignaTicket", MODE_PRIVATE);
        for (int i = 0;i<dt.DatosTicketAReporte.length;i++){
            DatosTickets[i]=sh.getString("DatosTickets"+String.valueOf(i), "");
        }
        return DatosTickets;
    }
    private String[] ObtenSharedPreference(){
        Datos = new String[dt.Datos.length];
        SharedPreferences sh = getSharedPreferences("Datos", MODE_PRIVATE);
        for (int i = 0;i<dt.Datos.length;i++){
            Datos[i]=sh.getString("Datos"+String.valueOf(i), "");
        }
        return Datos;
    }
    public void SeleccionaIngeniero(View view){
        Intent intent = new Intent(this,ListaUsuarios.class);
        if (Datos == null) Datos = ObtenDatosTickets();
        Datos [8] ="AsignaTicket";
        SharedPreferences sh = getSharedPreferences("AsignaTicket", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        if (ObtenDatosTickets()[0].contains("-")){
            myEdit.putString("TipoServicio",TipoServicio.getText().toString());
        }
        else {
            if (Tipodeservicio.getSelectedItem().toString().equals(""))
                System.out.println("No se ha seleccionado");
            else myEdit.putString("TipoServicio", Tipodeservicio.getSelectedItem().toString());
        }
        myEdit.putString("NoOficio",NOficio.getText().toString());
        myEdit.putString("FechaOficio",FechaOficiov.getText().toString());
        myEdit.putString("NombreBarco",NombreBarcoView.getText().toString());
        myEdit.putString("RNPBarco",RNPView.getText().toString());
        myEdit.putString("Permisionario",PermisionarioV.getText().toString());
        myEdit.putString("NombreIngeniero",NombreIngeniero.getText().toString());
        myEdit.putString("PuestoIngeniero",PuestoIngeniero.getText().toString());
        myEdit.putString("PuertoBase",PuertoBase.getText().toString());
        myEdit.commit();
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    public void SeleccionaBarco(View view){
        Intent intent = new Intent(this,ListaBarcos.class);
        if (Datos == null) Datos = ObtenDatosTickets();
        Datos[8] ="AsignaTicket";
        SharedPreferences sh = getSharedPreferences("AsignaTicket", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        if (ObtenDatosTickets()[0].contains("-")){
            myEdit.putString("TipoServicio",TipoServicio.getText().toString());
        }
        else {
            if (Tipodeservicio.getSelectedItem().toString().equals(""))
                System.out.println("No se ha seleccionado");
            else myEdit.putString("TipoServicio", Tipodeservicio.getSelectedItem().toString());
        }
        myEdit.putString("NoOficio",NOficio.getText().toString());
        myEdit.putString("FechaOficio",FechaOficiov.getText().toString());
        myEdit.putString("NombreBarco",NombreBarcoView.getText().toString());
        myEdit.putString("RNPBarco",RNPView.getText().toString());
        myEdit.putString("Permisionario",PermisionarioV.getText().toString());
        myEdit.putString("NombreIngeniero",NombreIngeniero.getText().toString());
        myEdit.putString("PuestoIngeniero",PuestoIngeniero.getText().toString());
        myEdit.putString("PuertoBase",PuertoBase.getText().toString());
        myEdit.commit();
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    private void MostrarIngenieroBarco(){
        if (Datos!= null){
        SharedPreferences sh = getSharedPreferences("AsignaTicket", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        if (Datos[0].contains("-")) System.out.println("No es bueno");
        else myEdit.putString("UID",Datos[0]);
        myEdit.putString("NombreIngeniero",Datos[1]);
        myEdit.putString("PuestoIngeniero",Datos[2]);
        myEdit.commit();
      NombreIngeniero.setText(Datos[1]);
      PuestoIngeniero.setText(Datos[2]);
      if (NombreBarco == null)NombreBarcoView.setText("");
      else {NombreBarcoView.setText(NombreBarco);
          myEdit.putString("RNPBarco",Datos[11]);
          myEdit.putString("NombreBarco",Datos[15]);
          myEdit.putString("PuertoBase",Datos[14]);
          myEdit.putString("Permisionario",Datos[16]);
          myEdit.commit();
      }}
        else {System.out.println("null");}
      //CargaImagen();
    }
    private void CargaImagen(){
        File localFile;
        try {
            StorageReference Credref = Credref = storageReference.child("Credenciales").child("Seguritech").child(Datos[1]+".jpg");
            localFile = File.createTempFile(Datos[1], "jpg");
            Credref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    FotoIngeniero.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //Toast.makeText(Usuarios.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (
                IOException ioException) {
            //ioException.printStackTrace();
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void AsignaTicket(View view){
        SharedPreferences sh = getSharedPreferences("AsignaTicket", MODE_PRIVATE);
        //String TS=Tipodeservicio.getSelectedItem().toString();
        if (NombreIngeniero.getText().toString().equals("")){
            Toast.makeText(this,"Porfavor selecciona un ingeniero",Toast.LENGTH_SHORT).show();
        }else if (TipoServicio.getText().toString().equals("")){
            Toast.makeText(this,"Porfavor selecciona un tipo de servicio",Toast.LENGTH_SHORT).show();
        }
        else if (NombreBarcoView.getText().toString().equals("")){
            Toast.makeText(this,"Porfavor selecciona un barco",Toast.LENGTH_SHORT).show();
        }
        else if (FechaOficiov.getText().toString().equals("")){
            Toast.makeText(this,"Porfavor selecciona una fecha",Toast.LENGTH_SHORT).show();
        }
        else if (NOficio.getText().toString().equals("")){
            Toast.makeText(this,"Porfavor agrega una fecha",Toast.LENGTH_SHORT).show();
        }else{
            CargandoTicket.setVisibility(View.VISIBLE);
            DatosTicketAsigna.setVisibility(View.GONE);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm");

            if (ObtenDatosTickets()[0].contains("-")){
                String[] DatosTicket = new String[]{
                        ObtenDatosTickets()[0],
                        NombreIngeniero.getText().toString(),
                        PuestoIngeniero.getText().toString(),
                        ObtenDatosTickets()[3],
                        TipoServicio.getText().toString(),
                        String.valueOf(dtf.format(LocalDateTime.now())),
                        String.valueOf(dtf2.format(LocalDateTime.now())),
                        ObtenDatosTickets()[7],
                        ObtenDatosTickets()[8],
                        ObtenDatosTickets()[9],
                        ObtenDatosTickets()[10],
                        RNPView.getText().toString(),
                        NOficio.getText().toString(),
                        FechaOficiov.getText().toString(),
                        PuertoBase.getText().toString(),
                        NombreBarcoView.getText().toString(),
                        PermisionarioV.getText().toString(),
                        sh.getString("UID", ""),
                        ObtenDatosTickets()[17],
                };
                for (int i = 1; i<DatosTicket.length;i++ ){
                    if (DatosTicket[i-1].equals(ObtenDatosTickets()[i-1])){
                        System.out.println("Sin cambio detectado");
                        if (i==1) System.out.println("Ticket "+DatosTicket[0]);
                    }
                    else{
                        if (i == 2||i==3 || i==18){ System.out.println("Reasignacion de ticket de"+ObtenDatosTickets()[i-1] +" /"+DatosTicket[i-1]); DatosTicket[7]="Re-asignacion";}
                        else if (i==6||i==7) System.out.println("");
                        else {System.out.println("Cambio de información de Ticket "+ObtenDatosTickets()[i-1] +" /"+DatosTicket[i-1]);DatosTicket[7]="Actualizacion";}
                    }
                }
                if ( DatosTicket[7].equals("Re-asignacion")){ Toast.makeText(this, "Se re-asignara ticket, es posible que si cambio algun otro parametro no se vea reflejado.",Toast.LENGTH_LONG).show();

                    mSheetServiceHelper.ActualizaBitacora(DatosTicket[10],"Re-Asignacion de ticket",DatosTicket[6],DatosTicket[5],"-",0)
                            .addOnSuccessListener(ok -> mSheetServiceHelper.Escribeentablareasignacion(DatosTicket)
                                    .addOnSuccessListener(Ok -> fb.Reasignaticket(DatosTicket,this))
                                    .addOnFailureListener(Error -> System.out.println(Error)))
                            .addOnFailureListener(Fail -> System.out.println(Fail));

                }
                if ( DatosTicket[7].equals("Actualizacion")){ Toast.makeText(this, "Actualizando Ticket",Toast.LENGTH_LONG).show();

                    mSheetServiceHelper.ActualizaBitacora(DatosTicket[10],"Actualizacion",DatosTicket[6],DatosTicket[5],"-",0)
                            .addOnSuccessListener(ok -> mSheetServiceHelper.Escribeentablareasignacion(DatosTicket)
                                    .addOnSuccessListener(Ok -> fb.ActualizaTicket(DatosTicket,this))
                                    .addOnFailureListener(Error -> System.out.println(Error)))
                            .addOnFailureListener(Fail -> System.out.println(Fail));

                }
            }else {

                String[] DatosTicket = new String[]{
                        Datos[0],
                        NombreIngeniero.getText().toString(),
                        PuestoIngeniero.getText().toString(),
                        "",
                        TipoServicio.getText().toString(),
                        String.valueOf(dtf.format(LocalDateTime.now())),
                        String.valueOf(dtf2.format(LocalDateTime.now())),
                        "Asignado",
                        "Link",
                        "Link2",
                        "Link3",
                        RNPView.getText().toString(),
                        NOficio.getText().toString(),
                        FechaOficiov.getText().toString(),
                        PuertoBase.getText().toString(),
                        NombreBarcoView.getText().toString(),
                        PermisionarioV.getText().toString(),
                        "UID.getText().toString()",
                        "Token"
                };
                reiniciash();



                fb.AsignaTicket(DatosTicket, this);
            }

        }

    }


    private boolean ValidaFotos(){
        Drawable drawable = FotoIngeniero.getDrawable();

        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)){
            return true;
        }
        else{
            return false;
        }

    }
    public void onBackPressed() {
        reiniciash();
        Datos = ObtenSharedPreference();
        Intent i = new Intent(this,Servicios.class);
        i.putExtra("Datos",Datos);
        startActivity(i);
    }
    private void EscribeTabla(String id,String id2,String id3){
        DatosTicket[8]= id;
        DatosTicket[9]= id2;
        DatosTicket[10]= id3;
        mSheetServiceHelper.ActualizaBitacora(DatosTicket[10],"Asignacion de ticket",DatosTicket[6],DatosTicket[5],"-",0)
                .addOnSuccessListener(ok ->          mSheetServiceHelper.Escribeentabla(DatosTicket)
                        .addOnSuccessListener(Ok -> fb.RegistraTicket(DatosTicket,this,DatosTicket[17]))
                        .addOnFailureListener(Error -> System.out.println(Error)))
                .addOnFailureListener(Fail -> System.out.println(Fail));

    }
    ///////////////////////////SERVICIOS GOOGLE//////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:
                if (resultCode == Activity.RESULT_OK && resultData != null) {
                    handleSignInResult(resultData);
                }
                break;

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
                    if (DatosTicket!=null){
                         CargandoTicket.setVisibility(View.VISIBLE);
                         DatosTicketAsigna.setVisibility(View.GONE);
                         mDriveServiceHelper.createFolder(DatosTicket[3],DatosTicket[4])
                                 .addOnSuccessListener(id ->mDriveServiceHelper.createFolderFotos(id[0],DatosTicket[3])
                                         .addOnSuccessListener(id2 -> mSheetServiceHelper.CreaBitacora(DatosTicket[3])
                                                 .addOnSuccessListener(id3 -> mDriveServiceHelper.createBitacora(id[0],id3)
                                                         .addOnSuccessListener(id4->EscribeTabla(id[0],id2,id3))
                                                         .addOnFailureListener(Error ->System.out.println(Error)))
                                                 .addOnFailureListener(Error ->System.out.println(Error)))
                                         .addOnFailureListener(Error -> System.out.println(Error)))
                                 .addOnFailureListener(Error -> System.out.println(Error));
                    }
                    else{
                        if(NombreIngeniero.getText().toString().equals(""))FotoIngeniero.setVisibility(View.GONE);
                        else FotoIngeniero.setVisibility(View.GONE);
                        CargandoTicket.setVisibility(View.GONE);
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
    public void AbreCalendario(View view) {
        Calendar cal = Calendar.getInstance();
        int anio = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(AsignaTicket.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fecha;
                if ((month+1<10)&&(dayOfMonth<10))fecha = "0"+dayOfMonth+"-0"+(month+1)+"-"+year;
                else if ((month+1<10))fecha = dayOfMonth+"-0"+(month+1)+"-"+year;
                else if ((dayOfMonth<10))fecha = "0"+dayOfMonth+"-"+(month+1)+"-"+year;
                else fecha = dayOfMonth+"-"+(month+1)+"-"+year;

                SharedPreferences sh = getSharedPreferences("AsignaTicket", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                myEdit.putString("FechaOficio","");
                myEdit.commit();

                FechaOficiov.setText(fecha);

            }
        },2023,mes,dia);
        dpd.show();
    }

}