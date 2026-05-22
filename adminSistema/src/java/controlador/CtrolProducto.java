package controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Producto;
import modelo.ProductoDAO;

@WebServlet(name = "CtrolProducto", urlPatterns = {"/CtrolProducto"})
public class CtrolProducto extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession sesion = request.getSession(false);
        if (sesion == null || sesion.getAttribute("idUsuario") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String accion = request.getParameter("accion");
        ProductoDAO pdao = new ProductoDAO();

        // Parámetro volver indica desde dónde se llamó (para redirigir al final)
        String volver = request.getParameter("volver");
        if (volver == null) volver = "catalogo";  // por defecto vuelve al catálogo

        if ("agregar".equals(accion)) {
            // --- Crear nuevo producto ---
            String nombre = new String(request.getParameter("cnombre").getBytes("ISO-8859-1"), "UTF-8");
            String categoria = new String(request.getParameter("ccategoria").getBytes("ISO-8859-1"), "UTF-8");

            Producto p = new Producto();
            p.setNombre(nombre);
            p.setCategoria(categoria);
            int status = pdao.agregar(p);

            if (volver.equals("catalogo")) {
                response.sendRedirect("listaProductos.jsp?ok=agregado");
            } else {
                // Si se llamó desde una lista, volver a ella (por si se quiere reutilizar)
                response.sendRedirect("listaItems.jsp?id=" + volver);
            }

        } else if ("editar".equals(accion)) {
            // --- Cargar producto para editar en el formulario ---
            int id = Integer.parseInt(request.getParameter("id"));
            Producto prod = pdao.buscarPorId(id);
            if (prod != null) {
                request.setAttribute("producto", prod);
                request.setAttribute("modo", "editar");
                request.getRequestDispatcher("agregarProducto.jsp").forward(request, response);
            } else {
                response.sendRedirect("listaProductos.jsp?error=noexiste");
            }

        } else if ("actualizar".equals(accion)) {
            // --- Guardar cambios de edición ---
            int id = Integer.parseInt(request.getParameter("id"));
            String nombre = new String(request.getParameter("cnombre").getBytes("ISO-8859-1"), "UTF-8");
            String categoria = new String(request.getParameter("ccategoria").getBytes("ISO-8859-1"), "UTF-8");

            Producto p = new Producto();
            p.setIdProducto(id);
            p.setNombre(nombre);
            p.setCategoria(categoria);
            int status = pdao.actualizar(p);

            if (status > 0) {
                response.sendRedirect("listaProductos.jsp?ok=1");
            } else {
                response.sendRedirect("listaProductos.jsp?error=1");
            }

        } else if ("eliminar".equals(accion)) {
            // --- Eliminar producto ---
            int id = Integer.parseInt(request.getParameter("id"));
            int status = pdao.eliminar(id);

            if (status > 0) {
                response.sendRedirect("listaProductos.jsp?ok=2");
            } else {
                response.sendRedirect("listaProductos.jsp?error=1");
            }

        } else if ("listar".equals(accion)) {
            // --- Listar productos (con filtros opcionales) ---
            // Esta acción puede ser usada si quieres un controlador puro,
            // pero hemos optado por que listaProductos.jsp llame directamente al DAO.
            // La dejamos por si se requiere.
            String categoria = request.getParameter("categoria");
            String nombre = request.getParameter("nombre");
            request.setAttribute("productos", pdao.listarFiltrado(categoria, nombre));
            request.getRequestDispatcher("listaProductos.jsp").forward(request, response);

        } else {
            // Acción por defecto: redirigir al catálogo
            response.sendRedirect("listaProductos.jsp");
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
        return "Controlador de productos (CRUD completo)";
    }
}