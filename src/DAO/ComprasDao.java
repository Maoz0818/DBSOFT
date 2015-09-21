package DAO;

import Logica.OrdenesCompra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ComprasDao {

    //CONEXION
    private Connection conexion;

    //SQL PARA CREAR UNA NUEVA COMPRA
    public PreparedStatement nuevaCompra(OrdenesCompra oc) {

        PreparedStatement estado = null;

        try {
            conexion = ConexionDB.conectar();
            String sql = "inser ino ordenes_compra "
                    + " (codigo_proveedor, estadoid) "
                    + " VALUES (?, ?)";

            estado = conexion.prepareStatement(sql);

            estado.setInt(1, oc.getCodigo_proveedor());
            estado.setInt(2, oc.getEstado());

        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }

        return estado;
    }
    
    public PreparedStatement modificarCompra (OrdenesCompra oc){
        
        PreparedStatement estado = null;
        
        try { 
            conexion = ConexionDB.conectar();
            String sql = "UPDATE ordenes_compra "
                    + " SET estadoid = ?,"
                    + " WHERE ordenid = "+oc.getOrdenid();
            
            estado = conexion.prepareStatement(sql);
            estado.setInt(1, oc.getEstado());
        } catch (SQLException e){
            System.out.println("Error "+e.getMessage());
        }
        return estado;
    }
}
