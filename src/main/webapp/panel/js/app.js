// ── Configuración global ─────────────────────────────────
const ROL_LABEL = { 1: 'Administrador', 2: 'Contador', 3: 'Capturista' };

const labels = {
    dashboard:     { title: 'Inicio',                  accion: '+ Nueva cotización'  },
    cotizaciones:  { title: 'Cotizador de Servicios',  accion: '+ Nueva cotización'  },
    clientes:      { title: 'Directorio de Clientes',  accion: '+ Nuevo cliente'     },
    facturas:      { title: 'Facturas',                accion: '+ Nueva factura'     },
    usuarios:      { title: 'Usuarios del sistema',    accion: '+ Nuevo usuario'     },
    empleados:     { title: 'Empleados',               accion: '+ Nuevo empleado'    },
    nomina:        { title: 'Nómina',                  accion: '+ Nuevo periodo'     },
    polizas:       { title: 'Pólizas',                 accion: '+ Nueva póliza'      },
    declaraciones: { title: 'Declaraciones fiscales',  accion: '+ Nueva declaración' },
    cuentas:       { title: 'Catálogo de cuentas',     accion: '+ Nueva cuenta'      },
    config:        { title: 'Configuración',           accion: null                  }
};

var currentSection    = 'dashboard';
var usuariosData      = [];
var empleadosData     = [];
var clientesData      = [];
var cotizacionesData  = [];

// Helper para escapar HTML y evitar XSS
function esc(s) {
    if (s === null || s === undefined) return '';
    return String(s)
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#39;');
}

// ── Navegación SPA ───────────────────────────────────────
function loadSection(id, el) {
    try {
        var currentTarget = document.getElementById('section-' + currentSection);
        if (currentTarget) currentTarget.style.display = 'none';

        document.querySelectorAll('.nav-item').forEach(function(n) { n.classList.remove('active'); });

        var target = document.getElementById('section-' + id);
        if (target) target.style.display = 'block';
        if (el) el.classList.add('active');

        var cfg = labels[id] || {};
        document.getElementById('breadcrumb').textContent = cfg.title || id;

        var btn = document.getElementById('btn-accion');
        if (cfg.accion) {
            btn.textContent = cfg.accion;
            btn.style.display = '';
        } else {
            btn.style.display = 'none';
        }

        currentSection = id;

        if (id === 'usuarios'  && typeof cargarUsuarios   === 'function') cargarUsuarios();
        if (id === 'empleados' && typeof cargarEmpleados  === 'function') cargarEmpleados();
        if (id === 'clientes'  && typeof cargarClientes   === 'function') cargarClientes();
        if (id === 'nomina'    && typeof cargarNomina     === 'function') {
            cargarEmpleados();
            cargarNomina();
        }

        if (id === 'dashboard' || id === 'cotizaciones') {
            if (typeof cargarClientes     === 'function') cargarClientes();
            if (typeof cargarEmpleados    === 'function') cargarEmpleados();
            if (typeof cargarCotizaciones === 'function') cargarCotizaciones();
        }
    } catch (error) {
        console.error("Error al cargar la sección:", error);
    }

    return false;
}

function accionPrincipal() {
    if (currentSection === 'usuarios')                                    { abrirModalNuevo();          return; }
    if (currentSection === 'empleados')                                   { abrirModalEmpleadoNuevo();  return; }
    if (currentSection === 'clientes')                                    { abrirModalClienteNuevo();   return; }
    if (currentSection === 'dashboard' || currentSection === 'cotizaciones') { abrirModalCotizador();   return; }

    var cfg = labels[currentSection];
    if (cfg && cfg.accion) alert('Acción aún no implementada para: ' + cfg.accion);
}

// ══════════════════════════════════════════════════════════
//  COTIZADOR DE SERVICIOS
// ══════════════════════════════════════════════════════════

