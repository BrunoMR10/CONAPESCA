package com.bmr.conapesca.Documentos;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;
import static java.lang.Integer.parseInt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bmr.conapesca.Datos.CaracteristicasCuadros;
import com.bmr.conapesca.Datos.CorrectivoRD;
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
import java.io.IOException;

public class Correctivos {

    CorrectivoRD correctivoRD = new CorrectivoRD();
    int y,fin,nelementos,conteoelementos;

    public void CreaDocumento(String Ticket) throws IOException {
        String Name = Ticket+"(RD).pdf";
        String [] c = correctivoRD.CaracPagina;
        @SuppressLint("RestrictedApi") ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(Ticket, Context.MODE_PRIVATE);
        File file = new File(directory, Name);
        PdfDocument pdf = new PdfDocument(new PdfWriter(file));
        int hdoc= parseInt(c[0]);
        int wdoc= parseInt(c[1]);
        Rectangle TamañoDocumento = new Rectangle(wdoc, hdoc);
        pdf.setDefaultPageSize(new PageSize(TamañoDocumento));
        Main(pdf);
        //EstructuraCuerpo(pdf);
        pdf.close();
    }
    public void Main(PdfDocument pdfDocument) throws IOException {
        System.out.println(y);
        ///CreaPagina return y maxima superior de cuerpo y maxima inferior de cuerpo Numero de paginas

        fin = 0;
        nelementos = 0;
        conteoelementos=1;

        while (fin == 0){
            PdfCanvas pdfCanvas = new PdfCanvas(CreaPagina(pdfDocument));
            System.out.println(y);
            fin = ContenidoElementos(pdfCanvas, "cuerpo")[1];
            if (fin == 0) {
                System.out.println("NuevaPagina");
                System.out.println("N elemento donde se quedo" + nelementos);
            } else {System.out.println("Documento terminado");break;}
        }


        /*pdfCanvas = new PdfCanvas(CreaPagina(pdfDocument));
        System.out.println(y);
        fin = ContenidoElementos(pdfCanvas, "cuerpo")[1];
        if (fin == 0) {
            System.out.println("NuevaPagina");
            System.out.println("N elemento donde se quedo" + nelementos);
        } else System.out.println("Documento terminado");*/
        ///Cuerpo
    }
    PdfPage CreaPagina(PdfDocument pdf) throws IOException {
        int[] xy = new int[2];
        ///Nueva Pagina
        //ysquare=xy[1];
        PdfPage page = pdf.addNewPage();
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        //AñadeEncabezado
        xy=ContenidoElementos(pdfCanvas,"encabezado");
        y=xy[0];
        ///AñadePiedePagina
        System.out.println("y: "+y);
        return page;

    }

