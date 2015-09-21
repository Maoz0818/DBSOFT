
package screensframework;

import DAO.DespachosDao;
import DAO.DetalleDespachoDao;
import DAO.EstadosDao;
import DAO.GestionDespachosDao;
import DAO.GestionPedidosDao;
import DAO.GestionProductosDao;
import DAO.PedidosDao;
import DAO.ProductosDao;
import DAO.ProductosEnExistenciasDao;
import Logica.Despacho;
import Logica.DetalleDespacho;
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

public class GestionDespachosController implements Initializable {
       
    @FXML    private TableView tablaDespachos;
    @FXML    private TableView tablaDetallesDespacho;
    @FXML    private TextField txtBuscarDespacho;
    @FXML    private TableView tablaPedidos, tablaDetallesPedido;
    @FXML    private TextField txtBuscarPedido;
    @FXML    private TableView tablaExistencias;
    @FXML    private TextField txtBuscarExistencias;   
    @FXML    private ComboBox cbEstado;
    @FXML    private ComboBox cbProductos;
    @FXML    private TextField txtCantidad;
    @FXML    private Label lbDespachoid, lbDetalleid, lbPedidoid;
    @FXML    private TextField txtCodPedido;
    @FXML    private Button btCrearDespacho;
    @FXML    private Button btModificarDespacho;
    @FXML    private Button btCrearDetalle;  
    @FXML    private Button btModificarDetalle;
    @FXML    private TableColumn col;

    Stage stage;
    
    private Validaciones validation = new Validaciones();
    
/////OBJETOS DAO //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    GestionProductosDao gpDao = new GestionProductosDao();
    GestionDespachosDao gdDao = new GestionDespachosDao();
    GestionPedidosDao gPedDao = new GestionPedidosDao();
    ProductosDao proDao = new ProductosDao();
    DespachosDao desDao = new DespachosDao();
    DetalleDespachoDao detDesDao = new DetalleDespachoDao();
    ProductosEnExistenciasDao proExistDao = new ProductosEnExistenciasDao();
    EstadosDao estDao = new EstadosDao();
    PedidosDao pedDao = new PedidosDao();
    
///// LISTAS OBSERVABLES //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ObservableList<ObservableList> existencia;
    ObservableList<ObservableList> despachos;
    ObservableList<ObservableList> detalle;
    ObservableList<ObservableList> pedido;
    ObservableList<ObservableList> detallePedido;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
        //BOTONES DESABILITADOS
        
         btModificarDespacho.setDisable(true);
         btModificarDespacho.setStyle("-fx-background-color:grey");
         btCrearDespacho.setDisable(true);
         btCrearDespacho.setStyle("-fx-background-color:grey");
         btModificarDetalle.setDisable(true);
         btModificarDetalle.setStyle("-fx-background-color:grey");
         btCrearDetalle.setDisable(true);
         btCrearDetalle.setStyle("-fx-background-color:grey");
         
///////CARGAR DATOS A LOS COMBOBOX ///////////////////////////////////////////////////////////////////////////////////////////
         
         ResultSet resultadoEstados = estDao.comboBoxEstadosDespacho();
         ResultSet resultadoProductos = proDao.comboBoxProductos();
         
