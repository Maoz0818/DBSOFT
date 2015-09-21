
package Logica;


public class Productos {
    
    private Integer productoid;
    private int tipo;
    private String nombre;  
    private int precio; 
    private String marca;

    public Productos() {
    }

    public Productos(Integer productoid, int tipo, String nombre, int precio, String marca) {
        this.productoid = productoid;
        this.tipo = tipo;
        this.nombre = nombre;
        this.precio = precio;
        this.marca = marca;
    }
    
    public Productos(int tipo, String nombre, int precio, String marca) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.precio = precio;
        this.marca = marca;
    }

       
    public Integer getProductoid() {
        return productoid;
    }
    
    public int getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public String getMarca() {
        return marca;
    }

    public void setProductoid(Integer productoid) {
        this.productoid = productoid;
    }
    
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
    
}
