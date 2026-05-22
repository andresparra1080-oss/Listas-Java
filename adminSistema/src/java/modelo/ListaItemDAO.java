package modelo;

import Interfaces.CRUD;
import java.sql.*;
import java.util.*;

public class ListaItemDAO implements CRUD<ListaItem> {

    @Override
    public int agregar(ListaItem item) {
        String q = "INSERT INTO Tb_Lista_Items "
                + "(fk_Lista, fk_Producto, fk_Agregado_Por, Cantidad, Unidad, Comprado) "
                + "VALUES (?,?,?,?,?,?)";

        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, item.getFkLista());
            ps.setInt(2, item.getFkProducto());
            ps.setInt(3, item.getFkAgregadoPor());
            ps.setDouble(4, item.getCantidad());
            ps.setString(5, item.getUnidad());
            ps.setBoolean(6, item.isComprado());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al agregar item: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int actualizar(ListaItem item) {
        String q = "UPDATE Tb_Lista_Items SET Cantidad=?, Unidad=? WHERE id_Item=?";
        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setDouble(1, item.getCantidad());
            ps.setString(2, item.getUnidad());
            ps.setInt(3, item.getIdItem());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar item: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int eliminar(int id) {
        String q = "DELETE FROM Tb_Lista_Items WHERE id_Item=?";
        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar item: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public ListaItem buscarPorId(int id) {
        String q = "SELECT i.*, p.Producto_Nombre, u.User_Nombre AS nombre_agregado "
                + "FROM Tb_Lista_Items i "
                + "JOIN Tb_Productos p ON i.fk_Producto = p.id_Producto "
                + "JOIN Tb_Usuarios u ON i.fk_Agregado_Por = u.id_Usuario "
                + "WHERE i.id_Item = ?";

        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapear(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar item: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<ListaItem> listarTodos() {
        return listarPorLista(-1); // no se usa directo
    }

    // Método principal: todos los items de una lista con JOIN
    public List<ListaItem> listarPorLista(int idLista) {
        List<ListaItem> items = new ArrayList<>();
        String q = "SELECT i.*, p.Producto_Nombre, u.User_Nombre AS nombre_agregado "
                + "FROM Tb_Lista_Items i "
                + "JOIN Tb_Productos p ON i.fk_Producto = p.id_Producto "
                + "JOIN Tb_Usuarios u ON i.fk_Agregado_Por = u.id_Usuario "
                + "WHERE i.fk_Lista = ? "
                + "ORDER BY i.Comprado ASC, i.id_Item DESC";
        
        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, idLista);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                items.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar items: " + e.getMessage());
        }
        return items;
    }

        public int marcarComprado(int idItem, int idUsuario) {
        String q = "UPDATE Tb_Lista_Items SET Comprado=1, fk_Comprado_Por=? WHERE id_Item=?";
        try (Connection con = new Conexion().crearConexion(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idItem);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al marcar comprado: " + e.getMessage());
            return 0;
        }
    }

    private ListaItem mapear(ResultSet rs) throws SQLException {
        ListaItem item = new ListaItem();
        item.setIdItem(rs.getInt("id_Item"));
        item.setFkLista(rs.getInt("fk_Lista"));
        item.setFkProducto(rs.getInt("fk_Producto"));
        item.setFkAgregadoPor(rs.getInt("fk_Agregado_Por"));
        item.setFkCompradoPor((Integer) rs.getObject("fk_Comprado_Por")); // soporta null
        item.setCantidad(rs.getDouble("Cantidad"));
        item.setUnidad(rs.getString("Unidad"));
        item.setComprado(rs.getBoolean("Comprado"));
        item.setNombreProducto(rs.getString("Producto_Nombre"));
        item.setNombreAgregadoPor(rs.getString("nombre_agregado"));
        return item;
    }
}
