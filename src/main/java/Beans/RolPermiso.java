package Beans;

public class RolPermiso {

    private int idRol;
    private int idPermiso;

    // Constructor vacío
    public RolPermiso() {
    }

    // Constructor con parámetros
    public RolPermiso(int idRol, int idPermiso) {
        this.idRol = idRol;
        this.idPermiso = idPermiso;
    }

    // Getters y Setters
    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public int getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(int idPermiso) {
        this.idPermiso = idPermiso;
    }

    // toString para depuración
    @Override
    public String toString() {
        return "RolPermiso [idRol=" + idRol + ", idPermiso=" + idPermiso + "]";
    }
}