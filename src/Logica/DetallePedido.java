
package Logica;


public class DetallePedido {
    
    private Integer detallePedidoid;
    private int cantidad; 
    private String fechaPosibleEntrega;  
    private Integer pedidoid; 
    private Integer productoexistid;

    public DetallePedido() {
    }

    public DetallePedido(Integer detallePedidoid, int cantidad, String fechaPosibleEntrega, Integer pedidoid, Integer productoexistid) {
        this.detallePedidoid = detallePedidoid;
        this.cantidad = cantidad;
        this.fechaPosibleEntrega = fechaPosibleEntrega;
        this.pedidoid = pedidoid;
        this.productoexistid = productoexistid;
    }
    
    public DetallePedido(Integer pedidoid, Integer productoexistid, int cantidad) {
        this.pedidoid = pedidoid;
        this.productoexistid = productoexistid;
        this.cantidad = cantidad;
    }

    public Integer getDetallePedidoid() {
        return detallePedidoid;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getFechaPosibleEntrega() {
        return fechaPosibleEntrega;
    }

    public Integer getPedidoid() {
        return pedidoid;
    }

    public Integer getProductoexistid() {
        return productoexistid;
    }

    public void setDetallePedidoid(Integer detallePedidoid) {
        this.detallePedidoid = detallePedidoid;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setFechaPosibleEntrega(String fechaPosibleEntrega) {
        this.fechaPosibleEntrega = fechaPosibleEntrega;
    }

    public void setPedidoid(Integer pedidoid) {
        this.pedidoid = pedidoid;
    }

    public void setProductoexistid(Integer productoexistid) {
        this.productoexistid = productoexistid;
    }
    
}
