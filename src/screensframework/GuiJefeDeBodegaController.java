
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GuiJefeDeBodegaController implements Initializable {
    
    @FXML    private Button gesProductos;
    @FXML    private Button gesDespa;
    @FXML    private Button cerrar;
    
    Stage stage;
    
    Principal p = new Principal();
    
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
    // LLAMADA A LA GUI GESTIONAR PRODUCTOS /////////////////////////////////////////////////////////////////////////////////
    @FXML
    private void GestionarProductos(ActionEvent event) {
        
         try {
                    // cargamos la scene
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Principal.class.getResource("GestionProductos.fxml"));
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
    
    // LLAMADO A LA GUI GESTIONAR DESPACHOS /////////////////////////////////////////////////////////////////////////////
    @FXML
    private void GestionarDespachos(ActionEvent event) {
        try {
                    // cargamos la scene
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Principal.class.getResource("GestionDespachos.fxml"));
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
