<%-- 
    Document   : agregarProducto
    Created on : 10/05/2026
    Updated    : 21/05/2026 – Soporta modo edición
    Description: Formulario para agregar o editar un producto.
--%>

<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ page import="modelo.Producto" %>
<%
    // Verificar sesión
    if (session.getAttribute("idUsuario") == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    // Determinar si estamos en modo edición
    Producto producto = (Producto) request.getAttribute("producto");
    String modo = (String) request.getAttribute("modo");
    boolean esEditar = "editar".equals(modo) && producto != null;

    // Parámetro volver (puede venir del formulario)
    String volver = request.getParameter("volver");
    if (volver == null) volver = "catalogo";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title><%= esEditar ? "Editar Producto" : "Agregar Producto" %></title>
    <style>
        body { font-family: Arial, sans-serif; font-size: 13px;
               background: #fff; padding: 20px; }
        .form-container { border: 1px solid #999; padding: 20px;
                          width: 400px; margin: 0 auto; }
        h2 { text-align: center; border-bottom: 1px solid #999;
             padding-bottom: 10px; }
        .form-group { margin-bottom: 12px; }
        label { display: block; margin-bottom: 4px; font-weight: bold; }
        input[type="text"], select {
            width: 100%; padding: 6px; box-sizing: border-box;
            border: 1px solid #ccc; }
        .btn { background: #f0f0f0; border: 1px solid #999; padding: 8px 15px;
               cursor: pointer; width: 100%; font-weight: bold; }
        .btn:hover { background: #e0e0e0; }
        .error-msg { color: red; text-align: center; margin-bottom: 10px; }
        .link-volver { text-align: center; margin-top: 10px; }
    </style>
</head>
<body>
<div class="form-container">
    <h2><%= esEditar ? "Editar Producto" : "Agregar Producto" %></h2>

    <% if ("1".equals(request.getParameter("error"))) { %>
        <div class="error-msg">Error al guardar. Intente de nuevo.</div>
    <% } %>

    <form method="post" action="CtrolProducto">
        <%-- La acción cambia según el modo --%>
        <input type="hidden" name="accion" value="<%= esEditar ? "actualizar" : "agregar" %>"/>
        <input type="hidden" name="volver" value="<%= volver %>"/>

        <% if (esEditar) { %>
            <input type="hidden" name="id" value="<%= producto.getIdProducto() %>"/>
        <% } %>

        <div class="form-group">
            <label>Nombre del producto:</label>
            <input type="text" name="cnombre" placeholder="Ej: Leche entera" required
                   value="<%= esEditar ? producto.getNombre() : "" %>"/>
        </div>

        <div class="form-group">
            <label>Categoría:</label>
            <select name="ccategoria" required>
                <option value="">Seleccione...</option>
                <%
                    String[] categorias = {"Lácteos","Panadería","Carnes","Verduras",
                                           "Frutas","Granos","Bebidas","Despensa","Otros"};
                    String catActual = esEditar ? producto.getCategoria() : "";
                    for (String cat : categorias) {
                        String sel = cat.equals(catActual) ? "selected" : "";
                %>
                <option value="<%= cat %>" <%= sel %>><%= cat %></option>
                <% } %>
            </select>
        </div>

        <button type="submit" class="btn"><%= esEditar ? "Actualizar" : "Guardar" %> Producto</button>
    </form>

    <div class="link-volver">
        <% if (volver.equals("catalogo")) { %>
            <a href="listaProductos.jsp">← Volver al catálogo</a>
        <% } else { %>
            <a href="listaItems.jsp?id=<%= volver %>">← Volver a la lista</a>
        <% } %>
    </div>
</div>
</body>
</html>