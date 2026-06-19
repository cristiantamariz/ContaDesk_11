package Daos;

import Beans.Usuario;
import Conexion.ConexionMongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
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

    // ── 1. VALIDAR CREDENCIALES (LOGIN) ───────────────────────────────────
    public boolean validarUsuario(String username, String password) {
        try {
            Document usuario = collection.find(
                    Filters.eq("username", username.trim())
            ).first();

            if (usuario == null) {
                return false;
            }

            String storedPass = usuario.getString("passwords");
            return storedPass != null && storedPass.equals(password);
        } catch (Exception e) {
            System.err.println("Error al validar usuario: " + e.getMessage());
            return false;
        }
    }

    // ── 2. LISTAR TODOS ───────────────────────────────────────────────────
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        try {
            for (Document doc : collection.find()) {
                lista.add(docToUsuario(doc));
            }
        } catch (Exception e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }

    // ── 3. CREAR (INSERT) ─────────────────────────────────────────────────
    public boolean crear(Usuario u) {
        try {
            if (u.getCreatedAt() == null) {
                u.setCreatedAt(new Date());
            }

            Document doc = usuarioToDocument(u);
            collection.insertOne(doc);

            // Recuperar el ID generado por Mongo
            ObjectId idGenerado = doc.getObjectId("_id");
            if (idGenerado != null) {
                u.setId(idGenerado.toHexString());
            }
            return true;
        } catch (Exception e) {
            System.err.println("Error al crear usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ── 4. ACTUALIZAR (UPDATE) ────────────────────────────────────────────
    public boolean actualizar(Usuario u) {
        try {
            if (u.getId() == null || u.getId().isEmpty()) {
                System.err.println("No se puede actualizar un usuario sin un ID válido.");
                return false;
            }

            ObjectId objectId = new ObjectId(u.getId());
            Document doc = usuarioToDocument(u);

            // Reemplazo completo usando el filtro por ObjectId real
            var resultado = collection.replaceOne(Filters.eq("_id", objectId), doc);
            return resultado.getModifiedCount() > 0;
        } catch (Exception e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ── 5. ELIMINAR (DELETE) ──────────────────────────────────────────────
    public boolean eliminar(String id) {
        try {
            if (id == null || id.isEmpty()) return false;
            ObjectId objectId = new ObjectId(id);

            var resultado = collection.deleteOne(Filters.eq("_id", objectId));
            return resultado.getDeletedCount() > 0;
        } catch (Exception e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

    // ── METODOS DE MAPEO (DOCUMENT <-> BEAN) ──────────────────────────────

    private Usuario docToUsuario(Document doc) {
        if (doc == null) return null;

        Usuario u = new Usuario();
        u.setId(doc.getObjectId("_id").toHexString());
        u.setUsername(doc.getString("username"));
        u.setNombreCompleto(doc.getString("nombre_completo"));
        u.setEmail(doc.getString("email"));
        u.setPasswords(doc.getString("passwords"));
        u.setActivo(doc.getBoolean("activo", true));
        u.setIntentosFallidos(doc.getInteger("intentos_fallidos", 0));
        u.setBloqueadoHasta(doc.getDate("bloqueado_hasta"));
        u.setUltimoAcceso(doc.getDate("ultimo_acceso"));
        u.setCreatedAt(doc.getDate("created_at"));

        // Mapeo seguro del objeto embebido: rol
        Document rolDoc = (Document) doc.get("rol");
        if (rolDoc != null) {
            u.getRol().setNombre(rolDoc.getString("nombre"));

            // Deducción del ID numérico interno para mantener compatibilidad con tu vista si es necesario
            Number idRolNum = (Number) rolDoc.get("id_rol");
            if (idRolNum != null) {
                u.getRol().setIdRol(idRolNum.intValue());
            } else {
                // Si no existía el campo numérico en Atlas, lo deducimos dinámicamente por texto
                String nombreRol = rolDoc.getString("nombre");
                if ("administrador".equals(nombreRol)) u.getRol().setIdRol(1);
                else if ("superusuario".equals(nombreRol)) u.getRol().setIdRol(2);
                else u.getRol().setIdRol(3);
            }
        }

        return u;
    }

    private Document usuarioToDocument(Usuario u) {
        Document doc = new Document();

        if (u.getId() != null && !u.getId().isEmpty()) {
            doc.append("_id", new ObjectId(u.getId()));
        }

        doc.append("username", u.getUsername() != null ? u.getUsername() : "");
        doc.append("nombre_completo", u.getNombreCompleto() != null ? u.getNombreCompleto() : "");
        doc.append("email", u.getEmail() != null ? u.getEmail() : "");
        doc.append("passwords", u.getPasswords() != null ? u.getPasswords() : "");
        doc.append("activo", u.isActivo());
        doc.append("intentos_fallidos", u.getIntentosFallidos());
        doc.append("bloqueado_hasta", u.getBloqueadoHasta());
        doc.append("ultimo_acceso", u.getUltimoAcceso());
        doc.append("created_at", u.getCreatedAt() != null ? u.getCreatedAt() : new Date());

        // Mapeo seguro hacia el subdocumento embebido del rol
        Document rolDoc = new Document();
        if (u.getRol() != null) {
            String nombreRol = u.getRol().getNombre();

            // Si el nombre viene vacío pero el ID numérico está seteado, rellenamos por consistencia
            if (nombreRol == null || nombreRol.isEmpty()) {
                switch (u.getRol().getIdRol()) {
                    case 1: nombreRol = "administrador"; break;
                    case 2: nombreRol = "superusuario"; break;
                    default: nombreRol = "usuario"; break;
                }
            }

            rolDoc.append("id_rol", u.getRol().getIdRol() != 0 ? u.getRol().getIdRol() : 3);
            rolDoc.append("nombre", nombreRol);
            rolDoc.append("descripcion", "administrador".equals(nombreRol) ? "Acceso total" :
                    "superusuario".equals(nombreRol) ? "Gestión operativa" : "Usuario estándar");
            rolDoc.append("permisos", new ArrayList<String>()); // Mantiene el array vacío del esquema original
        }
        doc.append("rol", rolDoc);

        return doc;
    }
}