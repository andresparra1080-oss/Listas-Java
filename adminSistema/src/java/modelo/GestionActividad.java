package modelo;

import java.sql.Timestamp;

public class GestionActividad {
    private int idGestion;
    private int fkIdPerfil;
    private int fkIdActividad;
    private Integer idListaAsociada;  // puede ser null
    private Timestamp fechaHora;
    private String detalle;
    private String resultado;

    public GestionActividad() {
    }

    public GestionActividad(int idGestion, int fkIdPerfil, int fkIdActividad, Integer idListaAsociada,
                            Timestamp fechaHora, String detalle, String resultado) {
        this.idGestion = idGestion;
        this.fkIdPerfil = fkIdPerfil;
        this.fkIdActividad = fkIdActividad;
        this.idListaAsociada = idListaAsociada;
        this.fechaHora = fechaHora;
        this.detalle = detalle;
        this.resultado = resultado;
    }

    // Getters y Setters
    public int getIdGestion() {
        return idGestion;
    }

    public void setIdGestion(int idGestion) {
        this.idGestion = idGestion;
    }

    public int getFkIdPerfil() {
        return fkIdPerfil;
    }

    public void setFkIdPerfil(int fkIdPerfil) {
        this.fkIdPerfil = fkIdPerfil;
    }

    public int getFkIdActividad() {
        return fkIdActividad;
    }

    public void setFkIdActividad(int fkIdActividad) {
        this.fkIdActividad = fkIdActividad;
    }

    public Integer getIdListaAsociada() {
        return idListaAsociada;
    }

    public void setIdListaAsociada(Integer idListaAsociada) {
        this.idListaAsociada = idListaAsociada;
    }

    public Timestamp getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
