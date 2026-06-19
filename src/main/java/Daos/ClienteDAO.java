package Daos;

import Beans.Cliente;
import Conexion.ConexionMongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClienteDAO {
    private final MongoCollection<Document> collection;

    public ClienteDAO() {
        this.collection = ConexionMongo.getDatabase().getCollection("clientes");
    }

    // ── 1. LISTAR TODOS ───────────────────────────────────────────────────
    public List<Cliente> listarTodos() {
        List<Cliente> lista = new ArrayList<>();
        try {
            for (Document doc : collection.find()) {
                lista.add(docToCliente(doc));
            }
        } catch (Exception e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        }
        return lista;
    }

    // ── 2. CREAR (INSERT) ─────────────────────────────────────────────────
    public boolean crear(Cliente c) {
        try {
            if (c.getCreatedAt() == null) {
                c.setCreatedAt(new Date());
            }
            Document doc = clienteToDocument(c);
            collection.insertOne(doc);

            // Recuperamos el ID real de 24 caracteres generado por Mongo
            ObjectId idGenerado = doc.getObjectId("_id");
            if (idGenerado != null) {
                c.setIdCliente(idGenerado.toHexString());
            }
            return true;
        } catch (Exception e) {
            System.err.println("Error al crear cliente: " + e.getMessage());
            return false;
        }
    }

    // ── 3. ACTUALIZAR (UPDATE) ────────────────────────────────────────────
    public boolean actualizar(Cliente c) {
        try {
            if (c.getIdCliente() == null || c.getIdCliente().isEmpty()) {
                System.err.println("No se puede actualizar un cliente sin un ID válido.");
                return false;
            }

            ObjectId objectId = new ObjectId(c.getIdCliente());
            Document doc = clienteToDocument(c);

            // Reemplazo completo usando el filtro por ObjectId real
            var resultado = collection.replaceOne(Filters.eq("_id", objectId), doc);
            return resultado.getModifiedCount() > 0;
        } catch (Exception e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    // ── METODOS DE MAPEO (DOCUMENT <-> BEAN) ──────────────────────────────

    private Cliente docToCliente(Document doc) {
        if (doc == null) return null;

        Cliente c = new Cliente();
        // CORRECCIÓN: Guardamos el String Hexadecimal completo de 24 caracteres
        c.setIdCliente(doc.getObjectId("_id").toHexString());
        c.setNombre(doc.getString("nombre"));
        c.setRazonSocial(doc.getString("razon_social"));
        c.setRfc(doc.getString("rfc"));
        c.setTelefono(doc.getString("telefono"));
        c.setEmail(doc.getString("email"));
        c.setDireccion(doc.getString("direccion"));
        c.setCodigoPostal(doc.getString("codigo_postal"));
        c.setRegimenFiscal(doc.getString("regimen_fiscal"));
        c.setUsoCfdi(doc.getString("uso_cfdi"));
        c.setActivo(doc.getBoolean("activo", true));
        c.setCreatedAt(doc.getDate("created_at"));
        return c;
    }

    private Document clienteToDocument(Cliente c) {
        Document doc = new Document();

        if (c.getIdCliente() != null && !c.getIdCliente().isEmpty()) {
            doc.append("_id", new ObjectId(c.getIdCliente()));
        }

        doc.append("nombre", c.getNombre() != null ? c.getNombre() : "");
        doc.append("razon_social", c.getRazonSocial() != null ? c.getRazonSocial() : "");
        doc.append("rfc", c.getRfc() != null ? c.getRfc() : "");
        doc.append("telefono", c.getTelefono() != null ? c.getTelefono() : "");
        doc.append("email", c.getEmail() != null ? c.getEmail() : "");
        doc.append("direccion", c.getDireccion() != null ? c.getDireccion() : "");
        doc.append("codigo_postal", c.getCodigoPostal() != null ? c.getCodigoPostal() : "");
        doc.append("regimen_fiscal", c.getRegimenFiscal() != null ? c.getRegimenFiscal() : "");
        doc.append("uso_cfdi", c.getUsoCfdi() != null ? c.getUsoCfdi() : "");
        doc.append("activo", c.isActivo());
        doc.append("created_at", c.getCreatedAt() != null ? c.getCreatedAt() : new Date());

        return doc;
    }

    // ── 4. ELIMINAR (DELETE) ──────────────────────────────────────────────
    public boolean eliminar(String id) {
        try {
            if (id == null || id.isEmpty()) return false;

            var resultado = collection.deleteOne(Filters.eq("_id", new ObjectId(id)));
            return resultado.getDeletedCount() > 0;
        } catch (Exception e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }
}