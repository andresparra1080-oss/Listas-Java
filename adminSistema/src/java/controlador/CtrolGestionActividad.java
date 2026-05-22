package controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.GestionActividadDAO;
import modelo.Perfil;
import modelo.PerfilDAO;

@WebServlet(name = "CtrolGestionActividad", urlPatterns = {"/CtrolGestionActividad"})
public class CtrolGestionActividad extends HttpServlet {

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
        GestionActividadDAO gdao = new GestionActividadDAO();

        if ("historialLista".equals(accion)) {
            int idLista = Integer.parseInt(request.getParameter("idLista"));
            request.setAttribute("gestiones", gdao.listarPorLista(idLista));
            request.setAttribute("titulo", "Historial de la lista #" + idLista);
            request.getRequestDispatcher("historial.jsp").forward(request, response);

        } else if ("historialUsuario".equals(accion) || accion == null) {
            // Buscar el perfil del usuario actual
            Perfil perfil = new PerfilDAO().buscarPorUsuario(idUsuario);
            if (perfil != null) {
                request.setAttribute("gestiones", gdao.listarPorPerfil(perfil.getIdPerfil()));
                request.setAttribute("titulo", "Mi historial de actividades");
            } else {
                request.setAttribute("gestiones", null);
                request.setAttribute("titulo", "No tiene perfil creado");
            }
            request.getRequestDispatcher("historial.jsp").forward(request, response);

        } else if ("todas".equals(accion)) {
            // Solo para administradores (podrías agregar verificación de rol)
            request.setAttribute("gestiones", gdao.listarTodos());
            request.setAttribute("titulo", "Historial global");
            request.getRequestDispatcher("historial.jsp").forward(request, response);

        } else {
            response.sendRedirect("CtrolGestionActividad?accion=historialUsuario");
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
        return "Controlador de historial de actividades";
    }
}