package com.bmr.conapesca.Documentos;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import com.bmr.conapesca.Datos.Datos;
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
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class InstalacionOficialCarta {
    Datos datos = new Datos();
    /////ARCHIVO DE INSTALACION
    public void CreaArchivo(String Ticket, ImageData imageData)throws IOException {
        String Name = Ticket+"(RD).pdf";
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(Ticket, Context.MODE_PRIVATE);
        File file = new File(directory, Name);
        Rectangle TamañoOficio = new Rectangle(612, 1008);
        PageSize Oficio = new PageSize(TamañoOficio);
        PdfDocument pdf = new PdfDocument(new PdfWriter(file));
        //pdf.setDefaultPageSize(Oficio);
        PdfPage page = pdf.addNewPage();
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
        PdfFont italix = PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC);
        AñadirLineas(page,Ticket);
        InsertaSquare(page,Ticket);
        CuadroDatos(pdfCanvas,font,bold,10, 680, 570, 140,imageData);
        CuadroDatos2(pdfCanvas,bold,10, 655-70, 570, 70);
        CuadroDatos3(pdfCanvas,bold,10, 640-70-83, 570, 83);
        CuadroDatos4(pdfCanvas,bold,10, 625-70-83-60, 570, 60);
        CuadroDatos5(pdfCanvas,bold,10, 610-70-83-60-95, 570, 95);//36, 305, 530, 95
        CuadroDatos6(pdfCanvas,bold,10, 595-70-83-60-95-110, 570, 110);//36, 155, 530, 100

        Aceptacion(Ticket,pdfCanvas,font,bold,10, 90+60, 570, 15,page);
        InsertaPie(pdf);
        AgregaContenidoCuadroDatosJUS(italix, page,36, 30, 530, 50, "*SEGURITECH PRIVADA, S.A. DE C.V. se obliga a no divulgar, transmitir, reproducir, relevar o difundir a terceros la información que se genere a través de este acto y que sea considerada como “Información Reservada” o como “Información Confidencial” en términos de lo dispuesto por la Ley General de Transparencia y Acceso a la Información Pública. De igual modo, se obliga a proteger los “Datos Personales” de particulares que se recaben a propósito de este acto y a darle el tratamiento que corresponda de conformidad con la Ley Federal de Protección de Datos Personales en Posesión de Particulares*",7);
        AgregaContenidoCuadro1(page,Ticket);
        AgregaContenidoCuadro2(page,Ticket);
        AgregaContenidoCuadro3(page,Ticket);
        AgregaContenidoCuadro4(page,Ticket);
        AgregaContenidoCuadro5(page,Ticket);
        AgregaContenidoCuadro6(page,Ticket);
        pdf.close();
    }
    private void InsertaPie(PdfDocument pdf ){
        PdfFont font = null;
        try {
            font = PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PdfFont bold = null;
        try {
            bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int numberOfPages = pdf.getNumberOfPages();
        for (int i = 1; i <= numberOfPages; i++) {
            Document doc = new Document(pdf);
            Text Piepagina = new Text("Tel. 5550830000                   Seguritech Privada S.A de C.V.            www.seguritech.com").setFont(font).setFontSize(8);
            // Write aligned text to the specified by parameters point
            doc.showTextAligned(new Paragraph(Piepagina),
                    300, 30, i, TextAlignment.CENTER, VerticalAlignment.TOP, 0);
        }
    }
    private void CuadroDatos(PdfCanvas pdfCanvas,PdfFont font,PdfFont bold,int x,int y,int w,int h,ImageData imageData){
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke().closePathStroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text title = new Text("FORMATO DE INSTALACIÓN DE EQUIPO TRANSRECEPTOR").setFont(bold).setFontColor(ColorConstants.BLUE).setFontSize(10);
        Text title2 = new Text("").setFont(font).setFontColor(ColorConstants.BLACK).setFontSize(10);
        Text Space = new Text(".          .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);
        Paragraph a = new Paragraph().add(title).add(Space).setTextAlignment(TextAlignment.RIGHT);
        InsertaImagenLogo(imageData,canvas,50,700+20,190);
        canvas.add(a);
        canvas.close();
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
        Text DatosEquipo = new Text("Datos del permisionario / concesionario").setFont(bold).setFontSize(8);
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
        Text DatosEquipo = new Text("Datos de la persona designada para atención 24 horas del equipo transreceptor").setFont(bold).setFontSize(8);
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
        Text DatosEquipo = new Text("Datos de la embarcación").setFont(bold).setFontSize(8);
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
        Text DatosEquipo = new Text("Permisos de pesca de la embarcación").setFont(bold).setFontSize(8);
        Paragraph b = new Paragraph().add(DatosEquipo).setTextAlignment(TextAlignment.CENTER);
        canvas2.add(b).close();
        canvas.close();
        canvas2.close();
    }
    private void CuadroDatos6(PdfCanvas pdfCanvas,PdfFont bold,int x,int y, int w,int h){
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke().closePathStroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Rectangle rectangle2 = new Rectangle(x, y+h, w, 15);
        pdfCanvas.rectangle(rectangle2);
        pdfCanvas.stroke().closePathStroke();
        Canvas canvas2 = new Canvas(pdfCanvas, rectangle2);
        Text DatosEquipo = new Text("Instalación equipo transreceptor").setFont(bold).setFontSize(8);
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
            AgregaContenidoCuadroDatosRIGHT(bold, page, 240, 726+95, 325, 13, Ticket,10);
            //AgregaContenidoCuadroDatosRIGHT(font, page, 240, 726+200, 310, 13, sh.getString("DatosTicket3",""),9);
            AgregaContenidoCuadroDatosCENTER(font, page, 240, 726+20, 310, 45,
                    "SERVICIO INTEGRAL DE TELECOMUNICACIÓN PARA LA LOCALIZACIÓN Y MONITOREO SATELITAL DE " +
                            "EMBARCACIONES PESQUERAS \n                           " +
                            "CONTRATO  LPIE/006/12/22",8);
            AgregaContenidoCuadroDatosRIGHT(bold, page, 240, 713+20, 200, 13,"No. de aprobación de instalación de la CONAPESCA:" ,8);
            AgregaContenidoCuadroDatosRIGHT(bold, page, 240, 700+20, 200, 13, "Localidad o puerto de instalación:",8);
            AgregaContenidoCuadroDatosRIGHT(bold, page, 240, 687+20, 200, 13, "Fecha de Instalación (DD/MM/AAAA):",8);
            AgregaContenidoCuadroDatosCENTER(font, page, 40, 670+20, 510, 13,"Aplicable a embarcaciones que se ubican en el segundo párrafo de Art. 125" +
                    " de la ley General de Pesca y Acuacultura Sustentables; y Apartado 1.2 de la NOM-062PESC-2014" ,7);

            AgregaContenidoCuadroDatosLEFT(font, page, 445, 713+20, 100, 13, "DGIV.-"+sh.getString("DatosTicket12",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 445, 700+20, 100, 13, sh.getString("DatoBarco25",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 445, 687+20, 100, 13, sh.getString("FechaCerrado",""),8);

            //AgregaContenidoCuadroDatosLEFT(font, page, 445, 713, 100, 13, " Aqui va el número",8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 445, 700, 100, 13, " Aqui va la localidad",8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 445, 687, 100, 13, " 00-00-0000",8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 475, 736, 100, 13,sh.getString("DatosTicket13",""),8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 475, 716, 120, 13, sh.getString("DatosTicket5",""),8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void AgregaContenidoCuadro2(PdfPage page,String Ticket){
        try {
            SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket,Context.MODE_PRIVATE);

            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
            PdfFont italic = PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC);

            AgregaContenidoCuadroDatosLEFT(bold, page, 20, 620+20, 140, 13,"Titular del permisionario/concesión:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 390, 620+20, 70, 13,"Persona Física",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 490, 620+20, 70, 13,"Persona Moral",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 390, 607+20, 70, 13,"R.N.P.A. Titular:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 20, 607+20, 140, 13,"Representante Legal (Persona Moral):",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 20, 594+20, 75, 13,"Correo electrónico:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 260, 594+20, 55, 13,"Teléfono Fijo:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 390, 594+20, 60, 13,"Teléfono Móvil:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 20, 581+20, 75, 13,"Domicilio:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 115, 568+20, 445, 13,"Calle y No.                Colonia                Municipio                Ciudad o Puerto                Entidad Federativa                Código Postal",8);

            AgregaContenidoCuadroDatosLEFT(font, page, 160, 620+20, 220, 13, sh.getString("DatoBarco21",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 160, 607+20, 220, 13, sh.getString("DatoBarco28",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,460, 607+20, 100, 13, sh.getString("DatoBarco20",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,95, 594+20, 165, 13, sh.getString("DatoBarco29",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,315, 594+20, 75, 13, sh.getString("DatoBarco27",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,460, 594+20, 100, 13, "",8);
            AgregaContenidoCuadroDatosLEFT(font, page,95, 581+20, 465, 13, sh.getString("DatoBarco26",""),8);
            /*AgregaContenidoCuadroDatosLEFT(font, page, 180, 620, 200, 13, "Aqui va el nombre del permisionario/Conseción.",8);
            AgregaContenidoCuadroDatosLEFT(font, page, 180, 607, 200, 13, "Aqui va el representante legal (persona moral).",8);
            AgregaContenidoCuadroDatosLEFT(font, page,460, 607, 100, 13, "Aqui va el R.N.P.A",8);
            AgregaContenidoCuadroDatosLEFT(font, page,120, 594, 140, 13, "Aqui va el correo electronico",8);
            AgregaContenidoCuadroDatosLEFT(font, page,315, 594, 75, 13, "(000) 0000000",8);
            AgregaContenidoCuadroDatosLEFT(font, page,460, 594, 100, 13, "(000) 0000000",8);
            AgregaContenidoCuadroDatosLEFT(font, page,120, 581, 440, 13, "Aqui va la direccion",8);*/
            //AgregaContenidoCuadroDatosLEFT(font, page, 475, 736, 100, 13,sh.getString("DatosTicket13",""),8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 475, 716, 120, 13, sh.getString("DatosTicket5",""),8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void AgregaContenidoCuadro3(PdfPage page,String Ticket){
        SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket,Context.MODE_PRIVATE);
        try {

            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
            AgregaContenidoCuadroDatosLEFT(bold, page, 20, 525+30, 75, 13,"Nombre:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 320, 512+30, 95, 13,"Puesto o cargo",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 20, 512+30, 75, 13,"No.Cred.De Elector:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 320, 499+30, 95, 13,"Teléfono de atención 24hrs:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 20, 499+30, 75, 13,"Correo electrónico:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 20, 486+30, 75, 13,"Domicilio:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 120, 473+30, 440, 13,"Calle y No.                Colonia                Municipio                Ciudad o Puerto                Entidad Federativa                Código Postal",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 20, 460+30, 75, 13,"Observaciones:",8);

            AgregaContenidoCuadroDatosLEFT(font, page, 95, 525+30, 465, 13, sh.getString("NombreResponable",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 95, 512+30, 220, 13, sh.getString("CredResponsable",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,420, 512+30, 140, 13, sh.getString("CargoResponsable",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,95, 499+30, 220, 13, sh.getString("CorreoResposable",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,420, 499+30, 140, 13, sh.getString("Contacto",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 95, 486+30, 465, 13, sh.getString("DireccionResponsable",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 95, 460+30, 465, 13, sh.getString("Observaciones",""),8);

            /*AgregaContenidoCuadroDatosLEFT(font, page, 95, 525+30, 465, 13, sh.getString("DatosUsuario1",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 95, 512+30, 220, 13, sh.getString("DatosUsuario2",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,420, 512+30, 140, 13, sh.getString("DatosUsuario4",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,95, 499+30, 220, 13, sh.getString("DatosUsuario3",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,420, 499+30, 140, 13, sh.getString("DatosUsuario5",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 95, 486+30, 465, 13, sh.getString("DatosUsuario11",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 95, 460+30, 465, 13, sh.getString("Observaciones",""),8);*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void AgregaContenidoCuadro4(PdfPage page,String Ticket){
        try {
            SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket,Context.MODE_PRIVATE);

            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
            PdfFont italic = PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC);

            AgregaContenidoCuadroDatosLEFT(bold, page, 20, 620-205+40, 100, 13,"Nombre de la embarcación:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 390, 607-205+40, 82, 13,"R.N.P.A. Embarcación:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 20, 607-205+40, 55, 13,"Puerto Base:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 20, 594-205+40, 55, 13,"Matrícula:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 260, 594-205+40, 55, 13,"Tonelaje Bruto:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 390, 594-205+40, 60, 13,"Tonelaje Neto:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 20, 581-205+40, 55, 13,"Marca Motor:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 260, 581-205+40, 76, 13,"Potencia Motor (HP):",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 390, 581-205+40, 65, 13,"Eslora (Mts):",8);

            AgregaContenidoCuadroDatosLEFT(font, page, 125, 620-205+40, 255, 13, sh.getString("DatoBarco3",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 80, 607-205+40, 300, 13, sh.getString("DatoBarco2",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,473, 607-205+40, 100, 13, sh.getString("DatoBarco4",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,80, 594-205+40, 180, 13,sh.getString("DatoBarco5",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,337, 594-205+40, 43, 13, sh.getString("DatoBarco11",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,473, 594-205+40, 100, 13, sh.getString("DatoBarco12",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,80, 581-205+40, 480, 13, sh.getString("DatoBarco18",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,337, 581-205+40, 43, 13, sh.getString("DatoBarco19",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,473, 581-205+40, 100, 13, sh.getString("DatoBarco15",""),8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 475, 736, 100, 13,sh.getString("DatosTicket13",""),8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 475, 716, 120, 13, sh.getString("DatosTicket5",""),8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void AgregaContenidoCuadro5(PdfPage page,String Ticket){
        try {
            SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket,Context.MODE_PRIVATE);
            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
            String Permiso1 = sh.getString("DatoBarco6","");
            String Permiso2 = sh.getString("DatoBarco7","");
            String Permiso3 = sh.getString("DatoBarco8","");
            String Permiso4 = sh.getString("DatoBarco9","");
            String Permiso5 = sh.getString("DatoBarco10","");
            if (!Permiso1.equals("")&&!Permiso1.equals("NA")&&!Permiso1.equals("N/A")){
                AgregaContenidoCuadroDatosLEFT(bold, page, 50, 385-55+50, 100, 13, Permiso1,8);
                AgregaContenidoCuadroDatosLEFT(bold, page, 200, 385-55+50, 50, 13, "Fecha Inicial:",8);
                AgregaContenidoCuadroDatosLEFT(font, page, 251, 385-55+50, 50, 13,sh.getString("DatoBarco34",""),8);
                AgregaContenidoCuadroDatosLEFT(bold, page, 301, 385-55+50, 50, 13, "Fecha Final:",8);
                AgregaContenidoCuadroDatosLEFT(font, page, 350, 385-55+50, 50, 13,  sh.getString("DatoBarco35",""),8);
                AgregaContenidoCuadroDatosLEFT(bold, page, 405, 385-55+50, 40, 13, "No. Folio:",8);
                AgregaContenidoCuadroDatosLEFT(font, page, 446, 385-55+50, 114, 13, sh.getString("DatoBarco36",""),8);
            }
            if (!Permiso2.equals("")&&!Permiso2.equals("NA")&&!Permiso2.equals("N/A")){
                AgregaContenidoCuadroDatosLEFT(bold, page, 50, 370-55+50, 100, 13, Permiso2,8);
                AgregaContenidoCuadroDatosLEFT(bold, page, 200, 370-55+50, 50, 13, "Fecha Inicial:",8);
                AgregaContenidoCuadroDatosLEFT(font, page, 251, 370-55+50, 50, 13,sh.getString("DatoBarco41",""),8);
                AgregaContenidoCuadroDatosLEFT(bold, page, 301, 370-55+50, 50, 13, "Fecha Final:",8);
                AgregaContenidoCuadroDatosLEFT(font, page, 350, 370-55+50, 50, 13,sh.getString("DatoBarco42",""),8);
                AgregaContenidoCuadroDatosLEFT(bold, page, 405, 370-55+50, 40, 13,"No. Folio:",8);
                AgregaContenidoCuadroDatosLEFT(font, page, 446, 370-55+50, 114, 13, sh.getString("DatoBarco43",""),8);
            }
            if (!Permiso3.equals("")&&!Permiso3.equals("NA")&&!Permiso2.equals("N/A")){
                AgregaContenidoCuadroDatosLEFT(bold, page, 50, 355-55+50, 100, 13, Permiso3,8);
                AgregaContenidoCuadroDatosLEFT(bold, page, 200, 355-55+50, 50, 13, "Fecha Inicial:",8);
                AgregaContenidoCuadroDatosLEFT(font, page, 251, 355-55+50 ,50, 13,sh.getString("DatoBarco48",""),8);
                AgregaContenidoCuadroDatosLEFT(bold, page, 301, 355-55+50, 50, 13, "Fecha Final:",8);
                AgregaContenidoCuadroDatosLEFT(font, page, 350, 355-55+50 ,50, 13,sh.getString("DatoBarco49",""),8);
                AgregaContenidoCuadroDatosLEFT(bold, page, 405, 355-55+50 ,40, 13,"No. Folio:" ,8);
                AgregaContenidoCuadroDatosLEFT(font, page, 446, 355-55+50 ,114, 13, sh.getString("DatoBarco50","") ,8);
            }
            if (!Permiso4.equals("")&&!Permiso4.equals("NA")&&!Permiso2.equals("N/A")){
                AgregaContenidoCuadroDatosLEFT(bold, page, 50, 340-55+50, 100, 13, Permiso4,8);
                AgregaContenidoCuadroDatosLEFT(bold, page, 200, 340-55+50, 50, 13, "Fecha Inicial:",8);
                AgregaContenidoCuadroDatosLEFT(font, page, 251, 340-55+50, 50, 13, sh.getString("DatoBarco55",""),8);
                AgregaContenidoCuadroDatosLEFT(bold, page, 301, 340-55+50, 50, 13, "Fecha Final:",8);
                AgregaContenidoCuadroDatosLEFT(font, page, 350, 340-55+50, 50, 13, sh.getString("DatoBarco56",""),8);
                AgregaContenidoCuadroDatosLEFT(bold, page, 405, 340-55+50, 40, 13, "No. Folio:",8);
                AgregaContenidoCuadroDatosLEFT(font, page, 446, 340-55+50, 114, 13, sh.getString("DatoBarco57",""),8);
            }
            if (!Permiso5.equals("")&&!Permiso5.equals("NA")&&!Permiso2.equals("N/A")){
                AgregaContenidoCuadroDatosLEFT(bold, page, 50, 325-55+50, 100, 13, Permiso5,8);
                AgregaContenidoCuadroDatosLEFT(bold, page, 200, 325-55+50, 50, 13, "Fecha Inicial:",8);
                AgregaContenidoCuadroDatosLEFT(font, page, 251, 325-55+50,50, 13,sh.getString("DatoBarco62","") ,8);
                AgregaContenidoCuadroDatosLEFT(bold, page, 301, 325-55+50, 50, 13, "Fecha Final:",8);
                AgregaContenidoCuadroDatosLEFT(font, page, 350, 325-55+50 ,50, 13,sh.getString("DatoBarco63","") ,8);
                AgregaContenidoCuadroDatosLEFT(bold, page, 405, 325-55+50 ,40, 13,"No. Folio:" ,8);
                AgregaContenidoCuadroDatosLEFT(font, page, 446, 325-55+50 ,114, 13, sh.getString("DatoBarco64","") ,8);
            }

           /* AgregaContenidoCuadroDatosLEFT(bold, page, 50, 385-55+50, 100, 13, "Camarón",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 50, 370-55+50, 100, 13,"Tunidos" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 50, 355-55+50 ,100, 13,"Tiburón y Raya" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 50, 340-55+50, 120, 13, "Escama",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 50, 325-55+50 ,100, 13,"Pelagicos Menores" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 50, 310-55+50, 67, 13, "Otros Especifique:",8);

            AgregaContenidoCuadroDatosLEFT(bold, page, 405, 385-55+50, 40, 13, "No. Folio:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 405, 370-55+50, 40, 13,"No. Folio:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 405, 355-55+50 ,40, 13,"No. Folio:" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 405, 340-55+50, 40, 13, "No. Folio:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 405, 325-55+50 ,40, 13,"No. Folio:" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 405, 310-55+50, 40, 13, "No. Folio:",8);


            AgregaContenidoCuadroDatosLEFT(bold, page, 120, 310-55+50, 70, 13,  sh.getString("OtrosPermiso",""),8);

            AgregaContenidoCuadroDatosLEFT(bold, page, 200, 385-55+50, 50, 13, "Fecha Inicial:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 200, 370-55+50, 50, 13,"Fecha Inicial:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 200, 355-55+50 ,50, 13,"Fecha Inicial:" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 200, 340-55+50, 50, 13, "Fecha Inicial:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 200, 325-55+50 ,50, 13,"Fecha Inicial:" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 200, 310-55+50, 50, 13, "Fecha Inicial:",8);

            AgregaContenidoCuadroDatosLEFT(bold, page, 301, 385-55+50, 50, 13, "Fecha Final:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 301, 370-55+50, 50, 13,"Fecha Final:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 301, 355-55+50 ,50, 13,"Fecha Final:" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 301, 340-55+50, 50, 13, "Fecha Final:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 301, 325-55+50,50, 13,"Fecha Final:" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 301, 310-55+50, 50, 13, "Fecha Final:",8);

            AgregaContenidoCuadroDatosLEFT(font, page, 446, 385-55+50, 114, 13, sh.getString("DatoBarco36",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 446, 370-55+50, 114, 13, sh.getString("DatoBarco43",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 446, 355-55+50 ,114, 13, sh.getString("DatoBarco50","") ,8);
            AgregaContenidoCuadroDatosLEFT(font, page, 446, 340-55+50, 114, 13, sh.getString("DatoBarco57",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 446, 325-55+50 ,114, 13, sh.getString("DatoBarco64","") ,8);
            AgregaContenidoCuadroDatosLEFT(font, page, 446, 310-55+50, 114, 13, "N/A",8);

            AgregaContenidoCuadroDatosLEFT(font, page, 251, 385-55+50, 50, 13,sh.getString("DatoBarco34",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 251, 370-55+50, 50, 13,sh.getString("DatoBarco41",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 251, 355-55+50 ,50, 13,sh.getString("DatoBarco48",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 251, 340-55+50, 50, 13, sh.getString("DatoBarco55",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 251, 325-55+50,50, 13,sh.getString("DatoBarco62","") ,8);
            AgregaContenidoCuadroDatosLEFT(font, page, 251, 310-55+50, 50, 13, "N/A",8);

            AgregaContenidoCuadroDatosLEFT(font, page, 350, 385-55+50, 50, 13,  sh.getString("DatoBarco35",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 350, 370-55+50, 50, 13,sh.getString("DatoBarco42",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 350, 355-55+50 ,50, 13,sh.getString("DatoBarco49",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 350, 340-55+50, 50, 13, sh.getString("DatoBarco56",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 350, 325-55+50 ,50, 13,sh.getString("DatoBarco63","") ,8);
            AgregaContenidoCuadroDatosLEFT(font, page, 350, 310-55+50, 50, 13, "N/A",8);*/


            //AgregaContenidoCuadroDatosLEFT(font, page, 200, 435, 100, 13, sh.getString("DatoBarco21",""),8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 200, 420, 100, 13,sh.getString("DatoBarco22",""),8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 200, 405 ,100, 13,sh.getString("DatoBarco23","") ,8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 200, 390, 120, 13, sh.getString("DatoBarco24",""),8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 200, 375 ,100, 13,sh.getString("DatoBarco25",""),8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 200, 360, 120, 13, sh.getString("DatoBarco26",""),8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void AgregaContenidoCuadro6(PdfPage page,String Ticket){
        try {
            SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket,Context.MODE_PRIVATE);
            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
            AgregaContenidoCuadroDatosLEFT(bold, page, 120, 285-15, 100, 13, "Pre-Instalación" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 320, 285-15, 100, 13,"Equipo Transreceptor" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 470, 285-15, 100, 13,"Verificación y Pruebas" ,8);

            AgregaContenidoCuadroDatosLEFT(bold, page, 20, 270-15, 100, 13, "Tubo Base",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 20, 255-15, 100, 13,"Cable Eléctrico" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 20, 240-15,100, 13,"Orificio" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 20, 225-15,100, 13,"Base BlueTraker" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 20, 210-15,100, 13,"Base ConBox" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 20, 195-15,70, 13,"Voltaje de Batería" ,8);

            AgregaContenidoCuadroDatosLEFT(bold, page, 200, 270-15, 100, 13, "Cable de Comunicación",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 200, 255-15, 110, 13,"Mica de seguridad botón pánico" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 200, 240-15 ,80, 13,"No. de serie ConBox" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 200, 225-15 ,80, 13,"No.de serie BlueTraker" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 200, 210-15 ,30, 13,"IMEI" ,8);

            AgregaContenidoCuadroDatosLEFT(bold, page, 420, 270-15, 100, 13, "Encendido",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 420, 255-15, 100, 13,"Leds" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 420, 240-15,100, 13,"Emergencia" ,8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 420, 225-15,100, 13,"Alarma Audible" ,8);

            AgregaContenidoCuadroDatosLEFT(font, page, 285, 240-15 ,100, 13,sh.getString("NSerieConBox","") ,8);
            AgregaContenidoCuadroDatosLEFT(font, page, 285, 225-15 ,110, 13,sh.getString("NSerieTransreceptor","") ,8);
            AgregaContenidoCuadroDatosLEFT(font, page, 235, 210-15 ,110, 13,sh.getString("IMEIIridium","") ,8);
            AgregaContenidoCuadroDatosLEFT(font, page, 105, 195-15,80, 13,sh.getString("VoltajeBateria","") ,8);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    private void AgregaContenidoCuadroDatosCENTER(PdfFont font,PdfPage page,int x, int y,int w,int h,String Comentario, int tamaño){
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text Comen1 = new Text(Comentario.replaceAll("(\n|\r)", " ")).setFont(font).setFontSize(tamaño);
        Paragraph a = new Paragraph().add(Comen1)
                .setTextAlignment(TextAlignment.CENTER);
        canvas.add(a).close();
        canvas.close();
        pdfCanvas.setColor(ColorConstants.WHITE,false).stroke().closePathStroke();
    }
    private void AgregaContenidoCuadroDatosJUS(PdfFont font,PdfPage page,int x, int y,int w,int h,String Comentario, int tamaño){
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text Comen1 = new Text(Comentario.replaceAll("(\n|\r)", " ")).setFont(font).setFontSize(tamaño);
        Paragraph a = new Paragraph().add(Comen1)
                .setTextAlignment(TextAlignment.JUSTIFIED);
        canvas.add(a).close();
        canvas.close();
        pdfCanvas.setColor(ColorConstants.WHITE,false).stroke().closePathStroke();
    }
    private void InsertaImagenLogo(ImageData imageData,Canvas canvas,int left,int bottom,int width){
        Image img = new Image(imageData,left,bottom,width);
        canvas.add(img).close();
    }
    private void AñadirLineas(PdfPage page,String Ticket){
        PdfCanvas canvas = new PdfCanvas(page);
        // Create a 100% Magenta color
        canvas
                .moveTo(445, 688+20)
                .lineTo(540, 688+20)
                .moveTo(445, 701+20)
                .lineTo(540, 701+20)
                .moveTo(445, 714+20)
                .lineTo(540, 714+20) ///CUADRO1

                .moveTo(160, 621+20)
                .lineTo(380, 621+20)
                .moveTo(160, 608+20)
                .lineTo(380, 608+20)
                .moveTo(460, 608+20)
                .lineTo(560, 608+20)
                .moveTo(95, 595+20)
                .lineTo(250, 595+20)
                .moveTo(315, 595+20)
                .lineTo(380, 595+20)
                .moveTo(460, 595+20)
                .lineTo(560, 595+20)
                .moveTo(95, 582+20)
                .lineTo(560, 582+20) ///CUADRO2

                .moveTo(95, 526+30)
                .lineTo(560, 526+30)
                .moveTo(95, 513+30)
                .lineTo(315, 513+30)
                .moveTo(420, 513+30)
                .lineTo(560, 513+30)
                .moveTo(95, 500+30)
                .lineTo(315, 500+30)
                .moveTo(420, 500+30)
                .lineTo(560, 500+30)
                .moveTo(95, 487+30)
                .lineTo(560, 487+30)
                .moveTo(95, 461+30)
                .lineTo(560, 461+30) ///CUADRO3

                .moveTo(125, 621-205+40)
                .lineTo(380, 621-205+40)
                .moveTo(80, 608-205+40)
                .lineTo(380, 608-205+40)
                .moveTo(473, 608-205+40)
                .lineTo(560, 608-205+40)
                .moveTo(80, 595-205+40)
                .lineTo(250, 595-205+40)
                .moveTo(337, 595-205+40)
                .lineTo(380, 595-205+40)
                .moveTo(473, 595-205+40)
                .lineTo(560, 595-205+40)
                .moveTo(80, 582-205+40)
                .lineTo(250, 582-205+40)
                .lineTo(250, 582-205+40)
                .moveTo(337, 582-205+40)
                .lineTo(380, 582-205+40)
                .moveTo(473, 582-205+40)
                .lineTo(560, 582-205+40)///CUADRO4




                .moveTo(90, 225-81+50-15)
                .lineTo(190, 225-81+50-15)
                .moveTo(280, 225-81+80-15)
                .lineTo(410, 225-81+80-15)
                .moveTo(280, 240-81+80-15)
                .lineTo(410, 240-81+80-15)
                .moveTo(230, 210-81+80-15)
                .lineTo(410, 210-81+80-15)///CUADRO6


                .setLineWidth(1).closePathStroke();

        /*canvas
                .moveTo(446, 311-55+50)
                .lineTo(560, 311-55+50)
                .moveTo(250, 311-55+50)
                .lineTo(290, 311-55+50)


                .moveTo(350, 311-55+50)
                .lineTo(390, 311-55+50)

                .moveTo(120, 311-55+50)
                .lineTo(180, 311-55+50)
                .setLineWidth(1).closePathStroke(); ///CUADRO5
                */
        SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket,Context.MODE_PRIVATE);
        String Permiso1 = sh.getString("DatoBarco6","");
        String Permiso2 = sh.getString("DatoBarco7","");
        String Permiso3 = sh.getString("DatoBarco8","");
        String Permiso4 = sh.getString("DatoBarco9","");
        String Permiso5 = sh.getString("DatoBarco10","");

        if (!Permiso1.equals("")&&!Permiso1.equals("NA")&&!Permiso1.equals("N/A")){
                   canvas.moveTo(446, 386-55+50)
                           .lineTo(560, 386-55+50)
                           .moveTo(250, 386-55+50)
                           .lineTo(290, 386-55+50)
                           .moveTo(350, 386-55+50)
                           .lineTo(390, 386-55+50)
                    .setLineWidth(1).closePathStroke();
        }
        if (!Permiso2.equals("")&&!Permiso2.equals("NA")&&!Permiso2.equals("N/A")){
                  canvas     .moveTo(446, 371-55+50)
                          .lineTo(560, 371-55+50)
                          .moveTo(250, 371-55+50)
                          .lineTo(290, 371-55+50)
                          .moveTo(350, 371-55+50)
                          .lineTo(390, 371-55+50)
                          .setLineWidth(1).closePathStroke();
        }
        if (!Permiso3.equals("")&&!Permiso3.equals("NA")&&!Permiso2.equals("N/A")){
                  canvas.     moveTo(446, 356-55+50)
                          .lineTo(560, 356-55+50)
                          .moveTo(250, 356-55+50)
                          .lineTo(290, 356-55+50)
                          .moveTo(350, 356-55+50)
                          .lineTo(390, 356-55+50)
                          .setLineWidth(1).closePathStroke();
        }
        if (!Permiso4.equals("")&&!Permiso4.equals("NA")&&!Permiso2.equals("N/A")){
                 canvas          .moveTo(446, 341-55+50)
                         .lineTo(560, 341-55+50)
                         .moveTo(250, 341-55+50)
                         .lineTo(290, 341-55+50)
                         .moveTo(350, 341-55+50)
                         .lineTo(390, 341-55+50)

                         .setLineWidth(1).closePathStroke();
        }
        if (!Permiso5.equals("")&&!Permiso5.equals("NA")&&!Permiso2.equals("N/A")){
            canvas .moveTo(446, 326-55+50)
                    .lineTo(560, 326-55+50)
                    .moveTo(250, 326-55+50)
                    .lineTo(290, 326-55+50)
                    .moveTo(350, 326-55+50)
                    .lineTo(390, 326-55+50)
                    .setLineWidth(1).closePathStroke();
        }


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
    private void squareX(PdfPage page,int y,int x){
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

                .moveTo(x, y-7) //y-4
                .lineTo(x-7, y) //y+3

                .moveTo(x, y) //y-4
                .lineTo(x-7, y-7) //y+3
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
    private void InsertaSquare(PdfPage page,String Ticket){
        SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket,Context.MODE_PRIVATE);



        if (sh.getBoolean("CheckSec17",false)) squareX(page, 628+20, 450);
        else    square(page, 628+20, 450);
        if (sh.getBoolean("CheckSec18",false)) squareX(page, 628+20, 550);
        else   square(page, 628+20, 550);

        if (sh.getBoolean("CheckSec2",false))squareX(page, 270-32-40+65, 140);
        else square(page, 270-32-40+65, 140);

        if (sh.getBoolean("CheckSec3",false))  squareX(page, 255-32-40+65, 140);
        else   square(page, 255-32-40+65, 140);


        if (sh.getBoolean("CheckSec4",false))  squareX(page, 240-32-40+65, 140);
        else   square(page, 240-32-40+65, 140);

        if (sh.getBoolean("CheckSec0",false))  squareX(page, 225-32-40+65, 140);
        else    square(page, 225-32-40+65, 140);

        if (sh.getBoolean("CheckSec1",false))    squareX(page, 210-32-40+65, 140);
        else     square(page, 210-32-40+65, 140);


        if (sh.getBoolean("CheckSec5",false))     squareX(page, 270-32-40+65, 350);
        else      square(page, 270-32-40+65, 350);

        if (sh.getBoolean("CheckSec6",false))     squareX(page, 255-32-40+65, 350);
        else       square(page, 255-32-40+65, 350);

        if (sh.getBoolean("CheckSec7",false))    squareX(page, 270-32-40+65, 500);
        else      square(page, 270-32-40+65, 500);

        if (sh.getBoolean("CheckSec9",false))   squareX(page, 255-32-40+65, 500);
        else      square(page, 255-32-40+65, 500);

        if (sh.getBoolean("CheckSec8",false))  squareX(page, 240-32-40+65, 500);
        else     square(page, 240-32-40+65, 500);

        if (sh.getBoolean("CheckSec10",false))   squareX(page, 225-32-40+65, 500);
        else     square(page, 225-32-40+65, 500);



        String Permiso1 = sh.getString("DatoBarco6","");
        String Permiso2 = sh.getString("DatoBarco7","");
        String Permiso3 = sh.getString("DatoBarco8","");
        String Permiso4 = sh.getString("DatoBarco9","");
        String Permiso5 = sh.getString("DatoBarco10","");

        if (!Permiso1.equals("")&&!Permiso1.equals("NA")&&!Permiso1.equals("N/A")){
            squareX(page, 444-55, 46);
        }
        if (!Permiso2.equals("")&&!Permiso2.equals("NA")&&!Permiso2.equals("N/A")){
            squareX(page, 429-55, 46);
        }
        if (!Permiso3.equals("")&&!Permiso3.equals("NA")&&!Permiso2.equals("N/A")){
            squareX(page, 414-55, 46);
        }
        if (!Permiso4.equals("")&&!Permiso4.equals("NA")&&!Permiso2.equals("N/A")){
            squareX(page, 399-55, 46);
        }
        if (!Permiso5.equals("")&&!Permiso5.equals("NA")&&!Permiso2.equals("N/A")){
            squareX(page, 384-55, 46);
        }

        /*if (sh.getBoolean("CheckSec11",false))squareX(page, 444-55, 46);
        else square(page, 444-55, 46);
        if (sh.getBoolean("CheckSec12",false))squareX(page, 429-55, 46);
        else square(page, 429-55, 46);
        if (sh.getBoolean("CheckSec13",false))squareX(page, 414-55, 46);
        else square(page, 414-55, 46);
        if (sh.getBoolean("CheckSec14",false))squareX(page, 399-55, 46);
        else square(page, 399-55, 46);
        if (sh.getBoolean("CheckSec15",false)) squareX(page, 384-55, 46);
        else   square(page, 384-55, 46);
        if (sh.getBoolean("CheckSec16",false))squareX(page, 369-55, 46);
        else  square(page, 369-55, 46);*/




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
        Text SeguritechA = new Text("Responsable por parte del permisionario").setFont(bold).setFontSize(9);
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
        Text NombreSeguritech = new Text(sh.getString("DatosUsuario1","")).setFont(font).setFontSize(9);
        //Text NombreSeguritech = new Text("Angel Natanael Sandoval Rosas").setFont(font).setFontSize(9);
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
        Text NombreANAM = new Text(sh.getString("NombreResponable","")).setFont(font).setFontSize(9);
        //Text NombreANAM = new Text("").setFont(font).setFontSize(9);
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
        NombreSeguritech = new Text(sh.getString("DatosUsuario4","")).setFont(font).setFontSize(9);
        //NombreSeguritech = new Text("Ingeniero de soporte").setFont(font).setFontSize(9);
        e = new Paragraph().add(Space).add(NombreSeguritech).setTextAlignment(TextAlignment.LEFT);
        canvas5.add(e);

        /*PdfCanvas pdfCanvas50 = new PdfCanvas(page);
        Rectangle rectangle50 = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle50);
        Canvas canvas50 = new Canvas(pdfCanvas50, rectangle50);
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(Ticket, Context.MODE_PRIVATE);
        File file = new File(directory, "FirmaBarco.png");
        ImageData data = null;
        try {
            data = ImageDataFactory.create(file.getAbsolutePath());
            Image img = new Image(data,x+(w/2)+40,y-h-h-h-h-h-h,(w/2)-40);
            //img.setHeight(h);
            canvas50.add(img).close();
            canvas50.close();
        } catch (MalformedURLException ez) {
            ez.printStackTrace();
        }*/



        /*PdfCanvas pdfCanvas60 = new PdfCanvas(page);
        Rectangle rectangle60 = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle60);
        Canvas canvas60 = new Canvas(pdfCanvas60, rectangle60);
        cw = new ContextWrapper(getApplicationContext());
        directory = cw.getDir(Ticket, Context.MODE_PRIVATE);
        file = new File(directory, "FirmaSeguritech.png");
        data = null;
        try {
            data = ImageDataFactory.create(file.getAbsolutePath());
            Image img = new Image(data,x+40,y-h-h-h-h-h-h,(w/2)-40);
            //img.setHeight(h);
            canvas60.add(img).close();
            canvas60.close();
        } catch (MalformedURLException ez) {
            ez.printStackTrace();
        }*/



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
        NombreANAM = new Text(sh.getString("CargoResponsable","")).setFont(font).setFontSize(9);
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
    ///////ARCHIVO NO INSTALACIÓN
    public void CreaArchivo2(String Ticket, ImageData imageData)throws IOException {
        String Name = Ticket+"(NI).pdf";
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(Ticket, Context.MODE_PRIVATE);
        File file = new File(directory, Name);
        Rectangle TamañoOficio = new Rectangle(612, 1008);
        PageSize Oficio = new PageSize(TamañoOficio);
        PdfDocument pdf = new PdfDocument(new PdfWriter(file));
        //pdf.setDefaultPageSize(Oficio);
        PdfPage page = pdf.addNewPage();
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
        AñadirLineas2(page);
        //InsertaSquare(page,Ticket);
        CuadroDatosArchivo2(pdfCanvas,font,bold,36, 660+64, 530, 93,imageData);
        CuadroDatos2(pdfCanvas,bold,36, 565+40, 530, 70);
        CuadroDatos4(pdfCanvas,bold,36, 460+30+40, 530, 60);
        CuadroDatos5Archivo2(pdfCanvas,bold,36, 440, 530, 75);
        CuadroDatos6Archivo2(pdfCanvas,bold,36, 90, 530, 90);
        AgregaContenidoCuadro1Archivo2(page,Ticket);
        AgregaContenidoCuadro2Archivo2(page,Ticket);
        AgregaContenidoCuadro4Archivo2(page,Ticket);
        AgregaContenidoCuadro5Archivo2(page,Ticket);
        AgregaContenidoCuadro6Archivo2(page,Ticket);
        InsertaSquare2(page,Ticket);
        InseraImagenes1(page,Ticket);
        InsertaPie(pdf);
        pdf.close();
    }
    private void CuadroDatosArchivo2(PdfCanvas pdfCanvas,PdfFont font,PdfFont bold,int x,int y,int w,int h,ImageData imageData){
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke().closePathStroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text title = new Text("JUSTIFICACIÓN DE NO INSTALACIÓN DE EQUIPO").setFont(bold).setFontColor(ColorConstants.BLUE).setFontSize(10);
        Text title2 = new Text("").setFont(font).setFontColor(ColorConstants.BLACK).setFontSize(10);
        Text Space = new Text(".          .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);
        Paragraph a = new Paragraph().add(title).add(Space).setTextAlignment(TextAlignment.RIGHT);
        InsertaImagenLogo(imageData,canvas,50,680+57,170);
        canvas.add(a);
        canvas.close();
    }
    private void CuadroDatos5Archivo2(PdfCanvas pdfCanvas,PdfFont bold,int x,int y, int w,int h){
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke().closePathStroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Rectangle rectangle2 = new Rectangle(x, y+h, w, 15);
        pdfCanvas.rectangle(rectangle2);
        pdfCanvas.stroke().closePathStroke();
        Canvas canvas2 = new Canvas(pdfCanvas, rectangle2);
        Text DatosEquipo = new Text("Justificación de no instalación").setFont(bold).setFontSize(8);
        Paragraph b = new Paragraph().add(DatosEquipo).setTextAlignment(TextAlignment.CENTER);
        canvas2.add(b).close();
        canvas.close();
        canvas2.close();
    }
    private void CuadroDatos6Archivo2(PdfCanvas pdfCanvas,PdfFont bold,int x,int y, int w,int h){
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke().closePathStroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Rectangle rectangle2 = new Rectangle(x, y+h, w, 15);
        pdfCanvas.rectangle(rectangle2);
        pdfCanvas.stroke().closePathStroke();
        Canvas canvas2 = new Canvas(pdfCanvas, rectangle2);
        Text DatosEquipo = new Text("Sin otro particular por el momento, reciba un cordial saludo.").setFont(bold).setFontSize(8);
        Paragraph b = new Paragraph().add(DatosEquipo).setTextAlignment(TextAlignment.CENTER);
        canvas2.add(b).close();
        canvas.close();
        canvas2.close();
    }
    private void AgregaContenidoCuadro1Archivo2(PdfPage page,String Ticket){
        try {
            SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket,Context.MODE_PRIVATE);

            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
            AgregaContenidoCuadroDatosRIGHT(bold, page, 240, 726+35+46+14, 325, 13, Ticket,10);
            AgregaContenidoCuadroDatosCENTER(font, page, 240, 717+40, 310, 45,
                    "SERVICIO INTEGRAL DE TELECOMUNICACIÓN PARA LA LOCALIZACIÓN Y MONITOREO SATELITAL DE " +
                            "EMBARCACIONES PESQUERAS \n                           " +
                            "CONTRATO  LPIE/006/12/22",8);
            //AgregaContenidoCuadroDatosRIGHT(font, page, 240, 726+200, 310, 13, sh.getString("DatosTicket3",""),9);
            AgregaContenidoCuadroDatosRIGHT(bold, page, 300, 713+40, 47, 13,"No. de oficio:" ,8);
            AgregaContenidoCuadroDatosRIGHT(bold, page, 300, 700+40, 47, 13, "Lugar:",8);
            AgregaContenidoCuadroDatosRIGHT(bold, page, 300, 687+40, 47, 13, "Fecha:",8);
           /* AgregaContenidoCuadroDatosCENTER(font, page, 40, 670+180, 510, 13,"En seguimiento a la solicitud de instalación de equipo según oficio DGIV.-"+sh.getString("DatosTicket12","")+" de fecha: "
                    +sh.getString("DatosTicket12","")+" A continuacion presento lo siguiente:" ,7);*/
            AgregaContenidoCuadroDatosLEFT(font, page, 40, 660+31, 520, 36,"En seguimiento a la solicitud de instalación de equipo según oficio DGIV.-"+sh.getString("DatosTicket12","")+" de fecha: "
                    +sh.getString("DatosTicket13","")+" A continuación presento lo siguiente:" ,10);

            AgregaContenidoCuadroDatosLEFT(font, page, 350, 713+40, 150, 13,"DGIV-" +sh.getString("DatosTicket12",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 350, 700+40, 150, 13, sh.getString("DatoBarco25",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 350, 687+40, 150, 13, sh.getString("FechaCerrado",""),8);

            //AgregaContenidoCuadroDatosLEFT(font, page, 445, 713, 100, 13, " Aqui va el número",8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 445, 700, 100, 13, " Aqui va la localidad",8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 445, 687, 100, 13, " 00-00-0000",8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 475, 736, 100, 13,sh.getString("DatosTicket13",""),8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 475, 716, 120, 13, sh.getString("DatosTicket5",""),8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void AgregaContenidoCuadro2Archivo2(PdfPage page,String Ticket){
        try {
            SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket,Context.MODE_PRIVATE);

            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
            PdfFont italic = PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC);

            AgregaContenidoCuadroDatosLEFT(bold, page, 40, 620+40, 140, 13,"Titular del permisionario/concesión:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 390, 620+40, 70, 13,"Persona Física",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 490, 620+40, 70, 13,"Persona Moral",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 390, 607+40, 70, 13,"R.N.P.A. Titular:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 40, 607+40, 140, 13,"Representante Legal (Persona Moral):",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 40, 594+40, 80, 13,"Correo electrónico:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 260, 594+40, 55, 13,"Teléfono Fijo:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 390, 594+40, 60, 13,"Teléfono Móvil:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 40, 581+40, 80, 13,"Domicilio:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 120, 568+40, 440, 13,"Calle y No.                Colonia                Municipio                Ciudad o Puerto                Entidad Federativa                Código Postal",8);

            AgregaContenidoCuadroDatosLEFT(font, page, 180, 620+40, 200, 13, sh.getString("DatoBarco21",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 180, 607+40, 200, 13, sh.getString("DatoBarco28",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,460, 607+40, 100, 13, sh.getString("DatoBarco20",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,120, 594+40, 140, 13, sh.getString("DatoBarco29",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,315, 594+40, 75, 13, sh.getString("DatoBarco27",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,460, 594+40, 100, 13, sh.getString("DatoBarco67",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,120, 581+40, 440, 13, sh.getString("DatoBarco26",""),8);


            /*AgregaContenidoCuadroDatosLEFT(font, page, 180, 620, 200, 13, "Aqui va el nombre del permisionario/Conseción.",8);
            AgregaContenidoCuadroDatosLEFT(font, page, 180, 607, 200, 13, "Aqui va el representante legal (persona moral).",8);
            AgregaContenidoCuadroDatosLEFT(font, page,460, 607, 100, 13, "Aqui va el R.N.P.A",8);
            AgregaContenidoCuadroDatosLEFT(font, page,120, 594, 140, 13, "Aqui va el correo electronico",8);
            AgregaContenidoCuadroDatosLEFT(font, page,315, 594, 75, 13, "(000) 0000000",8);
            AgregaContenidoCuadroDatosLEFT(font, page,460, 594, 100, 13, "(000) 0000000",8);
            AgregaContenidoCuadroDatosLEFT(font, page,120, 581, 440, 13, "Aqui va la direccion",8);*/
            //AgregaContenidoCuadroDatosLEFT(font, page, 475, 736, 100, 13,sh.getString("DatosTicket13",""),8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 475, 716, 120, 13, sh.getString("DatosTicket5",""),8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void AgregaContenidoCuadro4Archivo2(PdfPage page,String Ticket){
        try {
            SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket,Context.MODE_PRIVATE);

            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
            PdfFont italic = PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC);

            AgregaContenidoCuadroDatosLEFT(bold, page, 40, 620-115+40+30, 100, 13,"Nombre de la embarcación:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 390, 607-115+40+30, 82, 13,"R.N.P.A. Embarcación:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 40, 607-115+40+30, 55, 13,"Puerto Base:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 40, 594-115+40+30, 55, 13,"Matrícula:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 260, 594-115+40+30, 55, 13,"Tonelaje Bruto:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 390, 594-115+40+30, 60, 13,"Tonelaje Neto:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 40, 581-115+40+30, 55, 13,"Marca Motor:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 260, 581-115+40+30, 76, 13,"Potencia Motor (HP):",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 390, 581-115+40+30, 65, 13,"Eslora (Mts):",8);

            AgregaContenidoCuadroDatosLEFT(font, page, 145, 620-115+40+30, 235, 13, sh.getString("DatoBarco3",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 100, 607-115+40+30, 280, 13, sh.getString("DatoBarco2",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,473, 607-115+40+30, 100, 13, sh.getString("DatoBarco4",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,100, 594-115+40+30, 160, 13,sh.getString("DatoBarco5",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,337, 594-115+40+30, 43, 13, sh.getString("DatoBarco11",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,473, 594-115+40+30, 100, 13, sh.getString("DatoBarco12",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,100, 581-115+40+30, 460, 13, sh.getString("DatoBarco18",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,337, 581-115+40+30, 43, 13, sh.getString("DatoBarco19",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,473, 581-115+40+30, 100, 13, sh.getString("DatoBarco15",""),8);


            //AgregaContenidoCuadroDatosLEFT(font, page, 475, 736, 100, 13,sh.getString("DatosTicket13",""),8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 475, 716, 120, 13, sh.getString("DatosTicket5",""),8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void AgregaContenidoCuadro5Archivo2(PdfPage page,String Ticket){
        try {
            SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket,Context.MODE_PRIVATE);

            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
            PdfFont italic = PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC);

            AgregaContenidoCuadroDatosLEFT(font, page, 40, 360+111, 520, 43,sh.getString("Justificacion",""),8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 475, 736, 100, 13,sh.getString("DatosTicket13",""),8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 475, 716, 120, 13, sh.getString("DatosTicket5",""),8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void AgregaContenidoCuadro6Archivo2(PdfPage page,String Ticket){
        try {
            SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket,Context.MODE_PRIVATE);

            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
            PdfFont italic = PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC);

            AgregaContenidoCuadroDatosCENTER(font, page, 40, 160, 510, 19,"ATENTAMENTE",8);
            AgregaContenidoCuadroDatosCENTER(font, page, 40, 100, 510, 19,"_________________________________",8);
            AgregaContenidoCuadroDatosCENTER(bold, page, 40, 87, 510, 19,"DIRECTOR DE PROYECTO",8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 475, 736, 100, 13,sh.getString("DatosTicket13",""),8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 475, 716, 120, 13, sh.getString("DatosTicket5",""),8);
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
    private void AñadirLineas2(PdfPage page){
        PdfCanvas canvas = new PdfCanvas(page);
        // Create a 100% Magenta color
        canvas
                .moveTo(350, 688+40)
                .lineTo(500, 688+40)
                .moveTo(350, 701+40)
                .lineTo(500, 701+40)
                .moveTo(350, 714+40)
                .lineTo(500, 714+40) ///CUADRO1

                .moveTo(180, 621+40)
                .lineTo(380, 621+40)
                .moveTo(180, 608+40)
                .lineTo(380, 608+40)
                .moveTo(460, 608+40)
                .lineTo(560, 608+40)
                .moveTo(120, 595+40)
                .lineTo(250, 595+40)
                .moveTo(315, 595+40)
                .lineTo(380, 595+40)
                .moveTo(460, 595+40)
                .lineTo(560, 595+40)
                .moveTo(120, 582+40)
                .lineTo(560, 582+40) ///CUADRO2


                .moveTo(145, 621-115+70)
                .lineTo(380, 621-115+70)
                .moveTo(100, 608-115+70)
                .lineTo(380, 608-115+70)
                .moveTo(473, 608-115+70)
                .lineTo(560, 608-115+70)
                .moveTo(100, 595-115+70)
                .lineTo(250, 595-115+70)
                .moveTo(337, 595-115+70)
                .lineTo(380, 595-115+70)
                .moveTo(473, 595-115+70)
                .lineTo(560, 595-115+70)
                .moveTo(100, 582-115+70)
                .lineTo(250, 582-115+70)
                .lineTo(250, 582-115+70)
                .moveTo(337, 582-115+70)
                .lineTo(380, 582-115+70)
                .moveTo(473, 582-115+70)
                .lineTo(560, 582-115+70)///CUADRO4

                .moveTo(40,360+141 )
                .lineTo(560, 360+141)
                .moveTo(40,347+141 )
                .lineTo(560, 347+141)
                .moveTo(40,334+141 )
                .lineTo(560, 334+141)
                .moveTo(40,321+141 )
                .lineTo(560, 321+141)
                .moveTo(40,308+141 )
                .lineTo(560, 308+141)

                .setLineWidth(1).closePathStroke();


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
    private void InseraImagenes1 (PdfPage page,String Ticket){
        SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket,Context.MODE_PRIVATE);
        PdfFont font = null;
        try {font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            InsertImagen (page, 50,450-200,230,172,Ticket,"Foto1.png");
            InsertImagen (page, 320,450-200,230,172,Ticket,"Foto2.png");
            //InsertImagen (page, 50,150-85,230,172,Ticket,"Foto3.png");
            //InsertImagen (page, 320,150-85,230,172,Ticket,"Foto4.png");
            InsertaBordeImagen(page, 50,450-200,230,172);
            InsertaBordeImagen(page, 320,450-200,230,172);
            //InsertaBordeImagen(page, 50,150-85,230,172);
            //InsertaBordeImagen(page, 320,150-85,230,172);
            AgregaComentarios(font,page,50, 399-200, 232, 50,sh.getString("ComentarioFoto1",""));
            AgregaComentarios(font,page,320,399-200,230,50,sh.getString("ComentarioFoto2",""));
            //AgregaComentarios(font,page,50,99-85,230,50,sh.getString("ComentarioFoto3",""));
            //AgregaComentarios(font,page,320,99-85,230,50,sh.getString("ComentarioFoto4",""));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void InsertaSquare2(PdfPage page,String Ticket){
        SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket,Context.MODE_PRIVATE);
        if (sh.getBoolean("CheckSec17",false)) squareX(page, 628+40, 450);
        else    square(page, 628+40, 450);
        if (sh.getBoolean("CheckSec18",false)) squareX(page, 628+40, 550);
        else   square(page, 628+40, 550);

    }
    ////RF
    public void CreaArchivo3(String Ticket, ImageData imageData)throws IOException {
        String Name = Ticket+"(RF).pdf";
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(Ticket, Context.MODE_PRIVATE);
        File file = new File(directory, Name);
        Rectangle TamañoOficio = new Rectangle(612, 1008);
        PageSize Oficio = new PageSize(TamañoOficio);
        PdfDocument pdf = new PdfDocument(new PdfWriter(file));
        //pdf.setDefaultPageSize(Oficio);
        PdfPage page = pdf.addNewPage();
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
        AñadirLineas3(page);
        CuadroDatosArchivo3(pdfCanvas,font,bold,36, 683, 530, 77,imageData);
        CuadroDatos2(pdfCanvas,bold,36, 565, 530, 70);
        CuadroDatos4(pdfCanvas,bold,36, 460+30, 530, 60);
        //CuadroDatos5Archivo2(pdfCanvas,bold,36, 360+200, 530, 80);
        AgregaContenidoCuadro1Archivo3(page,Ticket);
        AgregaContenidoCuadro2Archivo3(page,Ticket);
        AgregaContenidoCuadro4Archivo3(page,Ticket);
        //AgregaContenidoCuadro5Archivo2(page,Ticket);
        PdfPage page2=CuadroNextPage(pdf,font,bold,imageData,Ticket);
        InseraImagenes(page,Ticket);
        InseraImagenes2(page2,Ticket);
        //InsertaSquare2(page,Ticket);

        InsertaPie(pdf);
        pdf.close();
    }
    private void InseraImagenes2 (PdfPage page,String Ticket){
        PdfFont font = null;
        try {
            font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            InsertImagen (page, 59, 509+0,212,144,Ticket,"Foto5.png");
            InsertImagen (page, 329, 509+0,212,144,Ticket,"Foto6.png");
            InsertImagen (page, 59, 309+0,212,144,Ticket,"Foto7.png");
            InsertImagen (page, 329, 309+0,212,144,Ticket,"Foto8.png");
            InsertImagen (page, 59, 109+0,212,144,Ticket,"Foto9.png");
            InsertImagen(page, 329, 109+0,212,144,Ticket,"Foto10.png");
            InsertaBordeImagen(page, 59, 509+0,212,144);
            InsertaBordeImagen(page, 329, 509+0,212,144);
            InsertaBordeImagen(page, 59, 309+0,212,144);
            InsertaBordeImagen(page, 329, 309+0,212,144);
            InsertaBordeImagen(page, 59, 109+0,212,144);
            InsertaBordeImagen(page, 329, 109+0,212,144);
            AgregaComentarios(font,page,60, 460+0, 212, 50,datos.FotosReporteInstalacion[4]);
            AgregaComentarios(font,page,330, 460+0, 212, 50,datos.FotosReporteInstalacion[5]);
            AgregaComentarios(font,page,60, 260+0, 212, 50,datos.FotosReporteInstalacion[6]);
            AgregaComentarios(font,page,330, 260+0, 212, 50,datos.FotosReporteInstalacion[7]);
            AgregaComentarios(font,page,60, 60+0, 212, 50,datos.FotosReporteInstalacion[8]);
            AgregaComentarios(font,page,330, 60+0, 212, 50,datos.FotosReporteInstalacion[9]);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void InseraImagenes (PdfPage page,String Ticket){
        SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket,Context.MODE_PRIVATE);
        PdfFont font = null;
        try {font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            InsertImagen (page, 50,450-150,230,172,Ticket,"Foto1.png");
            InsertImagen (page, 320,450-150,230,172,Ticket,"Foto2.png");
            InsertImagen (page, 50,150-70,230,172,Ticket,"Foto3.png");
            InsertImagen (page, 320,150-70,230,172,Ticket,"Foto4.png");
            InsertaBordeImagen(page, 50,450-150,230,172);
            InsertaBordeImagen(page, 320,450-150,230,172);
            InsertaBordeImagen(page, 50,150-70,230,172);
            InsertaBordeImagen(page, 320,150-70,230,172);
            AgregaComentarios(font,page,50, 399-150, 232, 50,sh.getString("ComentarioFoto1",""));
            AgregaComentarios(font,page,320,399-150,230,50,sh.getString("ComentarioFoto2",""));
            AgregaComentarios(font,page,50,99-70,230,50,sh.getString("ComentarioFoto3",""));
            AgregaComentarios(font,page,320,99-70,230,50,sh.getString("ComentarioFoto4",""));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private PdfPage CuadroNextPage(PdfDocument pdf,PdfFont font,PdfFont bold,ImageData imageData,String Ticket){
        SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket,Context.MODE_PRIVATE);
        PdfPage nextpage = pdf.addNewPage();
        PdfCanvas pdfCanvas = new PdfCanvas(nextpage);
        Rectangle rectangle = new Rectangle(36, 670, 530, 90);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text NReporte = new Text("No de oficio:").setFont(font).setFontSize(9);
        Text title = new Text("REPORTE FOTOGÁFICO DE INSTALACIÓN").setFont(bold).setFontColor(ColorConstants.BLUE).setFontSize(10);
        Text TICKET = new Text("DGIV.-"+sh.getString("DatosTicket12","")).setFont(font).setUnderline().setFontSize(9);
        Text Space = new Text(".  .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);
        Paragraph a = new Paragraph().add(title).add(Space).setTextAlignment(TextAlignment.RIGHT);
        Paragraph b = new Paragraph().add(NReporte).add(Space).add(TICKET).add(Space).add(Space).add(Space).setTextAlignment(TextAlignment.RIGHT);
        //InsertaImagen("FotosBase/", "seguritechlogo.png",canvas,50,715,190,142);
        InsertaImagenLogo(imageData,canvas,50,660+25,180);
        canvas.add(a);
        canvas.add(b);
        canvas.close();
        return (nextpage);
    }
    private void AñadirLineas3(PdfPage page){
        PdfCanvas canvas = new PdfCanvas(page);
        // Create a 100% Magenta color
        canvas
                .moveTo(350, 688+0)
                .lineTo(430, 688+0)
                .moveTo(350, 701+0)
                .lineTo(430, 701+0)
                .moveTo(350, 714+0)
                .lineTo(430, 714+0) ///CUADRO1

                .moveTo(180, 621+0)
                .lineTo(380, 621+0)
                .moveTo(180, 608+0)
                .lineTo(380, 608+0)
                .moveTo(460, 608+0)
                .lineTo(560, 608+0)
                .moveTo(120, 595+0)
                .lineTo(250, 595+0)
                .moveTo(315, 595+0)
                .lineTo(380, 595+0)
                .moveTo(460, 595+0)
                .lineTo(560, 595+0)
                .moveTo(120, 582+0)
                .lineTo(560, 582+0) ///CUADRO2


                .moveTo(145, 621-115+30)
                .lineTo(380, 621-115+30)
                .moveTo(100, 608-115+30)
                .lineTo(380, 608-115+30)
                .moveTo(473, 608-115+30)
                .lineTo(560, 608-115+30)
                .moveTo(100, 595-115+30)
                .lineTo(250, 595-115+30)
                .moveTo(337, 595-115+30)
                .lineTo(380, 595-115+30)
                .moveTo(473, 595-115+30)
                .lineTo(560, 595-115+30)
                .moveTo(100, 582-115+30)
                .lineTo(250, 582-115+30)
                .lineTo(250, 582-115+30)
                .moveTo(337, 582-115+30)
                .lineTo(380, 582-115+30)
                .moveTo(473, 582-115+30)
                .lineTo(560, 582-115+30)///CUADRO4


                .setLineWidth(1).closePathStroke();


    }
    private void AgregaContenidoCuadro1Archivo3(PdfPage page,String Ticket){
        try {
            SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket,Context.MODE_PRIVATE);

            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
            AgregaContenidoCuadroDatosRIGHT(bold, page, 240, 726+35, 325, 13, Ticket,10);
            //AgregaContenidoCuadroDatosRIGHT(font, page, 240, 726+200, 310, 13, sh.getString("DatosTicket3",""),9);
            AgregaContenidoCuadroDatosRIGHT(bold, page, 300, 713+0, 47, 13,"No. de oficio:" ,8);
            AgregaContenidoCuadroDatosRIGHT(bold, page, 300, 700+0, 47, 13, "Lugar:",8);
            AgregaContenidoCuadroDatosRIGHT(bold, page, 300, 687+0, 47, 13, "Fecha:",8);
           /* AgregaContenidoCuadroDatosCENTER(font, page, 40, 670+180, 510, 13,"En seguimiento a la solicitud de instalación de equipo según oficio DGIV.-"+sh.getString("DatosTicket12","")+" de fecha: "
                    +sh.getString("DatosTicket12","")+" A continuacion presento lo siguiente:" ,7);*/
            AgregaContenidoCuadroDatosLEFT(font, page, 40, 651+0, 520, 36,"En seguimiento a la solicitud de instalación de equipo según oficio DGIV.-"+sh.getString("DatosTicket12","")+" de fecha: "
                    +sh.getString("DatosTicket13","")+" A continuación presento lo siguiente:" ,10);

            AgregaContenidoCuadroDatosLEFT(font, page, 350, 713+0, 150, 13, "DGIV.-"+sh.getString("DatosTicket12",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 350, 700+0, 150, 13, sh.getString("DatoBarco25",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 350, 687+0, 150, 13, sh.getString("FechaCerrado",""),8);

            //AgregaContenidoCuadroDatosLEFT(font, page, 445, 713, 100, 13, " Aqui va el número",8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 445, 700, 100, 13, " Aqui va la localidad",8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 445, 687, 100, 13, " 00-00-0000",8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 475, 736, 100, 13,sh.getString("DatosTicket13",""),8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 475, 716, 120, 13, sh.getString("DatosTicket5",""),8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void AgregaContenidoCuadro2Archivo3(PdfPage page,String Ticket){
        try {
            SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket,Context.MODE_PRIVATE);

            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
            PdfFont italic = PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC);

            AgregaContenidoCuadroDatosLEFT(bold, page, 40, 620+0, 140, 13,"Titular del permisionario/concesión:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 390, 620+0, 70, 13,"Persona Física",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 490, 620+0, 70, 13,"Persona Moral",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 390, 607+0, 70, 13,"R.N.P.A. Titular:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 40, 607+0, 140, 13,"Representante Legal (Persona Moral):",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 40, 594+0, 80, 13,"Correo electrónico:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 260, 594+0, 55, 13,"Teléfono Fijo:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 390, 594+0, 60, 13,"Teléfono Móvil:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 40, 581+0, 80, 13,"Domicilio:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 120, 568+0, 440, 13,"Calle y No.                Colonia                Municipio                Ciudad o Puerto                Entidad Federativa                Código Postal",8);

            AgregaContenidoCuadroDatosLEFT(font, page, 180, 620+0, 200, 13, sh.getString("DatoBarco21",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 180, 607+0, 200, 13, sh.getString("DatoBarco28",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,460, 607+0, 100, 13, sh.getString("DatoBarco20",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,120, 594+0, 140, 13, sh.getString("DatoBarco29",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,315, 594+0, 75, 13, sh.getString("DatoBarco27",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,460, 594+0, 100, 13, sh.getString("DatoBarco67",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,120, 581+0, 440, 13, sh.getString("DatoBarco26",""),8);


            /*AgregaContenidoCuadroDatosLEFT(font, page, 180, 620, 200, 13, "Aqui va el nombre del permisionario/Conseción.",8);
            AgregaContenidoCuadroDatosLEFT(font, page, 180, 607, 200, 13, "Aqui va el representante legal (persona moral).",8);
            AgregaContenidoCuadroDatosLEFT(font, page,460, 607, 100, 13, "Aqui va el R.N.P.A",8);
            AgregaContenidoCuadroDatosLEFT(font, page,120, 594, 140, 13, "Aqui va el correo electronico",8);
            AgregaContenidoCuadroDatosLEFT(font, page,315, 594, 75, 13, "(000) 0000000",8);
            AgregaContenidoCuadroDatosLEFT(font, page,460, 594, 100, 13, "(000) 0000000",8);
            AgregaContenidoCuadroDatosLEFT(font, page,120, 581, 440, 13, "Aqui va la direccion",8);*/
            //AgregaContenidoCuadroDatosLEFT(font, page, 475, 736, 100, 13,sh.getString("DatosTicket13",""),8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 475, 716, 120, 13, sh.getString("DatosTicket5",""),8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void AgregaContenidoCuadro4Archivo3(PdfPage page,String Ticket){
        try {
            SharedPreferences sh = getApplicationContext().getSharedPreferences(Ticket,Context.MODE_PRIVATE);

            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
            PdfFont italic = PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC);

            AgregaContenidoCuadroDatosLEFT(bold, page, 40, 620-115+30, 100, 13,"Nombre de la embarcación:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 390, 607-115+30, 82, 13,"R.N.P.A. Embarcación:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 40, 607-115+30, 55, 13,"Puerto Base:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 40, 594-115+30, 55, 13,"Matrícula:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 260, 594-115+30, 55, 13,"Tonelaje Bruto:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 390, 594-115+30, 60, 13,"Tonelaje Neto:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 40, 581-115+30, 55, 13,"Marca Motor:",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 260, 581-115+30, 76, 13,"Potencia Motor (HP):",8);
            AgregaContenidoCuadroDatosLEFT(bold, page, 390, 581-115+30, 65, 13,"Eslora (Mts):",8);

            AgregaContenidoCuadroDatosLEFT(font, page, 145, 620-115+30, 235, 13, sh.getString("DatoBarco3",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page, 100, 607-115+30, 280, 13, sh.getString("DatoBarco2",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,473, 607-115+30, 80, 13, sh.getString("DatoBarco4",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,100, 594-115+30, 160, 13,sh.getString("DatoBarco5",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,337, 594-115+30, 43, 13, sh.getString("DatoBarco11",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,473, 594-115+30, 80, 13, sh.getString("DatoBarco12",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,100, 581-115+30, 460, 13, sh.getString("DatoBarco18",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,337, 581-115+30, 43, 13, sh.getString("DatoBarco19",""),8);
            AgregaContenidoCuadroDatosLEFT(font, page,473, 581-115+30, 80, 13, sh.getString("DatoBarco15",""),8);


            //AgregaContenidoCuadroDatosLEFT(font, page, 475, 736, 100, 13,sh.getString("DatosTicket13",""),8);
            //AgregaContenidoCuadroDatosLEFT(font, page, 475, 716, 120, 13, sh.getString("DatosTicket5",""),8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void CuadroDatosArchivo3(PdfCanvas pdfCanvas,PdfFont font,PdfFont bold,int x,int y,int w,int h,ImageData imageData){
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke().closePathStroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text title = new Text("REPORTE FOTOGÁFICO DE INSTALACIÓN").setFont(bold).setFontColor(ColorConstants.BLUE).setFontSize(10);
        Text title2 = new Text("").setFont(font).setFontColor(ColorConstants.BLACK).setFontSize(10);
        Text Space = new Text(".          .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);
        Paragraph a = new Paragraph().add(title).add(Space).setTextAlignment(TextAlignment.RIGHT);
        InsertaImagenLogo(imageData,canvas,50,687,170);
        canvas.add(a);
        canvas.close();
    }


    ////CONVENIO/////
    public void CreaArchivo4(String Ticket)throws IOException {
        String Name = Ticket+"(Convenio).pdf";
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(Ticket, Context.MODE_PRIVATE);
        File file = new File(directory, Name);
        // Tamaño Oficio = 612*1008  Tamaño Carta 612*792
        Rectangle Tamaño = new Rectangle(612, 792);
        PdfDocument pdf = new PdfDocument(new PdfWriter(file));
        pdf.setDefaultPageSize(new PageSize(Tamaño));
        PdfPage page = pdf.addNewPage();
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);


        InsertaPie(pdf);
        pdf.close();
    }


}
