
package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class EstadosDao {
     // CONEXION
    private Connection conexion;
    
    // SQL PARA LLENAR EL COMBOBOX DE ESTADOS /////////////////////////////////////////////////////////////////////////////
    public ResultSet comboBoxEstadosDespacho(){
        
        ResultSet resultadoEstados = null;
                
        try {
        conexion = ConexionDB.conectar();
        String slqTipo = "SELECT estadoid, nombre FROM estados \n" +
        "order by estadoid";
        resultadoEstados = conexion.createStatement().executeQuery(slqTipo);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return resultadoEstados;
    }
    
}
