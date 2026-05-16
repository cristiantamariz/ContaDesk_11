package Conexion;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class ProbarConexion {

    // metodo para el login
    public boolean validarAcceso(String user, String pass) {

        try {
            MongoDatabase db = ConexionMongo.getDatabase();

            MongoCollection<Document> col = db.getCollection("usuarios");

            // busca al usuario
            Document u = col.find(Filters.eq("username", user)).first();

            if (u != null) {
                String p = u.getString("passwords");

                if (pass.equals(p)) {
                    return true;
                }
            }

        } catch (Exception e) {
            System.out.println("error");
        }

        return false;
    }


}