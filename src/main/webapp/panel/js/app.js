// ── Configuración global ─────────────────────────────────
// Nota: La variable CTX ya está definida en panel.jsp
const ROL_LABEL = { 1: 'Administrador', 2: 'Contador', 3: 'Capturista' };

const labels = {
    dashboard:     { title: 'Inicio',                  accion: '+ Nueva factura'     },
    clientes:      { title: 'Clientes',                accion: '+ Nuevo cliente'     },
    facturas:      { title: 'Facturas',                accion: '+ Nueva factura'     },
    usuarios:      { title: 'Usuarios del sistema',    accion: '+ Nuevo usuario'     },
    empleados:     { title: 'Empleados',               accion: '+ Nuevo empleado'    },
    nomina:        { title: 'Nómina',                  accion: '+ Nuevo periodo'     },
    polizas:       { title: 'Pólizas',                 accion: '+ Nueva póliza'      },
    declaraciones: { title: 'Declaraciones fiscales',  accion: '+ Nueva declaración' },
    cuentas:       { title: 'Catálogo de cuentas',     accion: '+ Nueva cuenta'      },
    config:        { title: 'Configuración',           accion: null                  }
};

let currentSection    = 'dashboard';
let usuariosData      = [];
let empleadosData     = [];

// Helper para escapar HTML y evitar XSS
function esc(s) {
    if (!s) return '';
    return String(s)
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#39;');
}

// ── Navegación SPA ───────────────────────────────────────
function loadSection(id, el) {
    document.getElementById('section-' + currentSection).style.display = 'none';
    document.querySelectorAll('.nav-item').forEach(n => n.classList.remove('active'));

    const target = document.getElementById('section-' + id);
    if (target) target.style.display = 'block';
    if (el) el.classList.add('active');

    const cfg = labels[id] || {};
    document.getElementById('breadcrumb').textContent = cfg.title || id;

    const btn = document.getElementById('btn-accion');
    if (cfg.accion) {
        btn.textContent = cfg.accion;
        btn.style.display = '';
    } else {
        btn.style.display = 'none';
    }

    currentSection = id;

    // Cargar datos según la sección activa
    if (id === 'usuarios') cargarUsuarios();
    if (id === 'empleados') cargarEmpleados();
    if (id === 'clientes') cargarClientes();

    return false;
}

function accionPrincipal() {
    if (currentSection === 'usuarios') { abrirModalNuevo(); return; }
    if (currentSection === 'empleados') { abrirModalEmpleadoNuevo(); return; }
    if (currentSection === 'clientes') { abrirModalClienteNuevo(); return; }

    const cfg = labels[currentSection];
    if (cfg && cfg.accion) alert('Acción aún no implementada para: ' + cfg.accion);
}

// ══════════════════════════════════════════════════════════
//  EMPLEADOS (MongoDB) — CRUD
// ══════════════════════════════════════════════════════════

function cargarEmpleados() {
    fetch(CTX + '/EmpleadosServlet?action=list')
        .then(r => r.json())
        .then(data => { empleadosData = data; renderTablaEmpleados(data); })
        .catch(e => console.error("Error cargando empleados", e));
}


function renderTablaEmpleados(lista) {
    const tbody = document.getElementById('empleados-tbody');
    if (!lista.length) {
        tbody.innerHTML = `<tr><td colspan="6" class="empty">Sin empleados registrados.</td></tr>`;
        return;
    }

    tbody.innerHTML = lista.map(e => `
        <tr>
            <td><strong>${esc(e.numeroEmpleado)}</strong></td>
            <td>${esc(e.nombre)} ${esc(e.apellidoPaterno || '')}</td>
            <td>${esc(e.departamento?.nombre || '—')}</td>
            <td>${esc(e.puesto?.nombre || '—')}</td>
            <td><span class="badge ${e.activo ? 'badge-green' : 'badge-red'}">${e.activo ? 'Activo' : 'Baja'}</span></td>
            <td class="col-actions" style="text-align:right">
                <button class="action-btn" onclick="abrirModalEmpleadoEditar('${e.id}')">Editar</button>
                <button class="action-btn action-btn-danger" onclick="eliminarEmpleado('${e.id}')">Borrar</button>
            </td>
        </tr>
    `).join('');
}