         try{
        // COMBOBOX DE ESTADOS DE DESPACHO 
            while(resultadoEstados.next()) {
                cbEstado.getItems().add(resultadoEstados.getString("nombre"));
            }
        // COMBOBOX DE PRODUCTOS   
            while(resultadoProductos.next()) {
                cbProductos.getItems().add(resultadoProductos.getString("nombre"));
            }
        // CERRAMOS LA COMUNICACION DE LOS RESULTSET   
            resultadoEstados.close();
            resultadoProductos.close();
            
         }catch (SQLException ex) {
            Logger.getLogger(GestionProductosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
/////// CARGA DE DATOS EN LA TABLA EXISTENCIAS ///////////////////////////////////////////////////////////////////////////////////////
    
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
    
////// CARGA DE DATOS EN LA TABLA DESPACHOS /////////////////////////////////////////////////////////////////////////////////////////7 
    public  void cargarDatosTablaDespachos() {
        
         despachos = FXCollections.observableArrayList();
         ResultSet resultadoDatosTablaDespachos = gdDao.datosParaTablaDespachos();
         
         try{
            
            // TITULOS DE LAS COLUMNAS
            String[] titulos = {"Cod Despacho","Sucursal","Cod Pedido","Estado Despacho","Tipo","Proveedor","Fecha"};
            
            // AGREGAMOS LOS DATOS A LA TABLA DINAMICAMENTE
            for (int i = 0; i < resultadoDatosTablaDespachos.getMetaData().getColumnCount(); i++ ) {
                final int j = i;
                col = new TableColumn(titulos[i]);
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>(){                   
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> parametro) {                                                                                             
                        return new SimpleStringProperty((String)parametro.getValue().get(j));                       
                    }                   
                });
                tablaDespachos.getColumns().addAll(col);
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
            while(resultadoDatosTablaDespachos.next()){
                //ITERACION DE FILA
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i = 1 ; i <= resultadoDatosTablaDespachos.getMetaData().getColumnCount(); i++){
                    //ITERACION DE COLUMNA
                    row.add(resultadoDatosTablaDespachos.getString(i));
                }
                System.out.println("Row [1] added "+row );
                despachos.addAll(row);
            }
            //FINALMENTE AGREGAMOS A LA TABLA DESPACHOS
            tablaDespachos.setItems(despachos);
            resultadoDatosTablaDespachos.close();
          }catch(SQLException e){
              System.out.println("Error "+e);            
          }
    }
    
///// CARGA DE DATOS A LA TABLA DETALLES DESPACHO QUE DEPENDE DE EL ITEM SELECCIONADO DE DESPACHOS /////////////////////////////////
    public  void cargarDatosTablaDetallesDespacho() {
        
         detalle = FXCollections.observableArrayList();
         // TOMAMOS EL DATO QUE SE ENVIA DESDE EL ITEM SELECCIONADO EN DESPACHOS GET DESPACHO SELECCIONADO
         ResultSet resultadoDatosTablaDetalles = gdDao.datosParaTablaDetallesDespacho(lbDespachoid.getText());
         
         try{
            // TITULOS DE LAS COLUMNAS
            String[] titulos = {"Cod Detalle","Cod Despacho","Producto","Cantidad"};
            
            // AGREGAMOS LOS DATOS A LA TABLA DINAMICAMENTE
            for (int i = 0; i < resultadoDatosTablaDetalles.getMetaData().getColumnCount(); i++ ) {
                final int j = i;
                col = new TableColumn(titulos[i]);
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>(){                   
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> parametro) {                                                                                             
                        return new SimpleStringProperty((String)parametro.getValue().get(j));                       
                    }                   
                });
                tablaDetallesDespacho.getColumns().addAll(col);
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
            while(resultadoDatosTablaDetalles.next()){
                //ITERACION DE FILA
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i = 1 ; i <= resultadoDatosTablaDetalles.getMetaData().getColumnCount(); i++){
                    //ITERACION DE COLUMNA
                    row.add(resultadoDatosTablaDetalles.getString(i));
                }
                System.out.println("Row [1] added "+row );
                detalle.addAll(row);
            }
            //FINALMENTE AGREGAMOS A LA TABLA DETALLE DE DESPACHO
            tablaDetallesDespacho.setItems(detalle);
            resultadoDatosTablaDetalles.close();
          }catch(SQLException e){
              System.out.println("Error "+e);            
          }
    }
    
///// CARGA DE DATOS EN LA TABLA PEDIDOS ////////////////////////////////////////////////////////////////////////////////////////////
    public  void cargarDatosTablaPedidos() {
        
         pedido = FXCollections.observableArrayList();
         ResultSet resultadoDatosTablaPedidos = gPedDao.datosParaTablaPedidos();
         
         try{
            // TITULOS DE LAS COLUMNAS
            String[] titulos = {"Cod Pedido","Sucursal","Estado","Fecha Pedido"};
            
            // AGREGAMOS LOS DATOS A LA TABLA DINAMICAMENTE
            for (int i = 0; i < resultadoDatosTablaPedidos.getMetaData().getColumnCount(); i++ ) {
                final int j = i;
                col = new TableColumn(titulos[i]);
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>(){                   
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> parametro) {                                                                                             
                        return new SimpleStringProperty((String)parametro.getValue().get(j));                       
                    }                   
                });
                tablaPedidos.getColumns().addAll(col);
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
            while(resultadoDatosTablaPedidos.next()){
                //ITERACION DE FILA
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i = 1 ; i <= resultadoDatosTablaPedidos.getMetaData().getColumnCount(); i++){
                    //ITERACION DE COLUMNA
                    row.add(resultadoDatosTablaPedidos.getString(i));
                }
                System.out.println("Row [1] added "+row );
                pedido.addAll(row);
            }
            //FINALMENTE AGREGAMOS A LA TABLA PEDIDOS
            tablaPedidos.setItems(pedido);
            resultadoDatosTablaPedidos.close();
          }catch(SQLException e){
              System.out.println("Error "+e);            
          }
    }
    
