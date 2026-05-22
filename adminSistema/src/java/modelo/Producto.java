package modelo;

public class Producto {

    private int idProducto;
    private String nombre;
    private String categoria;

    public Producto() {
    }

    public Producto(int idProducto, String nombre, String categoria) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.categoria = categoria;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int id) {
        this.idProducto = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String n) {
        this.nombre = n;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String c) {
        this.categoria = c;
    }
}
