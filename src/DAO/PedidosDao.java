
package DAO;

import Logica.Pedidos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class PedidosDao {
    // CONEXION
   private Connection conexion;
   
    // SQL PARA MODIFICAR UN DESPACHO //////////////////////////////////////////////////////////////////////////////////////
   public void modificarEstado(String consulta, int estadoid){
        
        PreparedStatement estado;
                
        try {
        conexion = ConexionDB.conectar();
            String sql = "UPDATE pedidos "
                    + " SET  estadoid = ?"
                    + " WHERE pedidoid = "+consulta;
            
            estado = conexion.prepareStatement(sql);
            estado.setInt(1, estadoid);
            estado.executeUpdate();
           
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
   }
   
   // SQL PARA CREAR UN NUEVO PEDIDO //////////////////////////////////////////////////////////////////////////
   public PreparedStatement nuevoPedido(Pedidos ped){
        
        PreparedStatement estado = null;
                
        try {
        conexion = ConexionDB.conectar();
            String sql = "insert into pedidos "
                    + " (sucursalid, estadoid) "
                    + " VALUES (?, ?)";
            
            estado = conexion.prepareStatement(sql);
            
            estado.setInt(1, ped.getSucursalid());
            estado.setInt(2, ped.getEstado());
           
            
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return estado;
    }
   
  // SQL PARA MODIFICAR UN PEDIDO ////////////////////////////////////////////////////////////////////////////////////
   public PreparedStatement modificarPedido(Pedidos ped){
        
        PreparedStatement estado = null;
                
        try {
        conexion = ConexionDB.conectar();
            String sql = "UPDATE pedidos "
                    + " SET  sucursalid = ?,"
                    + " estadoid = ? "
                    + " WHERE pedidoid = "+ped.getPedidoid();
            
            estado = conexion.prepareStatement(sql);
            estado.setInt(1, ped.getSucursalid());
            estado.setInt(2, ped.getEstado());
           
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return estado;
    }
    
}
