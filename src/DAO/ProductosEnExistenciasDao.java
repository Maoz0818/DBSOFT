
package DAO;

import Logica.ProductosEnExistencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductosEnExistenciasDao {
    // CONEXION
    private Connection conexion;
    
    // SQL PARA CREAR UNA NUEVA EXISTENCIA ///////////////////////////////////////////////////////////////////////////////
    public PreparedStatement nuevaExistencia(ProductosEnExistencia proExist){
        
        PreparedStatement estado = null;
                
        try {
        conexion = ConexionDB.conectar();
            String sql = "insert into productos_en_existencia"
                    + " (almacenid, codigo_proveedor, productoid, cantidad) "
                    + " VALUES (?, ?, ?, ?)";
            
            estado = conexion.prepareStatement(sql);
         
            estado.setInt(1, proExist.getAlmacenid());
            estado.setInt(2, proExist.getCodigo_proveedor());
            estado.setInt(3, proExist.getProductoid());
            estado.setInt(4, proExist.getCantidad());
            
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return estado;
    }
    
    // SQL PARA MODIFICAR UNA EXISTENCIA /////////////////////////////////////////////////////////////////////////////////////
    public PreparedStatement modificarExistencia(ProductosEnExistencia proExist){
        
        PreparedStatement estado = null;
                
        try {
        conexion = ConexionDB.conectar();
            String sql = "UPDATE productos_en_existencia"
                    + " SET "
                    + " almacenid = ?, "
                    + " codigo_proveedor = ?, "
                    + " productoid = ?, "
                    + " cantidad = ?"
                    + " WHERE productoid = "+proExist.getProductoid();
            
            estado = conexion.prepareStatement(sql);
            
            estado.setInt(1, proExist.getAlmacenid());
            estado.setInt(2, proExist.getCodigo_proveedor());
            estado.setInt(3, proExist.getProductoid());
            estado.setInt(4, proExist.getCantidad());
            
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return estado;
    }
    
}
