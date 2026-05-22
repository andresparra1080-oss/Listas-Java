package modelo;

import Interfaces.CRUD;
import java.sql.*;
import java.util.*;

public class RolDAO implements CRUD<Rol> {

    @Override
    public int agregar(Rol r) {
        String q = "INSERT INTO Tb_Roles (Rol_Nombre, Puede_Agregar, Puede_Marcar, Puede_Eliminar, Puede_Gestionar) VALUES (?,?,?,?,?)";
        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setString(1, r.getRolNombre());
            ps.setBoolean(2, r.isPuedeAgregar());
            ps.setBoolean(3, r.isPuedeMarcar());
            ps.setBoolean(4, r.isPuedeEliminar());
            ps.setBoolean(5, r.isPuedeGestionar());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al agregar rol: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int actualizar(Rol r) {
        String q = "UPDATE Tb_Roles SET Rol_Nombre=?, Puede_Agregar=?, Puede_Marcar=?, Puede_Eliminar=?, Puede_Gestionar=? WHERE id_Rol=?";
        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setString(1, r.getRolNombre());
            ps.setBoolean(2, r.isPuedeAgregar());
            ps.setBoolean(3, r.isPuedeMarcar());
            ps.setBoolean(4, r.isPuedeEliminar());
            ps.setBoolean(5, r.isPuedeGestionar());
            ps.setInt(6, r.getIdRol());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar rol: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int eliminar(int id) {
        String q = "DELETE FROM Tb_Roles WHERE id_Rol=?";
        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar rol: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public Rol buscarPorId(int id) {
        String q = "SELECT * FROM Tb_Roles WHERE id_Rol=?";
        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapear(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar rol: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Rol> listarTodos() {
        List<Rol> lista = new ArrayList<>();
        String q = "SELECT * FROM Tb_Roles ORDER BY id_Rol";
        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar roles: " + e.getMessage());
        }
        return lista;
    }

    private Rol mapear(ResultSet rs) throws SQLException {
        Rol r = new Rol();
        r.setIdRol(rs.getInt("id_Rol"));
        r.setRolNombre(rs.getString("Rol_Nombre"));
        r.setPuedeAgregar(rs.getBoolean("Puede_Agregar"));
        r.setPuedeMarcar(rs.getBoolean("Puede_Marcar"));
        r.setPuedeEliminar(rs.getBoolean("Puede_Eliminar"));
        r.setPuedeGestionar(rs.getBoolean("Puede_Gestionar"));
        return r;
    }
}
