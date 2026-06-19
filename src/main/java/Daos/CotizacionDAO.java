package Daos;

import Beans.Cotizacion;
import Conexion.ConexionMongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CotizacionDAO {

    private final MongoCollection<Document> collection;

    public CotizacionDAO() {
        this.collection = ConexionMongo.getDatabase().getCollection("cotizaciones");
    }

    // ── 1. LISTAR TODOS ───────────────────────────────────────────────────
    public List<Cotizacion> listarTodos() {
        List<Cotizacion> lista = new ArrayList<>();
        try {
            for (Document doc : collection.find().sort(new Document("createdAt", -1))) {
                lista.add(docToCotizacion(doc));
            }
        } catch (Exception e) {
            System.err.println("Error al listar cotizaciones: " + e.getMessage());
        }
        return lista;
    }

    // ── 2. CREAR (INSERT) ─────────────────────────────────────────────────
    public boolean crear(Cotizacion c) {
        try {
            if (c.getCreatedAt() == null) {
                c.setCreatedAt(new Date());
            }
            if (c.getEstatus() == null || c.getEstatus().isEmpty()) {
                c.setEstatus("Pendiente");
            }

            Document doc = cotizacionToDocument(c);

            // Log de diagnóstico — confirma qué campos va a insertar
            System.out.println("[CotizacionDAO] Insertando documento: " + doc.toJson());

            collection.insertOne(doc);

            ObjectId idGenerado = doc.getObjectId("_id");
            if (idGenerado != null) {
                c.setId(idGenerado.toHexString());
            }
            return true;
        } catch (Exception e) {
            System.err.println("Error al crear cotización: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ── 3. ACTUALIZAR (UPDATE) ────────────────────────────────────────────
    public boolean actualizar(Cotizacion c) {
        try {
            if (c.getId() == null || c.getId().isEmpty()) {
                System.err.println("No se puede actualizar una cotización sin ID válido.");
                return false;
            }

            ObjectId objectId = new ObjectId(c.getId());
            Document doc = cotizacionToDocument(c);

            var resultado = collection.replaceOne(Filters.eq("_id", objectId), doc);
            return resultado.getModifiedCount() > 0;
        } catch (Exception e) {
            System.err.println("Error al actualizar cotización: " + e.getMessage());
            return false;
        }
    }

    // ── 4. ELIMINAR (DELETE) ──────────────────────────────────────────────
    public boolean eliminar(String id) {
        try {
            if (id == null || id.isEmpty()) return false;
            ObjectId objectId = new ObjectId(id);

            var resultado = collection.deleteOne(Filters.eq("_id", objectId));
            return resultado.getDeletedCount() > 0;
        } catch (Exception e) {
            System.err.println("Error al eliminar cotización: " + e.getMessage());
            return false;
        }
    }

    // ── MAPEO: Document → Bean ────────────────────────────────────────────
    private Cotizacion docToCotizacion(Document doc) {
        if (doc == null) return null;

        Cotizacion c = new Cotizacion();
        c.setId(doc.getObjectId("_id").toHexString());

        c.setIdCliente(doc.getString("idCliente"));
        c.setNombreCliente(doc.getString("nombreCliente"));
        c.setRegimen(doc.getString("regimen"));

        c.setIdEmpleado(doc.getString("idEmpleado"));
        c.setNombreEmpleado(doc.getString("nombreEmpleado"));

        c.setVolumenFacturas(doc.getInteger("volumenFacturas", 0));
        c.setHorasEstimadas(doc.getInteger("horasEstimadas", 0));

        Number tarifa = (Number) doc.get("tarifaBase");
        c.setTarifaBase(tarifa != null ? tarifa.doubleValue() : 0.0);

        Number total = (Number) doc.get("montoTotal");
        c.setMontoTotal(total != null ? total.doubleValue() : 0.0);

        c.setEstatus(doc.getString("estatus"));
        c.setCreatedAt(doc.getDate("createdAt"));   // ← camelCase, igual que el schema

        return c;
    }

    // ── MAPEO: Bean → Document ────────────────────────────────────────────
    private Document cotizacionToDocument(Cotizacion c) {
        Document doc = new Document();

        // _id solo se incluye al actualizar (en crear lo genera MongoDB)
        if (c.getId() != null && !c.getId().isEmpty()) {
            doc.append("_id", new ObjectId(c.getId()));
        }

        // Strings requeridos
        doc.append("idCliente",      c.getIdCliente()      != null ? c.getIdCliente()      : "");
        doc.append("nombreCliente",  c.getNombreCliente()  != null ? c.getNombreCliente()  : "");
        doc.append("regimen",        c.getRegimen()        != null ? c.getRegimen()        : "");
        doc.append("idEmpleado",     c.getIdEmpleado()     != null ? c.getIdEmpleado()     : "");
        doc.append("nombreEmpleado", c.getNombreEmpleado() != null ? c.getNombreEmpleado() : "");

        // Números
        doc.append("volumenFacturas", c.getVolumenFacturas());
        doc.append("horasEstimadas",  c.getHorasEstimadas());
        doc.append("tarifaBase",      c.getTarifaBase());
        doc.append("montoTotal",      c.getMontoTotal());

        // Control
        doc.append("estatus",   c.getEstatus()    != null ? c.getEstatus()    : "Pendiente");
        doc.append("createdAt", c.getCreatedAt()  != null ? c.getCreatedAt()  : new Date()); // ← camelCase

        return doc;
    }
}