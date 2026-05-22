package controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.AuditoriaModificacionDAO;

@WebServlet(name = "CtrolAuditoria", urlPatterns = {"/CtrolAuditoria"})
public class CtrolAuditoria extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession sesion = request.getSession(false);
        if (sesion == null || sesion.getAttribute("idUsuario") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String accion = request.getParameter("accion");
        AuditoriaModificacionDAO audao = new AuditoriaModificacionDAO();

        if ("filtrar".equals(accion)) {
            String tabla = request.getParameter("tabla");
            if (tabla != null && !tabla.isEmpty()) {
                request.setAttribute("auditorias", audao.listarPorTabla(tabla));
                request.setAttribute("filtro", tabla);
            } else {
                request.setAttribute("auditorias", audao.listarTodos());
                request.setAttribute("filtro", "Todas las tablas");
            }
            request.getRequestDispatcher("auditoria.jsp").forward(request, response);

        } else { // listar todas
            request.setAttribute("auditorias", audao.listarTodos());
            request.setAttribute("filtro", "Todas las tablas");
            request.getRequestDispatcher("auditoria.jsp").forward(request, response);
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
        return "Controlador de auditoría de modificaciones";
    }
}
