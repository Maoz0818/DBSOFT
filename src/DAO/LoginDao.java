
package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginDao {
    // CONEXION     
    private Connection conexion;
    
    // SQL PARA CONSULTA DE EMPLEADOS ////////////////////////////////////////////////////////////////////////////////////////
    public ResultSet loginEmpleados(String correo, String codigo){
        
     ResultSet resuladoConsultaEmpleados = null;
    
     try {
            conexion = ConexionDB.conectar();
            String sql = "SELECT * FROM "
                    + " empleados WHERE "
                    + " e_mail = '"+correo+"' AND "
                    + " empleadoid = "+codigo;
            resuladoConsultaEmpleados = conexion.createStatement().executeQuery(sql);
                    
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return resuladoConsultaEmpleados;
     }
    
    // SQL PARA CONSULTA DE SUCURSALES ////////////////////////////////////////////////////////////////////////////////////////
    public ResultSet loginSucursales(String correo, String codigo){
        
     ResultSet resuladoConsultaSucursales = null;
    
     try {
            conexion = ConexionDB.conectar();
            String sql = "SELECT * FROM "
                    + " sucursal WHERE "
                    + " e_mail = '"+correo+"' AND "
                    + " sucursalid = "+codigo;
            resuladoConsultaSucursales = conexion.createStatement().executeQuery(sql);
                    
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return resuladoConsultaSucursales;
     }
    
}
