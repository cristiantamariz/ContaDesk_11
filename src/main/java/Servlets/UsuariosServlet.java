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
                    // CORRECCIÓN 1: El JS envía 'id', no 'idUsuario'
                    String id = req.getParameter("id");
                    u.setId(id);

                    boolean ok = dao.actualizar(u);
                    respuesta.put("ok", ok);
                    respuesta.put("mensaje", ok ? "Usuario actualizado." : "No se pudo actualizar.");
                }
                case "delete" -> {
                    // CORRECCIÓN 1: El JS envía 'id', no 'idUsuario'
                    String id = req.getParameter("id");
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
            respuesta.put("ok", false);
            respuesta.put("mensaje", "Error interno del servidor: " + e.getMessage());
            e.printStackTrace();
        }

        out.print(gson.toJson(respuesta));
    }

    private Usuario extraerUsuario(HttpServletRequest req) {
        Usuario u = new Usuario();

        u.setUsername(req.getParameter("username"));
        u.setNombreCompleto(req.getParameter("nombre_completo"));
        u.setEmail(req.getParameter("email"));
        u.setPasswords(req.getParameter("passwords"));
        u.setActivo("true".equals(req.getParameter("activo")));

        // CORRECCIÓN 2: Uso correcto del sub-documento Rol y manejo de formato numérico vs texto
        String idRolParam = req.getParameter("rol_id");
        if (idRolParam != null && !idRolParam.trim().isEmpty()) {
            try {
                // Intenta parsearlo como número (1, 2, 3)
                u.getRol().setIdRol(Integer.parseInt(idRolParam));
            } catch (NumberFormatException e) {
                // Si el JS envió el nombre del rol en vez del número (ej. "administrador")
                u.getRol().setNombre(idRolParam);
                switch(idRolParam.toLowerCase()) {
                    case "administrador" -> u.getRol().setIdRol(1);
                    case "superusuario" -> u.getRol().setIdRol(2);
                    default -> u.getRol().setIdRol(3);
                }
            }
        } else {
            u.getRol().setIdRol(3); // Rol por defecto
        }

        return u;
    }
}