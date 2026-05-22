<%-- 
    Document   : cpanel
    Created on : 15/05/2026
    Updated    : 21/05/2026 – agregadas secciones perfil, actividades, historial, auditoría
    Description: Dashboard con iframe. Los enlaces del menú apuntan a controladores
                 para que las páginas siempre reciban los atributos necesarios.
--%>

<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%
    if (session.getAttribute("idUsuario") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    String nUsuario = (String) session.getAttribute("nUsuario");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <title>Panel de Control | Lista Compartida</title>
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }
            body {
                font-family: 'Segoe UI', Arial, sans-serif;
                font-size: 13px;
                background: #e9ecef;
                height: 100vh;
                overflow: hidden;
            }
            /* Layout principal */
            .dashboard {
                display: flex;
                flex-direction: column;
                height: 100%;
            }
            /* Header superior */
            .top-bar {
                background: #343a40;
                color: white;
                padding: 12px 24px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                z-index: 10;
            }
            .logo {
                font-size: 18px;
                font-weight: bold;
                letter-spacing: 1px;
            }
            .user-info {
                font-size: 14px;
            }
            .btn-salir {
                background: #dc3545;
                border: none;
                color: white;
                padding: 6px 14px;
                border-radius: 4px;
                cursor: pointer;
                text-decoration: none;
                font-size: 12px;
                transition: 0.2s;
            }
            .btn-salir:hover {
                background: #c82333;
            }
            /* Contenedor menú + contenido */
            .main-container {
                display: flex;
                flex: 1;
                overflow: hidden;
            }
            /* Menú lateral */
            .sidebar {
                width: 220px;
                background: #f8f9fa;
                border-right: 1px solid #dee2e6;
                display: flex;
                flex-direction: column;
                padding: 20px 0;
                overflow-y: auto;
            }
            .sidebar a {
                display: block;
                padding: 10px 20px;
                color: #495057;
                text-decoration: none;
                font-size: 14px;
                transition: 0.2s;
                border-left: 3px solid transparent;
            }
            .sidebar a:hover {
                background: #e9ecef;
                border-left-color: #007bff;
            }
            .sidebar a.active {
                background: #e9ecef;
                border-left-color: #007bff;
                font-weight: bold;
            }
            .sidebar hr {
                margin: 15px 0;
                border: none;
                border-top: 1px solid #dee2e6;
            }
            /* Área del iframe */
            .content-area {
                flex: 1;
                background: white;
                overflow: hidden;
            }
            iframe {
                width: 100%;
                height: 100%;
                border: none;
            }
            /* Scroll opcional */
            ::-webkit-scrollbar {
                width: 8px;
                height: 8px;
            }
            ::-webkit-scrollbar-track {
                background: #f1f1f1;
            }
            ::-webkit-scrollbar-thumb {
                background: #c1c1c1;
                border-radius: 4px;
            }
            .sidebar-footer {
                margin-top: auto;
                padding: 15px 20px;
                font-size: 11px;
                color: #6c757d;
                text-align: center;
                border-top: 1px solid #dee2e6;
            }
        </style>
        <script>
            // Función para cambiar el iframe y actualizar el menú activo
            function cargarPagina(url, elemento) {
                document.getElementById('contenidoIframe').src = url;
                // Quitar clase 'active' de todos los enlaces
                var enlaces = document.querySelectorAll('.sidebar a');
                enlaces.forEach(function (enl) {
                    enl.classList.remove('active');
                });
                if (elemento) {
                    elemento.classList.add('active');
                }
                return false;
            }

            // Cargar por defecto la primera opción (Mis listas) y marcar el enlace
            window.onload = function () {
                var primerEnlace = document.querySelector('.sidebar a');
                if (primerEnlace) {
                    cargarPagina(primerEnlace.getAttribute('data-url'), primerEnlace);
                }
            };
        </script>
    </head>
    <body>
        <div class="dashboard">
            <!-- Header superior -->
            <div class="top-bar">
                <div class="logo">📋 Lista Compartida · Dashboard</div>
                <div class="user-info">
                    👤 Hola, <strong><%= nUsuario%></strong> &nbsp;&nbsp;
                    <a href="CerrarSesion" class="btn-salir" target="_top">🚪 Cerrar sesión</a>
                </div>
            </div>

            <div class="main-container">
                <!-- Menú lateral -->
                <div class="sidebar">
                    <!-- Sección principal -->
                    <a href="#" data-url="misListas.jsp" 
                       onclick="return cargarPagina(this.getAttribute('data-url'), this)">
                        🏠 Mis listas
                    </a>
                    <a href="#" data-url="listaProductos.jsp" 
                       onclick="return cargarPagina(this.getAttribute('data-url'), this)">
                        📦 Catálogo de productos
                    </a>
                    <a href="#" data-url="listaRoles.jsp" 
                       onclick="return cargarPagina(this.getAttribute('data-url'), this)">
                        ⚙️ Roles del sistema
                    </a>

                    <hr>

                    <!-- Nuevos accesos a funcionalidades extendidas -->
                    <a href="#" data-url="CtrolPerfil?accion=ver" 
                       onclick="return cargarPagina(this.getAttribute('data-url'), this)">
                        👤 Mi perfil
                    </a>
                    <a href="#" data-url="CtrolGestionActividad?accion=historialUsuario" 
                       onclick="return cargarPagina(this.getAttribute('data-url'), this)">
                        📜 Historial
                    </a>
                    <a href="#" data-url="CtrolAuditoria" 
                       onclick="return cargarPagina(this.getAttribute('data-url'), this)">
                        🔍 Auditoría
                    </a>

                    <hr>

                    <!-- Enlaces originales de creación rápida -->
                    <a href="#" data-url="agregarProducto.jsp" 
                       onclick="return cargarPagina(this.getAttribute('data-url'), this)">
                        ➕ Nuevo producto (global)
                    </a>
                    <a href="#" data-url="agregarRol.jsp" 
                       onclick="return cargarPagina(this.getAttribute('data-url'), this)">
                        🆕 Crear nuevo rol
                    </a>

                    <!-- Pie del menú -->
                    <div class="sidebar-footer">
                        ℹ️ Gestión de miembros disponible<br>
                        desde "Mis listas" → cada lista
                    </div>
                </div>

                <!-- Área del iframe -->
                <div class="content-area">
                    <iframe id="contenidoIframe" title="Panel de contenido" src="about:blank"></iframe>
                </div>
            </div>
        </div>
    </body>
</html>