function abrirModalEmpleadoNuevo() {
    document.getElementById('emp-id').value = '';
    document.getElementById('emp-numero').value = '';
    document.getElementById('emp-fecha').value = '';
    document.getElementById('emp-nombre').value = '';
    document.getElementById('emp-paterno').value = '';
    document.getElementById('emp-depto').value = '';
    document.getElementById('emp-puesto').value = '';
    document.getElementById('emp-salario').value = '';
    document.getElementById('emp-activo').value = 'true';

    document.getElementById('modal-empleado-title').innerText = 'Nuevo Empleado';
    document.getElementById('modal-empleado').style.display = 'flex';
}

function abrirModalEmpleadoEditar(id) {
    const emp = empleadosData.find(e => e.id === id);
    if (!emp) return;

    document.getElementById('emp-id').value = emp.id;
    document.getElementById('emp-numero').value = emp.numeroEmpleado || '';
    document.getElementById('emp-fecha').value = emp.fechaContratacion || '';
    document.getElementById('emp-nombre').value = emp.nombre || '';
    document.getElementById('emp-paterno').value = emp.apellidoPaterno || '';
    document.getElementById('emp-depto').value = emp.departamento?.nombre || '';
    document.getElementById('emp-puesto').value = emp.puesto?.nombre || '';
    document.getElementById('emp-salario').value = emp.puesto?.salarioMinimo || '';
    document.getElementById('emp-activo').value = emp.activo ? "true" : "false";

    document.getElementById('modal-empleado-title').innerText = 'Editar Empleado';
    document.getElementById('modal-empleado').style.display = 'flex';
}

function cerrarModalEmpleado() {
    document.getElementById('modal-empleado').style.display = 'none';
}

function guardarEmpleado() {
    const params = new URLSearchParams();
    const id = document.getElementById('emp-id').value;

    params.append('action', id ? 'update' : 'create');
    if (id) params.append('id', id);

    params.append('numeroEmpleado', document.getElementById('emp-numero').value);
    params.append('fechaContratacion', document.getElementById('emp-fecha').value);
    params.append('nombre', document.getElementById('emp-nombre').value);
    params.append('apellidoPaterno', document.getElementById('emp-paterno').value);
    params.append('departamentoNombre', document.getElementById('emp-depto').value);
    params.append('puestoNombre', document.getElementById('emp-puesto').value);
    params.append('salario', document.getElementById('emp-salario').value);
    params.append('activo', document.getElementById('emp-activo').value);

    fetch(CTX + '/EmpleadosServlet', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
        body: params
    }).then(r => r.json()).then(res => {
        if (res.ok) {
            cerrarModalEmpleado();
            cargarEmpleados();
        } else {
            alert(res.mensaje);
        }
    });
}

function eliminarEmpleado(id) {
    if (!confirm("¿Seguro que deseas eliminar este empleado de MongoDB?")) return;

    const params = new URLSearchParams();
    params.append('action', 'delete');
    params.append('id', id);

    fetch(CTX + '/EmpleadosServlet', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
        body: params
    }).then(r => r.json()).then(res => {
        if (res.ok) cargarEmpleados();
        else alert(res.mensaje);
    });
}

function filtrarEmpleados(query) {
    const q = query.toLowerCase();
    const filtrados = empleadosData.filter(e =>
        (e.nombre && e.nombre.toLowerCase().includes(q)) ||
        (e.numeroEmpleado && e.numeroEmpleado.toLowerCase().includes(q))
    );
    renderTablaEmpleados(filtrados);
}

// ══════════════════════════════════════════════════════════
//  USUARIOS (SQL) — Funciones
// ══════════════════════════════════════════════════════════

function cargarUsuarios() {
    fetch(CTX + '/UsuariosServlet?action=list')
        .then(r => r.json())
        .then(data => {
            usuariosData = data;
            renderTablaUsuarios(data);
        })
        .catch(e => {
            console.error("Error cargando usuarios", e);
            const tbody = document.getElementById('usuarios-tbody');
            if(tbody) tbody.innerHTML = `<tr><td colspan="7" class="empty" style="color:var(--danger)">Error al cargar usuarios.</td></tr>`;
        });
}

