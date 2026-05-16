package Beans;

public class ConceptoNomina {

    private int idConcepto;
    private String codigo;
    private String nombre;
    private String tipo;
    private boolean esGravable;
    private boolean esImss;

    // Constructor vacío
    public ConceptoNomina() {
    }

    // Constructor completo (para SELECT)
    public ConceptoNomina(int idConcepto, String codigo, String nombre, String tipo,
                          boolean esGravable, boolean esImss) {
        this.idConcepto = idConcepto;
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.esGravable = esGravable;
        this.esImss = esImss;
    }

    // Constructor sin ID (para INSERT)
    public ConceptoNomina(String codigo, String nombre, String tipo,
                          boolean esGravable, boolean esImss) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.esGravable = esGravable;
        this.esImss = esImss;
    }

    // Getters y Setters
    public int getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(int idConcepto) {
        this.idConcepto = idConcepto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isEsGravable() {
        return esGravable;
    }

    public void setEsGravable(boolean esGravable) {
        this.esGravable = esGravable;
    }

    public boolean isEsImss() {
        return esImss;
    }

    public void setEsImss(boolean esImss) {
        this.esImss = esImss;
    }

    @Override
    public String toString() {
        return "ConceptoNomina{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}