<%-- 
    Document   : misListas
    Created on : 10/05/2026, 4:06:43 p. m.
    Author     : MSI
--%>
<%@ page import="java.util.List" %>
<%@ page import="modelo.Lista" %>
<%@ page import="modelo.ListaDAO" %>
<%@ page import="modelo.ListaMiembroDAO" %>
<%@ page import="modelo.Rol" %>
<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%
    // el filtro ya garantizó sesión válida
    int    idUsuario = (int)    session.getAttribute("idUsuario");
    String nUsuario  = (String) session.getAttribute("nUsuario");

    ListaDAO        ldao  = new ListaDAO();
    ListaMiembroDAO mDAO  = new ListaMiembroDAO();
    List<Lista>   listas  = ldao.listarPorUsuario(idUsuario);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Mis Listas</title>
    <style>
        body { font-family: Arial, sans-serif; font-size: 13px;
               background: #fff; padding: 20px; }
        #header { display: flex; justify-content: space-between;
                  align-items: center; border-bottom: 1px solid #999;
                  padding-bottom: 10px; margin-bottom: 20px; }
        #header .nav a { margin-left: 12px; text-decoration: none; color: #00c; }
        #header .nav a:hover { text-decoration: underline; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { border: 1px solid #999; padding: 8px; text-align: left; }
        th { background: #f0f0f0; }
        tr:nth-child(even) { background: #f9f9f9; }
        .btn { background: #f0f0f0; border: 1px solid #999; padding: 5px 10px;
               text-decoration: none; color: #000; }
        .btn:hover { background: #e0e0e0; }
        .btn-danger { border-color: #c00; color: #c00; }
        .btn-small  { padding: 3px 7px; font-size: 12px; }
        .form-inline { display: flex; gap: 8px; margin-bottom: 12px; }
        .form-inline input { padding: 5px; border: 1px solid #ccc; flex: 1; }
        .error-msg { color: red;   margin-bottom: 10px; }
        .ok-msg    { color: green; margin-bottom: 10px; }
        .codigo { font-family: monospace; background: #f5f5f5;
                  padding: 2px 6px; border: 1px solid #ddd; }
        .rol-tag { font-size: 11px; color: #666; border: 1px solid #ddd;
                   padding: 1px 5px; background: #f9f9f9; }
        .acciones { display: flex; gap: 6px; flex-wrap: wrap; align-items: center; }
        .form-renombrar { display: none; margin-top: 6px; }
        .form-renombrar input { padding: 4px; border: 1px solid #ccc; width: 160px; }
        .miembros-count { color: #666; font-size: 12px; }
    </style>
</head>
<body>

<div id="header">
    <span>Hola, <strong><%= nUsuario %></strong></span>
    <div class="nav">
        <a href="listaProductos.jsp">Productos</a>
        <a href="listaRoles.jsp">Roles</a>
    </div>
</div>

<h2>Mis Listas</h2>

<%
    String error = request.getParameter("error");
    String ok    = request.getParameter("ok");
%>
<% if ("1".equals(error)) { %>
    <div class="error-msg">Error al crear la lista.</div>
<% } else if ("2".equals(error)) { %>
    <div class="error-msg">Código de lista no encontrado.</div>
<% } else if ("permiso".equals(error)) { %>
    <div class="error-msg">No tienes permiso para hacer esa acción.</div>
<% } else if ("yaMiembro".equals(error)) { %>
    <div class="error-msg">Ya eres miembro de esa lista.</div>
<% } else if ("acceso".equals(error)) { %>
    <div class="error-msg">No tienes acceso a esa lista.</div>
<% } %>
<% if ("1".equals(ok)) { %>
    <div class="ok-msg">Lista actualizada correctamente.</div>
<% } %>

<%-- Crear lista --%>
<form method="post" action="CtrolLista" class="form-inline">
    <input type="hidden" name="accion" value="crear"/>
    <input type="text"   name="cnombre" placeholder="Nombre de la nueva lista" required/>
    <button type="submit" class="btn">Crear lista</button>
</form>

<%-- Unirse por código --%>
<form method="post" action="CtrolLista" class="form-inline">
    <input type="hidden" name="accion"   value="unirse"/>
    <input type="text"   name="ccodigo"  placeholder="Código de lista compartida" required/>
    <button type="submit" class="btn">Unirse</button>
</form>

<table>
    <thead>
        <tr>
            <th>Lista</th>
            <th>Código</th>
            <th>Miembros</th>
            <th>Mi rol</th>
            <th>Acciones</th>
        </tr>
    </thead>
    <tbody>
        <% if (listas == null || listas.isEmpty()) { %>
            <tr>
                <td colspan="5" style="text-align:center;">
                    No tienes listas. ¡Crea una o únete con un código!
                </td>
            </tr>
        <% } else {
               for (Lista l : listas) {
                   Rol   miRol      = mDAO.obtenerRol(l.getIdLista(), idUsuario);
                   int   numMiembros = mDAO.contarMiembros(l.getIdLista());
                   boolean esDueno  = miRol != null && miRol.getIdRol() == 1;
                   boolean puedeGes = miRol != null && miRol.isPuedeGestionar();
        %>
        <tr>
            <td>
                <a href="listaItems.jsp?id=<%= l.getIdLista() %>">
                    <%= l.getListaNombre() %>
                </a>
                <% if (puedeGes) { %>
                <div class="form-renombrar" id="renombrar-<%= l.getIdLista() %>">
                    <form method="post" action="CtrolLista"
                          style="display:flex; gap:4px;">
                        <input type="hidden" name="accion" value="renombrar"/>
                        <input type="hidden" name="id"     value="<%= l.getIdLista() %>"/>
                        <input type="text"   name="cnombre"
                               value="<%= l.getListaNombre() %>" required/>
                        <button type="submit" class="btn btn-small">Guardar</button>
                    </form>
                </div>
                <% } %>
            </td>
            <td><span class="codigo"><%= l.getCodigoCompartir() %></span></td>
            <td class="miembros-count"><%= numMiembros %> persona(s)</td>
            <td>
                <% if (miRol != null) { %>
                    <span class="rol-tag"><%= miRol.getRolNombre() %></span>
                <% } %>
            </td>
            <td>
                <div class="acciones">
                    <a href="listaItems.jsp?id=<%= l.getIdLista() %>"
                       class="btn btn-small">Ver items</a>

                    <% if (puedeGes) { %>
                        <a href="miembros.jsp?id=<%= l.getIdLista() %>"
                           class="btn btn-small">Miembros</a>
                        <a href="#"
                           onclick="document.getElementById('renombrar-<%= l.getIdLista() %>').style.display='block'; return false;"
                           class="btn btn-small">Renombrar</a>
                    <% } %>

                    <% if (esDueno) { %>
                        <a href="CtrolLista?accion=eliminar&id=<%= l.getIdLista() %>"
                           class="btn btn-small btn-danger"
                           onclick="return confirm('¿Eliminar la lista <%= l.getListaNombre() %>?')">
                            Eliminar
                        </a>
                    <% } %>
                </div>
            </td>
        </tr>
        <%  }
           } %>
    </tbody>
</table>

</body>
</html>