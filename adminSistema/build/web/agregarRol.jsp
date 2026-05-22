<%-- 
    Document   : agregarRol
    Created on : 14/05/2026, 4:26:47 p. m.
    Author     : USUARIO
--%>

<%@ page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <title>Crear Rol</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                font-size: 13px;
                background: #fff;
                padding: 20px;
            }
            .form-container {
                border: 1px solid #999;
                padding: 20px;
                width: 420px;
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
            input[type="text"] {
                width: 100%;
                padding: 6px;
                box-sizing: border-box;
                border: 1px solid #ccc;
            }
            .check-group {
                display: flex;
                flex-direction: column;
                gap: 8px;
                border: 1px solid #ddd;
                padding: 10px;
            }
            .check-group label {
                font-weight: normal;
                display: flex;
                align-items: center;
                gap: 8px;
            }
            .check-group input[type="checkbox"] {
                width: auto;
            }
            .btn {
                background: #f0f0f0;
                border: 1px solid #999;
                padding: 8px 15px;
                cursor: pointer;
                width: 100%;
                font-weight: bold;
                margin-top: 10px;
            }
            .btn:hover {
                background: #e0e0e0;
            }
            .error-msg {
                color: red;
                text-align: center;
                margin-bottom: 10px;
            }
            .link-volver {
                text-align: center;
                margin-top: 10px;
            }
            .hint {
                font-size: 11px;
                color: #666;
                margin-top: 3px;
            }
        </style>
    </head>
    <body>
        <div class="form-container">
            <h2>Crear Rol Personalizado</h2>

            <% if ("1".equals(request.getParameter("error"))) { %>
            <div class="error-msg">Error al guardar. Intente de nuevo.</div>
            <% }%>

            <form method="post" action="CtrolRol">
                <input type="hidden" name="accion" value="crearRol"/>

                <div class="form-group">
                    <label>Nombre del rol:</label>
                    <input type="text" name="cnombre" placeholder="Ej: Moderador" required/>
                </div>

                <div class="form-group">
                    <label>Permisos:</label>
                    <div class="check-group">
                        <label>
                            <input type="checkbox" name="cagregar" value="1"/>
                            Puede agregar productos a la lista
                        </label>
                        <label>
                            <input type="checkbox" name="cmarcar" value="1"/>
                            Puede marcar productos como comprados
                        </label>
                        <label>
                            <input type="checkbox" name="celiminar" value="1"/>
                            Puede eliminar productos de la lista
                        </label>
                        <label>
                            <input type="checkbox" name="cgestionar" value="1"/>
                            Puede gestionar miembros y sus roles
                        </label>
                    </div>
                    <p class="hint">Los roles base (Dueño, Editor, Colaborador, Lector) no se pueden modificar.</p>
                </div>

                <button type="submit" class="btn">Guardar Rol</button>
            </form>
            <div class="link-volver">
                <a href="listaRoles.jsp">← Ver todos los roles</a>
            </div>
        </div>
    </body>
</html>