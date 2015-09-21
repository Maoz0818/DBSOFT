
package Logica;


public class Almacen {
    private Integer almacenid;
    private String seccion;
    private int pasillo;
    private String estanteria;
    
    public Almacen(){};
    
    public Almacen(Integer almacenid, String seccion, int pasillo, String estanteria) {
        this.almacenid = almacenid;
        this.seccion = seccion;
        this.pasillo = pasillo;
        this.estanteria = estanteria;
    }

    public Integer getAlmacenid() {
        return almacenid;
    }

    public String getSeccion() {
        return seccion;
    }

    public int getPasillo() {
        return pasillo;
    }

    public String getEstanteria() {
        return estanteria;
    }

    public void setAlmacenid(Integer almacenid) {
        this.almacenid = almacenid;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public void setPasillo(int pasillo) {
        this.pasillo = pasillo;
    }

    public void setEstanteria(String estanteria) {
        this.estanteria = estanteria;
    }
    
     
}
