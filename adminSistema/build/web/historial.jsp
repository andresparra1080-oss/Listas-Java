<%-- 
    Document   : historial
    Created on : 21/05/2026
    Description: Muestra el historial de actividades (por lista o usuario)
--%>

<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ page import="java.util.List, modelo.GestionActividad" %>
<%
    if (session.getAttribute("idUsuario") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    List<GestionActividad> gestiones = (List<GestionActividad>) request.getAttribute("gestiones");
    String titulo = (String) request.getAttribute("titulo");
    if (titulo == null) titulo = "Historial de actividades";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title><%= titulo %></title>
    <style>
        body { font-family: Arial, sans-serif; font-size: 13px; background: #fff; padding: 20px; }
        h2 { border-bottom: 1px solid #999; padding-bottom: 10px; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background: #f0f0f0; }
        .filtro { margin-bottom: 15px; }
        .filtro input[type="text"] { padding: 4px; width: 200px; }
        .btn-sm { background: #f8f9fa; border: 1px solid #999; padding: 4px 8px; cursor: pointer; font-size: 11px; }
        .btn-sm:hover { background: #e2e6ea; }
    </style>
</head>
<body>
<h2><%= titulo %></h2>

<div class="filtro">
    <form method="get" action="CtrolGestionActividad">
        <input type="hidden" name="accion" value="historialLista"/>
        <label>Filtrar por ID Lista:</label>
        <input type="text" name="idLista" placeholder="Número de lista"/>
        <button type="submit" class="btn-sm">🔍 Buscar</button>
        <a href="CtrolGestionActividad?accion=historialUsuario" class="btn-sm">📋 Mi historial</a>
    </form>
</div>

<table>
    <tr><th>ID</th><th>Fecha/Hora</th><th>Lista</th><th>Detalle</th><th>Resultado</th></tr>
    <% if (gestiones != null && !gestiones.isEmpty()) {
        for (GestionActividad g : gestiones) { %>
            <tr>
                <td><%= g.getIdGestion() %></td>
                <td><%= g.getFechaHora() %></td>
                <td><%= g.getIdListaAsociada() != null ? g.getIdListaAsociada() : "—" %></td>
                <td><%= g.getDetalle() %></td>
                <td><%= g.getResultado() %></td>
            </tr>
    <%  } } else { %>
        <tr><td colspan="5">No hay registros para mostrar.</td></tr>
    <% } %>
</table>
</body>
</html>
