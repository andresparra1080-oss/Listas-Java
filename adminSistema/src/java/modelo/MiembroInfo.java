package modelo;

public class MiembroInfo {

    private int idUsuario;
    private String nombre;
    private int idRol;
    private String rolNombre;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int id) {
        this.idUsuario = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String n) {
        this.nombre = n;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int id) {
        this.idRol = id;
    }

    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String r) {
        this.rolNombre = r;
    }
}
