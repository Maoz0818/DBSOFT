
package Logica;


public class Proveedores {
    
    private Integer codigoProveedor;
    private Integer rut;  
    private Integer telefono;
    private String nombre;   
    private String pais;   
    private String ciudad;
    private String eMail;

    public Proveedores() {
    }

    public Proveedores(Integer codigoProveedor, Integer rut, Integer telefono, String nombre, String pais, String ciudad, String eMail) {
        this.codigoProveedor = codigoProveedor;
        this.rut = rut;
        this.telefono = telefono;
        this.nombre = nombre;
        this.pais = pais;
        this.ciudad = ciudad;
        this.eMail = eMail;
    }
    
    public Integer getCodigoProveedor(){
        return codigoProveedor;
    }

    public Integer getRut() {
        return rut;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPais() {
        return pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String geteMail() {
        return eMail;
    }
    
    public void setCodigoProveedor(Integer codigoProveedor){
        this.codigoProveedor = codigoProveedor;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
    
    
}
