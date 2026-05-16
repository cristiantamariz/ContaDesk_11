package Beans;

public class Departamento {

    private int idDepartamento;
    private String nombre;
    private String descripcion;

    // Constructor vacío
    public Departamento() {
    }

    // Constructor completo (para consultas SELECT)
    public Departamento(int idDepartamento, String nombre, String descripcion) {
        this.idDepartamento = idDepartamento;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Constructor sin ID (para operaciones INSERT)
    public Departamento(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
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

    // toString para facilitar el debug
    @Override
    public String toString() {
        return "Departamento{" +
                "idDepartamento=" + idDepartamento +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}