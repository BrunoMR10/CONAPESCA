package com.bmr.conapesca.ServiceHelper;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bmr.conapesca.ListaBarcos;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SheetsServiceHelper {
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference refUsuarios = database.getReference("Usuarios");
    public DatabaseReference refTickets = database.getReference("Tickets");
    public DatabaseReference refBarcos = database.getReference("Barcos");
    private final Sheets mSheetsService;
    private final Executor mExecutor = Executors.newSingleThreadExecutor();
    public SheetsServiceHelper(Sheets Service) {
        mSheetsService = Service;
    }
    public Task<Boolean> Escribeentablareasignacion(String[]Datos) {
        return Tasks.call(mExecutor, () -> {
            String spreadsheetId ;
            if (Datos[4].equals("Preventivo")){
                spreadsheetId= "197jGRK3XtNai23J_OWKOPmXa98Ov5ib5aKoxT8flQds";
            }else if (Datos[4].equals("Correctivo")){
                spreadsheetId = "1LEkgfatx1xaIM6R_uBDOCFuh6JiJAY0GuGkh8HDzdhM";
            }
            else if (Datos[4].contains("Instal")){
                //spreadsheetId = "1gdmQZXedP4l0Cm_jahXt7LCOJkOlC7GXagc7LPp0CCs";Version1
                spreadsheetId = "1b1G86lx9GagfW72X58GUJm3U9y7sAbGC8o1lmx2oDhY";

            }else{
                spreadsheetId= "1kmGFG6T5tJNne2C2lQD9G7LL9Qh24vbxNL5d4hLMRFI";
            }
            String range = "E2:E3000";
            int Numero = 0;
            int Count = 2;
            ValueRange response = mSheetsService.spreadsheets().values().get(spreadsheetId,range).execute();
            List<List<Object>> values = response.getValues();
            if (values!=null){
                System.out.println( values);
                for (List row : values){
                    if (row.get(0).toString().equals(Datos[3])){
                        Numero = Count;
                        System.out.println("Ticket detectado en "+Numero);
                    }
                    Count++;
                }

            }
            if (Numero == 0) Numero = 3;
            Object a = new Object();
            a = Numero-2;
            Object b = new Object();
            b = Datos[1];
            Object c = new Object();
            c= "-";
            Object d = new Object();
            d= Datos[7];
            Object e = new Object();
            e= Datos[3];
            Object f = new Object();
            f= Datos[11];
            Object g = new Object();
            g= Datos[15];
            Object h = new Object();
            h=  Datos[16];
            Object i = new Object();
            i=  Datos[14];
            Object j = new Object();
            j= Datos[12];
            Object k = new Object();
            k= Datos[13];
            Object l = new Object();
            l= Datos[5];
            Object m = new Object();
            m= Datos[6];
            ValueRange valueRange = new ValueRange();
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(a,b,c,d,e,f,g,h,i,j,k,l,m)));
            range = "A"+((Numero))+":M"+((Numero));


            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range, valueRange)
                    .setValueInputOption("RAW")
                    .execute();

            c = "=HIPERVINCULO(\"https://drive.google.com/drive/folders/"+Datos[8]+"\")";
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(c)));
            range = "C"+((Numero))+":C"+((Numero));
            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range, valueRange)
                    .setValueInputOption("USER_ENTERED")
                    .execute();
            return true;
        });

    }
    public Task<Boolean> Escribeentabla(String[]Datos) {
        return Tasks.call(mExecutor, () -> {
            String spreadsheetId ;
            if (Datos[4].equals("Preventivo")){
                spreadsheetId= "197jGRK3XtNai23J_OWKOPmXa98Ov5ib5aKoxT8flQds";
            }else if (Datos[4].equals("Correctivo")){
                spreadsheetId = "1LEkgfatx1xaIM6R_uBDOCFuh6JiJAY0GuGkh8HDzdhM";
            }
            else if (Datos[4].contains("Instal")){
                //spreadsheetId = "1gdmQZXedP4l0Cm_jahXt7LCOJkOlC7GXagc7LPp0CCs";Version1
                spreadsheetId = "1b1G86lx9GagfW72X58GUJm3U9y7sAbGC8o1lmx2oDhY";
            }else{
                spreadsheetId= "1kmGFG6T5tJNne2C2lQD9G7LL9Qh24vbxNL5d4hLMRFI";
            }
            String range = "B1:B1";
            int Numero = 0;
            ValueRange response = mSheetsService.spreadsheets().values().get(spreadsheetId,range).execute();
            List<List<Object>> values = response.getValues();
            if (values!=null){

                for (List row : values){
                    row.get(0).toString();
                    Numero = Integer.parseInt(row.get(0).toString());
                }

            }
            if (Numero == 0) Numero = 3;
            else Numero = Numero+1;
            Object a = new Object();
            a = Numero-2;
            Object b = new Object();
            b = Datos[1];
            Object c = new Object();
            c= "-";
            Object d = new Object();
            d= Datos[7];
            Object e = new Object();
            e= Datos[3];
            Object f = new Object();
            f= Datos[11];
            Object g = new Object();
            g= Datos[15];
            Object h = new Object();
            h=  Datos[16];
            Object i = new Object();
            i=  Datos[14];
            Object j = new Object();
            j= Datos[12];
            Object k = new Object();
            k= Datos[13];
            Object l = new Object();
            l= Datos[5];
            Object m = new Object();
            m= Datos[6];
            ValueRange valueRange = new ValueRange();
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(a,b,c,d,e,f,g,h,i,j,k,l,m)));
            range = "A"+((Numero))+":M"+((Numero));


            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range, valueRange)
                    .setValueInputOption("RAW")
                    .execute();

             c = "=HIPERVINCULO(\"https://drive.google.com/drive/folders/"+Datos[8]+"\")";
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(c)));
            range = "C"+((Numero))+":C"+((Numero));
            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range, valueRange)
                    .setValueInputOption("USER_ENTERED")
                    .execute();
            return true;
        });

    }
    public Task<Boolean> Escribeentabla2(String[]Datos,String Ticket) {
        return Tasks.call(mExecutor, () -> {
            String spreadsheetId ;
            if (Datos[2].equals("Preventivo")){
                spreadsheetId= "197jGRK3XtNai23J_OWKOPmXa98Ov5ib5aKoxT8flQds";
            }else if (Datos[2].equals("Correctivo")){
                spreadsheetId = "1LEkgfatx1xaIM6R_uBDOCFuh6JiJAY0GuGkh8HDzdhM";
            }
            else if (Datos[2].contains("Instal")){
                //spreadsheetId = "1gdmQZXedP4l0Cm_jahXt7LCOJkOlC7GXagc7LPp0CCs";Version1
                spreadsheetId = "1b1G86lx9GagfW72X58GUJm3U9y7sAbGC8o1lmx2oDhY";
            }else{
                spreadsheetId= "1kmGFG6T5tJNne2C2lQD9G7LL9Qh24vbxNL5d4hLMRFI";
            }
            String range = "E2:E3000";
            int Numero = 0;
            int Count = 2;

            ValueRange response = mSheetsService.spreadsheets().values().get(spreadsheetId,range).execute();
            List<List<Object>> values = response.getValues();
            if (values!=null){
                System.out.println( values);
                for (List row : values){
                    if (row.get(0).toString().equals(Ticket)){
                        Numero = Count;
                        System.out.println("Ticket detectado en "+Numero);
                    }
                    Count++;
                }

            }
            if (Numero == 0) Numero = 3;

            Object n = new Object();
            n = Datos[1];
            Object o = new Object();
            o = Datos[0];
            ValueRange valueRange = new ValueRange();
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(n,o)));
            range = "N"+((Numero))+":O"+((Numero));
            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range, valueRange)
                    .setValueInputOption("RAW")
                    .execute();
            Object d = new Object();
            d = Datos[3];
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(d)));
            range = "D"+((Numero))+":D"+((Numero));
            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range, valueRange)
                    .setValueInputOption("USER_ENTERED")
                    .execute();

            return true;
        });

    }
    public Task<Boolean> Escribeentabla3(String[]Datos,String Ticket) {
        return Tasks.call(mExecutor, () -> {
            String spreadsheetId ;
            if (Datos[2].equals("Preventivo")){
                spreadsheetId= "197jGRK3XtNai23J_OWKOPmXa98Ov5ib5aKoxT8flQds";
            }else if (Datos[2].equals("Correctivo")){
                spreadsheetId = "1LEkgfatx1xaIM6R_uBDOCFuh6JiJAY0GuGkh8HDzdhM";
            }
            else if (Datos[2].contains("Instal")){
                //spreadsheetId = "1gdmQZXedP4l0Cm_jahXt7LCOJkOlC7GXagc7LPp0CCs";Version1
                spreadsheetId = "1b1G86lx9GagfW72X58GUJm3U9y7sAbGC8o1lmx2oDhY";
            }else{
                spreadsheetId= "1kmGFG6T5tJNne2C2lQD9G7LL9Qh24vbxNL5d4hLMRFI";
            }
            String range = "E2:E3000";
            int Numero = 0;
            int Count = 2;

            ValueRange response = mSheetsService.spreadsheets().values().get(spreadsheetId,range).execute();
            List<List<Object>> values = response.getValues();
            if (values!=null){
                System.out.println( values);
                for (List row : values){
                    if (row.get(0).toString().equals(Ticket)){
                        Numero = Count;
                        System.out.println("Ticket detectado en "+Numero);

                    }
                    Count++;
                }

            }
            if (Numero == 0) Numero = 3;
            Object p = new Object();
            p = Datos[0];
            Object q = new Object();
            q = Datos[1];
            ValueRange valueRange = new ValueRange();
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(p,q)));
            range = "P"+((Numero))+":Q"+((Numero));

            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range, valueRange)
                    .setValueInputOption("RAW")
                    .execute();

            Object d = new Object();
            d = Datos[3];
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(d)));
            range = "D"+((Numero))+":D"+((Numero));
            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range, valueRange)
                    .setValueInputOption("USER_ENTERED")
                    .execute();
            return true;
        });

    }
    public Task<Boolean> Escribeentabla4(String[]Datos,String Ticket) {
        return Tasks.call(mExecutor, () -> {
            SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket, Context.MODE_PRIVATE);
            String Diferencia;
            String FechaHoraCierre = sh.getString("DatosTicket5","")+" "+sh.getString("DatosTicket6","");
            String FechaHoraInicio = Datos[0]+" "+Datos[1];
            SimpleDateFormat sdf
                    = new SimpleDateFormat(
                    "dd-MM-yyyy HH:mm");
            Date d1 = sdf.parse(FechaHoraCierre);
            Date d2 = sdf.parse(FechaHoraInicio);


                    long difference_In_Time
                            = d2.getTime() - d1.getTime();

                    long difference_In_Seconds
                            = (difference_In_Time
                            / 1000)
                            % 60;

                    long difference_In_Minutes
                            = (difference_In_Time
                            / (1000 * 60))
                            % 60;

                    long difference_In_Hours
                            = (difference_In_Time
                            / (1000 * 60 * 60))
                            % 24;

                    long difference_In_Years
                            = (difference_In_Time
                            / (1000l * 60 * 60 * 24 * 365));

                    long difference_In_Days
                            = (difference_In_Time
                            / (1000 * 60 * 60 * 24))
                            % 365;

                    System.out.print(
                            "Difference "
                                    + "between two dates is: ");

                    System.out.println(
                            difference_In_Years
                                    + " years, "
                                    + difference_In_Days
                                    + " days, "
                                    + difference_In_Hours
                                    + " hours, "
                                    + difference_In_Minutes
                                    + " minutes, "
                                    + difference_In_Seconds
                                    + " seconds");
                    Diferencia = "d:"+difference_In_Days+" h:"+difference_In_Hours+" m:"+difference_In_Minutes;




            String spreadsheetId ;
            if (Datos[2].equals("Preventivo")){
                spreadsheetId= "197jGRK3XtNai23J_OWKOPmXa98Ov5ib5aKoxT8flQds";
            }else if (Datos[2].equals("Correctivo")){
                spreadsheetId = "1LEkgfatx1xaIM6R_uBDOCFuh6JiJAY0GuGkh8HDzdhM";
            }
            else if (Datos[2].contains("Instal")){
                //spreadsheetId = "1gdmQZXedP4l0Cm_jahXt7LCOJkOlC7GXagc7LPp0CCs";Version1
                spreadsheetId = "1b1G86lx9GagfW72X58GUJm3U9y7sAbGC8o1lmx2oDhY";
            }else{
                spreadsheetId= "1kmGFG6T5tJNne2C2lQD9G7LL9Qh24vbxNL5d4hLMRFI";
            }
            String range = "E2:E3000";
            int Numero = 0;
            int Count = 2;

            ValueRange response = mSheetsService.spreadsheets().values().get(spreadsheetId,range).execute();
            List<List<Object>> values = response.getValues();
            if (values!=null){
                System.out.println( values);
                for (List row : values){
                    if (row.get(0).toString().equals(Ticket)){
                        System.out.println("Ticket detectado en "+Numero);
                        Numero = Count;
                    }
                    Count++;
                }

            }
            if (Numero == 0) Numero = 3;
            else Numero = Numero+1;
            Object p= new Object();
            p = Datos[0];
            Object q = new Object();
            q = Datos[1];
            Object r = new Object();
            r = "-";
            Object s = new Object();
            s = "-";
            Object t = new Object();
            t = Diferencia;
            Object u = new Object();
            u = "-";
            Object v = new Object();
            v = "-";
            Object w = new Object();
            w = sh.getString("NSerieConBox","");;
            Object x = new Object();
            x = sh.getString("NSerieTransreceptor","");;
            Object y = new Object();
            y = sh.getString("NSerieIridium","");;
            Object z = new Object();
            z = sh.getString("IMEIIridium","");;
            Object aa = new Object();
            aa= sh.getString("SelloBlueTraker","");;
            Object ab = new Object();
            ab= sh.getString("SelloConBox","");;
            Object ac= new Object();
            ac= sh.getString("Contacto","");;

            ValueRange valueRange = new ValueRange();
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(p,q,r,s,t,u,v,w,x,y,z,aa,ab,ac)));
            range = "R"+((Numero-1))+":AE"+((Numero-1));

            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range, valueRange)
                    .setValueInputOption("RAW")
                    .execute();


            Object d = new Object();
            d = Datos[3];
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(d)));
            range = "D"+((Numero-1))+":D"+((Numero-1));
            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range, valueRange)
                    .setValueInputOption("USER_ENTERED")
                    .execute();
            return true;
        });

    }
    public Task<Boolean> Escribeentabla5(String[]Datos,String Ticket) {
        return Tasks.call(mExecutor, () -> {
            String spreadsheetId ;
            if (Datos[2].equals("Preventivo")){
                spreadsheetId= "197jGRK3XtNai23J_OWKOPmXa98Ov5ib5aKoxT8flQds";
            }else if (Datos[2].equals("Correctivo")){
                spreadsheetId = "1LEkgfatx1xaIM6R_uBDOCFuh6JiJAY0GuGkh8HDzdhM";
            }
            else if (Datos[2].contains("Instal")){
                //spreadsheetId = "1gdmQZXedP4l0Cm_jahXt7LCOJkOlC7GXagc7LPp0CCs";Version1
                spreadsheetId = "1b1G86lx9GagfW72X58GUJm3U9y7sAbGC8o1lmx2oDhY";
            }else{
                spreadsheetId= "1kmGFG6T5tJNne2C2lQD9G7LL9Qh24vbxNL5d4hLMRFI";
            }
            String range = "E2:E3000";
            int Numero = 0;
            int Count = 2;

            ValueRange response = mSheetsService.spreadsheets().values().get(spreadsheetId,range).execute();
            List<List<Object>> values = response.getValues();
            if (values!=null){
                System.out.println( values);
                for (List row : values){
                    if (row.get(0).toString().equals(Ticket)){
                        Numero = Count;
                        System.out.println("Ticket detectado en "+Numero);

                    }
                    Count++;
                }

            }
            if (Numero == 0) Numero = 3;
            else Numero = Numero+1;
            Object t= new Object();
            t = Datos[0];
            Object u = new Object();
            u= Datos[1];
            ValueRange valueRange = new ValueRange();
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(t,u)));
            range = "T"+((Numero-1))+":U"+((Numero-1));

            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range, valueRange)
                    .setValueInputOption("RAW")
                    .execute();

            Object d = new Object();
            d = Datos[3];
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(d)));
            range = "D"+((Numero-1))+":D"+((Numero-1));
            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range, valueRange)
                    .setValueInputOption("USER_ENTERED")
                    .execute();

            return true;
        });

    }
    public Task<Boolean> Escribeentabla6(String[]Datos,String Ticket) {
        return Tasks.call(mExecutor, () -> {
            SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket, Context.MODE_PRIVATE);
            String Diferencia;
            String FechaHoraCierre = sh.getString("DatosTicket5","")+" "+sh.getString("DatosTicket6","");
            String FechaHoraInicio = Datos[0]+" "+Datos[1];
            SimpleDateFormat sdf
                    = new SimpleDateFormat(
                    "dd-MM-yyyy HH:mm");
            Date d1 = sdf.parse(FechaHoraCierre);
            Date d2 = sdf.parse(FechaHoraInicio);


            long difference_In_Time
                    = d2.getTime() - d1.getTime();

            long difference_In_Seconds
                    = (difference_In_Time
                    / 1000)
                    % 60;

            long difference_In_Minutes
                    = (difference_In_Time
                    / (1000 * 60))
                    % 60;

            long difference_In_Hours
                    = (difference_In_Time
                    / (1000 * 60 * 60))
                    % 24;

            long difference_In_Years
                    = (difference_In_Time
                    / (1000l * 60 * 60 * 24 * 365));

            long difference_In_Days
                    = (difference_In_Time
                    / (1000 * 60 * 60 * 24))
                    % 365;

            System.out.print(
                    "Difference "
                            + "between two dates is: ");

            System.out.println(
                    difference_In_Years
                            + " years, "
                            + difference_In_Days
                            + " days, "
                            + difference_In_Hours
                            + " hours, "
                            + difference_In_Minutes
                            + " minutes, "
                            + difference_In_Seconds
                            + " seconds");
            Diferencia = "d:"+difference_In_Days+" h:"+difference_In_Hours+" m:"+difference_In_Minutes;




            String spreadsheetId ;
            if (Datos[2].equals("Preventivo")){
                spreadsheetId= "197jGRK3XtNai23J_OWKOPmXa98Ov5ib5aKoxT8flQds";
            }else if (Datos[2].equals("Correctivo")){
                spreadsheetId = "1LEkgfatx1xaIM6R_uBDOCFuh6JiJAY0GuGkh8HDzdhM";
            }
            else if (Datos[2].contains("Instal")){
                //spreadsheetId = "1gdmQZXedP4l0Cm_jahXt7LCOJkOlC7GXagc7LPp0CCs";Version1
                spreadsheetId = "1b1G86lx9GagfW72X58GUJm3U9y7sAbGC8o1lmx2oDhY";
            }else{
                spreadsheetId= "1kmGFG6T5tJNne2C2lQD9G7LL9Qh24vbxNL5d4hLMRFI";
            }
            String range = "E2:E3000";
            int Numero = 0;
            int Count = 2;

            ValueRange response = mSheetsService.spreadsheets().values().get(spreadsheetId,range).execute();
            List<List<Object>> values = response.getValues();
            System.out.println(Datos[2]);
            if (values!=null){
                System.out.println( values);
                for (List row : values){
                    if (row.get(0).toString().equals(Ticket)){
                        System.out.println("Ticket detectado en "+Numero);
                        Numero = Count;
                    }
                    Count++;
                }

            }
           if (Numero == 0) Numero = 3;
            else Numero = Numero+1;

            Object u= new Object();
            u = FechaHoraInicio;
            Object v = new Object();
            v = Diferencia;
            Object w = new Object();
            w = sh.getString("NSerieConBox","");;
            Object x = new Object();
            x = sh.getString("NSerieTransreceptor","");;
            Object y = new Object();
            y = sh.getString("NSerieIridium","");;
            Object z = new Object();
            z = sh.getString("IMEIIridium","");;
            Object aa = new Object();
            aa= sh.getString("SelloBlueTraker","");;
            Object ab = new Object();
            ab= sh.getString("SelloConBox","");;
            Object ac= new Object();
            ac= sh.getString("Contacto","");;



            ValueRange valueRange = new ValueRange();
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(u,v,w,x,y,z,aa,ab,ac)));
            range = "W"+((Numero-1))+":AE"+((Numero-1));

            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range, valueRange)
                    .setValueInputOption("RAW")
                    .execute();

            Object d = new Object();
            d = Datos[3];
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(d)));
            range = "D"+((Numero-1))+":D"+((Numero-1));
            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range, valueRange)
                    .setValueInputOption("USER_ENTERED")
                    .execute();

            return true;
        });

    }
    public Task<String> CreaBitacora (String Ticket) {
        return Tasks.call(mExecutor, () -> {
            String Spreedsheetid = null;
            Spreadsheet spreadSheet = new Spreadsheet().setProperties(
                    new SpreadsheetProperties().setTitle(Ticket));
            Spreadsheet result = this.mSheetsService
                    .spreadsheets()
                    .create(spreadSheet).execute();

            Object a = new Object();
            a = "Cons";
            Object b = new Object();
            b = "LinkFoto";
            Object c = new Object();
            c= "Actividad";
            Object d = new Object();
            d= "Comentario";
            Object e = new Object();
            e= "Hora";
            Object f = new Object();
            f= "Fecha";
            ValueRange valueRange = new ValueRange();
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(a,b,c,d,e,f)));
            String range = "A2:F2";

            this.mSheetsService.spreadsheets().values().update(result.getSpreadsheetId(), range, valueRange)
                    .setValueInputOption("RAW")
                    .execute();
            a="Ultimacelda";
            b="=ARRAYFORMULA(MAX((A3:A<>\"\")*FILA(A3:A)))";
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(a,b)));
            range = "A1:B1";

            this.mSheetsService.spreadsheets().values().update(result.getSpreadsheetId(), range, valueRange)
                    .setValueInputOption("USER_ENTERED")
                    .execute();
            return result.getSpreadsheetId();
    });
    }
    public Task<Integer> ActualizaBitacora (String spreadsheetId, String Comentario, String Hora, String Fecha, String link, int ID) {
        return Tasks.call(mExecutor, () -> {

            String range = "B1:B1";
            int Numero = ID;
            ValueRange response = mSheetsService.spreadsheets().values().get(spreadsheetId,range).execute();
            List<List<Object>> values = response.getValues();
            if (values!=null){

                for (List row : values){
                    row.get(0).toString();
                    Numero = Integer.parseInt(row.get(0).toString());
                }

            }
            if (Numero == 0) Numero = 3;
            else Numero = Numero+1;
            Object a = new Object();
            a = Numero-2;
            Object b = new Object();
            b = "-";
            Object c = new Object();
            c= "Actividad"+ID;
            Object d = new Object();
            d= Comentario;
            Object e = new Object();
            e= Hora;
            Object f = new Object();
            f= Fecha;

            ValueRange valueRange = new ValueRange();
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(a,b,c,d,e,f)));
            range = "A"+((Numero))+":F"+((Numero));


            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range, valueRange)
                    .setValueInputOption("RAW")
                    .execute();
            if (link.equals("-")) b = "-";
            else b = "=HIPERVINCULO(\"https://drive.google.com/file/d/"+link+"/view"+"\")";
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(b)));
            range = "B"+((Numero))+":B"+((Numero));
            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range, valueRange)
                    .setValueInputOption("USER_ENTERED")
                    .execute();
            return Numero-2;
        });
    }
    public Task<Boolean> buscaBarco() {
        return Tasks.call(mExecutor, () -> {
            String range="!E296:E3000";
            String RNPINICIAL="00066233";
            int NumerodeBarcos=1;
            int NumerodePermisos=0;
            String spreadsheetId = "1yGsTs7e4VDk505s7laWoDO66YXf_-R2sIAh7Gh4GWX0"; //spreadsheetID;
            //String spreadsheetId = "1WDW6MUKIBTqIkClC0LqV0jOlrSeBaMqd2bOWSd-SBIk"; //Completa;
            List<String> results = new ArrayList<String>();
            ValueRange response = mSheetsService.spreadsheets().values().get(spreadsheetId,range).execute();
            List<List<Object>> values = response.getValues();
            if (values!=null){
                int k = 296;
                System.out.println(values);
                for (List row : values){
                    Thread.sleep(1000);
                    results.add(row.get(0).toString());
                    if(row.get(0).toString().equals(RNPINICIAL)){

                      NumerodePermisos++;
                    }
                    else{
                      NumerodeBarcos++;
                      NumerodePermisos =1;
                    }



                    RNPINICIAL = row.get(0).toString();
                    System.out.println("Numero de barcos: "+NumerodeBarcos);
                    System.out.println("Numero de permisos de : "+RNPINICIAL+" "+NumerodePermisos);
                    k++;

                    String newrange = "!A"+k+":BO"+k;
                    ValueRange newresponse = mSheetsService.spreadsheets().values().get(spreadsheetId,newrange).execute();
                    for (int i = 0;i<67;i++){
                        String Dato;
                        try{
                            //System.out.println(newresponse.getValues().get(0).get(i).toString());
                            Dato = newresponse.getValues().get(0).get(i).toString();
                        }
                        catch (Exception E){
                            //System.out.println("Dato no registrado");
                            Dato = "Dato no registrado";
                        }

                            Map<String, Object> UsuarioNuevo = new HashMap<>();
                            UsuarioNuevo.put("DatoBarco"+i, Dato);
                            System.out.println("Subiendo"+newresponse.getValues().get(0).get(4).toString());
                            refBarcos.child(newresponse.getValues().get(0).get(4).toString()).updateChildren(UsuarioNuevo, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    if (error == null && ref != null) {
                                        System.out.println("Se subio");
                                        // there was no error and the value is modified
                                    } else {
                                        // there was an error. try to update again
                                    }
                                }
                            });
                        }


                    }


                }

            //}

            return true;
        });

    }

    public Task<Boolean> RegistraBarco() {
        return Tasks.call(mExecutor, () -> {
            String spreadsheetId = "1yGsTs7e4VDk505s7laWoDO66YXf_-R2sIAh7Gh4GWX0"; //spreadsheetID;
            String range = "B1:B1";
            int Numero = 0;
            ValueRange response = mSheetsService.spreadsheets().values().get(spreadsheetId,range).execute();
            List<List<Object>> values = response.getValues();
            if (values!=null){
                for (List row : values){
                    row.get(0).toString();
                    Numero = Integer.parseInt(row.get(0).toString());
                }

            }
            for (int k = 2; k<Numero;k++){

                range = "!A"+(k+1)+":BO"+(k+1);
                //String spreadsheetId = "1WDW6MUKIBTqIkClC0LqV0jOlrSeBaMqd2bOWSd-SBIk"; //Completa;
                response = mSheetsService.spreadsheets().values().get(spreadsheetId,range).execute();
                System.out.println("Subiendo"+response.getValues().get(0).get(4).toString());
                for (int i = 0;i<67;i++){
                    String Dato;
                    try{
                        //System.out.println(newresponse.getValues().get(0).get(i).toString());
                        Dato = response.getValues().get(0).get(i).toString();
                    }
                    catch (Exception E){
                        //System.out.println("Dato no registrado");
                        Dato = "Dato no registrado";
                    }
                    System.out.println("Dato"+i+" "+Dato);
                    Map<String, Object> UsuarioNuevo = new HashMap<>();
                    UsuarioNuevo.put("DatoBarco"+i, Dato);
                    refBarcos.child(response.getValues().get(0).get(4).toString()).updateChildren(UsuarioNuevo, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error == null && ref != null) {
                                System.out.println("Se subio");
                                // there was no error and the value is modified
                            } else {
                                // there was an error. try to update again
                            }
                        }
                    });
                }
                Thread.sleep(1000);
            }





            //}

            return true;
        });

    }
}
