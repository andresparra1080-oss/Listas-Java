<%-- 
    Document   : indes
    Created on : 23/04/2026, 5:02:54?p.?m.
    Author     : USUARIO
--%>

<%@ page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Lista Compartida — Login</title>
    <style>
        body { font-family: Arial, sans-serif; font-size: 13px;
               background: #fff; padding: 40px; }
        .form-container { border: 1px solid #999; padding: 20px;
                          width: 360px; margin: 0 auto; }
        h2 { text-align: center; border-bottom: 1px solid #999;
             padding-bottom: 10px; }
        .form-group { margin-bottom: 12px; }
        label { display: block; margin-bottom: 4px; font-weight: bold; }
        input[type="email"], input[type="password"] {
            width: 100%; padding: 6px; box-sizing: border-box;
            border: 1px solid #ccc; }
        .btn { background: #f0f0f0; border: 1px solid #999; padding: 8px 15px;
               cursor: pointer; width: 100%; font-weight: bold; }
        .btn:hover { background: #e0e0e0; }
        .error-msg { color: red;   text-align: center; margin-bottom: 10px; }
        .ok-msg    { color: green; text-align: center; margin-bottom: 10px; }
        .link-registro { text-align: center; margin-top: 10px; }
    </style>
</head>
<body>
<div class="form-container">
    <h2>Lista Compartida</h2>

    <% if (request.getAttribute("error") != null) { %>
        <div class="error-msg"><%= request.getAttribute("error") %></div>
    <% } %>
    <% if ("1".equals(request.getParameter("ok"))) { %>
        <div class="ok-msg">Cuenta creada. Ya puedes ingresar.</div>
    <% } %>

    <form method="post" action="CtrolValidar">
        <input type="hidden" name="accion" value="Ingresar"/>
        <div class="form-group">
            <label>Correo:</label>
            <input type="email" name="cemail" required/>
        </div>
        <div class="form-group">
            <label>Contraseña:</label>
            <input type="password" name="cclave" required/>
        </div>
        <button type="submit" class="btn">Ingresar</button>
    </form>
    <div class="link-registro">
        <a href="registro.jsp">Crear cuenta nueva</a>
    </div>
</div>
</body>
</html>