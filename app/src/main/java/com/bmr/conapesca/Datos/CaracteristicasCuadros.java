package com.bmr.conapesca.Datos;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;
import static java.lang.Integer.parseInt;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CaracteristicasCuadros {
    SharedPreferences sh = getApplicationContext().getSharedPreferences("TempShared", Context.MODE_PRIVATE);
    String Ticket = sh.getString("Ticket","");
    SharedPreferences sh2 = getApplicationContext().getSharedPreferences(Ticket, Context.MODE_PRIVATE);
    String Ciudad=sh2.getString("DatoBarco2",""),EntidadFederativa="Estado",Hora=sh2.getString("HoraCerrado",""),fecha=sh2.getString("FechaCerrado",""),Dia="Dia",Mes="Mes",SerieTransreceptor=sh2.getString("NSerieTransreceptor",""),SerieConBox=sh2.getString("NSerieConBox",""),Consecionario=sh2.getString("DatoBarco21",""),
            Barco=sh2.getString("DatoBarco3",""),NombreResposable =sh2.getString("NombreResponable",""),Ingeniero = sh2.getString("DatosUsuario1",""),
            INEPermisionario=sh2.getString("CredResponsable","");
    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat formatter = new SimpleDateFormat("dd");
    SimpleDateFormat formatter2 = new SimpleDateFormat("MMMM");

    Date dataFormateada;

    {
        try {
            dataFormateada = formato.parse(fecha);
            Dia = formatter.format(dataFormateada);
            Mes = formatter2.format(dataFormateada);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    String Texto1="SEGURITECH PRIVADA S.A. DE C.V." ;
    String Texto2="Av. Bosques de Alisos 45B Int. 3 piso. Col. Bosques de las Lomas. Alcaldía Cuajimalpa de Morelos. C.P. 05120 Ciudad de México\n" +
            "R.F.C. SPR950828523 Tel. + 52 55 5083 0000 correo electrónico: atencion.gob@seguritech.com\n" +
            "www.seguritech.com\n" ;
    String Texto3=
            "COMPROMISO DE PERMANENCIA\n" +
            "DE EQUIPO TRANSRECEPTOR Y BUEN USO" ;

    public String Texto4 = "En la Ciudad de <neg" +Ciudad+"neg> siendo las <neg"+Hora+"neg> horas del día <neg" +Dia+"neg> de <neg"+Mes+"neg> del año 2023," +
            " en referencia al Contrato Abierto número LPIE/006/12/22 para la prestación del Servicio Integral de Telecomunicación para la Localización y " +
            "Monitoreo Satelital de Embarcaciones Pesqueras, que celebraron, por una parte, el Ejecutivo Federal por conducto de la Comisión Nacional de Acuacultura y " +
            "Pesca y, por la otra, Seguritech Privada S.A. de C.V. con una vigencia del 30 de diciembre de 2022 al 30 de noviembre de 2024, y en cumplimiento a lo establecido en la " +
            ", primera. Objeto del Contrato que establece lo siguiente: \"EL PROVEEDOR\" acepta y se obliga a proporcionar a " +
            "“LA DEPENDENCIA” la prestación del SERVICIO INTEGRAL DE TELECOMUNICACIÓN PARA LA LOCALIZACIÓN Y MONITOREO SATELITAL DE EMBARCACIONES PESQUERAS , " +
            "en los términos y condiciones establecidos en este contrato y sus Anexo Técnico (PROPUESTA TÉCNICA)… Propuesta Técnica “2.2.1.10 \"Seguritech\" " +
            "definirá el instrumento legal que formalizará con los concesionarios y/o permisionarios de las embarcaciones a quien les haya instalado el equipo transreceptor que garantice " +
            "la permanencia en la embarcación pesquera y su buen uso… , se hace constar que Seguritech Privada, S.A. de C.V. llevó a cabo la instalación y configuración del equipo " +
            "Transreceptor con <neg Número de Serie: neg> <neg"+SerieTransreceptor+"neg> marca <neg BlueTraker neg>, y  <neg ConBox2018 neg> con <neg Número de Serie: "+SerieConBox+"neg> " +
            "completamente nuevo en la embarcación pesquera identificada como <neg"+Barco+"neg> del concesionario y/o " +
            "permisionario de nombre <neg"+NombreResposable+"neg> identificándose con INE o IFE número <neg"+INEPermisionario+"neg> vigente," +
            " con el cual se llevará a cabo la Localización y Monitoreo Satelital a la embarcación referida.\n";

    String Texto5 = "En términos de lo antes citado, el concesionario y/o permisionario de la embarcación pesquera se obliga frente a " +
            "Seguritech Privada, S.A. de C.V. y a la Comisión Nacional de Acuacultura y Pesca, a no manipular, destruir y/o quitar el " +
            "Equipo <negTransreceptorneg> marca <negBlueTrakerneg>, modelo <negConBox2018neg> y/o cualquier conexión o accesorio utilizado para su instalación, que impidan" +
            " u obstaculice su buen funcionamiento, al tiempo de comprometerse a salvaguardar y darle buen uso, a efecto de garantizar la permanencia del mismo.";

    String Texto6 = "La inobservancia al compromiso asumido generará el derecho de la Comisión Nacional de Acuacultura y Pesca para ser indemnizado por parte del concesionario y/o" +
            " permisionario de nombre <neg"+Consecionario+"neg>, por el incumplimiento de obligaciones a su cargo, y a obtener la reparación económica respectiva mediante el pago de" +
            " los daños y perjuicios que correspondan incluyendo las erogaciones que, en su caso, realice en favor de Seguritech, S.A. de C.V. o de cualquier tercero por reinstalación de equipos" +
            " transceptores, además de tener a salvo sus derechos para ejercitar en contra del concesionario y/o permisionario arriba indicado, el resto de las acciones civiles o penales que legalmente " +
            "correspondan.\n";
    String Text7 = "Se firma por duplicado el presente, con la finalidad de hacer constar su aceptación en los términos y condiciones establecidos en el mismo, para los efectos a que haya lugar.";

    String Texto7 = "Se firma por duplicado el presente, con la finalidad de hacer constar su aceptación en los términos y condiciones establecidos en el mismo, para los efectos a que haya lugar.\n";
    String Texto8 = "POR \""+Consecionario+"\"";
    String Texto9 = "POR \"SEGURITECH PRIVADA S.A DE CV \"";
    String Texto10 = "Esta hoja forma parte del Resguardo del Equipo Transreceptor formalizado por el Concesionario y/o Permisionario de la embarcación pesquera en cumplimiento al Contrato abierto número LPIE/006/12/22.";
    String Texto11 = "SEGURITECH PRIVADA, S.A. DE C.V. se obliga a no divulgar, transmitir, reproducir, revelar o difundir a terceros la información que se genere a través de este acto y que sea considerada como “Información Reservada” o como “Información Confidencial” en términos de lo dispuesto por la Ley General de Transparencia y Acceso a la Información Pública. De igual modo, se obliga a proteger los “Datos Personales” de particulares que se recaben a propósito de este acto y a darle el tratamiento que corresponda de conformidad con la Ley General de Protección de Datos Personales en Posesión de Sujetos Obligados. \n" +
            "Consulta nuestro aviso de privacidad en www.seguritech.com\n";
    /// 72 Pixeles por pulgada
    String[] TamañoCarta = new String[]{
            "792",//h pagina en pixeles 11 pulgadas 27.94 cm
            "612",//w pagina en pixeles 8.5 pulgadas 21.59 cm
    };



    String[] TamañoOficio = new String[]{
            "1008",//h pagina en pixeles 14 pulgadas 35.56 cm
            "612",//w pagina en pixeles  8.5 pulgadas 21.59 cm
    };

    public String[] CaracPagina = new String[]{
            //Datos Generales
            TamañoCarta[0],//0 h pagina
            TamañoCarta[1],//1 w pagina
            "5",//           2 separación entre cuadros en pixeles
            "60",//          3 margen en pixeles
    };

    public String[] Encabezado = new String[]{
            //Elemento1
            //Parametros de cuadro
            "Logo",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "198",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "80",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "imagen",//Type       13 Tipo de contenido (imagen,text)
            "Logo",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            "Logo",//Text-Nima  15 Texto o nombre de la imagen
            "",//LeterAlig    16 Alineacion de letra
            "",//Tipoletra    17 Tipo de letra

            //Elemento2
            //Parametros de cuadro
            "Text1",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "198",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "10",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,text)
            "7",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            Texto1,//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra center right left justified
            "bold",//Tipoletra    17 Tipo de letra bold normal

            //Elemento3
            //Parametros de cuadro
            "Text2",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "512",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "60",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,text)
            "7",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            Texto2,//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra
            "italic",//Tipoletra    17 Tipo de letra
    };
    public String[] Cuerpo = new String[]{
            //Elemento1
            //Parametros de cuadro
            "Text1",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "612",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "40",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "2",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "true",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "11",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            Texto3,//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra,normal bold, italic

            //Elemento2
            //Parametros de cuadro
            "Text2",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "612",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "300",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "11",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            Texto4,//Text-Nima  15 Texto o nombre de la imagen
            "justified",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "auto",//Tipoletra    17 Tipo de letra

            //Elemento3
            //Parametros de cuadro
            "Text3",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "612",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "110",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "11",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            Texto5,//Text-Nima  15 Texto o nombre de la imagen
            "justified",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "auto",//Tipoletra    17 Tipo de letra

            //Elemento4
            //Parametros de cuadro
            "Text4",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "612",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "140",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "11",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            Texto6,//Text-Nima  15 Texto o nombre de la imagen
            "justified",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "auto",//Tipoletra    17 Tipo de letra


            //Elemento5
            //Parametros de cuadro
            "Text5",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "612",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "50",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "11",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            Texto7,//Text-Nima  15 Texto o nombre de la imagen
            "justified",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "normal",//Tipoletra    17 Tipo de letra


            //Elemento6
            //Parametros de cuadro
            "Text6",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "right",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "200",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "40",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "10",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            Texto8,//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra


            //Elemento7
            //Parametros de cuadro
            "Text7",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "right",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "210",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "30",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "80",//SpaceHori 10 Disntancia en horizontal entre elementos
            "true",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "10",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            Texto9,//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra

            //Elemento8
            //Parametros de cuadro
            "Text8",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "right",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "200",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "10",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "80",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "10",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            "",//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra

            //Elemento9
            //Parametros de cuadro
            "Text8",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "right",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "200",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "12",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "60",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "10",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            "___________________________________________",//Text-Nima  15 Texto o nombre de la imagen
            "left",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra

            //Elemento10
            //Parametros de cuadro
            "Text9",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "right",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "200",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "12",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "80",//SpaceHori 10 Disntancia en horizontal entre elementos
            "true",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "10",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            "___________________________________________",//Text-Nima  15 Texto o nombre de la imagen
            "left",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra

            //Elemento11
            //Parametros de cuadro
            "Text10",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "right",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "200",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "15",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "60",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "11",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            "C. "+NombreResposable,//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra

            //Elemento10
            //Parametros de cuadro
            "Text11",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "right",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "200",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "15",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "80",//SpaceHori 10 Disntancia en horizontal entre elementos
            "true",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "11",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            "C. "+Ingeniero,//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra

            //Elemento13
            //Parametros de cuadro
            "Text12",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "right",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "200",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "50",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "60",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "10",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            "REPRESENTANTE DEL \n PERMISIONARIO Y/O CONSECIONARIO",//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra

            //Elemento14
            //Parametros de cuadro
            "Text13",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "right",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "200",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "50",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "80",//SpaceHori 10 Disntancia en horizontal entre elementos
            "true",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "10",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            "REPRESENTANTE \n SEGURITECH PRIVADA S.A. DE C.V.",//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra

            //Elemento14
            //Parametros de cuadro
            "Text13",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "right",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "612",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "30",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "60",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "8",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            Texto10,//Text-Nima  15 Texto o nombre de la imagen
            "left",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "normal",//Tipoletra    17 Tipo de letra

            //Elemento15
            //Parametros de cuadro
            "Text14",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "right",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "612",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "80",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "60",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "8",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            Texto11,//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "normal",//Tipoletra    17 Tipo de letra


    };
    public String[] PiePagina = new String[]{
            //Elemento1
            //Parametros de cuadro
            "",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "",//Alignament 2 center,right,left
            "",//PosX       3 Si no se añadio alineacion
            "",//PosY       4 Si no se añadio alineacion
            "",//Wepaight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "",//Marginint  7 MargenInterno de cuadro
            "",//Spaceint   8 Espacio entre elementos internos
            "",//GrosorM    9 Grosor de marco
            "",//SaltoHori  10 true o false Si es false hace el salto en vertical
            "",//Marco      11 true o false Si es false oculta el marco
            //Parametros de contenido
            "",//Type       12 Tipo de contenido (imagen,text)
            "",//LSize-Ncar 13 Tamaño de letra o ruta de carpeta
            "",//Text-Nima  14 Texto o nombre de la imagen
            "",//LeterAlig  15 Alineacion de letra
            "",//Tipoletra    16 Tipo de letra
    };


    public String[] Estructura= new String[]{
            //Datos Generales
            TamañoCarta[0],// h pagina
            TamañoCarta[1],// w pagina
            "5",// separación entre cuadros en pixeles
            "50",//margen en pixeles

            //Cuadro1
            "center",//Alineacion
            "", //x
            "",//y
            "198",//w
            "80",//h
            "false",//marco
            "1",//GrosorMarco

            "",//tipo de letra
            "Logo",//Tamaño de letra o rutacarpeta
            "Logo",//Texto o nombre imagen
            "imagen",//Tipo
            "",//AlineacionLetra
            "",//Separacion en horizontal
            "",//Distancia en horizontal

            //Cuadro2
            "center",//Alineacion
            "", //x
            "",//y
            "198",//w
            "10",//h
            "false",//marco
            "1",//GrosorMarco

            "bold",//tipo de letra
            "7",//Tamaño de letra o rutacarpeta
            Texto1,//Texto o nombre imagen
            "texto",//Tipo
            "center",//AlineacionLetra
            "",//Separacion en horizontal
            "",//Distancia en horizontal

            //Cuadro3
            "center",//Alineacion
            "", //x
            "",//y
            "512",//w
            "60",//h
            "false",//marco
            "1",//GrosorMarco

            "italic",//tipo de letra
            "7",//Tamaño de letra o rutacarpeta
            Texto2,//Texto o nombre imagen
            "texto",//Tipo
            "center",//AlineacionLetra
            "",//Separacion en horizontal
            "",//Distancia en horizontal

            //Cuadro4
            "center",//Alineacion
            "", //x
            "",//y
            "512",//w
            "40",//h
            "true",//marco
            "2",//GrosorMarco

            "bold",//tipo de letra
            "11",//Tamaño de letra o rutacarpeta
            Texto3,//Texto o nombre imagen
            "texto",//Tipo
            "center",//AlineacionLetra
            "",//Separacion en horizontal
            "",//Distancia en horizontal

            //Cuadro5
            "center",//Alineacion
            "", //x
            "",//y
            "512",//w
            "370",//h
            "false",//marco
            "0",//GrosorMarco

            "normal",//tipo de letra
            "11",//Tamaño de letra o rutacarpeta
            Texto4,//Texto o nombre imagen
            "texto",//Tipo
            "justified",//AlineacionLetra
            "",//Separacion en horizontal
            "",//Distancia en horizontal

            //Cuadro6
            "center",//Alineacion
            "", //x
            "",//y
            "512",//w
            "150",//h
            "false",//marco
            "0",//GrosorMarco

            "normal",//tipo de letra
            "11",//Tamaño de letra o rutacarpeta
            Texto5,//Texto o nombre imagen
            "texto",//Tipo
            "justified",//AlineacionLetra
            "",//Separacion en horizontal
            "",//Distancia en horizontal

            //Cuadro7
            "center",//Alineacion
            "", //x
            "",//y
            "198",//w
            "80",//h
            "false",//marco
            "1",//GrosorMarco

            "",//tipo de letra
            "Logo",//Tamaño de letra o rutacarpeta
            "Logo",//Texto o nombre imagen
            "imagen",//Tipo
            "",//AlineacionLetra
            "",//Separacion en horizontal
            "",//Distancia en horizontal

            //Cuadro8
            "center",//Alineacion
            "", //x
            "",//y
            "198",//w
            "10",//h
            "false",//marco
            "1",//GrosorMarco

            "bold",//tipo de letra
            "7",//Tamaño de letra o rutacarpeta
            Texto1,//Texto o nombre imagen
            "texto",//Tipo
            "center",//AlineacionLetra
            "",//Separacion en horizontal
            "",//Distancia en horizontal

            //Cuadro9
            "center",//Alineacion
            "", //x
            "",//y
            "512",//w
            "60",//h
            "false",//marco
            "1",//GrosorMarco

            "italic",//tipo de letra
            "7",//Tamaño de letra o rutacarpeta
            Texto2,//Texto o nombre imagen
            "texto",//Tipo
            "center",//AlineacionLetra
            "",//Separacion en horizontal
            "",//Distancia en horizontal

            //Cuadro10
            "center",//Alineacion
            "", //x
            "",//y
            "512",//w
            "170",//h
            "false",//marco
            "0",//GrosorMarco

            "normal",//tipo de letra
            "11",//Tamaño de letra o rutacarpeta
            Texto6,//Texto o nombre imagen
            "texto",//Tipo
            "justified",//AlineacionLetra
            "",//Separacion en horizontal
            "",//Distancia en horizontal

            //Cuadro11
            "center",//Alineacion
            "", //x
            "",//y
            "512",//w
            "40",//h
            "false",//marco
            "0",//GrosorMarco

            "normal",//tipo de letra
            "11",//Tamaño de letra o rutacarpeta
            Texto7,//Texto o nombre imagen
            "texto",//Tipo
            "justified",//AlineacionLetra
            "",//Separacion en horizontal
            "",//Distancia en horizontal

            //Cuadro12
            "right",//Alineacion
            "", //x
            "",//y
            String.valueOf(parseInt(TamañoCarta[1])/2),//w
            "13",//h
            "false",//marco
            "0",//GrosorMarco

            "bold",//tipo de letra
            "10",//Tamaño de letra o rutacarpeta
            Texto8,//Texto o nombre imagen
            "texto",//Tipo
            "justified",//AlineacionLetra
            "",//Separacion en horizontal
            "",//Distancia en horizontal




            //Añada mas cuadros
    };
}
