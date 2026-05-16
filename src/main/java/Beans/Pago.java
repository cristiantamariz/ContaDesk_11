package Beans;

import java.sql.Date;
import java.sql.Timestamp;

public class Pago {

    private int idPago;
    private int idFactura;
    private Date fechaPago;
    private double monto;
    private String formaPago;
    private String referencia;
    private String banco;
    private String notas;
    private Timestamp createdAt;

    // Constructor vacío
    public Pago() {
    }

    // Constructor completo (SELECT)
    public Pago(int idPago, int idFactura, Date fechaPago, double monto, String formaPago,
                String referencia, String banco, String notas, Timestamp createdAt) {
        this.idPago = idPago;
        this.idFactura = idFactura;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.formaPago = formaPago;
        this.referencia = referencia;
        this.banco = banco;
        this.notas = notas;
        this.createdAt = createdAt;
    }

    // Constructor para INSERT (sin ID ni fecha de creación)
    public Pago(int idFactura, Date fechaPago, double monto, String formaPago,
                String referencia, String banco, String notas) {
        this.idFactura = idFactura;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.formaPago = formaPago;
        this.referencia = referencia;
        this.banco = banco;
        this.notas = notas;
    }

    // Getters y Setters
    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "idPago=" + idPago +
                ", idFactura=" + idFactura +
                ", monto=" + monto +
                '}';
    }
}