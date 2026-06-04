<%@ page contentType="text/html; charset=UTF-8" isELIgnored="true" %>
<%
    if (session.getAttribute("usuarioLogueado") == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
    String u = (String) session.getAttribute("usuarioLogueado");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ContaDesk | Panel</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/panel/panel.css">
    <style>
        /* Modales más amplios para grids de información pesada (Ej. Nómina/Empleados) */
        .modal-lg { max-width: 600px !important; }
        .form-grid-2 { display: grid; grid-template-columns: 1fr 1fr; gap: 16px; }
        @media (max-width: 640px) { .form-grid-2 { grid-template-columns: 1fr; } }
    </style>
</head>
<body>

<aside class="sidebar">
    <div class="sidebar-brand">ContaDesk <span>ERP</span></div>
    <div class="sidebar-section">
        <div class="sidebar-section-label">Principal</div>
        <a href="#" class="nav-item active" onclick="return loadSection('dashboard', this)">
            <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.7" viewBox="0 0 24 24"><rect x="3" y="3" width="7" height="7" rx="1"/><rect x="14" y="3" width="7" height="7" rx="1"/><rect x="3" y="14" width="7" height="7" rx="1"/><rect x="14" y="14" width="7" height="7" rx="1"/></svg>
            Inicio
        </a>
        <a href="#" class="nav-item" onclick="return loadSection('clientes', this)">
            <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.7" viewBox="0 0 24 24"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg>
            Clientes
        </a>
        <a href="#" class="nav-item" onclick="return loadSection('facturas', this)">
            <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.7" viewBox="0 0 24 24"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/><line x1="16" y1="13" x2="8" y2="13"/><line x1="16" y1="17" x2="8" y2="17"/><polyline points="10 9 9 9 8 9"/></svg>
            Facturas
        </a>
        <a href="#" class="nav-item" onclick="return loadSection('usuarios', this)">
            <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.7" viewBox="0 0 24 24"><circle cx="12" cy="8" r="4"/><path d="M4 20c0-4 3.6-7 8-7s8 3 8 7"/><circle cx="19" cy="8" r="2.5"/><path d="M22 14c-1-.5-2-.7-3-.7"/></svg>
            Usuarios
        </a>
    </div>

    <div class="sidebar-section">
        <div class="sidebar-section-label">Nómina</div>
        <a href="#" class="nav-item" onclick="return loadSection('empleados', this)">
            <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.7" viewBox="0 0 24 24"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
            Empleados
        </a>
        <a href="#" class="nav-item" onclick="return loadSection('nomina', this)">
            <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.7" viewBox="0 0 24 24"><rect x="2" y="5" width="20" height="14" rx="2"/><line x1="2" y1="10" x2="22" y2="10"/></svg>
            Nómina
        </a>
    </div>

    <div class="sidebar-section">
        <div class="sidebar-section-label">Contabilidad</div>
        <a href="#" class="nav-item" onclick="return loadSection('polizas', this)">
            <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.7" viewBox="0 0 24 24"><line x1="18" y1="20" x2="18" y2="10"/><line x1="12" y1="20" x2="12" y2="4"/><line x1="6" y1="20" x2="6" y2="14"/></svg>
            Pólizas
        </a>
        <a href="#" class="nav-item" onclick="return loadSection('declaraciones', this)">
            <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.7" viewBox="0 0 24 24"><circle cx="12" cy="12" r="10"/><path d="M12 8v4l3 3"/></svg>
            Declaraciones
        </a>
        <a href="#" class="nav-item" onclick="return loadSection('cuentas', this)">
            <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.7" viewBox="0 0 24 24"><line x1="8" y1="6" x2="21" y2="6"/><line x1="8" y1="12" x2="21" y2="12"/><line x1="8" y1="18" x2="21" y2="18"/><line x1="3" y1="6" x2="3.01" y2="6"/><line x1="3" y1="12" x2="3.01" y2="12"/><line x1="3" y1="18" x2="3.01" y2="18"/></svg>
            Catálogo de cuentas
        </a>
    </div>

    <div class="sidebar-bottom">
        <a href="#" class="nav-item" onclick="return loadSection('config', this)">
            <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.7" viewBox="0 0 24 24"><circle cx="12" cy="12" r="3"/><path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1-2.83 2.83l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-4 0v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83-2.83l.06-.06A1.65 1.65 0 0 0 4.68 15a1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1 0-4h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 2.83-2.83l.06.06A1.65 1.65 0 0 0 9 4.68a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 4 0v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 2.83l-.06.06A1.65 1.65 0 0 0 19.4 9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 0 4h-.09a1.65 1.65 0 0 0-1.51 1z"/></svg>
            Configuración
        </a>
        <a href="<%= request.getContextPath() %>/LogoutServlet" class="nav-item">
            <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.7" viewBox="0 0 24 24"><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/><polyline points="16 17 21 12 16 7"/><line x1="21" y1="12" x2="9" y2="12"/></svg>
            Cerrar sesión
        </a>
    </div>
</aside>

<div class="main">
    <header class="topbar">
        <div class="topbar-left">
            <span class="breadcrumb" id="breadcrumb">Inicio</span>
        </div>
        <div class="topbar-right">
            <button class="btn btn-primary" id="btn-accion" onclick="accionPrincipal()">
                + Nueva factura
            </button>
            <div class="divider"></div>
            <div class="avatar" title="<%= u %>">
                <% out.print(u.length() >= 2 ? u.substring(0, 2).toUpperCase() : "US"); %>
            </div>
        </div>
    </header>

    <main class="content" id="content-area">

        <%-- ── Dashboard ── --%>
        <div id="section-dashboard">
            <div class="stats-grid">
                <div class="stat-card">
                    <div class="stat-label">Facturas este mes</div>
                    <div class="stat-value">84</div>
                    <div class="stat-sub up">↑ 12% vs. mes anterior</div>
                </div>
                <div class="stat-card">
                    <div class="stat-label">Total facturado</div>
                    <div class="stat-value">$218,400</div>
                    <div class="stat-sub up">↑ 8% vs. mes anterior</div>
                </div>
                <div class="stat-card">
                    <div class="stat-label">Facturas pendientes</div>
                    <div class="stat-value">11</div>
                    <div class="stat-sub down">↓ 3 sin pagar &gt;30 días</div>
                </div>
                <div class="stat-card">
                    <div class="stat-label">Empleados activos</div>
                    <div class="stat-value">23</div>
                    <div class="stat-sub">Nómina quincenal</div>
                </div>
            </div>
            </div>

        <%-- ── Usuarios ── --%>
        <div id="section-usuarios" style="display:none">
            <div id="usuarios-toast" class="toast" style="display:none"></div>

            <div class="panel">
                <div class="panel-header">
                    <span class="panel-title">Usuarios del sistema</span>
                    <div class="panel-header-actions">
                        <div class="search-wrapper">
                            <svg class="search-icon" width="14" height="14" fill="none" stroke="currentColor" stroke-width="1.8" viewBox="0 0 24 24"><circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/></svg>
                            <input type="text" id="usuarios-search" class="input-search" placeholder="Buscar…" oninput="filtrarUsuarios(this.value)">
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <table id="usuarios-table">
                        <thead>
                            <tr>
                                <th>Usuario</th>
                                <th>Nombre completo</th>
                                <th>Email</th>
                                <th>Rol</th>
                                <th>Estado</th>
                                <th>Último acceso</th>
                                <th style="text-align:right">Acciones</th>
                            </tr>
                        </thead>
                        <tbody id="usuarios-tbody">
                            <tr><td colspan="7" class="empty">Cargando usuarios...</td></tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <%-- ── Secciones vacías (Clientes, Facturas, Nómina, etc.) ── --%>
        <div id="section-clientes"       style="display:none"><div class="empty-page">Sección Clientes</div></div>
        <div id="section-facturas"       style="display:none"><div class="empty-page">Sección Facturas</div></div>
        <div id="section-nomina"         style="display:none"><div class="empty-page">Sección Nómina</div></div>
        <div id="section-polizas"        style="display:none"><div class="empty-page">Sección Pólizas</div></div>
        <div id="section-declaraciones"  style="display:none"><div class="empty-page">Sección Declaraciones</div></div>
        <div id="section-cuentas"        style="display:none"><div class="empty-page">Sección Catálogo de cuentas</div></div>
        <div id="section-config"         style="display:none"><div class="empty-page">Sección Configuración</div></div>

        <%-- ── Empleados (MongoDB) ── --%>
        <div id="section-empleados" style="display:none">
            <div class="panel">
                <div class="panel-header">
                    <span class="panel-title">Directorio de Empleados</span>
                    <div class="panel-header-actions">
                        <div class="search-wrapper">
                            <svg class="search-icon" width="14" height="14" fill="none" stroke="currentColor" stroke-width="1.8" viewBox="0 0 24 24"><circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/></svg>
                            <input type="text" id="empleados-search" class="input-search" placeholder="Buscar empleado..." oninput="filtrarEmpleados(this.value)">
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <table id="empleados-table">
                        <thead>
                            <tr>
                                <th>No. Emp</th>
                                <th>Nombre</th>
                                <th>Departamento</th>
                                <th>Puesto</th>
                                <th>Estado</th>
                                <th style="text-align:right">Acciones</th>
                            </tr>
                        </thead>
                        <tbody id="empleados-tbody">
                            </tbody>
                    </table>
                </div>
            </div>

            <div id="modal-empleado" class="modal-overlay" style="display:none">
                <div class="modal-box modal-lg" id="modal-empleado-box">
                    <div class="modal-header">
                        <div class="modal-header-left">
                            <span id="modal-empleado-title">Nuevo Empleado</span>
                        </div>
                        <button class="modal-close" onclick="cerrarModalEmpleado()">✖</button>
                    </div>
                    <div class="modal-body">
                        <input type="hidden" id="emp-id">

                        <div class="form-grid-2">
                            <div class="form-group">
                                <label>No. Empleado <span class="required">*</span></label>
                                <input type="text" id="emp-numero" class="form-input">
                            </div>
                            <div class="form-group">
                                <label>Fecha Contratación <span class="required">*</span></label>
                                <input type="date" id="emp-fecha" class="form-input">
                            </div>
                            <div class="form-group">
                                <label>Nombre <span class="required">*</span></label>
                                <input type="text" id="emp-nombre" class="form-input">
                            </div>
                            <div class="form-group">
                                <label>Apellido Paterno</label>
                                <input type="text" id="emp-paterno" class="form-input">
                            </div>
                            <div class="form-group">
                                <label>Departamento</label>
                                <input type="text" id="emp-depto" class="form-input">
                            </div>
                            <div class="form-group">
                                <label>Puesto</label>
                                <input type="text" id="emp-puesto" class="form-input">
                            </div>
                            <div class="form-group">
                                <label>Salario Base (Mensual)</label>
                                <input type="number" id="emp-salario" class="form-input" step="0.01">
                            </div>
                            <div class="form-group">
                                <label>Estado</label>
                                <select id="emp-activo" class="form-input">
                                    <option value="true">Activo</option>
                                    <option value="false">Baja</option>
                                </select>
                            </div>
                        </div>
                        <div id="emp-error" class="form-error" style="display:none; margin-top:15px;"></div>
                    </div>
                    <div class="modal-footer">
                        <button class="btn" onclick="cerrarModalEmpleado()">Cancelar</button>
                        <button class="btn btn-primary" onclick="guardarEmpleado()">Guardar Empleado</button>
                    </div>
                </div>
            </div>
        </div>

    </main>
</div>

<script>
    // ── Configuración global ─────────────────────────────────
    const CTX = '<%= request.getContextPath() %>';

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

    // Helper para escapar HTML
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
        if (cfg.accion) { btn.textContent = cfg.accion; btn.style.display = ''; }
        else              { btn.style.display = 'none'; }

        currentSection = id;

        // Cargar datos según la sección activa
        if (id === 'usuarios') cargarUsuarios();
        if (id === 'empleados') cargarEmpleados();

        return false;
    }

    function accionPrincipal() {
        if (currentSection === 'usuarios') { abrirModalNuevo(); return; }
        if (currentSection === 'empleados') { abrirModalEmpleadoNuevo(); return; }
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
    //  USUARIOS (SQL) — Stub Functions
    // ══════════════════════════════════════════════════════════

    // ══════════════════════════════════════════════════════════
        //  USUARIOS (SQL) — Funciones Recuperadas
        // ══════════════════════════════════════════════════════════

        function cargarUsuarios() {
            // Asumo que tienes un servlet llamado UsuariosServlet
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

                // Tabla corregida sin las barras invertidas (\)
                tbody.innerHTML = lista.map(u => `
                    <tr>
                        <td>
                            <div class="user-cell">
                                <div class="user-avatar-sm">${u.username ? u.username.substring(0,2).toUpperCase() : 'US'}</div>
                                <strong>${esc(u.username)}</strong>
                            </div>
                        </td>
                        <td>${esc(u.nombre || '')} ${esc(u.apellidoPaterno || '')}</td>
                        <td class="email-cell">${esc(u.email || '—')}</td>
                        <td><span class="badge badge-rol badge-rol-${u.rol_id || 3}">${ROL_LABEL[u.rol_id] || 'Usuario'}</span></td>
                        <td>
                            <span class="dot ${u.activo ? 'dot-green' : 'dot-gray'}"></span>
                            ${u.activo ? ' Activo' : ' Inactivo'}
                        </td>
                        <td class="col-meta">${esc(u.ultimo_acceso || 'Nunca')}</td>
                        <td class="col-actions">
                            <button class="action-btn" onclick="abrirModalEditarUsuario('${u.id}')">Editar</button>
                            <button class="action-btn action-btn-danger" onclick="eliminarUsuario('${u.id}')">Borrar</button>
                        </td>
                    </tr>
                `).join('');
            }

        function filtrarUsuarios(query) {
            if (!usuariosData) return;
            const q = query.toLowerCase();
            const filtrados = usuariosData.filter(u =>
                (u.username && u.username.toLowerCase().includes(q)) ||
                (u.nombre && u.nombre.toLowerCase().includes(q)) ||
                (u.email && u.email.toLowerCase().includes(q))
            );
            renderTablaUsuarios(filtrados);
        }

        function abrirModalNuevo() {
            // Asumiendo que tu modal de usuarios tiene el ID 'modal-usuario'
            const modal = document.getElementById('modal-usuario');
            if (!modal) {
                console.warn("Falta crear el HTML del modal 'modal-usuario'");
                return;
            }

            // Limpiamos los inputs (ajusta los IDs a los que uses en tu HTML de usuarios)
            document.getElementById('usu-id').value = '';
            document.getElementById('usu-username').value = '';
            document.getElementById('usu-nombre').value = '';
            document.getElementById('usu-email').value = '';
            document.getElementById('usu-rol').value = '3';
            document.getElementById('usu-activo').value = 'true';

            document.getElementById('modal-usuario-title').innerText = 'Nuevo Usuario';
            modal.style.display = 'flex';
        }

        function abrirModalEditarUsuario(id) {
            const usu = usuariosData.find(u => u.id == id);
            if (!usu) return;

            // Ajusta los IDs según tu HTML
            document.getElementById('usu-id').value = usu.id;
            document.getElementById('usu-username').value = usu.username || '';
            document.getElementById('usu-nombre').value = usu.nombre || '';
            document.getElementById('usu-email').value = usu.email || '';
            document.getElementById('usu-rol').value = usu.rol_id || 3;
            document.getElementById('usu-activo').value = usu.activo ? "true" : "false";

            document.getElementById('modal-usuario-title').innerText = 'Editar Usuario';
            document.getElementById('modal-usuario').style.display = 'flex';
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

</script>
</body>
</html>