package Servlets;

import Beans.Usuario;
import Daos.UsuarioDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/UsuariosServlet")
public class UsuariosServlet extends HttpServlet {

    private final UsuarioDAO dao = new UsuarioDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        // Protección: solo usuarios con sesión activa
        if (req.getSession().getAttribute("usuarioLogueado") == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String action = req.getParameter("action");
        if ("list".equals(action)) {
            List<Usuario> lista = dao.listarTodos();
            out.print(gson.toJson(lista));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        if (req.getSession().getAttribute("usuarioLogueado") == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String action = req.getParameter("action");

        // Utilizamos un Map para que Gson genere el JSON limpiamente
        Map<String, Object> respuesta = new HashMap<>();

        try {
            switch (action == null ? "" : action) {
                case "create" -> {
                    Usuario u = extraerUsuario(req);
                    // TODO: hashear u.getPasswords() con BCrypt antes de guardar
                    boolean ok = dao.crear(u);
                    respuesta.put("ok", ok);
                    respuesta.put("mensaje", ok ? "Usuario creado correctamente." : "No se pudo crear el usuario.");
                }
                case "update" -> {
                    Usuario u = extraerUsuario(req);
                    // Corrección: Mantenemos el ID como String para el ObjectId de Mongo
                    String id = req.getParameter("idUsuario");
                    u.setId(id);

                    boolean ok = dao.actualizar(u);
                    respuesta.put("ok", ok);
                    respuesta.put("mensaje", ok ? "Usuario actualizado." : "No se pudo actualizar.");
                }
                case "delete" -> {
                    String id = req.getParameter("idUsuario");
                    boolean ok = dao.eliminar(id);
                    respuesta.put("ok", ok);
                    respuesta.put("mensaje", ok ? "Usuario eliminado." : "No se pudo eliminar.");
                }
                default -> {
                    respuesta.put("ok", false);
                    respuesta.put("mensaje", "Acción desconocida.");
                }
            }
        } catch (Exception e) {
            // Captura de errores para evitar que el cliente reciba un HTML de Tomcat
            respuesta.put("ok", false);
            respuesta.put("mensaje", "Error interno del servidor: " + e.getMessage());
            e.printStackTrace();
        }

        out.print(gson.toJson(respuesta));
    }

    private Usuario extraerUsuario(HttpServletRequest req) {
        Usuario u = new Usuario();

        u.setUsername(req.getParameter("username"));
        u.setNombreCompleto(req.getParameter("nombreCompleto"));
        u.setEmail(req.getParameter("email"));
        u.setPasswords(req.getParameter("passwords"));
        u.setActivo("true".equals(req.getParameter("activo")));

        // Manejo seguro del Integer para evitar NumberFormatException
        String idRolParam = req.getParameter("idRol");
        if (idRolParam != null && !idRolParam.trim().isEmpty()) {
            u.setIdRol(Integer.parseInt(idRolParam));
        } else {
            u.setIdRol(3); // Rol por defecto
        }

        return u;
    }
}