package Beans;

import java.sql.Date;
import java.sql.Timestamp;

public class Factura {

    private int idFactura;
    private int idCliente;
    private int idUsuario;
    private String folio;
    private String uuidCfdi;
    private String serie;
    private Date fecha;
    private Date fechaVencimiento;
    private String tipoCfdi;
    private String usoCfdi;
    private String metodoPago;
    private String formaPago;
    private String moneda;
    private double tipoCambio;
    private double subtotal;
    private double descuento;
    private double iva;
    private double isrRetenido;
    private double ivaRetenido;
    private double total;
    private String estado;
    private String observaciones;
    private String xmlCfdi;
    private Timestamp createdAt;

    // Constructor vacío
    public Factura() {
    }

    // Constructor completo (SELECT)
    public Factura(int idFactura, int idCliente, int idUsuario, String folio, String uuidCfdi, String serie,
                   Date fecha, Date fechaVencimiento, String tipoCfdi, String usoCfdi, String metodoPago,
                   String formaPago, String moneda, double tipoCambio, double subtotal, double descuento,
                   double iva, double isrRetenido, double ivaRetenido, double total, String estado,
                   String observaciones, String xmlCfdi, Timestamp createdAt) {
        this.idFactura = idFactura;
        this.idCliente = idCliente;
        this.idUsuario = idUsuario;
        this.folio = folio;
        this.uuidCfdi = uuidCfdi;
        this.serie = serie;
        this.fecha = fecha;
        this.fechaVencimiento = fechaVencimiento;
        this.tipoCfdi = tipoCfdi;
        this.usoCfdi = usoCfdi;
        this.metodoPago = metodoPago;
        this.formaPago = formaPago;
        this.moneda = moneda;
        this.tipoCambio = tipoCambio;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.iva = iva;
        this.isrRetenido = isrRetenido;
        this.ivaRetenido = ivaRetenido;
        this.total = total;
        this.estado = estado;
        this.observaciones = observaciones;
        this.xmlCfdi = xmlCfdi;
        this.createdAt = createdAt;
    }

    // Getters y Setters
    public int getIdFactura() { return idFactura; }
    public void setIdFactura(int idFactura) { this.idFactura = idFactura; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getFolio() { return folio; }
    public void setFolio(String folio) { this.folio = folio; }

    public String getUuidCfdi() { return uuidCfdi; }
    public void setUuidCfdi(String uuidCfdi) { this.uuidCfdi = uuidCfdi; }

    public String getSerie() { return serie; }
    public void setSerie(String serie) { this.serie = serie; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public Date getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(Date fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public String getTipoCfdi() { return tipoCfdi; }
    public void setTipoCfdi(String tipoCfdi) { this.tipoCfdi = tipoCfdi; }

    public String getUsoCfdi() { return usoCfdi; }
    public void setUsoCfdi(String usoCfdi) { this.usoCfdi = usoCfdi; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getFormaPago() { return formaPago; }
    public void setFormaPago(String formaPago) { this.formaPago = formaPago; }

    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }

    public double getTipoCambio() { return tipoCambio; }
    public void setTipoCambio(double tipoCambio) { this.tipoCambio = tipoCambio; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public double getDescuento() { return descuento; }
    public void setDescuento(double descuento) { this.descuento = descuento; }

    public double getIva() { return iva; }
    public void setIva(double iva) { this.iva = iva; }

    public double getIsrRetenido() { return isrRetenido; }
    public void setIsrRetenido(double isrRetenido) { this.isrRetenido = isrRetenido; }

    public double getIvaRetenido() { return ivaRetenido; }
    public void setIvaRetenido(double ivaRetenido) { this.ivaRetenido = ivaRetenido; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getXmlCfdi() { return xmlCfdi; }
    public void setXmlCfdi(String xmlCfdi) { this.xmlCfdi = xmlCfdi; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Factura{" + "folio='" + folio + '\'' + ", total=" + total + ", estado='" + estado + '\'' + '}';
    }
}