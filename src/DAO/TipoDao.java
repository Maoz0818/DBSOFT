
package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TipoDao {
    // CONEXION
    private Connection conexion;
    
    // SQL PARA LLENER EL COMBOBOX DE TIPO ////////////////////////////////////////////////////////////////////////////////
    public ResultSet comboBoxTipo(){
        
        ResultSet resultadoTipo = null;
                
        try {
        conexion = ConexionDB.conectar();
        String slqTipo = "SELECT tipoid, nombre FROM tipo order by tipoid";
        resultadoTipo = conexion.createStatement().executeQuery(slqTipo);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return resultadoTipo;
    }
    
}
