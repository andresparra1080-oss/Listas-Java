package modelo;

public class Lista {

    private int idLista;
    private int fkCreador;
    private String listaNombre;
    private String codigoCompartir;

    public Lista() {
    }

    public Lista(int idLista, int fkCreador, String listaNombre, String codigoCompartir) {
        this.idLista = idLista;
        this.fkCreador = fkCreador;
        this.listaNombre = listaNombre;
        this.codigoCompartir = codigoCompartir;
    }

    public int getIdLista() {
        return idLista;
    }

    public void setIdLista(int id) {
        this.idLista = id;
    }

    public int getFkCreador() {
        return fkCreador;
    }

    public void setFkCreador(int fk) {
        this.fkCreador = fk;
    }

    public String getListaNombre() {
        return listaNombre;
    }

    public void setListaNombre(String n) {
        this.listaNombre = n;
    }

    public String getCodigoCompartir() {
        return codigoCompartir;
    }

    public void setCodigoCompartir(String c) {
        this.codigoCompartir = c;
    }
}
