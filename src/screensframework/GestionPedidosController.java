
package screensframework;

import DAO.DetallePedidosDao;
import DAO.EstadosDao;
import DAO.GestionPedidosDao;
import DAO.GestionProductosDao;
import DAO.PedidosDao;
import DAO.ProductosDao;
import DAO.ProductosEnExistenciasDao;
import Logica.DetallePedido;
import Logica.Pedidos;
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
import javafx.geometry.Insets;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;


public class GestionPedidosController implements Initializable {
    @FXML    private Label lbCodSucursal;
    @FXML    private AnchorPane productoexistid;
    @FXML    private TableView tablaPedidos;
    @FXML    private TableView tablaDetallesPedido;
    @FXML    private TextField txtBuscarPedido;
    @FXML    private Button btBuscarPedido;
    @FXML    private Button btActualizarPedidos;
    @FXML    private Button btActualizarDetallePedido;
    @FXML    private AnchorPane productoexistid11;
    @FXML    private TableView tablaExistencias;
    @FXML    private TextField txtBuscarExistencias;
    @FXML    private Button btBuscarExistencia;
    @FXML    private Font x211;
    @FXML    private Button btActualizarExistencias;
    @FXML    private Font x3;
    @FXML    private ComboBox cbEstado;
    @FXML    private ComboBox cbProductos;
    @FXML    private TextField txtCantidad;
    @FXML    private Label lbPedidoId;
    @FXML    private Button btCrearPedido;
    @FXML    private Button btModificarPedido;
    @FXML    private Button btCrearDetalle;
    @FXML    private Button btModificarDetalle;
    @FXML    private Label lbDetalleid;
    @FXML    private Insets x1;
    @FXML    private Button btMenu;
    @FXML    private Button btNuevoPedido;
    @FXML    private TableColumn col;
             private String sucursalid;
             private int numero;
             
        // OBJETO VALIDACIONES
        private Validaciones validation = new Validaciones();
    
/////OBJETOS DAO //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    GestionProductosDao gpDao = new GestionProductosDao();
    GestionPedidosDao gPedDao = new GestionPedidosDao();
    ProductosDao proDao = new ProductosDao();
    DetallePedidosDao detPedDao = new DetallePedidosDao();
    ProductosEnExistenciasDao proExistDao = new ProductosEnExistenciasDao();
    EstadosDao estDao = new EstadosDao();
    PedidosDao pedDao = new PedidosDao();
    
///// LISTAS OBSERVABLES //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ObservableList<ObservableList> existencia;
    ObservableList<ObservableList> pedidos;
    ObservableList<ObservableList> detallePedido;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        boolean bandera;
        do{
        try{
        numero=Integer.parseInt(JOptionPane.showInputDialog(null,"INGRESA EL CODIGO DE TU SUCURSAL"));
        bandera=true;
        }
        catch(NumberFormatException e){
        javax.swing.JOptionPane.showMessageDialog(null, "EL CODIGO SE COMPONE DE NUMERO SOLAMENTE","CODIGO DE SUCURSAL INVALIDO", JOptionPane.ERROR_MESSAGE);
        bandera=false;
        }
        }while(!bandera);
        
        lbCodSucursal.setText(Integer.toString(numero));
        
         //BOTONES DESABILITADOS
        
         btModificarPedido.setDisable(true);
         btModificarPedido.setStyle("-fx-background-color:grey");
         btCrearPedido.setDisable(true);
         btCrearPedido.setStyle("-fx-background-color:grey");
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
         ResultSet resultadoDatosTablaExistenciasSucursal = gpDao.datosParaTablaExistenciasSucursal();
         
