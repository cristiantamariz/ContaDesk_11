package Beans;

import java.util.Date;
import org.bson.types.ObjectId;

public class Cliente {

    // En Mongo el ID estándar es una cadena hexadecimal manejada por ObjectId
    private String idCliente;
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
    // Mongo guarda fechas como java.util.Date de forma nativa
    private Date createdAt;

    // Constructor vacío
    public Cliente() {
    }

    // Constructor completo (Para cuando recuperas de MongoDB)
    public Cliente(String idCliente, String nombre, String razonSocial, String rfc, String telefono,
                   String email, String direccion, String codigoPostal, String regimenFiscal,
                   String usoCfdi, boolean activo, Date createdAt) {
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

    // Constructor para INSERT (sin ID ni fecha, Mongo los genera automáticamente)
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

    // Getters y Setters actualizados
    public String getIdCliente() { return idCliente; }
    public void setIdCliente(String idCliente) { this.idCliente = idCliente; }

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

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Cliente{" + "id='" + idCliente + "', nombre='" + nombre + "', rfc='" + rfc + "'}";
    }
}