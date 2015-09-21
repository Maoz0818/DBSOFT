
package DAO;

import Logica.DetalleDespacho;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DetalleDespachoDao {
   // CONEXION
   private Connection conexion;
   
   // SQL PARA NUEVO DETALLE DE DESPACHO ///////////////////////////////////////////////////////////////////////////////////
   public PreparedStatement nuevoDetalle(DetalleDespacho detDes){
        
        PreparedStatement estado = null;
                
        try {
        conexion = ConexionDB.conectar();
            String sql = "insert into detalle_despacho "
                    + " (despachoid, productoexistid, cantidad) "
                    + " VALUES (?, ?, ?)";
            
            estado = conexion.prepareStatement(sql);
            
            estado.setInt(1, detDes.getDespachoid());
            estado.setInt(2, detDes.getProductoexistid());
            estado.setInt(3, detDes.getCantidad());
           
            
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return estado;
    }
   
   // SQL PARA MODIFICAR UN DETALLE DE DESPACHO /////////////////////////////////////////////////////////////////////////////
   public PreparedStatement modificarDetalle(DetalleDespacho detDes){
        
        PreparedStatement estado = null;
                
        try {
        conexion = ConexionDB.conectar();
            String sql = "UPDATE detalle_despacho "
                    + " SET  productoexistid = ?,"
                    + " cantidad = ? "
                    + " WHERE detalle_despachoid = "+detDes.getDetalleDespachoid();
            
            estado = conexion.prepareStatement(sql);
            estado.setInt(1, detDes.getProductoexistid());
            estado.setInt(2, detDes.getCantidad());
           
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return estado;
    }
    
}
