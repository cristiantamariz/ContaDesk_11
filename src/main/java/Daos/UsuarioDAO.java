package Daos;

import Conexion.ConexionMongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class UsuarioDAO {

    private final MongoCollection<Document> collection;

    public UsuarioDAO() {
        MongoDatabase db = ConexionMongo.getDatabase();
        this.collection = db.getCollection("usuarios");
    }

    public boolean validarUsuario(String username, String password) {

        // 1. Buscar el documento por username (igual que ProbarConexion.validarAcceso)
        Document usuario = collection.find(
                Filters.eq("username", username.trim())
        ).first();

        // 2. Si no existe el usuario, denegar
        if (usuario == null) {
            return false;
        }

        // 3. Comparar la contraseña contra el campo "passwords" de MongoDB
        String storedPass = usuario.getString("passwords");
        return password.equals(storedPass);
    }
}