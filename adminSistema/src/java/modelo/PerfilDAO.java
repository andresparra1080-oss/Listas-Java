package modelo;

import Interfaces.CRUD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PerfilDAO implements CRUD<Perfil> {

    @Override
    public int agregar(Perfil p) {
        String q = "INSERT INTO Tb_Perfil (fk_idUsuario, fecha_nacimiento, telefono, direccion) VALUES (?,?,?,?)";
        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, p.getFkIdUsuario());
            ps.setDate(2, p.getFechaNacimiento());
            ps.setString(3, p.getTelefono());
            ps.setString(4, p.getDireccion());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al agregar perfil: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int actualizar(Perfil p) {
        String q = "UPDATE Tb_Perfil SET fecha_nacimiento=?, telefono=?, direccion=? WHERE id_Perfil=?";
        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setDate(1, p.getFechaNacimiento());
            ps.setString(2, p.getTelefono());
            ps.setString(3, p.getDireccion());
            ps.setInt(4, p.getIdPerfil());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar perfil: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int eliminar(int id) {
        String q = "DELETE FROM Tb_Perfil WHERE id_Perfil=?";
        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar perfil: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public Perfil buscarPorId(int id) {
        String q = "SELECT * FROM Tb_Perfil WHERE id_Perfil=?";
        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapear(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar perfil: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Perfil> listarTodos() {
        List<Perfil> lista = new ArrayList<>();
        String q = "SELECT * FROM Tb_Perfil";
        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar perfiles: " + e.getMessage());
        }
        return lista;
    }

    // Extra: buscar perfil por ID de usuario (relación 1 a 1)
    public Perfil buscarPorUsuario(int idUsuario) {
        String q = "SELECT * FROM Tb_Perfil WHERE fk_idUsuario = ?";
        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapear(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Perfil mapear(ResultSet rs) throws SQLException {
        Perfil p = new Perfil();
        p.setIdPerfil(rs.getInt("id_Perfil"));
        p.setFkIdUsuario(rs.getInt("fk_idUsuario"));
        p.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
        p.setTelefono(rs.getString("telefono"));
        p.setDireccion(rs.getString("direccion"));
        return p;
    }
}
