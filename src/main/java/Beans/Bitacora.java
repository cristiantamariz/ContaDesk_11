package Beans;

import java.sql.Timestamp;

public class Bitacora {

    private int idBitacora;
    private int idUsuario;
    private String accion;
    private String modulo;
    private String descripcion;
    private String ipAddress;
    private Timestamp fecha;

    // Constructor vacío
    public Bitacora() {
    }

    // Constructor completo (Útil para listar logs en el sistema)
    public Bitacora(int idBitacora, int idUsuario, String accion, String modulo,
                    String descripcion, String ipAddress, Timestamp fecha) {
        this.idBitacora = idBitacora;
        this.idUsuario = idUsuario;
        this.accion = accion;
        this.modulo = modulo;
        this.descripcion = descripcion;
        this.ipAddress = ipAddress;
        this.fecha = fecha;
    }

    // Constructor para insertar un nuevo registro (Usualmente no envías el id ni la fecha)
    public Bitacora(int idUsuario, String accion, String modulo, String descripcion, String ipAddress) {
        this.idUsuario = idUsuario;
        this.accion = accion;
        this.modulo = modulo;
        this.descripcion = descripcion;
        this.ipAddress = ipAddress;
    }

    // Getters y Setters
    public int getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(int idBitacora) {
        this.idBitacora = idBitacora;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Bitacora{" + "id=" + idBitacora + ", usuario=" + idUsuario + ", accion=" + accion + '}';
    }
}