package modelo;

import java.sql.*;
import java.util.*;

public class ListaMiembroDAO {

    // ── AGREGAR ──────────────────────────────────────────────────────────────
    // Agrega un miembro con un rol específico.
    // INSERT IGNORE evita duplicados si el usuario ya estaba en la lista.
    public int agregar(int idLista, int idUsuario, int idRol) {
        String q = "INSERT IGNORE INTO Tb_Lista_Miembros (fk_Lista, fk_Usuario, fk_Rol) VALUES (?,?,?)";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, idLista);
            ps.setInt(2, idUsuario);
            ps.setInt(3, idRol);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al agregar miembro: " + e.getMessage());
            return 0;
        }
    }

    // ── ACTUALIZAR ROL ───────────────────────────────────────────────────────
    // Cambia el rol de un miembro dentro de una lista.
    public int actualizarRol(int idLista, int idUsuario, int idRol) {
        String q = "UPDATE Tb_Lista_Miembros SET fk_Rol=? WHERE fk_Lista=? AND fk_Usuario=?";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, idRol);
            ps.setInt(2, idLista);
            ps.setInt(3, idUsuario);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar rol: " + e.getMessage());
            return 0;
        }
    }

    // ── ELIMINAR MIEMBRO ─────────────────────────────────────────────────────
    // Expulsa a un miembro de una lista.
    public int eliminar(int idLista, int idUsuario) {
        String q = "DELETE FROM Tb_Lista_Miembros WHERE fk_Lista=? AND fk_Usuario=?";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, idLista);
            ps.setInt(2, idUsuario);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar miembro: " + e.getMessage());
            return 0;
        }
    }

    // ── OBTENER ROL DE UN USUARIO EN UNA LISTA ───────────────────────────────
    // Devuelve el objeto Rol completo con todos los permisos.
    // Devuelve null si el usuario no es miembro de esa lista.
    public Rol obtenerRol(int idLista, int idUsuario) {
        String q = "SELECT r.id_Rol, r.Rol_Nombre, " +
           "       r.Puede_Agregar, r.Puede_Marcar, " +
           "       r.Puede_Eliminar, r.Puede_Gestionar " +
           "FROM Tb_Roles r " +
           "JOIN Tb_Lista_Miembros m ON r.id_Rol = m.fk_Rol " +
           "WHERE m.fk_Lista = ? AND m.fk_Usuario = ?";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, idLista);
            ps.setInt(2, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Rol r = new Rol();
                r.setIdRol(rs.getInt("id_Rol"));
                r.setRolNombre(rs.getString("Rol_Nombre"));
                r.setPuedeAgregar(rs.getBoolean("Puede_Agregar"));
                r.setPuedeMarcar(rs.getBoolean("Puede_Marcar"));
                r.setPuedeEliminar(rs.getBoolean("Puede_Eliminar"));
                r.setPuedeGestionar(rs.getBoolean("Puede_Gestionar"));
                return r;
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener rol: " + e.getMessage());
        }
        return null;
    }

    // ── LISTAR MIEMBROS DE UNA LISTA ─────────────────────────────────────────
    // Devuelve todos los miembros con nombre de usuario y nombre de rol.
    // Ordenado: primero el Dueño, luego el resto por id de rol.
    public List<MiembroInfo> listarMiembros(int idLista) {
        List<MiembroInfo> lista = new ArrayList<>();
        String q = "SELECT u.id_Usuario, " +
           "       u.User_Nombre, " +
           "       u.User_Apellido, " +
           "       r.id_Rol, " +
           "       r.Rol_Nombre " +
           "FROM Tb_Lista_Miembros m " +
           "JOIN Tb_Usuarios u ON m.fk_Usuario = u.id_Usuario " +
           "JOIN Tb_Roles r ON m.fk_Rol = r.id_Rol " +
           "WHERE m.fk_Lista = ? " +
           "ORDER BY r.id_Rol ASC, u.User_Nombre ASC";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, idLista);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MiembroInfo mi = new MiembroInfo();
                mi.setIdUsuario(rs.getInt("id_Usuario"));
                mi.setNombre(rs.getString("User_Nombre") + " " + rs.getString("User_Apellido"));
                mi.setIdRol(rs.getInt("id_Rol"));
                mi.setRolNombre(rs.getString("Rol_Nombre"));
                lista.add(mi);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar miembros: " + e.getMessage());
        }
        return lista;
    }

    // ── VERIFICAR SI UN USUARIO ES MIEMBRO ───────────────────────────────────
    // Útil para saber antes de mostrar una lista si el usuario tiene acceso.
    public boolean esMiembro(int idLista, int idUsuario) {
        String q = "SELECT 1 FROM Tb_Lista_Miembros WHERE fk_Lista=? AND fk_Usuario=?";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, idLista);
            ps.setInt(2, idUsuario);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error al verificar membresía: " + e.getMessage());
            return false;
        }
    }

    // ── CONTAR MIEMBROS DE UNA LISTA ─────────────────────────────────────────
    // Útil para mostrar en misListas.jsp cuántas personas están en cada lista.
    public int contarMiembros(int idLista) {
        String q = "SELECT COUNT(*) FROM Tb_Lista_Miembros WHERE fk_Lista=?";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, idLista);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("Error al contar miembros: " + e.getMessage());
        }
        return 0;
    }
}