package com.bmr.conapesca.Documentos;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.content.Context;
import android.content.ContextWrapper;
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

public class Interno {
    public void CreaRF(String[]Datos, ImageData imageData, int id, String[]Comentarios)throws IOException {
        //String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
        //String  rutacarpetaImagenes = "CONAPESCA/"+Datos[0]+"/"+Datos[1]+"/" + Datos[1] + "(CF)/";
        //String  rutacarpeta = "CONAPESCA/"+Datos[0]+"/"+Datos[1]+"/" ;
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
        //Fotos(page,pdf,Datos,font,bold,imageData,id,Comentarios,rutacarpetaImagenes);
        Fotos2(page,pdf,Datos,font,bold,imageData,id,Comentarios,Datos[3]);
        InsertaPiedePagina(pdf);
        pdf.close();
    }
    private void CuadroDatos(String[] Datos,PdfCanvas pdfCanvas,PdfFont font,PdfFont bold,int x,int y,int w,int h,ImageData imageData){
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke().closePathStroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text title = new Text("REPORTE FOTOGRÁFICO DE SERVICIO INTERNO").setFont(bold).setFontColor(ColorConstants.RED).setFontSize(10);
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
        Text DatosEquipo = new Text("Datos del equipo").setFont(bold).setFontSize(8);
        Paragraph b = new Paragraph().add(DatosEquipo).setTextAlignment(TextAlignment.CENTER);
        canvas2.add(b).close();
        canvas.close();
        canvas2.close();
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
                InsertaBordeImagen(page, 50,380,230,172);
                AgregaComentarios(font,page,50, 329, 232, 50,Descripcion[Foto1-1]);
                InsertImagenExtra (page, 50,380,230,172,rutacarpeta,Nombre1+".png");

            }
            else{
                InsertaBordeImagen(page, 59, 509, 212, 144);
                AgregaComentarios(font, page, 60, 460, 212, 50, Descripcion[Foto1 - 1]);
                InsertImagenExtra(page, 59, 509, 212, 144, rutacarpeta, Nombre1 + ".png");
            }


        }
        else if(Numero ==2){
            if (fisrt){
                InsertaBordeImagen(page, 50,380,230,172);
                InsertaBordeImagen(page, 320,380,230,172);
                AgregaComentarios(font,page,50, 329, 232, 50,Descripcion[Foto1-1]);
                AgregaComentarios(font,page,320,329,230,50,Descripcion[Foto2-1]);
                InsertImagenExtra (page, 50,380,230,172,rutacarpeta,Nombre1+".png");
                InsertImagenExtra (page, 320,380,230,172,rutacarpeta,Nombre2+".png");


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
                InsertaBordeImagen(page, 50,379,230,172);
                InsertaBordeImagen(page, 320,379,230,172);
                InsertaBordeImagen(page, 175,160,230,172);


                AgregaComentarios(font,page,50, 329, 232, 50,Descripcion[Foto1-1]);
                AgregaComentarios(font,page,320,329,230,50,Descripcion[Foto2-1]);
                AgregaComentarios(font,page,175,99,230,50,Descripcion[Foto3-1]);

                InsertImagenExtra (page, 50,379,230,172,rutacarpeta,Nombre1+".png");
                InsertImagenExtra (page, 320,379,230,172,rutacarpeta,Nombre2+".png");
                InsertImagenExtra (page, 175,160,230,172,rutacarpeta,Nombre3+".png");


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
                InsertaBordeImagen(page, 50,380,230,172);
                InsertaBordeImagen(page, 320,380,230,172);
                InsertaBordeImagen(page, 50,160,230,172);
                InsertaBordeImagen(page, 320,160,230,172);

                AgregaComentarios(font,page,50, 329, 232, 50,Descripcion[Foto1-1]);
                AgregaComentarios(font,page,320,329,230,50,Descripcion[Foto2-1]);
                AgregaComentarios(font,page,50,99,230,50,Descripcion[Foto3-1]);
                AgregaComentarios(font,page,320,99,230,50,Descripcion[Foto4-1]);

                InsertImagenExtra (page, 50,380,230,172,rutacarpeta,Nombre1+".png");
                InsertImagenExtra (page, 320,380,230,172,rutacarpeta,Nombre2+".png");
                InsertImagenExtra (page, 50,160,230,172,rutacarpeta,Nombre3+".png");
                InsertImagenExtra (page, 320,160,230,172,rutacarpeta,Nombre4+".png");
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
        PdfPage nextpage = pdf.addNewPage();
        PdfCanvas pdfCanvas = new PdfCanvas(nextpage);
        Rectangle rectangle = new Rectangle(36, 700, 530, 100);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text NReporte = new Text("No. de reporte:").setFont(bold).setFontSize(9);
        Text title = new Text("REPORTE FOTOGRÁFICO DE MANTENIMIENTO CORRECTIVO").setFont(bold).setFontColor(ColorConstants.RED).setFontSize(10);
        Text TICKET = new Text(Datos[1]).setFont(font).setUnderline().setFontSize(9);

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



}
