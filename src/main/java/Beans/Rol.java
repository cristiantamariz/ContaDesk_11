package Beans;

public class Rol {

    private int idRol;
    private String nombre;
    private String descripcion;

    // Constructor vacío
    public Rol() {
    }

    // Constructor con todos los campos (para obtener datos de la BD)
    public Rol(int idRol, String nombre, String descripcion) {
        this.idRol = idRol;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Constructor sin ID (para insertar nuevos registros)
    public Rol(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // toString para pruebas por consola
    @Override
    public String toString() {
        return "Rol{" + "idRol=" + idRol + ", nombre=" + nombre + ", descripcion=" + descripcion + '}';
    }
}