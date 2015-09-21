
package screensframework;

import DAO.AlmacenDao;
import DAO.GestionProductosDao;
import DAO.ProductosDao;
import DAO.ProductosEnExistenciasDao;
import DAO.ProveedorDao;
import DAO.TipoDao;
import Logica.Productos;
import Logica.ProductosEnExistencia;
import Logica.Validaciones;
import Vista.Principal;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;


public class GestionProductosController implements Initializable {

  
    @FXML    private TableView tablaExistencias;
    @FXML    private TableColumn col;
    @FXML    private TextField txtBuscarExistencia;
    @FXML    private TextField txtMarca, txtCantidad;
    @FXML    private ComboBox cbTipo;
    @FXML    private TextField txtNombrePro;
    @FXML    private TextField txtPrecio;
    @FXML    private ComboBox cbUbicacion;
    @FXML    private ComboBox cbProveedor;
    @FXML    private Button btAddExistencia;
    @FXML    private Button btModificarExistencia;
    @FXML    private Label lbCod;
    
    Stage stage;
    
    // OBJETO VALIDACIONES
    private Validaciones validation = new Validaciones();
    
    // OBJETOS DAO /////////////////////////////////////
    GestionProductosDao gpDao = new GestionProductosDao();
    ProductosDao proDao = new ProductosDao();
    ProductosEnExistenciasDao proExistDao = new ProductosEnExistenciasDao();
    TipoDao tipoDao = new TipoDao();
    AlmacenDao almDao = new AlmacenDao();
    ProveedorDao provDao = new ProveedorDao();
    
    // LISTAS OBSERVABLES //////////////////
    ObservableList<ObservableList> producto;
    ObservableList<ObservableList> existencia;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         // BOTONES DESACTIVADOS      
         btModificarExistencia.setDisable(true);
         btModificarExistencia.setStyle("-fx-background-color:grey");
         btAddExistencia.setDisable(true);
         btAddExistencia.setStyle("-fx-background-color:grey");
                
        // CARGA DE DATOS A LOS COMBOBOX DE LA GUI ////////////////////////////////////////////////////////////////////////
        ResultSet resultadoTipo = tipoDao.comboBoxTipo();
        ResultSet resultadoUbicacion = almDao.comboBoxUbicacion();
        ResultSet resultadoProveedor = provDao.comboBoxProveedor();

        try {
            // COMBOBOX DE TIPO
            while(resultadoTipo.next()) {
                cbTipo.getItems().add(resultadoTipo.getString("nombre"));
            }
                            
            // COMBOBOX DE UBICACION
             while(resultadoUbicacion.next()) {
                cbUbicacion.getItems().add(resultadoUbicacion.getString("seccion"));
            }
             
            // COMBOBOX DE PROVEEDOR
            while(resultadoProveedor.next()) {
                cbProveedor.getItems().add(resultadoProveedor.getString("nombre"));
            }         
            
            resultadoTipo.close();
            resultadoUbicacion.close();
            resultadoProveedor.close();

        } catch (SQLException ex) {
            Logger.getLogger(GestionProductosController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
           
    }
    
    
    // ACTUALIZACION DE DATOS DE LA TABLA EXISTENCIAS /////////////////////////////////////////////////////////////////////
    @FXML
    private void actualizarExistencias(ActionEvent event) {
        tablaExistencias.getColumns().clear();
        this.cargarDatosTablaExistencias();
        
    }
     
    // ON ACTION PARA VOLVER AL MENU DE INICIO //////////////////////////////////////////////////////////////////////////////
    @FXML
    private void irInicioContenido(ActionEvent event) {
        
         try {
                    // SE CARGA LA SCENE DE GUI JEFE DE BODEGA 
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Principal.class.getResource("GuiJefeDeBodega.fxml"));
                    AnchorPane Gerente = (AnchorPane) loader.load();

                    // AGREGAMOS LA VENTANA
                    Scene scene = new Scene(Gerente);
                    Node node = (Node) event.getSource();
                    Stage primaryStage = (Stage) node.getScene().getWindow();
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    
                } catch (IOException e) {
                    e.printStackTrace();
                } 
    }
    
