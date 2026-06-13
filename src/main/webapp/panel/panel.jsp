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

        <%-- INCLUSIÓN DE FRAGMENTOS --%>
        <jsp:include page="secciones/usuarios.jsp" />
        <jsp:include page="secciones/empleados.jsp" />
        <jsp:include page="secciones/clientes.jsp" />

        <%-- ── Secciones vacías (Clientes, Facturas, Nómina, etc.) ── --%>
        <div id="section-clientes"       style="display:none"><div class="empty-page">Sección Clientes</div></div>
        <div id="section-facturas"       style="display:none"><div class="empty-page">Sección Facturas</div></div>
        <div id="section-nomina"         style="display:none"><div class="empty-page">Sección Nómina</div></div>
        <div id="section-polizas"        style="display:none"><div class="empty-page">Sección Pólizas</div></div>
        <div id="section-declaraciones"  style="display:none"><div class="empty-page">Sección Declaraciones</div></div>
        <div id="section-cuentas"        style="display:none"><div class="empty-page">Sección Catálogo de cuentas</div></div>
        <div id="section-config"         style="display:none"><div class="empty-page">Sección Configuración</div></div>

    </main>
</div>

<script>
    const CTX = '<%= request.getContextPath() %>';
</script>
<script src="<%= request.getContextPath() %>/panel/js/app.js"></script>

</body>
</html>