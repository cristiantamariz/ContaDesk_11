package Beans;

public class PartidaFactura {

    private int idPartida;
    private int idFactura;
    private int idProducto;
    private String descripcion;
    private double cantidad;
    private double precioUnitario;
    private double descuento;
    private double tasaIva;
    private double importe;
    private double ivaImporte;

    // Constructor vacío
    public PartidaFactura() {
    }

    // Constructor completo (SELECT)
    public PartidaFactura(int idPartida, int idFactura, int idProducto, String descripcion,
                          double cantidad, double precioUnitario, double descuento,
                          double tasaIva, double importe, double ivaImporte) {
        this.idPartida = idPartida;
        this.idFactura = idFactura;
        this.idProducto = idProducto;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuento = descuento;
        this.tasaIva = tasaIva;
        this.importe = importe;
        this.ivaImporte = ivaImporte;
    }

    // Constructor sin ID (INSERT)
    public PartidaFactura(int idFactura, int idProducto, String descripcion, double cantidad,
                          double precioUnitario, double descuento, double tasaIva,
                          double importe, double ivaImporte) {
        this.idFactura = idFactura;
        this.idProducto = idProducto;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuento = descuento;
        this.tasaIva = tasaIva;
        this.importe = importe;
        this.ivaImporte = ivaImporte;
    }

    // Getters y Setters
    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getTasaIva() {
        return tasaIva;
    }

    public void setTasaIva(double tasaIva) {
        this.tasaIva = tasaIva;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getIvaImporte() {
        return ivaImporte;
    }

    public void setIvaImporte(double ivaImporte) {
        this.ivaImporte = ivaImporte;
    }

    @Override
    public String toString() {
        return "PartidaFactura{" +
                "idPartida=" + idPartida +
                ", descripcion='" + descripcion + '\'' +
                ", importe=" + importe +
                '}';
    }
}