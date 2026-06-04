package Servlets;

import Beans.Empleado;
import Daos.EmpleadoDAO;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "EmpleadosServlet", urlPatterns = {"/EmpleadosServlet"})
public class EmpleadosServlet extends HttpServlet {

    private EmpleadoDAO empleadoDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        empleadoDAO = new EmpleadoDAO();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        PrintWriter out = response.getWriter();

        if ("list".equals(action)) {
            List<Empleado> lista = empleadoDAO.listarTodos();
            out.print(gson.toJson(lista));
        } else {
            out.print("{\"error\": \"Acción no válida\"}");
        }
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        PrintWriter out = response.getWriter();
        String jsonRespuesta = "";

        try {
            if ("create".equals(action) || "update".equals(action)) {
                Empleado e = new Empleado();

                // Mapeo básico
                String id = request.getParameter("id");
                if (id != null && !id.isEmpty()) e.setId(id);

                e.setNumeroEmpleado(request.getParameter("numeroEmpleado"));
                e.setNombre(request.getParameter("nombre"));
                e.setApellidoPaterno(request.getParameter("apellidoPaterno"));
                e.setFechaContratacion(request.getParameter("fechaContratacion"));
                e.setActivo(Boolean.parseBoolean(request.getParameter("activo")));

                // Mapeo de objetos embebidos
                e.getDepartamento().setNombre(request.getParameter("departamentoNombre"));
                e.getPuesto().setNombre(request.getParameter("puestoNombre"));

                String salarioStr = request.getParameter("salario");
                if (salarioStr != null && !salarioStr.isEmpty()) {
                    e.getPuesto().setSalarioMinimo(Double.parseDouble(salarioStr));
                }

                boolean exito;
                if ("create".equals(action)) {
                    exito = empleadoDAO.crear(e);
                    jsonRespuesta = exito ? "{\"ok\":true, \"mensaje\":\"Empleado registrado con éxito\"}"
                            : "{\"ok\":false, \"mensaje\":\"Error al registrar empleado\"}";
                } else {
                    exito = empleadoDAO.actualizar(e);
                    jsonRespuesta = exito ? "{\"ok\":true, \"mensaje\":\"Empleado actualizado\"}"
                            : "{\"ok\":false, \"mensaje\":\"Error al actualizar empleado\"}";
                }

            } else if ("delete".equals(action)) {
                String id = request.getParameter("id");
                boolean exito = empleadoDAO.eliminar(id);
                jsonRespuesta = exito ? "{\"ok\":true, \"mensaje\":\"Empleado eliminado\"}"
                        : "{\"ok\":false, \"mensaje\":\"No se pudo eliminar el empleado\"}";
            } else {
                jsonRespuesta = "{\"ok\":false, \"mensaje\":\"Acción POST no reconocida\"}";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            jsonRespuesta = "{\"ok\":false, \"mensaje\":\"Error en el servidor: " + ex.getMessage() + "\"}";
        }

        out.print(jsonRespuesta);
        out.flush();
    }
}