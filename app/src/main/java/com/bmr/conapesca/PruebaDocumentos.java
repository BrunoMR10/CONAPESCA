package com.bmr.conapesca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.view.View;

import java.io.File;

public class PruebaDocumentos extends AppCompatActivity {
     String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_documentos);
        name ="Prueba"+"("+"Convenio"+").pdf";
        viewPDFFile();
    }

    private void viewPDFFile() {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("Prueba", Context.MODE_PRIVATE);
        //name =Datos[3]+"("+Iden+").pdf";
        File file = new File(directory, name);
        com.github.barteksc.pdfviewer.PDFView pdfView = (com.github.barteksc.pdfviewer.PDFView) findViewById(R.id.pdfView);
        pdfView.fromFile(file).load();
    }
}