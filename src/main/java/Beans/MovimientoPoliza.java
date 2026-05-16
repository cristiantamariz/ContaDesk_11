package Beans;

public class MovimientoPoliza {

    private int idMovimiento;
    private int idPoliza;
    private int idCuenta;
    private String descripcion;
    private double cargo;
    private double abono;

    // Constructor vacío
    public MovimientoPoliza() {
    }

    // Constructor completo (SELECT)
    public MovimientoPoliza(int idMovimiento, int idPoliza, int idCuenta, String descripcion,
                            double cargo, double abono) {
        this.idMovimiento = idMovimiento;
        this.idPoliza = idPoliza;
        this.idCuenta = idCuenta;
        this.descripcion = descripcion;
        this.cargo = cargo;
        this.abono = abono;
    }

    // Constructor sin ID (INSERT)
    public MovimientoPoliza(int idPoliza, int idCuenta, String descripcion, double cargo, double abono) {
        this.idPoliza = idPoliza;
        this.idCuenta = idCuenta;
        this.descripcion = descripcion;
        this.cargo = cargo;
        this.abono = abono;
    }

    // Getters y Setters
    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public int getIdPoliza() {
        return idPoliza;
    }

    public void setIdPoliza(int idPoliza) {
        this.idPoliza = idPoliza;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCargo() {
        return cargo;
    }

    public void setCargo(double cargo) {
        this.cargo = cargo;
    }

    public double getAbono() {
        return abono;
    }

    public void setAbono(double abono) {
        this.abono = abono;
    }

    @Override
    public String toString() {
        return "MovimientoPoliza{" +
                "idCuenta=" + idCuenta +
                ", cargo=" + cargo +
                ", abono=" + abono +
                '}';
    }
}