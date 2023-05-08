package com.bmr.conapesca.Entidades;

import android.graphics.Bitmap;

public class Fotos {
    public String getDescripcion() {
        return Descripcion;
    }
    public void setDescripcion2(String descripcion) {
        Descripcion = descripcion;
    }

    String Descripcion;

    public Bitmap getFoto2() {
        return foto;
    }

    public void setFoto2(Bitmap foto) {
        this.foto = foto;
    }

    Bitmap foto;

    public int getId2() {
        return id;
    }

    public void setId2(int id) {
        this.id = id;
    }

    int id;

    public boolean getReporteFotos() {
        return ReporteFotos;
    }

    public void setReporteFotos(boolean reporteFotos) {
        ReporteFotos = reporteFotos;
    }

    boolean ReporteFotos;


    public String[] getDatos() {
        return Datos;
    }

    public void setDatos(String[] datos) {
        Datos = datos;
    }

    String[]Datos;
}
