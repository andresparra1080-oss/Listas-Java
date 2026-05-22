package modelo;

public class ListaItem {

    private int idItem;
    private int fkLista;
    private int fkProducto;
    private int fkAgregadoPor;   // solo el ID, no el objeto Usuario
    private Integer fkCompradoPor;   // Integer (nullable) porque puede ser null
    private double cantidad;
    private String unidad;
    private boolean comprado;

    // Campos extra para mostrar en JSP via JOIN (no van a la BD)
    private String nombreProducto;
    private String nombreAgregadoPor;

    public ListaItem() {
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int id) {
        this.idItem = id;
    }

    public int getFkLista() {
        return fkLista;
    }

    public void setFkLista(int fk) {
        this.fkLista = fk;
    }

    public int getFkProducto() {
        return fkProducto;
    }

    public void setFkProducto(int fk) {
        this.fkProducto = fk;
    }

    public int getFkAgregadoPor() {
        return fkAgregadoPor;
    }

    public void setFkAgregadoPor(int fk) {
        this.fkAgregadoPor = fk;
    }

    public Integer getFkCompradoPor() {
        return fkCompradoPor;
    }

    public void setFkCompradoPor(Integer fk) {
        this.fkCompradoPor = fk;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double c) {
        this.cantidad = c;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String u) {
        this.unidad = u;
    }

    public boolean isComprado() {
        return comprado;
    }

    public void setComprado(boolean c) {
        this.comprado = c;
    }

    // Getters/setters de los campos extra (JOIN)
    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String n) {
        this.nombreProducto = n;
    }

    public String getNombreAgregadoPor() {
        return nombreAgregadoPor;
    }

    public void setNombreAgregadoPor(String n) {
        this.nombreAgregadoPor = n;
    }
}
