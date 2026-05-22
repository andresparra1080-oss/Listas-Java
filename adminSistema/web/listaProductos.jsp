<%-- 
    Document   : listaProductos
    Created on : 21/05/2026
    Description: Catálogo de productos con búsqueda, filtro, editar y eliminar
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ page import="java.util.List, modelo.Producto, modelo.ProductoDAO" %>
<%
    if (session.getAttribute("idUsuario") == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    String categoria = request.getParameter("categoria");
    String nombreBusqueda = request.getParameter("nombre");
    ProductoDAO pdao = new ProductoDAO();
    List<Producto> productos = pdao.listarFiltrado(categoria, nombreBusqueda);

    String ok = request.getParameter("ok");
    String error = request.getParameter("error");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Catálogo de Productos</title>
    <style>
        body { font-family: Arial, sans-serif; font-size: 13px; background: #fff; padding: 20px; }
        h2 { border-bottom: 1px solid #999; padding-bottom: 10px; }
        .msg { color: green; font-weight: bold; margin-bottom: 10px; }
        .err { color: red; font-weight: bold; margin-bottom: 10px; }
        .filtros { margin-bottom: 15px; display: flex; gap: 10px; flex-wrap: wrap; align-items: center; }
        .filtros select, .filtros input[type="text"] { padding: 4px; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background: #f0f0f0; }
        .btn-sm { background: #f8f9fa; border: 1px solid #999; padding: 4px 8px; cursor: pointer; font-size: 11px; text-decoration: none; color: black; display: inline-block; }
        .btn-sm:hover { background: #e2e6ea; }
        .btn-del { color: red; }
    </style>
</head>
<body>
<h2>Catálogo de Productos</h2>

<% if ("1".equals(ok)) { %><div class="msg">Producto actualizado correctamente.</div>
<% } else if ("2".equals(ok)) { %><div class="msg">Producto eliminado.</div>
<% } else if ("1".equals(error)) { %><div class="err">Error al realizar la operación.</div>
<% } %>

<div class="filtros">
    <form method="get" action="listaProductos.jsp" style="display:flex; gap:8px; align-items:center; width:100%;">
        <label>Categoría:</label>
        <select name="categoria">
            <option value="">Todas</option>
            <option value="Lácteos" <%= "Lácteos".equals(categoria) ? "selected" : "" %>>Lácteos</option>
            <option value="Panadería" <%= "Panadería".equals(categoria) ? "selected" : "" %>>Panadería</option>
            <option value="Carnes" <%= "Carnes".equals(categoria) ? "selected" : "" %>>Carnes</option>
            <option value="Verduras" <%= "Verduras".equals(categoria) ? "selected" : "" %>>Verduras</option>
            <option value="Frutas" <%= "Frutas".equals(categoria) ? "selected" : "" %>>Frutas</option>
            <option value="Granos" <%= "Granos".equals(categoria) ? "selected" : "" %>>Granos</option>
            <option value="Bebidas" <%= "Bebidas".equals(categoria) ? "selected" : "" %>>Bebidas</option>
            <option value="Despensa" <%= "Despensa".equals(categoria) ? "selected" : "" %>>Despensa</option>
            <option value="Otros" <%= "Otros".equals(categoria) ? "selected" : "" %>>Otros</option>
        </select>

        <label>Buscar por nombre:</label>
        <input type="text" name="nombre" value="<%= nombreBusqueda != null ? nombreBusqueda : "" %>" placeholder="Ej: leche"/>

        <button type="submit" class="btn-sm">🔍 Filtrar</button>
        <a href="listaProductos.jsp" class="btn-sm">Limpiar</a>
    </form>
</div>

<table>
    <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Categoría</th>
        <th style="width: 120px;">Acciones</th>
    </tr>
    <% for (Producto p : productos) { %>
    <tr>
        <td><%= p.getIdProducto() %></td>
        <td><%= p.getNombre() %></td>
        <td><%= p.getCategoria() %></td>
        <td>
            <a href="CtrolProducto?accion=editar&id=<%= p.getIdProducto() %>&volver=catalogo" class="btn-sm">✏️ Editar</a>
            <a href="CtrolProducto?accion=eliminar&id=<%= p.getIdProducto() %>&volver=catalogo" 
               class="btn-sm btn-del" onclick="return confirm('¿Eliminar este producto?');">🗑 Eliminar</a>
        </td>
    </tr>
    <% } %>
</table>
</body>
</html>