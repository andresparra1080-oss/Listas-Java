<%-- 
    Document   : actividades
    Created on : 21/05/2026
    Description: Listado y formulario para administrar tipos de actividad
--%>

<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ page import="java.util.List, modelo.Actividad" %>
<%
    if (session.getAttribute("idUsuario") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    List<Actividad> lista = (List<Actividad>) request.getAttribute("actividades");
    String ok = request.getParameter("ok");
    String idEditar = request.getParameter("editar");
    Actividad actEditar = null;
    if (idEditar != null) {
        for (Actividad a : lista) {
            if (a.getIdActividad() == Integer.parseInt(idEditar)) {
                actEditar = a;
                break;
            }
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Administrar Actividades</title>
    <style>
        body { font-family: Arial, sans-serif; font-size: 13px; background: #fff; padding: 20px; }
        h2 { border-bottom: 1px solid #999; padding-bottom: 10px; }
        table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background: #f0f0f0; }
        .btn-sm { background: #f8f9fa; border: 1px solid #999; padding: 4px 8px; cursor: pointer; font-size: 11px; margin-right: 4px; text-decoration: none; color: black; display: inline-block; }
        .btn-sm:hover { background: #e2e6ea; }
        .form-inline { margin-top: 20px; border: 1px solid #ccc; padding: 15px; background: #fafafa; }
        .form-group { margin-bottom: 8px; }
        label { display: inline-block; width: 100px; font-weight: bold; }
        input[type="text"], select { padding: 4px; width: 250px; }
        .msg { color: green; font-weight: bold; margin-bottom: 10px; }
    </style>
</head>
<body>
<h2>Catálogo de Actividades</h2>

<% if ("1".equals(ok)) { %><div class="msg">Actividad creada.</div>
<% } else if ("2".equals(ok)) { %><div class="msg">Actividad actualizada.</div>
<% } else if ("3".equals(ok)) { %><div class="msg">Actividad eliminada.</div>
<% } %>

<!-- Tabla de actividades -->
<table>
    <tr><th>ID</th><th>Nombre</th><th>Descripción</th><th>Tipo</th><th>Acciones</th></tr>
    <% if (lista != null) {
        for (Actividad a : lista) { %>
            <tr>
                <td><%= a.getIdActividad() %></td>
                <td><%= a.getNombreActividad() %></td>
                <td><%= a.getDescripcion() %></td>
                <td><%= a.getTipoActividad() %></td>
                <td>
                    <a href="actividades.jsp?editar=<%= a.getIdActividad() %>" class="btn-sm">✏️ Editar</a>
                    <a href="CtrolActividad?accion=eliminar&id=<%= a.getIdActividad() %>" class="btn-sm" 
                       onclick="return confirm('¿Eliminar esta actividad?');">🗑 Eliminar</a>
                </td>
            </tr>
    <%  } } %>
</table>

<!-- Formulario para crear o editar -->
<div class="form-inline">
    <h3><%= (actEditar != null) ? "Editar Actividad" : "Nueva Actividad" %></h3>
    <form method="post" action="CtrolActividad">
        <input type="hidden" name="accion" value="<%= (actEditar != null) ? "actualizar" : "crear" %>"/>
        <% if (actEditar != null) { %>
            <input type="hidden" name="id" value="<%= actEditar.getIdActividad() %>"/>
        <% } %>
        <div class="form-group">
            <label>Nombre:</label>
            <input type="text" name="nombre" value="<%= actEditar != null ? actEditar.getNombreActividad() : "" %>" required/>
        </div>
        <div class="form-group">
            <label>Descripción:</label>
            <input type="text" name="descripcion" value="<%= actEditar != null ? actEditar.getDescripcion() : "" %>"/>
        </div>
        <div class="form-group">
            <label>Tipo:</label>
            <select name="tipo">
                <option value="Lista" <%= actEditar != null && "Lista".equals(actEditar.getTipoActividad()) ? "selected" : "" %>>Lista</option>
                <option value="Compra" <%= actEditar != null && "Compra".equals(actEditar.getTipoActividad()) ? "selected" : "" %>>Compra</option>
            </select>
        </div>
        <button type="submit" class="btn-sm" style="margin-top:10px;">Guardar</button>
        <% if (actEditar != null) { %>
            <a href="actividades.jsp" class="btn-sm">Cancelar</a>
        <% } %>
    </form>
</div>
</body>
</html>
