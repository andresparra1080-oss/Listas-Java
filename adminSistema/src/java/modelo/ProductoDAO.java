package modelo;

import Interfaces.CRUD;
import java.sql.*;
import java.util.*;

public class ProductoDAO implements CRUD<Producto> {

    public ProductoDAO() {
    }

    @Override
    public int agregar(Producto p) {
        String q = "INSERT INTO Tb_Productos (Producto_Nombre, Producto_Categoria) VALUES (?,?)";
        try (Connection con = new Conexion().crearConexion()) {
            PreparedStatement ps = con.prepareStatement(q);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCategoria());
            return ps.executeUpdate();
        } catch (SQLException e) {System.out.println("Error al agregar producto: " + e.getMessage());
            return 0;}
}

    @Override
    public int actualizar(Producto p) {
        String q = "UPDATE Tb_Productos SET Producto_Nombre=?, Producto_Categoria=? WHERE id_Producto=?";
        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCategoria());
            ps.setInt(3, p.getIdProducto());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int eliminar(int id) {
        String q = "DELETE FROM Tb_Productos WHERE id_Producto=?";
        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public Producto buscarPorId(int id) {
        String q = "SELECT * FROM Tb_Productos WHERE id_Producto=?";
        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapear(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar producto: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Producto> listarTodos() {
        List<Producto> lista = new ArrayList<>();
        String q = "SELECT * FROM Tb_Productos ORDER BY Producto_Categoria, Producto_Nombre";
        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }

    // Método extra: buscar por categoría
    public List<Producto> listarPorCategoria(String categoria) {
        List<Producto> lista = new ArrayList<>();
        String q = "SELECT * FROM Tb_Productos WHERE Producto_Categoria=? ORDER BY Producto_Nombre";
        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setString(1, categoria);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar por categoría: " + e.getMessage());
        }
        return lista;
    }

    private Producto mapear(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setIdProducto(rs.getInt("id_Producto"));
        p.setNombre(rs.getString("Producto_Nombre"));
        p.setCategoria(rs.getString("Producto_Categoria"));
        return p;
    }
    // Dentro de ProductoDAO

    public List<Producto> listarFiltrado(String categoria, String nombre) {
        List<Producto> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Tb_Productos WHERE 1=1 ");

        if (categoria != null && !categoria.isEmpty()) {
            sql.append("AND Producto_Categoria = ? ");
        }
        if (nombre != null && !nombre.isEmpty()) {
            sql.append("AND Producto_Nombre LIKE ? ");
        }
        sql.append("ORDER BY id_Producto DESC");

        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (categoria != null && !categoria.isEmpty()) {
                ps.setString(paramIndex++, categoria);
            }
            if (nombre != null && !nombre.isEmpty()) {
                ps.setString(paramIndex++, "%" + nombre + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos filtrados: " + e.getMessage());
        }
        return lista;
    }
}
