package org.jala.university.model;

public class ChequeModel {
    private String nombre;
    private double monto;
    private String motivo;
    private String tipoMoneda;
    private String fechaHoraGeneracion;

    public ChequeModel() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getFechaHoraGeneracion() {
        return fechaHoraGeneracion;
    }

    public void setFechaHoraGeneracion(String fechaHoraGeneracion) {
        this.fechaHoraGeneracion = fechaHoraGeneracion;
    }
}

