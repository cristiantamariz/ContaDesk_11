package Daos;

import Beans.Empleado;
import Conexion.ConexionMongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmpleadoDAO {

    private final MongoCollection<Document> coleccion;

    public EmpleadoDAO() {
        MongoDatabase db = ConexionMongo.getDatabase();
        this.coleccion = db.getCollection("empleados");
    }

    // ── 1. LISTAR TODOS ───────────────────────────────────────────────────
    public List<Empleado> listarTodos() {
        List<Empleado> lista = new ArrayList<>();
        try {
            for (Document doc : coleccion.find()) {
                lista.add(documentToEmpleado(doc));
            }
        } catch (Exception e) {
            System.err.println("Error al listar empleados: " + e.getMessage());
        }
        return lista;
    }

    // ── 2. CREAR (INSERT) ─────────────────────────────────────────────────
    public boolean crear(Empleado e) {
        try {
            if (e.getCreatedAt() == null) {
                e.setCreatedAt(new Date());
            }
            // Generar número de empleado automáticamente si el frontend no lo envía
            if (e.getNumeroEmpleado() == null || e.getNumeroEmpleado().trim().isEmpty()) {
                e.setNumeroEmpleado(generarNumeroEmpleado());
            }
            Document doc = empleadoToDocument(e);
            coleccion.insertOne(doc);

            ObjectId idGenerado = doc.getObjectId("_id");
            if (idGenerado != null) {
                e.setId(idGenerado.toHexString());
            }
            return true;
        } catch (Exception ex) {
            System.err.println("Error al crear empleado: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    // ── 3. ACTUALIZAR (UPDATE) ────────────────────────────────────────────
    public boolean actualizar(Empleado e) {
        try {
            if (e.getId() == null || e.getId().isEmpty()) {
                System.err.println("No se puede actualizar un empleado sin un ID de MongoDB válido.");
                return false;
            }
            ObjectId objectId = new ObjectId(e.getId());
            Document doc = empleadoToDocument(e);
            var resultado = coleccion.replaceOne(Filters.eq("_id", objectId), doc);
            return resultado.getModifiedCount() > 0;
        } catch (Exception ex) {
            System.err.println("Error al actualizar empleado: " + ex.getMessage());
            return false;
        }
    }

    // ── 4. ELIMINAR (DELETE) ──────────────────────────────────────────────
    public boolean eliminar(String id) {
        try {
            if (id == null || id.isEmpty()) return false;
            ObjectId objectId = new ObjectId(id);
            var resultado = coleccion.deleteOne(Filters.eq("_id", objectId));
            return resultado.getDeletedCount() > 0;
        } catch (Exception e) {
            System.err.println("Error al eliminar empleado: " + e.getMessage());
            return false;
        }
    }

    // ── 5. GENERAR NÚMERO DE EMPLEADO AUTOMÁTICO ─────────────────────────
    private String generarNumeroEmpleado() {
        int maxNumero = 0;
        try {
            for (Document doc : coleccion.find()) {
                String num = doc.getString("numero_empleado");
                if (num != null && num.startsWith("EMP-")) {
                    try {
                        int valor = Integer.parseInt(num.substring(4));
                        if (valor > maxNumero) maxNumero = valor;
                    } catch (NumberFormatException ignored) {}
                }
            }
        } catch (Exception e) {
            System.err.println("Error generando número de empleado: " + e.getMessage());
        }
        return String.format("EMP-%03d", maxNumero + 1);
    }

    // ── MAPEO DOCUMENT → BEAN ─────────────────────────────────────────────
    private Empleado documentToEmpleado(Document doc) {
        if (doc == null) return null;

        Empleado e = new Empleado();
        e.setId(doc.getObjectId("_id").toHexString());
        e.setNumeroEmpleado(doc.getString("numero_empleado"));
        e.setNombre(doc.getString("nombre"));
        e.setApellidoPaterno(doc.getString("apellido_paterno"));
        e.setApellidoMaterno(doc.getString("apellido_materno"));
        e.setFechaContratacion(doc.getString("fecha_contratacion"));
        e.setFechaNacimiento(doc.getString("fecha_nacimiento"));
        e.setFechaBaja(doc.getString("fecha_baja"));
        e.setTelefono(doc.getString("telefono"));
        e.setEstadoCivil(doc.getString("estado_civil"));
        e.setTipoContrato(doc.getString("tipo_contrato"));

        Boolean activoOpt = doc.getBoolean("activo");
        e.setActivo(activoOpt != null ? activoOpt : false);
        e.setCreatedAt(doc.getDate("created_at"));

        Object uIdRaw = doc.get("usuario_id");
        if (uIdRaw instanceof Number) {
            e.setUsuarioId(((Number) uIdRaw).intValue());
        } else {
            e.setUsuarioId(0);
        }

        Document deptDoc = (Document) doc.get("departamento");
        if (deptDoc != null) {
            e.getDepartamento().setNombre(deptDoc.getString("nombre"));
            e.getDepartamento().setDescripcion(deptDoc.getString("descripcion"));
        }

        Document puestoDoc = (Document) doc.get("puesto");
        if (puestoDoc != null) {
            e.getPuesto().setNombre(puestoDoc.getString("nombre"));
            Number min = (Number) puestoDoc.get("salario_minimo");
            if (min != null) e.getPuesto().setSalarioMinimo(min.doubleValue());
            Number max = (Number) puestoDoc.get("salario_maximo");
            if (max != null) e.getPuesto().setSalarioMaximo(max.doubleValue());
        }

        return e;
    }

    // ── MAPEO BEAN → DOCUMENT ─────────────────────────────────────────────
    private Document empleadoToDocument(Empleado e) {
        Document doc = new Document();

        if (e.getId() != null && !e.getId().isEmpty()) {
            doc.append("_id", new ObjectId(e.getId()));
        }

        // numero_empleado siempre viene con valor (generado en crear(), preservado en actualizar())
        doc.append("numero_empleado",    e.getNumeroEmpleado()    != null ? e.getNumeroEmpleado()    : "");
        doc.append("nombre",             e.getNombre()            != null ? e.getNombre()            : "");
        doc.append("apellido_paterno",   e.getApellidoPaterno()   != null ? e.getApellidoPaterno()   : "");
        doc.append("apellido_materno",   e.getApellidoMaterno()   != null ? e.getApellidoMaterno()   : "");
        doc.append("fecha_contratacion", e.getFechaContratacion() != null ? e.getFechaContratacion() : "");
        doc.append("fecha_nacimiento",   e.getFechaNacimiento()   != null ? e.getFechaNacimiento()   : "");
        doc.append("fecha_baja",         e.getFechaBaja()         != null ? e.getFechaBaja()         : "");
        doc.append("telefono",           e.getTelefono()          != null ? e.getTelefono()          : "");
        doc.append("estado_civil",       e.getEstadoCivil()       != null ? e.getEstadoCivil()       : "");
        doc.append("tipo_contrato",      e.getTipoContrato()      != null ? e.getTipoContrato()      : "");
        doc.append("activo",             e.isActivo());
        doc.append("created_at",         e.getCreatedAt()         != null ? e.getCreatedAt()         : new Date());

        // Forzar int32 — el schema exige bsonType: ['int', 'null']
        Integer uId = e.getUsuarioId();
        doc.append("usuario_id", uId != null ? uId.intValue() : 0);

        Document depto = new Document();
        if (e.getDepartamento() != null) {
            depto.append("nombre",      e.getDepartamento().getNombre()      != null ? e.getDepartamento().getNombre()      : "");
            depto.append("descripcion", e.getDepartamento().getDescripcion() != null ? e.getDepartamento().getDescripcion() : "");
        }
        doc.append("departamento", depto);

        Document puesto = new Document();
        if (e.getPuesto() != null) {
            puesto.append("nombre",         e.getPuesto().getNombre()        != null ? e.getPuesto().getNombre()        : "");
            puesto.append("salario_minimo", e.getPuesto().getSalarioMinimo() != null ? e.getPuesto().getSalarioMinimo() : 0.0);
            puesto.append("salario_maximo", e.getPuesto().getSalarioMaximo() != null ? e.getPuesto().getSalarioMaximo() : 0.0);
        }
        doc.append("puesto", puesto);

        return doc;
    }
}