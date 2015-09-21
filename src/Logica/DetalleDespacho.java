
package Logica;


public class DetalleDespacho {
    
    private Integer detalleDespachoid;
    private int cantidad;
    private Integer despachoid;
    private Integer productoexistid;

    public DetalleDespacho() {
    }

    public DetalleDespacho(Integer detalleDespachoid, int cantidad, Integer despachoid, Integer productoexistid) {
        this.detalleDespachoid = detalleDespachoid;
        this.cantidad = cantidad;
        this.despachoid = despachoid;
        this.productoexistid = productoexistid;
    }
    
    public DetalleDespacho(Integer despachoid, Integer productoexistid, int cantidad) {
        this.cantidad = cantidad;
        this.despachoid = despachoid;
        this.productoexistid = productoexistid;
    }
      
    public Integer getDetalleDespachoid() {
        return detalleDespachoid;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Integer getDespachoid() {
        return despachoid;
    }

    public Integer getProductoexistid() {
        return productoexistid;
    }

    public void setDetalleDespachoid(Integer detalleDespachoid) {
        this.detalleDespachoid = detalleDespachoid;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setDespachoid(Integer despachoid) {
        this.despachoid = despachoid;
    }

    public void setProductoexistid(Integer productoexistid) {
        this.productoexistid = productoexistid;
    }
    
}
