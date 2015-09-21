
package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class GestionProductosDao {
    // CONEXION
    private Connection conexion;
    
    // SQL PARA OBTENER LOS DATOS PARA LA TABLA EXISTENCIAS ///////////////////////////////////////////////////////////////
    public ResultSet datosParaTablaExistencias(){
        
        ResultSet resultadoDatosTablaExistencias = null;
                
        try {
         conexion = ConexionDB.conectar();
            //sql para existencias 
            String sql = "select  exist.productoexistid, pro.nombre producto, exist.cantidad, pro.precio, tip.nombre tipo, prov.nombre proveedor, pro.marca, alm.seccion\n" +
            "from tipo tip inner join productos pro on tip.tipoid = pro.tipoid\n" +
            "inner join productos_en_existencia exist on pro.productoid = exist.productoid\n" +
            "inner join almacen alm on exist.almacenid = alm.almacenid\n" +
            "inner join proveedores prov on exist.codigo_proveedor = prov.codigo_proveedor\n" +
            "order by exist.productoexistid";
            //ResultSet
            resultadoDatosTablaExistencias = conexion.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return resultadoDatosTablaExistencias;
    }
    
    // SQL PARA OBTENER LOS DATOS PARA LA TABLA EXISTENCIAS ///////////////////////////////////////////////////////////////
    public ResultSet datosParaTablaExistenciasSucursal(){
        
        ResultSet resultadoDatosTablaExistenciasSucursal = null;
                
        try {
         conexion = ConexionDB.conectar();
            //sql para existencias 
            String sql = "select  exist.productoexistid, pro.nombre producto, exist.cantidad, pro.precio, prov.nombre proveedor, pro.marca\n" +
            "from  productos pro inner join productos_en_existencia exist on pro.productoid = exist.productoid\n" +
            "inner join proveedores prov on exist.codigo_proveedor = prov.codigo_proveedor\n" +
            "order by exist.productoexistid";
            //ResultSet
            resultadoDatosTablaExistenciasSucursal = conexion.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return resultadoDatosTablaExistenciasSucursal;
    }
    
    // SQL PARA BUSCAR EN LA TABLA EXISTENCIAS/////////////////////////////////////////////////////////////////////////
    public ResultSet buscarExistenciaTabla(String busqueda){
        
        ResultSet resultadoExistenciaTabla = null;
                
        try {
         conexion = ConexionDB.conectar();
            //sql para existencias 
            String sql = "select exist.productoexistid, pro.nombre, exist.cantidad, pro.precio, tip.nombre, prov.nombre, pro.marca, alm.seccion\n" +
            "from tipo tip inner join productos pro on tip.tipoid = pro.tipoid\n" +
            "inner join productos_en_existencia exist on pro.productoid = exist.productoid\n" +
            "inner join almacen alm on exist.almacenid = alm.almacenid\n" +
            "inner join proveedores prov on exist.codigo_proveedor = prov.codigo_proveedor\n" +
            "where pro.nombre like '%"+busqueda+"%'";
            //ResultSet
            resultadoExistenciaTabla = conexion.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return resultadoExistenciaTabla;
    }
    
    
    // SQL PARA CARGA DE FORMULARIO EXISTENCIAS //////////////////////////////////////////////////////////////////////////
    public ResultSet cargaDeExistencias(String valor){
        
        ResultSet cargaExistencias = null;
                
        try {
         conexion = ConexionDB.conectar();
            //sql para existencias 
            String sql = "select  exist.productoexistid codigo, pro.nombre producto, exist.cantidad, pro.precio, tip.nombre tipo, prov.nombre proveedor, pro.marca, alm.seccion\n" +
            "from tipo tip inner join productos pro on tip.tipoid = pro.tipoid\n" +
            "inner join productos_en_existencia exist on pro.productoid = exist.productoid\n" +
            "inner join almacen alm on exist.almacenid = alm.almacenid\n" +
            "inner join proveedores prov on exist.codigo_proveedor = prov.codigo_proveedor\n" +
            "where exist.productoexistid = "+valor;
            //ResultSet
            cargaExistencias = conexion.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return cargaExistencias;
    }
}


