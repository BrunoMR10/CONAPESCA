package com.bmr.conapesca.Herramientas;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bmr.conapesca.AsignaTicket;
import com.bmr.conapesca.Datos.Datos;
import com.bmr.conapesca.Entidades.Barcos;
import com.bmr.conapesca.ListaBarcos;
import com.bmr.conapesca.MainActivity;
import com.bmr.conapesca.Notificaciones;
import com.bmr.conapesca.PDFViewer;
import com.bmr.conapesca.PantallaPrincipal;
import com.bmr.conapesca.RInstalacion;
import com.bmr.conapesca.ReporteInstalacion;
import com.bmr.conapesca.ServiceHelper.DriveServiceHelper;
import com.bmr.conapesca.ServiceHelper.SheetsServiceHelper;
import com.bmr.conapesca.Servicios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Firebase {
    Datos dt = new Datos();
    private DriveServiceHelper mDriveServiceHelper;
    private SheetsServiceHelper mSheetServiceHelper;
    //Storage
    public FirebaseStorage storage = FirebaseStorage.getInstance();
    public StorageReference storageReference = storage.getReferenceFromUrl("gs://conapesca-fc179.appspot.com");
    //Auth
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    //RealtimeDatabase
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference refUsuarios = database.getReference("Usuarios");
    public DatabaseReference refTickets = database.getReference("Tickets");
    public DatabaseReference refBarcos = database.getReference("Barcos");
    public void Ingresa(Context context, String CorreoElectronico, String Contraseña, Activity activity) {
        mAuth.signInWithEmailAndPassword(CorreoElectronico, Contraseña)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            DatosUsuario(dt.Datos, context);

                        } else {
                            Toast.makeText(context, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }

                    }

                });

    }
    public void DatosUsuario(String[] Datos, Context context) {

        FirebaseUser user = mAuth.getCurrentUser();

        if (user.isEmailVerified()){
            refUsuarios.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        String UID = user.getUid();
                        String NombreCompleto = dataSnapshot.child("NombreCompleto").getValue(String.class);
                        String ApellidoPaterno= dataSnapshot.child("ApellidoPaterno").getValue(String.class);
                        String ApellidoMaterno= dataSnapshot.child("ApellidoMaterno").getValue(String.class);
                        String Nombre= dataSnapshot.child("Nombre").getValue(String.class);
                        String Puesto = dataSnapshot.child("Puesto").getValue(String.class);
                        String CorreoElectronico = dataSnapshot.child("CorreoElectronico").getValue(String.class);
                        String Numero = dataSnapshot.child("Numero").getValue(String.class);
                        Datos[0] = UID;
                        Datos[1] = NombreCompleto;
                        Datos[2] = ApellidoPaterno;
                        Datos[3] = ApellidoMaterno;
                        Datos[4] = Nombre;
                        Datos[5] = Puesto;
                        Datos[6] = CorreoElectronico;
                        Datos[7] = Numero;
                        Datos[8]= "Acceso";

                        FirebaseMessaging.getInstance().getToken()
                                .addOnCompleteListener(new OnCompleteListener<String>() {
                                    @Override
                                    public void onComplete(@NonNull Task<String> task) {
                                        if (!task.isSuccessful()) {

                                            return;
                                        }
                                        String token = task.getResult();
                                        System.out.println("Token : "+ token);

                                        Map<String, Object> UsuarioNuevo = new HashMap<>();
                                        UsuarioNuevo.put("token", token);
                                        // Log and toast
                                        refUsuarios.child(UID).updateChildren(UsuarioNuevo, new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                if (error == null && ref != null) {

                                                    Intent i = new Intent(context, PantallaPrincipal.class);
                                                    i.putExtra("Datos", Datos);
                                                    context.startActivity(i);

                                                    // there was no error and the value is modified
                                                } else {
                                                    Toast.makeText(context, "Error al registrarUsuario", Toast.LENGTH_SHORT).show();
                                                    // there was an error. try to update again
                                                }
                                            }
                                        });

                                        // Get new FCM registration token

                                    }
                                });



                        //Toast.makeText(context,Datos[4]+" "+Datos[5]+" "+Datos[8]+" "+Datos[9],Toast.LENGTH_SHORT).show();

                    } else {

                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
        else{
            user.sendEmailVerification();
            Toast.makeText(context,"Valida tu usuario porfavor, se te mando un correo a tu bandeja de entrada",Toast.LENGTH_SHORT).show();
        }

    }

    //// RegistraUsuario
    public void SubeCred(ImageView Foto, String[] DatosUsuario, String Iden, Context context) {
        StorageReference Ticketsref, NombreUs;
        Foto.setDrawingCacheEnabled(true);
        Foto.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) Foto.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        Ticketsref = storageReference.child("Credenciales").child(Iden);
        NombreUs = Ticketsref.child(DatosUsuario[1] + ".jpg");

        UploadTask uploadTask = NombreUs.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Foto.getContext(), "Fail", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if (DatosUsuario.length>8){
                            RegistraUsuarioAuth(DatosUsuario, context);
                        }
                        else{
                            RegistraUsuarioDatabase(DatosUsuario, DatosUsuario[0], context);
                        }


                //Toast.makeText(Foto.getContext(), "Credencial subida con éxtio", Toast.LENGTH_SHORT).show();
            }
        })
        ;
    }
    public void RegistraUsuarioAuth(String[] DatosUsuario, Context context) {
        mAuth.createUserWithEmailAndPassword(DatosUsuario[6], DatosUsuario[8])
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(DatosUsuario[0]).build();


                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");
                                            }
                                        }
                                    });
                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "Email sent.");
                                                Toast.makeText(context, "Te hemos mandado un correo de verificacion, verifica tu bandeja de entrada, si no aparece verifica en spam", Toast.LENGTH_SHORT).show();
                                                RegistraUsuarioDatabase(DatosUsuario, user.getUid(), context);
                                            }
                                        }
                                    });

                        } else {
                            Toast.makeText(context, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    public void RegistraUsuarioDatabase(String[] DatosUsuario, String Database, Context context) {
        Map<String, Object> UsuarioNuevo = new HashMap<>();
        UsuarioNuevo.put("NombreCompleto", DatosUsuario[1]);
        UsuarioNuevo.put("ApellidoPaterno", DatosUsuario[2]);
        UsuarioNuevo.put("ApellidoMaterno", DatosUsuario[3]);
        UsuarioNuevo.put("Nombre", DatosUsuario[4]);
        UsuarioNuevo.put("Puesto", DatosUsuario[5]);
        UsuarioNuevo.put("CorreoElectronico", DatosUsuario[6]);
        UsuarioNuevo.put("Numero", DatosUsuario[7]);
        UsuarioNuevo.put("NElector", DatosUsuario[10]);
        UsuarioNuevo.put("Direccion1", DatosUsuario[11]);
        UsuarioNuevo.put("Direccion2", DatosUsuario[12]);
        UsuarioNuevo.put("Direccion3", DatosUsuario[13]);
        UsuarioNuevo.put("Direccion4", DatosUsuario[14]);
        UsuarioNuevo.put("Direccion5", DatosUsuario[15]);
        UsuarioNuevo.put("Direccion6", DatosUsuario[16]);
        //UsuarioNuevo.put("Registro",Datos[0]);
        refUsuarios.child(Database).updateChildren(UsuarioNuevo, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null && ref != null) {
                    if (DatosUsuario.length>8){
                        Intent i = new Intent(context, MainActivity.class);
                        context.startActivity(i);
                    }
                    else{
                        Intent i = new Intent(context, PantallaPrincipal.class);
                        i.putExtra("Datos",DatosUsuario);
                        context.startActivity(i);
                    }


                    // there was no error and the value is modified
                } else {
                    Toast.makeText(context, "Error al registrarUsuario", Toast.LENGTH_SHORT).show();
                    // there was an error. try to update again
                }
            }
        });


    }
    public void CerrarSesion(Context context) {
        mAuth.signOut();
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
    }
    ////REASIGNA TICKET
    private String[] ObtenSharedPreference(){
        String [] Datos = new String[dt.Datos.length];
        SharedPreferences sh = getApplicationContext().getSharedPreferences("Datos", MODE_PRIVATE);
        for (int i = 0;i<dt.Datos.length;i++){
            Datos[i]=sh.getString("Datos"+String.valueOf(i), "");
        }
        return Datos;
    }
    public void Reasignaticket(String [] DatosTicket,Context context){
        Map<String, Object> Bitacora = new HashMap<>();
        Bitacora.put("Descripcion", "Actualizacion de estatus");
        Bitacora.put("Hora", DatosTicket[5]);
        Bitacora.put("Fecha", DatosTicket[6]);
        Bitacora.put("Link", "No hay link");
        Bitacora.put("Modificacion", "false");
        Bitacora.put("Actividad", "Re-asignacion a: "+DatosTicket[1]);


        Map<String, Object> Sobrescribe = new HashMap<>();
        Sobrescribe.put("NombreIngeniero", DatosTicket[1]);
        Sobrescribe.put("PuestoIngeniero", DatosTicket[2]);
        Sobrescribe.put("UID",DatosTicket[17]);
        Sobrescribe.put("Estatus",DatosTicket[7]);
        Sobrescribe.put("FechaReasignacion", DatosTicket[5]);
        Sobrescribe.put("FechaReasignacion", DatosTicket[6]);

        Map<String, Object> Reasigna = new HashMap<>();
        Reasigna.put(DatosTicket[3], DatosTicket[4]);
        refUsuarios.child(DatosTicket[18]).child("Tickets").child(DatosTicket[0]).removeValue( new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null && ref != null) {
                    refUsuarios.child(DatosTicket[17]).child("Tickets").updateChildren(Reasigna, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error == null && ref != null) {

                                refTickets.child(DatosTicket[4]).child(DatosTicket[3]).updateChildren(Sobrescribe, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        if (error == null && ref != null) {

                                            refTickets.child(DatosTicket[4]).child(DatosTicket[3]).child("Bitacora").child("Accion0").updateChildren(Bitacora, new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                    if (error == null && ref != null) {

                                                        Toast.makeText(context, "Ticket reasignado con exito", Toast.LENGTH_SHORT).show();
                                                        String [] Datos2 = ObtenSharedPreference();
                                                        Intent i = new Intent(context, Servicios.class);
                                                        i.putExtra("Datos",Datos2);
                                                        context.startActivity(i);
                                                        // there was no error and the value is modified
                                                    } else {
                                                        Toast.makeText(context, "Error al registrarUsuario", Toast.LENGTH_SHORT).show();
                                                        // there was an error. try to update again
                                                    }
                                                }
                                            });


                                        } else {
                                            Toast.makeText(context, "Error al asignar Ticket", Toast.LENGTH_SHORT).show();
                                            // there was an error. try to update again
                                        }
                                    }
                                });



                                Toast.makeText(context, "Ticket Re-asigando", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(context, "Error al asignar Ticket", Toast.LENGTH_SHORT).show();
                                // there was an error. try to update again
                            }
                        }
                    });

                }

                else {
                    Toast.makeText(context, "Error al re-asignar Ticket", Toast.LENGTH_SHORT).show();
                    // there was an error. try to update again
                }
            }
        });
    }
    ////ACTUALIZATICKET
    public void ActualizaTicket(String [] DatosTicket,Context context){
        Map<String, Object> Bitacora = new HashMap<>();
        Bitacora.put("Descripcion", "Actualizacion de estatus");
        Bitacora.put("Hora", DatosTicket[5]);
        Bitacora.put("Fecha", DatosTicket[6]);
        Bitacora.put("Link", "No hay link");
        Bitacora.put("Modificacion", "false");
        Bitacora.put("Actividad", "Re-asignacion a: "+DatosTicket[1]);


        Map<String, Object> Sobrescribe = new HashMap<>();
        Sobrescribe.put("NombreIngeniero", DatosTicket[1]);
        Sobrescribe.put("PuestoIngeniero", DatosTicket[2]);
        Sobrescribe.put("TipoServicio", DatosTicket[4]);
        Sobrescribe.put("FechaInicio", DatosTicket[5]);
        Sobrescribe.put("HoraInicio", DatosTicket[6]);
        Sobrescribe.put("Estatus",DatosTicket[7]);
        Sobrescribe.put("Link",DatosTicket[8]);
        Sobrescribe.put("Link2",DatosTicket[9]);
        Sobrescribe.put("Link3",DatosTicket[10]);
        Sobrescribe.put("Barco",DatosTicket[11]);
        Sobrescribe.put("Noficio",DatosTicket[12]);
        Sobrescribe.put("FechaOficio",DatosTicket[13]);
        Sobrescribe.put("LocalidadPuerto",DatosTicket[14]);
        Sobrescribe.put("NombreBarco",DatosTicket[15]);
        Sobrescribe.put("Permisionario",DatosTicket[16]);
        Sobrescribe.put("UID",DatosTicket[0]);

        refTickets.child(DatosTicket[4]).child(DatosTicket[3]).updateChildren(Sobrescribe, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null && ref != null) {

                    refTickets.child(DatosTicket[4]).child(DatosTicket[3]).child("Bitacora").child("Accion0").updateChildren(Bitacora, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error == null && ref != null) {
                                Toast.makeText(context, "DatosActualizados con éxito", Toast.LENGTH_SHORT).show();

                                String [] Datos2 = ObtenSharedPreference();
                                Intent i = new Intent(context, Servicios.class);
                                i.putExtra("Datos",Datos2);
                                context.startActivity(i);
                                // there was no error and the value is modified
                            } else {
                                Toast.makeText(context, "Error al registrarUsuario", Toast.LENGTH_SHORT).show();
                                // there was an error. try to update again
                            }
                        }
                    });



                } else {
                    Toast.makeText(context, "Error al asignar Ticket", Toast.LENGTH_SHORT).show();
                    // there was an error. try to update again
                }
            }
        });
    }
    ////ASIGNA TICKET
    public void GuardaInformacionTicket(String [] DatosTicket2,Boolean [] Checks,String [] DatosTicket,Context context){
        Map<String, Object> UsuarioNuevo = new HashMap<>();
        UsuarioNuevo.put("Contacto", DatosTicket2[0]);
        UsuarioNuevo.put("NombreResponable", DatosTicket2[1]);
        UsuarioNuevo.put("CargoResponsable", DatosTicket2[2]);
        UsuarioNuevo.put("Observaciones", DatosTicket2[3]);
        UsuarioNuevo.put("VBateria", DatosTicket2[4]);
        UsuarioNuevo.put("NoSelloConBox", DatosTicket2[5]);
        UsuarioNuevo.put("NoSelloTrans",DatosTicket2[6]);
        UsuarioNuevo.put("Justificacion",DatosTicket2[7]);
        UsuarioNuevo.put("NSerieIridium",DatosTicket2[8]);
        UsuarioNuevo.put("IMEIIridium",DatosTicket2[9]);
        UsuarioNuevo.put("NSerieTransreceptor",DatosTicket2[10]);
        UsuarioNuevo.put("NSerieConBox",DatosTicket2[11]);
        for (int i=0; i<Checks.length;i++){
            UsuarioNuevo.put("Check"+i, Checks[i]);
        }
        refTickets.child(DatosTicket[4]).child(DatosTicket[3]).updateChildren(UsuarioNuevo, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null && ref != null) {
                    Toast.makeText(context,"Datos Actualizados en Firebase",Toast.LENGTH_SHORT).show();
                    // there was no error and the value is modified
                } else {
                    Toast.makeText(context, "Error al registrarUsuario", Toast.LENGTH_SHORT).show();
                    // there was an error. try to update again
                }
            }
        });
    }
    public void AsignaTicket (String [] DatosTicket,Context context){

        refTickets.child(DatosTicket[4]).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(DatosTicket[4]);
                if (dataSnapshot.exists()) {
                    String NumeroVigente = dataSnapshot.child("Consecutivo Ticket vigente").getValue(String.class);
                    System.out.println(NumeroVigente);
                    String Clave;
                    if (DatosTicket[4].equals("Correctivo")) Clave = "C-";
                    else if (DatosTicket[4].equals("Interno")) Clave = "I-";
                    else if (DatosTicket[4].equals("Instalacion")) Clave = "S-";
                    else Clave ="P-";
                    try{int PosIn = NumeroVigente.indexOf(Clave);
                        int PosFin =NumeroVigente.length();
                        String Numero = NumeroVigente.substring(PosIn+2,PosFin);
                        int Number = Integer.parseInt(Numero);
                        int NuevoNumber = Number+1;
                        if (NuevoNumber<10){
                            DatosTicket[3] = Clave+"000"+NuevoNumber;
                        }else if (NuevoNumber>=10&&NuevoNumber<100){
                            DatosTicket[3]= Clave+"00"+NuevoNumber;
                        }
                        else if (NuevoNumber>=100&&NuevoNumber<1000){
                            DatosTicket[3] = Clave+"0"+NuevoNumber;
                        }else{
                            DatosTicket[3] = Clave+NuevoNumber;
                        }
                        Map<String, Object> UsuarioNuevo = new HashMap<>();
                        UsuarioNuevo.put(DatosTicket[3], DatosTicket[4]);

                        refUsuarios.child(DatosTicket[0]).child("Tickets").updateChildren(UsuarioNuevo, new DatabaseReference.CompletionListener() {


                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if (error == null && ref != null) {
                      ;


                                    refUsuarios.child(DatosTicket[0]).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            if (dataSnapshot.exists()) {
                                                String Token = dataSnapshot.child("token").getValue(String.class);

                                                refBarcos.child(DatosTicket[11]).child("Tickets").updateChildren(UsuarioNuevo, new DatabaseReference.CompletionListener() {
                                                    @Override
                                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                        if (error == null && ref != null) {

                                                            RegistraTicket(DatosTicket,context,Token);

                                                            // there was no error and the value is modified
                                                        } else {
                                                            Toast.makeText(context, "Error al asignar Ticket", Toast.LENGTH_SHORT).show();
                                                            // there was an error. try to update again
                                                        }
                                                    }
                                                    // there was no error and the value is modified
                                                });


                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });


                                } else {
                                    Toast.makeText(context, "Error al asignar Ticket", Toast.LENGTH_SHORT).show();
                                    // there was an error. try to update again
                                }
                            }
                        });

                    }catch (Exception e){
                        Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
                    }
                } else {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
    public void RegistraTicket(String[] DatosTicket, Context context,String Token){
        Map<String, Object> ActualizaConsecutivoTicket = new HashMap<>();
        ActualizaConsecutivoTicket.put("Consecutivo Ticket vigente", DatosTicket[3]);

        refTickets.child(DatosTicket[4]).updateChildren(ActualizaConsecutivoTicket, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null && ref != null) {

                    Map<String, Object> UsuarioNuevo = new HashMap<>();
                    UsuarioNuevo.put("NombreIngeniero", DatosTicket[1]);
                    UsuarioNuevo.put("PuestoIngeniero", DatosTicket[2]);
                    UsuarioNuevo.put("TipoServicio", DatosTicket[4]);
                    UsuarioNuevo.put("FechaInicio", DatosTicket[5]);
                    UsuarioNuevo.put("HoraInicio", DatosTicket[6]);
                    UsuarioNuevo.put("Estatus",DatosTicket[7]);
                    UsuarioNuevo.put("Link",DatosTicket[8]);
                    UsuarioNuevo.put("Link2",DatosTicket[9]);
                    UsuarioNuevo.put("Link3",DatosTicket[10]);
                    UsuarioNuevo.put("Barco",DatosTicket[11]);
                    UsuarioNuevo.put("Noficio",DatosTicket[12]);
                    UsuarioNuevo.put("FechaOficio",DatosTicket[13]);
                    UsuarioNuevo.put("LocalidadPuerto",DatosTicket[14]);
                    UsuarioNuevo.put("NombreBarco",DatosTicket[15]);
                    UsuarioNuevo.put("Permisionario",DatosTicket[16]);
                    UsuarioNuevo.put("UID",DatosTicket[0]);

                    UsuarioNuevo.put("Contacto", "");
                    UsuarioNuevo.put("NombreResponable", "");
                    UsuarioNuevo.put("CargoResponsable", "");
                    UsuarioNuevo.put("Observaciones", "");
                    UsuarioNuevo.put("VBateria", "24v");
                    UsuarioNuevo.put("NoSelloConBox", "");
                    UsuarioNuevo.put("NoSelloTrans","");
                    UsuarioNuevo.put("Justificacion","");
                    UsuarioNuevo.put("NSerieIridium","");
                    UsuarioNuevo.put("IMEIIridium","");
                    UsuarioNuevo.put("NSerieTransreceptor","");
                    UsuarioNuevo.put("NSerieConBox","");

                    for (int i=0; i<19;i++){
                        UsuarioNuevo.put("Check"+i,false);
                        if (i <11 && i!=4)UsuarioNuevo.put("Check"+i,true);
                        else if (i== 17|| i==18){
                            if (DatosTicket[16].contains("S.A")){
                                UsuarioNuevo.put("Check"+18,true);
                                UsuarioNuevo.put("Check"+17,false);
                            }
                            else{ UsuarioNuevo.put("Check"+17,true);
                                UsuarioNuevo.put("Check"+18,false);}
                        }
                        else UsuarioNuevo.put("Check"+i,false);

                    }

                    refTickets.child(DatosTicket[4]).child(DatosTicket[3]).updateChildren(UsuarioNuevo, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error == null && ref != null) {

                                Map<String, Object> UsuarioNuevo = new HashMap<>();
                                UsuarioNuevo.put("Descripcion", "Asignacion de ticket");
                                UsuarioNuevo.put("Hora", DatosTicket[6]);
                                UsuarioNuevo.put("Fecha", DatosTicket[5]);
                                UsuarioNuevo.put("Link", "");
                                UsuarioNuevo.put("Modificacion", "false");
                                UsuarioNuevo.put("Actividad", "Actualización de estatus");

                                refTickets.child(DatosTicket[4]).child(DatosTicket[3]).child("Bitacora").child("Accion1").updateChildren(UsuarioNuevo, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        if (error == null && ref != null) {

                                            Intent i = new Intent(context, AsignaTicket.class);
                                            DatosTicket[17]=Token;
                                            if (DatosTicket[8].equals("Link"))i.putExtra("DatosTicket",DatosTicket);
                                            else{
                                                Toast.makeText(context,"TicketAsignado",Toast.LENGTH_SHORT).show();
                                                System.out.println(Token);
                                                FCMSend.pushNotification(context,
                                                        Token,
                                                        "Asignacion de Ticket",
                                                        DatosTicket[3]+" Embarcación: " + DatosTicket[15]);
                                                System.out.println("Mandando mensaje");
                                            }
                                            context.startActivity(i);
                                            // there was no error and the value is modified
                                        } else {
                                            Toast.makeText(context, "Error al registrarUsuario", Toast.LENGTH_SHORT).show();
                                            // there was an error. try to update again
                                        }
                                    }
                                });



                                // there was no error and the value is modified
                            } else {
                                Toast.makeText(context, "Error al registrarUsuario", Toast.LENGTH_SHORT).show();
                                // there was an error. try to update again
                            }
                        }
                    });
                    // there was no error and the value is modified
                } else {
                    Toast.makeText(context, "Error al registrarUsuario", Toast.LENGTH_SHORT).show();
                    // there was an error. try to update again
                }
            }
        });


    }
    public void RegistraBitacoraenTicket(String[] DatosTicket, Context context,int ID,String []DatosBitacora){
        Map<String, Object> UsuarioNuevo = new HashMap<>();
        UsuarioNuevo.put("Descripcion", DatosBitacora[0]);
        UsuarioNuevo.put("Hora", DatosBitacora[1]);
        UsuarioNuevo.put("Fecha", DatosBitacora[2]);
        UsuarioNuevo.put("Link", "https://drive.google.com/file/d/"+DatosBitacora[3]+"/view");
        UsuarioNuevo.put("ID", DatosBitacora[3]);
        UsuarioNuevo.put("Modificacion", DatosBitacora[4]);
        UsuarioNuevo.put("Actividad", DatosBitacora[5]);

                    refTickets.child(DatosTicket[4]).child(DatosTicket[3]).child("Bitacora").child("Accion"+ID).updateChildren(UsuarioNuevo, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error == null && ref != null) {
                                // there was no error and the value is modified
                            } else {
                                Toast.makeText(context, "Error al registrarUsuario", Toast.LENGTH_SHORT).show();
                                // there was an error. try to update again
                            }
                        }
                    });
                    // there was no error and the value is modified
                }
    ////BARCO
    public void RegistraBarco(String[] Barco, Context context){
        Map<String, Object> UsuarioNuevo = new HashMap<>();
        UsuarioNuevo.put("NombreBarco", Barco[0]);
        UsuarioNuevo.put("MatriculaBarco", Barco[1]);
        UsuarioNuevo.put("AñoContr", Barco[2]);
        UsuarioNuevo.put("TonBruto", Barco[3]);
        UsuarioNuevo.put("TonNeto", Barco[4]);
        UsuarioNuevo.put("MaterialCasco", Barco[5]);
        UsuarioNuevo.put("Permisionario", Barco[6]);
        UsuarioNuevo.put("Rlegal", Barco[7]);
        UsuarioNuevo.put("TFijo", Barco[8]);
        UsuarioNuevo.put("Tmovil", Barco[9]);
        UsuarioNuevo.put("Ciudad", Barco[10]);
        UsuarioNuevo.put("Municipio", Barco[11]);
        UsuarioNuevo.put("Colonia", Barco[12]);
        UsuarioNuevo.put("CP", Barco[13]);
        UsuarioNuevo.put("RNPA", Barco[14]);
        UsuarioNuevo.put("EntidadFederativa", Barco[15]);
        UsuarioNuevo.put("CorreoElectronico", Barco[16]);
        UsuarioNuevo.put("TipoPersona", Barco[17]);
        UsuarioNuevo.put("MarcaMotor", Barco[18]);
        UsuarioNuevo.put("PotenciaMotor", Barco[19]);
        UsuarioNuevo.put("SerieMotor", Barco[20]);
        UsuarioNuevo.put("FolioCamaron", Barco[21]);
        UsuarioNuevo.put("FolioTunidos", Barco[22]);
        UsuarioNuevo.put("FolioTiburon", Barco[23]);
        UsuarioNuevo.put("FolioEscamas", Barco[24]);
        UsuarioNuevo.put("FolioSardia", Barco[25]);
        UsuarioNuevo.put("FolioOtros", Barco[26]);
        UsuarioNuevo.put("Comercial", Barco[27]);
        UsuarioNuevo.put("PescaDidactica", Barco[28]);
        UsuarioNuevo.put("Fomento", Barco[29]);
        UsuarioNuevo.put("Camaron", Barco[30]);
        UsuarioNuevo.put("Tunidos", Barco[31]);
        UsuarioNuevo.put("Tiburon", Barco[32]);
        UsuarioNuevo.put("Escama", Barco[32]);
        UsuarioNuevo.put("Sardina", Barco[34]);
        UsuarioNuevo.put("Otros", Barco[35]);
        UsuarioNuevo.put("Litoral", Barco[36]);
        UsuarioNuevo.put("PuertoBase", Barco[37]);
        UsuarioNuevo.put("ResponsableBarco", Barco[38]);
        refBarcos.child(Barco[0]).updateChildren(UsuarioNuevo, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null && ref != null) {
                    Intent i = new Intent(context, ListaBarcos.class);
                    context.startActivity(i);
                    // there was no error and the value is modified
                } else {
                    Toast.makeText(context, "Error al registrarUsuario", Toast.LENGTH_SHORT).show();
                    // there was an error. try to update again
                }
            }
        });


        // there was no error and the value is modified
    }
    public void ObtenDatosBarco(String NombreBarco,Context context,String [] Datos,String[]FechaHora){
        refBarcos.child(NombreBarco).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    String [] DatosBarco = new String[]{
                            NombreBarco,
                            dataSnapshot.child("MatriculaBarco").getValue(String.class),
                            dataSnapshot.child("AñoContr").getValue(String.class),
                            dataSnapshot.child("TonBruto").getValue(String.class),
                            dataSnapshot.child("TonNeto").getValue(String.class),
                            dataSnapshot.child("MaterialCasco").getValue(String.class),
                            dataSnapshot.child("Permisionario").getValue(String.class),
                            dataSnapshot.child("Rlegal").getValue(String.class),
                            dataSnapshot.child("TFijo").getValue(String.class),
                            dataSnapshot.child("Tmovil").getValue(String.class),
                            dataSnapshot.child("Ciudad").getValue(String.class),
                            dataSnapshot.child("Municipio").getValue(String.class),
                            dataSnapshot.child("Colonia").getValue(String.class),
                            dataSnapshot.child("CP").getValue(String.class),
                            dataSnapshot.child("RNPA").getValue(String.class),
                            dataSnapshot.child("EntidadFederativa").getValue(String.class),
                            dataSnapshot.child("CorreoElectronico").getValue(String.class),
                            dataSnapshot.child("TipoPersona").getValue(String.class),
                            dataSnapshot.child("MarcaMotor").getValue(String.class),
                            dataSnapshot.child("PotenciaMotor").getValue(String.class),
                            dataSnapshot.child("SerieMotor").getValue(String.class),
                            dataSnapshot.child("FolioCamaron").getValue(String.class),
                            dataSnapshot.child("FolioTunidos").getValue(String.class),
                            dataSnapshot.child("FolioTiburon").getValue(String.class),
                            dataSnapshot.child("FolioEscamas").getValue(String.class),
                            dataSnapshot.child("FolioSardia").getValue(String.class),
                            dataSnapshot.child("FolioOtros").getValue(String.class),
                            dataSnapshot.child("Comercial").getValue(String.class),
                            dataSnapshot.child("PescaDidactica").getValue(String.class),
                            dataSnapshot.child("Fomento").getValue(String.class),
                            dataSnapshot.child("Camaron").getValue(String.class),
                            dataSnapshot.child("Tunidos").getValue(String.class),
                            dataSnapshot.child("Tiburon").getValue(String.class),
                            dataSnapshot.child("Escama").getValue(String.class),
                            dataSnapshot.child("Sardina").getValue(String.class),
                            dataSnapshot.child("Otros").getValue(String.class),
                            dataSnapshot.child("Litoral").getValue(String.class),
                            dataSnapshot.child("PuertoBase").getValue(String.class),
                            dataSnapshot.child("ResponsableBarco").getValue(String.class),


                    };
                    SharedPreferences sh = getApplicationContext().getSharedPreferences(Datos[3], MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sh.edit();
                    for (int i =0; i<39; i++){
                        myEdit.putString("DatoBarco"+i,DatosBarco[i]);
                        myEdit.commit();
                    }
                    Map<String, Object> UsuarioNuevo = new HashMap<>();
                    UsuarioNuevo.put("Estatus", "En traslado");
                    refTickets.child(Datos[4]).child(Datos[3]).updateChildren(UsuarioNuevo, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error == null && ref != null) {
                                Map<String, Object> UsuarioNuevo = new HashMap<>();
                                UsuarioNuevo.put("Descripcion", "En traslado");
                                UsuarioNuevo.put("Hora", FechaHora[0]);
                                UsuarioNuevo.put("Fecha", FechaHora[1]);
                                UsuarioNuevo.put("Link", "");
                                UsuarioNuevo.put("Modificacion", "false");
                                UsuarioNuevo.put("Actividad", "Actualización de estatus");

                                refTickets.child(Datos[4]).child(Datos[3]).child("Bitacora").child("Accion2").updateChildren(UsuarioNuevo, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        if (error == null && ref != null) {

                                            Intent i = new Intent(context, RInstalacion.class);
                                            SharedPreferences.Editor myEdit = sh.edit();
                                            myEdit.putString("DatosTicket7","En traslado");
                                            myEdit.commit();
                                            i.putExtra("DatosBarco",DatosBarco);
                                            i.putExtra("Datos",Datos);
                                            context.startActivity(i);
                                            // there was no error and the value is modified
                                        } else {
                                            Toast.makeText(context, "Error al registrarUsuario", Toast.LENGTH_SHORT).show();
                                            // there was an error. try to update again
                                        }
                                    }
                                });

                                // there was no error and the value is modified
                            } else {
                                Toast.makeText(context, "Error al registrarUsuario", Toast.LENGTH_SHORT).show();
                                // there was an error. try to update again
                            }
                        }
                    });



                } else {

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void ActualizaEstatus(String []Datos,Context context,String[] FechaHora,String Estatus,int ID){
        Map<String, Object> UsuarioNuevo = new HashMap<>();
        UsuarioNuevo.put("Estatus", Estatus);
        refTickets.child(Datos[4]).child(Datos[3]).updateChildren(UsuarioNuevo, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null && ref != null) {
                    Map<String, Object> UsuarioNuevo = new HashMap<>();
                    UsuarioNuevo.put("Descripcion", Estatus);
                    UsuarioNuevo.put("Hora", FechaHora[0]);
                    UsuarioNuevo.put("Fecha", FechaHora[1]);
                    UsuarioNuevo.put("Link", "");
                    UsuarioNuevo.put("Modificacion", "false");
                    UsuarioNuevo.put("Actividad", "Actualización de estatus");

                    refTickets.child(Datos[4]).child(Datos[3]).child("Bitacora").child("Accion"+ID).updateChildren(UsuarioNuevo, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error == null && ref != null) {
                                SharedPreferences sh = getApplicationContext().getSharedPreferences(Datos[3], MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sh.edit();
                                myEdit.putString("DatosTicket7",Estatus);
                                myEdit.commit();

                                if (Estatus.equals("Cerrado")){
                                    System.out.println("CERRADO");
                                    Intent i = new Intent(context, PDFViewer.class);
                                    i.putExtra("Iden", "RD");
                                    i.putExtra("Datos", Datos);
                                    context.startActivity(i);
                                }
                                else{
                                    System.out.println("NO CERRADO");
                                    Intent i = new Intent(context, ReporteInstalacion.class);
                                    i.putExtra("Datos",Datos);
                                    context.startActivity(i);
                                }


                                // there was no error and the value is modified
                            } else {
                                Toast.makeText(context, "Error al registrarUsuario", Toast.LENGTH_SHORT).show();
                                // there was an error. try to update again
                            }
                        }
                    });

                    // there was no error and the value is modified
                } else {
                    Toast.makeText(context, "Error al registrarUsuario", Toast.LENGTH_SHORT).show();
                    // there was an error. try to update again
                }
            }
        });
    }
    ////AVISOS
    public void MandaAvisos(String title, String message,Context context) {
        refUsuarios.addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList<Barcos> listaActividades = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String Token = postSnapshot.child("token").getValue(String.class);
                    FCMSend.pushNotification(context,
                            Token,
                            title,
                            message);
                    System.out.println("Mandando mensaje");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