function renderTablaUsuarios(lista) {
    const tbody = document.getElementById('usuarios-tbody');
    if (!tbody) return;

    if (!lista || lista.length === 0) {
        tbody.innerHTML = `<tr><td colspan="7" class="empty">Sin usuarios registrados.</td></tr>`;
        return;
    }

    tbody.innerHTML = lista.map(u => {
        // 1. Detectar el nombre correcto sin importar cómo lo serializó Java
        const nombreReal = u.nombre_completo || u.nombreCompleto || u.nombre || '';
        // Si aún usas apellido paterno en tu backend, lo concatenamos, si no, se ignora.
        const apellido = u.apellidoPaterno || '';
        const nombreMostrar = (nombreReal + ' ' + apellido).trim();

        // 2. Extraer el rol según tu nuevo esquema de MongoDB
        let nombreRol = 'Usuario';
        if (u.rol && u.rol.nombre) {
            // Capitaliza la primera letra (ej. "administrador" -> "Administrador")
            nombreRol = u.rol.nombre.charAt(0).toUpperCase() + u.rol.nombre.slice(1);
        } else if (u.rol_id) {
            nombreRol = ROL_LABEL[u.rol_id] || 'Usuario';
        }

        // 3. Manejar el ID (MongoDB usa _id, pero a veces Java lo mapea a id)
        const idUsuario = u.id || u._id || '';

        return `
            <tr>
                <td>
                    <div class="user-cell">
                        <div class="user-avatar-sm">${u.username ? u.username.substring(0,2).toUpperCase() : 'US'}</div>
                        <strong>${esc(u.username)}</strong>
                    </div>
                </td>
                <td>${esc(nombreMostrar)}</td>
                <td class="email-cell">${esc(u.email || '—')}</td>
                <td><span class="badge badge-rol">${esc(nombreRol)}</span></td>
                <td>
                    <span class="dot ${u.activo ? 'dot-green' : 'dot-gray'}"></span>
                    ${u.activo ? ' Activo' : ' Inactivo'}
                </td>
                <td class="col-meta">${esc(u.ultimo_acceso || 'Nunca')}</td>
                <td class="col-actions">
                    <button class="action-btn" onclick="abrirModalEditarUsuario('${idUsuario}')">Editar</button>
                    <button class="action-btn action-btn-danger" onclick="eliminarUsuario('${idUsuario}')">Borrar</button>
                </td>
            </tr>
        `;
    }).join('');
}

function filtrarUsuarios(query) {
    if (!usuariosData) return;
    const q = query.toLowerCase();
    const filtrados = usuariosData.filter(u => {
        const nombreReal = u.nombre_completo || u.nombreCompleto || u.nombre || '';
        return (u.username && u.username.toLowerCase().includes(q)) ||
               (nombreReal.toLowerCase().includes(q)) ||
               (u.email && u.email.toLowerCase().includes(q));
    });
    renderTablaUsuarios(filtrados);
}

function abrirModalNuevo() {
    const modal = document.getElementById('modal-usuario');
    if (!modal) return;

    document.getElementById('usu-id').value = '';
    document.getElementById('usu-username').value = '';
    // Cambiado de usu-nombre a usu-nombre_completo
    document.getElementById('usu-nombre_completo').value = '';
    document.getElementById('usu-email').value = '';
    document.getElementById('usu-rol').value = '3';
    document.getElementById('usu-activo').value = 'true';

    document.getElementById('modal-usuario-title').innerText = 'Nuevo Usuario';
    modal.style.display = 'flex';
}

function abrirModalEditarUsuario(id) {
    const usu = usuariosData.find(u => u.id === id || u._id === id);
    if (!usu) return;

    // Ajustar campos con las nuevas variables
    document.getElementById('usu-id').value = usu.id || usu._id || '';
    document.getElementById('usu-username').value = usu.username || '';
    document.getElementById('usu-nombre_completo').value = usu.nombre_completo || usu.nombreCompleto || usu.nombre || '';
    document.getElementById('usu-email').value = usu.email || '';

    // Asignar el rol al select del modal (basado en el string del esquema)
    if (usu.rol && usu.rol.nombre) {
        document.getElementById('usu-rol').value = usu.rol.nombre;
    } else {
        document.getElementById('usu-rol').value = usu.rol_id || 3;
    }

    document.getElementById('usu-activo').value = usu.activo ? "true" : "false";

    document.getElementById('modal-usuario-title').innerText = 'Editar Usuario';
    document.getElementById('modal-usuario').style.display = 'flex';
}

