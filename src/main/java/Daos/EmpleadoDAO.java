package Daos;

import Beans.Empleado;
import Conexion.ConexionMongo;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmpleadoDAO {

    private MongoCollection<Document> coleccion;

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
            // Forzar la estampa de tiempo de creación si viene vacía
            if (e.getCreatedAt() == null) {
                e.setCreatedAt(new Date());
            }
            Document doc = empleadoToDocument(e);
            coleccion.insertOne(doc);

            // Recuperar el ID generado por Mongo y asignarlo al Bean
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

            // Reemplaza el documento completo que coincida con el _id
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

    // ── METODOS DE MAPEO (DOCUMENT <-> BEAN) ──────────────────────────────

    private Empleado documentToEmpleado(Document doc) {
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
        e.setCurp(doc.getString("curp"));
        e.setRfc(doc.getString("rfc"));
        e.setNss(doc.getString("nss"));
        e.setEstadoCivil(doc.getString("estado_civil"));
        e.setTipoContrato(doc.getString("tipo_contrato"));

        // Evita NullPointerException si el campo primitivo booleano no existe
        Boolean activoOpt = doc.getBoolean("activo");
        e.setActivo(activoOpt != null ? activoOpt : false);

        e.setCreatedAt(doc.getDate("created_at"));
        Number uId = (Number) doc.get("usuario_id");
        e.setUsuarioId(uId != null ? uId.intValue() : 0);

        // Mapeo del objeto embebido: departamento
        Document deptDoc = (Document) doc.get("departamento");
        if (deptDoc != null) {
            e.getDepartamento().setNombre(deptDoc.getString("nombre"));
            e.getDepartamento().setDescripcion(deptDoc.getString("descripcion"));
        }

        // Mapeo del objeto embebido: puesto
        Document puestoDoc = (Document) doc.get("puesto");
        if (puestoDoc != null) {
            e.getPuesto().setNombre(puestoDoc.getString("nombre"));

            // 2. CORRECCIÓN PARA LOS SALARIOS (usando Number)
            Number min = (Number) puestoDoc.get("salario_minimo");
            if (min != null) {
                e.getPuesto().setSalarioMinimo(min.doubleValue());
            }

            Number max = (Number) puestoDoc.get("salario_maximo");
            if (max != null) {
                e.getPuesto().setSalarioMaximo(max.doubleValue());
            }
        }

        return e;
    }

    private Document empleadoToDocument(Empleado e) {
        Document doc = new Document();

        if (e.getId() != null && !e.getId().isEmpty()) {
            doc.append("_id", new ObjectId(e.getId()));
        }

        // Método auxiliar para evitar el null en campos de texto
        doc.append("numero_empleado", e.getNumeroEmpleado() != null ? e.getNumeroEmpleado() : "");
        doc.append("nombre", e.getNombre() != null ? e.getNombre() : "");
        doc.append("apellido_paterno", e.getApellidoPaterno() != null ? e.getApellidoPaterno() : "");
        doc.append("apellido_materno", e.getApellidoMaterno() != null ? e.getApellidoMaterno() : "");
        doc.append("fecha_contratacion", e.getFechaContratacion() != null ? e.getFechaContratacion() : "");
        doc.append("fecha_nacimiento", e.getFechaNacimiento() != null ? e.getFechaNacimiento() : "");
        doc.append("fecha_baja", e.getFechaBaja() != null ? e.getFechaBaja() : "");
        doc.append("telefono", e.getTelefono() != null ? e.getTelefono() : "");
        doc.append("curp", e.getCurp() != null ? e.getCurp() : "");
        doc.append("rfc", e.getRfc() != null ? e.getRfc() : "");
        doc.append("nss", e.getNss() != null ? e.getNss() : "");
        doc.append("estado_civil", e.getEstadoCivil() != null ? e.getEstadoCivil() : "");
        doc.append("tipo_contrato", e.getTipoContrato() != null ? e.getTipoContrato() : "");
        doc.append("activo", e.isActivo());
        doc.append("created_at", e.getCreatedAt() != null ? e.getCreatedAt() : new Date());
        doc.append("usuario_id", e.getUsuarioId() != null ? e.getUsuarioId() : 0);

        // Subdocumento departamento
        Document depto = new Document("nombre", "");
        Document desc = new Document("descripcion", "");
        if (e.getDepartamento() != null) {
            depto.put("nombre", e.getDepartamento().getNombre() != null ? e.getDepartamento().getNombre() : "");
            depto.put("descripcion", e.getDepartamento().getDescripcion() != null ? e.getDepartamento().getDescripcion() : "");
        }
        doc.append("departamento", depto);

        // Mapeo del objeto embebido: puesto
        Document puestoDoc = (Document) doc.get("puesto");
        if (puestoDoc != null) {
            e.getPuesto().setNombre(puestoDoc.getString("nombre"));

            // Extracción segura que funciona tanto para Integer como para Double
            Number min = (Number) puestoDoc.get("salario_minimo");
            Number max = (Number) puestoDoc.get("salario_maximo");

            if (min != null) e.getPuesto().setSalarioMinimo(min.doubleValue());
            if (max != null) e.getPuesto().setSalarioMaximo(max.doubleValue());
        }

        return doc;
    }
}