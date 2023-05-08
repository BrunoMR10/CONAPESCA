package com.bmr.conapesca;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
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
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;

public class PDFViewer extends AppCompatActivity {
    Firebase fb = new Firebase();
    private DriveServiceHelper mDriveServiceHelper;
    private SheetsServiceHelper mSheetServiceHelper;
    private static final int REQUEST_CODE_SIGN_IN = 1;
    private final String storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
    Datos dt = new Datos();
    String []Datos;
    String name,rutacarpeta,outputPDF,Re;
    TextView Estatus,pdf,todo;
    ProgressBar Subiendo;
    String Fotos;
    Boolean Todo;
    ImageButton PDF,TODO;
    String Iden;
    String Ticket;
    RadioButton RF,RD,Convenio;
    String Where;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);
        RF = (RadioButton) findViewById(R.id.RF);
        RD = (RadioButton) findViewById(R.id.RD);
        Convenio = (RadioButton)findViewById(R.id.Convenio);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                Datos = extras.getStringArray("Datos");
                Fotos = extras.getString("Fotos");
                Iden = extras.getString("Iden");
            }
        }
        else {
            Datos = (String[]) savedInstanceState.getSerializable("Datos");
            Fotos = (String) savedInstanceState.getSerializable("Fotos");
        }
        Ticket = Datos[3];
        //rutacarpeta = "CONAPESCA/"+Datos[0]+"/"+Datos[1]+"/" ;
        name =Datos[3]+"("+Iden+").pdf";
        //outputPDF = storageDir + rutacarpeta + name;
        Estatus = (TextView)findViewById(R.id.EstatusView) ;
        pdf = (TextView)findViewById(R.id.TextPDF) ;
        todo = (TextView)findViewById(R.id.TextTodo) ;
        PDF = (ImageButton)findViewById(R.id.SubePDF) ;
        TODO = (ImageButton)findViewById(R.id.SubeTodo) ;
        Subiendo = (ProgressBar)findViewById(R.id.Subiendo) ;
        if (Iden.equals("NI")){
            RF.setVisibility(View.GONE);
            RD.setVisibility(View.GONE);
            Convenio.setVisibility(View.GONE);
            viewPDFFile();
        }
        else{
            TODO.setVisibility(View.VISIBLE);
            todo.setVisibility(View.VISIBLE);
            RutinaRD();
        }
        //viewPDFFile();

        Estatus.setVisibility(View.GONE);
        Subiendo.setVisibility(View.GONE);
        requestSignIn();
    }

    private void RutinaRD(){
        //rutacarpeta = "CONAPESCA/"+Datos[0]+"/"+Datos[1]+"/" ;
        RD.setChecked(true);
        RF.setChecked(false);
        Convenio.setChecked(false);
        name =Ticket+"("+"RD"+").pdf";
        Iden = "RD";
        Toast.makeText(this, name,Toast.LENGTH_SHORT).show();
        viewPDFFile();
    }
    private void RutinaRF(){
        Convenio.setChecked(false);
        RD.setChecked(false);
        RF.setChecked(true);
        name =Ticket+"("+"RF"+").pdf";
        Iden = "RF";
        Toast.makeText(this, name,Toast.LENGTH_SHORT).show();
        viewPDFFile();
    }
    private void RutinaConvenio(){
        Convenio.setChecked(true);
        RD.setChecked(false);
        RF.setChecked(false);
        name =Ticket+"("+"Convenio"+").pdf";
        Iden = "Convenio";
        Toast.makeText(this, name,Toast.LENGTH_SHORT).show();
        viewPDFFile();
    }
    public void CLICKRD(View view){
        RutinaRD();
    }
    public void CLICKRF(View view){
        RutinaRF();
    }
    public void CLICKCONVENIO(View view){
        RutinaConvenio();
    }

    public void CompartirPDF(View view){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(Ticket, Context.MODE_PRIVATE);
        //name =Ticket+"("+Iden+").pdf";
        File file = new File(directory, name);

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        Uri outputPdfUri = FileProvider.getUriForFile(this, PDFViewer.this.getPackageName() + ".fileprovider", file);
        //Uri screenshotUri = Uri.parse(file.getAbsolutePath());
        sharingIntent.setType("application/pdf");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, outputPdfUri);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void SubirPDF(View view){
        Subiendo.setVisibility(View.VISIBLE);
        Estatus.setVisibility(View.VISIBLE);
        name =Ticket+"("+Iden+")";

        mDriveServiceHelper.createFile(Datos[8],name,Datos[3]).addOnSuccessListener(ok -> RegistraenTabla())
                .addOnFailureListener(Fail->Toast.makeText(this,Fail.toString(),Toast.LENGTH_SHORT).show());
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void RegistraenTabla(){
        if (Ticket.contains("S-")) Where = "Instalación";
        else if (Ticket.contains("I-")) Where = "Interno";
        else if (Ticket.contains("P-")) Where = "Preventivo";
        else Where = "Correctivo";
        Toast.makeText(this,"Documento arriba",Toast.LENGTH_SHORT).show();
        SharedPreferences sh = getSharedPreferences(Datos[3], MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm");
        myEdit.putString("FechaDocument", String.valueOf(dtf.format(LocalDateTime.now())));
        myEdit.putString("HoraDocument", String.valueOf(dtf2.format(LocalDateTime.now())));
        myEdit.commit();
        String[] HoraFecha = new String[]{
                String.valueOf(dtf.format(LocalDateTime.now())),
                String.valueOf(dtf2.format(LocalDateTime.now())),
                Where,
                ObtenDatosTicket()[7]
        };

        System.out.println("Estatus"+ObtenDatosTicket()[7]);
        mSheetServiceHelper.Escribeentabla6(HoraFecha,Ticket)
                .addOnSuccessListener(ok -> Sesubuio())
                .addOnFailureListener(Fail -> System.out.println(Fail));
    }
    private void Sesubuio(){
        Toast.makeText(this,"Se ha subido el documento con éxito",Toast.LENGTH_SHORT).show();
        Subiendo.setVisibility(View.GONE);
        Estatus.setVisibility(View.GONE);
    }
    private String[] ObtenDatosTicket(){
        System.out.println("ObteniendoDatos");
        System.out.println(Ticket);
        String [] DatosTicket = new String[15];
        SharedPreferences sh = getSharedPreferences(Ticket, MODE_PRIVATE);
        for (int i = 0;i<15;i++){
            DatosTicket[i]=sh.getString("DatosTicket"+String.valueOf(i), "");
            System.out.println("Dato Ticket"+sh.getString("DatosTicket"+String.valueOf(i),""));
        }
        return DatosTicket;

    }

    private void viewPDFFile() {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(Datos[3], Context.MODE_PRIVATE);
        //name =Datos[3]+"("+Iden+").pdf";
        File file = new File(directory, name);
        com.github.barteksc.pdfviewer.PDFView pdfView = (com.github.barteksc.pdfviewer.PDFView) findViewById(R.id.pdfView);
        pdfView.fromFile(file).load();
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
                    // The DriveServiceHelper encapsulates all REST API and SAF functionality.
                    // Its instantiation is required before handling any onClick actions
                   // mSheetServiceHelper.buscaBarco().addOnSuccessListener(ok -> System.out.println(String.valueOf(ok))).addOnFailureListener(fail -> System.out.println(fail.toString()));

                })
                .addOnFailureListener(exception -> Toast.makeText(this,exception.toString(),Toast.LENGTH_SHORT).show());
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
    @Override
    public void onBackPressed() {
        /*if (Ticket.contains("S-")){
            Intent i = new Intent(this,ReporteInstalacion.class);
            i.putExtra("Datos",Datos);
            startActivity(i);
        }*/

        Intent i = new Intent(this,ReporteInstalacion.class);
        i.putExtra("Datos",Datos);
        startActivity(i);

    }
}