package Beans;

public class Puesto {

    private int idPuesto;
    private String nombre;
    private double salarioMinimo;
    private double salarioMaximo;

    // Constructor vacío
    public Puesto() {
    }

    // Constructor completo (para SELECT)
    public Puesto(int idPuesto, String nombre, double salarioMinimo, double salarioMaximo) {
        this.idPuesto = idPuesto;
        this.nombre = nombre;
        this.salarioMinimo = salarioMinimo;
        this.salarioMaximo = salarioMaximo;
    }

    // Constructor sin ID (para INSERT)
    public Puesto(String nombre, double salarioMinimo, double salarioMaximo) {
        this.nombre = nombre;
        this.salarioMinimo = salarioMinimo;
        this.salarioMaximo = salarioMaximo;
    }

    // Getters y Setters
    public int getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(int idPuesto) {
        this.idPuesto = idPuesto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getSalarioMinimo() {
        return salarioMinimo;
    }

    public void setSalarioMinimo(double salarioMinimo) {
        this.salarioMinimo = salarioMinimo;
    }

    public double getSalarioMaximo() {
        return salarioMaximo;
    }

    public void setSalarioMaximo(double salarioMaximo) {
        this.salarioMaximo = salarioMaximo;
    }

    // toString para depuración
    @Override
    public String toString() {
        return "Puesto{" +
                "idPuesto=" + idPuesto +
                ", nombre='" + nombre + '\'' +
                ", min=" + salarioMinimo +
                ", max=" + salarioMaximo +
                '}';
    }
}