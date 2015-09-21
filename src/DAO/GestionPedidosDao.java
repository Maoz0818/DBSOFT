
package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class GestionPedidosDao {
    // CONEXION
    private Connection conexion;
    
    // SQL PARA LLENAR LA TABLA PEDIDOS /////////////////////////////////////////////////////////////////////////////////
    public ResultSet datosParaTablaPedidos(){
        
        ResultSet resultadoDatosTablaPedidos = null;
                
        try {
         conexion = ConexionDB.conectar();
            //sql para existencias 
            String sql = "select ped.pedidoid, suc.nombre sucursal, est.nombre estado, ped.fecha_pedido\n" +
            "from estados est inner join pedidos ped on est.estadoid = ped.estadoid\n" +
            "inner join sucursal suc on ped.sucursalid = suc.sucursalid\n" +
            "order by ped.pedidoid";
            //ResultSet
            resultadoDatosTablaPedidos = conexion.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return resultadoDatosTablaPedidos;
    }
    
    // SQL PARA LLENAR LA TABLA PEDIDOS SEGUN SUCURSAL///////////////////////////////////////////////////////////
    public ResultSet datosParaTablaPedidosSucursal(String sucursalid){
        
        ResultSet resultadoDatosTablaPedidosSucursal = null;
                
        try {
         conexion = ConexionDB.conectar();
            //sql para existencias 
            String sql = "select ped.pedidoid, suc.nombre sucursal, est.nombre estado, ped.fecha_pedido\n" +
            "from estados est inner join pedidos ped on est.estadoid = ped.estadoid\n" +
            "inner join sucursal suc on ped.sucursalid = suc.sucursalid\n" +
            "where suc.sucursalid = "+sucursalid+"\n" +
            "order by ped.pedidoid";
            //ResultSet
            resultadoDatosTablaPedidosSucursal = conexion.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return resultadoDatosTablaPedidosSucursal;
    }
    
    // SQL PARA CARGA DE LA TABLA DETALLE PEDIDO ///////////////////////////////////////////////////////////////////////////
    public ResultSet datosParaTablaDetallesPedido(String pedidoid){
        
        ResultSet resultadoDatosTablaDetallesPedido = null;
                
        try {
         conexion = ConexionDB.conectar();
            //sql para existencias 
            String sql = "select det.detalle_pedidoid, pro.nombre producto, det.cantidad, det.fecha_posible_entrega fecha\n" +
            "from pedidos ped inner join detalle_pedido det on ped.pedidoid = det.pedidoid\n" +
            "inner join productos_en_existencia exist on det.productoexistid = exist.productoexistid\n" +
            "inner join productos pro on exist.productoid = pro.productoid\n" +
            "where ped.pedidoid = "+pedidoid+"order by det.detalle_pedidoid";
            //ResultSet
            resultadoDatosTablaDetallesPedido = conexion.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return resultadoDatosTablaDetallesPedido;
    }
    
    
    // SQL PARA LA CARGA DEL FORMULARIO PEDIDOS ///////////////////////////////////////////////////////////////////////////
    public ResultSet cargaDePedidos(String valor){
        
        ResultSet cargaPedidos = null;
                
        try {
         conexion = ConexionDB.conectar();
            //sql para pedidos 
            String sql = "select ped.pedidoid, est.nombre estado from estados est\n" +
            "inner join pedidos ped on est.estadoid = ped.estadoid \n"+
            "where pedidoid = "+valor;
            //ResultSet
            cargaPedidos = conexion.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return cargaPedidos;
    }
    
     // SQL PARA LA CARGA DEL FORMULARIO DETALLES DE PEDIDO ///////////////////////////////////////////////////////////
    public ResultSet cargaDeDetallesPedido(String valor){
        
        ResultSet cargaDetallesPedido = null;
                
        try {
         conexion = ConexionDB.conectar();
            //sql para existencias 
            String sql = "select det.detalle_pedidoid, pro.nombre producto, det.cantidad\n" +
            "from detalle_pedido det inner join productos_en_existencia exist on det.productoexistid = exist.productoexistid\n" + 
            "inner join productos pro on exist.productoid = pro.productoid\n" +
            "where det.detalle_pedidoid = "+valor;
            //ResultSet
            cargaDetallesPedido = conexion.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return cargaDetallesPedido;
    }
    
    // SQL PARA BUSCAR EN LA TABLA PEDIDOS ///////////////////////////////////////////////////////////////////////////////
    public ResultSet buscarPedidoTabla(String busqueda){
        
        ResultSet resultadoPedidoTabla = null;
                
        try {
         conexion = ConexionDB.conectar();
            //sql para existencias 
            String sql = "select ped.pedidoid, suc.nombre sucursal, est.nombre estado, ped.fecha_pedido\n" +
            "from estados est inner join pedidos ped on est.estadoid = ped.estadoid\n" +
            "inner join sucursal suc on ped.sucursalid = suc.sucursalid\n" +
            "where ped.pedidoid = "+busqueda;
            //ResultSet
            resultadoPedidoTabla = conexion.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return resultadoPedidoTabla;
    }
    
    // SQL PARA BUSCAR EN LA TABLA PEDIDOS ///////////////////////////////////////////////////////////////////////////////
    public ResultSet buscarPedidoTablaSucursal(String busqueda, String sucursalid){
        
        ResultSet resultadoPedidoTabla = null;
                
        try {
         conexion = ConexionDB.conectar();
            //sql para existencias 
            String sql = "select ped.pedidoid, suc.nombre sucursal, est.nombre estado, ped.fecha_pedido\n" +
            "from estados est inner join pedidos ped on est.estadoid = ped.estadoid\n" +
            "inner join sucursal suc on ped.sucursalid = suc.sucursalid\n" +
            "where ped.sucursalid = "+sucursalid+" and ped.pedidoid = "+busqueda;
            //ResultSet
            resultadoPedidoTabla = conexion.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return resultadoPedidoTabla;
    }
    
}
