package modelo;

import Interfaces.CRUD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuditoriaModificacionDAO implements CRUD<AuditoriaModificacion> {

    @Override
    public int agregar(AuditoriaModificacion a) {
        // Normalmente este registro se genera por triggers, pero se permite inserción manual
        String q = "INSERT INTO auditoria_modificaciones (tabla, accion, usuario, fecha_hora, id_registro) VALUES (?,?,?,?,?)";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setString(1, a.getTabla());
            ps.setString(2, a.getAccion());
            ps.setString(3, a.getUsuario());
            ps.setTimestamp(4, a.getFechaHora());
            ps.setString(5, a.getIdRegistro());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al agregar auditoría: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int actualizar(AuditoriaModificacion a) {
        // La auditoría normalmente no se actualiza, pero se implementa por completitud
        String q = "UPDATE auditoria_modificaciones SET tabla=?, accion=?, usuario=?, fecha_hora=?, id_registro=? WHERE id_audit=?";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setString(1, a.getTabla());
            ps.setString(2, a.getAccion());
            ps.setString(3, a.getUsuario());
            ps.setTimestamp(4, a.getFechaHora());
            ps.setString(5, a.getIdRegistro());
            ps.setInt(6, a.getIdAudit());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar auditoría: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int eliminar(int id) {
        String q = "DELETE FROM auditoria_modificaciones WHERE id_audit=?";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar auditoría: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public AuditoriaModificacion buscarPorId(int id) {
        String q = "SELECT * FROM auditoria_modificaciones WHERE id_audit=?";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapear(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar auditoría: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<AuditoriaModificacion> listarTodos() {
        List<AuditoriaModificacion> lista = new ArrayList<>();
        String q = "SELECT * FROM auditoria_modificaciones ORDER BY fecha_hora DESC";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar auditoría: " + e.getMessage());
        }
        return lista;
    }

    // Extra: filtrar auditoría por nombre de tabla
    public List<AuditoriaModificacion> listarPorTabla(String nombreTabla) {
        List<AuditoriaModificacion> lista = new ArrayList<>();
        String q = "SELECT * FROM auditoria_modificaciones WHERE tabla = ? ORDER BY fecha_hora DESC";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setString(1, nombreTabla);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar auditoría por tabla: " + e.getMessage());
        }
        return lista;
    }

    private AuditoriaModificacion mapear(ResultSet rs) throws SQLException {
        AuditoriaModificacion a = new AuditoriaModificacion();
        a.setIdAudit(rs.getInt("id_audit"));
        a.setTabla(rs.getString("tabla"));
        a.setAccion(rs.getString("accion"));
        a.setUsuario(rs.getString("usuario"));
        a.setFechaHora(rs.getTimestamp("fecha_hora"));
        a.setIdRegistro(rs.getString("id_registro"));
        return a;
    }
}
