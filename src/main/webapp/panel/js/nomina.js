// ══════════════════════════════════════════════════════════
//  NÓMINA — panel.js (sección a agregar)
//  ES5 puro: sin template literals, sin arrow functions,
//  sin const/let, sin forEach — compatible con JSP EL parser
// ══════════════════════════════════════════════════════════

var nominaData = [];
var nominaCalculada = null; // guarda el último cálculo para poder guardarlo

// ── Abrir modal ───────────────────────────────────────────
function abrirModalNomina() {
    // Poblar select de empleados con los que ya están en memoria
    var sel = document.getElementById('nom-empleado');
    if (sel) {
        var opts = '<option value="">Seleccione un empleado...</option>';
        for (var i = 0; i < empleadosData.length; i++) {
            var e = empleadosData[i];
            if (e.activo) {
                opts += '<option value="' + esc(e.id) + '">'
                      + esc(e.nombre) + ' ' + esc(e.apellidoPaterno)
                      + ' (' + esc(e.numeroEmpleado) + ')'
                      + '</option>';
            }
        }
        sel.innerHTML = opts;
    }

    // Periodo por defecto: mes actual
    var hoy   = new Date();
    var anio  = hoy.getFullYear();
    var mes   = String(hoy.getMonth() + 1);
    if (mes.length === 1) mes = '0' + mes;
    var periodoInput = document.getElementById('nom-periodo');
    if (periodoInput) periodoInput.value = anio + '-' + mes;

    document.getElementById('nom-deducciones').value = '0';

    // Ocultar resultado y botón guardar
    var res = document.getElementById('nom-resultado');
    if (res) res.style.display = 'none';

    var btnGuardar = document.getElementById('btn-guardar-nomina');
    if (btnGuardar) btnGuardar.style.display = 'none';

    var errDiv = document.getElementById('nom-error');
    if (errDiv) errDiv.style.display = 'none';

    nominaCalculada = null;

    document.getElementById('modal-nomina').style.display = 'flex';
}

function cerrarModalNomina() {
    document.getElementById('modal-nomina').style.display = 'none';
}

// ── Calcular nómina (llama al servlet GET ?accion=calcular) ───────────
function calcularNomina() {
    var idEmpleado  = document.getElementById('nom-empleado').value;
    var periodo     = document.getElementById('nom-periodo').value; // "2026-06"
    var deducciones = document.getElementById('nom-deducciones').value || '0';

    var errDiv = document.getElementById('nom-error');

    if (!idEmpleado) {
        errDiv.innerText = 'Debes seleccionar un empleado.';
        errDiv.style.display = 'block';
        return;
    }
    if (!periodo) {
        errDiv.innerText = 'Debes seleccionar un mes y año.';
        errDiv.style.display = 'block';
        return;
    }
    errDiv.style.display = 'none';

    var partes = periodo.split('-');  // ["2026", "06"]
    var anio   = partes[0];
    var mes    = partes[1];

    var url = CTX + '/NominaServlet?accion=calcular'
            + '&idEmpleado=' + encodeURIComponent(idEmpleado)
            + '&mes='        + encodeURIComponent(mes)
            + '&anio='       + encodeURIComponent(anio)
            + '&deducciones='+ encodeURIComponent(deducciones);

    fetch(url)
        .then(function(r) { return r.json(); })
        .then(function(res) {
            if (!res.success) {
                errDiv.innerText = res.message || 'Error al calcular.';
                errDiv.style.display = 'block';
                return;
            }

            var d = res.data;
            nominaCalculada = d;

            // Mostrar resultado
            document.getElementById('nom-res-sueldo').innerText = '$' + d.sueldoBase.toFixed(2);
            document.getElementById('nom-res-bonos').innerText  = '$' + d.bonos.toFixed(2);
            document.getElementById('nom-res-deduc').innerText  = '$' + d.deducciones.toFixed(2);
            document.getElementById('nom-res-total').innerText  = '$' + d.totalNeto.toFixed(2);
            document.getElementById('nom-res-periodo').innerText =
                'Periodo: ' + d.fechaInicio + ' al ' + d.fechaFin;

            document.getElementById('nom-resultado').style.display = 'block';

            // Llenar campos ocultos para el guardado
            document.getElementById('nom-h-idEmpleado').value     = d.idEmpleado || '';
            document.getElementById('nom-h-nombreEmpleado').value  = d.nombreEmpleado || '';
            document.getElementById('nom-h-numeroEmpleado').value  = d.numeroEmpleado || '';
            document.getElementById('nom-h-fechaInicio').value     = d.fechaInicio || '';
            document.getElementById('nom-h-fechaFin').value        = d.fechaFin || '';
            document.getElementById('nom-h-ejercicio').value       = d.ejercicio || '';
            document.getElementById('nom-h-sueldoBase').value      = d.sueldoBase || '0';
            document.getElementById('nom-h-bonos').value           = d.bonos || '0';
            document.getElementById('nom-h-totalNeto').value       = d.totalNeto || '0';

            // Mostrar botón guardar
            document.getElementById('btn-guardar-nomina').style.display = '';
        })
        .catch(function(e) {
            console.error('[Nómina] Error al calcular:', e);
            errDiv.innerText = 'Error de comunicación con el servidor.';
            errDiv.style.display = 'block';
        });
}

