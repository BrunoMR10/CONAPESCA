package com.bmr.conapesca;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Firmas extends AppCompatActivity {
    private SignaturePad mSignaturePad;
    String[] Datos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firmas);
        mSignaturePad = (SignaturePad) findViewById(R.id.signature_pad);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                Datos = extras.getStringArray("Datos");
            }
        } else {
            Datos = (String[]) savedInstanceState.getSerializable("Datos");
        }
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                //Toast.makeText(Firmas.this, "OnStartSigning", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
                //mSaveButton.setEnabled(true);
                //mClearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                //mSaveButton.setEnabled(false);
                //mClearButton.setEnabled(false);
            }
        });
    }

    public void Save(View view) {
        Bitmap signatureBitmap = mSignaturePad.getTransparentSignatureBitmap();
        if (addJpgSignatureToGallery(signatureBitmap)) {
            Toast.makeText(this, "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Unable to store the signature", Toast.LENGTH_SHORT).show();
        }
        mSignaturePad.clear();

        ToInstalacion();


    }
    public void Borrar(View view) {
        mSignaturePad.clear();
    }

    private void ToInstalacion(){
        ActivityCompat.finishAffinity(this);
        Intent intent = new Intent(this,RInstalacion.class);
        startActivity(intent);
    }

    public boolean addJpgSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getDir(Datos[0], Context.MODE_PRIVATE);
            File file = new File(directory, "Firma"+Datos[1]+ ".png");
            Log.d("path", file.toString());
            saveBitmapToJPG(signature, file);
            scanMediaFile(file);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }




    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.setHasAlpha(true);
        newBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        stream.close();
    }
}