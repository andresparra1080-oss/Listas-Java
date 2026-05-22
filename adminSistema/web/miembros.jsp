<%-- 
    Document   : miembros
    Created on : 14/05/2026, 4:27:14 p. m.
    Author     : USUARIO
--%>

<%@ page import="java.util.List" %>
<%@ page import="modelo.MiembroInfo" %>
<%@ page import="modelo.ListaMiembroDAO" %>
<%@ page import="modelo.Lista" %>
<%@ page import="modelo.ListaDAO" %>
<%@ page import="modelo.Rol" %>
<%@ page import="modelo.RolDAO" %>
<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%
    // el filtro ya garantizó sesión y membresía
    int idUsuario = (int) session.getAttribute("idUsuario");
    int idLista   = Integer.parseInt(request.getParameter("id"));

    ListaMiembroDAO mDAO       = new ListaMiembroDAO();
    Rol miRol                  = mDAO.obtenerRol(idLista, idUsuario);
    boolean puedeGestionar     = miRol != null && miRol.isPuedeGestionar();

    ListaDAO ldao              = new ListaDAO();
    Lista lista                = ldao.buscarPorId(idLista);

    List<MiembroInfo> miembros = mDAO.listarMiembros(idLista);
    List<Rol> roles            = new RolDAO().listarTodos();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Miembros — <%= lista != null ? lista.getListaNombre() : "" %></title>
    <style>
        body { font-family: Arial, sans-serif; font-size: 13px;
               background: #fff; padding: 20px; }
        #header { display: flex; justify-content: space-between;
                  align-items: center; border-bottom: 1px solid #999;
                  padding-bottom: 10px; margin-bottom: 20px; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #999; padding: 8px; text-align: left; }
        th { background: #f0f0f0; }
        tr:nth-child(even) { background: #f9f9f9; }
        .btn { background: #f0f0f0; border: 1px solid #999; padding: 4px 8px;
               text-decoration: none; color: #000; }
        .btn:hover { background: #e0e0e0; }
        .btn-danger { border-color: #c00; color: #c00; }
        .btn-small  { padding: 3px 7px; font-size: 12px; }
        .ok-msg    { color: green; margin-bottom: 10px; }
        .error-msg { color: red;   margin-bottom: 10px; }
        select { padding: 4px; border: 1px solid #ccc; }
        .yo-tag  { font-size: 11px; color: #666; margin-left: 4px; }
        .rol-tag { font-size: 11px; color: #666; border: 1px solid #ddd;
                   padding: 1px 5px; background: #f9f9f9; }
        .permisos-lista { font-size: 11px; color: #666; margin: 4px 0 0 0;
                          padding-left: 14px; }
        .hint { font-size: 12px; color: #666; margin-top: 15px;
                border: 1px dashed #ddd; padding: 8px; }
    </style>
</head>
<body>

<div id="header">
    <div>
        <a href="listaItems.jsp?id=<%= idLista %>">← Volver a la lista</a>
        &nbsp;|&nbsp;
        <strong>Miembros —
            <%= lista != null ? lista.getListaNombre() : "" %>
        </strong>
    </div>
    <span>Mi rol:
        <span class="rol-tag">
            <%= miRol != null ? miRol.getRolNombre() : "" %>
        </span>
    </span>
</div>

<%
    String error = request.getParameter("error");
    String ok    = request.getParameter("ok");
%>
<% if ("1".equals(ok)) { %>
    <div class="ok-msg">Rol actualizado correctamente.</div>
<% } else if ("2".equals(ok)) { %>
    <div class="ok-msg">Miembro expulsado de la lista.</div>
<% } else if ("permiso".equals(error)) { %>
    <div class="error-msg">No tienes permiso para hacer esa acción.</div>
<% } else if ("dueno".equals(error)) { %>
    <div class="error-msg">No se puede modificar al dueño de la lista.</div>
<% } %>

<table>
    <thead>
        <tr>
            <th>Miembro</th>
            <th>Rol y permisos</th>
            <% if (puedeGestionar) { %>
                <th>Cambiar rol</th>
                <th>Acción</th>
            <% } %>
        </tr>
    </thead>
    <tbody>
        <% for (MiembroInfo m : miembros) {
               boolean esDueno  = m.getIdRol() == 1;
               boolean esYo     = m.getIdUsuario() == idUsuario;
               Rol rolMiembro   = new RolDAO().buscarPorId(m.getIdRol());
        %>
        <tr>
            <td>
                <%= m.getNombre() %>
                <% if (esYo) { %>
                    <span class="yo-tag">(tú)</span>
                <% } %>
            </td>
            <td>
                <span class="rol-tag"><%= m.getRolNombre() %></span>
                <% if (rolMiembro != null) { %>
                <ul class="permisos-lista">
                    <li>Agregar:   <%= rolMiembro.isPuedeAgregar()   ? "✔" : "✗" %></li>
                    <li>Marcar:    <%= rolMiembro.isPuedeMarcar()    ? "✔" : "✗" %></li>
                    <li>Eliminar:  <%= rolMiembro.isPuedeEliminar()  ? "✔" : "✗" %></li>
                    <li>Gestionar: <%= rolMiembro.isPuedeGestionar() ? "✔" : "✗" %></li>
                </ul>
                <% } %>
            </td>
            <% if (puedeGestionar) { %>
            <td>
                <% if (!esDueno && !esYo) { %>
                    <form method="post" action="CtrolRol"
                          style="display:inline-flex; gap:4px;">
                        <input type="hidden" name="accion"    value="cambiarRol"/>
                        <input type="hidden" name="idLista"   value="<%= idLista %>"/>
                        <input type="hidden" name="idMiembro" value="<%= m.getIdUsuario() %>"/>
                        <select name="idRol">
                            <% for (Rol r : roles) { %>
                                <option value="<%= r.getIdRol() %>"
                                    <%= r.getIdRol() == m.getIdRol() ? "selected" : "" %>>
                                    <%= r.getRolNombre() %>
                                </option>
                            <% } %>
                        </select>
                        <button type="submit" class="btn btn-small">Guardar</button>
                    </form>
                <% } else { %>
                    <span style="color:#999; font-size:12px;">—</span>
                <% } %>
            </td>
            <td>
                <% if (!esDueno && !esYo) { %>
                    <a href="CtrolRol?accion=expulsar&idLista=<%= idLista %>&idMiembro=<%= m.getIdUsuario() %>"
                       class="btn btn-danger btn-small"
                       onclick="return confirm('¿Expulsar a este miembro?')">
                        Expulsar
                    </a>
                <% } else { %>
                    <span style="color:#999; font-size:12px;">—</span>
                <% } %>
            </td>
            <% } %>
        </tr>
        <% } %>
    </tbody>
</table>

<% if (puedeGestionar) { %>
    <div class="hint">
        Para invitar a alguien comparte el código:
        <strong><%= lista != null ? lista.getCodigoCompartir() : "" %></strong>
        — al unirse tendrá rol <strong>Colaborador</strong> por defecto.
    </div>
    <p style="margin-top:12px;">
        <a href="listaRoles.jsp" class="btn btn-small">Ver / crear roles</a>
    </p>
<% } %>

</body>
</html>