package com.bmr.conapesca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.bmr.conapesca.Herramientas.FCMSend;
import com.bmr.conapesca.Herramientas.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Notificaciones extends AppCompatActivity {
    private EditText mTittle,mMessage;
    Firebase fb = new Firebase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);
        mTittle = findViewById(R.id.mTittle);
        mMessage = findViewById(R.id.mMessage);

        findViewById(R.id.Send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = mTittle.getText().toString().trim();
                String message = mMessage.getText().toString().trim();
                if (!title.equals("")&&!message.equals("")){
                    /*FCMSend.pushNotification(Notificaciones.this,
                            "c32APgIHQy6V2yfLYSnKqU:APA91bFqy987YtzrqKU1ymKd7k9_Bishu-ubb8BRy80J5QxvdIjRo4kLhXn5TLjo-Qs0gq-58JFEW6Rf3P7NAYfrlKgh9Nwe2VDA448raMXtP_eCN0tWTp_gAkGsTqMwak4bjnPbY6sc",
                            title,
                            message);
                    System.out.println("Mandando mensaje");*/
                    fb.MandaAvisos(title,message,Notificaciones.this);
                }

            }
        });

       /* FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {

                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        System.out.println("Token : "+ token);
                        // Log and toast

                    }
                });*/


    }
}