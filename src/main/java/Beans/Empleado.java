package Beans;

import java.util.Date;

public class Empleado {

    // ID de MongoDB (_id) representado como String Hexadecimal
    private String id;

    // Campos requeridos por el esquema
    private String numeroEmpleado;
    private String nombre;
    private String fechaContratacion; // Definido como 'string' en tu esquema BSON

    // Campos opcionales / Atributos del empleado
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String fechaNacimiento;   // Definido como 'string' en tu esquema BSON
    private String fechaBaja;         // Definido como 'string' en tu esquema BSON
    private String telefono;
    private String curp;
    private String rfc;
    private String nss;
    private String estadoCivil;
    private String tipoContrato;
    private boolean activo;
    private Date createdAt;           // Permite 'date' y 'null'
    private Integer usuarioId;        // Permite 'int' y 'null'

    // Objetos embebidos (Documentos incrustados de MongoDB)
    private Departamento departamento;
    private Puesto puesto;

    // Constructor vacío requerido para la especificación de JavaBeans y Gson
    public Empleado() {
        this.departamento = new Departamento();
        this.puesto = new Puesto();
    }

    // ── GETTERS Y SETTERS ─────────────────────────────────────────────────

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(String fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(String fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }

    // ── CLASES INTERNAS ESTÁTICAS PARA OBJETOS EMBEBIDOS ──────────────────

    public static class Departamento {
        private String nombre;
        private String descripcion;

        public Departamento() {}

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
    }

    public static class Puesto {
        private String nombre;
        // Se usa Double (objeto) por si en BSON viene un entero o flotante y acepta nulos
        private Double salarioMinimo;
        private Double salarioMaximo;

        public Puesto() {}

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public Double getSalarioMinimo() {
            return salarioMinimo;
        }

        public void setSalarioMinimo(Double salarioMinimo) {
            this.salarioMinimo = salarioMinimo;
        }

        public Double getSalarioMaximo() {
            return salarioMaximo;
        }

        public void setSalarioMaximo(Double salarioMaximo) {
            this.salarioMaximo = salarioMaximo;
        }
    }
}