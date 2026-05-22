package controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Usuario;
import modelo.UsuarioDAO;

@WebServlet(name = "ControladorUsuario", urlPatterns = {"/ControladorUsuario"})
public class ControladorUsuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String nombre   = new String(request.getParameter("cnombre").getBytes("ISO-8859-1"),   "UTF-8");
        String apellido = new String(request.getParameter("capellido").getBytes("ISO-8859-1"), "UTF-8");
        String email    = new String(request.getParameter("cemail").getBytes("ISO-8859-1"),    "UTF-8");
        String clave    = new String(request.getParameter("cclave").getBytes("ISO-8859-1"),    "UTF-8");

        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setApellido(apellido);
        u.setEmail(email);
        u.setPassword(clave);

        int status = new UsuarioDAO().agregar(u);

        if (status > 0) {
            response.sendRedirect("index.jsp");
        } else {
            response.sendRedirect("registro.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() { return "Controlador de registro de usuario"; }
}