<%@ page contentType="text/html; charset=UTF-8" %>
<%
    // Si ya hay sesión activa, mandar directo al panel
    if (session.getAttribute("usuarioLogueado") != null) {
        response.sendRedirect(request.getContextPath() + "/panel/panel.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ContaDesk | Acceso</title>
    <link rel="stylesheet" href="login.css">
</head>
<body>

<div class="card">

    <div class="brand">
        <div class="brand-name">ContaDesk</div>
        <div class="brand-sub">Sistema  contable</div>
    </div>

    <form action="<%= request.getContextPath() %>/LoginServlet" method="POST">

        <div class="field">
            <label for="username">Usuario</label>
            <input type="text" id="username" name="username"
                   placeholder="nombre.usuario" required autofocus>
        </div>

        <div class="field">
            <label for="password">Contraseña</label>
            <input type="password" id="password" name="password"
                   placeholder="••••••••" required>
        </div>

        <button type="submit">Iniciar sesión</button>

    </form>

    <% if (request.getAttribute("error") != null) { %>
    <div class="error">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>

</div>

</body>
</html>
