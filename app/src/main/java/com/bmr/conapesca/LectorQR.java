package com.bmr.conapesca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class LectorQR extends AppCompatActivity {
    Button btn_Scan;
    EditText Resultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lector_qr);
        btn_Scan = (Button) findViewById(R.id.btn_Scan);
        Resultado = (EditText) findViewById(R.id.Resultado);
    }

    public void Escaneo (View view){
        IntentIntegrator intentIntegrator = new IntentIntegrator(LectorQR.this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Lector - CDP");
        intentIntegrator.setCameraId(0);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.initiateScan();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(this,"Cancelado",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,result.getContents(),Toast.LENGTH_SHORT).show();
                Resultado.setText(result.getContents());
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}