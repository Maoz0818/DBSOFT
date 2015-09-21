
package DAO;

import Logica.Proveedores;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ProveedorDao {
    // CONEXION
    private Connection conexion;
    
    // SQL PARA LLENAR EL COMBOBOX PROVEEDOR ///////////////////////////////////////////////////////////////////////////
    public ResultSet comboBoxProveedor(){
        
        ResultSet resultadoProveedor = null;
                
        try {
        conexion = ConexionDB.conectar();
        String slqTipo = "SELECT codigo_proveedor, nombre FROM proveedores order by codigo_proveedor";
        resultadoProveedor = conexion.createStatement().executeQuery(slqTipo);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return resultadoProveedor;
    }
    
     public PreparedStatement nuevoProveedor(Proveedores prov) {

        PreparedStatement estado = null;

        try {
            conexion = ConexionDB.conectar();
            String sql = "insert into proveedores "
                    + " (rut, nombre, pais, ciudad, telefono, e_mail) "
                    + " values (?,?,?,?,?,?)";

            estado = conexion.prepareStatement(sql);

            estado.setInt(1, prov.getRut());
            estado.setString(2, prov.getNombre());
            estado.setString(3, prov.getPais());
            estado.setString(4, prov.getCiudad());
            estado.setInt(5, prov.getTelefono());
            estado.setString(6, prov.geteMail());

        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        return estado;
    }

    public PreparedStatement modificarProveedor(Proveedores prov) {

        PreparedStatement estado = null;

        try {
            conexion = ConexionDB.conectar();
            String sql = "UPDATE proveedores "
                    + " SET rut = ?, "
                    + " nombre = ?, "
                    + " pais = ?, "
                    + " ciudad = ?, "
                    + " telefono = ?, "
                    + " e_mail = ? \n"
                    + " WHERE codigo_proveedor = " + prov.getCodigoProveedor();

            estado = conexion.prepareStatement(sql);

            estado.setInt(1, prov.getRut());
            estado.setString(2, prov.getNombre());
            estado.setString(3, prov.getPais());
            estado.setString(4, prov.getCiudad());
            estado.setInt(5, prov.getTelefono());
            estado.setString(6, prov.geteMail());

        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        return estado;
    }

    public int buscarProveedor(String nombreProv) {

        int codigoProveedor = 0;
        
        ResultSet resultadoCodigoProveedor = null;

        try {
            conexion = ConexionDB.conectar();
            String slqTipo = "select codigo_proveedor from proveedores where nombre = '" + nombreProv + "'";
            resultadoCodigoProveedor = conexion.createStatement().executeQuery(slqTipo);
            
            while (resultadoCodigoProveedor.next()) {
                codigoProveedor = resultadoCodigoProveedor.getInt("codigo_proveedor");
            }
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }

        return codigoProveedor;
    }

    public ResultSet datosParaTablaProveedores() {

        ResultSet resultadoDatosTablaProveedores = null;

        try {
            conexion = ConexionDB.conectar();
            //sql para oas proveedores
            String sql = "select codigo_proveedor, rut, nombre, pais, ciudad,telefono, e_mail\n"
                    + "from proveedores\n"
                    + "order by codigo_proveedor";
            //ResultSet
            resultadoDatosTablaProveedores = conexion.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }

        return resultadoDatosTablaProveedores;
    }

    public ResultSet buscarProveedoresTabla(String busqueda) {

        ResultSet resultadoProveedoresTabla = null;

        try {
            conexion = ConexionDB.conectar();
            //sql para proveedores
            String sql = "select rut, nombre, pais, ciudad, telefono, e_mail\n"
                    + "from proveedores\n"
                    + "where nombre like '%" + busqueda + "%'\n"
                    + "order by nombre";
            //ResultSet
            resultadoProveedoresTabla = conexion.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        return resultadoProveedoresTabla;
    }

    public ResultSet cargaProveedores(String valor) {

        ResultSet cargaProveedores = null;

        try {
            conexion = ConexionDB.conectar();
            //sql para los proveedores
            String sql = "select codigo_proveedor, rut, nombre, pais, ciudad, telefono, e_mail\n"
                    + "from proveedores\n"
                    + "where codigo_proveedor = "+valor;
            //ResultSet
            cargaProveedores = conexion.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        return cargaProveedores;
    }
}
