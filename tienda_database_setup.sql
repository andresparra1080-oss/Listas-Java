-- ============================================================================
-- Script SQL: Estructura y Datos Iniciales (Base de Datos: tienda)
-- Motor: MariaDB / MySQL
-- ============================================================================

DROP DATABASE IF EXISTS tienda;
CREATE DATABASE tienda CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE tienda;


-- ============================================================================
-- 2. CREACIÓN DE TABLAS (DDL)
-- ============================================================================

CREATE TABLE `auditoria_modificaciones` (
  `id_audit` int(11) NOT NULL AUTO_INCREMENT,
  `tabla` varchar(64) NOT NULL,
  `accion` enum('INSERT','UPDATE','DELETE') NOT NULL,
  `usuario` varchar(100) NOT NULL,
  `fecha_hora` datetime NOT NULL DEFAULT current_timestamp(),
  `id_registro` varchar(36) DEFAULT NULL,
  `descripcion` text DEFAULT NULL,
  PRIMARY KEY (`id_audit`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `tb_actividades` (
  `id_Actividad` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_actividad` varchar(50) NOT NULL,
  `descripcion` varchar(200) DEFAULT NULL,
  `tipo_actividad` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id_Actividad`),
  UNIQUE KEY `uq_actividad_nombre` (`nombre_actividad`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `tb_gestion_actividades` (
  `id_Gestion` int(11) NOT NULL AUTO_INCREMENT,
  `fk_idPerfil` int(11) NOT NULL,
  `fk_idActividad` int(11) NOT NULL,
  `id_lista_asociada` int(11) DEFAULT NULL,
  `fecha_hora` datetime DEFAULT current_timestamp(),
  `detalle` text DEFAULT NULL,
  `resultado` varchar(20) DEFAULT 'exito',
  PRIMARY KEY (`id_Gestion`),
  KEY `idx_gestion_perfil` (`fk_idPerfil`),
  KEY `idx_gestion_fecha` (`fecha_hora`),
  KEY `idx_gestion_lista` (`id_lista_asociada`),
  KEY `fk_gestion_actividad` (`fk_idActividad`),
  CONSTRAINT `fk_gestion_actividad` FOREIGN KEY (`fk_idActividad`) REFERENCES `tb_actividades` (`id_Actividad`),
  CONSTRAINT `fk_gestion_lista` FOREIGN KEY (`id_lista_asociada`) REFERENCES `tb_listas` (`id_Lista`) ON DELETE SET NULL,
  CONSTRAINT `fk_gestion_perfil` FOREIGN KEY (`fk_idPerfil`) REFERENCES `tb_perfil` (`id_Perfil`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `tb_lista_items` (
  `id_Item` int(11) NOT NULL AUTO_INCREMENT,
  `fk_Lista` int(11) NOT NULL,
  `fk_Producto` int(11) NOT NULL,
  `fk_Agregado_Por` int(11) NOT NULL,
  `fk_Comprado_Por` int(11) DEFAULT NULL,
  `Cantidad` decimal(10,2) NOT NULL DEFAULT 1.00,
  `Unidad` varchar(45) DEFAULT 'unidad',
  `Comprado` tinyint(1) DEFAULT 0,
  `Fecha_Agregado` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`id_Item`),
  KEY `fk_item_lista` (`fk_Lista`),
  KEY `fk_item_producto` (`fk_Producto`),
  KEY `fk_item_agregado` (`fk_Agregado_Por`),
  KEY `fk_item_comprado` (`fk_Comprado_Por`),
  CONSTRAINT `fk_item_agregado` FOREIGN KEY (`fk_Agregado_Por`) REFERENCES `tb_usuarios` (`id_Usuario`),
  CONSTRAINT `fk_item_comprado` FOREIGN KEY (`fk_Comprado_Por`) REFERENCES `tb_usuarios` (`id_Usuario`),
  CONSTRAINT `fk_item_lista` FOREIGN KEY (`fk_Lista`) REFERENCES `tb_listas` (`id_Lista`) ON DELETE CASCADE,
  CONSTRAINT `fk_item_producto` FOREIGN KEY (`fk_Producto`) REFERENCES `tb_productos` (`id_Producto`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `tb_lista_miembros` (
  `id_Miembro` int(11) NOT NULL AUTO_INCREMENT,
  `fk_Lista` int(11) NOT NULL,
  `fk_Usuario` int(11) NOT NULL,
  `fk_Rol` int(11) NOT NULL DEFAULT 2,
  PRIMARY KEY (`id_Miembro`),
  UNIQUE KEY `uq_lista_usuario` (`fk_Lista`,`fk_Usuario`),
  KEY `fk_miembro_usuario` (`fk_Usuario`),
  KEY `fk_miembro_rol` (`fk_Rol`),
  CONSTRAINT `fk_miembro_lista` FOREIGN KEY (`fk_Lista`) REFERENCES `tb_listas` (`id_Lista`) ON DELETE CASCADE,
  CONSTRAINT `fk_miembro_rol` FOREIGN KEY (`fk_Rol`) REFERENCES `tb_roles` (`id_Rol`),
  CONSTRAINT `fk_miembro_usuario` FOREIGN KEY (`fk_Usuario`) REFERENCES `tb_usuarios` (`id_Usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `tb_listas` (
  `id_Lista` int(11) NOT NULL AUTO_INCREMENT,
  `fk_Creador` int(11) NOT NULL,
  `Lista_Nombre` varchar(100) NOT NULL,
  `Codigo_Compartir` varchar(20) DEFAULT NULL,
  `Fecha_Creacion` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`id_Lista`),
  UNIQUE KEY `Codigo_Compartir` (`Codigo_Compartir`),
  KEY `fk_lista_creador` (`fk_Creador`),
  CONSTRAINT `fk_lista_creador` FOREIGN KEY (`fk_Creador`) REFERENCES `tb_usuarios` (`id_Usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `tb_perfil` (
  `id_Perfil` int(11) NOT NULL AUTO_INCREMENT,
  `fk_idUsuario` int(11) NOT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `fecha_registro_perfil` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`id_Perfil`),
  UNIQUE KEY `uq_perfil_usuario` (`fk_idUsuario`),
  CONSTRAINT `fk_perfil_usuario` FOREIGN KEY (`fk_idUsuario`) REFERENCES `tb_usuarios` (`id_Usuario`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `tb_productos` (
  `id_Producto` int(11) NOT NULL AUTO_INCREMENT,
  `Producto_Nombre` varchar(100) NOT NULL,
  `Producto_Categoria` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_Producto`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `tb_roles` (
  `id_Rol` int(11) NOT NULL AUTO_INCREMENT,
  `Rol_Nombre` varchar(50) NOT NULL,
  `Puede_Agregar` tinyint(1) DEFAULT 0,
  `Puede_Marcar` tinyint(1) DEFAULT 0,
  `Puede_Eliminar` tinyint(1) DEFAULT 0,
  `Puede_Gestionar` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`id_Rol`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `tb_usuarios` (
  `id_Usuario` int(11) NOT NULL AUTO_INCREMENT,
  `User_Nombre` varchar(45) NOT NULL,
  `User_Apellido` varchar(45) NOT NULL,
  `User_Email` varchar(100) NOT NULL,
  `User_Password` varchar(255) NOT NULL,
  PRIMARY KEY (`id_Usuario`),
  UNIQUE KEY `User_Email` (`User_Email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ============================================================================
-- 3. INSERCIÓN DE DATOS (DML)
-- ============================================================================

INSERT INTO `tb_actividades` VALUES (1,'Creacion de lista','Usuario crea una nueva lista de compras','Lista'),(2,'Producto comprado','Usuario marca un producto como comprado','Compra'),(3,'Agregar producto','Usuario a?ade un producto a la lista','Lista'),(4,'Eliminar producto','Usuario elimina un producto de la lista','Lista'),(5,'Modificar cantidad','Usuario modifica cantidad o unidad de un producto','Lista'),(7,'Prueba trigger','Probando','Test');
    INSERT INTO auditoria_modificaciones (tabla, accion, usuario, fecha_hora, id_registro, descripcion)
    INSERT INTO auditoria_modificaciones (tabla, accion, usuario, fecha_hora, id_registro, descripcion)
    INSERT INTO auditoria_modificaciones (tabla, accion, usuario, fecha_hora, id_registro, descripcion)
    INSERT INTO auditoria_modificaciones (tabla, accion, usuario, fecha_hora, id_registro, descripcion)
    INSERT INTO auditoria_modificaciones (tabla, accion, usuario, fecha_hora, id_registro, descripcion)
    INSERT INTO auditoria_modificaciones (tabla, accion, usuario, fecha_hora, id_registro, descripcion)
INSERT INTO `tb_lista_items` VALUES (4,1,10,2,1,3.00,'kg',1,'2026-05-21 02:04:49'),(5,1,13,3,1,4.00,'unidades',1,'2026-05-21 02:04:49'),(6,1,14,3,1,2.00,'unidades',1,'2026-05-21 02:04:49'),(9,2,6,2,2,6.00,'unidades',1,'2026-05-21 02:04:49'),(10,2,9,4,NULL,1.00,'kg',0,'2026-05-21 02:04:49'),(11,2,11,4,NULL,500.00,'g',0,'2026-05-21 02:04:49'),(12,2,17,5,5,2.00,'kg',1,'2026-05-21 02:04:49'),(13,2,18,5,NULL,1.00,'kg',0,'2026-05-21 02:04:49'),(14,2,23,2,2,1.00,'bolsa',1,'2026-05-21 02:04:49'),(15,2,24,4,NULL,2.00,'litros',0,'2026-05-21 02:04:49'),(16,3,2,3,NULL,400.00,'g',0,'2026-05-21 02:04:49'),(17,3,3,1,1,4.00,'unidades',1,'2026-05-21 02:04:49'),(18,3,8,3,NULL,1.00,'kg',0,'2026-05-21 02:04:49'),(19,3,12,1,NULL,500.00,'g',0,'2026-05-21 02:04:49'),(20,3,15,3,3,1.00,'cabeza',1,'2026-05-21 02:04:49'),(21,3,21,1,NULL,1.00,'bolsa',0,'2026-05-21 02:04:49'),(22,4,1,1,NULL,3.00,'litros',0,'2026-05-21 02:04:49'),(23,4,4,1,1,2.00,'unidades',1,'2026-05-21 02:04:49'),(24,4,5,1,NULL,1.00,'unidad',0,'2026-05-21 02:04:49'),(25,4,17,1,NULL,1.00,'kg',0,'2026-05-21 02:04:49'),(26,4,19,1,1,2.00,'kg',1,'2026-05-21 02:04:49'),(27,4,23,1,NULL,1.00,'bolsa',0,'2026-05-21 02:04:49'),(29,1,9,1,1,1.00,'unidad',1,'2026-05-21 13:47:43');
  INSERT INTO auditoria_modificaciones (tabla, accion, usuario, fecha_hora, id_registro)
  INSERT INTO auditoria_modificaciones (tabla, accion, usuario, fecha_hora, id_registro)
  INSERT INTO auditoria_modificaciones (tabla, accion, usuario, fecha_hora, id_registro)
INSERT INTO `tb_lista_miembros` VALUES (1,1,1,1),(2,1,2,2),(3,1,3,2),(4,2,2,1),(5,2,4,2),(6,2,5,2),(7,3,3,1),(8,3,1,2),(9,4,1,1);
INSERT INTO `tb_listas` VALUES (1,1,'Hola','ABC123','2025-05-01 09:00:00'),(2,2,'Fiesta del viernes','XYZ789','2025-05-03 14:30:00'),(3,3,'Lista del mes','MNO456','2025-05-05 08:00:00'),(4,1,'Desayunos semana','DEF321','2025-05-06 07:45:00');
INSERT INTO `tb_perfil` VALUES (1,1,'1990-05-15','+34 612 345 678','Calle Mayor 10, Madrid','2026-05-21 02:04:49'),(2,2,'1985-08-22','623456789','Avenida Sol 45','2026-05-21 02:04:49'),(3,3,'1992-11-30','634567890','Plaza Luna 3','2026-05-21 02:04:49'),(4,4,'1988-03-10','645678901','Calle Rio 12','2026-05-21 02:04:49'),(5,5,'1995-07-19','656789012','Paseo Mar 77','2026-05-21 02:04:49');
  INSERT INTO auditoria_modificaciones (tabla, accion, usuario, fecha_hora, id_registro)
  INSERT INTO auditoria_modificaciones (tabla, accion, usuario, fecha_hora, id_registro)
  INSERT INTO auditoria_modificaciones (tabla, accion, usuario, fecha_hora, id_registro)
INSERT INTO `tb_productos` VALUES (1,'Leche entera','Lacteos'),(2,'Queso mozzarella','Lacteos'),(3,'Yogur natural','Lacteos'),(4,'Pan tajado','Panaderia'),(5,'Pan integral','Panaderia'),(6,'Croissant','Panaderia'),(7,'Pollo entero','Carnes'),(8,'Carne molida','Carnes'),(9,'Chorizo','Carnes'),(10,'Arroz blanco','Granos'),(11,'Lentejas','Granos'),(12,'Pasta espagueti','Granos'),(13,'Tomate','Verduras'),(14,'Cebolla','Verduras'),(15,'Ajo','Verduras'),(16,'Zanahoria','Verduras'),(17,'Manzana','Frutas'),(18,'Banano','Frutas'),(19,'Naranja','Frutas'),(20,'Aceite de oliva','Despensa'),(21,'Sal','Despensa'),(22,'Azucar','Despensa'),(23,'Cafe molido','Bebidas'),(24,'Jugo de naranja','Bebidas'),(25,'Agua en botella','Bebidas');
  INSERT INTO auditoria_modificaciones (tabla, accion, usuario, fecha_hora, id_registro)
  INSERT INTO auditoria_modificaciones (tabla, accion, usuario, fecha_hora, id_registro)
  INSERT INTO auditoria_modificaciones (tabla, accion, usuario, fecha_hora, id_registro)
INSERT INTO `tb_roles` VALUES (1,'Due?o',1,1,1,1),(2,'Editor',1,1,1,0),(3,'Colaborador',1,1,0,0),(4,'Lector',0,0,0,0);
INSERT INTO `tb_usuarios` VALUES (1,'Carlos','Mendoza','carlos@email.com','hash_clave123'),(2,'Ana','Garcia','ana@email.com','hash_pass456'),(3,'Luis','Torres','luis@email.com','hash_luis789'),(4,'Valentina','Rios','vale@email.com','hash_vale321'),(5,'Jorge','Pe?a','jorge@email.com','hash_jorge000');
  INSERT INTO auditoria_modificaciones (tabla, accion, usuario, fecha_hora, id_registro)
  INSERT INTO auditoria_modificaciones (tabla, accion, usuario, fecha_hora, id_registro)
  INSERT INTO auditoria_modificaciones (tabla, accion, usuario, fecha_hora, id_registro)