package com.bmr.conapesca.Documentos;

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
    public void CreaRF(String[]Datos,ImageData imageData,int id, String[]Comentarios)throws IOException{
        String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
        String  rutacarpetaImagenes = "CONAPESCA/"+Datos[0]+"/"+Datos[1]+"/" + Datos[1] + "(CF)/";
        String  rutacarpeta = "CONAPESCA/"+Datos[0]+"/"+Datos[1]+"/" ;
        String Name = Datos[1]+"(RFD).pdf";
        String DEST = ExternalStorageDirectory+rutacarpeta+Name;
        PdfDocument pdf = new PdfDocument(new PdfWriter(DEST));
        PdfPage page = pdf.addNewPage();
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
        CuadroDatos(Datos,pdfCanvas,font,bold,36, 700, 530, 100,imageData);
        CuadroDatos2(pdfCanvas,bold,36, 615, 530, 50);
        InsertaPiedePagina(pdf);
        pdf.close();
    }
    private void CuadroDatos(String[] Datos,PdfCanvas pdfCanvas,PdfFont font,PdfFont bold,int x,int y,int w,int h,ImageData imageData){
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke().closePathStroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text title = new Text("REPORTE FOTOGRÁFICO DE MANTENIMIENTO CORRECTIVO").setFont(bold).setFontColor(ColorConstants.RED).setFontSize(10);
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