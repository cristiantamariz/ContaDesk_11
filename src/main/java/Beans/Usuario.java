package Beans;

import java.util.Date;

public class Usuario {

    // El _id de MongoDB representado como String Hexadecimal de 24 caracteres
    private String id;
    private String username;
    private String passwords;
    private String nombreCompleto;
    private String email;
    private boolean activo;
    private int intentosFallidos;

    // Mongo maneja fechas nativas mapeadas como java.util.Date
    private Date bloqueadoHasta;
    private Date ultimoAcceso;
    private Date createdAt;

    // Objeto embebido (Documento incrustado de MongoDB para el Rol)
    private Rol rol;

    // Constructor vacío requerido para JavaBeans y serializadores
    public Usuario() {
        this.rol = new Rol();
    }

    // Constructor completo (Para uso general en el DAO)
    public Usuario(String id, String username, String passwords, String nombreCompleto, String email,
                   boolean activo, int intentosFallidos, Date bloqueadoHasta,
                   Date ultimoAcceso, Date createdAt, Rol rol) {
        this.id = id;
        this.username = username;
        this.passwords = passwords;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.activo = activo;
        this.intentosFallidos = intentosFallidos;
        this.bloqueadoHasta = bloqueadoHasta;
        this.ultimoAcceso = ultimoAcceso;
        this.createdAt = createdAt;
        this.rol = rol;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswords() { return passwords; }
    public void setPasswords(String passwords) { this.passwords = passwords; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public int getIntentosFallidos() { return intentosFallidos; }
    public void setIntentosFallidos(int intentosFallidos) { this.intentosFallidos = intentosFallidos; }

    public Date getBloqueadoHasta() { return bloqueadoHasta; }
    public void setBloqueadoHasta(Date bloqueadoHasta) { this.bloqueadoHasta = bloqueadoHasta; }

    public Date getUltimoAcceso() { return ultimoAcceso; }
    public void setUltimoAcceso(Date ultimoAcceso) { this.ultimoAcceso = ultimoAcceso; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    @Override
    public String toString() {
        return "Usuario{" + "id='" + id + "', username='" + username + "', rol='" + (rol != null ? rol.getNombre() : "Ninguno") + "'}";
    }

    // ── CLASE INTERNA ESTÁTICA PARA EL ROL EMBEBIDO ──────────────────────
    public static class Rol {
        private int idRol; // Por si guardaron un identificador numérico interno (ej. 1, 2, 3)
        private String nombre; // 'admin', 'contador', 'capturista'

        public Rol() {}

        public Rol(int idRol, String nombre) {
            this.idRol = idRol;
            this.nombre = nombre;
        }

        public int getIdRol() { return idRol; }
        public void setIdRol(int idRol) { this.idRol = idRol; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
    }
}