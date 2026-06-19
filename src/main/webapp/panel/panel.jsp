<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    if (session.getAttribute("usuarioLogueado") == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
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
        <div class="sidebar-section-label">Despacho</div>
        <a href="#" class="nav-item active" onclick="return loadSection('dashboard', this)">
            <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.7" viewBox="0 0 24 24"><rect x="3" y="3" width="7" height="7" rx="1"/><rect x="14" y="3" width="7" height="7" rx="1"/><rect x="3" y="14" width="7" height="7" rx="1"/><rect x="14" y="14" width="7" height="7" rx="1"/></svg>
            Inicio
        </a>
        <a href="#" class="nav-item" onclick="return loadSection('clientes', this)">
            <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.7" viewBox="0 0 24 24"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg>
            Directorio de Clientes
        </a>
        <a href="#" class="nav-item" onclick="return loadSection('cotizaciones', this)">
            <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.7" viewBox="0 0 24 24"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/><line x1="16" y1="13" x2="8" y2="13"/><line x1="16" y1="17" x2="8" y2="17"/><polyline points="10 9 9 9 8 9"/></svg>
            Cotizador de Servicios
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
        <div class="sidebar-section-label">Sistema</div>
        <a href="#" class="nav-item" onclick="return loadSection('usuarios', this)">
            <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.7" viewBox="0 0 24 24"><circle cx="12" cy="8" r="4"/><path d="M4 20c0-4 3.6-7 8-7s8 3 8 7"/><circle cx="19" cy="8" r="2.5"/><path d="M22 14c-1-.5-2-.7-3-.7"/></svg>
            Usuarios
        </a>
    </div>

    <div class="sidebar-bottom">
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
                + Nueva cotización
            </button>
            <div class="divider"></div>

            <div class="avatar" title="${sessionScope.usuarioLogueado}">
                <c:choose>
                    <c:when test="${fn:length(sessionScope.usuarioLogueado) >= 2}">
                        ${fn:toUpperCase(fn:substring(sessionScope.usuarioLogueado, 0, 2))}
                    </c:when>
                    <c:otherwise>
                        US
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </header>

    <main class="content" id="content-area">

       <%-- ── Dashboard ── --%>
       <div id="section-dashboard">
           <div class="stats-grid">
               <div class="stat-card">
                   <div class="stat-label">Cotizaciones Pendientes</div>
                   <div class="stat-value" id="stat-cot-pendientes">0</div>
                   <div class="stat-sub text-muted">A la espera de aprobación</div>
               </div>
               <div class="stat-card">
                   <div class="stat-label">Declaraciones Próximas</div>
                   <div class="stat-value">${empty dashboardStats.declaracionesProximas ? '0' : dashboardStats.declaracionesProximas}</div>
                   <div class="stat-sub ${dashboardStats.declaracionesProximas > 5 ? 'down' : 'up'}">
                       Vencen en los próximos 7 días
                   </div>
               </div>
              <div class="stat-card">
                  <div class="stat-label">Clientes Activos</div>
                  <div class="stat-value" id="stat-cli-activos">0</div>
                  <div class="stat-sub">Cartera actual</div>
              </div>
               <div class="stat-card">
                   <div class="stat-label">Carga de Trabajo Promedio</div>
                   <div class="stat-value">${empty dashboardStats.cargaPromedio ? '0' : dashboardStats.cargaPromedio} hrs</div>
                   <div class="stat-sub">Por contador asignado</div>
               </div>
           </div>

           <div class="panel mt-4">
               <div class="panel-header">
                   <h3 class="panel-title">Últimas Cotizaciones Generadas</h3>
               </div>
               <div class="panel-body">
                   <table>
                       <thead>
                           <tr>
                               <th>Cliente / Prospecto</th>
                               <th>Régimen</th>
                               <th>Monto Calculado</th>
                               <th>Estatus</th>
                           </tr>
                       </thead>
                       <tbody>
                           <c:forEach var="cotizacion" items="${listaUltimasCotizaciones}">
                               <tr>
                                   <td>${cotizacion.nombreCliente}</td>
                                   <td><span class="badge badge-gray">${cotizacion.regimen}</span></td>
                                   <td><fmt:formatNumber value="${cotizacion.montoTotal}" type="currency" currencySymbol="$"/></td>
                                   <td>
                                       <span class="badge ${cotizacion.estatus == 'Aprobada' ? 'badge-green' : 'badge-yellow'}">
                                           ${cotizacion.estatus}
                                       </span>
                                   </td>
                               </tr>
                           </c:forEach>
                           <c:if test="${empty listaUltimasCotizaciones}">
                               <tr><td colspan="4" class="empty">No hay cotizaciones recientes.</td></tr>
                           </c:if>
                       </tbody>
                   </table>
               </div>
           </div>
       </div>

        <%-- INCLUSIÓN DE FRAGMENTOS REALES --%>
        <jsp:include page="secciones/usuarios.jsp" />
        <jsp:include page="secciones/empleados.jsp" />
        <jsp:include page="secciones/clientes.jsp" />

        <%-- ── Secciones vacías restantes ── --%>
       <jsp:include page="secciones/cotizaciones.jsp" />
        <div id="section-facturas"       style="display:none"><div class="empty-page">Sección Facturas</div></div>
        <jsp:include page="secciones/nomina.jsp" />
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
<script src="${pageContext.request.contextPath}/panel/js/nomina.js"></script>

</body>
</html>