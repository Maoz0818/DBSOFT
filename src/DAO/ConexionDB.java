
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB{
    
    private static Connection conn;
    private static final String url = "jdbc:postgresql://localhost:5432/DBBodega";
    private static final String usuario = "postgres";
    private static final String contraseña = "mao4202229";
   
    
    public static Connection conectar() throws SQLException{
	try {
            Class.forName("org.postgresql.Driver").newInstance();
	} catch(ClassNotFoundException cnfe) {
            System.err.println("Error: "+cnfe.getMessage());
	} catch(InstantiationException ie) {
            System.err.println("Error: "+ie.getMessage());
	} catch(IllegalAccessException iae) {
            System.err.println("Error: "+iae.getMessage());
	}
            conn = DriverManager.getConnection(url,usuario,contraseña);
            return conn;
	}
	
    public static Connection getConexion() throws SQLException, ClassNotFoundException{
        if(conn !=null && !conn.isClosed())
            return conn;
            conectar();
            return conn;
    }
}
