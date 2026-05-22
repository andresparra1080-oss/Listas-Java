package modelo;

import Interfaces.CRUD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionActividadDAO implements CRUD<GestionActividad> {

    @Override
    public int agregar(GestionActividad g) {
        String q = "INSERT INTO Tb_Gestion_Actividades (fk_idPerfil, fk_idActividad, id_lista_asociada, detalle, resultado) VALUES (?,?,?,?,?)";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, g.getFkIdPerfil());
            ps.setInt(2, g.getFkIdActividad());
            if (g.getIdListaAsociada() != null) {
                ps.setInt(3, g.getIdListaAsociada());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setString(4, g.getDetalle());
            ps.setString(5, g.getResultado());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al agregar gestión: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int actualizar(GestionActividad g) {
        String q = "UPDATE Tb_Gestion_Actividades SET fk_idPerfil=?, fk_idActividad=?, id_lista_asociada=?, detalle=?, resultado=? WHERE id_Gestion=?";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, g.getFkIdPerfil());
            ps.setInt(2, g.getFkIdActividad());
            if (g.getIdListaAsociada() != null) {
                ps.setInt(3, g.getIdListaAsociada());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setString(4, g.getDetalle());
            ps.setString(5, g.getResultado());
            ps.setInt(6, g.getIdGestion());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar gestión: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int eliminar(int id) {
        String q = "DELETE FROM Tb_Gestion_Actividades WHERE id_Gestion=?";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar gestión: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public GestionActividad buscarPorId(int id) {
        String q = "SELECT * FROM Tb_Gestion_Actividades WHERE id_Gestion=?";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapear(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar gestión: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<GestionActividad> listarTodos() {
        List<GestionActividad> lista = new ArrayList<>();
        String q = "SELECT * FROM Tb_Gestion_Actividades ORDER BY fecha_hora DESC";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar gestiones: " + e.getMessage());
        }
        return lista;
    }

    // Extra: historial de una lista concreta
    public List<GestionActividad> listarPorLista(int idLista) {
        List<GestionActividad> lista = new ArrayList<>();
        String q = "SELECT * FROM Tb_Gestion_Actividades WHERE id_lista_asociada = ? ORDER BY fecha_hora DESC";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, idLista);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar gestiones por lista: " + e.getMessage());
        }
        return lista;
    }

    // Extra: historial de un perfil/usuario
    public List<GestionActividad> listarPorPerfil(int idPerfil) {
        List<GestionActividad> lista = new ArrayList<>();
        String q = "SELECT * FROM Tb_Gestion_Actividades WHERE fk_idPerfil = ? ORDER BY fecha_hora DESC";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, idPerfil);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar gestiones por perfil: " + e.getMessage());
        }
        return lista;
    }

    private GestionActividad mapear(ResultSet rs) throws SQLException {
        GestionActividad g = new GestionActividad();
        g.setIdGestion(rs.getInt("id_Gestion"));
        g.setFkIdPerfil(rs.getInt("fk_idPerfil"));
        g.setFkIdActividad(rs.getInt("fk_idActividad"));
        int idLista = rs.getInt("id_lista_asociada");
        if (rs.wasNull()) {
            g.setIdListaAsociada(null);
        } else {
            g.setIdListaAsociada(idLista);
        }
        g.setFechaHora(rs.getTimestamp("fecha_hora"));
        g.setDetalle(rs.getString("detalle"));
        g.setResultado(rs.getString("resultado"));
        return g;
    }
}