function abrirModalCotizador() {
    document.getElementById('cot-id').value = '';
    document.getElementById('modal-cotizador-title').innerText = 'Generar Cotización';

    var selectCliente = document.getElementById('cot-cliente');
    if (selectCliente) {
        var optsCliente = '<option value="">Seleccione un cliente / prospecto...</option>';
        for (var i = 0; i < clientesData.length; i++) {
            var c = clientesData[i];
            if (c.activo) {
                optsCliente += '<option value="' + esc(c.idCliente) + '" data-regimen="' + esc(c.regimenFiscal) + '">' + esc(c.nombre) + '</option>';
            }
        }
        selectCliente.innerHTML = optsCliente;
    }

    var selectEmpleado = document.getElementById('cot-empleado');
    if (selectEmpleado) {
        var optsEmp = '<option value="">Asignar contador...</option>';
        for (var j = 0; j < empleadosData.length; j++) {
            var e = empleadosData[j];
            var puestoNombre = (e.puesto && e.puesto.nombre) ? e.puesto.nombre.toLowerCase() : '';
            if (e.activo && (puestoNombre.indexOf('contador') !== -1 || puestoNombre.indexOf('auxiliar') !== -1)) {
                var salario = (e.puesto && e.puesto.salarioMinimo) ? e.puesto.salarioMinimo : 0;
                optsEmp += '<option value="' + esc(e.id) + '" data-tarifa="' + salario + '">' + esc(e.nombre) + ' ' + esc(e.apellidoPaterno) + '</option>';
            }
        }
        selectEmpleado.innerHTML = optsEmp;
    }

    document.getElementById('cot-volumen').value = '0';
    document.getElementById('cot-horas').value   = '0';
    document.getElementById('cot-total').innerText = '$0.00';

    var errorDiv = document.getElementById('cot-error');
    if (errorDiv) errorDiv.style.display = 'none';

    var modal = document.getElementById('modal-cotizador');
    if (modal) modal.style.display = 'flex';
}

function calcularCotizacion() {
    var clienteSelect  = document.getElementById('cot-cliente');
    var empleadoSelect = document.getElementById('cot-empleado');
    var volumenFacturas = parseInt(document.getElementById('cot-volumen').value) || 0;
    var horasEstimadas  = parseInt(document.getElementById('cot-horas').value)   || 0;

    if (!clienteSelect || clienteSelect.selectedIndex === 0 || !empleadoSelect || empleadoSelect.selectedIndex === 0) {
        document.getElementById('cot-total').innerText = '$0.00';
        return;
    }

    var regimen    = clienteSelect.options[clienteSelect.selectedIndex].getAttribute('data-regimen') || '';
    var tarifaBase = 500;
    if (regimen.indexOf('Moral')  !== -1) tarifaBase = 1500;
    else if (regimen.indexOf('RESICO') !== -1) tarifaBase = 800;

    var costoPorFactura    = 2.50;
    var salarioMensual     = parseFloat(empleadoSelect.options[empleadoSelect.selectedIndex].getAttribute('data-tarifa')) || 8000;
    var tarifaHoraEmpleado = salarioMensual / 160;

    var total = tarifaBase + (volumenFacturas * costoPorFactura) + (horasEstimadas * tarifaHoraEmpleado);

    document.getElementById('cot-total').innerText = '$' + total.toFixed(2);
}

function cerrarModalCotizador() {
    var modal = document.getElementById('modal-cotizador');
    if (modal) modal.style.display = 'none';
}

