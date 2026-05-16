package Beans;

public class DetalleNomina {

    private int idDetalle;
    private int idNomina;
    private int idConcepto;
    private double importe;

    // Constructor vacío
    public DetalleNomina() {
    }

    // Constructor completo (para obtener datos de la BD)
    public DetalleNomina(int idDetalle, int idNomina, int idConcepto, double importe) {
        this.idDetalle = idDetalle;
        this.idNomina = idNomina;
        this.idConcepto = idConcepto;
        this.importe = importe;
    }

    // Constructor sin ID (útil para insertar nuevos desgloses en el DAO)
    public DetalleNomina(int idNomina, int idConcepto, double importe) {
        this.idNomina = idNomina;
        this.idConcepto = idConcepto;
        this.importe = importe;
    }

    // Getters y Setters
    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getIdNomina() {
        return idNomina;
    }

    public void setIdNomina(int idNomina) {
        this.idNomina = idNomina;
    }

    public int getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(int idConcepto) {
        this.idConcepto = idConcepto;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    @Override
    public String toString() {
        return "DetalleNomina{" +
                "idNomina=" + idNomina +
                ", idConcepto=" + idConcepto +
                ", importe=" + importe +
                '}';
    }
}