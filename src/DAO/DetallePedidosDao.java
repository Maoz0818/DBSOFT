
package DAO;

import Logica.DetallePedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DetallePedidosDao {
    
    // CONEXION
   private Connection conexion;
   
   // SQL PARA NUEVO DETALLE DE PEDIDO ///////////////////////////////////////////////////////////////////////////////////
   public PreparedStatement nuevoDetalle(DetallePedido detPed){
        
        PreparedStatement estado = null;
                
        try {
        conexion = ConexionDB.conectar();
            String sql = "insert into detalle_pedido \n"
                    + "(productoexistid, pedidoid, cantidad)\n"
                    + "VALUES (?, ?, ?)";
            
            estado = conexion.prepareStatement(sql);
            
            estado.setInt(1, detPed.getProductoexistid());
            estado.setInt(2, detPed.getPedidoid());
            estado.setInt(3, detPed.getCantidad());
           
            
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return estado;
    }
   
   // SQL PARA MODIFICAR UN DETALLE DE DESPACHO /////////////////////////////////////////////////////////////////////////////
   public PreparedStatement modificarDetalle(DetallePedido detPed){
        
        PreparedStatement estado = null;
                
        try {
        conexion = ConexionDB.conectar();
            String sql = "UPDATE detalle_pedido "
                    + " SET  productoexistid = ?,"
                    + " cantidad = ? "
                    + " WHERE detalle_pedidoid = "+detPed.getDetallePedidoid();
            
            estado = conexion.prepareStatement(sql);
            estado.setInt(1, detPed.getProductoexistid());
            estado.setInt(2, detPed.getCantidad());
           
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return estado;
    }
    
}
