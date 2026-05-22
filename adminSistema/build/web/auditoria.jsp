<%-- 
    Document   : auditoria
    Created on : 21/05/2026
    Description: Consulta de registros de auditoría de modificaciones
--%>

<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ page import="java.util.List, modelo.AuditoriaModificacion" %>
<%
    if (session.getAttribute("idUsuario") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    List<AuditoriaModificacion> auditorias = (List<AuditoriaModificacion>) request.getAttribute("auditorias");
    String filtro = (String) request.getAttribute("filtro");
    if (filtro == null) filtro = "Todas las tablas";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Auditoría de Modificaciones</title>
    <style>
        body { font-family: Arial, sans-serif; font-size: 13px; background: #fff; padding: 20px; }
        h2 { border-bottom: 1px solid #999; padding-bottom: 10px; }
        .filtro { margin-bottom: 15px; }
        .filtro select, .filtro input[type="submit"] { padding: 4px; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background: #f0f0f0; }
        .btn-sm { background: #f8f9fa; border: 1px solid #999; padding: 4px 8px; cursor: pointer; font-size: 11px; }
    </style>
</head>
<body>
<h2>Auditoría de Modificaciones <small style="font-weight:normal;">(Filtro: <%= filtro %>)</small></h2>

<div class="filtro">
    <form method="post" action="CtrolAuditoria">
        <input type="hidden" name="accion" value="filtrar"/>
        <label>Tabla:</label>
        <select name="tabla">
            <option value="">Todas</option>
            <option value="Tb_Usuarios" <%= "Tb_Usuarios".equals(filtro) ? "selected" : "" %>>Tb_Usuarios</option>
            <option value="Tb_Productos" <%= "Tb_Productos".equals(filtro) ? "selected" : "" %>>Tb_Productos</option>
            <option value="Tb_Lista_Items" <%= "Tb_Lista_Items".equals(filtro) ? "selected" : "" %>>Tb_Lista_Items</option>
            <option value="Tb_Perfil" <%= "Tb_Perfil".equals(filtro) ? "selected" : "" %>>Tb_Perfil</option>
            <option value="Tb_Actividades" <%= "Tb_Actividades".equals(filtro) ? "selected" : "" %>>Tb_Actividades</option>
            <option value="Tb_Gestion_Actividades" <%= "Tb_Gestion_Actividades".equals(filtro) ? "selected" : "" %>>Tb_Gestion_Actividades</option>
        </select>
        <button type="submit" class="btn-sm">Filtrar</button>
        <a href="CtrolAuditoria" class="btn-sm">Mostrar todo</a>
    </form>
</div>

<table>
    <tr><th>ID</th><th>Tabla</th><th>Acción</th><th>Usuario</th><th>Fecha/Hora</th><th>ID Registro</th></tr>
    <% if (auditorias != null && !auditorias.isEmpty()) {
        for (AuditoriaModificacion a : auditorias) { %>
            <tr>
                <td><%= a.getIdAudit() %></td>
                <td><%= a.getTabla() %></td>
                <td><%= a.getAccion() %></td>
                <td><%= a.getUsuario() %></td>
                <td><%= a.getFechaHora() %></td>
                <td><%= a.getIdRegistro() != null ? a.getIdRegistro() : "—" %></td>
            </tr>
    <%  } } else { %>
        <tr><td colspan="6">No hay registros de auditoría.</td></tr>
    <% } %>
</table>
</body>
</html>
