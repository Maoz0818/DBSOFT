
package Logica;


public class Despacho {
    
    private Integer despachoid;
    private Integer estado;
    private String fechaDespacho;
    private Integer empleadoid;
    private Integer pedidoid;

    public Despacho() {
    }
    
    public Despacho(Integer despachoid, Integer pedidoid, Integer empleadoid, Integer estado, String fechaDespacho) {
        this.despachoid = despachoid;
        this.estado = estado;
        this.fechaDespacho = fechaDespacho;
        this.empleadoid = empleadoid;
        this.pedidoid = pedidoid;
    }

    public Despacho(Integer pedidoid, Integer estado) {
        this.estado = estado;
        this.pedidoid = pedidoid;
    }
    
    public Despacho(Integer despachoid, Integer pedidoid, Integer estado) {
        this.despachoid = despachoid;
        this.estado = estado;
        this.pedidoid = pedidoid;
    }

    public Integer getDespachoid() {
        return despachoid;
    }

    public Integer getEstado() {
        return estado;
    }

    public String getFechaDespacho() {
        return fechaDespacho;
    }

    public Integer getEmpleadoid() {
        return empleadoid;
    }

    public Integer getPedidoid() {
        return pedidoid;
    }

    public void setDespachoid(Integer despachoid) {
        this.despachoid = despachoid;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public void setFechaDespacho(String fechaDespacho) {
        this.fechaDespacho = fechaDespacho;
    }

    public void setEmpleadoid(Integer empleadoid) {
        this.empleadoid = empleadoid;
    }

    public void setPedidoid(Integer pedidoid) {
        this.pedidoid = pedidoid;
    }
    
    
}
