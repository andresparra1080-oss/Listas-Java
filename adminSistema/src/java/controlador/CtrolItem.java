package controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.ActividadConstantes;
import modelo.GestionActividad;
import modelo.GestionActividadDAO;
import modelo.ListaItem;
import modelo.ListaItemDAO;
import modelo.ListaMiembroDAO;
import modelo.Producto;            // NUEVO
import modelo.ProductoDAO;        // NUEVO
import modelo.Rol;

@WebServlet(name = "CtrolItem", urlPatterns = {"/CtrolItem"})
public class CtrolItem extends HttpServlet {

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
        String sIdLista  = request.getParameter("idLista");

        if (sIdLista == null || sIdLista.isEmpty()) {
            response.sendRedirect("misListas.jsp");
            return;
        }

        int idLista = Integer.parseInt(sIdLista);

        // obtener rol — el filtro ya verificó que es miembro
        Rol rol = new ListaMiembroDAO().obtenerRol(idLista, idUsuario);
        if (rol == null) {
            response.sendRedirect("misListas.jsp");
            return;
        }

        GestionActividadDAO actividadDAO = new GestionActividadDAO();
        ProductoDAO productoDAO = new ProductoDAO();   // NUEVO

        if ("agregar".equals(accion)) {

            if (!rol.isPuedeAgregar()) {
                response.sendRedirect("listaItems.jsp?id=" + idLista + "&error=permiso");
                return;
            }

            String sIdProducto = request.getParameter("cidProducto");
            String sCantidad   = request.getParameter("ccantidad");
            String sUnidad     = request.getParameter("cunidad");

            if (sIdProducto == null || sIdProducto.isEmpty()) {
                response.sendRedirect("listaItems.jsp?id=" + idLista + "&error=1");
                return;
            }

            int    idProducto = Integer.parseInt(sIdProducto);
            double cantidad   = Double.parseDouble(sCantidad);
            String unidad     = new String(sUnidad.getBytes("ISO-8859-1"), "UTF-8");

            ListaItem item = new ListaItem();
            item.setFkLista(idLista);
            item.setFkProducto(idProducto);
            item.setFkAgregadoPor(idUsuario);
            item.setCantidad(cantidad);
            item.setUnidad(unidad);
            item.setComprado(false);

            int status = new ListaItemDAO().agregar(item);

            if (status > 0) {
                // --- NUEVO: obtener nombre del producto ---
                String nombreProducto = "Desconocido";
                Producto prod = productoDAO.buscarPorId(idProducto);
                if (prod != null) {
                    nombreProducto = prod.getNombre();
                }

                GestionActividad act = new GestionActividad();
                act.setFkIdPerfil(idUsuario);
                act.setFkIdActividad(ActividadConstantes.AGREGAR_PRODUCTO);
                act.setIdListaAsociada(idLista);
                act.setDetalle("Producto agregado: " + nombreProducto
                        +  " cantidad: " + cantidad + " " + unidad);
                act.setResultado("exitoso");
                actividadDAO.agregar(act);

                response.sendRedirect("listaItems.jsp?id=" + idLista + "&ok=1");
            } else {
                response.sendRedirect("listaItems.jsp?id=" + idLista + "&error=1");
            }

        } else if ("marcar".equals(accion)) {

            if (!rol.isPuedeMarcar()) {
                response.sendRedirect("listaItems.jsp?id=" + idLista + "&error=permiso");
                return;
            }

            int idItem = Integer.parseInt(request.getParameter("idItem"));
            new ListaItemDAO().marcarComprado(idItem, idUsuario);

            // --- NUEVO: obtener nombre del producto desde el ítem ---
            String nombreProducto = "Desconocido";
            ListaItem itemMarcado = new ListaItemDAO().buscarPorId(idItem);   // necesitas este método
            if (itemMarcado != null) {
                Producto prod = productoDAO.buscarPorId(itemMarcado.getFkProducto());
                if (prod != null) {
                    nombreProducto = prod.getNombre();
                }
            }

            GestionActividad act = new GestionActividad();
            act.setFkIdPerfil(idUsuario);
            act.setFkIdActividad(ActividadConstantes.PRODUCTO_COMPRADO);
            act.setIdListaAsociada(idLista);
            act.setDetalle("Producto marcado como comprado: " + nombreProducto);
            act.setResultado("exitoso");
            actividadDAO.agregar(act);

            response.sendRedirect("listaItems.jsp?id=" + idLista);

        } else if ("eliminar".equals(accion)) {

            if (!rol.isPuedeEliminar()) {
                response.sendRedirect("listaItems.jsp?id=" + idLista + "&error=permiso");
                return;
            }

            int idItem = Integer.parseInt(request.getParameter("idItem"));

            // --- NUEVO: obtener nombre del producto antes de eliminar ---
            String nombreProducto = "Desconocido";
            ListaItem itemEliminar = new ListaItemDAO().buscarPorId(idItem);
            if (itemEliminar != null) {
                Producto prod = productoDAO.buscarPorId(itemEliminar.getFkProducto());
                if (prod != null) {
                    nombreProducto = prod.getNombre();
                }
            }

            new ListaItemDAO().eliminar(idItem);

            GestionActividad act = new GestionActividad();
            act.setFkIdPerfil(idUsuario);
            act.setFkIdActividad(ActividadConstantes.ELIMINAR_PRODUCTO);
            act.setIdListaAsociada(idLista);
            act.setDetalle("Producto eliminado: " + nombreProducto);
            act.setResultado("exitoso");
            actividadDAO.agregar(act);

            response.sendRedirect("listaItems.jsp?id=" + idLista);

        } else {
            response.sendRedirect("listaItems.jsp?id=" + idLista);
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
    public String getServletInfo() { return "Controlador de items de lista"; }
}