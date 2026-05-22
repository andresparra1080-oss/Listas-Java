package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

    public LoginDAO() {
    }

    public Usuario Login_datos(String email, String clave) {
        Usuario datos = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Conexion cn = new Conexion();
            conn = cn.crearConexion();

            stmt = conn.prepareStatement("SELECT * FROM tb_usuarios WHERE TRIM(User_Email) = ? AND TRIM(User_Password) = ?");
            stmt.setString(1, email);
            stmt.setString(2, clave);
            rs = stmt.executeQuery();

            if (rs.next()) {
                datos = new Usuario();
                datos.setEmail(rs.getString("email"));
                datos.setPassword(rs.getString("clave"));
            }

        } catch (SQLException e) {
            
            System.out.println("Database error during login: " + e.getMessage());
        } finally {
            
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }

        return datos;
    }
}
