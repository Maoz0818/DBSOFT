
package Logica;


public class ProductosEnExistencia {
    
    private Integer productoexistid;
    private int cantidad;      
    private Integer almacenid;   
    private Integer productoid;    
    private Integer codigo_proveedor;

    public ProductosEnExistencia() {
    }

    public ProductosEnExistencia(Integer productoexistid, Integer almacenid, Integer codigo_proveedor, Integer productoid, int cantidad) {
        this.productoexistid = productoexistid;
        this.cantidad = cantidad;
        this.almacenid = almacenid;
        this.productoid = productoid;
        this.codigo_proveedor = codigo_proveedor;
    }
    
    public ProductosEnExistencia(Integer almacenid, Integer rut, Integer productoid, int cantidad, Integer codigo_proveedor) {
        this.cantidad = cantidad;
        this.almacenid = almacenid;
        this.productoid = productoid;
        this.codigo_proveedor = codigo_proveedor;
    }

    public Integer getProductoexistid() {
        return productoexistid;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Integer getAlmacenid() {
        return almacenid;
    }

    public Integer getProductoid() {
        return productoid;
    }

    public Integer getCodigo_proveedor() {
        return codigo_proveedor;
    }

    public void setProductoexistid(Integer productoexistid) {
        this.productoexistid = productoexistid;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setAlmacenid(Integer almacenid) {
        this.almacenid = almacenid;
    }

    public void setProductoid(Integer productoid) {
        this.productoid = productoid;
    }

    public void setCodigo_proveedor(Integer codigo_proveedor) {
        this.codigo_proveedor = codigo_proveedor;
    }
    
    
}
