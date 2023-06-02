package com.bmr.conapesca.Datos;

public class CorrectivoRD {
    String Ticket = "Ticket";

    String Texto1 = "wefervavvqkmdkqemergkm \n <neg efercweecej neg> \n cewcwcewc"+Ticket+"eccwdcw";
    /// 72 Pixeles por pulgada
    String[] TamañoCarta = new String[]{
            "792",// h pagina en pixeles 11 pulgadas 27.94 cm
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
            Texto1,//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra,normal bold, italic

    };

    public String[] PiedePagina = new String[]{

    };
}
