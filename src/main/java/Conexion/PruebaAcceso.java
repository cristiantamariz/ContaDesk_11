package Conexion;

import com.mongodb.client.MongoDatabase;

public class PruebaAcceso {

    public static void main(String[] args) {

        ProbarConexion v = new ProbarConexion();

        // datos para probar
        String u = "admin";
        String p = "123456";

        System.out.println("probando...");

        if (v.validarAcceso(u, p)) {
            System.out.println("funciono");

            MongoDatabase db = ConexionMongo.getDatabase();
            System.out.println(db.getName());
        } else {
            System.out.println("no funciono");
        }

        ConexionMongo.cerrarConexion();
    }
}