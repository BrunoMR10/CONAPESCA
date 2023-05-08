package com.bmr.conapesca.Documentos;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.fonts.Font;
import android.os.Environment;

import com.bmr.conapesca.Datos.Datos;
import com.bmr.conapesca.Entidades.Ticket;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class Instalacion {
    Datos datos = new Datos();
    public void CreaArchivo(String[] Datos, ImageData imageData)throws IOException {
            String Name = Datos[3]+"(RFD).pdf";
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getDir(Datos[3], Context.MODE_PRIVATE);
            File file = new File(directory, Name);
            PdfDocument pdf = new PdfDocument(new PdfWriter(file));
            PdfPage page = pdf.addNewPage();
            PdfCanvas pdfCanvas = new PdfCanvas(page);
            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
            CuadroDatos(Datos,pdfCanvas,font,bold,36, 700, 530, 100,imageData);
            CuadroDatos2(pdfCanvas,bold,36, 615, 530, 50);
            CuadroDatos3(pdfCanvas,bold,36, 515, 530, 50);
            CuadroDatos4(pdfCanvas,bold,36, 355, 530, 110);
            CuadroDatos5(pdfCanvas,bold,36, 195, 530, 110);
            Aceptacion(Datos[3],pdfCanvas,font,bold,36, 140, 530, 15,page);
            AgregaContenidoCuadro1(page,Datos[3]);
            AgregaContenidoCuadro2(page,Datos[3]);
            AgregaContenidoCuadro3(page,Datos[3]);
            AgregaContenidoCuadro4(page,Datos[3]);
            AgregaContenidoCuadro5(page,Datos[3]);
            PdfPage page2=CuadroNextPage(pdf,Datos,font,bold,imageData);
            PdfPage page3=CuadroNextPage(pdf,Datos,font,bold,imageData);
            InsertaSquare(page,Datos[3]);
            AñadirLineas(page);
            InseraImagenes1(page2,Datos[3]);
            InseraImagenes2(page3,Datos[3]);
            InsertaPiedePagina(pdf);
            pdf.close();

    }
    private void CuadroDatos(String[] Datos,PdfCanvas pdfCanvas,PdfFont font,PdfFont bold,int x,int y,int w,int h,ImageData imageData){
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke().closePathStroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text title = new Text("REPORTE FOTOGRÁFICO DE INSTALACIÓN").setFont(bold).setFontColor(ColorConstants.RED).setFontSize(10);
        Text Space = new Text(".  .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);
        Paragraph a = new Paragraph().add(title).add(Space).setTextAlignment(TextAlignment.RIGHT);
        InsertaImagenLogo(imageData,canvas,50,715,190);
        canvas.add(a);
        canvas.close();
    }
    private void InsertaImagenLogo(ImageData imageData,Canvas canvas,int left,int bottom,int width){
        Image img = new Image(imageData,left,bottom,width);
        canvas.add(img).close();
    }
    private void CuadroDatos2(PdfCanvas pdfCanvas,PdfFont bold,int x,int y, int w,int h){
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke().closePathStroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Rectangle rectangle2 = new Rectangle(x, y+h, w, 15);
        pdfCanvas.rectangle(rectangle2);
        pdfCanvas.stroke().closePathStroke();
        Canvas canvas2 = new Canvas(pdfCanvas, rectangle2);
        Text DatosEquipo = new Text("Datos del permisionario / consecionario").setFont(bold).setFontSize(8);
        Paragraph b = new Paragraph().add(DatosEquipo).setTextAlignment(TextAlignment.CENTER);
        canvas2.add(b).close();
        canvas.close();
        canvas2.close();
    }
    private void CuadroDatos3(PdfCanvas pdfCanvas,PdfFont bold,int x,int y, int w,int h){
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke().closePathStroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Rectangle rectangle2 = new Rectangle(x, y+h, w, 15);
        pdfCanvas.rectangle(rectangle2);
        pdfCanvas.stroke().closePathStroke();
        Canvas canvas2 = new Canvas(pdfCanvas, rectangle2);
        Text DatosEquipo = new Text("Caracteristicas de la embarcación").setFont(bold).setFontSize(8);
        Paragraph b = new Paragraph().add(DatosEquipo).setTextAlignment(TextAlignment.CENTER);
        canvas2.add(b).close();
        canvas.close();
        canvas2.close();
    }
    private void CuadroDatos4(PdfCanvas pdfCanvas,PdfFont bold,int x,int y, int w,int h){
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke().closePathStroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Rectangle rectangle2 = new Rectangle(x, y+h, w, 15);
        pdfCanvas.rectangle(rectangle2);
        pdfCanvas.stroke().closePathStroke();
        Canvas canvas2 = new Canvas(pdfCanvas, rectangle2);
        Text DatosEquipo = new Text("Permisos de pesca de la embarcación").setFont(bold).setFontSize(8);
        Paragraph b = new Paragraph().add(DatosEquipo).setTextAlignment(TextAlignment.CENTER);
        canvas2.add(b).close();
        canvas.close();
        canvas2.close();
    }
    private void CuadroDatos5(PdfCanvas pdfCanvas,PdfFont bold,int x,int y, int w,int h){
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke().closePathStroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Rectangle rectangle2 = new Rectangle(x, y+h, w, 15);
        pdfCanvas.rectangle(rectangle2);
        pdfCanvas.stroke().closePathStroke();
        Canvas canvas2 = new Canvas(pdfCanvas, rectangle2);
        Text DatosEquipo = new Text("Equipos instalados").setFont(bold).setFontSize(8);
        Paragraph b = new Paragraph().add(DatosEquipo).setTextAlignment(TextAlignment.CENTER);
        canvas2.add(b).close();
        canvas.close();
        canvas2.close();
    }
    private void AgregaContenidoCuadro1(PdfPage page,String Ticket){
        try {
            SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket,Context.MODE_PRIVATE);

            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
            AgregaContenidoCuadroDatosRIGHT(bold, page, 370, 756, 100, 13, "No. de oficio:",8);
            AgregaContenidoCuadroDatosRIGHT(bold, page, 370, 736, 100, 13,"Fecha de oficio:" ,8);
            AgregaContenidoCuadroDatosRIGHT(bold, page, 350, 716, 120, 13, "Fecha de instalación:",8);

            AgregaContenidoCuadroDatosLEFT(font, page, 475, 756, 100, 13, sh.getString("DatosTicket12",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 475, 736, 100, 13,sh.getString("DatosTicket13",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 475, 716, 120, 13, sh.getString("DatosTicket5",""),8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void AgregaContenidoCuadro2(PdfPage page,String Ticket){
        try {
            SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket,Context.MODE_PRIVATE);
            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
            AgregaContenidoCuadroDatosLEFT(bold, page, 50, 650, 100, 13, "Nombre del permisionario:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 50, 635, 100, 13,"Razón social:" ,8);
            AgregaContenidoCuadroDatosRIGHT(bold, page, 300, 635, 70, 13,"R.N.P.A. Titular:" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 50, 620, 120, 13, "Responsable de la embarcación:",8);

            AgregaContenidoCuadroDatosLEFT(font, page, 170, 650, 100, 13, sh.getString("DatoBarco6",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 170, 635, 100, 13,sh.getString("DatoBarco7","") ,8);
            AgregaContenidoCuadroDatosLEFT(font, page, 375, 635, 100, 13,sh.getString("DatoBarco14","") ,8);
            AgregaContenidoCuadroDatosLEFT(font, page, 170, 620, 120, 13, sh.getString("DatoBarco38",""),8);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void AgregaContenidoCuadro3(PdfPage page,String Ticket){
        try {
            SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket,Context.MODE_PRIVATE);
            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
            AgregaContenidoCuadroDatosLEFT(bold, page, 50, 550, 100, 13, "Nombre de la embarcación:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 50, 535, 100, 13,"Puerto base:" ,8);
            AgregaContenidoCuadroDatosRIGHT(bold, page, 320, 535, 50, 13,"Litoral:" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 50, 520, 120, 13, "Matricula:",8);

            AgregaContenidoCuadroDatosLEFT(font, page, 170, 550, 100, 13, sh.getString("DatoBarco0",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 170, 535, 100, 13,sh.getString("DatoBarco37","") ,8);
            AgregaContenidoCuadroDatosLEFT(font, page, 375, 535, 100, 13,sh.getString("DatoBarco36","") ,8);
            AgregaContenidoCuadroDatosLEFT(font, page, 170, 520, 120, 13, sh.getString("DatoBarco1",""),8);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void AgregaContenidoCuadro4(PdfPage page,String Ticket){
        try {
            SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket,Context.MODE_PRIVATE);
            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
            AgregaContenidoCuadroDatosLEFT(bold, page, 50, 450, 100, 13, "Tipo de permiso:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 150, 450, 100, 13,"Comercial" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 250, 450, 100, 13,"Pesca Didáctica" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 350, 450, 120, 13, "Fomento",8);

            AgregaContenidoCuadroDatosLEFT(bold, page, 50, 435, 100, 13, "Camarón",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 50, 420, 100, 13,"Tunidos" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 50, 405 ,100, 13,"Tiburón y Raya" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 50, 390, 120, 13, "Escama",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 50, 375 ,100, 13,"Sardina" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 50, 360, 120, 13, "Otros",8);

            AgregaContenidoCuadroDatosLEFT(bold, page, 150, 435, 50, 13, "No. Folio:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 150, 420, 50, 13,"No. Folio:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 150, 405 ,50, 13,"No. Folio:" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 150, 390, 50, 13, "No. Folio:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 150, 375 ,50, 13,"No. Folio:" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 150, 360, 50, 13, "No. Folio:",8);

            AgregaContenidoCuadroDatosLEFT(font, page, 200, 435, 100, 13, sh.getString("DatoBarco21",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 200, 420, 100, 13,sh.getString("DatoBarco22",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 200, 405 ,100, 13,sh.getString("DatoBarco23","") ,8);
            AgregaContenidoCuadroDatosLEFT(font, page, 200, 390, 120, 13, sh.getString("DatoBarco24",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 200, 375 ,100, 13,sh.getString("DatoBarco25",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 200, 360, 120, 13, sh.getString("DatoBarco26",""),8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void AgregaContenidoCuadro5(PdfPage page,String Ticket){
        try {
            SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket,Context.MODE_PRIVATE);
            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
            AgregaContenidoCuadroDatosLEFT(bold, page, 150, 285, 100, 13, "Iridium Satelite LLC" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 300, 285, 100, 13,"Transreceptor" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 450, 285, 100, 13,"ConBox" ,8);

            AgregaContenidoCuadroDatosLEFT(bold, page, 50, 270, 100, 13, "No.Parte",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 50, 255, 100, 13,"No.Serie" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 50, 240 ,100, 13,"IMEI" ,8);

            AgregaContenidoCuadroDatosLEFT(bold, page, 225, 225, 100, 13, "Sello Transreceptor" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 375, 225, 100, 13,"Sello ConBox" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 50, 205 ,100, 13,"No.Sello" ,8);


            AgregaContenidoCuadroDatosLEFT(font, page, 150, 270, 100, 13, sh.getString("NParteIridium",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 150, 255, 100, 13,sh.getString("NSerieIridium","") ,8);
            AgregaContenidoCuadroDatosLEFT(font, page, 150, 240 ,100, 13,sh.getString("IMEIIridium","") ,8);

            AgregaContenidoCuadroDatosLEFT(font, page, 300, 270, 100, 13, sh.getString("NParteTransreceptor",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 300, 255, 100, 13,sh.getString("NSerieTransreceptor","") ,8);

            AgregaContenidoCuadroDatosLEFT(font, page, 450, 270, 100, 13, sh.getString("NParteConBox",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 450, 255, 100, 13,sh.getString("NSerieConBox","") ,8);

            AgregaContenidoCuadroDatosLEFT(font, page, 225, 205, 100, 13, sh.getString("SelloTrans",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 375, 205, 100, 13,sh.getString("SelloConBox","") ,8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void InsertaSquare(PdfPage page,String Ticket){
        SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket,Context.MODE_PRIVATE);
        if (sh.getString("DatoBarco27","").equals("true"))squarelleno(page, 452, 200);
        else square(page, 459, 200);
        if (sh.getString("DatoBarco28","").equals("true"))squarelleno(page, 452, 315);
        else square(page, 459, 315);
        if (sh.getString("DatoBarco29","").equals("true"))squarelleno(page, 452, 400);
        else square(page, 459, 400);

        if (sh.getString("DatoBarco30","").equals("true"))squarelleno(page, 437, 120);
        else square(page, 444, 120);
        if (sh.getString("DatoBarco31","").equals("true"))squarelleno(page, 422, 120);
        else square(page, 429, 120);
        if (sh.getString("DatoBarco32","").equals("true"))squarelleno(page, 407, 120);
        else square(page, 414, 120);
        if (sh.getString("DatoBarco33","").equals("true"))squarelleno(page, 392, 120);
        else square(page, 399, 120);
        if (sh.getString("DatoBarco32","").equals("true"))squarelleno(page, 377, 120);
        else   square(page, 384, 120);
        if (sh.getString("DatoBarco33","").equals("true"))squarelleno(page, 362, 120);
        else  square(page, 369, 120);

    }
    private void AgregaContenidoCuadroDatosLEFT(PdfFont font,PdfPage page,int x, int y,int w,int h,String Comentario, int tamaño){
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text Comen1 = new Text(Comentario.replaceAll("(\n|\r)", " ")).setFont(font).setFontSize(tamaño);
        Paragraph a = new Paragraph().add(Comen1)
                .setTextAlignment(TextAlignment.LEFT);
        canvas.add(a).close();
        canvas.close();
        pdfCanvas.setColor(ColorConstants.WHITE,false).stroke().closePathStroke();
    }
    private void AgregaContenidoCuadroDatosRIGHT(PdfFont font,PdfPage page,int x, int y,int w,int h,String Comentario, int tamaño){
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text Comen1 = new Text(Comentario.replaceAll("(\n|\r)", " ")).setFont(font).setFontSize(tamaño);
        Paragraph a = new Paragraph().add(Comen1)
                .setTextAlignment(TextAlignment.RIGHT);
        canvas.add(a).close();
        canvas.close();
        pdfCanvas.setColor(ColorConstants.WHITE,false).stroke().closePathStroke();
    }
    private void Aceptacion(String Ticket,PdfCanvas pdfCanvas,PdfFont font,PdfFont bold,int x,int y, int w,int h,PdfPage page){
        SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket,Context.MODE_PRIVATE);
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();

        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text DatosEquipo = new Text("Aceptación del servicio").setFont(bold).setFontSize(9);
        Paragraph a = new Paragraph().add(DatosEquipo).setTextAlignment(TextAlignment.CENTER);
        canvas.add(a);

        Text Space = new Text(".  .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);

        Rectangle rectangle2 = new Rectangle(x, y-h, (w/2), h);
        pdfCanvas.rectangle(rectangle2);
        pdfCanvas.stroke();
        Canvas canvas2 = new Canvas(pdfCanvas, rectangle2);
        Text SeguritechR = new Text("Responsable por parte de Seguritech").setFont(bold).setFontSize(9);
        Paragraph b = new Paragraph().add(SeguritechR).setTextAlignment(TextAlignment.CENTER);
        canvas2.add(b);

        Rectangle rectangle3 = new Rectangle(x+(w/2), y-h, (w/2), h);
        pdfCanvas.rectangle(rectangle3);
        pdfCanvas.stroke();
        Canvas canvas3 = new Canvas(pdfCanvas, rectangle3);
        Text SeguritechA = new Text("Responsable de la embarcación").setFont(bold).setFontSize(9);
        Paragraph c = new Paragraph().add(SeguritechA).setTextAlignment(TextAlignment.CENTER);
        canvas3.add(c);


        Rectangle rectangle4 = new Rectangle(x, y-h-h, 40, h);
        pdfCanvas.rectangle(rectangle4);
        pdfCanvas.stroke();
        Canvas canvas4 = new Canvas(pdfCanvas, rectangle4);
        Text Nombre = new Text(" Nombre:").setFont(bold).setFontSize(9);
        Paragraph d = new Paragraph().add(Nombre).setTextAlignment(TextAlignment.CENTER);
        canvas4.add(d);

        Rectangle rectangle5 = new Rectangle(x+40, y-h-h, (w/2)-40, h);
        pdfCanvas.rectangle(rectangle5);
        pdfCanvas.stroke();
        Canvas canvas5 = new Canvas(pdfCanvas, rectangle5);
        Text NombreSeguritech = new Text(sh.getString("DatosTicket1","")).setFont(font).setFontSize(9);
        Paragraph e = new Paragraph().add(Space).add(NombreSeguritech).setTextAlignment(TextAlignment.LEFT);
        canvas5.add(e);

        Rectangle rectangle6 = new Rectangle(x+(w/2), y-h-h, 40, h);
        pdfCanvas.rectangle(rectangle6);
        pdfCanvas.stroke();
        Canvas canvas6 = new Canvas(pdfCanvas, rectangle6);
        Paragraph f = new Paragraph().add(Nombre).setTextAlignment(TextAlignment.CENTER);
        canvas6.add(f);

        Rectangle rectangle7 = new Rectangle(x+(w/2)+40, y-h-h, (w/2)-40, h);
        pdfCanvas.rectangle(rectangle7);
        pdfCanvas.stroke();
        Canvas canvas7 = new Canvas(pdfCanvas, rectangle7);
        Text NombreANAM = new Text(sh.getString("DatoBarco38","")).setFont(font).setFontSize(9);
        Paragraph g = new Paragraph().add(Space).add(NombreANAM).setTextAlignment(TextAlignment.LEFT);
        canvas7.add(g);


        rectangle4 = new Rectangle(x, y-h-h-h, 40, h);
        pdfCanvas.rectangle(rectangle4);
        pdfCanvas.stroke();
        canvas4 = new Canvas(pdfCanvas, rectangle4);
        Nombre = new Text(" Cargo:").setFont(bold).setFontSize(9);
        d = new Paragraph().add(Nombre).setTextAlignment(TextAlignment.CENTER);
        canvas4.add(d);

        rectangle5 = new Rectangle(x+40, y-h-h-h, (w/2)-40, h);
        pdfCanvas.rectangle(rectangle5);
        pdfCanvas.stroke();
        canvas5 = new Canvas(pdfCanvas, rectangle5);
        NombreSeguritech = new Text(sh.getString("DatosTicket2","")).setFont(font).setFontSize(9);
        e = new Paragraph().add(Space).add(NombreSeguritech).setTextAlignment(TextAlignment.LEFT);
        canvas5.add(e);

        PdfCanvas pdfCanvas50 = new PdfCanvas(page);
        Rectangle rectangle50 = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle50);
        Canvas canvas50 = new Canvas(pdfCanvas50, rectangle50);
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(Ticket, Context.MODE_PRIVATE);
        File file = new File(directory, "FirmaBarco.png");
        ImageData data = null;
        try {
            data = ImageDataFactory.create(file.getAbsolutePath());
        } catch (MalformedURLException ez) {
            ez.printStackTrace();
        }

        Image img = new Image(data,x+(w/2)+40,y-h-h-h-h-h-h,(w/2)-40);
        //img.setHeight(h);
        canvas50.add(img).close();
        canvas50.close();

        PdfCanvas pdfCanvas60 = new PdfCanvas(page);
        Rectangle rectangle60 = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle60);
        Canvas canvas60 = new Canvas(pdfCanvas60, rectangle60);
        cw = new ContextWrapper(getApplicationContext());
        directory = cw.getDir(Ticket, Context.MODE_PRIVATE);
        file = new File(directory, "FirmaSeguritech.png");
        data = null;
        try {
            data = ImageDataFactory.create(file.getAbsolutePath());
        } catch (MalformedURLException ez) {
            ez.printStackTrace();
        }

        img = new Image(data,x+40,y-h-h-h-h-h-h,(w/2)-40);
        //img.setHeight(h);
        canvas60.add(img).close();
        canvas60.close();

        rectangle6 = new Rectangle(x+(w/2), y-h-h-h, 40, h);
        pdfCanvas.rectangle(rectangle6);
        pdfCanvas.stroke();
        canvas6 = new Canvas(pdfCanvas, rectangle6);
        f = new Paragraph().add(Nombre).setTextAlignment(TextAlignment.CENTER);
        canvas6.add(f);

        rectangle7 = new Rectangle(x+(w/2)+40, y-h-h-h, (w/2)-40, h);
        pdfCanvas.rectangle(rectangle7);
        pdfCanvas.stroke();
        canvas7 = new Canvas(pdfCanvas, rectangle7);
        NombreANAM = new Text(" ").setFont(font).setFontSize(9);
        g = new Paragraph().add(Space).add(NombreANAM).setTextAlignment(TextAlignment.LEFT);
        canvas7.add(g);

        rectangle4 = new Rectangle(x, y-h-h-h-h-5, 40, h+5);
        pdfCanvas.rectangle(rectangle4);
        pdfCanvas.stroke();
        canvas4 = new Canvas(pdfCanvas, rectangle4);
        Nombre = new Text(" Firma:").setFont(bold).setFontSize(9);
        d = new Paragraph().add(Nombre).setTextAlignment(TextAlignment.CENTER);
        canvas4.add(d);

        rectangle5 = new Rectangle(x+40, y-h-h-h-h-5, (w/2)-40, h+5);
        pdfCanvas.rectangle(rectangle5);
        pdfCanvas.stroke();
        canvas5 = new Canvas(pdfCanvas, rectangle5);
        e = new Paragraph().add(Space).add("").setTextAlignment(TextAlignment.LEFT);
        canvas5.add(e);

        rectangle6 = new Rectangle(x+(w/2), y-h-h-h-h-5, 40, h+5);
        pdfCanvas.rectangle(rectangle6);
        pdfCanvas.stroke();
        canvas6 = new Canvas(pdfCanvas, rectangle6);
        f = new Paragraph().add(Nombre).setTextAlignment(TextAlignment.CENTER);
        canvas6.add(f);

        rectangle7 = new Rectangle(x+(w/2)+40, y-h-h-h-h-5, (w/2)-40, h+5);
        pdfCanvas.rectangle(rectangle7);
        pdfCanvas.stroke();
        canvas7 = new Canvas(pdfCanvas, rectangle7);
        g = new Paragraph().add(Space).add("").setTextAlignment(TextAlignment.LEFT);
        canvas7.add(g);


        canvas.close();
        canvas2.close();


    }
    private PdfPage CuadroNextPage(PdfDocument pdf,String [] Datos,PdfFont font,PdfFont bold,ImageData imageData){
        PdfPage nextpage = pdf.addNewPage();
        PdfCanvas pdfCanvas = new PdfCanvas(nextpage);
        Rectangle rectangle = new Rectangle(36, 700, 530, 100);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text NReporte = new Text("No de reporte:").setFont(font).setFontSize(9);
        Text title = new Text("REPORTE DE INSTALACIÓN").setFont(bold).setFontColor(ColorConstants.RED).setFontSize(10);
        Text TICKET = new Text("").setFont(font).setUnderline().setFontSize(9);
        Text Space = new Text(".  .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);
        Paragraph a = new Paragraph().add(title).add(Space).setTextAlignment(TextAlignment.RIGHT);
        Paragraph b = new Paragraph().add(NReporte).add(Space).add(TICKET).add(Space).add(Space).add(Space).setTextAlignment(TextAlignment.RIGHT);
        //InsertaImagen("FotosBase/", "seguritechlogo.png",canvas,50,715,190,142);
        InsertaImagenLogo(imageData,canvas,50,715,190);
        canvas.add(a);
        canvas.add(b);
        canvas.close();
        return (nextpage);
    }
    private void InseraImagenes1 (PdfPage page,String Ticket){
        PdfFont font = null;
        try {font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        InsertImagen (page, 50,450,230,172,Ticket,"Foto1.png");
        InsertImagen (page, 320,450,230,172,Ticket,"Foto2.png");
        InsertImagen (page, 50,160,230,172,Ticket,"Foto3.png");
        InsertImagen (page, 320,160,230,172,Ticket,"Foto4.png");
        InsertaBordeImagen(page, 50,450,230,172);
        InsertaBordeImagen(page, 320,450,230,172);
        InsertaBordeImagen(page, 50,160,230,172);
        InsertaBordeImagen(page, 320,160,230,172);
        AgregaComentarios(font,page,50, 399, 232, 50,datos.FotosReporteInstalacion[0]);
        AgregaComentarios(font,page,320,399,230,50,datos.FotosReporteInstalacion[1]);
        AgregaComentarios(font,page,50,99,230,50,datos.FotosReporteInstalacion[2]);
        AgregaComentarios(font,page,320,99,230,50,datos.FotosReporteInstalacion[3]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void InseraImagenes2 (PdfPage page,String Ticket){
        PdfFont font = null;
        try {
            font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            InsertImagen (page, 59, 509,212,144,Ticket,"Foto5.png");
            InsertImagen (page, 329, 509,212,144,Ticket,"Foto6.png");
            InsertImagen (page, 59, 309,212,144,Ticket,"Foto7.png");
            InsertImagen (page, 329, 309,212,144,Ticket,"Foto8.png");
            InsertImagen (page, 59, 109,212,144,Ticket,"Foto9.png");
            InsertImagen(page, 329, 109,212,144,Ticket,"Foto10.png");
            InsertaBordeImagen(page, 59, 509,212,144);
            InsertaBordeImagen(page, 329, 509,212,144);
            InsertaBordeImagen(page, 59, 309,212,144);
            InsertaBordeImagen(page, 329, 309,212,144);
            InsertaBordeImagen(page, 59, 109,212,144);
            InsertaBordeImagen(page, 329, 109,212,144);
            AgregaComentarios(font,page,60, 460, 212, 50,datos.FotosReporteInstalacion[4]);
            AgregaComentarios(font,page,330, 460, 212, 50,datos.FotosReporteInstalacion[5]);
            AgregaComentarios(font,page,60, 260, 212, 50,datos.FotosReporteInstalacion[6]);
            AgregaComentarios(font,page,330, 260, 212, 50,datos.FotosReporteInstalacion[7]);
            AgregaComentarios(font,page,60, 60, 212, 50,datos.FotosReporteInstalacion[8]);
            AgregaComentarios(font,page,330, 60, 212, 50,datos.FotosReporteInstalacion[9]);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void InsertaBordeImagen(PdfPage page, int x, int y,int w,int h){
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.setLineWidth(0).setColor(ColorConstants.BLACK,false).stroke().closePathStroke();
    }
    private void AgregaComentarios(PdfFont font,PdfPage page,int x, int y,int w,int h,String Comentario){
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle).setColor(ColorConstants.WHITE,false);
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text Comen1 = new Text(Comentario).setFont(font);
        Paragraph a = new Paragraph().add(Comen1)
                .setTextAlignment(TextAlignment.CENTER);
        pdfCanvas.stroke().closePathStroke();
        canvas.add(a).close();
        canvas.close();
    }
    private void square(PdfPage page,int y,int x){
        PdfCanvas canvas = new PdfCanvas(page);

        // Create a 100% Magenta color

        canvas

                .moveTo(x, y) //y 643
                .lineTo(x-7, y) //y-4

                .moveTo(x-7, y) //y 643
                .lineTo(x-7, y-7) //y-4

                .moveTo(x-7, y-7) //y-4
                .lineTo(x, y-7) //y+3

                .moveTo(x, y-7) //y-4
                .lineTo(x, y) //y+3
                .setColor(ColorConstants.BLACK,false).setLineWidth(1).closePathStroke();
    }
    private void squarelleno(PdfPage page,int y,int x) {

        PdfCanvas canvas = new PdfCanvas(page);

        Rectangle rectangle = new Rectangle(x, y, 7, 7);
        canvas.setFillColor(ColorConstants.GRAY);
        canvas.rectangle(rectangle).setColor(ColorConstants.BLACK,false).setLineWidth(1);
        canvas.fillStroke();
        // Create a 100% Magenta color
        y=y+4;
        canvas.closePathFillStroke();

    }
    private void InsertaPiedePagina(PdfDocument pdf ){
        int numberOfPages = pdf.getNumberOfPages();
        for (int i = 1; i <= numberOfPages; i++) {
            Document doc = new Document(pdf);
            // Write aligned text to the specified by parameters point
            doc.showTextAligned(new Paragraph(String.format("Página %s de %s", i, numberOfPages)),
                    559, 50, i, TextAlignment.RIGHT, VerticalAlignment.TOP, 0);
        }
    }
    private void AñadirLineas(PdfPage page){
        PdfCanvas canvas = new PdfCanvas(page);
        // Create a 100% Magenta color
        canvas
                .moveTo(100, 270)
                .lineTo(565, 270)
                .moveTo(100, 256)
                .lineTo(565, 256)
                .moveTo(100, 242)
                .lineTo(565, 242)
                .moveTo(100, 203)
                .lineTo(565, 203)

                .moveTo(190, 436)
                .lineTo(565, 436)
                .moveTo(190, 421)
                .lineTo(565, 421)
                .moveTo(190, 406)
                .lineTo(565, 406)
                .moveTo(190, 391)
                .lineTo(565, 391)
                .moveTo(190, 376)
                .lineTo(565, 376)
                .moveTo(190, 361)
                .lineTo(565, 361)

                .moveTo(155, 550)
                .lineTo(565, 550)
                .moveTo(155, 535)
                .lineTo(565, 535)
                .moveTo(155, 521)
                .lineTo(565, 521)

                .moveTo(159, 650)
                .lineTo(565, 650)
                .moveTo(159, 635)
                .lineTo(565, 635)
                .moveTo(159, 621)
                .lineTo(565, 621)

                .moveTo(475, 717)
                .lineTo(540, 717)
                .moveTo(475, 737)
                .lineTo(540, 737)
                .moveTo(475, 756)
                .lineTo(540, 756)
                .setLineWidth(0).closePathStroke();
    }
    private void InsertImagen(PdfPage page, int x, int y,int w,int h,String rutacarpeta,String nombre){
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(rutacarpeta, Context.MODE_PRIVATE);
        File file = new File(directory, nombre);
        ImageData data = null;
        try {
            data = ImageDataFactory.create(file.getAbsolutePath());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Image img = new Image(data,x+1,y+1,w-2);
        img.setHeight(h-2);
        canvas.add(img).close();
        canvas.close();
    }

}
