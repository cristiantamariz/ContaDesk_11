package Servlets;

import Daos.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    // Instanciar el DAO una sola vez (es stateless, no hay problema)
    private final UsuarioDAO uDAO = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String u = req.getParameter("username");
        String p = req.getParameter("password");

        if (uDAO.validarUsuario(u, p)) {

            // Invalida la sesión anterior y crea una nueva (previene session fixation)
            req.getSession(false);
            HttpSession s = req.getSession(true);
            s.setAttribute("usuarioLogueado", u);

            // Redirigir al panel usando contextPath para que funcione
            // sin importar el nombre del context root de la aplicación
            res.sendRedirect(req.getContextPath() + "/panel/panel.jsp");

        } else {
            req.setAttribute("error", "Usuario o contraseña incorrectos.");
            req.getRequestDispatcher("/index.jsp").forward(req, res);
        }
    }
}