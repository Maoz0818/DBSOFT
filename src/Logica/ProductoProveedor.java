
package Logica;


public class ProductoProveedor {
    
    private Integer prodProvid;
    private Almacen almacenid;
    private Integer productoid;    
    private Integer rut;

    public ProductoProveedor() {
    }

    public ProductoProveedor(Integer prodProvid, Almacen almacenid, Integer productoid, Integer rut) {
        this.prodProvid = prodProvid;
        this.almacenid = almacenid;
        this.productoid = productoid;
        this.rut = rut;
    }

    public Integer getProdProvid() {
        return prodProvid;
    }

    public Almacen getAlmacenid() {
        return almacenid;
    }

    public Integer getProductoid() {
        return productoid;
    }

    public Integer getRut() {
        return rut;
    }

    public void setProdProvid(Integer prodProvid) {
        this.prodProvid = prodProvid;
    }

    public void setAlmacenid(Almacen almacenid) {
        this.almacenid = almacenid;
    }

    public void setProductoid(Integer productoid) {
        this.productoid = productoid;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
    }
    
}
