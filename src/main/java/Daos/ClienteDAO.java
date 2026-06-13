package Daos;

import Beans.Cliente;
import Conexion.ConexionMongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
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

    public List<Cliente> listarTodos() {
        List<Cliente> lista = new ArrayList<>();
        for (Document doc : collection.find()) {
            lista.add(docToCliente(doc));
        }
        return lista;
    }

    public boolean crear(Cliente c) {
        try {
            Document doc = new Document()
                    .append("nombre", c.getNombre())
                    .append("razon_social", c.getRazonSocial())
                    .append("rfc", c.getRfc())
                    .append("telefono", c.getTelefono())
                    .append("email", c.getEmail())
                    .append("direccion", c.getDireccion())
                    .append("codigo_postal", c.getCodigoPostal())
                    .append("regimen_fiscal", c.getRegimenFiscal())
                    .append("uso_cfdi", c.getUsoCfdi())
                    .append("activo", c.isActivo())
                    .append("created_at", new Date());
            collection.insertOne(doc);
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean actualizar(String id, Cliente c) {
        try {
            collection.updateOne(Filters.eq("_id", new ObjectId(id)),
                    Updates.combine(
                            Updates.set("nombre", c.getNombre()),
                            Updates.set("razon_social", c.getRazonSocial()),
                            Updates.set("rfc", c.getRfc()),
                            Updates.set("telefono", c.getTelefono()),
                            Updates.set("email", c.getEmail()),
                            Updates.set("direccion", c.getDireccion()),
                            Updates.set("codigo_postal", c.getCodigoPostal()),
                            Updates.set("regimen_fiscal", c.getRegimenFiscal()),
                            Updates.set("uso_cfdi", c.getUsoCfdi()),
                            Updates.set("activo", c.isActivo())
                    ));
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    private Cliente docToCliente(Document doc) {
        Cliente c = new Cliente();
        // Usamos toString() para el ID ya que es ObjectId en Mongo
        c.setIdCliente(Integer.parseInt(doc.getObjectId("_id").toHexString().substring(0, 8), 16));
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
        return c;
    }
}