///// CARGA DE DATOS A LA TABLA DETALLES PEDIDO QUE DEPENDE DE EL ITEM SELECCIONADO DE PEDIDOS /////////////////////////////////
    public  void cargarDatosTablaDetallesPedido() {
        
         detallePedido = FXCollections.observableArrayList();
         // TOMAMOS EL DATO QUE SE ENVIA DESDE EL ITEM SELECCIONADO EN PEDIDOS GET PEDIDO SELECCIONADO
         ResultSet resultadoDatosTablaDetallesPedido = gPedDao.datosParaTablaDetallesPedido(lbPedidoid.getText());
         
         try{
            // TITULOS DE LAS COLUMNAS
            String[] titulos = {"Cod Detalle","Cod Pedido","Producto","Cantidad","Fecha Posible Entrega"};
            
            // AGREGAMOS LOS DATOS A LA TABLA DINAMICAMENTE
            for (int i = 0; i < resultadoDatosTablaDetallesPedido.getMetaData().getColumnCount(); i++ ) {
                final int j = i;
                col = new TableColumn(titulos[i]);
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>(){                   
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> parametro) {                                                                                             
                        return new SimpleStringProperty((String)parametro.getValue().get(j));                       
                    }                   
                });
                tablaDetallesPedido.getColumns().addAll(col);
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
            while(resultadoDatosTablaDetallesPedido.next()){
                //ITERACION DE FILA
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i = 1 ; i <= resultadoDatosTablaDetallesPedido.getMetaData().getColumnCount(); i++){
                    //ITERACION DE COLUMNA
                    row.add(resultadoDatosTablaDetallesPedido.getString(i));
                }
                System.out.println("Row [1] added "+row );
                detallePedido.addAll(row);
            }
            //FINALMENTE AGREGAMOS A LA TABLA DETALLE DE PEDIDOS
            tablaDetallesPedido.setItems(detallePedido);
            resultadoDatosTablaDetallesPedido.close();
          }catch(SQLException e){
              System.out.println("Error "+e);            
          }
    }
    
/////SET DE DATOS DEL FOMULARIO DESPACHO ///////////////////////////////////////////////////////////////////////////////////////////
    public void cargarDespachosText(String valor) {
        // CONSULTA A LA BASE DE DATOS 
        ResultSet cargaDespachos = gdDao.cargaDeDespachos(valor);
        
        try {                 
            while (cargaDespachos.next()) {
                // SET DE FORMULARIO CON LOS DATOS DE CONSULTA
                lbDespachoid.setText(cargaDespachos.getString("despachoid"));
                txtCodPedido.setText(cargaDespachos.getString("pedidoid"));
                cbEstado.setValue(cargaDespachos.getString("estado"));                 
            }
            cargaDespachos.close();
          } catch (SQLException ex) {
            System.out.println("Error "+ex);
        }
    }
    
////// GET DEL DESPACHO SELECCIONADO PARA TOMAR DATOS DE LA TABLA Y PONERLOS EN EL FORMULARIO DE DESPACHO ////////////////////////////
    @FXML
    private void getDespachoSeleccionada(MouseEvent event) {
        // TOMAMOS EL EVENTO DE LA TABLA DESPACHOS
        tablaDespachos.setOnMouseClicked(new EventHandler<MouseEvent>() {
        
            @Override
            public void handle(MouseEvent e) {
                tablaDetallesDespacho.getColumns().clear();
                if (tablaDespachos != null) {
                    // HABILITACION Y DESHABILITACION DE BOTONES
                    btCrearDetalle.setDisable(false);
                    btCrearDetalle.setStyle("-fx-background-color:#66CCCC");
                    btModificarDetalle.setDisable(true);
                    btModificarDetalle.setStyle("-fx-background-color:grey");
                    btModificarDespacho.setDisable(false);
                    btModificarDespacho.setStyle("-fx-background-color:#66CCCC");
                    
                    // TOMAMOS EL VALOR DE LA TABLA ELEGIDA
                    String valor = tablaDespachos.getSelectionModel().getSelectedItems().get(0).toString();
                    
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
                        cargarDespachosText(cincoDigitos);
                    } else {
                        if (m4.find()) {
                            cargarDespachosText(cuatroDigitos);
                        } else {
                            if (m3.find()) {
                                cargarDespachosText(tresDigitos);
                            } else {
                                if (m2.find()) {
                                    cargarDespachosText(dosDigitos);
                                } else {
                                    cargarDespachosText(unDigitos);                      
                                }
                             }
                        }
                    }
                }
            }
        });
        
    }
    
