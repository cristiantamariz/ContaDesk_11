package Beans;

import java.sql.Timestamp;

public class Usuario {

    private String id;
    private int idUsuario;
    private String username;
    private String passwords;
    private String nombreCompleto;
    private String email;
    private int idRol;
    private boolean activo;
    private int intentosFallidos;
    private Timestamp bloqueadoHasta;
    private Timestamp ultimoAcceso;
    private Timestamp createdAt;

    // Constructor vacío
    public Usuario() {
    }

    // Constructor completo (para obtener datos de la BD en el DAO)
    public Usuario( String id, String username, String passwords, String nombreCompleto, String email,
                   int idRol, boolean activo, int intentosFallidos, Timestamp bloqueadoHasta,
                   Timestamp ultimoAcceso, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.passwords = passwords;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.idRol = idRol;
        this.activo = activo;
        this.intentosFallidos = intentosFallidos;
        this.bloqueadoHasta = bloqueadoHasta;
        this.ultimoAcceso = ultimoAcceso;
        this.createdAt = createdAt;
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswords() {
        return passwords;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getIntentosFallidos() {
        return intentosFallidos;
    }

    public void setIntentosFallidos(int intentosFallidos) {
        this.intentosFallidos = intentosFallidos;
    }

    public Timestamp getBloqueadoHasta() {
        return bloqueadoHasta;
    }

    public void setBloqueadoHasta(Timestamp bloqueadoHasta) {
        this.bloqueadoHasta = bloqueadoHasta;
    }

    public Timestamp getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(Timestamp ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + idUsuario + ", username=" + username + ", email=" + email + ", idRol=" + idRol + '}';
    }
}