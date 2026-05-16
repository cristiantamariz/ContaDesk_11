<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ContaDesk | Acceso</title>
    <style>
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }

        body {
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background: #f5f5f4;
            font-family: system-ui, -apple-system, sans-serif;
            color: #1c1917;
        }

        .card {
            background: #ffffff;
            border: 1px solid #e7e5e4;
            border-radius: 10px;
            padding: 40px 36px;
            width: 100%;
            max-width: 380px;
        }

        .brand {
            margin-bottom: 28px;
        }

        .brand-name {
            font-size: 18px;
            font-weight: 600;
            letter-spacing: -0.3px;
            color: #1c1917;
        }

        .brand-sub {
            font-size: 13px;
            color: #78716c;
            margin-top: 3px;
        }

        label {
            display: block;
            font-size: 13px;
            font-weight: 500;
            color: #44403c;
            margin-bottom: 6px;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 9px 12px;
            font-size: 14px;
            border: 1px solid #d6d3d1;
            border-radius: 6px;
            background: #fafaf9;
            color: #1c1917;
            outline: none;
            transition: border-color 0.15s, box-shadow 0.15s;
        }

        input:focus {
            border-color: #2563eb;
            box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.1);
            background: #fff;
        }

        .field {
            margin-bottom: 16px;
        }

        button[type="submit"] {
            width: 100%;
            padding: 10px;
            margin-top: 8px;
            font-size: 14px;
            font-weight: 500;
            background: #1c1917;
            color: #fff;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            transition: background 0.15s;
        }

        button[type="submit"]:hover { background: #292524; }
        button[type="submit"]:active { background: #0c0a09; }

        .error {
            margin-top: 16px;
            padding: 10px 12px;
            background: #fef2f2;
            border: 1px solid #fecaca;
            border-radius: 6px;
            font-size: 13px;
            color: #dc2626;
        }
    </style>
</head>
<body>

<div class="card">

    <div class="brand">
        <div class="brand-name">ContaDesk</div>
        <div class="brand-sub">Sistema contable</div>
    </div>

    <form action="LoginServlet" method="POST">

        <div class="field">
            <label>Usuario</label>
            <input type="text" name="username" placeholder="nombre.usuario" required>
        </div>

        <div class="field">
            <label>Contraseña</label>
            <input type="password" name="password" placeholder="••••••••" required>
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