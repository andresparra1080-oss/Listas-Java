# Aplicación Web de Listas de Compras Compartidas 🛒✨

Una aplicación web robusta y colaborativa construida con **Java (Servlets y JSP)** que permite a los usuarios crear, gestionar y compartir listas de compras en tiempo real. El sistema cuenta con un modelo avanzado de Control de Acceso Basado en Roles (RBAC), paneles interactivos (dashboards) e historiales de auditoría para ofrecer una experiencia de compra cooperativa, fluida y segura.

---

## 🚀 Características Principales

* **Listas de Compras Compartidas:** Crea múltiples listas de compras (ej. "Mercado Semanal", "Fiesta del Viernes") y compártelas usando códigos únicos de invitación.
* **Control de Acceso Basado en Roles (RBAC):** Define roles granulares (Dueño, Editor, Colaborador, Lector) que determinan quién puede agregar, ver, marcar como comprado o eliminar productos.
* **Gestión de Productos:** Catálogo global de artículos categorizados (Lácteos, Panadería, Carnes, etc.) que se pueden añadir dinámicamente a cualquier lista con cantidades y unidades específicas.
* **Seguimiento de Miembros y Roles:** Visualiza a todos los participantes dentro de una lista junto con sus permisos asignados y la jerarquía administrativa.
* **Panel Interactivo (Dashboard):** Supervisión visual de actividades recientes, listas activas, balance de artículos comprados vs. pendientes y estadísticas de usuario.
* **Sistema de Auditoría Automatizado:** Respaldado por disparadores (triggers) en la base de datos para rastrear todas las inserciones, actualizaciones y modificaciones críticas por seguridad.

---

## 🛠️ Stack Tecnológico

* **Backend:** Java EE (Servlets, JSP, JSTL)
* **Base de Datos:** MySQL / MariaDB (Esquema relacional con restricciones de Claves Foráneas y Triggers)
* **Servidor de Aplicaciones:** Apache TomEE
* **IDE:** NetBeans

---

## 📋 Prerrequisitos e Instalación

Sigue estos pasos para configurar el entorno y ejecutar la aplicación de forma local usando **NetBeans**.

### 1. Configuración de la Base de Datos (MySQL / MariaDB)
1. Inicia tu servidor local de **MySQL/MariaDB** (a través de XAMPP, Workbench o Docker).
2. Crea la base de datos y despliega el esquema utilizando tu script de respaldo SQL:
   ```sql
   -- Ejecuta tu script 'tienda_database_setup.sql' para inicializar la base de datos
