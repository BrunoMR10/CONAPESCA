package com.bmr.conapesca.Entidades;

import android.graphics.Bitmap;

public class RFP {
    public String getComentario1() {
        return Comentario1;
    }

    public void setComentario1(String comentario1) {
        Comentario1 = comentario1;
    }

    public String getComentario2() {
        return Comentario2;
    }

    public void setComentario2(String comentario2) {
        Comentario2 = comentario2;
    }

    public Bitmap getFoto1() {
        return Foto1;
    }

    public void setFoto1(Bitmap foto1) {
        Foto1 = foto1;
    }

    public Bitmap getFoto2() {
        return Foto2;
    }

    public void setFoto2(Bitmap foto2) {
        Foto2 = foto2;
    }

    public int getID1() {
        return ID1;
    }

    public void setID1(int ID1) {
        this.ID1 = ID1;
    }

    public int getID2() {
        return ID2;
    }

    public void setID2(int ID2) {
        this.ID2 = ID2;
    }

    String  Comentario1;
    String  Comentario2;
    Bitmap  Foto1;
    Bitmap  Foto2;
    int ID1;
    int ID2;

    public String[] getDatos() {
        return Datos;
    }

    public void setDatos(String[] datos) {
        Datos = datos;
    }

    String [] Datos;
}
