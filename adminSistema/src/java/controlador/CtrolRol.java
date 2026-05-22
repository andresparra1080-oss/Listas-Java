package controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.ListaMiembroDAO;
import modelo.Rol;
import modelo.RolDAO;

@WebServlet(name = "CtrolRol", urlPatterns = {"/CtrolRol"})
public class CtrolRol extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession sesion = request.getSession(false);
        if (sesion == null || sesion.getAttribute("idUsuario") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        int    idUsuario = (int) sesion.getAttribute("idUsuario");
        String accion    = request.getParameter("accion");

        if ("crearRol".equals(accion)) {

            String  nombre    = new String(request.getParameter("cnombre").getBytes("ISO-8859-1"), "UTF-8");
            boolean agregar   = "1".equals(request.getParameter("cagregar"));
            boolean marcar    = "1".equals(request.getParameter("cmarcar"));
            boolean eliminar  = "1".equals(request.getParameter("celiminar"));
            boolean gestionar = "1".equals(request.getParameter("cgestionar"));

            Rol r = new Rol();
            r.setRolNombre(nombre);
            r.setPuedeAgregar(agregar);
            r.setPuedeMarcar(marcar);
            r.setPuedeEliminar(eliminar);
            r.setPuedeGestionar(gestionar);

            int status = new RolDAO().agregar(r);
            if (status > 0) {
                response.sendRedirect("listaRoles.jsp?ok=1");
            } else {
                response.sendRedirect("agregarRol.jsp?error=1");
            }

        } else if ("eliminarRol".equals(accion)) {

            int idRol = Integer.parseInt(request.getParameter("id"));
            // no se pueden eliminar los 4 roles base
            if (idRol <= 4) {
                response.sendRedirect("listaRoles.jsp?error=base");
                return;
            }
            new RolDAO().eliminar(idRol);
            response.sendRedirect("listaRoles.jsp");

        } else if ("cambiarRol".equals(accion)) {

            int idLista   = Integer.parseInt(request.getParameter("idLista"));
            int idMiembro = Integer.parseInt(request.getParameter("idMiembro"));
            int idRol     = Integer.parseInt(request.getParameter("idRol"));

            // verificar que quien hace el cambio pueda gestionar
            Rol rolActual = new ListaMiembroDAO().obtenerRol(idLista, idUsuario);
            if (rolActual == null || !rolActual.isPuedeGestionar()) {
                response.sendRedirect("miembros.jsp?id=" + idLista + "&error=permiso");
                return;
            }

            // no se puede tocar al Dueño
            Rol rolMiembro = new ListaMiembroDAO().obtenerRol(idLista, idMiembro);
            if (rolMiembro != null && rolMiembro.getIdRol() == 1) {
                response.sendRedirect("miembros.jsp?id=" + idLista + "&error=dueno");
                return;
            }

            new ListaMiembroDAO().actualizarRol(idLista, idMiembro, idRol);
            response.sendRedirect("miembros.jsp?id=" + idLista + "&ok=1");

        } else if ("expulsar".equals(accion)) {

            int idLista   = Integer.parseInt(request.getParameter("idLista"));
            int idMiembro = Integer.parseInt(request.getParameter("idMiembro"));

            Rol rolActual = new ListaMiembroDAO().obtenerRol(idLista, idUsuario);
            if (rolActual == null || !rolActual.isPuedeGestionar()) {
                response.sendRedirect("miembros.jsp?id=" + idLista + "&error=permiso");
                return;
            }

            Rol rolMiembro = new ListaMiembroDAO().obtenerRol(idLista, idMiembro);
            if (rolMiembro != null && rolMiembro.getIdRol() == 1) {
                response.sendRedirect("miembros.jsp?id=" + idLista + "&error=dueno");
                return;
            }

            new ListaMiembroDAO().eliminar(idLista, idMiembro);
            response.sendRedirect("miembros.jsp?id=" + idLista + "&ok=2");

        } else if ("editarRol".equals(accion)) {

            int     idRol     = Integer.parseInt(request.getParameter("id"));
            String  nombre    = new String(request.getParameter("cnombre").getBytes("ISO-8859-1"), "UTF-8");
            boolean agregar   = "1".equals(request.getParameter("cagregar"));
            boolean marcar    = "1".equals(request.getParameter("cmarcar"));
            boolean eliminar  = "1".equals(request.getParameter("celiminar"));
            boolean gestionar = "1".equals(request.getParameter("cgestionar"));

            // no se editan roles base
            if (idRol <= 4) {
                response.sendRedirect("listaRoles.jsp?error=base");
                return;
            }

            Rol r = new Rol();
            r.setIdRol(idRol);
            r.setRolNombre(nombre);
            r.setPuedeAgregar(agregar);
            r.setPuedeMarcar(marcar);
            r.setPuedeEliminar(eliminar);
            r.setPuedeGestionar(gestionar);

            int status = new RolDAO().actualizar(r);
            if (status > 0) {
                response.sendRedirect("listaRoles.jsp?ok=2");
            } else {
                response.sendRedirect("listaRoles.jsp?error=1");
            }

        } else {
            response.sendRedirect("listaRoles.jsp");
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
    public String getServletInfo() { return "Controlador de roles"; }
}