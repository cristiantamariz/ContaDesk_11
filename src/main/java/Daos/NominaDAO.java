package Daos;

import Beans.Nomina;
import Conexion.ConexionMongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NominaDAO {

    private final MongoCollection<Document> colNomina;
    private final MongoCollection<Document> colCotizaciones;
    private final MongoCollection<Document> colEmpleados;

    private static final double PORCENTAJE_BONO = 0.03;

    public NominaDAO() {
        this.colNomina       = ConexionMongo.getDatabase().getCollection("nomina");
        this.colCotizaciones = ConexionMongo.getDatabase().getCollection("cotizaciones");
        this.colEmpleados    = ConexionMongo.getDatabase().getCollection("empleados");
    }

    // ── 1. LISTAR TODOS — enriquece con datos del empleado ───────────────
    public List<Nomina> listarTodos() {
        List<Nomina> lista = new ArrayList<>();
        try {
            // Primero cargamos todos los empleados en un Map para evitar N queries
            Map<String, Document> mapaEmpleados = new HashMap<>();
            for (Document empDoc : colEmpleados.find()) {
                // Si tu empleado usa "usuario_id" para el 101, 102, pon ese nombre de campo aquí.
                // Si usa "id_empleado", pon "id_empleado".
                Object idNumerico = empDoc.get("usuario_id");
                if (idNumerico != null) {
                    mapaEmpleados.put(String.valueOf(idNumerico), empDoc);
                }
            }

            for (Document doc : colNomina.find()
                    .sort(new Document("periodo.ejercicio", -1)
                            .append("periodo.fecha_inicio", -1))) {

                Nomina n = docToNomina(doc);

                // Si nombre_empleado está vacío, lo tomamos del Map
                if (n.getNombreEmpleado() == null || n.getNombreEmpleado().isEmpty()) {
                    String idEmp = n.getIdEmpleado();
                    if (idEmp != null && !idEmp.isEmpty()) {
                        Document empDoc = mapaEmpleados.get(idEmp);
                        if (empDoc != null) {
                            String nombre = empDoc.getString("nombre") + " "
                                    + empDoc.getString("apellido_paterno");
                            n.setNombreEmpleado(nombre.trim());
                            n.setNumeroEmpleado(empDoc.getString("numero_empleado"));
                        }
                    }
                }

                lista.add(n);
            }
        } catch (Exception e) {
            System.err.println("[NominaDAO] Error al listar: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // ── 2. CALCULAR (preview sin guardar) ────────────────────────────────
    public Nomina calcular(String idEmpleado, int mes, int anio, double deducciones) {
        Nomina n = new Nomina();
        try {
            // a) Datos del empleado
            Document empDoc = colEmpleados.find(
                    Filters.eq("_id", new ObjectId(idEmpleado))
            ).first();

            if (empDoc == null) {
                System.err.println("[NominaDAO] Empleado no encontrado: " + idEmpleado);
                return null;
            }

            String nombre   = (empDoc.getString("nombre") + " "
                    + empDoc.getString("apellido_paterno")).trim();
            String numEmp   = empDoc.getString("numero_empleado");
            double sueldoBase = 0.0;

            Document puesto = (Document) empDoc.get("puesto");
            if (puesto != null) {
                Number sal = (Number) puesto.get("salario_minimo");
                if (sal != null) sueldoBase = sal.doubleValue();
            }

            // b) Fechas del periodo
            String mesStr   = String.format("%02d", mes);
            String fechaIni = anio + "-" + mesStr + "-01";
            int ultimoDia   = ultimoDiaDelMes(mes, anio);
            String fechaFin = anio + "-" + mesStr + "-" + String.format("%02d", ultimoDia);

            java.util.Calendar calIni = java.util.Calendar.getInstance();
            calIni.set(anio, mes - 1, 1, 0, 0, 0);
            calIni.set(java.util.Calendar.MILLISECOND, 0);
            Date dateIni = calIni.getTime();

            java.util.Calendar calFin = java.util.Calendar.getInstance();
            calFin.set(anio, mes - 1, ultimoDia, 23, 59, 59);
            calFin.set(java.util.Calendar.MILLISECOND, 999);
            Date dateFin = calFin.getTime();

            // c) Sumar cotizaciones Aprobadas del empleado en el periodo
            double sumaCotizaciones = 0.0;
            for (Document cotDoc : colCotizaciones.find(
                    Filters.and(
                            Filters.eq("idEmpleado", idEmpleado),
                            Filters.eq("estatus", "Aprobada"),
                            Filters.gte("createdAt", dateIni),
                            Filters.lte("createdAt", dateFin)
                    )
            )) {
                Number monto = (Number) cotDoc.get("montoTotal");
                if (monto != null) sumaCotizaciones += monto.doubleValue();
            }

            double bonos     = sumaCotizaciones * PORCENTAJE_BONO;
            double totalNeto = sueldoBase + bonos - deducciones;

            n.setIdEmpleado(idEmpleado);
            n.setNombreEmpleado(nombre);
            n.setNumeroEmpleado(numEmp);
            n.setFechaInicio(fechaIni);
            n.setFechaFin(fechaFin);
            n.setEjercicio(anio);
            n.setSueldoBase(sueldoBase);
            n.setBonos(bonos);
            n.setDeducciones(deducciones);
            n.setTotalNeto(totalNeto);
            n.setEstado("Pendiente");
            n.setCreatedAt(new Date());

            System.out.println("[NominaDAO] Calculado — " + nombre
                    + " | sueldo=" + sueldoBase + " | bonos=" + bonos
                    + " | deduc=" + deducciones + " | neto=" + totalNeto);

        } catch (Exception e) {
            System.err.println("[NominaDAO] Error al calcular: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        return n;
    }

    // ── 3. GUARDAR ────────────────────────────────────────────────────────
    public boolean guardar(Nomina n) {
        try {
            if (n.getCreatedAt() == null) n.setCreatedAt(new Date());
            if (n.getEstado() == null || n.getEstado().isEmpty()) n.setEstado("Pendiente");

            Document doc = nominaToDocument(n);
            System.out.println("[NominaDAO] Insertando: " + doc.toJson());
            colNomina.insertOne(doc);

            ObjectId idGen = doc.getObjectId("_id");
            if (idGen != null) n.setId(idGen.toHexString());
            return true;
        } catch (Exception e) {
            System.err.println("[NominaDAO] Error al guardar: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ── 4. ACTUALIZAR ESTADO ──────────────────────────────────────────────
    public boolean actualizarEstado(String id, String nuevoEstado) {
        try {
            if (id == null || id.isEmpty()) return false;
            var res = colNomina.updateOne(
                    Filters.eq("_id", new ObjectId(id)),
                    new Document("$set", new Document("estado", nuevoEstado))
            );
            return res.getModifiedCount() > 0;
        } catch (Exception e) {
            System.err.println("[NominaDAO] Error al actualizar estado: " + e.getMessage());
            return false;
        }
    }

    // ── 5. ELIMINAR ───────────────────────────────────────────────────────
    public boolean eliminar(String id) {
        try {
            if (id == null || id.isEmpty()) return false;
            var res = colNomina.deleteOne(Filters.eq("_id", new ObjectId(id)));
            return res.getDeletedCount() > 0;
        } catch (Exception e) {
            System.err.println("[NominaDAO] Error al eliminar: " + e.getMessage());
            return false;
        }
    }

    // ── MAPEO Document → Bean ─────────────────────────────────────────────
    private Nomina docToNomina(Document doc) {
        if (doc == null) return null;
        Nomina n = new Nomina();

        n.setId(doc.getObjectId("_id").toHexString());

        // id_empleado puede venir como String o como referencia
        Object idEmpObj = doc.get("id_empleado");
        if (idEmpObj != null) {
            n.setIdEmpleado(String.valueOf(idEmpObj)); // Convierte el 101 a "101"
        } else {
            n.setIdEmpleado("");
        }

        // Datos desnormalizados del empleado (pueden estar vacíos en docs viejos)
        n.setNombreEmpleado(doc.getString("nombre_empleado"));
        n.setNumeroEmpleado(doc.getString("numero_empleado"));

        // Periodo embebido
        Document periodo = (Document) doc.get("periodo");
        if (periodo != null) {
            n.setFechaInicio(periodo.getString("fecha_inicio"));
            n.setFechaFin(periodo.getString("fecha_fin"));
            Number ej = (Number) periodo.get("ejercicio");
            if (ej != null) n.setEjercicio(ej.intValue());
        }

        n.setSueldoBase(getDouble(doc, "sueldo_base"));
        n.setBonos(getDouble(doc, "bonos"));
        n.setDeducciones(getDouble(doc, "deducciones"));
        n.setTotalNeto(getDouble(doc, "total_neto"));
        n.setEstado(doc.getString("estado"));
        n.setCreatedAt(doc.getDate("created_at"));

        return n;
    }

    // ── MAPEO Bean → Document ─────────────────────────────────────────────
    private Document nominaToDocument(Nomina n) {
        Document doc = new Document();

        if (n.getId() != null && !n.getId().isEmpty()) {
            doc.append("_id", new ObjectId(n.getId()));
        }

        // Convertimos el String del Bean al int que exige el $jsonSchema
        int idEmpleadoInt = 0;
        if (n.getIdEmpleado() != null && !n.getIdEmpleado().isEmpty()) {
            try {
                idEmpleadoInt = Integer.parseInt(n.getIdEmpleado());
            } catch (NumberFormatException e) {
                System.err.println("[NominaDAO] Error parseando idEmpleado a int: " + e.getMessage());
            }
        }
        doc.append("id_empleado", idEmpleadoInt);
        doc.append("nombre_empleado", n.getNombreEmpleado() != null ? n.getNombreEmpleado() : "");
        doc.append("numero_empleado", n.getNumeroEmpleado() != null ? n.getNumeroEmpleado() : "");

        Document periodo = new Document()
                .append("fecha_inicio", n.getFechaInicio() != null ? n.getFechaInicio() : "")
                .append("fecha_fin",    n.getFechaFin()    != null ? n.getFechaFin()    : "")
                .append("ejercicio",    n.getEjercicio());

        doc.append("periodo",     periodo);
        doc.append("sueldo_base", n.getSueldoBase());
        doc.append("bonos",       n.getBonos());
        doc.append("deducciones", n.getDeducciones());
        doc.append("total_neto",  n.getTotalNeto());
        doc.append("estado",      n.getEstado() != null ? n.getEstado() : "Pendiente");
        doc.append("created_at",  n.getCreatedAt() != null ? n.getCreatedAt() : new Date());

        return doc;
    }

    private double getDouble(Document doc, String key) {
        Number val = (Number) doc.get(key);
        return val != null ? val.doubleValue() : 0.0;
    }

    private int ultimoDiaDelMes(int mes, int anio) {
        int[] dias = {31,28,31,30,31,30,31,31,30,31,30,31};
        if (mes == 2 && ((anio % 4 == 0 && anio % 100 != 0) || anio % 400 == 0)) return 29;
        return dias[mes - 1];
    }
}