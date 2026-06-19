package Servlets;

import Beans.Cotizacion;
import Daos.CotizacionDAO;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/CotizacionServlet", "/CotizacionesServlet"})
public class CotizacionServlet extends HttpServlet {

    private final CotizacionDAO dao = new CotizacionDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");

        String action = req.getParameter("accion");
        if (action == null) action = req.getParameter("action");

        if ("listar".equals(action) || "list".equals(action)) {
            resp.getWriter().print(gson.toJson(dao.listarTodos()));
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Map<String, Object> err = new HashMap<>();
            err.put("success", false);
            err.put("message", "Acción no reconocida: " + action);
            resp.getWriter().print(gson.toJson(err));
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
            if ("eliminar".equals(action) || "delete".equals(action)) {
                String id = req.getParameter("id");
                boolean ok = dao.eliminar(id);
                respuesta.put("success", ok);
                respuesta.put("message", ok ? "Cotización eliminada correctamente." : "No se pudo eliminar.");

            } else {
                // ── Leer el body completo como String primero ──────────────────
                StringBuilder sb = new StringBuilder();
                try (BufferedReader reader = req.getReader()) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                }
                String bodyRaw = sb.toString().trim();

                // Log de diagnóstico (puedes comentar estas líneas después)
                System.out.println("[CotizacionServlet] accion=" + action);
                System.out.println("[CotizacionServlet] body=" + bodyRaw);

                if (bodyRaw.isEmpty()) {
                    respuesta.put("success", false);
                    respuesta.put("message", "El cuerpo de la solicitud está vacío.");
                    resp.getWriter().print(gson.toJson(respuesta));
                    return;
                }

                // ── Deserializar el JSON a un CotizacionDTO temporal ───────────
                // Usamos un Map para ver exactamente qué campos llegaron
                @SuppressWarnings("unchecked")
                Map<String, Object> jsonMap = gson.fromJson(bodyRaw, Map.class);

                System.out.println("[CotizacionServlet] campos recibidos: " + jsonMap.keySet());

                // ── Construir el Bean manualmente desde el Map ─────────────────
                // Esto evita problemas de mismatch de nombres con Gson
                Cotizacion c = new Cotizacion();

                c.setIdCliente(getString(jsonMap, "idCliente"));
                c.setNombreCliente(getString(jsonMap, "nombreCliente"));
                c.setRegimen(getString(jsonMap, "regimen"));
                c.setIdEmpleado(getString(jsonMap, "idEmpleado"));
                c.setNombreEmpleado(getString(jsonMap, "nombreEmpleado"));
                c.setEstatus(getString(jsonMap, "estatus"));

                // ID solo para actualizaciones
                String id = getString(jsonMap, "id");
                if (id != null && !id.isEmpty()) c.setId(id);

                // Números — Gson los deserializa como Double por defecto
                c.setVolumenFacturas(getInt(jsonMap, "volumenFacturas"));
                c.setHorasEstimadas(getInt(jsonMap, "horasEstimadas"));
                c.setTarifaBase(getDouble(jsonMap, "tarifaBase"));
                c.setMontoTotal(getDouble(jsonMap, "montoTotal"));

                System.out.println("[CotizacionServlet] Bean construido — idCliente=" + c.getIdCliente()
                        + ", montoTotal=" + c.getMontoTotal()
                        + ", tarifaBase=" + c.getTarifaBase()
                        + ", volumen=" + c.getVolumenFacturas()
                        + ", horas=" + c.getHorasEstimadas());

                // ── Ejecutar la acción ─────────────────────────────────────────
                if ("crear".equals(action) || "create".equals(action)) {
                    boolean ok = dao.crear(c);
                    respuesta.put("success", ok);
                    respuesta.put("message", ok ? "Cotización guardada exitosamente." : "Error al guardar cotización.");

                } else if ("actualizar".equals(action) || "update".equals(action)) {
                    boolean ok = dao.actualizar(c);
                    respuesta.put("success", ok);
                    respuesta.put("message", ok ? "Cotización actualizada." : "No se pudo actualizar.");

                } else {
                    respuesta.put("success", false);
                    respuesta.put("message", "Acción no reconocida: " + action);
                }
            }

        } catch (Exception e) {
            respuesta.put("success", false);
            respuesta.put("message", "Error procesando la solicitud: " + e.getMessage());
            e.printStackTrace();
        }

        resp.getWriter().print(gson.toJson(respuesta));
    }

    // ── Helpers de extracción segura desde Map ─────────────────────────────

    private String getString(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val == null) return null;
        return val.toString();
    }

    private int getInt(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val == null) return 0;
        // Gson deserializa números como Double; hay que convertir
        if (val instanceof Number) return ((Number) val).intValue();
        try { return Integer.parseInt(val.toString()); } catch (Exception e) { return 0; }
    }

    private double getDouble(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val == null) return 0.0;
        if (val instanceof Number) return ((Number) val).doubleValue();
        try { return Double.parseDouble(val.toString()); } catch (Exception e) { return 0.0; }
    }
}