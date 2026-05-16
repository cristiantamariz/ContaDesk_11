package Beans;

import java.sql.Date;
import java.sql.Timestamp;

public class DeclaracionFiscal {

    private int idDeclaracion;
    private int idPeriodo;
    private int idUsuario;
    private String tipo;
    private Date fechaPresentacion;
    private Date fechaLimite;
    private double importeDeterminado;
    private double importePagado;
    private double saldoFavor;
    private String numeroOperacion;
    private String estado;
    private String observaciones;
    private Timestamp createdAt;

    // Constructor vacío
    public DeclaracionFiscal() {
    }

    // Constructor completo (SELECT)
    public DeclaracionFiscal(int idDeclaracion, int idPeriodo, int idUsuario, String tipo,
                             Date fechaPresentacion, Date fechaLimite, double importeDeterminado,
                             double importePagado, double saldoFavor, String numeroOperacion,
                             String estado, String observaciones, Timestamp createdAt) {
        this.idDeclaracion = idDeclaracion;
        this.idPeriodo = idPeriodo;
        this.idUsuario = idUsuario;
        this.tipo = tipo;
        this.fechaPresentacion = fechaPresentacion;
        this.fechaLimite = fechaLimite;
        this.importeDeterminado = importeDeterminado;
        this.importePagado = importePagado;
        this.saldoFavor = saldoFavor;
        this.numeroOperacion = numeroOperacion;
        this.estado = estado;
        this.observaciones = observaciones;
        this.createdAt = createdAt;
    }

    // Getters y Setters
    public int getIdDeclaracion() { return idDeclaracion; }
    public void setIdDeclaracion(int idDeclaracion) { this.idDeclaracion = idDeclaracion; }

    public int getIdPeriodo() { return idPeriodo; }
    public void setIdPeriodo(int idPeriodo) { this.idPeriodo = idPeriodo; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Date getFechaPresentacion() { return fechaPresentacion; }
    public void setFechaPresentacion(Date fechaPresentacion) { this.fechaPresentacion = fechaPresentacion; }

    public Date getFechaLimite() { return fechaLimite; }
    public void setFechaLimite(Date fechaLimite) { this.fechaLimite = fechaLimite; }

    public double getImporteDeterminado() { return importeDeterminado; }
    public void setImporteDeterminado(double importeDeterminado) { this.importeDeterminado = importeDeterminado; }

    public double getImportePagado() { return importePagado; }
    public void setImportePagado(double importePagado) { this.importePagado = importePagado; }

    public double getSaldoFavor() { return saldoFavor; }
    public void setSaldoFavor(double saldoFavor) { this.saldoFavor = saldoFavor; }

    public String getNumeroOperacion() { return numeroOperacion; }
    public void setNumeroOperacion(String numeroOperacion) { this.numeroOperacion = numeroOperacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "DeclaracionFiscal{" + "tipo='" + tipo + '\'' + ", operacion='" + numeroOperacion + '\'' + ", estado='" + estado + '\'' + '}';
    }
}