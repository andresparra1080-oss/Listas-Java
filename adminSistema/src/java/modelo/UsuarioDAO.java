
package modelo;

import Interfaces.CRUD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static modelo.Conexion.clave;


public class UsuarioDAO implements CRUD<Usuario> {

    @Override
    public int agregar(Usuario u) {
        String q = "INSERT INTO Tb_Usuarios (User_Nombre, User_Apellido, User_Email, User_Password) VALUES (?,?,?,?)";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getPassword());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al agregar usuario: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int actualizar(Usuario u) {
        String q = "UPDATE Tb_Usuarios SET User_Nombre=?, User_Apellido=?, User_Email=? WHERE id_Usuario=?";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getEmail());
            ps.setInt(4, u.getIdUsuario());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int eliminar(int id) {
        String q = "DELETE FROM Tb_Usuarios WHERE id_Usuario=?";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public Usuario buscarPorId(int id) {
        String q = "SELECT * FROM Tb_Usuarios WHERE id_Usuario=?";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.out.println("Error al buscar usuario: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String q = "SELECT * FROM Tb_Usuarios";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }

    // Método extra: login
    public Usuario login(String email, String password) {
        String q = "SELECT * FROM Tb_Usuarios WHERE User_Email=? AND User_Password=?";
        try (Connection con = new Conexion().crearConexion();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.out.println("Error en login: " + e.getMessage());
        }
        return null;
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("id_Usuario"));
        u.setNombre(rs.getString("User_Nombre"));
        u.setApellido(rs.getString("User_Apellido"));
        u.setEmail(rs.getString("User_Email"));
        u.setPassword(rs.getString("User_Password"));
        return u;
    }
}