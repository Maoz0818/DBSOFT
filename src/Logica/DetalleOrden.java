
package Logica;


public class DetalleOrden {
    
    private Integer detalleid;
    private String plazoEntrega;
    private Integer cantidad;
    private Integer ordenid;
    private Integer productoexistid;

    public DetalleOrden() {
    }

    public DetalleOrden(Integer detalleid, String plazoEntrega, int cantidad, Integer ordenid, Integer productoexistid) {
        this.detalleid = detalleid;
        this.plazoEntrega = plazoEntrega;
        this.cantidad = cantidad;
        this.ordenid = ordenid;
        this.productoexistid = productoexistid;
    }

    public Integer getDetalleid() {
        return detalleid;
    }

    public String getPlazoEntrega() {
        return plazoEntrega;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Integer getOrdenid() {
        return ordenid;
    }

    public Integer getProductoexistid() {
        return productoexistid;
    }

    public void setDetalleid(Integer detalleid) {
        this.detalleid = detalleid;
    }

    public void setPlazoEntrega(String plazoEntrega) {
        this.plazoEntrega = plazoEntrega;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setOrdenid(Integer ordenid) {
        this.ordenid = ordenid;
    }

    public void setProductoexistid(Integer productoexistid) {
        this.productoexistid = productoexistid;
    }
    
    
}