    // METODO ENCARGADO DE CARGAR LOS DATOS A LA TABLA EXISTENCIAS  ////////////////////////////////////////////////////////
    public  void cargarDatosTablaExistencias() {
        
         existencia = FXCollections.observableArrayList();
         ResultSet resultadoDatosTablaExistencias = gpDao.datosParaTablaExistencias();
         
         try{
            
            // TITULOS DE LAS COLUMNAS
            String[] titulos = {"Codigo","Producto","Cantidad","Precio","Tipo","Proveedor","Marca","Sección"};
            
            // AGREGAMOS LOS DATOS A LA TABLA DINAMICAMENTE
            
            for (int i = 0; i < resultadoDatosTablaExistencias.getMetaData().getColumnCount(); i++ ) {
                final int j = i;
                col = new TableColumn(titulos[i]);
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>(){                   
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> parametro) {                                                                                             
                        return new SimpleStringProperty((String)parametro.getValue().get(j));                       
                    }                   
                });
                tablaExistencias.getColumns().addAll(col);
                // ASIGNAMOS UN TAMAÑO A LAS COLUMNAS
                col.setMinWidth(100);
                System.out.println("Column ["+i+"] ");
                // CENTRAMOS LOS DATOS EN LA TABLA
                col.setCellFactory(new Callback<TableColumn<String,String>,TableCell<String,String>>(){
                    @Override
                    public TableCell<String, String> call(TableColumn<String, String> p) {
                        TableCell cell = new TableCell(){
                            @Override
                            protected void updateItem(Object t, boolean bln) {
                                if(t != null){
                                    super.updateItem(t, bln);
                                    System.out.println(t);
                                    setText(t.toString());
                                    setAlignment(Pos.CENTER_LEFT); //Setting the Alignment
                                }
                            }
                        };
                        return cell;
                    }
                });
            }
           
