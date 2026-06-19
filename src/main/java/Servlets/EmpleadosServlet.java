package Servlets;

import Beans.Empleado;
import Daos.EmpleadoDAO;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Mapeamos ambas rutas por compatibilidad
@WebServlet(urlPatterns = {"/EmpleadoServlet", "/EmpleadosServlet"})
public class EmpleadosServlet extends HttpServlet {

    private final EmpleadoDAO dao = new EmpleadoDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");

        String action = req.getParameter("accion");
        if (action == null) action = req.getParameter("action");

        if ("listar".equals(action) || "list".equals(action)) {
            resp.getWriter().print(gson.toJson(dao.listarTodos()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        String action = req.getParameter("accion");
        if (action == null) action = req.getParameter("action");

        Map<String, Object> respuesta = new HashMap<>();

        try {
            // El caso de eliminar lee el ID desde la URL
            if ("eliminar".equals(action) || "delete".equals(action)) {
                String id = req.getParameter("id");
                boolean ok = dao.eliminar(id);
                respuesta.put("success", ok);
                respuesta.put("message", ok ? "Empleado eliminado correctamente." : "No se pudo eliminar.");
            }
            // Para crear y actualizar, leemos el JSON del cuerpo de la petición
            else {
                BufferedReader reader = req.getReader();
                Empleado e = gson.fromJson(reader, Empleado.class);

                if ("crear".equals(action) || "create".equals(action)) {
                    boolean ok = dao.crear(e);
                    respuesta.put("success", ok);
                    respuesta.put("message", ok ? "Empleado creado correctamente." : "No se pudo crear.");
                }
                else if ("actualizar".equals(action) || "update".equals(action)) {
                    boolean ok = dao.actualizar(e);
                    respuesta.put("success", ok);
                    respuesta.put("message", ok ? "Empleado actualizado correctamente." : "No se pudo actualizar.");
                }
                else {
                    respuesta.put("success", false);
                    respuesta.put("message", "Acción no reconocida.");
                }
            }
        } catch (Exception ex) {
            respuesta.put("success", false);
            respuesta.put("message", "Error procesando la solicitud: " + ex.getMessage());
            ex.printStackTrace();
        }

        resp.getWriter().print(gson.toJson(respuesta));
    }
}