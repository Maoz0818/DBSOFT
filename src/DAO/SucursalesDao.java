package DAO;

import Logica.Sucursal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SucursalesDao {

    private Connection conexion;

    public PreparedStatement nuevaSucursal(Sucursal su) {

        PreparedStatement estado = null;

        try {
            conexion = ConexionDB.conectar();
            String sql = "insert into sucursal "
                    + " (nombre, pais, ciudad, direccion, telefono, e_mail) "
                    + " values (?,?,?,?,?,?)";

            estado = conexion.prepareStatement(sql);

            estado.setString(1, su.getNombre());
            estado.setString(2, su.getPais());
            estado.setString(3, su.getCiudad());
            estado.setString(4, su.getDireccion());
            estado.setInt(5, su.getTelefono());
            estado.setString(6, su.geteMail());

        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        return estado;
    }

    public PreparedStatement modificarSucursal(Sucursal su) {

        PreparedStatement estado = null;

        try {
            conexion = ConexionDB.conectar();
            String sql = "UPDATE sucursal "
                    + " SET nombre = ?, "
                    + " pais = ?, "
                    + " ciudad = ?, "
                    + " direccion = ?, "
                    + " telefono = ?, "
                    + " e_mail = ? \n"
                    + " WHERE sucursalid = " + su.getSucursalid();

            estado = conexion.prepareStatement(sql);

            estado.setString(1, su.getNombre());
            estado.setString(2, su.getPais());
            estado.setString(3, su.getCiudad());
            estado.setString(4, su.getDireccion());
            estado.setInt(5, su.getTelefono());
            estado.setString(6, su.geteMail());

        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        return estado;
    }

    public int buscarSucursal(String nombreSu) {

        int sucursalid = 0;
        ResultSet resultadoSucursalid = null;

        try {
            conexion = ConexionDB.conectar();
            String slqTipo = "select sucursalid from sucursal where nombre = '" + nombreSu + "'";
            resultadoSucursalid = conexion.createStatement().executeQuery(slqTipo);
            
            while (resultadoSucursalid.next()) {
                sucursalid = resultadoSucursalid.getInt("sucursalid");
            }
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }

        return sucursalid;
    }

    public ResultSet datosParaTablaSucursales() {

        ResultSet resultadoDatosTablaSucursales = null;

        try {
            conexion = ConexionDB.conectar();
            //sql para las sucursales
            String sql = "select sucursalid, nombre, pais, ciudad, direccion, telefono, e_mail\n"
                    + "from sucursal\n"
                    + "order by sucursalid";
            //ResultSet
            resultadoDatosTablaSucursales = conexion.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }

        return resultadoDatosTablaSucursales;
    }

    public ResultSet buscarSucursalesTabla(String busqueda) {

        ResultSet resultadoSucursalesTabla = null;

        try {
            conexion = ConexionDB.conectar();
            //sql para sucursales
            String sql = "select nombre, pais, ciudad, direccion, telefono, e_mail\n"
                    + "from sucursal\n"
                    + "where nombre like '%" + busqueda + "%'\n"
                    + "order by nombre";
            //ResultSet
            resultadoSucursalesTabla = conexion.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        return resultadoSucursalesTabla;
    }

    public ResultSet cargaSucursales(String valor) {

        ResultSet cargaSucursales = null;

        try {
            conexion = ConexionDB.conectar();
            //sql para las sucursales
            String sql = "select sucursalid, nombre, pais, ciudad, direccion, telefono, e_mail\n"
                    + "from sucursal\n"
                    + "where sucursalid = " + valor;
                 
            //ResultSet
            cargaSucursales = conexion.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        return cargaSucursales;
    }
}
