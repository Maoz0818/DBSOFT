package DAO;

import Logica.DetalleOrden;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DetalleOrdenDao {

    //CONEXION

    private Connection conexion;

    //SQL PARA NUEVO DETALLE DE ORDEN
    public PreparedStatement nuevoDetalle(DetalleOrden detO) {

        PreparedStatement estado = null;

        try {
            conexion = ConexionDB.conectar();
            String sql = "insert into detalle_orden"
                    + " (ordenid,  productoexistid, plazo_entrega, cantidad) "
                    + " VALUES (?, ?, ?, ?)";

            estado = conexion.prepareStatement(sql);

            estado.setInt(1, detO.getOrdenid());
            estado.setInt(2, detO.getProductoexistid());
            estado.setString(3, detO.getPlazoEntrega());
            estado.setInt(4, detO.getCantidad());
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        return estado;
    }
    public PreparedStatement modificarDetalle(DetalleOrden detO) {

        PreparedStatement estado = null;

        try {
            conexion = ConexionDB.conectar();
            String sql = "UPDATE detalle_orden "
                    + " SET ";

            estado = conexion.prepareStatement(sql);

            estado.setInt(1, detO.getOrdenid());
            estado.setInt(2, detO.getProductoexistid());
            estado.setString(3, detO.getPlazoEntrega());
            estado.setInt(4, detO.getCantidad());
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        return estado;
    }
}
