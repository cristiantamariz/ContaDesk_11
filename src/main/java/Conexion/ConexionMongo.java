package Conexion;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConexionMongo {

    private static MongoClient cliente;
    private static MongoDatabase bd;

    public static MongoDatabase getDatabase() {

        if (cliente == null) {
            try {

                String uri =
                        "mongodb+srv://cristiantamariz09_db_user:root@cluster0.v8ku75s.mongodb.net/?appName=Cluster0";

                cliente = MongoClients.create(uri);

                bd = cliente.getDatabase("contadesk_v2");

                System.out.println("Conexión a MongoDB Atlas exitosa");

            } catch (Exception e) {

                System.out.println("Error al conectar con MongoDB:");
                e.printStackTrace();

            }
        }

        return bd;
    }

    public static void cerrarConexion() {

        if (cliente != null) {

            cliente.close();
            cliente = null;
            bd = null;

            System.out.println("Conexión cerrada");
        }
    }
}