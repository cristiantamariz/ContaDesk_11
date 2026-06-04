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

            System.out.println("BD: " + db.getName());

            MongoCollection<Document> col =
                    db.getCollection("usuarios");

            System.out.println("Colección obtenida");

            Document u =
                    col.find(Filters.eq("username", user)).first();

            System.out.println("Resultado: " + u);

            if (u != null) {

                String p = u.getString("passwords");

                System.out.println("Password BD: " + p);

                return pass.equals(p);
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }


}