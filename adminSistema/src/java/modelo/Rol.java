package modelo;

public class Rol {

    private int idRol;
    private String rolNombre;
    private boolean puedeAgregar;
    private boolean puedeMarcar;
    private boolean puedeEliminar;
    private boolean puedeGestionar;

    public Rol() {
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

    public void setRolNombre(String n) {
        this.rolNombre = n;
    }

    public boolean isPuedeAgregar() {
        return puedeAgregar;
    }

    public void setPuedeAgregar(boolean b) {
        this.puedeAgregar = b;
    }

    public boolean isPuedeMarcar() {
        return puedeMarcar;
    }

    public void setPuedeMarcar(boolean b) {
        this.puedeMarcar = b;
    }

    public boolean isPuedeEliminar() {
        return puedeEliminar;
    }

    public void setPuedeEliminar(boolean b) {
        this.puedeEliminar = b;
    }

    public boolean isPuedeGestionar() {
        return puedeGestionar;
    }

    public void setPuedeGestionar(boolean b) {
        this.puedeGestionar = b;
    }
}
