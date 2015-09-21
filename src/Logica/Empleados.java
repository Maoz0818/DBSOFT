
package Logica;


public class Empleados {
    
    private Integer empleadoid;
    private String cargo;  
    private String nombre;  
    private String apellido;  
    private String eMail;

    public Empleados() {
    }

    public Empleados(Integer empleadoid, String cargo, String nombre, String apellido, String eMail) {
        this.empleadoid = empleadoid;
        this.cargo = cargo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.eMail = eMail;
    }

    public Integer getEmpleadoid() {
        return empleadoid;
    }

    public String getCargo() {
        return cargo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String geteMail() {
        return eMail;
    }

    public void setEmpleadoid(Integer empleadoid) {
        this.empleadoid = empleadoid;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
    
    
}
