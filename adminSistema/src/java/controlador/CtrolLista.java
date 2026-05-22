package controlador;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Lista;
import modelo.ListaDAO;
import modelo.ListaMiembroDAO;
import modelo.Rol;

@WebServlet(name = "CtrolLista", urlPatterns = {"/CtrolLista"})
public class CtrolLista extends HttpServlet {

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

        if ("crear".equals(accion)) {

            String nombre = new String(
                request.getParameter("cnombre").getBytes("ISO-8859-1"), "UTF-8");
            String codigo = UUID.randomUUID().toString()
                                .substring(0, 8).toUpperCase();

            Lista l = new Lista();
            l.setFkCreador(idUsuario);
            l.setListaNombre(nombre);
            l.setCodigoCompartir(codigo);

            ListaDAO ldao = new ListaDAO();
            int status    = ldao.agregar(l);

            if (status > 0) {
                Lista creada = ldao.buscarPorCodigo(codigo);
                // creador entra como Dueño — id_Rol = 1
                new ListaMiembroDAO().agregar(creada.getIdLista(), idUsuario, 1);
                response.sendRedirect("misListas.jsp");
            } else {
                response.sendRedirect("misListas.jsp?error=1");
            }

        } else if ("unirse".equals(accion)) {

            String codigo = request.getParameter("ccodigo").trim().toUpperCase();
            ListaDAO ldao = new ListaDAO();
            Lista lista   = ldao.buscarPorCodigo(codigo);

            if (lista != null) {
                Rol rolExistente = new ListaMiembroDAO()
                        .obtenerRol(lista.getIdLista(), idUsuario);
                if (rolExistente != null) {
                    // ya es miembro — ir directo a la lista
                    response.sendRedirect("listaItems.jsp?id="
                            + lista.getIdLista() + "&error=yaMiembro");
                } else {
                    // entra como Colaborador — id_Rol = 3
                    new ListaMiembroDAO().agregar(lista.getIdLista(), idUsuario, 3);
                    response.sendRedirect("listaItems.jsp?id=" + lista.getIdLista());
                }
            } else {
                response.sendRedirect("misListas.jsp?error=2");
            }

        } else if ("eliminar".equals(accion)) {

            int idLista = Integer.parseInt(request.getParameter("id"));

            // solo el Dueño puede eliminar
            Rol rol = new ListaMiembroDAO().obtenerRol(idLista, idUsuario);
            if (rol == null || rol.getIdRol() != 1) {
                response.sendRedirect("misListas.jsp?error=permiso");
                return;
            }

            new ListaDAO().eliminar(idLista);
            response.sendRedirect("misListas.jsp");

        } else if ("renombrar".equals(accion)) {

            int idLista = Integer.parseInt(request.getParameter("id"));
            String nombre = new String(
                request.getParameter("cnombre").getBytes("ISO-8859-1"), "UTF-8");

            // solo quien puede gestionar puede renombrar
            Rol rol = new ListaMiembroDAO().obtenerRol(idLista, idUsuario);
            if (rol == null || !rol.isPuedeGestionar()) {
                response.sendRedirect("misListas.jsp?error=permiso");
                return;
            }

            Lista l = new Lista();
            l.setIdLista(idLista);
            l.setListaNombre(nombre);
            new ListaDAO().actualizar(l);
            response.sendRedirect("misListas.jsp?ok=1");

        } else {
            response.sendRedirect("misListas.jsp");
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
    public String getServletInfo() { return "Controlador de listas compartidas"; }
}