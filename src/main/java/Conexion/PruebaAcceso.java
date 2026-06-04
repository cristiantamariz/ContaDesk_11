package Conexion;

import com.mongodb.client.MongoDatabase;

public class PruebaAcceso {

    public static void main(String[] args) {

        try {

            System.out.println("===== INICIANDO PRUEBA =====");

            String usuario = "admin";
            String password = "123456";

            ProbarConexion prueba = new ProbarConexion();

            System.out.println("Intentando validar usuario: " + usuario);

            boolean acceso = prueba.validarAcceso(usuario, password);

            if (acceso) {

                System.out.println("✓ Login correcto");

                MongoDatabase db = ConexionMongo.getDatabase();

                if (db != null) {
                    System.out.println("Base de datos: " + db.getName());
                } else {
                    System.out.println("La base de datos es null");
                }

            } else {

                System.out.println("✗ Login incorrecto");
            }

        } catch (Exception e) {

            System.out.println("Error durante la prueba:");
            e.printStackTrace();

        } finally {

            ConexionMongo.cerrarConexion();
        }
    }
}