    public int[] ContenidoElementos(PdfCanvas canvas,String TipoContenido) throws IOException {
        System.out.println("--------------------------------------");
        System.out.println("Insertando Contenido: "+TipoContenido);
        System.out.println("--------------------------------------");

        String[]cp = correctivoRD.CaracPagina;
        String[]ce;
        String[]parent = null;
        int NElementos;
        int hpagina,wpagina,space,margin;
        if (TipoContenido.equals("encabezado"))  hpagina=parseInt(cp[0]);
        else {hpagina=y;};
        wpagina=parseInt(cp[1]);space=parseInt(cp[2]);margin=parseInt(cp[3]);
        System.out.println("hpagina : "+ hpagina);
        if (nelementos>0&&!TipoContenido.equals("encabezado")){System.out.println("Siguiente pagina");conteoelementos=nelementos;}
        else{conteoelementos=1;}
        NElementos = NElementos(TipoContenido);
        int xsquare=0,yactualmax,xactualmax,ysquare=hpagina,ymaxultimopadre=0,xmaxultimopadre=0,ymaxima=hpagina;
        System.out.println("ysquare : "+ ysquare);
        int wsquare ,hsquare;
        int [] xy = new int[2];
        String alineacion,nombreultimopadre="";
        boolean Hijos;
        ///Corrimiento de Elementos
        for (int i=conteoelementos; i<=NElementos;i++){
            System.out.println("----------------------------------------------------");
            ce=CContenido(TipoContenido,i);
            wsquare= parseInt(ce[5]); hsquare= parseInt(ce[6]);
            alineacion = ce[2];
            //Altura maxima cuadro
            yactualmax=ysquare;
            xactualmax = xsquare;
            System.out.println("yactualmax : "+ yactualmax);
            System.out.println("xactualmax : "+ xactualmax);
            if (yactualmax<ymaxima && ymaxima>0) ymaxima=yactualmax;
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
                if (i==1&&TipoContenido.equals("encabezado"))xy = SetAlineacion(alineacion,yactualmax,xactualmax,margin, wpagina,margin,parseInt(ce[10]),hsquare,wsquare,ce[11],parent,xmaxultimopadre);
                else xy = SetAlineacion(alineacion,yactualmax,xactualmax,margin, wpagina,space,parseInt(ce[10]),hsquare,wsquare,ce[11],parent,xmaxultimopadre);
                xsquare=xy[0]; ysquare=xy[1];}
            else{xsquare=parseInt(ce[3]);ysquare=parseInt(ce[4]);}
            ///GeneraCuadros
            //if ((xsquare+wsquare)>(wpagina-margin)) wsquare=(wpagina-margin)-xsquare; ///Si se sale del margen horizontal de la pagina
            ////Deteccion nuevaPagina
            if (i==NElementos){
                System.out.println("Fin del "+TipoContenido);
                yactualmax=ysquare;
                xactualmax = xsquare;
                System.out.println("yactualmax : "+ yactualmax);
                System.out.println("xactualmax : "+ xactualmax);
                if (yactualmax<ymaxima && ymaxima>0) ymaxima=yactualmax;
            }
            if (ysquare<=0) {
                System.out.println("FINAL DE LA PAGINA");
                xy[0]=ymaxima;
                xy[1]=0;
                nelementos = i;
                return xy;
            }
            Contenido(xsquare,ysquare,wsquare,hsquare,canvas,ce);
            ///ObtenPuntoMaximoX
            xsquare = xsquare+wsquare;
            xy[0]=ymaxima;
            xy[1]=1;
        }
        return xy;
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
            String textonegritasapertura = "<neg";
            String textonegritascierre = "neg>";

