package Servlets;

import Beans.Cliente;
import Daos.ClienteDAO;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/ClientesServlet")
public class ClientesServlet extends HttpServlet {
    private final ClienteDAO dao = new ClienteDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String action = req.getParameter("action");
        if ("list".equals(action)) {
            resp.getWriter().print(gson.toJson(dao.listarTodos()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        String action = req.getParameter("action");
        Map<String, Object> respuesta = new HashMap<>();

        Cliente c = new Cliente(
                req.getParameter("nombre"),
                req.getParameter("razon_social"),
                req.getParameter("rfc"),
                req.getParameter("telefono"),
                req.getParameter("email"),
                req.getParameter("direccion"),
                req.getParameter("codigo_postal"),
                req.getParameter("regimen_fiscal"),
                req.getParameter("uso_cfdi"),
                "true".equals(req.getParameter("activo"))
        );

        if ("create".equals(action)) {
            boolean ok = dao.crear(c);
            respuesta.put("ok", ok);
        } else if ("update".equals(action)) {
            boolean ok = dao.actualizar(req.getParameter("id"), c);
            respuesta.put("ok", ok);
        }

        resp.getWriter().print(gson.toJson(respuesta));
    }
}