// ── Guardar Cotización ──
function guardarCotizacion() {
    var clienteSelect  = document.getElementById('cot-cliente');
    var empleadoSelect = document.getElementById('cot-empleado');

    if (clienteSelect.selectedIndex === 0 || empleadoSelect.selectedIndex === 0) {
        alert("Debes seleccionar un cliente y asignar un contador.");
        return;
    }

    var idCotizacion = document.getElementById('cot-id').value;
    var totalTexto   = document.getElementById('cot-total').innerText.replace('$', '').replace(/,/g, '');

    var clienteOpt = clienteSelect.options[clienteSelect.selectedIndex];
    var regimen    = clienteOpt.getAttribute('data-regimen') || '';
    var tarifaBase = 500;
    if (regimen.indexOf('Moral')  !== -1) tarifaBase = 1500;
    else if (regimen.indexOf('RESICO') !== -1) tarifaBase = 800;

    var payload = {
        id:             idCotizacion || null,
        idCliente:      clienteSelect.value,
        nombreCliente:  clienteOpt.text,
        regimen:        regimen,
        idEmpleado:     empleadoSelect.value,
        nombreEmpleado: empleadoSelect.options[empleadoSelect.selectedIndex].text,
        volumenFacturas: parseInt(document.getElementById('cot-volumen').value) || 0,
        horasEstimadas:  parseInt(document.getElementById('cot-horas').value)   || 0,
        tarifaBase:      tarifaBase,
        montoTotal:      parseFloat(totalTexto) || 0.0,
        estatus:        'Pendiente'
    };

    var accion = idCotizacion ? 'actualizar' : 'crear';

    fetch(CTX + '/CotizacionServlet?accion=' + accion, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json;charset=UTF-8' },
        body: JSON.stringify(payload)
    }).then(function(r) { return r.json(); }).then(function(res) {
        if (res.success) {
            cerrarModalCotizador();
            cargarCotizaciones();
        } else {
            alert('Error: ' + res.message);
        }
    }).catch(function(e) {
        console.error("Error guardando cotización:", e);
        alert('Error de comunicación con el servidor.');
    });
}

// ── Cargar y Renderizar Historial ──
function cargarCotizaciones() {
    fetch(CTX + '/CotizacionServlet?accion=listar')
        .then(function(r) { return r.json(); })
        .then(function(data) { cotizacionesData = data; renderTablaCotizaciones(data); renderDashboard(); })
        .catch(function(e) { console.error("Error cargando cotizaciones:", e); });
}

function renderTablaCotizaciones(lista) {
    var tbody = document.getElementById('cotizaciones-tbody');
    if (!tbody) return;

    if (!lista || lista.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" class="empty">No hay cotizaciones generadas.</td></tr>';
        return;
    }

    var html = '';
    for (var i = 0; i < lista.length; i++) {
        var c = lista[i];
        var badgeColor = 'badge-yellow';
        if (c.estatus === 'Aprobada')  badgeColor = 'badge-green';
        else if (c.estatus === 'Rechazada') badgeColor = 'badge-red';

        var monto = (c.montoTotal || 0).toFixed(2);

        html += '<tr>' +
            '<td><strong>' + esc(c.nombreCliente) + '</strong></td>' +
            '<td><span class="badge badge-gray">' + esc(c.regimen || 'N/A') + '</span></td>' +
            '<td class="col-meta">' + esc(c.nombreEmpleado) + '</td>' +
            '<td><strong>$' + monto + '</strong></td>' +
            '<td><span class="badge ' + badgeColor + '">' + esc(c.estatus) + '</span></td>' +
            '<td class="col-actions">' +
                '<button class="action-btn action-btn-danger" onclick="eliminarCotizacion(\'' + esc(c.id) + '\')">Eliminar</button>' +
            '</td>' +
        '</tr>';
    }
    tbody.innerHTML = html;
}

function eliminarCotizacion(id) {
    if (!confirm("¿Eliminar esta cotización permanentemente?")) return;
    fetch(CTX + '/CotizacionServlet?accion=eliminar&id=' + id, { method: 'POST' })
        .then(function(r) { return r.json(); })
        .then(function(res) { if (res.success) cargarCotizaciones(); else alert(res.message); })
        .catch(function(e) { console.error("Error eliminando cotización:", e); });
}

// ══════════════════════════════════════════════════════════
//  EMPLEADOS (MongoDB)
// ══════════════════════════════════════════════════════════

function cargarEmpleados() {
    fetch(CTX + '/EmpleadoServlet?accion=listar')
        .then(function(r) { return r.json(); })
        .then(function(data) { empleadosData = data; renderTablaEmpleados(data); })
        .catch(function(e) { console.error("Error cargando empleados", e); });
}

