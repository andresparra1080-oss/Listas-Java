<%-- 
    Document   : listaItems
    Created on : 10/05/2026, 4:07:08 p. m.
    Author     : MSI
--%>
<%@ page import="java.util.List" %>
<%@ page import="modelo.ListaItem" %>
<%@ page import="modelo.ListaItemDAO" %>
<%@ page import="modelo.Lista" %>
<%@ page import="modelo.ListaDAO" %>
<%@ page import="modelo.Producto" %>
<%@ page import="modelo.ProductoDAO" %>
<%@ page import="modelo.ListaMiembroDAO" %>
<%@ page import="modelo.Rol" %>
<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%
    // el filtro ya garantizó sesión y membresía
    int idUsuario = (int) session.getAttribute("idUsuario");
    int idLista = Integer.parseInt(request.getParameter("id"));

    ListaMiembroDAO mDAO = new ListaMiembroDAO();
    Rol miRol = mDAO.obtenerRol(idLista, idUsuario);
    boolean puedeAgregar = miRol != null && miRol.isPuedeAgregar();
    boolean puedeMarcar = miRol != null && miRol.isPuedeMarcar();
    boolean puedeEliminar = miRol != null && miRol.isPuedeEliminar();
    boolean puedeGestionar = miRol != null && miRol.isPuedeGestionar();

    ListaDAO ldao = new ListaDAO();
    Lista lista = ldao.buscarPorId(idLista);

    ListaItemDAO itemDAO = new ListaItemDAO();
    List<ListaItem> items = itemDAO.listarPorLista(idLista);

    ProductoDAO pdao = new ProductoDAO();
    List<Producto> productos = pdao.listarTodos();

    int totalItems = items != null ? items.size() : 0;
    int totalComprados = 0;
    if (items != null) {
        for (ListaItem it : items) {
            if (it.isComprado()) {
                totalComprados++;
            }
        }
    }
    int totalPendientes = totalItems - totalComprados;
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <title><%= lista != null ? lista.getListaNombre() : "Lista"%></title>
        <style>
            body {
                font-family: Arial, sans-serif;
                font-size: 13px;
                background: #fff;
                padding: 20px;
            }
            #header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                border-bottom: 1px solid #999;
                padding-bottom: 10px;
                margin-bottom: 16px;
            }
            #header .info {
                font-size: 12px;
                color: #666;
                margin-top: 4px;
            }
            .resumen {
                display: flex;
                gap: 20px;
                margin-bottom: 16px;
                font-size: 12px;
            }
            .resumen span {
                border: 1px solid #ddd;
                padding: 4px 10px;
                background: #f9f9f9;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 10px;
            }
            th, td {
                border: 1px solid #999;
                padding: 8px;
                text-align: left;
            }
            th {
                background: #f0f0f0;
            }
            tr:nth-child(even) {
                background: #f9f9f9;
            }
            .comprado td {
                color: #999;
                text-decoration: line-through;
            }
            .btn {
                background: #f0f0f0;
                border: 1px solid #999;
                padding: 4px 8px;
                text-decoration: none;
                color: #000;
            }
            .btn:hover {
                background: #e0e0e0;
            }
            .btn-danger {
                border-color: #c00;
                color: #c00;
            }
            .btn-ok     {
                border-color: #090;
                color: #090;
            }
            .btn-small  {
                padding: 3px 7px;
                font-size: 12px;
            }
            .form-agregar {
                border: 1px solid #999;
                padding: 15px;
                margin-bottom: 16px;
            }
            .form-agregar h3 {
                margin: 0 0 10px 0;
            }
            table.form-table {
                border: none;
                margin: 0;
            }
            table.form-table td {
                border: none;
                padding: 4px 8px 4px 0;
            }
            table.form-table select,
            table.form-table input {
                padding: 5px;
                border: 1px solid #ccc;
                width: 100%;
            }
            .error-msg {
                color: red;
                margin-bottom: 10px;
            }
            .ok-msg    {
                color: green;
                margin-bottom: 10px;
            }
            .codigo {
                font-family: monospace;
                background: #f5f5f5;
                padding: 2px 6px;
                border: 1px solid #ddd;
            }
            .rol-tag {
                font-size: 11px;
                color: #666;
                border: 1px solid #ddd;
                padding: 1px 5px;
                background: #f9f9f9;
            }
            .sin-permiso {
                color: #999;
                font-style: italic;
                font-size: 12px;
                padding: 8px;
                border: 1px dashed #ddd;
                margin-bottom: 16px;
            }
            .link-nuevo {
                font-size: 12px;
                margin-top: 8px;
            }
        </style>
    </head>
    <body>

        <div id="header">
            <div>
                <a href="misListas.jsp">← Mis listas</a>
                &nbsp;|&nbsp;
                <strong><%= lista != null ? lista.getListaNombre() : ""%></strong>
                <div class="info">
                    Código: <span class="codigo">
                        <%= lista != null ? lista.getCodigoCompartir() : ""%>
                    </span>
                    &nbsp;·&nbsp;
                    Mi rol: <span class="rol-tag">
                        <%= miRol != null ? miRol.getRolNombre() : ""%>
                    </span>
                    <% if (puedeGestionar) {%>
                    &nbsp;·&nbsp;
                    <a href="miembros.jsp?id=<%= idLista%>"
                       class="btn btn-small">Gestionar miembros</a>
                    <% } %>
                </div>
            

        <%
            String error = request.getParameter("error");
            String ok = request.getParameter("ok");
        %>
        <% if ("1".equals(error)) { %>
        <div class="error-msg">Error al agregar el producto.</div>
        <% } else if ("permiso".equals(error)) { %>
        <div class="error-msg">No tienes permiso para hacer esa acción.</div>
        <% } %>
        <% if ("1".equals(ok)) { %>
        <div class="ok-msg">Producto agregado correctamente.</div>
        <% }%>

        <div class="resumen">
            <span>Total: <strong><%= totalItems%></strong></span>
            <span>Pendientes: <strong><%= totalPendientes%></strong></span>
            <span>Comprados: <strong><%= totalComprados%></strong></span>
        </div>

        <% if (puedeAgregar) { %>
        <div class="form-agregar">
            <h3>Agregar producto</h3>
            <% if (productos == null || productos.isEmpty()) {%>
            <p style="color:#c00;">
                No hay productos.
                <a href="agregarProducto.jsp?volver=<%= idLista%>">
                    Crear producto nuevo
                </a>
            </p>
            <% } else {%>
            <form method="post" action="CtrolItem">
                <input type="hidden" name="accion"  value="agregar"/>
                <input type="hidden" name="idLista" value="<%= idLista%>"/>
                <table class="form-table">
                    <tr>
                        <td><label>Producto:</label></td>
                        <td><label>Cantidad:</label></td>
                        <td><label>Unidad:</label></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td style="width:220px;">
                            <select name="cidProducto" required>
                                <option value="">Seleccione...</option>
                                <%
                                    String catActual = "";
                                    for (Producto p : productos) {
                                        if (!p.getCategoria().equals(catActual)) {
                                            catActual = p.getCategoria();
                                %>
                                <option value="" disabled>
                                    ── <%= catActual%> ──
                                </option>
                                <%      }%>
                                <option value="<%= p.getIdProducto()%>">
                                    <%= p.getNombre()%>
                                </option>
                                <%  }%>
                            </select>
                        </td>
                        <td style="width:90px;">
                            <input type="number" name="ccantidad"
                                   value="1" min="0.1" step="0.1" required/>
                        </td>
                        <td style="width:90px;">
                            <input type="text" name="cunidad"
                                   value="unidad" required/>
                        </td>
                        <td>
                            <button type="submit" class="btn">Agregar</button>
                        </td>
                    </tr>
                </table>
            </form>
            <div class="link-nuevo">
                ¿No encuentras el producto?
                <a href="agregarProducto.jsp?volver=<%= idLista%>">
                    Crear producto nuevo
                </a>
            </div>
            <% } %>
        </div>
        <% } else {%>
        <div class="sin-permiso">
            Tu rol (<%= miRol != null ? miRol.getRolNombre() : ""%>)
            solo permite ver esta lista.
        </div>
        <% } %>

        <table>
            <thead>
                <tr>
                    <th>Producto</th>
                    <th>Cantidad</th>
                    <th>Unidad</th>
                    <th>Agregado por</th>
                    <th>Estado</th>
                        <% if (puedeMarcar || puedeEliminar) { %>
                    <th>Acción</th>
                        <% } %>
                </tr>
            </thead>
            <tbody>
                <% if (items == null || items.isEmpty()) { %>
                <tr>
                    <td colspan="6" style="text-align:center;">
                        La lista está vacía.
                        <% if (puedeAgregar) { %>¡Agrega el primer producto!<% } %>
                    </td>
                </tr>
                <% } else {
            for (ListaItem item : items) {%>
                <tr class="<%= item.isComprado() ? "comprado" : ""%>">
                    <td><%= item.getNombreProducto()%></td>
                    <td><%= item.getCantidad()%></td>
                    <td><%= item.getUnidad()%></td>
                    <td><%= item.getNombreAgregadoPor()%></td>
                    <td><%= item.isComprado() ? "✔ Comprado" : "Pendiente"%></td>
                    <% if (puedeMarcar || puedeEliminar) { %>
                    <td style="white-space:nowrap;">
                        <% if (!item.isComprado() && puedeMarcar) {%>
                        <a href="CtrolItem?accion=marcar&idLista=<%= idLista%>&idItem=<%= item.getIdItem()%>"
                           class="btn btn-ok btn-small">✔ Marcar</a>
                        <% } %>
                        <% if (puedeEliminar) {%>
                        <a href="CtrolItem?accion=eliminar&idLista=<%= idLista%>&idItem=<%= item.getIdItem()%>"
                           class="btn btn-danger btn-small"
                           onclick="return confirm('¿Quitar este producto?')">
                            Quitar
                        </a>
                        <% } %>
                    </td>
                    <% } %>
                </tr>
                <%     }
            }%>
            </tbody>
        </table>

    </body>
</html>