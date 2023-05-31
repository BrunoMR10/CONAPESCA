package com.bmr.conapesca.Documentos;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.os.Environment;

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

public class Correctivo{
    public void CreaRF(String[]Datos, ImageData imageData, int id, String[]Comentarios)throws IOException {
        //String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
        //String  rutacarpetaImagenes = "CONAPESCA/"+Datos[0]+"/"+Datos[1]+"/" + Datos[1] + "(CF)/";
        //String  rutacarpeta = "CONAPESCA/"+Datos[0]+"/"+Datos[1]+"/" ;
        String Name = Datos[3]+"(RF).pdf";
        String Ticket = Datos[3];
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(Datos[3], Context.MODE_PRIVATE);
        File file = new File(directory, Name);
        PdfDocument pdf = new PdfDocument(new PdfWriter(file));
        PdfPage page = pdf.addNewPage();
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);

        AñadirLineas3(page);
        CuadroDatosArchivo3(pdfCanvas,font,bold,36, 683, 530, 77,imageData);
        CuadroDatos2(pdfCanvas,bold,36, 565, 530, 70);
        CuadroDatos4(pdfCanvas,bold,36, 460+30, 530, 60);
        AgregaContenidoCuadro1Archivo3(page,Ticket);
        AgregaContenidoCuadro2Archivo3(page,Ticket);
        AgregaContenidoCuadro4Archivo3(page,Ticket);
        //Fotos(page,pdf,Datos,font,bold,imageData,id,Comentarios,rutacarpetaImagenes);
        Fotos2(page,pdf,Datos,font,bold,imageData,id,Comentarios,Datos[3]);
        InsertaPiedePagina(pdf);
        pdf.close();
    }



    private void InsertaImagenLogo(ImageData imageData,Canvas canvas,int left,int bottom,int width){
        Image img = new Image(imageData,left,bottom,width);
        canvas.add(img).close();
    }
    private  void Fotos2(PdfPage page,PdfDocument pdf,String [] Datos,PdfFont font,PdfFont bold,ImageData imageData,int id,String[]Comentarios,String rutacarpetaImagenes){
        String[]Fotos = new String[id];
        String[]ComentariosNew = new String[id];
        PdfPage nextpage;
        int NumerorealFotos =0;
        int NumberFotos;
        for (int i =0; i<id;i++){
            if (Comentarios[i].equals(""))System.out.println("FotoDescartada");
            else {
                ComentariosNew[NumerorealFotos]=Comentarios[i];
                Fotos[NumerorealFotos]="Foto"+String.valueOf(i+1);
                System.out.println(Fotos[NumerorealFotos]);
                NumerorealFotos++;
            }
        }
        System.out.println(NumerorealFotos);


        int Paginasextran;
        if ((NumerorealFotos+2) % 6 == 0) {
            Paginasextran = ((NumerorealFotos+2) / 6);
        }
        else {
            Paginasextran = ((NumerorealFotos+2) / 6)+1;

        }

        System.out.println("Paginas extra"+Paginasextran);
        NumberFotos = 6;
        int ident = 0;
        int b =0;
        Boolean first;
        for (int i = 0; i < Paginasextran; i++) {
            if(i==0){
                if (NumerorealFotos <= 4){
                    NumberFotos = 4 - (4 - NumerorealFotos);
                    nextpage = page;
                    first = true;
                    InsertaFotos2(nextpage, NumberFotos, rutacarpetaImagenes, ident, font, ComentariosNew,first,i,Fotos);
                }

                else{
                    NumberFotos = 4;
                    nextpage = page;
                    first = true;
                    InsertaFotos2(nextpage, NumberFotos, rutacarpetaImagenes, ident, font, ComentariosNew,first,i,Fotos);
                }


            }
            else {
                NumberFotos = 6;
                if (i == Paginasextran - 1) {

                    //NumberFotos =  ((Paginasextran * 6) - (id-4)-6);
                    NumberFotos = NumerorealFotos +(4-b)-(Paginasextran*4);
                }
                first = false;
                nextpage = CuadroNextPage(pdf, Datos, font, bold, imageData);
                InsertaFotos2(nextpage, NumberFotos, rutacarpetaImagenes, ident, font, ComentariosNew,first,i,Fotos);
                b=b+2;
            }



            ident++;
        }
    }
    private void InsertaFotos2(PdfPage page,int Numero,String rutacarpeta,int extra,PdfFont font,String [] Descripcion,Boolean fisrt,int i,String[]NombresFoto){
        String Nombre1 = null,Nombre2 = null,Nombre3=null,Nombre4=null,Nombre5=null,Nombre6=null;
        int base = 9;
        if (i==0){
            if (Numero == 1){Nombre1 = NombresFoto[0];}
            else if (Numero ==2){Nombre1 = NombresFoto[0];Nombre2 = NombresFoto[1];}
            else if(Numero==3){Nombre1 = NombresFoto[0];Nombre2 = NombresFoto[1];Nombre3 = NombresFoto[2];}
            else{Nombre1 = NombresFoto[0];Nombre2 = NombresFoto[1];Nombre3 = NombresFoto[2];Nombre4 = NombresFoto[3];}
        }
        else if (i==1) {

            if (Numero == 1){Nombre1 = NombresFoto[3+Numero];}
            else if(Numero ==2){Nombre1 = NombresFoto[3+Numero-1];Nombre2 = NombresFoto[(3+Numero)];}
            else if(Numero==3){Nombre1 = NombresFoto[3+Numero-2];Nombre2 = NombresFoto[3+Numero-1];Nombre3 = NombresFoto[3+Numero];}
            else if(Numero==4){
                Nombre1 = NombresFoto[3+Numero-3];Nombre2 = NombresFoto[3+Numero-2];Nombre3 = NombresFoto[3+Numero-1];Nombre4= NombresFoto[3+Numero];
            }
            else if(Numero==5){
                Nombre1 = NombresFoto[3+Numero-4];Nombre2 = NombresFoto[3+Numero-3];Nombre3 = NombresFoto[3+Numero-2];Nombre4= NombresFoto[3+Numero-1];
                Nombre5= NombresFoto[3+Numero];
            }
            else{
                Nombre1 = NombresFoto[3+Numero-5];Nombre2 = NombresFoto[3+Numero-4];Nombre3 = NombresFoto[3+Numero-3];Nombre4= NombresFoto[3+Numero-2];
                Nombre5= NombresFoto[3+Numero-1]; Nombre6= NombresFoto[3+Numero];
            }
        }
        else{
            if (Numero == 1){Nombre1 = NombresFoto[base+(6*(i-2))+Numero];}
            else if(Numero ==2){Nombre1 = NombresFoto[base+(6*(i-2))+Numero-1];Nombre2 = NombresFoto[base+(6*(i-2))+Numero];}
            else if(Numero==3){Nombre1 = NombresFoto[base+(6*(i-2))+Numero-2];Nombre2 = NombresFoto[base+(6*(i-2))+Numero-1];Nombre3 =NombresFoto[base+(6*(i-2))+Numero];}
            else if(Numero==4){
                Nombre1 = NombresFoto[base+(6*(i-2))+Numero-3];Nombre2 = NombresFoto[base+(6*(i-2))+Numero-2];
                Nombre3 = NombresFoto[base+(6*(i-2))+Numero-1];Nombre4= NombresFoto[base+(6*(i-2))+Numero];
            }
            else if(Numero==5){
                Nombre1 = NombresFoto[base+(6*(i-2))+Numero-4];Nombre2 = NombresFoto[base+(6*(i-2))+Numero-3];Nombre3 = NombresFoto[base+(6*(i-2))+Numero-2];
                Nombre4= NombresFoto[base+(6*(i-2))+Numero-1];
                Nombre5= NombresFoto[base+(6*(i-2))+Numero];
            }
            else{
                Nombre1 =NombresFoto[base+(6*(i-2))+Numero-5];Nombre2 = NombresFoto[base+(6*(i-2))+Numero-4];Nombre3 = NombresFoto[base+(6*(i-2))+Numero-3];
                Nombre4= NombresFoto[base+(6*(i-2))+Numero-2];
                Nombre5= NombresFoto[base+(6*(i-2))-1]; Nombre6= NombresFoto[base+(6*(i-2))+Numero];
            }
        }
        System.out.println("Nombre"+Nombre1);
        System.out.println("Carpeta:"+rutacarpeta);


        int Identificador;
        int Foto1;
        int Foto2 ;
        int Foto3;
        int Foto4 ;
        int Foto5 ;
        int Foto6;
        if (fisrt) {
            Identificador = extra * 4;
            Foto1 = Identificador + 1;
            Foto2 = Identificador + 2;
            Foto3 = Identificador + 3;
            Foto4 = Identificador + 4;
            Foto5 = Identificador + 5+4;
            Foto6 = Identificador + 6+4;
        }
        else{
            Identificador = extra * 6;
            Foto1 = Identificador -1;
            Foto2 = Identificador ;
            Foto3 = Identificador +1;
            Foto4 = Identificador + 2;
            Foto5 = Identificador + 3;
            Foto6 = Identificador + 4;
        }
        if (Numero==1){
            if (fisrt) {
                InsertaBordeImagen(page, 50,380-67,230,172);
                AgregaComentarios(font,page,50, 329-67, 232, 50,Descripcion[Foto1-1]);
                InsertImagenExtra (page, 50,380-67,230,172,rutacarpeta,Nombre1+".png");

            }
            else{
                InsertaBordeImagen(page, 59, 509, 212, 144);
                AgregaComentarios(font, page, 60, 460, 212, 50, Descripcion[Foto1 - 1]);
                InsertImagenExtra(page, 59, 509, 212, 144, rutacarpeta, Nombre1 + ".png");
            }


        }
        else if(Numero ==2){
            if (fisrt){
                InsertaBordeImagen(page, 50,380-67,230,172);
                InsertaBordeImagen(page, 320,380-67,230,172);
                AgregaComentarios(font,page,50, 329-67, 232, 50,Descripcion[Foto1-1]);
                AgregaComentarios(font,page,320,329-67,230,50,Descripcion[Foto2-1]);
                InsertImagenExtra (page, 50,380-67,230,172,rutacarpeta,Nombre1+".png");
                InsertImagenExtra (page, 320,380-67,230,172,rutacarpeta,Nombre2+".png");


            }

            else{
                InsertaBordeImagen(page, 59, 509, 212, 144);
                InsertaBordeImagen(page, 329, 509, 212, 144);
                AgregaComentarios(font, page, 60, 460, 212, 50, Descripcion[Foto1 - 1]);
                AgregaComentarios(font, page, 330, 460, 212, 50, Descripcion[Foto2 - 1]);
                InsertImagenExtra(page, 59, 509, 212, 144, rutacarpeta, Nombre1 + ".png");
                InsertImagenExtra(page, 329, 509, 212, 144, rutacarpeta, Nombre2 + ".png");
            }


        }
        else if (Numero == 3){

            if (fisrt){
                InsertaBordeImagen(page, 50,379-67,230,172);
                InsertaBordeImagen(page, 320,379-67,230,172);
                InsertaBordeImagen(page, 175,160-67,230,172);


                AgregaComentarios(font,page,50, 329-67, 232, 50,Descripcion[Foto1-1]);
                AgregaComentarios(font,page,320,329-67,230,50,Descripcion[Foto2-1]);
                AgregaComentarios(font,page,175,99-67,230,50,Descripcion[Foto3-1]);

                InsertImagenExtra (page, 50,379-67,230,172,rutacarpeta,Nombre1+".png");
                InsertImagenExtra (page, 320,379-67,230,172,rutacarpeta,Nombre2+".png");
                InsertImagenExtra (page, 175,160-67,230,172,rutacarpeta,Nombre3+".png");


            }else {
                InsertaBordeImagen(page, 59, 509, 212, 144);
                InsertaBordeImagen(page, 329, 509, 212, 144);
                InsertaBordeImagen(page, 180, 309, 212, 144);
                AgregaComentarios(font, page, 60, 460, 212, 50, Descripcion[Foto1 - 1]);
                AgregaComentarios(font, page, 330, 460, 212, 50, Descripcion[Foto2 - 1]);
                AgregaComentarios(font, page, 180, 260, 212, 50, Descripcion[Foto3 - 1]);
                InsertImagenExtra(page, 59, 509, 212, 144, rutacarpeta, Nombre1 + ".png");
                InsertImagenExtra(page, 329, 509, 212, 144, rutacarpeta, Nombre2 + ".png");
                InsertImagenExtra(page, 179, 309, 212, 144, rutacarpeta, Nombre3 + ".png");
            }

        }
        else if (Numero == 4){
            if (fisrt){
                InsertaBordeImagen(page, 50,380-67,230,172);
                InsertaBordeImagen(page, 320,380-67,230,172);
                InsertaBordeImagen(page, 50,160-67,230,172);
                InsertaBordeImagen(page, 320,160-67,230,172);

                AgregaComentarios(font,page,50, 329-67, 232, 50,Descripcion[Foto1-1]);
                AgregaComentarios(font,page,320,329-67,230,50,Descripcion[Foto2-1]);
                AgregaComentarios(font,page,50,99-67,230,50,Descripcion[Foto3-1]);
                AgregaComentarios(font,page,320,99-67,230,50,Descripcion[Foto4-1]);

                InsertImagenExtra (page, 50,380-67,230,172,rutacarpeta,Nombre1+".png");
                InsertImagenExtra (page, 320,380-67,230,172,rutacarpeta,Nombre2+".png");
                InsertImagenExtra (page, 50,160-67,230,172,rutacarpeta,Nombre3+".png");
                InsertImagenExtra (page, 320,160-67,230,172,rutacarpeta,Nombre4+".png");
            }else {
                InsertaBordeImagen(page, 59, 509, 212, 144);
                InsertaBordeImagen(page, 329, 509, 212, 144);
                InsertaBordeImagen(page, 59, 309, 212, 144);
                InsertaBordeImagen(page, 329, 309, 212, 144);
                AgregaComentarios(font, page, 60, 460, 212, 50, Descripcion[Foto1 - 1]);
                AgregaComentarios(font, page, 330, 460, 212, 50, Descripcion[Foto2 - 1]);
                AgregaComentarios(font, page, 60, 260, 212, 50, Descripcion[Foto3 - 1]);
                AgregaComentarios(font, page, 330, 260, 212, 50, Descripcion[Foto4 - 1]);
                InsertImagenExtra(page, 59, 509, 212, 144, rutacarpeta, Nombre1 + ".png");
                InsertImagenExtra(page, 329, 509, 212, 144, rutacarpeta, Nombre2 + ".png");
                InsertImagenExtra(page, 59, 309, 212, 144, rutacarpeta, Nombre3 + ".png");
                InsertImagenExtra(page, 329, 309, 212, 144, rutacarpeta, Nombre4 + ".png");
            }


        }
        else if (Numero == 5){

            InsertaBordeImagen(page, 59, 509,212,144);
            InsertaBordeImagen(page, 329, 509,212,144);
            InsertaBordeImagen(page, 59, 309,212,144);
            InsertaBordeImagen(page, 329, 309,212,144);
            InsertaBordeImagen(page, 180, 109,212,144);
            AgregaComentarios(font,page,60, 460, 212, 50,Descripcion[Foto1-1]);
            AgregaComentarios(font,page,330, 460, 212, 50,Descripcion[Foto2-1]);
            AgregaComentarios(font,page,60, 260, 212, 50,Descripcion[Foto3-1]);
            AgregaComentarios(font,page,330, 260, 212, 50,Descripcion[Foto4-1]);
            AgregaComentarios(font,page,180, 60, 212, 50,Descripcion[Foto5-1]);
            InsertImagenExtra (page, 59, 509,212,144,rutacarpeta,Nombre1+".png");
            InsertImagenExtra (page, 329, 509,212,144,rutacarpeta,Nombre2+".png");
            InsertImagenExtra (page, 59, 309,212,144,rutacarpeta,Nombre3+".png");
            InsertImagenExtra (page, 329, 309,212,144,rutacarpeta,Nombre4+".png");
            InsertImagenExtra (page, 180, 109,212,144,rutacarpeta,Nombre5+".png");
        }

        else{

            InsertaBordeImagen(page, 59, 509,212,144);
            InsertaBordeImagen(page, 329, 509,212,144);
            InsertaBordeImagen(page, 59, 309,212,144);
            InsertaBordeImagen(page, 329, 309,212,144);
            InsertaBordeImagen(page, 59, 109,212,144);
            InsertaBordeImagen(page, 329, 109,212,144);
            AgregaComentarios(font,page,60, 460, 212, 50,Descripcion[Foto1-1]);
            AgregaComentarios(font,page,330, 460, 212, 50,Descripcion[Foto2-1]);
            AgregaComentarios(font,page,60, 260, 212, 50,Descripcion[Foto3-1]);
            AgregaComentarios(font,page,330, 260, 212, 50,Descripcion[Foto4-1]);
            AgregaComentarios(font,page,60, 60, 212, 50,Descripcion[Foto5-1]);
            AgregaComentarios(font,page,330, 60, 212, 50,Descripcion[Foto6-1]);
            InsertImagenExtra (page, 59, 509,212,144,rutacarpeta,Nombre1+".png");
            InsertImagenExtra (page, 329, 509,212,144,rutacarpeta,Nombre2+".png");
            InsertImagenExtra (page, 59, 309,212,144,rutacarpeta,Nombre3+".png");
            InsertImagenExtra (page, 329, 309,212,144,rutacarpeta,Nombre4+".png");
            InsertImagenExtra (page, 59, 109,212,144,rutacarpeta,Nombre5+".png");
            InsertImagenExtra (page, 329, 109,212,144,rutacarpeta,Nombre6+".png");
        }

    }

    private PdfPage CuadroNextPage(PdfDocument pdf,String [] Datos,PdfFont font,PdfFont bold,ImageData imageData){
        SharedPreferences sh = getApplicationContext().getSharedPreferences(Datos[3],Context.MODE_PRIVATE);
        PdfPage nextpage = pdf.addNewPage();
        PdfCanvas pdfCanvas = new PdfCanvas(nextpage);
        Rectangle rectangle = new Rectangle(36, 700, 530, 100);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text NReporte = new Text("No. de oficio:").setFont(bold).setFontSize(9);
        Text title = new Text("REPORTE FOTOGRÁFICO DE SERVICIO CORRECTIVO").setFont(bold).setFontColor(ColorConstants.BLUE).setFontSize(10);
        Text TICKET = new Text("DGIV-" +sh.getString("DatosTicket12","")).setFont(font).setUnderline().setFontSize(9);

        Text Space = new Text(".  .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);

        Paragraph a = new Paragraph().add(title).add(Space).setTextAlignment(TextAlignment.RIGHT);
        Paragraph b = new Paragraph().add(NReporte).add(Space).add(TICKET).add(Space).add(Space).add(Space).setTextAlignment(TextAlignment.RIGHT);
        InsertaImagenLogo(imageData,canvas,50,715,190);
        canvas.add(a).close();
        canvas.add(b).close();
        canvas.close();

        return (nextpage);
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
    private void InsertImagenExtra (PdfPage page, int x, int y,int w,int h,String rutacarpeta,String nombre){
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
        ImageData data = null;
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(rutacarpeta, Context.MODE_PRIVATE);
        File file = new File(directory, nombre);
        if (file.exists())System.out.println("File encontrado");
        try {
            //data = ImageDataFactory.create(ExternalStorageDirectory + rutacarpeta + nombre);
            data = ImageDataFactory.create(file.getAbsolutePath());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Image img = new Image(data,x+1,y+1,w-2);
        img.setHeight(h-2);
        canvas.add(img).close();
        canvas.close();

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

/////
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
    private void CuadroDatosArchivo3(PdfCanvas pdfCanvas,PdfFont font,PdfFont bold,int x,int y,int w,int h,ImageData imageData){
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke().closePathStroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text title = new Text("REPORTE FOTOGRÁFICO DE SERVICIO CORRECTIVO").setFont(bold).setFontColor(ColorConstants.BLUE).setFontSize(10);
        Text title2 = new Text("").setFont(font).setFontColor(ColorConstants.BLACK).setFontSize(10);
        Text Space = new Text(".          .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);
        Paragraph a = new Paragraph().add(title).add(Space).setTextAlignment(TextAlignment.RIGHT);
        InsertaImagenLogo(imageData,canvas,50,687,170);
        canvas.add(a);
        canvas.close();
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
}