            //CARGAMOS DE LA BASE DE DATOS  
            while(resultadoDatosTablaExistencias.next()){
                //ITERACION DE FILA
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i = 1 ; i <= resultadoDatosTablaExistencias.getMetaData().getColumnCount(); i++){
                    //ITERACION DE COLUMNA
                    row.add(resultadoDatosTablaExistencias.getString(i));
                }
                System.out.println("Row [1] added "+row );
                existencia.addAll(row);
            }
            //FINALMENTE AGREGAMOS A LA TABLA EXISTENCIAS
            tablaExistencias.setItems(existencia);
            resultadoDatosTablaExistencias.close();
          }catch(SQLException e){
              System.out.println("Error "+e);            
          }
    }
  
    // METODO PARA BUSCAR EXISTENCIAS ///////////////////////////////////////////////////////////////////////////////////
    @FXML
    private void buscarExistencia(ActionEvent event) {
        
        if (!validation.soloLetras(txtBuscarExistencia.getText())) {
            return;
        }
        
        tablaExistencias.getItems().clear();
        String busqueda = txtBuscarExistencia.getText();
        ResultSet resultadoExistenciaTabla = gpDao.buscarExistenciaTabla(busqueda);
    
        try {
                       
            while(resultadoExistenciaTabla.next()){
                
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i = 1 ; i <= resultadoExistenciaTabla.getMetaData().getColumnCount(); i++){
                   
                    row.add(resultadoExistenciaTabla.getString(i));
                }
                existencia.addAll(row);
            }
            tablaExistencias.setItems(existencia);
            resultadoExistenciaTabla.close();
            
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }   
    }

    // METODO PARA CREAR UN NUEVO PRODUCTO ////////////////////////////////////////////////////////////////////////////////
    private void addProducto() {      
        
        int tipoid = cbTipo.getSelectionModel().getSelectedIndex() + 1;
        String nombre = txtNombrePro.getText();
        int precio = Integer.parseInt(txtPrecio.getText());
        String marca = txtMarca.getText();
        
        Productos pro = new Productos();
        
        pro.setTipo(tipoid);
        pro.setNombre(nombre);
        pro.setPrecio(precio);
        pro.setMarca(marca);
        
        PreparedStatement estado = proDao.nuevoProducto(pro);
        try {
            estado.executeUpdate();

          estado.close();
        } catch (SQLException ex) {
            Logger.getLogger(GestionProductosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // METODO PARA CREAR UNA NUEVA EXISTENCIA ///////////////////////////////////////////////////////////////////////////
    @FXML
    private void addExistencia(ActionEvent event) {
        // VALIDACIONES //////////////////////////////////////////////////
        if (!validation.soloNumeros(txtCantidad.getText(), "cantidad")) {
            return;
        }
        
         if (!validation.validarVacios(txtCantidad.getText(), "cantidad")) {
            return;
        }
         
        if (!validation.validarVacios(txtMarca.getText(), "marca")) {
            return;
        }
        
        if (!validation.validarVacios(txtNombrePro.getText(), "nombre")) {
            return;
        }
        
                
        if (!validation.validarVacios(txtPrecio.getText(), "precio")) {
            return;
        }
        
        
        
        if (!validation.soloNumeros(txtPrecio.getText(), "precio")) {
            return;
        }
        
        addProducto();
        
        int productoid1 = proDao.buscarProductoid(txtNombrePro.getText());
        
        int almacenid = cbUbicacion.getSelectionModel().getSelectedIndex() + 1;
        int codigo_proveedor = cbProveedor.getSelectionModel().getSelectedIndex() + 1;
        int productoid = productoid1;
        int cantidad = Integer.parseInt(txtCantidad.getText());
        
        ProductosEnExistencia proExist = new ProductosEnExistencia();
        
        proExist.setAlmacenid(almacenid);
        proExist.setCodigo_proveedor(codigo_proveedor);
        proExist.setProductoid(productoid);
        proExist.setCantidad(cantidad);
        
        PreparedStatement estado = proExistDao.nuevaExistencia(proExist);
        
        try {      
            
            int n  = estado.executeUpdate();
            
            if (n > 0) {
                
                tablaExistencias.getColumns().clear();
                tablaExistencias.getItems().clear();
                cargarDatosTablaExistencias();
            }
            estado.close();
            
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }

    // METODO PARA MODIFICAR UN PRODUCTO //////////////////////////////////////////////////////////////////////////////////   
    private void modificarProducto(){
               
        
        String nombre = txtNombrePro.getText();
        int tipoid = cbTipo.getSelectionModel().getSelectedIndex() + 1;
        int precio = Integer.parseInt(txtPrecio.getText());
        String marca = txtMarca.getText();
        int productoid2 = proDao.buscarProductoid(txtNombrePro.getText());
        
        Productos pro = new Productos();
        
        pro.setProductoid(productoid2);
        pro.setTipo(tipoid);
        pro.setNombre(nombre);
        pro.setPrecio(precio);
        pro.setMarca(marca);
        
        PreparedStatement estado = proDao.modificarProducto(pro);
        
        try {
                        
            int n = estado.executeUpdate();
            
            if (n > 0) {
                tablaExistencias.getColumns().clear();
                tablaExistencias.getItems().clear();
                cargarDatosTablaExistencias();
            }
            
            estado.close();
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
      
    }
        
    // METODO PARA MODIFICAR UNA EXISTENCIA ////////////////////////////////////////////////////////////////////////////////
    @FXML
    private void modificarExistencia(ActionEvent event) {
        // VALIDACIONES ////////////////////////////////////////////
        if (!validation.validarVacios(txtMarca.getText(), "marca")) {
            return;
        }
        
        if (!validation.validarVacios(txtNombrePro.getText(), "nombre")) {
            return;
        }
                        
        if (!validation.validarVacios(txtPrecio.getText(), "precio")) {
            return;
        }
                
        if (!validation.soloNumeros(txtPrecio.getText(), "precio")) {
            return;
        }
        
        if (!validation.soloNumeros(txtCantidad.getText(), "cantidad")) {
            return;
        }
        
         if (!validation.validarVacios(txtCantidad.getText(), "cantidad")) {
            return;
        }
        
        modificarProducto();
                
        int productoid1 = proDao.buscarProductoid(txtNombrePro.getText());
               
        int almacenid = cbUbicacion.getSelectionModel().getSelectedIndex() + 1;
        int codigo_proveedor = cbProveedor.getSelectionModel().getSelectedIndex() + 1;
        int productoid = productoid1;
        int cantidad = Integer.parseInt(txtCantidad.getText());
        
        ProductosEnExistencia proExist = new ProductosEnExistencia();
        
        proExist.setAlmacenid(almacenid);
        proExist.setCodigo_proveedor(codigo_proveedor);
        proExist.setProductoid(productoid);
        proExist.setCantidad(cantidad);
        
        PreparedStatement estado = proExistDao.modificarExistencia(proExist);   
        
        try {
                        
            int n = estado.executeUpdate();
            
            if (n > 0) {
                tablaExistencias.getColumns().clear();
                tablaExistencias.getItems().clear();
                cargarDatosTablaExistencias();
            }
            
            estado.close();
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
        
        
    }
    
    // METODO PARA CARGAR EXISTENCIAS EN EL FORMULARIO ////////////////////////////////////////////////////////////////////// 
    public void cargarExistenciasText(String valor) {
         
        ResultSet cargaExistencias = gpDao.cargaDeExistencias(valor);
        
        try {
                                 
            while (cargaExistencias.next()) {
                
                lbCod.setText(cargaExistencias.getString("codigo"));
                txtNombrePro.setText(cargaExistencias.getString("producto"));
                txtCantidad.setText(cargaExistencias.getString("cantidad"));
                txtPrecio.setText(cargaExistencias.getString("precio"));
                cbTipo.setValue(cargaExistencias.getString("tipo"));
                cbProveedor.setValue(cargaExistencias.getString("proveedor"));
                txtMarca.setText(cargaExistencias.getString("marca"));
                cbUbicacion.setValue(cargaExistencias.getString("seccion")); 
                 
            }
            cargaExistencias.close();
          } catch (SQLException ex) {
            System.out.println("Error "+ex);
        }
        
    }
    
    // GET PARA ENVIAR DATO A LA CARGA DEL FORMULARIO //////////////////////////////////////////////////////////////////
    @FXML
    private void getExistenciaSeleccionada(MouseEvent event) {
        
         tablaExistencias.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (tablaExistencias != null) {
                    // HABILITACION Y DESHABILITACION DE BOTONES ///////////////
                    btAddExistencia.setDisable(true);
                    btModificarExistencia.setDisable(false);
                    btAddExistencia.setStyle("-fx-background-color:grey");
                    btModificarExistencia.setStyle("-fx-background-color:#66CCCC");
                    
                    String valor = tablaExistencias.getSelectionModel().getSelectedItems().get(0).toString();
                    
                    String cincoDigitos = valor.substring(1, 6);
                    String cuatroDigitos = valor.substring(1, 5);
                    String tresDigitos = valor.substring(1, 4);
                    String dosDigitos = valor.substring(1, 3);
                    String unDigitos = valor.substring(1, 2);
                    
                    Pattern p = Pattern.compile("^[0-9]*$");
                    
                    Matcher m5 = p.matcher(cincoDigitos);
                    Matcher m4 = p.matcher(cuatroDigitos);
                    Matcher m3 = p.matcher(tresDigitos);
                    Matcher m2 = p.matcher(dosDigitos);
                    
                    if (m5.find()) {
                        cargarExistenciasText(cincoDigitos);
                    } else {
                        if (m4.find()) {
                            cargarExistenciasText(cuatroDigitos);
                        } else {
                            if (m3.find()) {
                                cargarExistenciasText(tresDigitos);
                            } else {
                                if (m2.find()) {
                                    cargarExistenciasText(dosDigitos);
                                } else {
                                    cargarExistenciasText(unDigitos);
                                }
                             }
                        }
                    }
                }
            }
        });
    }

    // NUEVO PARA LIMPIAR CAMPOS PRODUCTO //////////////////////////////////////////////////////////////////////////////////
    private void nuevoProducto() {
        
        cbTipo.setValue("Seleccionar");
        txtNombrePro.setText("");
        txtPrecio.setText("");
        txtMarca.setText("");
       
    }

            
    // NUEVO PARA LIMPIAR CAMPOS EXISTENCIA //////////////////////////////////////////////////////////////////////////////
    @FXML
    private void nuevaExistencia(ActionEvent event) {
    
        nuevoProducto();
                
        btAddExistencia.setDisable(false);
        btModificarExistencia.setDisable(true);
        btAddExistencia.setStyle("-fx-background-color:#66CCCC");
        btModificarExistencia.setStyle("-fx-background-color:grey");
        cbUbicacion.setValue("Seleccionar");
        cbProveedor.setValue("Seleccionar");
        txtCantidad.setText("");
        lbCod.setText("");
    }
    
      @FXML
    private void cerrarSesion(ActionEvent event) {
        
    }

    @FXML
    private void salir(ActionEvent event) {
        
    }
    
}
