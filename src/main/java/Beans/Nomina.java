package Beans;

public class Nomina {

    private int idNomina;
    private int idEmpleado;
    private int idPeriodo;
    private double salarioBase;
    private double diasTrabajados;
    private double diasIncapacidad;
    private double diasVacaciones;
    private double totalPercepciones;
    private double totalDeducciones;
    private double isrRetenido;
    private double imssEmpleado;
    private double infonavit;
    private double netoPagar;
    private String estado;

    // Constructor vacío
    public Nomina() {
    }

    // Constructor completo (para consultas SELECT / Reportes)
    public Nomina(int idNomina, int idEmpleado, int idPeriodo, double salarioBase, double diasTrabajados,
                  double diasIncapacidad, double diasVacaciones, double totalPercepciones,
                  double totalDeducciones, double isrRetenido, double imssEmpleado,
                  double infonavit, double netoPagar, String estado) {
        this.idNomina = idNomina;
        this.idEmpleado = idEmpleado;
        this.idPeriodo = idPeriodo;
        this.salarioBase = salarioBase;
        this.diasTrabajados = diasTrabajados;
        this.diasIncapacidad = diasIncapacidad;
        this.diasVacaciones = diasVacaciones;
        this.totalPercepciones = totalPercepciones;
        this.totalDeducciones = totalDeducciones;
        this.isrRetenido = isrRetenido;
        this.imssEmpleado = imssEmpleado;
        this.infonavit = infonavit;
        this.netoPagar = netoPagar;
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdNomina() { return idNomina; }
    public void setIdNomina(int idNomina) { this.idNomina = idNomina; }

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public int getIdPeriodo() { return idPeriodo; }
    public void setIdPeriodo(int idPeriodo) { this.idPeriodo = idPeriodo; }

    public double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }

    public double getDiasTrabajados() { return diasTrabajados; }
    public void setDiasTrabajados(double diasTrabajados) { this.diasTrabajados = diasTrabajados; }

    public double getDiasIncapacidad() { return diasIncapacidad; }
    public void setDiasIncapacidad(double diasIncapacidad) { this.diasIncapacidad = diasIncapacidad; }

    public double getDiasVacaciones() { return diasVacaciones; }
    public void setDiasVacaciones(double diasVacaciones) { this.diasVacaciones = diasVacaciones; }

    public double getTotalPercepciones() { return totalPercepciones; }
    public void setTotalPercepciones(double totalPercepciones) { this.totalPercepciones = totalPercepciones; }

    public double getTotalDeducciones() { return totalDeducciones; }
    public void setTotalDeducciones(double totalDeducciones) { this.totalDeducciones = totalDeducciones; }

    public double getIsrRetenido() { return isrRetenido; }
    public void setIsrRetenido(double isrRetenido) { this.isrRetenido = isrRetenido; }

    public double getImssEmpleado() { return imssEmpleado; }
    public void setImssEmpleado(double imssEmpleado) { this.imssEmpleado = imssEmpleado; }

    public double getInfonavit() { return infonavit; }
    public void setInfonavit(double infonavit) { this.infonavit = infonavit; }

    public double getNetoPagar() { return netoPagar; }
    public void setNetoPagar(double netoPagar) { this.netoPagar = netoPagar; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Nomina{" + "idNomina=" + idNomina + ", empleado=" + idEmpleado + ", neto=" + netoPagar + '}';
    }
}