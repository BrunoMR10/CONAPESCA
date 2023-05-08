package com.bmr.conapesca.Entidades;

public class Ticket {
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String Ticket;

    public String getTicket() {
        return Ticket;
    }

    public void setTicket(String ticket) {
        Ticket = ticket;
    }

    public String getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        FechaInicio = fechaInicio;
    }

    public String getHoraInicio() {
        return HoraInicio;
    }

    public void setHoraInicio(String horaInicio) {
        HoraInicio = horaInicio;
    }

    public String getNombreIngeniero() {
        return NombreIngeniero;
    }

    public void setNombreIngeniero(String nombreIngeniero) {
        NombreIngeniero = nombreIngeniero;
    }

    public String getPuestoIngeniero() {
        return PuestoIngeniero;
    }

    public void setPuestoIngeniero(String puestoIngeniero) {
        PuestoIngeniero = puestoIngeniero;
    }

    public String getTipoServicio() {
        return TipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        TipoServicio = tipoServicio;
    }

    String FechaInicio;
    String HoraInicio;
    String NombreIngeniero;
    String PuestoIngeniero;
    String TipoServicio;

    public String getNombreBarco() {
        return NombreBarco;
    }

    public void setNombreBarco(String nombreBarco) {
        NombreBarco = nombreBarco;
    }

    public String getNumeroOficio() {
        return NumeroOficio;
    }

    public void setNumeroOficio(String numeroOficio) {
        NumeroOficio = numeroOficio;
    }

    String NombreBarco;
    String NumeroOficio;
    public String getWhere() {
        return Where;
    }

    public void setWhere(String where) {
        Where = where;
    }

    String Where;

}
