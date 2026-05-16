package Beans;

public class CuentaContable {

    private int idCuenta;
    private String codigo;
    private String nombre;
    private String tipo;
    private String naturaleza;
    private int idCuentaPadre;
    private int nivel;
    private boolean esAuxiliar;
    private boolean activo;

    // Constructor vacío
    public CuentaContable() {
    }

    // Constructor completo (SELECT)
    public CuentaContable(int idCuenta, String codigo, String nombre, String tipo, String naturaleza,
                          int idCuentaPadre, int nivel, boolean esAuxiliar, boolean activo) {
        this.idCuenta = idCuenta;
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.naturaleza = naturaleza;
        this.idCuentaPadre = idCuentaPadre;
        this.nivel = nivel;
        this.esAuxiliar = esAuxiliar;
        this.activo = activo;
    }

    // Constructor para INSERT (sin ID)
    public CuentaContable(String codigo, String nombre, String tipo, String naturaleza,
                          int idCuentaPadre, int nivel, boolean esAuxiliar, boolean activo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.naturaleza = naturaleza;
        this.idCuentaPadre = idCuentaPadre;
        this.nivel = nivel;
        this.esAuxiliar = esAuxiliar;
        this.activo = activo;
    }

    // Getters y Setters
    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public String getNaturaleza() {
        return naturaleza;
    }

    public void setNaturaleza(String naturaleza) {
        this.naturaleza = naturaleza;
    }

    public int getIdCuentaPadre() {
        return idCuentaPadre;
    }

    public void setIdCuentaPadre(int idCuentaPadre) {
        this.idCuentaPadre = idCuentaPadre;
    }

    public int getNivel() {
        return nivel;
    }

    public void setLevel(int nivel) {
        this.nivel = nivel;
    }

    public boolean isEsAuxiliar() {
        return esAuxiliar;
    }

    public void setEsAuxiliar(boolean esAuxiliar) {
        this.esAuxiliar = esAuxiliar;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "CuentaContable{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", nivel=" + nivel +
                '}';
    }
}