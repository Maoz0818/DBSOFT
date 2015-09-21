
package Logica;


public class Pedidos {
    
    private Integer pedidoid; 
    private Integer estado;
    private String fechaPedido;  
    private Integer sucursalid;

    public Pedidos() {
    }

    public Pedidos(Integer pedidoid, Integer sucursalid, Integer estado) {
        this.pedidoid = pedidoid;
        this.sucursalid = sucursalid;
        this.estado = estado;
    }
    
    public Pedidos(Integer sucursalid, Integer estado) {
        this.sucursalid = sucursalid;
        this.estado = estado;
    }

    public Integer getPedidoid() {
        return pedidoid;
    }

    public Integer getEstado() {
        return estado;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public Integer getSucursalid() {
        return sucursalid;
    }

    public void setPedidoid(Integer pedidoid) {
        this.pedidoid = pedidoid;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public void setSucursalid(Integer sucursalid) {
        this.sucursalid = sucursalid;
    }
    
    
}
