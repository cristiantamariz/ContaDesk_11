<%@ page contentType="text/html; charset=UTF-8" %>
<%
    // checar si hay sesion
    if (session.getAttribute("usuarioLogueado") == null) {
        response.sendRedirect("index.jsp");
        return; // para que no siga cargando la pagina
    }

    // guardar el usuario en una variable
    String u = (String) session.getAttribute("usuarioLogueado");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ContaDesk | Panel</title>
    <style>
        /* EL CSS SE QUEDA EXACTAMENTE IGUAL */
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }

        :root {
            --sidebar-w: 220px;
            --topbar-h: 52px;
            --border: #e7e5e4;
            --text: #1c1917;
            --muted: #78716c;
            --faint: #f5f5f4;
            --accent: #2563eb;
        }

        body {
            font-family: system-ui, -apple-system, sans-serif;
            font-size: 14px;
            color: var(--text);
            background: var(--faint);
            height: 100vh;
            display: flex;
            overflow: hidden;
        }

        .sidebar {
            width: var(--sidebar-w);
            height: 100vh;
            background: #fff;
            border-right: 1px solid var(--border);
            display: flex;
            flex-direction: column;
            flex-shrink: 0;
        }

        .sidebar-brand {
            height: var(--topbar-h);
            display: flex;
            align-items: center;
            padding: 0 18px;
            border-bottom: 1px solid var(--border);
            font-weight: 600;
            font-size: 15px;
            letter-spacing: -0.3px;
            flex-shrink: 0;
        }

        .sidebar-brand span {
            font-size: 12px;
            font-weight: 400;
            color: var(--muted);
            margin-left: 6px;
        }

        .sidebar-section {
            padding: 16px 10px 6px;
        }

        .sidebar-section-label {
            font-size: 11px;
            font-weight: 500;
            color: var(--muted);
            text-transform: uppercase;
            letter-spacing: 0.06em;
            padding: 0 8px;
            margin-bottom: 4px;
        }

        .nav-item {
            display: flex;
            align-items: center;
            gap: 9px;
            padding: 8px 10px;
            border-radius: 6px;
            cursor: pointer;
            color: var(--muted);
            text-decoration: none;
            font-size: 13.5px;
            transition: background 0.1s, color 0.1s;
            border: none;
            background: none;
            width: 100%;
            text-align: left;
        }

        .nav-item:hover { background: var(--faint); color: var(--text); }

        .nav-item.active {
            background: #eff6ff;
            color: var(--accent);
            font-weight: 500;
        }

        .nav-item svg { flex-shrink: 0; opacity: 0.75; }
        .nav-item.active svg { opacity: 1; }

        .sidebar-bottom {
            margin-top: auto;
            padding: 10px;
            border-top: 1px solid var(--border);
        }

        .main {
            flex: 1;
            display: flex;
            flex-direction: column;
            overflow: hidden;
        }

        .topbar {
            height: var(--topbar-h);
            background: #fff;
            border-bottom: 1px solid var(--border);
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 0 22px;
            flex-shrink: 0;
            gap: 12px;
        }

        .topbar-left {
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .page-title { font-size: 14px; font-weight: 500; }
        .breadcrumb { font-size: 13px; color: var(--muted); }
        .breadcrumb-sep { margin: 0 5px; }

        .topbar-right {
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .btn {
            display: inline-flex;
            align-items: center;
            gap: 6px;
            padding: 6px 12px;
            font-size: 13px;
            border-radius: 6px;
            border: 1px solid var(--border);
            background: #fff;
            color: var(--text);
            cursor: pointer;
            transition: background 0.12s;
        }

        .btn:hover { background: var(--faint); }

        .btn-primary {
            background: var(--text);
            color: #fff;
            border-color: var(--text);
        }

        .btn-primary:hover { background: #292524; }

        .avatar {
            width: 30px;
            height: 30px;
            border-radius: 50%;
            background: #e7e5e4;
            color: var(--muted);
            font-size: 12px;
            font-weight: 600;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
        }

        .divider { width: 1px; height: 20px; background: var(--border); }

        .content {
            flex: 1;
            overflow-y: auto;
            padding: 24px 28px;
        }

        .stats-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 14px;
            margin-bottom: 24px;
        }

        .stat-card {
            background: #fff;
            border: 1px solid var(--border);
            border-radius: 8px;
            padding: 16px 18px;
        }

        .stat-label { font-size: 12px; color: var(--muted); margin-bottom: 6px; }
        .stat-value { font-size: 22px; font-weight: 600; letter-spacing: -0.5px; color: var(--text); }
        .stat-sub { font-size: 12px; color: var(--muted); margin-top: 4px; }
        .stat-sub.up { color: #16a34a; }
        .stat-sub.down { color: #dc2626; }

        .panels {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 14px;
        }

        .panel {
            background: #fff;
            border: 1px solid var(--border);
            border-radius: 8px;
            overflow: hidden;
        }

        .panel-header {
            padding: 14px 18px;
            border-bottom: 1px solid var(--border);
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        .panel-title { font-size: 13.5px; font-weight: 500; }
        .panel-body { padding: 0; }

        table { width: 100%; border-collapse: collapse; font-size: 13px; }
        thead th {
            padding: 10px 18px;
            text-align: left;
            font-size: 11.5px;
            font-weight: 500;
            color: var(--muted);
            background: var(--faint);
            border-bottom: 1px solid var(--border);
            text-transform: uppercase;
            letter-spacing: 0.04em;
        }
        tbody td { padding: 11px 18px; border-bottom: 1px solid var(--border); color: var(--text); }
        tbody tr:last-child td { border-bottom: none; }
        tbody tr:hover td { background: #fafaf9; }

        .badge {
            display: inline-block;
            padding: 2px 8px;
            border-radius: 20px;
            font-size: 11.5px;
            font-weight: 500;
        }

        .badge-green  { background: #f0fdf4; color: #16a34a; }
        .badge-yellow { background: #fefce8; color: #ca8a04; }
        .badge-red    { background: #fef2f2; color: #dc2626; }
        .badge-gray   { background: #f5f5f4; color: #78716c; }

        .list-item {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 12px 18px;
            border-bottom: 1px solid var(--border);
            font-size: 13px;
        }

        .list-item:last-child { border-bottom: none; }
        .list-item-label { color: var(--text); }
        .list-item-meta  { color: var(--muted); font-size: 12px; }
        .list-item-amount { font-weight: 500; }

        .empty { padding: 40px 18px; text-align: center; color: var(--muted); font-size: 13px; }
    </style>
</head>
<body>

<aside class="sidebar">

    <div class="sidebar-brand">
        ContaDesk <span>ERP</span>
    </div>

    <div class="sidebar-section">
        <div class="sidebar-section-label">Principal</div>
        <a href="#" class="nav-item active" onclick="loadSection('dashboard', this)">
            <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.7" viewBox="0 0 24 24"><rect x="3" y="3" width="7" height="7" rx="1"/><rect x="14" y="3" width="7" height="7" rx="1"/><rect x="3" y="14" width="7" height="7" rx="1"/><rect x="14" y="14" width="7" height="7" rx="1"/></svg>
            Inicio
        </a>
        <a href="#" class="nav-item" onclick="loadSection('clientes', this)">
            <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.7" viewBox="0 0 24 24"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg>
            Clientes
        </a>
        <a href="#" class="nav-item" onclick="loadSection('facturas', this)">
            <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.7" viewBox="0 0 24 24"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/><line x1="16" y1="13" x2="8" y2="13"/><line x1="16" y1="17" x2="8" y2="17"/><polyline points="10 9 9 9 8 9"/></svg>
            Facturas
        </a>
    </div>

    <div class="sidebar-section">
        <div class="sidebar-section-label">Nómina</div>
        <a href="#" class="nav-item" onclick="loadSection('empleados', this)">
            <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.7" viewBox="0 0 24 24"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
            Empleados
        </a>
        <a href="#" class="nav-item" onclick="loadSection('nomina', this)">
            <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.7" viewBox="0 0 24 24"><rect x="2" y="5" width="20" height="14" rx="2"/><line x1="2" y1="10" x2="22" y2="10"/></svg>
            Nómina
        </a>
    </div>

    <div class="sidebar-section">
        <div class="sidebar-section-label">Contabilidad</div>
        <a href="#" class="nav-item" onclick="loadSection('polizas', this)">
            <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.7" viewBox="0 0 24 24"><line x1="18" y1="20" x2="18" y2="10"/><line x1="12" y1="20" x2="12" y2="4"/><line x1="6" y1="20" x2="6" y2="14"/></svg>
            Pólizas
        </a>
        <a href="#" class="nav-item" onclick="loadSection('declaraciones', this)">
            <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.7" viewBox="0 0 24 24"><circle cx="12" cy="12" r="10"/><path d="M12 8v4l3 3"/></svg>
            Declaraciones
        </a>
        <a href="#" class="nav-item" onclick="loadSection('cuentas', this)">
            <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.7" viewBox="0 0 24 24"><line x1="8" y1="6" x2="21" y2="6"/><line x1="8" y1="12" x2="21" y2="12"/><line x1="8" y1="18" x2="21" y2="18"/><line x1="3" y1="6" x2="3.01" y2="6"/><line x1="3" y1="12" x2="3.01" y2="12"/><line x1="3" y1="18" x2="3.01" y2="18"/></svg>
            Catálogo de cuentas
        </a>
    </div>

    <div class="sidebar-bottom">
        <a href="#" class="nav-item" onclick="loadSection('config', this)">
            <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.7" viewBox="0 0 24 24"><circle cx="12" cy="12" r="3"/><path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1-2.83 2.83l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-4 0v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83-2.83l.06-.06A1.65 1.65 0 0 0 4.68 15a1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1 0-4h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 2.83-2.83l.06.06A1.65 1.65 0 0 0 9 4.68a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 4 0v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 2.83l-.06.06A1.65 1.65 0 0 0 19.4 9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 0 4h-.09a1.65 1.65 0 0 0-1.51 1z"/></svg>
            Configuración
        </a>
        <a href="LogoutServlet" class="nav-item">
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
                <%
                    if(u.length() >= 2) {
                        out.print(u.substring(0, 2).toUpperCase());
                    } else {
                        out.print("US");
                    }
                %>
            </div>
        </div>
    </header>

    <main class="content" id="content-area">

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

            <div class="panels">
                <div class="panel">
                    <div class="panel-header">
                        <span class="panel-title">Últimas facturas</span>
                        <a href="#" class="btn" onclick="loadSection('facturas', null)">Ver todas</a>
                    </div>
                    <div class="panel-body">
                        <table>
                            <thead>
                            <tr>
                                <th>Folio</th>
                                <th>Cliente</th>
                                <th>Total</th>
                                <th>Estado</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr><td>F-0084</td><td>Grupo Alfa S.A.</td><td>$12,400</td><td><span class="badge badge-green">Pagada</span></td></tr>
                            <tr><td>F-0083</td><td>Inversiones del Norte</td><td>$8,750</td><td><span class="badge badge-yellow">Pendiente</span></td></tr>
                            <tr><td>F-0082</td><td>Comercial Torres</td><td>$3,200</td><td><span class="badge badge-green">Pagada</span></td></tr>
                            <tr><td>F-0081</td><td>Servicios Pérez SC</td><td>$19,000</td><td><span class="badge badge-red">Vencida</span></td></tr>
                            <tr><td>F-0080</td><td>Logística MX</td><td>$5,600</td><td><span class="badge badge-green">Pagada</span></td></tr>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="panel">
                    <div class="panel-header">
                        <span class="panel-title">Declaraciones próximas</span>
                    </div>
                    <div class="panel-body">
                        <div class="list-item">
                            <div>
                                <div class="list-item-label">IVA — Junio 2026</div>
                                <div class="list-item-meta">Vence 17 jul · SAT</div>
                            </div>
                            <span class="badge badge-yellow">Pendiente</span>
                        </div>
                        <div class="list-item">
                            <div>
                                <div class="list-item-label">ISR — Junio 2026</div>
                                <div class="list-item-meta">Vence 17 jul · SAT</div>
                            </div>
                            <span class="badge badge-yellow">Pendiente</span>
                        </div>
                        <div class="list-item">
                            <div>
                                <div class="list-item-label">IMSS — Junio 2026</div>
                                <div class="list-item-meta">Vence 23 jun · IMSS</div>
                            </div>
                            <span class="badge badge-green">Presentada</span>
                        </div>
                        <div class="list-item">
                            <div>
                                <div class="list-item-label">INFONAVIT — Junio 2026</div>
                                <div class="list-item-meta">Vence 25 jun</div>
                            </div>
                            <span class="badge badge-green">Presentada</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="section-clientes"      style="display:none"><div class="empty">Sección Clientes — contenido del servidor</div></div>
        <div id="section-facturas"      style="display:none"><div class="empty">Sección Facturas — contenido del servidor</div></div>
        <div id="section-empleados"     style="display:none"><div class="empty">Sección Empleados — contenido del servidor</div></div>
        <div id="section-nomina"        style="display:none"><div class="empty">Sección Nómina — contenido del servidor</div></div>
        <div id="section-polizas"       style="display:none"><div class="empty">Sección Pólizas — contenido del servidor</div></div>
        <div id="section-declaraciones" style="display:none"><div class="empty">Sección Declaraciones — contenido del servidor</div></div>
        <div id="section-cuentas"       style="display:none"><div class="empty">Sección Catálogo de cuentas — contenido del servidor</div></div>
        <div id="section-config"        style="display:none"><div class="empty">Sección Configuración — contenido del servidor</div></div>

    </main>
</div>

<script>
    const labels = {
        dashboard:    { title: 'Inicio',                accion: '+ Nueva factura',    fn: 'facturas'     },
        clientes:     { title: 'Clientes',              accion: '+ Nuevo cliente',    fn: 'clientes'     },
        facturas:     { title: 'Facturas',              accion: '+ Nueva factura',    fn: 'facturas'     },
        empleados:    { title: 'Empleados',             accion: '+ Nuevo empleado',   fn: 'empleados'    },
        nomina:       { title: 'Nómina',                accion: '+ Nuevo periodo',    fn: 'nomina'       },
        polizas:      { title: 'Pólizas',               accion: '+ Nueva póliza',     fn: 'polizas'      },
        declaraciones:{ title: 'Declaraciones fiscales',accion: '+ Nueva declaración',fn: 'declaraciones'},
        cuentas:      { title: 'Catálogo de cuentas',   accion: '+ Nueva cuenta',     fn: 'cuentas'      },
        config:       { title: 'Configuración',         accion: null,                 fn: null           }
    };

    let currentSection = 'dashboard';

    function loadSection(id, el) {
        document.getElementById('section-' + currentSection).style.display = 'none';

        document.querySelectorAll('.nav-item').forEach(n => n.classList.remove('active'));

        const target = document.getElementById('section-' + id);
        if (target) { target.style.display = 'block'; }

        if (el) { el.classList.add('active'); }

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
        return false;
    }

    function accionPrincipal() {
        const cfg = labels[currentSection];
        if (cfg && cfg.fn) {
            alert('Acción: ' + cfg.accion);
        }
    }
</script>

</body>
</html>