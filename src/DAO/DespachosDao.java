
package DAO;

import Logica.Despacho;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DespachosDao {
   // CONEXION
   private Connection conexion;
   
   // SQL PARA CREAR UN NUEVO DESPACHO //////////////////////////////////////////////////////////////////////////
   public PreparedStatement nuevoDespacho(Despacho desp){
        
        PreparedStatement estado = null;
                
        try {
        conexion = ConexionDB.conectar();
            String sql = "insert into despacho "
                    + " (pedidoid, estadoid) "
                    + " VALUES (?, ?)";
            
            estado = conexion.prepareStatement(sql);
            
            estado.setInt(1, desp.getPedidoid());
            estado.setInt(2, desp.getEstado());
           
            
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return estado;
    }
   
   // SQL PARA MODIFICAR UN DESPACHO //////////////////////////////////////////////////////////////////////////////////////
   public PreparedStatement modificarDespacho(Despacho desp){
        
        PreparedStatement estado = null;
                
        try {
        conexion = ConexionDB.conectar();
            String sql = "UPDATE despacho "
                    + " SET  pedidoid = ?,"
                    + " estadoid = ? "
                    + " WHERE despachoid = "+desp.getDespachoid();
            
            estado = conexion.prepareStatement(sql);
            estado.setInt(1, desp.getPedidoid());
            estado.setInt(2, desp.getEstado());
           
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return estado;
    }
    
}
