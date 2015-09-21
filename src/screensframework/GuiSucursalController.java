
package screensframework;

import Vista.Principal;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class GuiSucursalController implements Initializable {
    @FXML    private Button gesPedidos;
    @FXML    private Button reporProductos;
    @FXML    private Button cerrar;
    @FXML    private Label  txtcodSucursal;
                        
    
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }
    
    
    
    @FXML
    private void GestionarPedidos(ActionEvent event) {
        
        try {
                    // cargamos la scene
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Principal.class.getResource("GestionPedidos.fxml"));
                    AnchorPane Producto = (AnchorPane) loader.load();

                    // agregamos a la ventana
                    Scene scene = new Scene(Producto);
                    Node node = (Node) event.getSource();
                    Stage primaryStage = (Stage) node.getScene().getWindow();
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    
                } catch (IOException e) {
                    e.printStackTrace();
               }
        
    }

    @FXML
    private void reportarProductosVencidos(ActionEvent event) {
    }

    // LLAMADO A CERRAR SESION ////////////////////////////////////////////////////////////////////////////////////////////7
    @FXML
    private void cerrarSesion(ActionEvent event) {
         try {
                    // cargamos la scene
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Principal.class.getResource("Login.fxml"));
                    AnchorPane Producto = (AnchorPane) loader.load();

                    // agregamos a la ventana
                    Scene scene = new Scene(Producto);
                    Node node = (Node) event.getSource();
                    Stage primaryStage = (Stage) node.getScene().getWindow();
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    
                } catch (IOException e) {
                    e.printStackTrace();
               }
    }
    
       
}
