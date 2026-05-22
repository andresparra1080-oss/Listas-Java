package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class AuditoriaModificacion {
    private int idAudit;
    private String tabla;
    private String accion;
    private String usuario;
    private Timestamp fechaHora;
    private String idRegistro;
    private String descripcion;

    public AuditoriaModificacion() {}

    public AuditoriaModificacion(int idAudit, String tabla, String accion, String usuario,
                                 Timestamp fechaHora, String idRegistro, String descripcion) {
        this.idAudit = idAudit;
        this.tabla = tabla;
        this.accion = accion;
        this.usuario = usuario;
        this.fechaHora = fechaHora;
        this.idRegistro = idRegistro;
        this.descripcion = descripcion;
    }

    public int getIdAudit() { return idAudit; }
    public void setIdAudit(int idAudit) { this.idAudit = idAudit; }
    public String getTabla() { return tabla; }
    public void setTabla(String tabla) { this.tabla = tabla; }
    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public Timestamp getFechaHora() { return fechaHora; }
    public void setFechaHora(Timestamp fechaHora) { this.fechaHora = fechaHora; }
    public String getIdRegistro() { return idRegistro; }
    public void setIdRegistro(String idRegistro) { this.idRegistro = idRegistro; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
private AuditoriaModificacion mapear(ResultSet rs) throws SQLException {
    AuditoriaModificacion a = new AuditoriaModificacion();
    a.setIdAudit(rs.getInt("id_audit"));
    a.setTabla(rs.getString("tabla"));
    a.setAccion(rs.getString("accion"));
    a.setUsuario(rs.getString("usuario"));
    a.setFechaHora(rs.getTimestamp("fecha_hora"));
    a.setIdRegistro(rs.getString("id_registro"));
    a.setDescripcion(rs.getString("descripcion"));
    return a;
}
}