         try{
            
            // TITULOS DE LAS COLUMNAS
            String[] titulos = {"Codigo","Producto","Cantidad","Precio","Proveedor","Marca"};
            
            // AGREGAMOS LOS DATOS A LA TABLA DINAMICAMENTE
            
            for (int i = 0; i < resultadoDatosTablaExistenciasSucursal.getMetaData().getColumnCount(); i++ ) {
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
            while(resultadoDatosTablaExistenciasSucursal.next()){
                //ITERACION DE FILA
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i = 1 ; i <= resultadoDatosTablaExistenciasSucursal.getMetaData().getColumnCount(); i++){
                    //ITERACION DE COLUMNA
                    row.add(resultadoDatosTablaExistenciasSucursal.getString(i));
                }
                System.out.println("Row [1] added "+row );
                existencia.addAll(row);
            }
            //FINALMENTE AGREGAMOS A LA TABLA EXISTENCIAS
            tablaExistencias.setItems(existencia);
            resultadoDatosTablaExistenciasSucursal.close();
          }catch(SQLException e){
              System.out.println("Error "+e);            
          }
    }
    
    ///// CARGA DE DATOS A LA TABLA DETALLES PEDIDO QUE DEPENDE DE EL ITEM SELECCIONADO DE PEDIDOS /////////////////////////////////
    public  void cargarDatosTablaDetallesPedido() {
        
         detallePedido = FXCollections.observableArrayList();
         // TOMAMOS EL DATO QUE SE ENVIA DESDE EL ITEM SELECCIONADO EN PEDIDOS GET PEDIDO SELECCIONADO
         ResultSet resultadoDatosTablaDetallesPedido = gPedDao.datosParaTablaDetallesPedido(lbPedidoId.getText());
         
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
    
    ///// CARGA DE DATOS EN LA TABLA PEDIDOS ////////////////////////////////////////////////////////////////////////////////////////////
    public  void cargarDatosTablaPedidos() {
        
         pedidos = FXCollections.observableArrayList();
         ResultSet resultadoDatosTablaPedidosSucursal = gPedDao.datosParaTablaPedidosSucursal(lbCodSucursal.getText());
         
         try{
            // TITULOS DE LAS COLUMNAS
            String[] titulos = {"Cod Pedido","Sucursal","Estado","Fecha Pedido"};
            
            // AGREGAMOS LOS DATOS A LA TABLA DINAMICAMENTE
            for (int i = 0; i < resultadoDatosTablaPedidosSucursal.getMetaData().getColumnCount(); i++ ) {
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
            while(resultadoDatosTablaPedidosSucursal.next()){
                //ITERACION DE FILA
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i = 1 ; i <= resultadoDatosTablaPedidosSucursal.getMetaData().getColumnCount(); i++){
                    //ITERACION DE COLUMNA
                    row.add(resultadoDatosTablaPedidosSucursal.getString(i));
                }
                System.out.println("Row [1] added "+row );
                pedidos.addAll(row);
            }
            //FINALMENTE AGREGAMOS A LA TABLA PEDIDOS
            tablaPedidos.setItems(pedidos);
            resultadoDatosTablaPedidosSucursal.close();
          }catch(SQLException e){
              System.out.println("Error "+e);            
          }
    }
    
    /////SET DE DATOS DEL FOMULARIO DETALLE /////////////////////////////////////////////////////////////////////////////////////////////
    public void cargarPedidoText(String valor) {
         
        ResultSet cargaPedidos = gPedDao.cargaDePedidos(valor);
        
        try {                   
            while (cargaPedidos.next()) {
                // SET DE FORMULARIO CON LOS DATOS DE CONSULTA
                lbPedidoId.setText(cargaPedidos.getString("pedidoid"));
                cbEstado.setValue(cargaPedidos.getString("estado"));
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
                    
                    // HABILITACION Y DESHABILITACION DE BOTONES
                    btCrearDetalle.setDisable(false);
                    btCrearDetalle.setStyle("-fx-background-color:#66CCCC");
                    btModificarDetalle.setDisable(true);
                    btModificarDetalle.setStyle("-fx-background-color:grey");
                    btModificarPedido.setDisable(false);
                    btModificarPedido.setStyle("-fx-background-color:#66CCCC");
                                                                                
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
    
    /////SET DE DATOS DEL FOMULARIO DETALLE /////////////////////////////////////////////////////////////////////////////////////////////
    public void cargarDetalleText(String valor) {
         
        ResultSet cargaDetalles = gPedDao.cargaDeDetallesPedido(valor);
        
        try {
                                 
            while (cargaDetalles.next()) {
                
                lbDetalleid.setText(cargaDetalles.getString("detalle_pedidoid"));
                cbProductos.setValue(cargaDetalles.getString("producto"));
                txtCantidad.setText(cargaDetalles.getString("cantidad"));
                                                
            }
            
            cargaDetalles.close();
          } catch (SQLException ex) {
            System.out.println("Error "+ex);
        }
        
    }

    ///// GET DEL DETALLE SELECCIONADO PARA TOMAR DATOS DE LA TABLA Y PONERLOS EN EL FORMULARIO DE DETALLE ///////////////////////////////
    @FXML
    private void getDetallePedidoSeleccionado(MouseEvent event) {
        
        tablaDetallesPedido.setOnMouseClicked(new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent e) {
                if (tablaDetallesPedido != null) {
                    
                    btCrearDetalle.setDisable(true);
                    btCrearDetalle.setStyle("-fx-background-color:grey");
                    btModificarDetalle.setDisable(false);
                    btModificarDetalle.setStyle("-fx-background-color:#66CCCC");
                    btModificarPedido.setDisable(true);
                    btModificarPedido.setStyle("-fx-background-color:grey");
                    btCrearPedido.setDisable(true);
                    btCrearPedido.setStyle("-fx-background-color:grey");
                                                           
                    String valor = tablaDetallesPedido.getSelectionModel().getSelectedItems().get(0).toString();
                    
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

    ///// BUSQUEDA DE PEDIDO EN LA TABLA ////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private void buscarPedido(ActionEvent event) {
        
        if (!validation.soloNumeros(txtBuscarPedido.getText(), "buscar pedido")) {
            return;
        }
        
        tablaPedidos.getItems().clear();
        String busqueda = txtBuscarPedido.getText();
        String codSucursal = lbCodSucursal.getText();
        ResultSet resultadoPedidoTabla = gPedDao.buscarPedidoTablaSucursal(busqueda, codSucursal);
    
        try {     
            while(resultadoPedidoTabla.next()){
                
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i = 1 ; i <= resultadoPedidoTabla.getMetaData().getColumnCount(); i++){
                   
                    row.add(resultadoPedidoTabla.getString(i));
                }
                pedidos.addAll(row);
            }
            tablaPedidos.setItems(pedidos);
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

    /////ACTUALIZAR TABLA PEDIDOS //////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private void actualizarPedidos(ActionEvent event) {
        tablaPedidos.getColumns().clear();
        this.cargarDatosTablaPedidos();
        tablaDetallesPedido.getItems().clear();
    }
    
    /////ACTUALIZAR TABLA EXISTENCIAS //////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private void actualizarExistencias(ActionEvent event) {
        tablaExistencias.getColumns().clear();
        this.cargarDatosTablaExistencias();
    }

    /////ACTUALIZAR TABLA DETALLE PEDIDOS //////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private void actualizarDetallePedido(ActionEvent event) {
       tablaDetallesPedido.getColumns().clear();
       this.cargarDatosTablaDetallesPedido();
    }

    @FXML
    private void crearPedido(ActionEvent event) {
        
        int codSucursal = Integer.parseInt(lbCodSucursal.getText());
        int estadoid = cbEstado.getSelectionModel().getSelectedIndex() + 1;
        
        Pedidos ped = new Pedidos();
        
        ped.setSucursalid(codSucursal);
        ped.setEstado(estadoid);
                    
        PreparedStatement estado = pedDao.nuevoPedido(ped);
        
        try {
            
            int n = estado.executeUpdate();
            
            if (n > 0) {
                tablaPedidos.getColumns().clear();
                tablaPedidos.getItems().clear();
                cargarDatosTablaPedidos();
            }

          estado.close();
        } catch (SQLException ex) {
            Logger.getLogger(GestionProductosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //// MODIFICAR PEDIDO ///////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private void modificarPedido(ActionEvent event) {
        
        int pedidoid = Integer.parseInt(lbPedidoId.getText());
        int codSucursal = Integer.parseInt(lbCodSucursal.getText());
        int estadoid = cbEstado.getSelectionModel().getSelectedIndex() + 1;
        
        Pedidos ped = new Pedidos();
        
        ped.setPedidoid(pedidoid);
        ped.setSucursalid(codSucursal);
        ped.setEstado(estadoid);
               
        PreparedStatement estado = pedDao.modificarPedido(ped);
        
        try {
                        
            int n = estado.executeUpdate();
            
            if (n > 0) {
                tablaPedidos.getColumns().clear();
                tablaPedidos.getItems().clear();
                cargarDatosTablaPedidos();
            }
            
            estado.close();
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }

    ///// NUEVO DETALLE ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private void nuevoDetalle(ActionEvent event) {
        
        if (!validation.soloNumeros(txtCantidad.getText(), "cantidad")) {
            return;
        }
        
        int pedidoid = Integer.parseInt(lbPedidoId.getText());
        int producto_existid = cbProductos.getSelectionModel().getSelectedIndex() + 1;
        int cantidad = Integer.parseInt(txtCantidad.getText());
        
        DetallePedido detPed = new DetallePedido();
        
        detPed.setPedidoid(pedidoid);
        detPed.setProductoexistid(producto_existid);
        detPed.setCantidad(cantidad);
        
        PreparedStatement estado = detPedDao.nuevoDetalle(detPed);
        
        try {
            int n = estado.executeUpdate();
            
            if (n > 0) {
                tablaDetallesPedido.getColumns().clear();
                tablaDetallesPedido.getItems().clear();
                cargarDatosTablaDetallesPedido();
            }

          estado.close();
        } catch (SQLException ex) {
            Logger.getLogger(GestionProductosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ///// MODIFICAR DETALLE ///////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private void modificarDetalle(ActionEvent event) {
        
        int detalleid = Integer.parseInt(lbDetalleid.getText());
        int existenciaid = cbProductos.getSelectionModel().getSelectedIndex() + 1;
        int cantidad = Integer.parseInt(txtCantidad.getText());
                
        DetallePedido detPed = new DetallePedido();
        
        detPed.setDetallePedidoid(detalleid);
        detPed.setProductoexistid(existenciaid);
        detPed.setCantidad(cantidad);
               
        PreparedStatement estado = detPedDao.modificarDetalle(detPed);
        
        try {
                        
            int n = estado.executeUpdate();
            
            if (n > 0) {
                tablaDetallesPedido.getColumns().clear();
                tablaDetallesPedido.getItems().clear();
                cargarDatosTablaDetallesPedido();
            }
            
            estado.close();
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }

    ///// VOLVER A INICIO DE VISTA SUCURSAL /////////////////////////////////////////////////////////////////////
    @FXML
    private void irInicioContenido(ActionEvent event) {
        
        try {
                    // SE CARGA LA SCENE DE GUI JEFE DE BODEGA 
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Principal.class.getResource("GuiSucursal.fxml"));
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

    ///// LIMPIAR PARA NUEVO PEDIDO ///////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private void nuevoPedido(ActionEvent event) {
        lbPedidoId.setText("");
        cbEstado.setValue("Seleccionar");
        lbDetalleid.setText("");
        cbProductos.setValue("Seleccionar");
        txtCantidad.setText("");
        btCrearDetalle.setDisable(true);
        btCrearDetalle.setStyle("-fx-background-color:grey");
        btModificarDetalle.setDisable(true);
        btModificarDetalle.setStyle("-fx-background-color:grey");
        btModificarPedido.setDisable(true);
        btModificarPedido.setStyle("-fx-background-color:grey");
        btCrearPedido.setDisable(false);
        btCrearPedido.setStyle("-fx-background-color:#66CCCC");
    }
    
}
