package Daos;

import Beans.Usuario;
import Conexion.ConexionMongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UsuarioDAO {

    private final MongoCollection<Document> collection;

    public UsuarioDAO() {
        MongoDatabase db = ConexionMongo.getDatabase();
        this.collection = db.getCollection("usuarios");
    }

    public boolean validarUsuario(String username, String password) {

        Document usuario = collection.find(
                Filters.eq("username", username.trim())
        ).first();

        if (usuario == null) {
            return false;
        }

        String storedPass = usuario.getString("passwords");

        // Validación extra: previene NullPointerException si el campo no existe
        return storedPass != null && storedPass.equals(password);
    }

    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        for (Document doc : collection.find()) {
            lista.add(docToUsuario(doc));
        }
        return lista;
    }

    public boolean crear(Usuario u) {
        try {
            Document rol = crearRol(u.getIdRol());

            // Asegúrate de enviar un string vacío en lugar de null
            String nombre = (u.getNombreCompleto() != null) ? u.getNombreCompleto() : "";
            String username = (u.getUsername() != null) ? u.getUsername() : "";
            String email = (u.getEmail() != null) ? u.getEmail() : "";

            Document doc = new Document()
                    .append("username", username)
                    .append("nombre_completo", nombre) // Aquí ya no será null
                    .append("email", email)
                    .append("passwords", u.getPasswords() != null ? u.getPasswords() : "")
                    .append("activo", u.isActivo())
                    .append("intentos_fallidos", 0)
                    .append("bloqueado_hasta", null)
                    .append("ultimo_acceso", null)
                    .append("created_at", new Date())
                    .append("rol", rol);

            collection.insertOne(doc);
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // Aquí verás si hay otro campo fallando
            return false;
        }
    }

    public boolean actualizar(Usuario u) {

        try {
            Document rol = crearRol(u.getIdRol());

            collection.updateOne(
                    Filters.eq("_id", new ObjectId(u.getId())),
                    Updates.combine(
                            Updates.set("username", u.getUsername()),
                            Updates.set("nombre_completo", u.getNombreCompleto()),
                            Updates.set("email", u.getEmail()),
                            Updates.set("activo", u.isActivo()),
                            Updates.set("rol", rol)
                    )
            );
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(String id) {

        try {
            collection.deleteOne(
                    Filters.eq("_id", new ObjectId(id))
            );
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Document crearRol(int idRol) {

        Document rol = new Document();

        switch (idRol) {
            case 1:
                rol.append("nombre", "administrador")
                        .append("descripcion", "Acceso total")
                        .append("permisos", new ArrayList<>());
                break;

            case 2:
                // Corrección: Ahora sí respeta el Enum 'superusuario' del esquema
                rol.append("nombre", "superusuario")
                        .append("descripcion", "Gestión operativa")
                        .append("permisos", new ArrayList<>());
                break;

            case 3:
            default:
                rol.append("nombre", "usuario")
                        .append("descripcion", "Usuario estándar")
                        .append("permisos", new ArrayList<>());
                break;
        }

        return rol;
    }

    private Usuario docToUsuario(Document doc) {

        Usuario u = new Usuario();

        u.setId(doc.getObjectId("_id").toHexString());
        u.setUsername(doc.getString("username"));
        u.setNombreCompleto(doc.getString("nombre_completo"));
        u.setEmail(doc.getString("email"));
        u.setActivo(Boolean.TRUE.equals(doc.getBoolean("activo")));

        Document rol = doc.get("rol", Document.class);

        if (rol != null) {
            String nombreRol = rol.getString("nombre");

            if (nombreRol != null) {
                switch (nombreRol) {
                    case "administrador":
                        u.setIdRol(1);
                        break;
                    case "superusuario":
                        u.setIdRol(2);
                        break;
                    case "usuario":
                    default:
                        u.setIdRol(3);
                        break;
                }
            } else {
                u.setIdRol(3);
            }
        }

        return u;
    }
}