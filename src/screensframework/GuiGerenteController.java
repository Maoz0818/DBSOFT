
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


public class GuiGerenteController implements Initializable {
    @FXML    private Button gesUsuarios;
    @FXML    private Button gesCompras;
    @FXML    private Button cerrar;
    
    Stage stage;
    
    Principal p = new Principal();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void GestionarUsuarios(ActionEvent event) {

         try {
                    // cargamos la scene
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Principal.class.getResource("GestionUsuarios.fxml"));
                    AnchorPane Usuario = (AnchorPane) loader.load();

                    // agregamos a la ventana
                    Scene scene = new Scene(Usuario);
                    Node node = (Node) event.getSource();
                    Stage primaryStage = (Stage) node.getScene().getWindow();
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }        
    }

    @FXML
    private void GestionarCompras(ActionEvent event) {
        
         try {
                    // cargamos la scene
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Principal.class.getResource("GestionCompras.fxml"));
                    AnchorPane Usuario = (AnchorPane) loader.load();

                    // agregamos a la ventana
                    Scene scene = new Scene(Usuario);
                    Node node = (Node) event.getSource();
                    Stage primaryStage = (Stage) node.getScene().getWindow();
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    
                } catch (IOException e) {
                    e.printStackTrace();
                } 
    }

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

