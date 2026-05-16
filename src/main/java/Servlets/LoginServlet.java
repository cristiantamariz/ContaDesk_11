package Servlets;

import Daos.UsuarioDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    UsuarioDAO uDAO = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        // los datos del jsp
        String u = req.getParameter("username");
        String p = req.getParameter("password");

        if (uDAO.validarUsuario(u, p)) {

            HttpSession s = req.getSession();
            s.setAttribute("usuarioLogueado", u);

            res.sendRedirect("panel/panel.jsp");

        } else {
            // si falla
            req.setAttribute("error", "error de datos");
            req.getRequestDispatcher("index.jsp").forward(req, res);
        }
    }
}