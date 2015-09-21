
package Logica;


public class Tipo {
    
    private Integer tipoid;
    private String nombre;

    public Tipo() {
    }

    public Tipo(Integer tipoid, String nombre) {
        this.tipoid = tipoid;
        this.nombre = nombre;
    }

    public Integer getTipoid() {
        return tipoid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setTipoid(Integer tipoid) {
        this.tipoid = tipoid;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