function renderTablaEmpleados(lista) {
    var tbody = document.getElementById('empleados-tbody');
    if (!tbody) return;

    if (!lista || lista.length === 0) {
        tbody.innerHTML = '<tr><td colspan="7" class="empty">Sin empleados registrados.</td></tr>';
        return;
    }

    var html = '';
    for (var i = 0; i < lista.length; i++) {
        var e = lista[i];
        var iniciales   = (e.nombre || 'E').substring(0, 2).toUpperCase();
        var deptNombre  = (e.departamento && e.departamento.nombre) ? e.departamento.nombre : '—';
        var puestoNombre = (e.puesto && e.puesto.nombre)  ? e.puesto.nombre  : '—';
        var salario     = (e.puesto && e.puesto.salarioMinimo) ? e.puesto.salarioMinimo.toFixed(2) : '0.00';
        var badgeClass  = e.activo ? 'badge-green' : 'badge-red';
        var badgeText   = e.activo ? 'Activo' : 'Baja';

        html += '<tr>' +
            '<td><strong>' + esc(e.numeroEmpleado) + '</strong></td>' +
            '<td><div class="user-cell"><div class="user-avatar-sm">' + iniciales + '</div><div>' + esc(e.nombre) + ' ' + esc(e.apellidoPaterno) + '</div></div></td>' +
            '<td>' + esc(deptNombre) + '</td>' +
            '<td>' + esc(puestoNombre) + '</td>' +
            '<td>$' + salario + '</td>' +
            '<td><span class="badge ' + badgeClass + '">' + badgeText + '</span></td>' +
            '<td class="col-actions">' +
                '<button class="action-btn" onclick="abrirModalEmpleadoEditar(\'' + esc(e.id) + '\')">Editar</button>' +
                '<button class="action-btn action-btn-danger" onclick="eliminarEmpleado(\'' + esc(e.id) + '\')">Borrar</button>' +
            '</td>' +
        '</tr>';
    }
    tbody.innerHTML = html;
}

function filtrarEmpleados(valor) {
    if (!valor || valor.trim() === '') {
        renderTablaEmpleados(empleadosData);
        return;
    }
    var v = valor.toLowerCase();
    var filtrados = [];
    for (var i = 0; i < empleadosData.length; i++) {
        var e = empleadosData[i];
        var nombre = ((e.nombre || '') + ' ' + (e.apellidoPaterno || '')).toLowerCase();
        var depto  = (e.departamento && e.departamento.nombre) ? e.departamento.nombre.toLowerCase() : '';
        var puesto = (e.puesto && e.puesto.nombre)  ? e.puesto.nombre.toLowerCase()  : '';
        var num    = (e.numeroEmpleado || '').toLowerCase();
        if (nombre.indexOf(v) !== -1 || depto.indexOf(v) !== -1 || puesto.indexOf(v) !== -1 || num.indexOf(v) !== -1) {
            filtrados.push(e);
        }
    }
    renderTablaEmpleados(filtrados);
}

function abrirModalEmpleadoNuevo() {
    document.getElementById('emp-id').value = '';
    var campos = ['fecha', 'nombre', 'paterno', 'depto', 'puesto', 'salario'];
    for (var i = 0; i < campos.length; i++) {
        document.getElementById('emp-' + campos[i]).value = '';
    }
    // El número se genera automáticamente en el backend
    var numInput = document.getElementById('emp-numero');
    numInput.value = '';
    numInput.placeholder = 'Se asignará automáticamente';
    numInput.readOnly = true;
    numInput.style.background = 'var(--bg-secondary, #f5f5f5)';
    numInput.style.color = 'var(--text-muted, #888)';

    document.getElementById('emp-activo').value = 'true';
    document.getElementById('modal-empleado-title').innerText = 'Nuevo Empleado';
    document.getElementById('modal-empleado').style.display = 'flex';
}

