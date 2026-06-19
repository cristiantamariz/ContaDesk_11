package Servlets;

import Beans.Cliente;
import Daos.ClienteDAO;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Mapeamos ambas rutas por si el JS lo llama en singular o plural
@WebServlet(urlPatterns = {"/ClientesServlet", "/ClienteServlet"})
public class ClientesServlet extends HttpServlet {

    private final ClienteDAO dao = new ClienteDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");

        // Soporte de compatibilidad para "action" (viejo JS) y "accion" (nuevo JS)
        String action = req.getParameter("action");
        if (action == null) action = req.getParameter("accion");

        if ("list".equals(action) || "listar".equals(action)) {
            resp.getWriter().print(gson.toJson(dao.listarTodos()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        String action = req.getParameter("action");
        if (action == null) action = req.getParameter("accion");

        Map<String, Object> respuesta = new HashMap<>();

        try {
            // Magia de Gson: Lee el JSON del cuerpo de la petición y lo convierte en el Bean Cliente automáticamente
            BufferedReader reader = req.getReader();
            Cliente c = gson.fromJson(reader, Cliente.class);

            if ("create".equals(action) || "crear".equals(action)) {

                boolean ok = dao.crear(c);
                respuesta.put("success", ok); // El JS actual espera "success"
                respuesta.put("message", ok ? "Cliente creado correctamente." : "No se pudo crear.");

            } else if ("update".equals(action) || "actualizar".equals(action)) {

                // El idCliente ya viene seteado dentro del objeto 'c' gracias a Gson
                boolean ok = dao.actualizar(c);
                respuesta.put("success", ok);
                respuesta.put("message", ok ? "Cliente actualizado correctamente." : "No se pudo actualizar.");

            } else if ("delete".equals(action) || "eliminar".equals(action)) {

                // Para eliminar, el ID suele venir en la URL: ?accion=eliminar&id=...
                String id = req.getParameter("id");
                boolean ok = dao.eliminar(id);
                respuesta.put("success", ok);
                respuesta.put("message", ok ? "Cliente eliminado correctamente." : "No se pudo eliminar.");

            } else {
                respuesta.put("success", false);
                respuesta.put("message", "Acción no reconocida.");
            }

        } catch (Exception e) {
            respuesta.put("success", false);
            respuesta.put("message", "Error procesando la solicitud: " + e.getMessage());
            e.printStackTrace();
        }

        resp.getWriter().print(gson.toJson(respuesta));
    }
}