function guardarUsuario() {
    const params = new URLSearchParams();
    const id = document.getElementById('usu-id').value;

    params.append('action', id ? 'update' : 'create');
    if (id) params.append('id', id);

    params.append('username', document.getElementById('usu-username').value);
params.append('nombre_completo', document.getElementById('usu-nombre_completo').value);
    params.append('email', document.getElementById('usu-email').value);
    params.append('rol_id', document.getElementById('usu-rol').value);
    params.append('activo', document.getElementById('usu-activo').value);

    fetch(CTX + '/UsuariosServlet', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
        body: params
    }).then(r => r.json()).then(res => {
        if (res.ok) {
            cerrarModalUsuario();
            cargarUsuarios();
        } else {
            alert(res.mensaje || 'Error al guardar usuario');
        }
    }).catch(e => {
        console.error(e);
        alert('Error de comunicación con el servidor.');
    });
}

function cerrarModalUsuario() {
    document.getElementById('modal-usuario').style.display = 'none';
}

function eliminarUsuario(id) {
    if (!confirm("¿Seguro que deseas eliminar este usuario del sistema?")) return;

    const params = new URLSearchParams();
    params.append('action', 'delete');
    params.append('id', id);

    fetch(CTX + '/UsuariosServlet', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
        body: params
    }).then(r => r.json()).then(res => {
        if (res.ok) cargarUsuarios();
        else alert(res.mensaje || 'Error al eliminar');
    });
}

let clientesData = [];

function cargarClientes() {
    fetch(CTX + '/ClientesServlet?action=list')
        .then(r => r.json())
        .then(data => { clientesData = data; renderTablaClientes(data); });
}

function renderTablaClientes(lista) {
    const tbody = document.getElementById('clientes-tbody');
    tbody.innerHTML = lista.map(c => `
        <tr>
            <td><strong>${esc(c.nombre)}</strong><br><small>${esc(c.razon_social || '')}</small></td>
            <td>${esc(c.rfc)}</td>
            <td>${esc(c.email || '')}</td>
            <td>${esc(c.telefono || '')}</td>
            <td><span class="badge ${c.activo ? 'badge-green' : 'badge-red'}">${c.activo ? 'Activo' : 'Inactivo'}</span></td>
            <td style="text-align:right">
                <button class="action-btn" onclick="abrirModalClienteEditar('${c.id}')">Editar</button>
            </td>
        </tr>
    `).join('');
}

function abrirModalClienteNuevo() {
    // Resetear campos
    document.getElementById('cli-id').value = '';
    document.getElementById('modal-cliente').style.display = 'flex';
}

function guardarCliente() {
    const params = new URLSearchParams();
    params.append('action', document.getElementById('cli-id').value ? 'update' : 'create');
    params.append('id', document.getElementById('cli-id').value);

    // Estos nombres deben coincidir con los getParameter del Servlet
    params.append('nombre', document.getElementById('cli-nombre').value);
    params.append('razon_social', document.getElementById('cli-razon').value);
    params.append('rfc', document.getElementById('cli-rfc').value);
    params.append('telefono', document.getElementById('cli-telefono').value);
    params.append('email', document.getElementById('cli-email').value);
    params.append('direccion', document.getElementById('cli-direccion').value);
    params.append('codigo_postal', document.getElementById('cli-cp').value);
    params.append('regimen_fiscal', document.getElementById('cli-regimen').value);
    params.append('uso_cfdi', 'G03'); // O el valor por defecto que necesites
    params.append('activo', 'true');

    fetch(CTX + '/ClientesServlet', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
        body: params
    }).then(r => r.json()).then(res => {
        if (res.ok) {
            cerrarModalCliente();
            cargarClientes();
        } else {
            alert('Error al guardar: ' + res.mensaje);
        }
    });
}

function cerrarModalCliente() { document.getElementById('modal-cliente').style.display = 'none'; }