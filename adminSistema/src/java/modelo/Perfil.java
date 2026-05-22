package modelo;

import java.sql.Date;

public class Perfil {
    private int idPerfil;
    private int fkIdUsuario;
    private Date fechaNacimiento;
    private String telefono;
    private String direccion;

    public Perfil() {
    }

    public Perfil(int idPerfil, int fkIdUsuario, Date fechaNacimiento, String telefono, String direccion) {
        this.idPerfil = idPerfil;
        this.fkIdUsuario = fkIdUsuario;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // Getters y Setters
    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public int getFkIdUsuario() {
        return fkIdUsuario;
    }

    public void setFkIdUsuario(int fkIdUsuario) {
        this.fkIdUsuario = fkIdUsuario;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}