function abrirModalEmpleadoEditar(id) {
    var emp = null;
    for (var i = 0; i < empleadosData.length; i++) {
        if (empleadosData[i].id === id) { emp = empleadosData[i]; break; }
    }
    if (!emp) return;

    document.getElementById('emp-id').value     = emp.id;
    // Al editar, el número es visible pero no editable (se preserva el asignado)
    var numInput = document.getElementById('emp-numero');
    numInput.value = emp.numeroEmpleado || '';
    numInput.readOnly = true;
    numInput.style.background = 'var(--bg-secondary, #f5f5f5)';
    numInput.style.color = 'var(--text-muted, #888)';
    document.getElementById('emp-fecha').value   = emp.fechaContratacion  || '';
    document.getElementById('emp-nombre').value  = emp.nombre             || '';
    document.getElementById('emp-paterno').value = emp.apellidoPaterno    || '';
    document.getElementById('emp-depto').value   = (emp.departamento && emp.departamento.nombre) ? emp.departamento.nombre : '';
    document.getElementById('emp-puesto').value  = (emp.puesto && emp.puesto.nombre) ? emp.puesto.nombre : '';
    document.getElementById('emp-salario').value = (emp.puesto && emp.puesto.salarioMinimo) ? emp.puesto.salarioMinimo : '';
    document.getElementById('emp-activo').value  = emp.activo ? 'true' : 'false';

    document.getElementById('modal-empleado-title').innerText = 'Editar Empleado';
    document.getElementById('modal-empleado').style.display = 'flex';
}

function cerrarModalEmpleado() { document.getElementById('modal-empleado').style.display = 'none'; }

function guardarEmpleado() {
    var id = document.getElementById('emp-id').value;

    // Al crear (id vacío) no se envía numeroEmpleado — el DAO lo genera automáticamente.
    // Al actualizar se envía para que el replaceOne conserve el número ya asignado.
    var numeroEmpleado = id ? document.getElementById('emp-numero').value : null;

    var payload = {
        id:              id || null,
        numeroEmpleado:  numeroEmpleado,
        fechaContratacion: document.getElementById('emp-fecha').value,
        nombre:          document.getElementById('emp-nombre').value,
        apellidoPaterno: document.getElementById('emp-paterno').value,
        activo:          document.getElementById('emp-activo').value === 'true',
        departamento: { nombre: document.getElementById('emp-depto').value  },
        puesto: {
            nombre:        document.getElementById('emp-puesto').value,
            salarioMinimo: parseFloat(document.getElementById('emp-salario').value) || 0
        }
    };

    var accion = id ? 'actualizar' : 'crear';

    fetch(CTX + '/EmpleadoServlet?accion=' + accion, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json;charset=UTF-8' },
        body: JSON.stringify(payload)
    }).then(function(r) { return r.json(); }).then(function(res) {
        if (res.success) { cerrarModalEmpleado(); cargarEmpleados(); }
        else { alert(res.message); }
    }).catch(function(e) {
        console.error("Error guardando empleado:", e);
        alert('Error de comunicación con el servidor.');
    });
}

function eliminarEmpleado(id) {
    if (!confirm("¿Seguro que deseas eliminar este empleado?")) return;
    fetch(CTX + '/EmpleadoServlet?accion=eliminar&id=' + id, { method: 'POST' })
        .then(function(r) { return r.json(); })
        .then(function(res) { if (res.success) cargarEmpleados(); else alert(res.message); })
        .catch(function(e) { console.error("Error eliminando empleado:", e); });
}

// ══════════════════════════════════════════════════════════
//  CLIENTES (MongoDB)
// ══════════════════════════════════════════════════════════

function cargarClientes() {
    fetch(CTX + '/ClienteServlet?accion=listar')
        .then(function(r) { return r.json(); })
        // FIX: Se eliminó la llamada recursiva a cargarClientes() que causaba un bucle infinito
        .then(function(data) { clientesData = data; renderTablaClientes(data); })
        .catch(function(e) { console.error("Error cargando clientes", e); });
}

