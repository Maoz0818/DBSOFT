
package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class GestionDespachosDao {
    // CONEXION
    private Connection conexion;
    
    // SQL PARA LLENAR LA TABLA DESPACHOS /////////////////////////////////////////////////////////////////////////////////
    public ResultSet datosParaTablaDespachos(){
        
        ResultSet resultadoDatosTablaDespachos = null;
                
        try {
         conexion = ConexionDB.conectar();
            //sql para existencias 
            String sql = "select des.despachoid, suc.nombre sucursal, des.pedidoid , est.nombre estado, des.fecha_despacho\n" +
            "from estados est inner join despacho des on est.estadoid = des.estadoid\n" +
            "inner join pedidos ped on des.pedidoid = ped.pedidoid \n" +
            "inner join sucursal suc on ped.sucursalid = suc.sucursalid\n" +
            "order by des.despachoid";
            //ResultSet
            resultadoDatosTablaDespachos = conexion.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return resultadoDatosTablaDespachos;
    }
    
    // SQL PARA LLENAR LA TABLA DETALLE DESPACHOS ////////////////////////////////////////////////////////////////////////
    public ResultSet datosParaTablaDetallesDespacho(String despachoid){
        
        ResultSet resultadoDatosTablaDetallesDespacho = null;
                
        try {
         conexion = ConexionDB.conectar();
            //sql para existencias 
            String sql = "select det.detalle_despachoid, det.despachoid, pro.nombre producto, det.cantidad\n" +
            "from despacho des inner join detalle_despacho det on des.despachoid = det.despachoid\n" +
            "inner join productos_en_existencia exist on det.productoexistid = exist.productoexistid\n" +
            "inner join productos pro on exist.productoid = pro.productoid\n" +
            "where des.despachoid = "+despachoid+"order by det.detalle_despachoid";
            //ResultSet
            resultadoDatosTablaDetallesDespacho = conexion.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return resultadoDatosTablaDetallesDespacho;
    }
    
    // SQL PARA LA CARGA DEL FORMULARIO DESPACHOS /////////////////////////////////////////////////////////////////////////    
    public ResultSet cargaDeDespachos(String valor){
        
        ResultSet cargaDespachos = null;
                
        try {
         conexion = ConexionDB.conectar();
            //sql para existencias 
            String sql = "select des.despachoid, des.pedidoid, est.nombre estado\n" +
            "from estados est inner join despacho des on est.estadoid = des.estadoid\n" +
            "where des.despachoid = "+valor;
            //ResultSet
            cargaDespachos = conexion.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return cargaDespachos;
    }
    
    // SQL PARA LA CARGA DEL FORMULARIO DETALLES DE DESPACHO ///////////////////////////////////////////////////////////////////
    public ResultSet cargaDeDetalles(String valor){
        
        ResultSet cargaDetalles = null;
                
        try {
         conexion = ConexionDB.conectar();
            //sql para existencias 
            String sql = "select det.detalle_despachoid, pro.nombre producto, det.cantidad\n" +
            "from detalle_despacho det inner join productos_en_existencia exist on det.productoexistid = exist.productoexistid\n" + 
            "inner join productos pro on exist.productoid = pro.productoid\n" +
            "where det.detalle_despachoid = "+valor;
            //ResultSet
            cargaDetalles = conexion.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return cargaDetalles;
    }
    
    // SQL PARA BUSCAR EN LA TABLA DESPACHO ////////////////////////////////////////////////////////////////////////////    
    public ResultSet buscarDespachosTabla(String busqueda){
        
        ResultSet resultadoDespachosTabla = null;
                
        try {
         conexion = ConexionDB.conectar();
            //sql para existencias 
            String sql = "select des.despachoid, suc.nombre sucursal, des.pedidoid , est.nombre estado, des.fecha_despacho\n" +
            "from estados est inner join despacho des on est.estadoid = des.estadoid\n" +
            "inner join pedidos ped on des.pedidoid = ped.pedidoid \n" +
            "inner join sucursal suc on ped.sucursalid = suc.sucursalid\n" +
            "where des.despachoid = "+busqueda;
            //ResultSet
            resultadoDespachosTabla = conexion.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return resultadoDespachosTabla;
    }
    
}

 