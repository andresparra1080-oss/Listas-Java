<%-- 
    Document   : Registro
    Created on : 10/05/2026, 4:06:09 p. m.
    Author     : MSI
--%>

<%@ page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <title>Registro</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                font-size: 13px;
                background: #fff;
                padding: 40px;
            }
            .form-container {
                border: 1px solid #999;
                padding: 20px;
                width: 380px;
                margin: 0 auto;
            }
            h2 {
                text-align: center;
                border-bottom: 1px solid #999;
                padding-bottom: 10px;
            }
            .form-group {
                margin-bottom: 12px;
            }
            label {
                display: block;
                margin-bottom: 4px;
                font-weight: bold;
            }
            input[type="text"], input[type="email"], input[type="password"] {
                width: 100%;
                padding: 6px;
                box-sizing: border-box;
                border: 1px solid #ccc;
            }
            .btn {
                background: #f0f0f0;
                border: 1px solid #999;
                padding: 8px 15px;
                cursor: pointer;
                width: 100%;
                font-weight: bold;
            }
            .btn:hover {
                background: #e0e0e0;
            }
            .error-msg {
                color: red;
                text-align: center;
                margin-bottom: 10px;
            }
        </style>
    </head>
    <body>
        <div class="form-container">
            <h2>Crear Cuenta</h2>

            <%-- Bloque para mostrar mensaje en caso de error en el registro --%>
            <% if ("1".equals(request.getParameter("error"))) { %>
            <div class="error-msg">Error al registrar. Intente de nuevo.</div>
            <% }%>

            <%-- CORRECCIÓN: Se añade el Context Path dinámico para evitar el Error 404 --%>
            <form method="post" action="<%= request.getContextPath()%>/ControladorUsuario">
                <div class="form-group">
                    <label>Nombre:</label>
                    <input type="text" name="cnombre" required/>
                </div>
                <div class="form-group">
                    <label>Apellido:</label>
                    <input type="text" name="capellido" required/>
                </div>
                <div class="form-group">
                    <label>Correo:</label>
                    <input type="email" name="cemail" required/>
                </div>
                <div class="form-group">
                    <label>Contraseña:</label>
                    <input type="password" name="cclave" required/>
                </div>
                <button type="submit" class="btn">Registrarse</button>
            </form>

            <p style="text-align:center; margin-top:10px;">
                <a href="index.jsp">Ya tengo cuenta</a>
            </p>
        </div>
    </body>
</html>