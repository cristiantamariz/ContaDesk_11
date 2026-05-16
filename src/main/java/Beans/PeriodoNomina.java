package Beans;

import java.sql.Date;
import java.sql.Timestamp;

public class PeriodoNomina {

    private int idPeriodo;
    private String nombre;
    private String tipo;
    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaPago;
    private String estado;
    private Timestamp createdAt;

    // Constructor vacío
    public PeriodoNomina() {
    }

    // Constructor completo (para obtener datos de la BD)
    public PeriodoNomina(int idPeriodo, String nombre, String tipo, Date fechaInicio,
                         Date fechaFin, Date fechaPago, String estado, Timestamp createdAt) {
        this.idPeriodo = idPeriodo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaPago = fechaPago;
        this.estado = estado;
        this.createdAt = createdAt;
    }

    // Constructor para insertar nuevo periodo (sin ID ni fecha de creación)
    public PeriodoNomina(String nombre, String tipo, Date fechaInicio, Date fechaFin,
                         Date fechaPago, String estado) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaPago = fechaPago;
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "PeriodoNomina{" +
                "id=" + idPeriodo +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}