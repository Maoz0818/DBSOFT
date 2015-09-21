
package Logica;


public class Sucursal {
    
    private Integer sucursalid;  
    private String nombre;   
    private String eMail;   
    private String ciudad;  
    private String direccion;  
    private String pais;  
    private int telefono;

    public Sucursal() {
    }

    public Sucursal(Integer sucursalid, String nombre, String eMail, String ciudad, String direccion, String pais, int telefono) {
        this.sucursalid = sucursalid;
        this.nombre = nombre;
        this.eMail = eMail;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.pais = pais;
        this.telefono = telefono;
    }
    
    public Sucursal(Integer sucursalid) {
        this.sucursalid = sucursalid;
    }

    public Integer getSucursalid() {
        return sucursalid;
    }

    public String getNombre() {
        return nombre;
    }

    public String geteMail() {
        return eMail;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getPais() {
        return pais;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setSucursalid(Integer sucursalid) {
        this.sucursalid = sucursalid;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
    
}
