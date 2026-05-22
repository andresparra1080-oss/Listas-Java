package modelo;

import Interfaces.CRUD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActividadDAO implements CRUD<Actividad> {

    @Override
    public int agregar(Actividad a) {
        String q = "INSERT INTO Tb_Actividades (nombre_actividad, descripcion, tipo_actividad) VALUES (?,?,?)";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setString(1, a.getNombreActividad());
            ps.setString(2, a.getDescripcion());
            ps.setString(3, a.getTipoActividad());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al agregar actividad: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int actualizar(Actividad a) {
        String q = "UPDATE Tb_Actividades SET nombre_actividad=?, descripcion=?, tipo_actividad=? WHERE id_Actividad=?";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setString(1, a.getNombreActividad());
            ps.setString(2, a.getDescripcion());
            ps.setString(3, a.getTipoActividad());
            ps.setInt(4, a.getIdActividad());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar actividad: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int eliminar(int id) {
        String q = "DELETE FROM Tb_Actividades WHERE id_Actividad=?";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar actividad: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public Actividad buscarPorId(int id) {
        String q = "SELECT * FROM Tb_Actividades WHERE id_Actividad=?";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapear(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar actividad: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Actividad> listarTodos() {
        List<Actividad> lista = new ArrayList<>();
        String q = "SELECT * FROM Tb_Actividades";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar actividades: " + e.getMessage());
        }
        return lista;
    }

    private Actividad mapear(ResultSet rs) throws SQLException {
        Actividad a = new Actividad();
        a.setIdActividad(rs.getInt("id_Actividad"));
        a.setNombreActividad(rs.getString("nombre_actividad"));
        a.setDescripcion(rs.getString("descripcion"));
        a.setTipoActividad(rs.getString("tipo_actividad"));
        return a;
    }
}