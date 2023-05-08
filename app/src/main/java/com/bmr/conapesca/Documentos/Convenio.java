package com.bmr.conapesca.Documentos;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.bmr.conapesca.R;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

public class Convenio {
    int widthcartaoficio = 612;
    int hightcarta = 792;
    int hightoficio = 1008;
    Rectangle TamañoCarta = new Rectangle(widthcartaoficio, hightcarta);
    public void CuerpoDocumento(String Ticket) throws IOException {
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
        PdfFont italic= PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC);

        //String Name = Ticket+"(Convenio).pdf";
        String Name = Ticket+"(Convenio).pdf";
        @SuppressLint("RestrictedApi") ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(Ticket, Context.MODE_PRIVATE);
        File file = new File(directory, Name);
        PdfDocument pdf = new PdfDocument(new PdfWriter(file));
        pdf.setDefaultPageSize(new PageSize(TamañoCarta));
        PdfPage page = pdf.addNewPage();
        InsertaImagen(page,"Logo","Logo",(widthcartaoficio/2)-(300/2),hightcarta-100,300,100,false);

        pdf.close();
    }
    private void InsertaTextoCenter(PdfPage page, String text,int x,int y, int w, int h,boolean negrita,boolean italic,boolean marco,int size) throws IOException {
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        PdfFont font;
        if (negrita && !italic) font = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
        else if (!negrita && italic) font = PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC);
        else if (negrita && italic) font = PdfFontFactory.createFont(StandardFonts.TIMES_BOLDITALIC);
        else font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        if (marco) pdfCanvas.setLineWidth(0).setColor(ColorConstants.BLACK,false).stroke().closePathStroke();
        Text Comen1 = new Text(text).setFont(font).setFontSize(size);
        Paragraph a = new Paragraph().add(Comen1).setTextAlignment(TextAlignment.CENTER);
        pdfCanvas.stroke().closePathStroke();
        canvas.add(a).close();
        canvas.close();
    }

    private void InsertaImagen(PdfPage page,String rutacarpeta,String nombre,int x,int y,int w, int h,boolean Marco){
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Image image;
        if (Marco) pdfCanvas.setLineWidth(0).setColor(ColorConstants.BLACK,false).stroke().closePathStroke();
        if (nombre.equals("Logo")){image = new Image(Logo(),x,y,w);  image.setHeight(h);}
        else {image = new Image(Foto(rutacarpeta,nombre),x,y,w);  image.setHeight(h);}
        canvas.add(image).close();
        canvas.close();
    }
    private ImageData Foto (String rutacarpeta,String nombre){
        ImageData data = null;
        @SuppressLint("RestrictedApi") ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(rutacarpeta, Context.MODE_PRIVATE);
        File file = new File(directory, nombre);
        try {
            data = ImageDataFactory.create(file.getAbsolutePath());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return data;
    }
    private ImageData Logo(){
        ImageData imageData = null;
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.seguritech);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapData = stream.toByteArray();
        imageData = ImageDataFactory.create(bitmapData);
        return imageData;
    }



}
