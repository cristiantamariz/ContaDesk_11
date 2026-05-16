package Beans;

public class Permiso {

    // 1. Atributos privados (mapeados a las columnas de la tabla)
    private int idPermiso;
    private String nombre;
    private String descripcion;
    private String modulo;

    // 2. Constructor vacío (Obligatorio por el estándar Java Bean)
    public Permiso() {
    }

    // 3. Constructor con parámetros (Útil para crear objetos rápidamente)
    public Permiso(int idPermiso, String nombre, String descripcion, String modulo) {
        this.idPermiso = idPermiso;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.modulo = modulo;
    }

    // Constructor opcional (sin ID, útil para cuando vas a insertar un nuevo registro)
    public Permiso(String nombre, String descripcion, String modulo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.modulo = modulo;
    }

    // 4. Métodos Getter y Setter
    public int getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(int idPermiso) {
        this.idPermiso = idPermiso;
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

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    // 5. Método toString para depuración (opcional pero muy útil)
    @Override
    public String toString() {
        return "Permiso [" +
                "id=" + idPermiso +
                ", nombre='" + nombre + '\'' +
                ", modulo='" + modulo + '\'' +
                ']';
    }
}
