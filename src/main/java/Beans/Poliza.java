package Beans;

import java.sql.Date;
import java.sql.Timestamp;

public class Poliza {

    private int idPoliza;
    private int idPeriodo;
    private int idUsuario;
    private String tipoPoliza;
    private String numeroPoliza;
    private Date fecha;
    private String concepto;
    private double totalCargos;
    private double totalAbonos;
    private String estado;
    private Timestamp createdAt;

    // Constructor vacío
    public Poliza() {
    }

    // Constructor completo (SELECT)
    public Poliza(int idPoliza, int idPeriodo, int idUsuario, String tipoPoliza, String numeroPoliza,
                  Date fecha, String concepto, double totalCargos, double totalAbonos,
                  String estado, Timestamp createdAt) {
        this.idPoliza = idPoliza;
        this.idPeriodo = idPeriodo;
        this.idUsuario = idUsuario;
        this.tipoPoliza = tipoPoliza;
        this.numeroPoliza = numeroPoliza;
        this.fecha = fecha;
        this.concepto = concepto;
        this.totalCargos = totalCargos;
        this.totalAbonos = totalAbonos;
        this.estado = estado;
        this.createdAt = createdAt;
    }

    // Constructor para INSERT (sin ID ni timestamp)
    public Poliza(int idPeriodo, int idUsuario, String tipoPoliza, String numeroPoliza,
                  Date fecha, String concepto, double totalCargos, double totalAbonos, String estado) {
        this.idPeriodo = idPeriodo;
        this.idUsuario = idUsuario;
        this.tipoPoliza = tipoPoliza;
        this.numeroPoliza = numeroPoliza;
        this.fecha = fecha;
        this.concepto = concepto;
        this.totalCargos = totalCargos;
        this.totalAbonos = totalAbonos;
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdPoliza() { return idPoliza; }
    public void setIdPoliza(int idPoliza) { this.idPoliza = idPoliza; }

    public int getIdPeriodo() { return idPeriodo; }
    public void setIdPeriodo(int idPeriodo) { this.idPeriodo = idPeriodo; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getTipoPoliza() { return tipoPoliza; }
    public void setTipoPoliza(String tipoPoliza) { this.tipoPoliza = tipoPoliza; }

    public String getNumeroPoliza() { return numeroPoliza; }
    public void setNumeroPoliza(String numeroPoliza) { this.numeroPoliza = numeroPoliza; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getConcepto() { return concepto; }
    public void setConcepto(String concepto) { this.concepto = concepto; }

    public double getTotalCargos() { return totalCargos; }
    public void setTotalCargos(double totalCargos) { this.totalCargos = totalCargos; }

    public double getTotalAbonos() { return totalAbonos; }
    public void setTotalAbonos(double totalAbonos) { this.totalAbonos = totalAbonos; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Poliza{" + "num='" + numeroPoliza + '\'' + ", concepto='" + concepto + '\'' + '}';
    }
}