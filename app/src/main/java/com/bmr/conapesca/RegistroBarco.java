package com.bmr.conapesca;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.Arrays;
import java.util.Collections;

public class RegistroBarco extends AppCompatActivity {
    //Spinner PersonaFisica,Materialcasco;
    EditText Permisionario,RLegal,TFijo,Tmovil,Ciudad,Municipio,Domicilio,
            Colonia,CP,RNPAT,EntidadFed,CorreoEP,NombreBarco,Matricula,AñoCons,
            TonNeto,TonBruto,MarcaMotor,PotenciaMotor,SerieMotor,
            FolioCamarón,FolioTunidos,FolioTiburon,FolioEscama,FolioSardina,FolioOtros,Puertobase,Eslora,Encargado;
    RadioButton Comercial,PescaDidactica,Fomento,Camarón,Tunidos,TiburonRaya,Escama,Sardina,Otros;
    ProgressBar RegistrandoBarco;
    String [] Datos,DatosBarco;
    TextView NombreBarcoV;
    Firebase fb = new Firebase();
    Boolean[] Botones;
    private DriveServiceHelper mDriveServiceHelper;
    private SheetsServiceHelper mSheetServiceHelper;
    private static final int REQUEST_CODE_SIGN_IN = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registro_barco);
        NombreBarcoV = (TextView)findViewById(R.id.NombreBarco);
        RegistrandoBarco = (ProgressBar)findViewById(R.id.RegistrandoBarco);
        NombreBarco = (EditText)findViewById(R.id.NombreBarco);
        Matricula = (EditText)findViewById(R.id.Matricula);
        //AñoCons = (EditText)findViewById(R.id.AñoCons);
        TonNeto = (EditText)findViewById(R.id.TonNeto);
        TonBruto = (EditText)findViewById(R.id.TonBruto);
        Puertobase = (EditText)findViewById(R.id.Puertobase);
        Eslora = (EditText)findViewById(R.id.Eslora);
       // Materialcasco = (Spinner) findViewById(R.id.Materialcasco);
        //PersonaFisica = (Spinner) findViewById(R.id.PersonaFisica);
        Permisionario = (EditText)findViewById(R.id.Permisionario);
        RLegal = (EditText)findViewById(R.id.RLegal);
        RLegal = (EditText)findViewById(R.id.RLegal);
        TFijo = (EditText)findViewById(R.id.TFijo);
        Tmovil = (EditText)findViewById(R.id.Tmovil);
        Ciudad = (EditText)findViewById(R.id.Ciudad);
        RLegal = (EditText)findViewById(R.id.RLegal);
        Municipio = (EditText)findViewById(R.id.Municipio);
        Domicilio = (EditText)findViewById(R.id.Domicilio);
        Colonia = (EditText)findViewById(R.id.Colonia);
        CP = (EditText)findViewById(R.id.CP);
        RNPAT = (EditText)findViewById(R.id.RNPAT);
        EntidadFed = (EditText)findViewById(R.id.EntidadFed);
        CorreoEP = (EditText)findViewById(R.id.CorreoEP);
        Encargado = (EditText)findViewById(R.id.Encargado);

        MarcaMotor = (EditText)findViewById(R.id.MarcaMotor);
        PotenciaMotor = (EditText)findViewById(R.id.PotenciaMotor);
        CorreoEP = (EditText)findViewById(R.id.CorreoEP);
        SerieMotor= (EditText)findViewById(R.id.SerieMotor);


        FolioCamarón = (EditText)findViewById(R.id.FolioCamarón);
        FolioTunidos = (EditText)findViewById(R.id.FolioTunidos);
        FolioTiburon = (EditText)findViewById(R.id.FolioTiburon);
        FolioEscama= (EditText)findViewById(R.id.FolioEscama);
        FolioSardina = (EditText)findViewById(R.id.FolioSardina);
        FolioOtros= (EditText)findViewById(R.id.FolioOtros);


        Comercial = (RadioButton) findViewById(R.id.Comercial);
        PescaDidactica = (RadioButton) findViewById(R.id.PescaDidactica);
        Fomento = (RadioButton) findViewById(R.id.Fomento);
        Camarón = (RadioButton) findViewById(R.id.Camarón);
        Tunidos= (RadioButton) findViewById(R.id.Tunidos);
        TiburonRaya = (RadioButton) findViewById(R.id.TiburonRaya);
        Escama = (RadioButton) findViewById(R.id.Escama);
        Sardina = (RadioButton) findViewById(R.id.PelagiosM);
        Otros = (RadioButton) findViewById(R.id.Otros);

        Botones = new Boolean[]{
                   false,
                   false,
                   false,
                   false,
                   false,
                   false,
                   false,
                   false,
                   false,
        };

        String [] opciones1 = {"Persona física","Persona Moral"};
        String [] opciones2 = {"Material","Acero","Madera","Fibra de vidrio"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opciones1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opciones2);
        //PersonaFisica.setAdapter(adapter);
        //Materialcasco.setAdapter(adapter2);

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
        if (DatosBarco!=null)ConfiguraInicio();
        requestSignIn();
    }
    private void ConfiguraInicio(){
        NombreBarcoV.setVisibility(View.VISIBLE);
        NombreBarco.setVisibility(View.GONE);
        NombreBarcoV.setText(DatosBarco[0]);
        NombreBarco.setText(DatosBarco[0]);
        Permisionario.setText(DatosBarco[6]);
        RLegal.setText(DatosBarco[7]);
        RNPAT.setText(DatosBarco[14]);
        Encargado.setText(DatosBarco[38]);
        Puertobase.setText(DatosBarco[37]);
        Matricula.setText(DatosBarco[1]);
        //Litoral.setText(DatosBarco[36]);

       FolioCamarón.setText(DatosBarco[21]);
       FolioTunidos.setText(DatosBarco[22]);
       FolioTiburon.setText(DatosBarco[23]);
       FolioEscama.setText(DatosBarco[24]);
       FolioSardina.setText(DatosBarco[25]);
       FolioOtros.setText(DatosBarco[26]);

       if (DatosBarco[27].equals("true")){Comercial.setChecked(true);Botones[0]=true;}
       else Comercial.setChecked(false);
        if (DatosBarco[28].equals("true")){PescaDidactica.setChecked(true);Botones[1]=true;}
        else PescaDidactica.setChecked(false);
        if (DatosBarco[29].equals("true")){Fomento.setChecked(true);;Botones[2]=true;}
        else Fomento.setChecked(false);
        if (DatosBarco[30].equals("true")){Camarón.setChecked(true);;Botones[3]=true;}
        else Camarón.setChecked(false);
        if (DatosBarco[31].equals("true")){Tunidos.setChecked(true);;Botones[4]=true;}
        else Tunidos.setChecked(false);
        if (DatosBarco[32].equals("true")){TiburonRaya.setChecked(true);;Botones[5]=true;}
        else TiburonRaya.setChecked(false);
        if (DatosBarco[33].equals("true")){Escama.setChecked(true);Botones[6]=true;}
        else Escama.setChecked(false);
        if (DatosBarco[34].equals("true")){Sardina.setChecked(true);Botones[7]=true;}
        else Sardina.setChecked(false);
        if (DatosBarco[35].equals("true")){Otros.setChecked(true);Botones[8]=true;}
        else Otros.setChecked(false);



    }

    private Boolean[] ObtenCheck(){
        Boolean [] Check = new Boolean[]{
                Comercial.isChecked(),///0
                PescaDidactica.isChecked(),///1
                Fomento.isChecked(),///Sec2
                Camarón.isChecked(),///////Sec3
                Tunidos.isChecked(),///4
                TiburonRaya.isChecked(),///5
                Escama.isChecked(),///S6
                Sardina.isChecked(),///7
                Otros.isChecked(),///8

        };

        return Check;

    }

    public void ComercialClick(View view){
        Comercial.setChecked(!Botones[0]);
        Botones[0]=!Botones[0];
    }
    public void PescaClick(View view){
        PescaDidactica.setChecked(!Botones[1]);
        Botones[1]=!Botones[1];
    }
    public void FomentoClick(View view){
        Fomento.setChecked(!Botones[2]);
        Botones[2]=!Botones[2];
    }
    public void CamaronClick(View view){
        Camarón.setChecked(!Botones[3]);
        Botones[3]=!Botones[3];
    }
    public void TunidosClick(View view){
        Tunidos.setChecked(!Botones[4]);
        Botones[4]=!Botones[4];
    }
    public void TiburonClick(View view){
        TiburonRaya.setChecked(!Botones[5]);
        Botones[5]=!Botones[5];
    }
    public void EscamaClick(View view){
        Escama.setChecked(!Botones[6]);
        Botones[6]=!Botones[6];
    }
    public void SardinaClick(View view){
        Sardina.setChecked(!Botones[7]);
        Botones[7]=!Botones[7];
    }
    public void OtrosClick(View view) {
        Otros.setChecked(!Botones[8]);
        Botones[8] = !Botones[8];
    }

    public void RegistraBarco(View view){
        RegistrandoBarco.setVisibility(View.VISIBLE);
        mSheetServiceHelper.RegistraBarco().addOnSuccessListener(ok-> RegistrandoBarco.setVisibility(View.GONE))
                .addOnFailureListener(Fail ->Toast.makeText(this,Fail.toString(),Toast.LENGTH_SHORT).show());
        /*mSheetServiceHelper.RegistraHoraCierre().addOnSuccessListener(ok-> RegistrandoBarco.setVisibility(View.GONE))
                .addOnFailureListener(Fail ->Toast.makeText(this,Fail.toString(),Toast.LENGTH_SHORT).show());*/
    }
    public void ValidaBarco(View view){


        String nombreBarco = NombreBarco.getText().toString();
        String matriculabarco = Matricula.getText().toString();
        String AñoContr = AñoCons.getText().toString();
        String tonBruto = TonBruto.getText().toString();
        String tonneto = TonNeto.getText().toString();
        String puertobase = Puertobase.getText().toString();
        //String litoral = Litoral.getText().toString();

        String permisionario = Permisionario.getText().toString();
        String Rlegarl = RLegal.getText().toString();
        String Tfijo = TFijo.getText().toString();
        String tmovil = Tmovil.getText().toString();
        String ciudad = Ciudad.getText().toString();
        String municipio = Municipio.getText().toString();
        String colonia = Colonia.getText().toString();
        String cP = CP.getText().toString();
        String RnPAT = RNPAT.getText().toString();
        String Entidadfed= EntidadFed.getText().toString();
        String CorreoE = CorreoEP.getText().toString();
        String responsablebarco = Encargado.getText().toString();

        String Marcamotor = MarcaMotor.getText().toString();
        String Potenciam = PotenciaMotor.getText().toString();
        String SeriMotor = SerieMotor.getText().toString();

        String Foliocamaron = FolioCamarón.getText().toString();
        String Foliotnidos = FolioTunidos.getText().toString();
        String FolioTiburom = FolioTiburon.getText().toString();
        String FolioEscamas = FolioEscama.getText().toString();
        String Foliosardina = FolioSardina.getText().toString();
        String Foliootros = FolioOtros.getText().toString();

        //String Materialcaso = Materialcasco.getSelectedItem().toString();
        //String personfisica = PersonaFisica.getSelectedItem().toString();


        if (TextUtils.isEmpty(nombreBarco)) nombreBarco = "Dato no registrado";
        if (TextUtils.isEmpty(matriculabarco)) matriculabarco = "Dato no registrado";
        if (TextUtils.isEmpty(AñoContr))AñoContr= "Dato no registrado";
        if (TextUtils.isEmpty(tonBruto)) tonBruto = "Dato no registrado";
        if (TextUtils.isEmpty(tonneto)) tonneto = "Dato no registrado";


        if (TextUtils.isEmpty(permisionario)) permisionario = "Dato no registrado";
        if (TextUtils.isEmpty(Rlegarl)) Rlegarl = "Dato no registrado";
        if (TextUtils.isEmpty(Tfijo)) Tfijo = "Dato no registrado";
        if (TextUtils.isEmpty(tmovil))tmovil = "Dato no registrado";
        if (TextUtils.isEmpty(ciudad)) ciudad = "Dato no registrado";
        if (TextUtils.isEmpty(municipio)) municipio= "Dato no registrado";
        if (TextUtils.isEmpty(colonia)) colonia= "Dato no registrado";
        if (TextUtils.isEmpty(cP))cP= "Dato no registrado";
        if (TextUtils.isEmpty(RnPAT))RnPAT= "Dato no registrado";
        if (TextUtils.isEmpty(Entidadfed))Entidadfed= "Dato no registrado";
        if (TextUtils.isEmpty(CorreoE))CorreoE= "Dato no registrado";

        if (TextUtils.isEmpty(Marcamotor))Marcamotor= "Dato no registrado";
        if (TextUtils.isEmpty(Potenciam))Potenciam= "Dato no registrado";
        if (TextUtils.isEmpty(SeriMotor))SeriMotor= "Dato no registrado";

        if (TextUtils.isEmpty(Foliocamaron))Foliocamaron= "Dato no registrado";
        if (TextUtils.isEmpty(Foliotnidos))Foliotnidos= "Dato no registrado";
        if (TextUtils.isEmpty(FolioTiburom))FolioTiburom= "Dato no registrado";
        if (TextUtils.isEmpty(FolioEscamas))FolioEscamas= "Dato no registrado";
        if (TextUtils.isEmpty(Foliosardina))Foliosardina= "Dato no registrado";
        if (TextUtils.isEmpty(Foliootros))Foliootros= "Dato no registrado";

        String comercial,pescadidactica,fomento,camaron,tunidos,tiburon,escama,sardina,otros;

        if (Comercial.isChecked()) comercial="true"; else comercial = "false";
        if (PescaDidactica.isChecked())pescadidactica="true";else pescadidactica="false";
        if (Fomento.isChecked())fomento="true";else fomento = "false";
        if (Camarón.isChecked())camaron="true";else camaron = "false";
        if (Tunidos.isChecked())tunidos = "true";else tunidos = "false";
        if (TiburonRaya.isChecked())tiburon = "true";else tiburon = "false";
        if (Escama.isChecked())escama ="true";else escama = "false";
        if (Sardina.isChecked())sardina = "true";else sardina = "false";
        if (Otros.isChecked())otros = "true";else otros ="false";



        String[]DatosBarco = new String[]{
                    nombreBarco,//0
                    matriculabarco,//1
                    AñoContr,//2
                    tonBruto,//3
                    tonneto,//4
                    "Materialcaso",//5
                    permisionario ,//6
                    Rlegarl,//7
                    Tfijo,//8
                    tmovil,//9
                ciudad,//10
                    municipio,//11
                    colonia,//12
                    cP,//13
                    RnPAT,//14
                    Entidadfed,//15
                    CorreoE,//16
                    "personfisica",//17
                    Marcamotor,//18
                    Potenciam,//19
                    SeriMotor,//20
                    Foliocamaron,//21
                    Foliotnidos, //22
                    FolioTiburom, //23
                    FolioEscamas, //24
                    Foliosardina, //25
                    Foliootros,//26
                    comercial,//27
                    pescadidactica,//28
                    fomento,//29
                    camaron,//30
                    tunidos,//31
                    tiburon,//32
                    escama,//33
                    sardina,//34
                    otros,//35
                    "litoral",//36
                    puertobase,//37
                    responsablebarco//38


            };
            fb.RegistraBarco(DatosBarco,this);


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


}