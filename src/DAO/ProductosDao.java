
package DAO;

import Logica.Productos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductosDao {
    // CONEXION
    private Connection conexion;
    
    // SQL PARA LLENAR EL COMBO BOX PRODUCTOS /////////////////////////////////////////////////////////////////////
    public ResultSet comboBoxProductos(){
        
        ResultSet resultadoProductos = null;
                
        try {
        conexion = ConexionDB.conectar();
        String slqTipo = "SELECT productoid, nombre FROM productos order by productoid";
        resultadoProductos = conexion.createStatement().executeQuery(slqTipo);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return resultadoProductos;
    }
    
    // SQL PARA CREAR UN NUEVO PRODUCTO //////////////////////////////////////////////////////////////////////////////////
    public PreparedStatement nuevoProducto(Productos pro){
        
        PreparedStatement estado = null;
                
        try {
        conexion = ConexionDB.conectar();
            String sql = "insert into productos "
                    + " (tipoid, nombre, precio, marca) "
                    + " VALUES (?, ?, ?, ?)";
            
            estado = conexion.prepareStatement(sql);
            
            estado.setInt(1, pro.getTipo());
            estado.setString(2, pro.getNombre());
            estado.setInt(3, pro.getPrecio());
            estado.setString(4, pro.getMarca());
            
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return estado;
    }
    
    // SQL PARA MODIFICAR UN PRODUCTO /////////////////////////////////////////////////////////////////////////////////////
    public PreparedStatement modificarProducto(Productos pro){
        
        PreparedStatement estado = null;
                
        try {
        conexion = ConexionDB.conectar();
            String sql = "UPDATE productos "
                    + " SET  productoid = ?,"
                    + " tipoid = ?, "
                    + " nombre = ?, "
                    + " precio = ?, "
                    + " marca = ?"
                    + " WHERE productoid = "+pro.getProductoid();
            
            estado = conexion.prepareStatement(sql);
            estado.setInt(1, pro.getProductoid());
            estado.setInt(2, pro.getTipo());
            estado.setString(3, pro.getNombre());
            estado.setInt(4, pro.getPrecio());
            estado.setString(5, pro.getMarca());
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return estado;
    }
    
    // SQL PARA BUSCAR UN PRODUCTO POR NOMBRE //////////////////////////////////////////////////////////////////////////
    public int buscarProductoid(String nombrePro){
        
        int productoid = 0;
        ResultSet resultadoProductoid = null;
               
        try {
        conexion = ConexionDB.conectar();
        String slqTipo = "select productoid from productos where nombre = '"+nombrePro+"'";
        resultadoProductoid = conexion.createStatement().executeQuery(slqTipo);
        while(resultadoProductoid.next()){
        productoid = resultadoProductoid.getInt("productoid");
        }
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
             
        return productoid;
    }
    
}
