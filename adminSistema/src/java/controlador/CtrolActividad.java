package controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Actividad;
import modelo.ActividadDAO;

@WebServlet(name = "CtrolActividad", urlPatterns = {"/CtrolActividad"})
public class CtrolActividad extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession sesion = request.getSession(false);
        if (sesion == null || sesion.getAttribute("idUsuario") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String accion = request.getParameter("accion");
        ActividadDAO adao = new ActividadDAO();

        if ("listar".equals(accion) || accion == null) {
            // Mostrar todas las actividades (para administrador)
            request.setAttribute("actividades", adao.listarTodos());
            request.getRequestDispatcher("actividades.jsp").forward(request, response);

        } else if ("crear".equals(accion)) {
            String nombre = new String(request.getParameter("nombre").getBytes("ISO-8859-1"), "UTF-8");
            String desc   = new String(request.getParameter("descripcion").getBytes("ISO-8859-1"), "UTF-8");
            String tipo   = new String(request.getParameter("tipo").getBytes("ISO-8859-1"), "UTF-8");

            Actividad a = new Actividad();
            a.setNombreActividad(nombre);
            a.setDescripcion(desc);
            a.setTipoActividad(tipo);
            adao.agregar(a);
            response.sendRedirect("CtrolActividad?accion=listar&ok=1");

        } else if ("actualizar".equals(accion)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String nombre = new String(request.getParameter("nombre").getBytes("ISO-8859-1"), "UTF-8");
            String desc   = new String(request.getParameter("descripcion").getBytes("ISO-8859-1"), "UTF-8");
            String tipo   = new String(request.getParameter("tipo").getBytes("ISO-8859-1"), "UTF-8");

            Actividad a = new Actividad();
            a.setIdActividad(id);
            a.setNombreActividad(nombre);
            a.setDescripcion(desc);
            a.setTipoActividad(tipo);
            adao.actualizar(a);
            response.sendRedirect("CtrolActividad?accion=listar&ok=2");

        } else if ("eliminar".equals(accion)) {
            int id = Integer.parseInt(request.getParameter("id"));
            adao.eliminar(id);
            response.sendRedirect("CtrolActividad?accion=listar&ok=3");

        } else {
            response.sendRedirect("CtrolActividad?accion=listar");
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
        return "Controlador de actividades (catálogo)";
    }
}