/////SET DE DATOS DEL FOMULARIO DETALLE /////////////////////////////////////////////////////////////////////////////////////////////
    public void cargarDetalleText(String valor) {
         
        ResultSet cargaDetalles = gdDao.cargaDeDetalles(valor);
        
        try {
                                 
            while (cargaDetalles.next()) {
                
                lbDetalleid.setText(cargaDetalles.getString("detalle_despachoid"));
                cbProductos.setValue(cargaDetalles.getString("producto"));
                txtCantidad.setText(cargaDetalles.getString("cantidad"));
                                                
            }
            
            cargaDetalles.close();
          } catch (SQLException ex) {
            System.out.println("Error "+ex);
        }
        
    }
    
////// GET DEL DETALLE SELECCIONADO PARA TOMAR DATOS DE LA TABLA Y PONERLOS EN EL FORMULARIO DE DETALLE ///////////////////////////////
    @FXML
    private void getDetalleDespachoSeleccionado(MouseEvent event) {
        
        tablaDetallesDespacho.setOnMouseClicked(new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent e) {
                if (tablaDetallesDespacho != null) {
                    
                    btCrearDetalle.setDisable(true);
                    btCrearDetalle.setStyle("-fx-background-color:grey");
                    btModificarDetalle.setDisable(false);
                    btModificarDetalle.setStyle("-fx-background-color:#66CCCC");
                    btModificarDespacho.setDisable(true);
                    btModificarDespacho.setStyle("-fx-background-color:grey");
                    btCrearDespacho.setDisable(true);
                    btCrearDespacho.setStyle("-fx-background-color:grey");
                                                           
                    String valor = tablaDetallesDespacho.getSelectionModel().getSelectedItems().get(0).toString();
                    
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
                        cargarDetalleText(cincoDigitos);
                    } else {
                        if (m4.find()) {
                            cargarDetalleText(cuatroDigitos);
                        } else {
                            if (m3.find()) {
                                cargarDetalleText(tresDigitos);
                            } else {
                                if (m2.find()) {
                                    cargarDetalleText(dosDigitos);
                                } else {
                                    cargarDetalleText(unDigitos);
                                }
                             }
                        }
                    }
                }
            }
        });
    }
    
/////SET DE DATOS DEL FOMULARIO DETALLE /////////////////////////////////////////////////////////////////////////////////////////////
    public void cargarPedidoText(String valor) {
         
        ResultSet cargaPedidos = gPedDao.cargaDePedidos(valor);
        
        try {                   
            while (cargaPedidos.next()) {
                lbPedidoid.setText(cargaPedidos.getString("pedidoid"));                                        
            }
            
            cargaPedidos.close();
          } catch (SQLException ex) {
            System.out.println("Error "+ex);
        }
    }
    
//////// GET DEL PEDIDO SELECCIONADO PARA TOMAR DATOS DE LA TABLA Y TRAER LOS DETALLES CORRESPONDIENTES ///////////////////////////////
    @FXML
    private void getPedidoSeleccionado(MouseEvent event) {
        
         tablaPedidos.setOnMouseClicked(new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent e) {
                tablaDetallesPedido.getColumns().clear();
                if (tablaPedidos != null) {
                                                                                
                    String valor = tablaPedidos.getSelectionModel().getSelectedItems().get(0).toString();
                    
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
                        cargarPedidoText(cincoDigitos);
                    } else {
                        if (m4.find()) {
                            cargarPedidoText(cuatroDigitos);
                        } else {
                            if (m3.find()) {
                                cargarPedidoText(tresDigitos);
                            } else {
                                if (m2.find()) {
                                    cargarPedidoText(dosDigitos);
                                } else {
                                    cargarPedidoText(unDigitos);
                                }
                             }
                        }
                    }
                }
            }
        });
    }
    
