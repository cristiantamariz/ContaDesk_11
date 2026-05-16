package Beans;

import java.sql.Date;

public class PeriodoFiscal {

    private int idPeriodo;
    private int ejercicio;
    private int mes;
    private String tipo;
    private Date fechaInicio;
    private Date fechaFin;
    private String estado;

    // Constructor vacío
    public PeriodoFiscal() {
    }

    // Constructor completo (SELECT)
    public PeriodoFiscal(int idPeriodo, int ejercicio, int mes, String tipo,
                         Date fechaInicio, Date fechaFin, String estado) {
        this.idPeriodo = idPeriodo;
        this.ejercicio = ejercicio;
        this.mes = mes;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    // Constructor para INSERT (sin ID)
    public PeriodoFiscal(int ejercicio, int mes, String tipo,
                         Date fechaInicio, Date fechaFin, String estado) {
        this.ejercicio = ejercicio;
        this.mes = mes;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public int getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(int ejercicio) {
        this.ejercicio = ejercicio;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "PeriodoFiscal{" +
                "ejercicio=" + ejercicio +
                ", mes=" + mes +
                ", tipo='" + tipo + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}