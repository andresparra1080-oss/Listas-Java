<%-- 
    Document   : perfil
    Created on : 21/05/2026
    Description: Formulario de perfil del usuario (autocargado desde sesión)
--%>

<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ page import="modelo.Perfil" %>
<%
    if (session.getAttribute("idUsuario") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    // El controlador CtrolPerfil ya ha puesto el atributo "perfil" si existe
    Perfil perfil = (Perfil) request.getAttribute("perfil");
    String ok = request.getParameter("ok");
    String crear = request.getParameter("crear");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Mi Perfil</title>
    <style>
        body { font-family: Arial, sans-serif; font-size: 13px; background: #fff; padding: 20px; }
        .form-container { border: 1px solid #999; padding: 20px; width: 450px; margin: 0 auto; }
        h2 { text-align: center; border-bottom: 1px solid #999; padding-bottom: 10px; }
        .form-group { margin-bottom: 12px; }
        label { display: block; margin-bottom: 4px; font-weight: bold; }
        input[type="text"], input[type="date"] { width: 100%; padding: 6px; box-sizing: border-box; border: 1px solid #ccc; }
        .btn { background: #f0f0f0; border: 1px solid #999; padding: 8px 15px; cursor: pointer; width: 100%; font-weight: bold; }
        .btn:hover { background: #e0e0e0; }
        .success-msg { color: green; text-align: center; margin-bottom: 10px; }
        .info-msg { color: #0056b3; text-align: center; margin-bottom: 10px; }
    </style>
</head>
<body>
<div class="form-container">
    <h2>Mi Perfil</h2>
    <% if ("1".equals(ok)) { %>
        <div class="success-msg">Perfil actualizado correctamente.</div>
    <% } else if ("1".equals(crear)) { %>
        <div class="info-msg">Complete sus datos de perfil.</div>
    <% } %>

    <form method="post" action="CtrolPerfil">
        <input type="hidden" name="accion" value="actualizar"/>

        <div class="form-group">
            <label>Fecha de Nacimiento:</label>
            <input type="date" name="fecha_nacimiento" 
                   value="<%= perfil != null ? perfil.getFechaNacimiento() : "" %>" required/>
        </div>

        <div class="form-group">
            <label>Teléfono:</label>
            <input type="text" name="telefono" placeholder="Ej: 612345678" 
                   value="<%= perfil != null ? perfil.getTelefono() : "" %>" required/>
        </div>

        <div class="form-group">
            <label>Dirección:</label>
            <input type="text" name="direccion" placeholder="Calle, número, ciudad" 
                   value="<%= perfil != null ? perfil.getDireccion() : "" %>" required/>
        </div>

        <button type="submit" class="btn">Guardar Perfil</button>
    </form>
</div>
</body>
</html>