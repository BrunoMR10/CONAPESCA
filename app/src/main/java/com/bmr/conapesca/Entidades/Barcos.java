package com.bmr.conapesca.Entidades;

public class Barcos {
    String AñoConstruccion;
    String Materialcasco;
    String MatriculaBarco;
    String NombreBarco;
    String TonBruto;

    public String getWhere() {
        return Where;
    }

    public void setWhere(String where) {
        Where = where;
    }

    String Where;

    public String getPermisionario() {
        return Permisionario;
    }

    public void setPermisionario(String permisionario) {
        Permisionario = permisionario;
    }

    String Permisionario;

    public String getAñoConstruccion() {
        return AñoConstruccion;
    }

    public void setAñoConstruccion(String añoConstruccion) {
        AñoConstruccion = añoConstruccion;
    }

    public String getMaterialcasco() {
        return Materialcasco;
    }

    public void setMaterialcasco(String materialcasco) {
        Materialcasco = materialcasco;
    }

    public String getMatriculaBarco() {
        return MatriculaBarco;
    }

    public void setMatriculaBarco(String matriculaBarco) {
        MatriculaBarco = matriculaBarco;
    }

    public String getNombreBarco() {
        return NombreBarco;
    }

    public void setNombreBarco(String nombreBarco) {
        NombreBarco = nombreBarco;
    }

    public String getTonBruto() {
        return TonBruto;
    }

    public void setTonBruto(String tonBruto) {
        TonBruto = tonBruto;
    }

    public String getTonNeto() {
        return TonNeto;
    }

    public void setTonNeto(String tonNeto) {
        TonNeto = tonNeto;
    }

    public String[] getDatos() {
        return Datos;
    }

    public void setDatos(String[] datos) {
        Datos = datos;
    }

    String[]Datos;

    public String[] getDatosBarco() {
        return DatosBarco;
    }

    public void setDatosBarco(String[] datosBarco) {
        DatosBarco = datosBarco;
    }

    String[]DatosBarco;
    String TonNeto;

}