////// BUSQUEDA DE DESPACHO EN LA TABLA ////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private void buscarDespacho(ActionEvent event) {
        
        if (!validation.soloNumeros(txtBuscarDespacho.getText(), "buscar despacho")) {
            return;
        }
     
        tablaDespachos.getItems().clear();
        String busqueda = txtBuscarDespacho.getText();
        ResultSet resultadoDespachoTabla = gdDao.buscarDespachosTabla(busqueda);
    
        try {
                       
            while(resultadoDespachoTabla.next()){
                
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i = 1 ; i <= resultadoDespachoTabla.getMetaData().getColumnCount(); i++){
                   
                    row.add(resultadoDespachoTabla.getString(i));
                }
                despachos.addAll(row);
            }
            tablaDespachos.setItems(despachos);
            resultadoDespachoTabla.close();
            
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
    }
    
///// BUSQUEDA DE PEDIDO EN LA TABLA ////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private void buscarPedido(ActionEvent event) {
        
        if (!validation.soloNumeros(txtBuscarPedido.getText(), "buscar pedido")) {
            return;
        }
        
        tablaPedidos.getItems().clear();
        String busqueda = txtBuscarPedido.getText();
        ResultSet resultadoPedidoTabla = gPedDao.buscarPedidoTabla(busqueda);
    
        try {     
            while(resultadoPedidoTabla.next()){
                
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i = 1 ; i <= resultadoPedidoTabla.getMetaData().getColumnCount(); i++){
                   
                    row.add(resultadoPedidoTabla.getString(i));
                }
                pedido.addAll(row);
            }
            tablaPedidos.setItems(pedido);
            resultadoPedidoTabla.close();
            
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }   
        
    }
    
////// BUSQUEDA DE EXISTENCIA EN LA TABLA ////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private void buscarExistencia(ActionEvent event) {
        
        if (!validation.soloLetras(txtBuscarExistencias.getText())) {
            return;
        }
        
        tablaExistencias.getItems().clear();
        String busqueda = txtBuscarExistencias.getText();
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
    
/////ACTUALIZAR TABLA DESPACHOS //////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private void actualizarDespachos(ActionEvent event) {
        tablaDespachos.getColumns().clear();
        this.cargarDatosTablaDespachos();
        tablaDetallesDespacho.getItems().clear();
    }
    
/////ACTUALIZAR TABLA DETALLE DESPACHOS //////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private void actualizarDetalle(ActionEvent event) {
        tablaDetallesDespacho.getColumns().clear();
        this.cargarDatosTablaDetallesDespacho();
    }
    
/////ACTUALIZAR TABLA PEDIDOS //////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private void actualizarPedidos(ActionEvent event) {
        tablaPedidos.getColumns().clear();
        this.cargarDatosTablaPedidos();
        tablaDetallesPedido.getItems().clear();
    }
    
/////ACTUALIZAR TABLA DETALLE PEDIDOS //////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private void actualizarDetallePedido(ActionEvent event) {
       tablaDetallesPedido.getColumns().clear();
       this.cargarDatosTablaDetallesPedido();
    }
    
/////ACTUALIZAR TABLA EXISTENCIAS //////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private void actualizarExistencias(ActionEvent event) {
        tablaExistencias.getColumns().clear();
        this.cargarDatosTablaExistencias();
    }
    
/////ACTUALIZAR ESTADO DEL PEDIDO //////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private void actualizarEstado(ActionEvent event) {
        String consulta = lbPedidoid.getText();
        int estadoid = cbEstado.getSelectionModel().getSelectedIndex() + 1;
        pedDao.modificarEstado(consulta, estadoid);
        tablaPedidos.getColumns().clear();
        this.cargarDatosTablaPedidos();
    }
   
    @FXML
    private void getDetallePedidoSeleccionado(MouseEvent event) {
    }

    @FXML
    private void getExistenciaSeleccionada(MouseEvent event) {
    }