function renderTablaClientes(lista) {
    var tbody = document.getElementById('clientes-tbody');
    if (!tbody) return;

    if (!lista || lista.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" class="empty">Sin clientes registrados.</td></tr>';
        return;
    }

    var html = '';
    for (var i = 0; i < lista.length; i++) {
        var c = lista[i];
        var badgeClass = c.activo ? 'badge-green' : 'badge-red';
        var badgeText  = c.activo ? 'Activo' : 'Inactivo';

        html += '<tr>' +
            '<td><strong>' + esc(c.nombre) + '</strong><br><small class="col-meta">' + esc(c.razonSocial || '') + '</small></td>' +
            '<td>' + esc(c.rfc) + '</td>' +
            '<td class="email-cell">' + esc(c.email || '') + '</td>' +
            '<td>' + esc(c.telefono || '') + '</td>' +
            '<td><span class="badge ' + badgeClass + '">' + badgeText + '</span></td>' +
            '<td class="col-actions">' +
                '<button class="action-btn" onclick="abrirModalClienteEditar(\'' + esc(c.idCliente) + '\')">Editar</button>' +
                '<button class="action-btn action-btn-danger" onclick="eliminarCliente(\'' + esc(c.idCliente) + '\')">Borrar</button>' +
            '</td>' +
        '</tr>';
    }
    tbody.innerHTML = html;
}

function abrirModalClienteNuevo() {
    document.getElementById('cli-id').value = '';
    var campos = ['nombre', 'razon', 'rfc', 'telefono', 'email', 'direccion', 'cp', 'regimen'];
    for (var i = 0; i < campos.length; i++) {
        document.getElementById('cli-' + campos[i]).value = '';
    }
    document.getElementById('modal-cliente-title').innerText = 'Nuevo Cliente';
    document.getElementById('modal-cliente').style.display = 'flex';
}

function abrirModalClienteEditar(id) {
    var cli = null;
    for (var i = 0; i < clientesData.length; i++) {
        if (clientesData[i].idCliente === id) { cli = clientesData[i]; break; }
    }
    if (!cli) return;

    document.getElementById('cli-id').value        = cli.idCliente;
    document.getElementById('cli-nombre').value    = cli.nombre        || '';
    document.getElementById('cli-razon').value     = cli.razonSocial   || '';
    document.getElementById('cli-rfc').value       = cli.rfc           || '';
    document.getElementById('cli-telefono').value  = cli.telefono      || '';
    document.getElementById('cli-email').value     = cli.email         || '';
    document.getElementById('cli-direccion').value = cli.direccion     || '';
    document.getElementById('cli-cp').value        = cli.codigoPostal  || '';
    document.getElementById('cli-regimen').value   = cli.regimenFiscal || '';

    document.getElementById('modal-cliente-title').innerText = 'Editar Cliente';
    document.getElementById('modal-cliente').style.display = 'flex';
}

function cerrarModalCliente() { document.getElementById('modal-cliente').style.display = 'none'; }

function guardarCliente() {
    var id = document.getElementById('cli-id').value;

    var payload = {
        idCliente:     id || null,
        nombre:        document.getElementById('cli-nombre').value,
        razonSocial:   document.getElementById('cli-razon').value,
        rfc:           document.getElementById('cli-rfc').value,
        telefono:      document.getElementById('cli-telefono').value,
        email:         document.getElementById('cli-email').value,
        direccion:     document.getElementById('cli-direccion').value,
        codigoPostal:  document.getElementById('cli-cp').value,
        regimenFiscal: document.getElementById('cli-regimen').value,
        usoCfdi:       'G03',
        activo:        true
    };

    var accion = id ? 'actualizar' : 'crear';

    fetch(CTX + '/ClienteServlet?accion=' + accion, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json;charset=UTF-8' },
        body: JSON.stringify(payload)
    }).then(function(r) { return r.json(); }).then(function(res) {
        if (res.success) { cerrarModalCliente(); cargarClientes(); }
        else { alert('Error al guardar: ' + res.message); }
    }).catch(function(e) {
        console.error("Error guardando cliente:", e);
        alert('Error de comunicación con el servidor.');
    });
}

function eliminarCliente(id) {
    if (!confirm("¿Seguro que deseas eliminar este cliente?")) return;
    fetch(CTX + '/ClienteServlet?accion=eliminar&id=' + id, { method: 'POST' })
        .then(function(r) { return r.json(); })
        .then(function(res) { if (res.success) cargarClientes(); else alert(res.message); })
        .catch(function(e) { console.error("Error eliminando cliente:", e); });
}

