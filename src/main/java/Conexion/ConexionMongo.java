package Conexion;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConexionMongo {

    public static MongoClient cliente;
    public static MongoDatabase bd;

    // para conectar
    public static MongoDatabase getDatabase() {

        if (cliente == null) {
            try {
                // la ruta de mongo
                String c = "mongodb+srv://cristiantamariz09_db_user:ContaDesk2026@cluster0.v8ku75s.mongodb.net/contadesk_v2?retryWrites=true&w=majority&appName=Cluster0";

                cliente = MongoClients.create(c);
                bd = cliente.getDatabase("contadesk_v2");

                System.out.println("funciono");

            } catch (Exception e) {
                System.out.println("error");
            }
        }

        return bd;
    }

    // para cerrar
    public static void cerrarConexion() {
        if (cliente != null) {
            cliente.close();
            cliente = null;
            System.out.println("cerrado");
        }
    }
}