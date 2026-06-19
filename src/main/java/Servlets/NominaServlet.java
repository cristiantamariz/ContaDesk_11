package Servlets;

import Beans.Nomina;
import Daos.NominaDAO;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/NominaServlet")
public class NominaServlet extends HttpServlet {

    private final NominaDAO dao  = new NominaDAO();
    private final Gson      gson = new Gson();

    // ── GET: listar ───────────────────────────────────────────────────────
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");

        String accion = req.getParameter("accion");

        if ("listar".equals(accion)) {
            resp.getWriter().print(gson.toJson(dao.listarTodos()));
            return;
        }

        // ── calcular: preview antes de guardar ────────────────────────────
        if ("calcular".equals(accion)) {
            String idEmpleado  = req.getParameter("idEmpleado");
            String mesStr      = req.getParameter("mes");
            String anioStr     = req.getParameter("anio");
            String deducStr    = req.getParameter("deducciones");

            Map<String, Object> respuesta = new HashMap<>();
            try {
                int    mes        = Integer.parseInt(mesStr);
                int    anio       = Integer.parseInt(anioStr);
                double deducciones = deducStr != null && !deducStr.isEmpty()
                        ? Double.parseDouble(deducStr) : 0.0;

                Nomina n = dao.calcular(idEmpleado, mes, anio, deducciones);
                if (n != null) {
                    respuesta.put("success", true);
                    respuesta.put("data", n);
                } else {
                    respuesta.put("success", false);
                    respuesta.put("message", "No se pudo calcular. Verifica el empleado.");
                }
            } catch (Exception e) {
                respuesta.put("success", false);
                respuesta.put("message", "Parámetros inválidos: " + e.getMessage());
            }
            resp.getWriter().print(gson.toJson(respuesta));
            return;
        }

        // Acción no reconocida
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        Map<String, Object> err = new HashMap<>();
        err.put("success", false);
        err.put("message", "Acción no reconocida: " + accion);
        resp.getWriter().print(gson.toJson(err));
    }

    // ── POST: guardar, actualizarEstado, eliminar ─────────────────────────
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        String accion = req.getParameter("accion");
        Map<String, Object> respuesta = new HashMap<>();

        try {
            // ── eliminar: parámetros en URL ────────────────────────────────
            if ("eliminar".equals(accion)) {
                String id = req.getParameter("id");
                boolean ok = dao.eliminar(id);
                respuesta.put("success", ok);
                respuesta.put("message", ok ? "Nómina eliminada." : "No se pudo eliminar.");
                resp.getWriter().print(gson.toJson(respuesta));
                return;
            }

            // ── actualizarEstado: id + nuevoEstado en URL ──────────────────
            if ("actualizarEstado".equals(accion)) {
                String id          = req.getParameter("id");
                String nuevoEstado = req.getParameter("estado");
                boolean ok = dao.actualizarEstado(id, nuevoEstado);
                respuesta.put("success", ok);
                respuesta.put("message", ok ? "Estado actualizado a: " + nuevoEstado : "No se pudo actualizar.");
                resp.getWriter().print(gson.toJson(respuesta));
                return;
            }

            // ── guardar: body JSON ─────────────────────────────────────────
            if ("guardar".equals(accion)) {
                StringBuilder sb = new StringBuilder();
                try (BufferedReader reader = req.getReader()) {
                    String line;
                    while ((line = reader.readLine()) != null) sb.append(line);
                }
                String bodyRaw = sb.toString().trim();

                System.out.println("[NominaServlet] body=" + bodyRaw);

                if (bodyRaw.isEmpty()) {
                    respuesta.put("success", false);
                    respuesta.put("message", "Body vacío.");
                    resp.getWriter().print(gson.toJson(respuesta));
                    return;
                }

                @SuppressWarnings("unchecked")
                Map<String, Object> json = gson.fromJson(bodyRaw, Map.class);

                Nomina n = new Nomina();
                n.setIdEmpleado(getString(json, "idEmpleado"));
                n.setNombreEmpleado(getString(json, "nombreEmpleado"));
                n.setNumeroEmpleado(getString(json, "numeroEmpleado"));
                n.setFechaInicio(getString(json, "fechaInicio"));
                n.setFechaFin(getString(json, "fechaFin"));
                n.setEjercicio(getInt(json, "ejercicio"));
                n.setSueldoBase(getDouble(json, "sueldoBase"));
                n.setBonos(getDouble(json, "bonos"));
                n.setDeducciones(getDouble(json, "deducciones"));
                n.setTotalNeto(getDouble(json, "totalNeto"));
                n.setEstado("Pendiente");

                boolean ok = dao.guardar(n);
                respuesta.put("success", ok);
                respuesta.put("message", ok ? "Nómina guardada exitosamente." : "Error al guardar nómina.");
                if (ok) respuesta.put("id", n.getId());
                resp.getWriter().print(gson.toJson(respuesta));
                return;
            }

            respuesta.put("success", false);
            respuesta.put("message", "Acción no reconocida: " + accion);

        } catch (Exception e) {
            respuesta.put("success", false);
            respuesta.put("message", "Error: " + e.getMessage());
            e.printStackTrace();
        }

        resp.getWriter().print(gson.toJson(respuesta));
    }

    // ── Helpers de extracción desde Map ───────────────────────────────────
    private String getString(Map<String, Object> m, String k) {
        Object v = m.get(k); return v != null ? v.toString() : null;
    }
    private int getInt(Map<String, Object> m, String k) {
        Object v = m.get(k); if (v == null) return 0;
        if (v instanceof Number) return ((Number) v).intValue();
        try { return Integer.parseInt(v.toString()); } catch (Exception e) { return 0; }
    }
    private double getDouble(Map<String, Object> m, String k) {
        Object v = m.get(k); if (v == null) return 0.0;
        if (v instanceof Number) return ((Number) v).doubleValue();
        try { return Double.parseDouble(v.toString()); } catch (Exception e) { return 0.0; }
    }
}