// ══════════════════════════════════════════════════════════
//  USUARIOS (MongoDB)
// ══════════════════════════════════════════════════════════

function cargarUsuarios() {
    fetch(CTX + '/UsuariosServlet?action=list')
        .then(function(r) { return r.json(); })
        .then(function(data) {
            usuariosData = data;
            renderTablaUsuarios(data);
        })
        .catch(function(e) {
            console.error("Error cargando usuarios", e);
            var tbody = document.getElementById('usuarios-tbody');
            if (tbody) tbody.innerHTML = '<tr><td colspan="7" class="empty" style="color:var(--danger)">Error al cargar usuarios.</td></tr>';
        });
}

function renderTablaUsuarios(lista) {
    var tbody = document.getElementById('usuarios-tbody');
    if (!tbody) return;

    if (!lista || lista.length === 0) {
        tbody.innerHTML = '<tr><td colspan="7" class="empty">Sin usuarios registrados.</td></tr>';
        return;
    }

    var html = '';
    for (var i = 0; i < lista.length; i++) {
        var u = lista[i];
        var nombreRol = (u.rol && u.rol.nombre) ? u.rol.nombre : 'Usuario';
        var idUsuario = u.id || '';
        var iniciales = u.username ? u.username.substring(0, 2).toUpperCase() : 'US';
        var dotClass  = u.activo ? 'dot-green' : 'dot-gray';
        var estadoText = u.activo ? ' Activo' : ' Inactivo';

        html += '<tr>' +
            '<td><div class="user-cell"><div class="user-avatar-sm">' + iniciales + '</div><strong>' + esc(u.username) + '</strong></div></td>' +
            '<td>' + esc(u.nombreCompleto || '') + '</td>' +
            '<td class="email-cell">' + esc(u.email || '—') + '</td>' +
            '<td><span class="badge badge-rol">' + esc(nombreRol) + '</span></td>' +
            '<td><span class="dot ' + dotClass + '"></span>' + estadoText + '</td>' +
            '<td class="col-meta">' + (u.ultimoAcceso || 'Nunca') + '</td>' +
            '<td class="col-actions">' +
                '<button class="action-btn" onclick="abrirModalEditarUsuario(\'' + idUsuario + '\')">Editar</button>' +
                '<button class="action-btn action-btn-danger" onclick="eliminarUsuario(\'' + idUsuario + '\')">Borrar</button>' +
            '</td>' +
        '</tr>';
    }
    tbody.innerHTML = html;
}

function abrirModalNuevo() {
    var modal = document.getElementById('modal-usuario');
    if (!modal) return;

    document.getElementById('usu-id').value             = '';
    document.getElementById('usu-username').value       = '';
    document.getElementById('usu-nombre_completo').value = '';
    document.getElementById('usu-email').value          = '';
    document.getElementById('usu-rol').value            = '3';
    document.getElementById('usu-activo').value         = 'true';

    document.getElementById('modal-usuario-title').innerText = 'Nuevo Usuario';
    modal.style.display = 'flex';
}

function abrirModalEditarUsuario(id) {
    var usu = null;
    for (var i = 0; i < usuariosData.length; i++) {
        if (usuariosData[i].id === id) { usu = usuariosData[i]; break; }
    }
    if (!usu) return;

    document.getElementById('usu-id').value              = usu.id            || '';
    document.getElementById('usu-username').value        = usu.username      || '';
    document.getElementById('usu-nombre_completo').value = usu.nombreCompleto || '';
    document.getElementById('usu-email').value           = usu.email         || '';

    if (usu.rol && usu.rol.idRol) {
        document.getElementById('usu-rol').value = usu.rol.idRol;
    } else {
        document.getElementById('usu-rol').value = '3';
    }

    document.getElementById('usu-activo').value = usu.activo ? 'true' : 'false';
    document.getElementById('modal-usuario-title').innerText = 'Editar Usuario';
    document.getElementById('modal-usuario').style.display = 'flex';
}

