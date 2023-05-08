package com.bmr.conapesca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.bmr.conapesca.Adapters.AdapterUsuarios;
import com.bmr.conapesca.Datos.Datos;
import com.bmr.conapesca.Entidades.Users;
import com.bmr.conapesca.Herramientas.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListaUsuarios extends AppCompatActivity implements SearchView.OnQueryTextListener{
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference refUsuarios;
    Datos dt = new Datos();
    Firebase fb = new Firebase();
    String[]Datos;
    AdapterUsuarios adapterUsuarios;
    RecyclerView UsuariosView;
    SearchView BuscarUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);

        BuscarUsuario = findViewById(R.id.BuscaBarcos);
        UsuariosView = (RecyclerView) findViewById(R.id.ListaBarcos);
        UsuariosView.setLayoutManager(new LinearLayoutManager(this));
        refUsuarios = database.getReference("Usuarios");
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                Datos = extras.getStringArray("Datos");
            }
        }
        else {
            Datos = (String[]) savedInstanceState.getSerializable("Datos");
        }
        MuestraUsuarios();
    }
    private void MuestraUsuarios(){
        refUsuarios.addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList<Users> ListaUsuarios = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String Numero = postSnapshot.child("Numero").getValue(String.class);
                    String Correo = postSnapshot.child("CorreoElectronico").getValue(String.class);
                    String Puesto = postSnapshot.child("Puesto").getValue(String.class);
                    String NombreCompleto = postSnapshot.child("NombreCompleto").getValue(String.class);
                    String uid = postSnapshot.getKey();
                    Users users= null;
                    users= new Users();
                    users.setDatos(Datos);
                    users.setCorreo(Correo);
                    users.setNumero(Numero);
                    users.setNombreCompleto(NombreCompleto);
                    users.setPuesto(Puesto);
                    users.setUid(uid);
                    ListaUsuarios.add(users);
                }
                adapterUsuarios = new AdapterUsuarios(ListaUsuarios);
                UsuariosView.setAdapter(adapterUsuarios);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        BuscarUsuario.setOnQueryTextListener(this);
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        adapterUsuarios.Filtrados(newText);
        return false;
    }

}