///// NUEVO DESPACHO ///////////////////////////////////////////////////////////////////////////////////////////////////////////////         
    @FXML
    private void crearDespacho(ActionEvent event) {
        
        if (!validation.soloNumeros(txtCodPedido.getText(), "codigo pedido")) {
            return;
        }
        
        int pedidoid = Integer.parseInt(txtCodPedido.getText());
        int estadoid = cbEstado.getSelectionModel().getSelectedIndex() + 1;
        
        Despacho desp = new Despacho();
        
        desp.setPedidoid(pedidoid);
        desp.setEstado(estadoid);
                    
        PreparedStatement estado = desDao.nuevoDespacho(desp);
        
        try {
            estado.executeUpdate();

          estado.close();
        } catch (SQLException ex) {
            Logger.getLogger(GestionProductosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
///// NUEVO DETALLE ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private void nuevoDetalle(ActionEvent event) {
        
        if (!validation.soloNumeros(txtCantidad.getText(), "cantidad")) {
            return;
        }
        
        int despachoid = Integer.parseInt(lbDespachoid.getText());
        int producto_existid = cbProductos.getSelectionModel().getSelectedIndex() + 1;
        int cantidad = Integer.parseInt(txtCantidad.getText());
        
        DetalleDespacho detDes = new DetalleDespacho();
        
        detDes.setDespachoid(despachoid);
        detDes.setProductoexistid(producto_existid);
        detDes.setCantidad(cantidad);
        
        PreparedStatement estado = detDesDao.nuevoDetalle(detDes);
        
        try {
            estado.executeUpdate();

          estado.close();
        } catch (SQLException ex) {
            Logger.getLogger(GestionProductosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//// MODIFICAR DESPACHO ///////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private void modificarDespacho(ActionEvent event) {
        
        int despachoid = Integer.parseInt(lbDespachoid.getText());
        int pedidoid = Integer.parseInt(txtCodPedido.getText());
        int estadoid = cbEstado.getSelectionModel().getSelectedIndex() + 1;
        
        Despacho desp = new Despacho();
        
        desp.setDespachoid(despachoid);
        desp.setPedidoid(pedidoid);
        desp.setEstado(estadoid);
               
        PreparedStatement estado = desDao.modificarDespacho(desp);
        
        try {
                        
            int n = estado.executeUpdate();
            
            if (n > 0) {
                tablaDespachos.getColumns().clear();
                tablaDespachos.getItems().clear();
                cargarDatosTablaDespachos();
            }
            
            estado.close();
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
        
    }
    
///// MODIFICAR DETALLE ///////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private void modificarDetalle(ActionEvent event) {
        
        int detalleid = Integer.parseInt(lbDetalleid.getText());
        int existenciaid = cbProductos.getSelectionModel().getSelectedIndex() + 1;
        int cantidad = Integer.parseInt(txtCantidad.getText());
                
        DetalleDespacho detDes = new DetalleDespacho();
        
        detDes.setDetalleDespachoid(detalleid);
        detDes.setProductoexistid(existenciaid);
        detDes.setCantidad(cantidad);
               
        PreparedStatement estado = detDesDao.modificarDetalle(detDes);
        
        try {
                        
            int n = estado.executeUpdate();
            
            if (n > 0) {
                tablaDetallesDespacho.getColumns().clear();
                tablaDetallesDespacho.getItems().clear();
                cargarDatosTablaDetallesDespacho();
            }
            
            estado.close();
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }
    
///// VOLVER A INICIO DE VISTA JEFE DE BODEGA ////////////////////////////////////////////////////////////////////////////////
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
    
///// LIMPIAR PARA NUEVO DESPACHO ///////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private void nuevoDespacho(ActionEvent event) {
        lbDespachoid.setText("");
        txtCodPedido.setText("");
        cbEstado.setValue("Seleccionar");
        lbDetalleid.setText("");
        cbProductos.setValue("Seleccionar");
        txtCantidad.setText("");
        btCrearDetalle.setDisable(true);
        btCrearDetalle.setStyle("-fx-background-color:grey");
        btModificarDetalle.setDisable(true);
        btModificarDetalle.setStyle("-fx-background-color:grey");
        btModificarDespacho.setDisable(true);
        btModificarDespacho.setStyle("-fx-background-color:grey");
        btCrearDespacho.setDisable(false);
        btCrearDespacho.setStyle("-fx-background-color:#66CCCC");
    }
        
}
