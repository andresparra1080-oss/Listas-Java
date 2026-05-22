<%-- 
    Document   : ListaRoles
    Created on : 14/05/2026, 4:25:33 p. m.
    Author     : USUARIO
--%>

<%@ page import="java.util.List" %>
<%@ page import="modelo.Rol" %>
<%@ page import="modelo.RolDAO" %>
<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%
    if (session.getAttribute("idUsuario") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    List<Rol> roles = new RolDAO().listarTodos();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <title>Roles</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                font-size: 13px;
                background: #fff;
                padding: 20px;
            }
            .header-container {
                display: flex;
                justify-content: space-between;
                align-items: center;
                border-bottom: 1px solid #999;
                padding-bottom: 10px;
                margin-bottom: 20px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
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
            .si  {
                color: #090;
                font-weight: bold;
            }
            .no  {
                color: #999;
            }
            .btn {
                background: #f0f0f0;
                border: 1px solid #999;
                padding: 5px 10px;
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
            .ok-msg    {
                color: green;
                margin-bottom: 10px;
            }
            .error-msg {
                color: red;
                margin-bottom: 10px;
            }
            .base-tag  {
                font-size: 11px;
                color: #999;
            }
        </style>
    </head>
    <body>

        <div class="header-container">
            <h2>Roles del sistema</h2>
            <a href="agregarRol.jsp" class="btn">+ Crear rol personalizado</a>
        </div>

        <% if ("1".equals(request.getParameter("ok"))) { %>
        <div class="ok-msg">Rol creado correctamente.</div>
        <% } else if ("base".equals(request.getParameter("error"))) { %>
        <div class="error-msg">Los roles base no se pueden eliminar.</div>
        <% } %>

        <table>
            <thead>
                <tr>
                    <th>Rol</th>
                    <th>Agregar items</th>
                    <th>Marcar comprado</th>
                    <th>Eliminar items</th>
                    <th>Gestionar miembros</th>
                    <th>Acción</th>
                </tr>
            </thead>
            <tbody>
                <% for (Rol r : roles) {%>
                <tr>
                    <td>
                        <%= r.getRolNombre()%>
                        <% if (r.getIdRol() <= 4) { %>
                        <span class="base-tag">(base)</span>
                        <% }%>
                    </td>
                    <td class="<%= r.isPuedeAgregar() ? "si" : "no"%>">
                        <%= r.isPuedeAgregar() ? "Sí" : "No"%>
                    </td>
                    <td class="<%= r.isPuedeMarcar() ? "si" : "no"%>">
                        <%= r.isPuedeMarcar() ? "Sí" : "No"%>
                    </td>
                    <td class="<%= r.isPuedeEliminar() ? "si" : "no"%>">
                        <%= r.isPuedeEliminar() ? "Sí" : "No"%>
                    </td>
                    <td class="<%= r.isPuedeGestionar() ? "si" : "no"%>">
                        <%= r.isPuedeGestionar() ? "Sí" : "No"%>
                    </td>
                    <td>
                        <% if (r.getIdRol() > 4) {%>
                        <a href="CtrolRol?accion=eliminarRol&id=<%= r.getIdRol()%>"
                           class="btn btn-danger"
                           onclick="return confirm('¿Eliminar este rol?')">Eliminar</a>
                        <% } else { %>
                        <span class="base-tag">—</span>
                        <% } %>
                    </td>
                </tr>
                <% }%>
            </tbody>
        </table>

        <p style="margin-top:15px;">
            <a href="misListas.jsp">← Volver a mis listas</a>
        </p>
    </body>
</html>