// ── Guardar nómina calculada ──────────────────────────────
function guardarNomina() {
    var errDiv = document.getElementById('nom-error');

    var payload = {
        idEmpleado:    document.getElementById('nom-h-idEmpleado').value,
        nombreEmpleado: document.getElementById('nom-h-nombreEmpleado').value,
        numeroEmpleado: document.getElementById('nom-h-numeroEmpleado').value,
        fechaInicio:   document.getElementById('nom-h-fechaInicio').value,
        fechaFin:      document.getElementById('nom-h-fechaFin').value,
        ejercicio:     parseInt(document.getElementById('nom-h-ejercicio').value) || 0,
        sueldoBase:    parseFloat(document.getElementById('nom-h-sueldoBase').value) || 0,
        bonos:         parseFloat(document.getElementById('nom-h-bonos').value) || 0,
        deducciones:   parseFloat(document.getElementById('nom-deducciones').value) || 0,
        totalNeto:     parseFloat(document.getElementById('nom-h-totalNeto').value) || 0,
        estado:        'Pendiente'
    };

    if (!payload.idEmpleado) {
        errDiv.innerText = 'Primero calcula la nómina antes de guardar.';
        errDiv.style.display = 'block';
        return;
    }

    fetch(CTX + '/NominaServlet?accion=guardar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json;charset=UTF-8' },
        body: JSON.stringify(payload)
    })
    .then(function(r) { return r.json(); })
    .then(function(res) {
        if (res.success) {
            cerrarModalNomina();
            cargarNomina();
        } else {
            errDiv.innerText = 'Error: ' + (res.message || 'No se pudo guardar.');
            errDiv.style.display = 'block';
        }
    })
    .catch(function(e) {
        console.error('[Nómina] Error al guardar:', e);
        errDiv.innerText = 'Error de comunicación con el servidor.';
        errDiv.style.display = 'block';
    });
}

// ── Cargar historial ──────────────────────────────────────
function cargarNomina() {
    fetch(CTX + '/NominaServlet?accion=listar')
        .then(function(r) { return r.json(); })
        .then(function(data) { nominaData = data; renderTablaNomina(data); })
        .catch(function(e) { console.error('[Nómina] Error al cargar:', e); });
}

// ── Renderizar tabla ──────────────────────────────────────
function renderTablaNomina(lista) {
    var tbody = document.getElementById('nomina-tbody');
    if (!tbody) return;

    if (!lista || lista.length === 0) {
        tbody.innerHTML = '<tr><td colspan="8" class="empty">Sin registros de nómina.</td></tr>';
        return;
    }

    var html = '';
    for (var i = 0; i < lista.length; i++) {
        var n = lista[i];

        var badgeClass = 'badge-yellow';
        if (n.estado === 'Pagado')    badgeClass = 'badge-green';
        if (n.estado === 'Cancelado') badgeClass = 'badge-red';

        var periodo = (n.fechaInicio || '') + ' — ' + (n.fechaFin || '');

        // Botón "Marcar Pagado" solo si está Pendiente
        var btnPagar = '';
        if (n.estado === 'Pendiente') {
            btnPagar = '<button class="action-btn" onclick="pagarNomina(\'' + esc(n.id) + '\')">Pagar</button>';
        }

        html += '<tr>'
            + '<td>'
                + '<div class="user-cell">'
                    + '<div class="user-avatar-sm">'
                        + (n.nombreEmpleado ? n.nombreEmpleado.substring(0, 2).toUpperCase() : 'EM')
                    + '</div>'
                    + '<div>'
                        + '<strong>' + esc(n.nombreEmpleado || '') + '</strong>'
                        + '<br><small class="col-meta">' + esc(n.numeroEmpleado || '') + '</small>'
                    + '</div>'
                + '</div>'
            + '</td>'
            + '<td>' + esc(periodo) + '</td>'
            + '<td>$' + (n.sueldoBase || 0).toFixed(2) + '</td>'
            + '<td style="color:#16a34a;font-weight:600">$' + (n.bonos || 0).toFixed(2) + '</td>'
            + '<td style="color:#dc2626">$' + (n.deducciones || 0).toFixed(2) + '</td>'
            + '<td><strong>$' + (n.totalNeto || 0).toFixed(2) + '</strong></td>'
            + '<td><span class="badge ' + badgeClass + '">' + esc(n.estado || '') + '</span></td>'
            + '<td class="col-actions">'
                + btnPagar
                + '<button class="action-btn action-btn-danger" onclick="eliminarNomina(\'' + esc(n.id) + '\')">Eliminar</button>'
            + '</td>'
            + '</tr>';
    }
    tbody.innerHTML = html;

    // Conectar búsqueda
    var searchInput = document.getElementById('nomina-search');
    if (searchInput) {
        searchInput.oninput = function() {
            var q = this.value.toLowerCase();
            var filas = document.querySelectorAll('#nomina-tbody tr');
            for (var j = 0; j < filas.length; j++) {
                var texto = filas[j].textContent.toLowerCase();
                filas[j].style.display = texto.indexOf(q) !== -1 ? '' : 'none';
            }
        };
    }
}

// ── Marcar como Pagado ────────────────────────────────────
function pagarNomina(id) {
    if (!confirm('¿Confirmar pago de esta nómina?')) return;
    fetch(CTX + '/NominaServlet?accion=actualizarEstado&id=' + id + '&estado=Pagado', {
        method: 'POST'
    })
    .then(function(r) { return r.json(); })
    .then(function(res) {
        if (res.success) cargarNomina();
        else alert('Error: ' + res.message);
    })
    .catch(function(e) { console.error('[Nómina] Error al pagar:', e); });
}

// ── Eliminar ──────────────────────────────────────────────
function eliminarNomina(id) {
    if (!confirm('¿Eliminar este registro de nómina permanentemente?')) return;
    fetch(CTX + '/NominaServlet?accion=eliminar&id=' + id, { method: 'POST' })
    .then(function(r) { return r.json(); })
    .then(function(res) {
        if (res.success) cargarNomina();
        else alert('Error: ' + res.message);
    })
    .catch(function(e) { console.error('[Nómina] Error al eliminar:', e); });
}