package modelo;

import Interfaces.CRUD;
import java.sql.*;
import java.util.*;

public class ListaDAO implements CRUD<Lista> {

    @Override
    public int agregar(Lista l) {
        String q = "INSERT INTO Tb_Listas (fk_Creador, Lista_Nombre, Codigo_Compartir) VALUES (?,?,?)";
        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, l.getFkCreador());
            ps.setString(2, l.getListaNombre());
            ps.setString(3, l.getCodigoCompartir());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al crear lista: " + e.getMessage());
            return 0;
        }
    }
    

    @Override
    public int actualizar(Lista l) {
        String q = "UPDATE Tb_Listas SET Lista_Nombre=? WHERE id_Lista=?";
        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setString(1, l.getListaNombre());
            ps.setInt(2, l.getIdLista());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar lista: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int eliminar(int id) {
        String q = "DELETE FROM Tb_Listas WHERE id_Lista=?";
        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar lista: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public Lista buscarPorId(int id) {
        String q = "SELECT * FROM Tb_Listas WHERE id_Lista=?";
        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapear(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar lista: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Lista> listarTodos() {
        List<Lista> listas = new ArrayList<>();
        String q = "SELECT * FROM Tb_Listas";
        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                listas.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar listas: " + e.getMessage());
        }
        return listas;
    }

    // Método extra: listas de un usuario (creadas + donde es miembro)
    public List<Lista> listarPorUsuario(int idUsuario) {
        List<Lista> listas = new ArrayList<>();
        
        String q = "SELECT DISTINCT l.* FROM Tb_Listas l "
                + "JOIN Tb_Lista_Miembros m ON l.id_Lista = m.fk_Lista "
                + "WHERE m.fk_Usuario = ?";;

        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listas.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar listas por usuario: " + e.getMessage());
        }
        return listas;
    }

    // Método extra: unirse a lista por código
    public Lista buscarPorCodigo(String codigo) {
        String q = "SELECT * FROM Tb_Listas WHERE Codigo_Compartir=?";
        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapear(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar por código: " + e.getMessage());
        }
        return null;
    }

    private Lista mapear(ResultSet rs) throws SQLException {
        Lista l = new Lista();
        l.setIdLista(rs.getInt("id_Lista"));
        l.setFkCreador(rs.getInt("fk_Creador"));
        l.setListaNombre(rs.getString("Lista_Nombre"));
        l.setCodigoCompartir(rs.getString("Codigo_Compartir"));
        return l;
    }
}
