
package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AlmacenDao {
    // CONEXION
    private Connection conexion;
    
    // CONSULTA PARA COMBOBOX ALMACEN
    public ResultSet comboBoxUbicacion(){
        
        ResultSet resultadoUbicacion = null;
                
        try {
        conexion = ConexionDB.conectar();
        String slqTipo = "SELECT almacenid, seccion FROM almacen order by almacenid";
        resultadoUbicacion = conexion.createStatement().executeQuery(slqTipo);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return resultadoUbicacion;
    }
}
