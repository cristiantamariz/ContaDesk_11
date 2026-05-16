package Beans;

public class ProductoServicio {

    private int idProducto;
    private String codigoSat;
    private String descripcion;
    private String unidadMedida;
    private double precioUnitario;
    private double tasaIva;
    private boolean activo;

    // Constructor vacío (estándar Java Bean)
    public ProductoServicio() {
    }

    // Constructor completo (para obtener datos de la BD en el DAO)
    public ProductoServicio(int idProducto, String codigoSat, String descripcion, String unidadMedida,
                            double precioUnitario, double tasaIva, boolean activo) {
        this.idProducto = idProducto;
        this.codigoSat = codigoSat;
        this.descripcion = descripcion;
        this.unidadMedida = unidadMedida;
        this.precioUnitario = precioUnitario;
        this.tasaIva = tasaIva;
        this.activo = activo;
    }

    // Constructor para insertar nuevos productos (sin ID)
    public ProductoServicio(String codigoSat, String descripcion, String unidadMedida,
                            double precioUnitario, double tasaIva, boolean activo) {
        this.codigoSat = codigoSat;
        this.descripcion = descripcion;
        this.unidadMedida = unidadMedida;
        this.precioUnitario = precioUnitario;
        this.tasaIva = tasaIva;
        this.activo = activo;
    }

    // Getters y Setters
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getCodigoSat() {
        return codigoSat;
    }

    public void setCodigoSat(String codigoSat) {
        this.codigoSat = codigoSat;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getTasaIva() {
        return tasaIva;
    }

    public void setTasaIva(double tasaIva) {
        this.tasaIva = tasaIva;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    // toString para facilitar la depuración en consola
    @Override
    public String toString() {
        return "ProductoServicio{" +
                "id=" + idProducto +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precioUnitario +
                '}';
    }
}