package screensframework;

import DAO.LoginDao;
import Logica.Validaciones;
import Vista.Principal;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


public class LoginController implements Initializable {
    
    
    private Validaciones validation = new Validaciones();
    
    // DAO DE LOGIN
    LoginDao logindao = new LoginDao();
    
    String cargo;
    
    @FXML    private TextField txtCorreo;
    @FXML    private PasswordField txtCodigo;
             String sucursalid;
    Stage stage;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
                
    // EVENTO PARA VALIDAR EL INGRESO A LA APP ////////////////////////////////////////////////////////////////////////////
    @FXML
    private void validarIngreso(ActionEvent event) throws IOException, SQLException{
       // VALIDACIONES ////////////////////////////////////////////////
       if (!validation.validarVacios(txtCorreo.getText(), "Usuario")) {
            return;
        }
       
       if (!validation.validarCorreo(txtCorreo.getText())) {
            return;
        }
       
       if (!validation.validarVacios(txtCodigo.getText(), "Contraseña")) {
            return;
        }
       
        if (!validation.soloNumeros(txtCodigo.getText(), "Contraseña")) {
            return;
        }
        // VARIALBLES A USAR PARA LA VALIDACION////
        String correo = txtCorreo.getText();
        String codigo = txtCodigo.getText();
            
        // BOOLEANO PARA CONSTATAR QUE SI SE ENCONTRO UN USUARIO /////////////////////////////////////////////////////////
            boolean existeUsuario = logindao.loginEmpleados(correo, codigo).next();
            boolean existeSucursal = logindao.loginSucursales(correo, codigo).next();
            ResultSet resultadoConsulta = logindao.loginEmpleados(correo, codigo);
            while(resultadoConsulta.next()){
                cargo = resultadoConsulta.getString("cargo").trim();
            }
                        
            // SI EXISTE EL USUARIO SE CARGA LA GUI /////////////////////////////////////////////////////////////////////
            if (existeUsuario) {
                txtCorreo.setText("");
                txtCodigo.setText("");
                switch(cargo){
                    case "Gerente":
                                try {
                                // cargamos la scene
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(Principal.class.getResource("GuiGerente.fxml"));
                                AnchorPane Gerente = (AnchorPane) loader.load();

                                // agregamos a la ventana
                                Scene scene = new Scene(Gerente);
                                Node node = (Node) event.getSource();
                                Stage primaryStage = (Stage) node.getScene().getWindow();
                                primaryStage.setScene(scene);
                                primaryStage.show();
                    
                                } catch (IOException e) {
                                e.printStackTrace();
                                }
                        break;
                        
                        case "Jefe de Bodega":
                                try {
                                // cargamos la scene
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(Principal.class.getResource("GuiJefeDeBodega.fxml"));
                                AnchorPane JefeBodega = (AnchorPane) loader.load();
                                
                                // agregamos a la ventana
                                Scene scene = new Scene(JefeBodega);
                                Node node = (Node) event.getSource();
                                Stage primaryStage = (Stage) node.getScene().getWindow();
                                primaryStage.setScene(scene);
                                primaryStage.show();
                                
                                } catch (IOException e) {
                                e.printStackTrace();
                                }
                        break;
                }
                
            } else {
                
                if(existeSucursal){
                    try {       
//                        
                                // cargamos la scene
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(Principal.class.getResource("GuiSucursal.fxml"));
                                AnchorPane Sucursal = (AnchorPane) loader.load();
                               
                                // agregamos a la ventana
                                Scene scene = new Scene(Sucursal);
                                Node node = (Node) event.getSource();
                                Stage primaryStage = (Stage) node.getScene().getWindow();
                                primaryStage.setScene(scene);
                                primaryStage.show();
                                
                                
                                
                                } catch (IOException e) {
                                e.printStackTrace();
                                }
                }
                else{
                JOptionPane.showMessageDialog(null, "Este usuario no está registrado");
                }
            }
            
    }
        
}
