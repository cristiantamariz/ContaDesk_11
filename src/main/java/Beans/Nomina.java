package Beans;

import java.util.Date;

/**
 * Bean que representa un documento de la colección "nomina" en MongoDB.
 * Schema: id_empleado, periodo {fecha_inicio, fecha_fin, ejercicio},
 *         sueldo_base, bonos, deducciones, total_neto, estado, created_at
 *
 * El bono se calcula como el 3% del montoTotal de todas las cotizaciones
 * con estatus "Aprobada" asignadas al empleado dentro del periodo.
 */
public class Nomina {

    // ID del documento MongoDB (hex string del ObjectId)
    private String id;

    // Referencia al empleado (ObjectId del documento en la colección empleados)
    private String idEmpleado;

    // Datos desnormalizados del empleado (para mostrar sin JOIN)
    private String nombreEmpleado;
    private String numeroEmpleado;

    // Periodo de la nómina
    private String fechaInicio;   // "2026-06-01"
    private String fechaFin;      // "2026-06-30"
    private int    ejercicio;     // 2026

    // Montos
    private double sueldoBase;    // Salario fijo mensual del empleado
    private double bonos;         // 3% del total de cotizaciones Aprobadas del periodo
    private double deducciones;   // ISR + IMSS + otros descuentos (captura manual)
    private double totalNeto;     // sueldoBase + bonos - deducciones

    // Control
    private String estado;        // "Pendiente" | "Pagado" | "Cancelado"
    private Date   createdAt;

    public Nomina() {}

    // ── Getters y Setters ──────────────────────────────────────────────

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(String idEmpleado) { this.idEmpleado = idEmpleado; }

    public String getNombreEmpleado() { return nombreEmpleado; }
    public void setNombreEmpleado(String nombreEmpleado) { this.nombreEmpleado = nombreEmpleado; }

    public String getNumeroEmpleado() { return numeroEmpleado; }
    public void setNumeroEmpleado(String numeroEmpleado) { this.numeroEmpleado = numeroEmpleado; }

    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }

    public String getFechaFin() { return fechaFin; }
    public void setFechaFin(String fechaFin) { this.fechaFin = fechaFin; }

    public int getEjercicio() { return ejercicio; }
    public void setEjercicio(int ejercicio) { this.ejercicio = ejercicio; }

    public double getSueldoBase() { return sueldoBase; }
    public void setSueldoBase(double sueldoBase) { this.sueldoBase = sueldoBase; }

    public double getBonos() { return bonos; }
    public void setBonos(double bonos) { this.bonos = bonos; }

    public double getDeducciones() { return deducciones; }
    public void setDeducciones(double deducciones) { this.deducciones = deducciones; }

    public double getTotalNeto() { return totalNeto; }
    public void setTotalNeto(double totalNeto) { this.totalNeto = totalNeto; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Nomina{id=" + id + ", empleado=" + idEmpleado
                + ", periodo=" + fechaInicio + "/" + fechaFin
                + ", neto=" + totalNeto + ", estado=" + estado + "}";
    }
}