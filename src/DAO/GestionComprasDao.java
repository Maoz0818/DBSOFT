package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestionComprasDao {

    //CONEXION
    private Connection conexion;

    //SQL PARA LLENAR LA TABLA COMPRAS
    public ResultSet datosParaTablaCompras() {

        ResultSet resultadoDatosTablaCompras = null;

        try {
            conexion = ConexionDB.conectar();
            //sql para compras+
            String sql = "select oc.ordenid, prov.nombre, oc.fecha_orden, es.nombre \n"
                    + "from ordenes_compra oc inner join proveedores prov on prov.codigo_proveedor=oc.codigo_proveedor\n"
                    + "inner join estados es on es.estadoid=oc.estadoid\n"
                    + "order by oc.ordenid";
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        return resultadoDatosTablaCompras;
    }

    //SQL PARA LLENAR LA TABLA DETALLEs COMPRA

    public ResultSet datosParaTablaDetallesCompra(String ordenid) {

        ResultSet resultadoDatosTablaDetallesCompra = null;

        try {
            conexion = ConexionDB.conectar();
            //sql para existencias

            String sql = "select dt.detalleid, oc.ordenid, prov.nombre, prod.nombre, prod.marca, dt.cantidad, dt.plazo_entrega\n"
                    + "from detalle_orden dt inner join ordenes_compra oc on dt.ordenid=oc.ordenid\n"
                    + "inner join productos_en_existencia prex on prex.productoexistid=dt.productoexistid\n"
                    + "inner join productos prod on prod.productoid=prex.productoid\n"
                    + "inner join proveedores prov on prov.codigo_proveedor=oc.codigo_proveedor\n"
                    + "where oc.ordenid = "+ordenid+" order by dt.detalleid";
            //ResultSet
            resultadoDatosTablaDetallesCompra = conexion.createStatement().executeQuery(sql);
        } catch (SQLException e){
            System.out.println("Error "+e.getMessage());
        }
        return resultadoDatosTablaDetallesCompra;
    }
    //SQL PARA LA CARGA DEL FORMULARIO DE COMPRAS
    
    public ResultSet cargarCompras(String valor){
        
        ResultSet cargarCompras = null;
        
        try {
            conexion = ConexionDB.conectar();
            //sql para orden de compra
            String sql = "select oc.ordenid, prov.nombre, es.nombre\n"
                    + "from ordenes_compra oc inner join proveedores prov on prov.codigo_proveedor = oc.codigo_proveedor\n"
                    + "inner join estados es on es.estadoid = oc.estadoid\n"
                    + "where oc.estadoid = "+valor;
            //ResultSet
            cargarCompras = conexion.createStatement().executeQuery(sql);
        } catch (SQLException e){
            System.out.println("Error "+e.getMessage());
        }
        return cargarCompras;
    }
    
    //SQL PARA LA CARGA DEL FORMULARIO DETALLES DE ORDEN
    public ResultSet cargarDetalles(String valor){
        
        ResultSet cargarDetalles = null;
        
        try {
            conexion = ConexionDB.conectar();
            //sql para cargar datos del detalle
            String sql = "select oc.ordenid, prod.nombre, prod.marca, dt.plazo_entrega, dt.cantidad\n"
                    + "from detalle_orden dt inner join ordenes_compra oc on dt.ordenid=oc.ordenid\n"
                    + "inner join productos_en_existencia prex on prex.productoexistid=dt.productoexistid\n"
                    + "inner join productos prod on prod.productoid=prex.productoid\n"
                    + "where dt.detalleid = "+valor;
            cargarDetalles = conexion.createStatement().executeQuery(sql);
        }catch (SQLException e){
            System.out.println("Error "+e.getMessage());
        }
        return cargarDetalles;
    }
    
    //SQL PARA BUSCAR EN LA TABLA ORDENES DE COMPRA
    public ResultSet buscarComprasTabla(String busqueda){
        
        ResultSet resultadoComprasTabla = null;
        
        try { 
            conexion = ConexionDB.conectar();
            //sql para existencias
            String sql = "select oc.ordenid, prov.nombre, oc.fecha_orden, es.nombre \n"
                    + "from ordenes_compra oc inner join proveedores prov on prov.codigo_proveedor=oc.codigo_proveedor\n"
                    + "inner join estados es on es.estadoid=oc.estadoid\n"
                    + "where oc.ordenid = "+busqueda;
            //ResultSet
            resultadoComprasTabla = conexion.createStatement().executeQuery(sql);
        }catch (SQLException e){
            System.out.println("Error "+e.getMessage());
        }
        return resultadoComprasTabla;
    }
}
