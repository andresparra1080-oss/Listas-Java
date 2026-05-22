package controlador;

import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Perfil;
import modelo.PerfilDAO;

@WebServlet(name = "CtrolPerfil", urlPatterns = {"/CtrolPerfil"})
public class CtrolPerfil extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession sesion = request.getSession(false);
        if (sesion == null || sesion.getAttribute("idUsuario") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        int idUsuario = (int) sesion.getAttribute("idUsuario");
        String accion = request.getParameter("accion");
        PerfilDAO pdao = new PerfilDAO();

        if ("ver".equals(accion) || accion == null) {
            // Cargar perfil del usuario y mostrarlo en formulario
            Perfil perfil = pdao.buscarPorUsuario(idUsuario);
            if (perfil != null) {
                request.setAttribute("perfil", perfil);
                request.getRequestDispatcher("perfil.jsp").forward(request, response);
            } else {
                // Si no tiene perfil, redirigir a crear
                response.sendRedirect("perfil.jsp?crear=1");
            }

        } else if ("actualizar".equals(accion)) {
            // Recoger datos del formulario (codificación UTF-8)
            String telefono   = new String(request.getParameter("telefono").getBytes("ISO-8859-1"), "UTF-8");
            String direccion  = new String(request.getParameter("direccion").getBytes("ISO-8859-1"), "UTF-8");
            String fechaStr   = request.getParameter("fecha_nacimiento"); // formato yyyy-mm-dd

            Perfil perfil = pdao.buscarPorUsuario(idUsuario);
            if (perfil == null) {
                // Si no existe, lo creamos
                perfil = new Perfil();
                perfil.setFkIdUsuario(idUsuario);
                perfil.setFechaNacimiento(Date.valueOf(fechaStr));
                perfil.setTelefono(telefono);
                perfil.setDireccion(direccion);
                pdao.agregar(perfil);
            } else {
                perfil.setFechaNacimiento(Date.valueOf(fechaStr));
                perfil.setTelefono(telefono);
                perfil.setDireccion(direccion);
                pdao.actualizar(perfil);
            }
            response.sendRedirect("CtrolPerfil?accion=ver&ok=1");

        } else {
            response.sendRedirect("CtrolPerfil?accion=ver");
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
    public String getServletInfo() {
        return "Controlador de perfil de usuario";
    }
}