            String textosubrayadoapertura = "<sub";
            String textosubrayadocierre = "sub>";
            if (ce[17].equals("auto")){
                int sizestring;
                sizestring=ce[15].length();
                System.out.println("Arreglo de tamaño: "+sizestring);
                // Contador de ocurrencias
                int contador = 0;
                int inicionormal = 0;
                int finalnegritas =0;
                String textonormal;
                String textonegrita;
                if ((ce[16]).equals("right"))  a = new Paragraph().setTextAlignment(TextAlignment.RIGHT);
                else if ((ce[16]).equals("left"))  a = new Paragraph().setTextAlignment(TextAlignment.LEFT);
                else if ((ce[16]).equals("justified"))  a = new Paragraph().setTextAlignment(TextAlignment.JUSTIFIED);
                else   a = new Paragraph().setTextAlignment(TextAlignment.CENTER);
                while (ce[15].indexOf(textonegritasapertura) > -1 || ce[15].indexOf(textosubrayadoapertura) > -1) {
                    int posnegrita = ce[15].indexOf(textonegritasapertura);
                    int posSub =ce[15].indexOf(textosubrayadoapertura);
                    String lineas = "_____________________";
                    System.out.println("Posicion negrita: "+posnegrita);
                    System.out.println("Posicion subrayado: "+posSub);
                    if ((posnegrita<posSub || posSub<0) && posnegrita>0 ){
                        textonormal = ce[15].substring(inicionormal,ce[15].indexOf(
                                textonegritasapertura));
                        System.out.println("Index1: "+(ce[15].indexOf(textonegritasapertura)+textonegritasapertura.length())+"Index2: "+(ce[15].indexOf(textonegritascierre)-1));
                        if (ce[15].indexOf(textonegritasapertura)+textonegritasapertura.length()==ce[15].indexOf(textonegritascierre)){
                            textonegrita=lineas;
                        }else{
                            textonegrita = ce[15].substring(
                                    ce[15].indexOf(textonegritasapertura)+textonegritasapertura.length(),
                                    ce[15].indexOf(textonegritascierre));
                        }
                        //finalnegritas=ce[15].indexOf(textonegritascierre)+textonegritascierre.length();
                        //inicionormal= ce[15].indexOf(textonegritascierre)+textonegritascierre.length();
                        ce[15] = ce[15].substring(ce[15].indexOf(
                                textonegritascierre)+textonegritascierre.length(),ce[15].length());
                        contador++;
                        Text normal = new Text(textonormal).setFont(pdfFont("normal")).setFontSize(parseInt(ce[14]));
                        Text negrita = new Text(textonegrita).setFont(pdfFont("bold")).setFontSize(parseInt(ce[14]));
                        a.add(normal);
                        a.add(negrita);
                        System.out.println ("Negritas: "+ contador+" "+textonegrita);
                    }
                    else{
                        System.out.println("Insertando sub");
                        textonormal = ce[15].substring(inicionormal,ce[15].indexOf(
                                textosubrayadoapertura));
                        textonegrita = ce[15].substring(
                                ce[15].indexOf(textosubrayadoapertura)+textosubrayadoapertura.length(),
                                ce[15].indexOf(textosubrayadocierre));
                        //finalnegritas=ce[15].indexOf(textonegritascierre)+textonegritascierre.length();
                        //inicionormal= ce[15].indexOf(textonegritascierre)+textonegritascierre.length();
                        ce[15] = ce[15].substring(ce[15].indexOf(
                                textosubrayadocierre)+textosubrayadocierre.length(),ce[15].length());
                        contador++;
                        Text normal = new Text(textonormal).setFont(pdfFont("normal")).setFontSize(parseInt(ce[14]));
                        Text subrayado = new Text(textonegrita).setFont(pdfFont("normal")).setFontSize(parseInt(ce[14])).setUnderline();
                        a.add(normal);
                        a.add(subrayado);
                        System.out.println ("Subrayado: "+ contador+" "+textonegrita);
                    }
                }
                Text normal = new Text(ce[15]).setFont(pdfFont(ce[17])).setFontSize(parseInt(ce[14]));
                a.add(normal);
                canvas.add(a);
                canvas.close();
                System.out.println ("Se encontraron: "+ contador+" negritas");

            }
            else{
                Text Comen1 = new Text(ce[15]).setFont(pdfFont(ce[17])).setFontSize(parseInt(ce[14]));
                if ((ce[16]).equals("right"))  a = new Paragraph().add(Comen1).setTextAlignment(TextAlignment.RIGHT);
                else if ((ce[16]).equals("left"))  a = new Paragraph().add(Comen1).setTextAlignment(TextAlignment.LEFT);
                else if ((ce[16]).equals("justified"))  a = new Paragraph().add(Comen1).setTextAlignment(TextAlignment.JUSTIFIED);
                else   a = new Paragraph().add(Comen1).setTextAlignment(TextAlignment.CENTER);
                canvas.add(a).close();
            }

        }

    }
    int []  SetAlineacion(String Alineacion, int hparcial,int wparcial,int margin,int wdoc,int space,int spacehori,int hsquare,int wsquare,String SaltoHori,String[]parent,int xpadre){
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
        if (Where.equals("encabezado"))cc= correctivoRD.Encabezado;
        else if (Where.equals("cuerpo"))cc=correctivoRD.Cuerpo;
        else cc=correctivoRD.PiedePagina;
        for (int i =Iniciofor;i<18*Elemento;i++){
            CC[conteo]=cc[i];
            conteo++;
        }
        return CC;
    }

    public int NElementos(String Where){
        int NElementos = 0;
        String []cc;
        if (Where.equals("encabezado"))cc=correctivoRD.Encabezado;
        else if (Where.equals("cuerpo"))cc=correctivoRD.Cuerpo;
        else cc=correctivoRD.PiedePagina;
        NElementos=cc.length/18;
        return NElementos;
    }
    private String[] ObteniendoDatosParent(String parent,String where){
        String [] pa = new String[18];
        String []cc;
        int conteo = 0;
        if (where.equals("encabezado"))cc=correctivoRD.Encabezado;
        else if (where.equals("cuerpo"))cc=correctivoRD.Cuerpo;
        else cc=correctivoRD.PiedePagina;
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
    PdfFont pdfFont (String font) throws IOException {
        PdfFont pdfFont = null;
        if (font.equals("bold")) pdfFont = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
        else if (font.equals("italic")) pdfFont = PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC);
        else pdfFont = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        return pdfFont;
    }
}
