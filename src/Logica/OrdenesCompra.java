
package Logica;


public class OrdenesCompra {
    
    private Integer ordenid;  
    private String fechaOrden;  
    private Integer estado;  
    private Empleados empleadoid;
    private Integer codigo_proveedor;



    public OrdenesCompra() {
    }

    public OrdenesCompra(Integer ordenid, String fechaOrden, Integer estado, Empleados empleadoid, Integer codigo_proveedor) {
        this.ordenid = ordenid;
        this.fechaOrden = fechaOrden;
        this.estado = estado;
        this.empleadoid = empleadoid;
        this.codigo_proveedor=codigo_proveedor;
    }

    public Integer getOrdenid() {
        return ordenid;
    }

    public String getFechaOrden() {
        return fechaOrden;
    }

    public Integer getEstado() {
        return estado;
    }

    public Empleados getEmpleadoid() {
        return empleadoid;
    }

    public void setOrdenid(Integer ordenid) {
        this.ordenid = ordenid;
    }
        public Integer getCodigo_proveedor() {
        return codigo_proveedor;
    }

    public void setCodigo_proveedor(Integer codigo_proveedor) {
        this.codigo_proveedor = codigo_proveedor;
    }

    public void setFechaOrden(String fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public void setEmpleadoid(Empleados empleadoid) {
        this.empleadoid = empleadoid;
    }
    
    
}
