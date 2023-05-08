package com.bmr.conapesca.Documentos;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import static java.lang.Integer.parseInt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bmr.conapesca.Datos.CaracteristicasCuadros;
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
import com.itextpdf.layout.element.ILargeElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

public class PruebaMetododocumentos {
    CaracteristicasCuadros caracteristicasCuadros = new CaracteristicasCuadros();

    public void CuerpoDocumento(String Ticket) throws IOException {
        String Name = Ticket+"(Convenio).pdf";
        String [] c = caracteristicasCuadros.Estructura;
        @SuppressLint("RestrictedApi") ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(Ticket, Context.MODE_PRIVATE);
        File file = new File(directory, Name);
        PdfDocument pdf = new PdfDocument(new PdfWriter(file));
        int hdoc= parseInt(c[0]);
        int wdoc= parseInt(c[1]);
        Rectangle TamañoDocumento = new Rectangle(wdoc, hdoc);
        pdf.setDefaultPageSize(new PageSize(TamañoDocumento));
        CreaPagina(pdf);
        //EstructuraCuerpo(pdf);
        pdf.close();
    }
    public void EstructuraCuerpo (PdfDocument pdf) throws IOException {
        String [] c = caracteristicasCuadros.Estructura;
        int cantidad_cuadros = (c.length-4)/14;//Se restan las caracteristicas generales
        ////ObteniendoCaracteriticas de archivo
        System.out.println(" Cuadros solicitados: "+cantidad_cuadros);

        int hdoc= parseInt(c[0]);
        int wdoc= parseInt(c[1]);
        int margin = parseInt(c[3]);
        int conteocuadros =1;
        int wsquare =0,hsquare=0,hparcial=0;
        int space =0,xsquare=0,ysquare=0;

        ///
        PdfPage page = pdf.addNewPage();
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        ///
        for (int i = 4; i<c.length; i = i+14){
            //AlturaParcial
            if (i>4){
                if (c[i].equals("true")) {System.out.println("Horizontal"); space = 0;}
                else{ hparcial = ysquare; space = parseInt(c[2]);}

            }
            else {hparcial=hdoc; space = margin;}
            //Dimensiones
            wsquare =parseInt(c[i+3]);  hsquare=parseInt(c[i+4]);
            if (wsquare>wdoc-(2*margin)) wsquare = wdoc-(2*margin);
            if (hsquare>hdoc-(2*margin)) hsquare = hdoc-(2*margin);
            ////Alineacion

            //int [] xy = SetAlineacion(c[i],hparcial,margin, wdoc,space,hsquare,wsquare);
            //xsquare=xy[0];
            //ysquare=xy[1];
            System.out.println(xsquare+","+ysquare+" Cuadro: "+conteocuadros);
            System.out.println(" Dimensiones: "+wsquare+","+hsquare);
            /////Deteccion de nueva pagina
            if (ysquare<=0){
                hparcial=hdoc;
                space = margin;
                System.out.println("Pagina Nueva");
               // xy = SetAlineacion(c[i],hparcial,margin, wdoc,space,hsquare,wsquare);
               // xsquare=xy[0];
                //ysquare=xy[1];
                page = pdf.addNewPage();
                pdfCanvas = new PdfCanvas(page);

            }
            System.out.println("--------------------------------------------");
            //Creacion de rectangulo
            Rectangle rectangle = new Rectangle(xsquare, ysquare, wsquare, hsquare);
            pdfCanvas.rectangle(rectangle);
            Canvas canvas = new Canvas(pdfCanvas, rectangle);
            Image image;
            ///Marco
            if (c[i+5].equals("true")) pdfCanvas.setLineWidth(parseInt(c[i+6])).setColor(ColorConstants.BLACK,false).stroke().closePathStroke();
            else pdfCanvas.setLineWidth(0).setColor(ColorConstants.WHITE,false).stroke().closePathStroke();
            ////Añade Imagen
            if (c[i+10].equals("imagen")){
                System.out.println("Insertando Imagen");
               image = new Image(Logo(),xsquare,ysquare,wsquare);  image.setHeight(hsquare);
                //else image = new Image(Foto(c[i+6],c[i+8]),xsquare,ysquare,wsquare);  image.setHeight(hsquare);
                canvas.add(image).close();
                canvas.close();
            }
            ////Añade Texto
            else{
                //Text Comen1 = new Text(c[i+8]).setFont(font).setFontSize(size);
                Paragraph a;
                Text Comen1 = new Text(c[i+9]).setFont(pdfFont(c[i+7])).setFontSize(parseInt(c[i+8]));
                if ((c[i+11]).equals("right"))  a = new Paragraph().add(Comen1).setTextAlignment(TextAlignment.RIGHT);
                else if ((c[i+11]).equals("left"))  a = new Paragraph().add(Comen1).setTextAlignment(TextAlignment.LEFT);
                else if ((c[i+11]).equals("justified"))  a = new Paragraph().add(Comen1).setTextAlignment(TextAlignment.JUSTIFIED);
                else   a = new Paragraph().add(Comen1).setTextAlignment(TextAlignment.CENTER);
                canvas.add(a).close();
            }

            conteocuadros++;

        }


    }
    PdfFont pdfFont (String font) throws IOException {
        PdfFont pdfFont = null;
        if (font.equals("bold")) pdfFont = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
        else if (font.equals("italic")) pdfFont = PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC);
        else pdfFont = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        return pdfFont;
    }

    private ImageData Foto (String rutacarpeta, String nombre){
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
    @SuppressLint("RestrictedApi")
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
    public class PaginaNueva{
        PdfPage page;
        int y;
        public PaginaNueva(PdfPage page,int y){
            this.page = page;
            this.y = y;
        }
    }
    public void Main(PdfDocument pdfDocument) throws IOException {
        int yinicial = 0;
        ///CreaPagina return y maxima superior de cuerpo y maxima inferior de cuerpo Numero de paginas
        CreaPagina(pdfDocument);
        ///Cuerpo


    }
    PaginaNueva CreaPagina(PdfDocument pdf) throws IOException {
        int[] nuevapagina = new int[3];
        ///Nueva Pagina
        //ysquare=xy[1];
        PdfPage page = pdf.addNewPage();
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        //AñadeEncabezado
        nuevapagina[1]=Contenido(pdfCanvas,"encabezado")[1];
        ///AñadePiedePagina
        return new PaginaNueva(page,nuevapagina[1]);

    }
    public int[] Contenido(PdfCanvas canvas,String TipoContenido) throws IOException {
        int[]xyfinal = new int[2];
        String[]cp = caracteristicasCuadros.CaracPagina;
        String[]ce;
        String[]parent = null;
        int hpagina=parseInt(cp[0]),wpagina=parseInt(cp[1]),space=parseInt(cp[2]), margin=parseInt(cp[3]);
        System.out.println("hpagina : "+ hpagina);
        int NElementos = caracteristicasCuadros.Encabezado.length/18;
        int xsquare=0,yactualmax,xactualmax,ysquare=hpagina,ymaxultimopadre=0,xmaxultimopadre=0,ymaxima=0;
        System.out.println("ysquare : "+ ysquare);
        int wsquare ,hsquare;
        int [] xy;
        String alineacion,nombreultimopadre="";
        boolean Hijos;
        ///Corrimiento de Elementos
        for (int i=1; i<=NElementos;i++){
            ce=CContenido(TipoContenido,i);
            wsquare= parseInt(ce[5]); hsquare= parseInt(ce[6]);
            alineacion = ce[2];
            //Altura maxima cuadro
            yactualmax=ysquare;
            xactualmax = xsquare;
            System.out.println("yactualmax : "+ yactualmax);
            System.out.println("xactualmax : "+ xactualmax);
            if (yactualmax>ymaxima) ymaxima=yactualmax;
            ///Dimensiones maximas
            if (!ce[1].equals("")){
                parent=ObteniendoDatosParent(ce[1],"encabezado");
                if (!nombreultimopadre.equals(ce[1])) {
                    ymaxultimopadre=yactualmax;
                    xmaxultimopadre=xactualmax;
                    System.out.println("ymaxultimopadre: "+ymaxultimopadre);
                    System.out.println("wparent : "+parent[5]);
                    System.out.println("hparent : "+parent[6]);
                    System.out.println("margen interno : "+parent[7]);
                    //yactualmax=ysquare+parseInt(parent[6]);
                    //xactualmax = xsquare+parseInt(parent[5]);
                    yactualmax=ysquare+parseInt(parent[6]);
                }

                System.out.println("yactualmax : "+ yactualmax);
                System.out.println("xactualmax : "+ xactualmax);
                wpagina = parseInt(parent[5]);
                hpagina = parseInt(parent[6]);
                space =parseInt(parent[7]);
                margin=parseInt(parent[7]);
                nombreultimopadre=ce[1];
            }
            else {
                hpagina=parseInt(cp[0]);wpagina=parseInt(cp[1]); space=parseInt(cp[2]); margin=parseInt(cp[3]);
                if (!nombreultimopadre.equals("")){ yactualmax = ymaxultimopadre; nombreultimopadre = "";parent=null;}
            }
            if (parseInt(ce[5])>wpagina-(2*margin))  {wsquare = wpagina-(2*margin);wpagina=parseInt(cp[1]);}
            if (parseInt(ce[6])>(hpagina)-(2*margin))  hsquare = (hpagina)-(2*margin);
            System.out.println("Altura recuadro: "+ce[6]+"Altura pagina: "+hpagina+"Margen: "+margin);

            ///Alineacion
            if (!alineacion.equals("")){
                wpagina=parseInt(cp[1]); space=parseInt(cp[2]); margin=parseInt(cp[3]);
                System.out.println("Salto horizontal: "+ce[11]);
                if (i==1)xy = SetAlineacion(alineacion,yactualmax,xactualmax,margin, wpagina,margin,parseInt(ce[10]),hsquare,wsquare,ce[11],parent,xmaxultimopadre);
                else xy = SetAlineacion(alineacion,yactualmax,xactualmax,margin, wpagina,space,parseInt(ce[10]),hsquare,wsquare,ce[11],parent,xmaxultimopadre);
                xsquare=xy[0]; ysquare=xy[1];}
            else{xsquare=parseInt(ce[3]);ysquare=parseInt(ce[4]);}
            ///GeneraCuadros
            //if ((xsquare+wsquare)>(wpagina-margin)) wsquare=(wpagina-margin)-xsquare; ///Si se sale del margen horizontal de la pagina
            Contenido(xsquare,ysquare,wsquare,hsquare,canvas,ce);
            ///ObtenPuntoMaximoX
            xsquare = xsquare+wsquare;
            xyfinal[0] = xsquare;
            xyfinal[1]=  ymaxima;
        }
        return xyfinal;
        //Numero de cuadros de encabezado
    }
    private void Contenido(int xsquare,int ysquare,int wsquare,int hsquare,PdfCanvas pdfCanvas,String [] ce) throws IOException {
        Rectangle rectangle = new Rectangle(xsquare, ysquare, wsquare, hsquare);
        pdfCanvas.rectangle(rectangle);
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Image image;
        //Marco
        if (ce[12].equals("true")) pdfCanvas.setLineWidth(parseInt(ce[9])).setColor(ColorConstants.BLACK,false).stroke().closePathStroke();
        else pdfCanvas.setLineWidth(0).setColor(ColorConstants.WHITE,false).stroke().closePathStroke();
        ////Añade Imagen
        if (ce[13].equals("imagen")){
            System.out.println("Insertando Imagen");
            //// LOGO falta condicionante
            image = new Image(Logo(),xsquare,ysquare,wsquare);  image.setHeight(hsquare);
            ///imagen normal
            //else image = new Image(Foto(c[i+6],c[i+8]),xsquare,ysquare,wsquare);  image.setHeight(hsquare);
            canvas.add(image).close();
            canvas.close();
        }
        ////Añade Texto
        else{
            //Text Comen1 = new Text(c[i+8]).setFont(font).setFontSize(size);
            Paragraph a;
            System.out.println("Fuente: "+ce[17]);
            System.out.println("Tamaño: "+ce[14]);
            System.out.println("Alineacion fuente:"+ce[16]);
            Text Comen1 = new Text(ce[15]).setFont(pdfFont(ce[17])).setFontSize(parseInt(ce[14]));
            if ((ce[16]).equals("right"))  a = new Paragraph().add(Comen1).setTextAlignment(TextAlignment.RIGHT);
            else if ((ce[16]).equals("left"))  a = new Paragraph().add(Comen1).setTextAlignment(TextAlignment.LEFT);
            else if ((ce[16]).equals("justified"))  a = new Paragraph().add(Comen1).setTextAlignment(TextAlignment.JUSTIFIED);
            else   a = new Paragraph().add(Comen1).setTextAlignment(TextAlignment.CENTER);
            canvas.add(a).close();
        }

    }
    int []  SetAlineacion(String Alineacion, int hparcial,int wparcial,int margin,int wdoc,int space,int spacehori,int hsquare,int wsquare,String SaltoHori,String[]parent,int xpadre){
        System.out.println("----------------------------------------------------");
        int [] xy = new int[2];
        int xsquare,ysquare;
        ////Alineacion
            if (parent != null){
                int wparent= parseInt(parent[5]);
                margin = parseInt(parent[7]);
                space =  parseInt(parent[8]);


                if (Alineacion.equals("left")){
                    if (SaltoHori.equals("true")) {xsquare = wparcial+spacehori; ysquare = hparcial;}
                    else {xsquare = xpadre-wparent+margin; ysquare = hparcial-hsquare-space;}
                    System.out.println("right");
                    System.out.println("hparcial: "+hparcial);
                    System.out.println("hsquare: "+hsquare);
                    System.out.println("space: "+space);
                    System.out.println("margin: "+margin);
                    System.out.println("wdoc: "+wdoc);
                    System.out.println("wsquare: "+wsquare);
                    System.out.println("xsquare: "+xsquare);
                }
                else if (Alineacion.equals("right")){
                    if (SaltoHori.equals("true")) {xsquare = wparcial+spacehori; ysquare = hparcial;}
                    else {xsquare = xpadre-margin-wsquare ; ysquare = hparcial-hsquare-space;}
                    System.out.println("left");
                    System.out.println("hparcial: "+hparcial);
                    System.out.println("hsquare: "+hsquare);
                    System.out.println("space: "+space);
                    System.out.println("margin: "+margin);
                    System.out.println("wdoc: "+wdoc);
                    System.out.println("wsquare: "+wsquare);
                    System.out.println("xsquare: "+xsquare);
                }
                else  {
                    if (SaltoHori.equals("true")) {xsquare = wparcial+spacehori; ysquare = hparcial;}
                    else {xsquare = (xpadre-(wparent/2))-(wsquare/2) ; ysquare = hparcial-hsquare-space;}
                    System.out.println("center");
                    System.out.println("hparcial: "+hparcial);
                    System.out.println("hsquare: "+hsquare);
                    System.out.println("space: "+space);
                    System.out.println("margin: "+margin);
                    System.out.println("wdoc: "+wdoc);
                    System.out.println("wsquare: "+wsquare);
                    System.out.println("xsquare: "+xsquare);
                }//center
            }else{
                if (Alineacion.equals("right")){
                    if (SaltoHori.equals("true")) {xsquare = wparcial+spacehori; ysquare = hparcial;}
                    else {xsquare = margin; ysquare = hparcial-hsquare-space;}
                    System.out.println("right");
                    System.out.println("hparcial: "+hparcial);
                    System.out.println("hsquare: "+hsquare);
                    System.out.println("space: "+space);
                    System.out.println("margin: "+margin);
                    System.out.println("wdoc: "+wdoc);
                    System.out.println("wsquare: "+wsquare);
                    System.out.println("xsquare: "+xsquare);
                }
                else if (Alineacion.equals("left")){
                    if (SaltoHori.equals("true")) {xsquare = wparcial+spacehori; ysquare = hparcial;}
                    else {xsquare = wdoc-margin-wsquare ; ysquare = hparcial-hsquare-space;}
                    System.out.println("left");
                    System.out.println("hparcial: "+hparcial);
                    System.out.println("hsquare: "+hsquare);
                    System.out.println("space: "+space);
                    System.out.println("margin: "+margin);
                    System.out.println("wdoc: "+wdoc);
                    System.out.println("wsquare: "+wsquare);
                    System.out.println("xsquare: "+xsquare);
                }
                else  {
                    if (SaltoHori.equals("true")) {xsquare = wparcial+spacehori; ysquare = hparcial;}
                    else {xsquare = (wdoc/2)-(wsquare/2) ; ysquare = hparcial-hsquare-space;}
                    System.out.println("center");
                    System.out.println("hparcial: "+hparcial);
                    System.out.println("hsquare: "+hsquare);
                    System.out.println("space: "+space);
                    System.out.println("margin: "+margin);
                    System.out.println("wdoc: "+wdoc);
                    System.out.println("wsquare: "+wsquare);
                    System.out.println("xsquare: "+xsquare);
                }//center
            }

        xy[0] = xsquare ;    xy[1] = ysquare ;
        System.out.println("xsquare: "+xsquare+" ysquare: "+ysquare);
        return  xy;
    }
    public String[]CContenido(String Where,int Elemento){
        String []CC= new String[18];
        String []cc;
        int Iniciofor = (18*Elemento)-18;
        int conteo = 0;
        if (Where.equals("encabezado"))cc=caracteristicasCuadros.Encabezado;
        else if (Where.equals("cuerpo"))cc=caracteristicasCuadros.Cuerpo;
        else cc=caracteristicasCuadros.PiePagina;
        for (int i =Iniciofor;i<18*Elemento;i++){
            CC[conteo]=cc[i];
            conteo++;
        }
        return CC;
    }
    private String[] ObteniendoDatosParent(String parent,String where){
        String [] pa = new String[18];
        String []cc;
        int conteo = 0;
        if (where.equals("encabezado"))cc=caracteristicasCuadros.Encabezado;
        else if (where.equals("cuerpo"))cc=caracteristicasCuadros.Cuerpo;
        else cc=caracteristicasCuadros.PiePagina;
        for (int i =0;i<cc.length;i= i+18){
            if (parent.equals(cc[i])) {
                int b=0;
                while (b<18){
                    System.out.println("Dato: "+(i+b)+": " +cc[i+b]);
                    pa[b] = cc[i+b];
                    b++;
                }

                System.out.println("Padre encontrado : "+"Pariente: "+ cc[i]);
                return pa;

            }
            System.out.println("Pariente: "+ cc[i]);
            conteo++;
        }
        ///Buscar cuadro padre;

        return pa;
    }
    public void PiedePagina(PdfCanvas canvas){

    }


}
