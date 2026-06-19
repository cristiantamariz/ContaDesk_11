package Beans;

import java.util.Date;

public class Cotizacion {

    private String id;

    // Referencias y datos desnormalizados del Cliente
    private String idCliente;
    private String nombreCliente;
    private String regimen;

    // Referencias y datos desnormalizados del Empleado (Contador)
    private String idEmpleado;
    private String nombreEmpleado;

    // Variables del motor de cálculo
    private int volumenFacturas;
    private int horasEstimadas;
    private double tarifaBase;
    private double montoTotal;

    // Control operativo
    private String estatus; // Ej: "Pendiente", "Aprobada", "Rechazada"
    private Date createdAt;

    public Cotizacion() {
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getIdCliente() { return idCliente; }
    public void setIdCliente(String idCliente) { this.idCliente = idCliente; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getRegimen() { return regimen; }
    public void setRegimen(String regimen) { this.regimen = regimen; }

    public String getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(String idEmpleado) { this.idEmpleado = idEmpleado; }

    public String getNombreEmpleado() { return nombreEmpleado; }
    public void setNombreEmpleado(String nombreEmpleado) { this.nombreEmpleado = nombreEmpleado; }

    public int getVolumenFacturas() { return volumenFacturas; }
    public void setVolumenFacturas(int volumenFacturas) { this.volumenFacturas = volumenFacturas; }

    public int getHorasEstimadas() { return horasEstimadas; }
    public void setHorasEstimadas(int horasEstimadas) { this.horasEstimadas = horasEstimadas; }

    public double getTarifaBase() { return tarifaBase; }
    public void setTarifaBase(double tarifaBase) { this.tarifaBase = tarifaBase; }

    public double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(double montoTotal) { this.montoTotal = montoTotal; }

    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}