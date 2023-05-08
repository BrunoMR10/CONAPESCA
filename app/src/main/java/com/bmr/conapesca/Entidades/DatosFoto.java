package com.bmr.conapesca.Entidades;

public class DatosFoto {
    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    String Descripcion;

    public String getReporte() {
        return Reporte;
    }

    public void setReporte(String reporte) {
        Reporte = reporte;
    }

    String Reporte;
    int ID;
}
