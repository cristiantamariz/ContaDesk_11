package Beans;

import java.sql.Date;
import java.sql.Timestamp;

public class Empleado {

    private int idEmpleado;
    private int idUsuario;
    private String numeroEmpleado;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Date fechaContratacion;
    private Date fechaBaja;
    private String telefono;
    private String direccion;
    private String curp;
    private String rfc;
    private String nss;
    private Date fechaNacimiento;
    private String estadoCivil;
    private String tipoContrato;
    private int idDepartamento;
    private int idPuesto;
    private boolean activo;
    private Timestamp createdAt;

    // Constructor vacío
    public Empleado() {
    }

    // Constructor completo (para consultas SELECT en el DAO)
    public Empleado(int idEmpleado, int idUsuario, String numeroEmpleado, String nombre, String apellidoPaterno,
                    String apellidoMaterno, Date fechaContratacion, Date fechaBaja, String telefono,
                    String direccion, String curp, String rfc, String nss, Date fechaNacimiento,
                    String estadoCivil, String tipoContrato, int idDepartamento, int idPuesto,
                    boolean activo, Timestamp createdAt) {
        this.idEmpleado = idEmpleado;
        this.idUsuario = idUsuario;
        this.numeroEmpleado = numeroEmpleado;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaContratacion = fechaContratacion;
        this.fechaBaja = fechaBaja;
        this.telefono = telefono;
        this.direccion = direccion;
        this.curp = curp;
        this.rfc = rfc;
        this.nss = nss;
        this.fechaNacimiento = fechaNacimiento;
        this.estadoCivil = estadoCivil;
        this.tipoContrato = tipoContrato;
        this.idDepartamento = idDepartamento;
        this.idPuesto = idPuesto;
        this.activo = activo;
        this.createdAt = createdAt;
    }

    // Getters y Setters
    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNumeroEmpleado() { return numeroEmpleado; }
    public void setNumeroEmpleado(String numeroEmpleado) { this.numeroEmpleado = numeroEmpleado; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidoPaterno() { return apellidoPaterno; }
    public void setApellidoPaterno(String apellidoPaterno) { this.apellidoPaterno = apellidoPaterno; }

    public String getApellidoMaterno() { return apellidoMaterno; }
    public void setApellidoMaterno(String apellidoMaterno) { this.apellidoMaterno = apellidoMaterno; }

    public Date getFechaContratacion() { return fechaContratacion; }
    public void setFechaContratacion(Date fechaContratacion) { this.fechaContratacion = fechaContratacion; }

    public Date getFechaBaja() { return fechaBaja; }
    public void setFechaBaja(Date fechaBaja) { this.fechaBaja = fechaBaja; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCurp() { return curp; }
    public void setCurp(String curp) { this.curp = curp; }

    public String getRfc() { return rfc; }
    public void setRfc(String rfc) { this.rfc = rfc; }

    public String getNss() { return nss; }
    public void setNss(String nss) { this.nss = nss; }

    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getEstadoCivil() { return estadoCivil; }
    public void setEstadoCivil(String estadoCivil) { this.estadoCivil = estadoCivil; }

    public String getTipoContrato() { return tipoContrato; }
    public void setTipoContrato(String tipoContrato) { this.tipoContrato = tipoContrato; }

    public int getIdDepartamento() { return idDepartamento; }
    public void setIdDepartamento(int idDepartamento) { this.idDepartamento = idDepartamento; }

    public int getIdPuesto() { return idPuesto; }
    public void setIdPuesto(int idPuesto) { this.idPuesto = idPuesto; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Empleado{" + "num=" + numeroEmpleado + ", nombre=" + nombre + " " + apellidoPaterno + "}";
    }
}