function cerrarModalUsuario() {
    var modal = document.getElementById('modal-usuario');
    if (modal) modal.style.display = 'none';
}

function guardarUsuario() {
    var id = document.getElementById('usu-id').value;
    var params = new URLSearchParams();

    params.append('action', id ? 'update' : 'create');
    if (id) params.append('id', id);

    params.append('username',        document.getElementById('usu-username').value);
    params.append('nombre_completo', document.getElementById('usu-nombre_completo').value);
    params.append('email',           document.getElementById('usu-email').value);
    params.append('rol_id',          document.getElementById('usu-rol').value);
    params.append('activo',          document.getElementById('usu-activo').value);

    fetch(CTX + '/UsuariosServlet', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
        body: params
    }).then(function(r) { return r.json(); }).then(function(res) {
        if (res.ok) {
            cerrarModalUsuario();
            cargarUsuarios();
        } else {
            alert(res.mensaje || 'Error al guardar usuario');
        }
    }).catch(function(e) {
        console.error(e);
        alert('Error de comunicación con el servidor.');
    });
}

function eliminarUsuario(id) {
    if (!confirm("¿Seguro que deseas eliminar este usuario del sistema?")) return;

    var params = new URLSearchParams();
    params.append('action', 'delete');
    params.append('id', id);

    fetch(CTX + '/UsuariosServlet', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
        body: params
    }).then(function(r) { return r.json(); }).then(function(res) {
        if (res.ok) cargarUsuarios();
        else alert(res.mensaje || 'Error al eliminar');
    }).catch(function(e) { console.error("Error eliminando usuario:", e); });
}

// ══════════════════════════════════════════════════════════
//  DASHBOARD / INICIO (Dinámico)
// ══════════════════════════════════════════════════════════

function renderDashboard() {
    // 1. Clientes Activos
    var clientesActivos = 0;
    if (clientesData && clientesData.length > 0) {
        for (var i = 0; i < clientesData.length; i++) {
            if (clientesData[i].activo) clientesActivos++;
        }
    }
    var elCli = document.getElementById('stat-cli-activos');
    if (elCli) elCli.innerText = clientesActivos;

    // 2. Cotizaciones Pendientes y últimas 5
    var cotPendientes      = 0;
    var ultimasCotizaciones = [];

    if (cotizacionesData && cotizacionesData.length > 0) {
        for (var j = 0; j < cotizacionesData.length; j++) {
            if (cotizacionesData[j].estatus === 'Pendiente') cotPendientes++;
        }
        ultimasCotizaciones = cotizacionesData.slice().reverse().slice(0, 5);
    }

    var elCot = document.getElementById('stat-cot-pendientes');
    if (elCot) elCot.innerText = cotPendientes;

    // 3. Tabla de Últimas Cotizaciones
    var tbodyDash = document.getElementById('dashboard-cotizaciones-tbody');
    if (tbodyDash) {
        if (ultimasCotizaciones.length === 0) {
            tbodyDash.innerHTML = '<tr><td colspan="4" class="empty">No hay cotizaciones recientes.</td></tr>';
        } else {
            var html = '';
            for (var k = 0; k < ultimasCotizaciones.length; k++) {
                var c = ultimasCotizaciones[k];
                var badgeColor = 'badge-yellow';
                if (c.estatus === 'Aprobada')  badgeColor = 'badge-green';
                else if (c.estatus === 'Rechazada') badgeColor = 'badge-red';

                var monto = (c.montoTotal || 0).toFixed(2);

                html += '<tr>' +
                    '<td>' + esc(c.nombreCliente) + '</td>' +
                    '<td><span class="badge badge-gray">' + esc(c.regimen || 'N/A') + '</span></td>' +
                    '<td>$' + monto + '</td>' +
                    '<td><span class="badge ' + badgeColor + '">' + esc(c.estatus) + '</span></td>' +
                '</tr>';
            }
            tbodyDash.innerHTML = html;
        }
    }
}