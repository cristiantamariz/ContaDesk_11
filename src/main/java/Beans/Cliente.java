package Beans;

import java.sql.Timestamp;

public class Cliente {

    private int idCliente;
    private String nombre;
    private String razonSocial;
    private String rfc;
    private String telefono;
    private String email;
    private String direccion;
    private String codigoPostal;
    private String regimenFiscal;
    private String usoCfdi;
    private boolean activo;
    private Timestamp createdAt;

    // Constructor vacío
    public Cliente() {
    }

    // Constructor completo (SELECT)
    public Cliente(int idCliente, String nombre, String razonSocial, String rfc, String telefono,
                   String email, String direccion, String codigoPostal, String regimenFiscal,
                   String usoCfdi, boolean activo, Timestamp createdAt) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.razonSocial = razonSocial;
        this.rfc = rfc;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.regimenFiscal = regimenFiscal;
        this.usoCfdi = usoCfdi;
        this.activo = activo;
        this.createdAt = createdAt;
    }

    // Constructor para INSERT (sin ID ni fecha)
    public Cliente(String nombre, String razonSocial, String rfc, String telefono,
                   String email, String direccion, String codigoPostal, String regimenFiscal,
                   String usoCfdi, boolean activo) {
        this.nombre = nombre;
        this.razonSocial = razonSocial;
        this.rfc = rfc;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.regimenFiscal = regimenFiscal;
        this.usoCfdi = usoCfdi;
        this.activo = activo;
    }

    // Getters y Setters
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public String getRfc() { return rfc; }
    public void setRfc(String rfc) { this.rfc = rfc; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }

    public String getRegimenFiscal() { return regimenFiscal; }
    public void setRegimenFiscal(String regimenFiscal) { this.regimenFiscal = regimenFiscal; }

    public String getUsoCfdi() { return usoCfdi; }
    public void setUsoCfdi(String usoCfdi) { this.usoCfdi = usoCfdi; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + idCliente + ", nombre='" + nombre + "', rfc='" + rfc + "'}";
    }
}