package screensframework;

import DAO.ComprasDao;
import DAO.DetalleOrdenDao;
import DAO.GestionComprasDao;
import DAO.GestionProductosDao;
import DAO.EstadosDao;
import DAO.ProveedorDao;
import Logica.OrdenesCompra;
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

public class GestionComprasController implements Initializable {

    @FXML    private TableView tablaCompras;
    @FXML    private TextField txtBuscarCompra;
    @FXML    private TableView tablaDetallesCompra;
    @FXML    private TableView tablaExistencias;
    @FXML    private TextField txtBuscarInventario;
    @FXML    private ComboBox cbProveedor;
    @FXML    private ComboBox cbEstado;
    @FXML    private Button btCrearCompra;
    @FXML    private Button btmodificarCompra;
    @FXML    private TextField txtNombrePro;
    @FXML    private TextField txtMarca;
    @FXML    private TextField txtDia;
    @FXML    private TextField txtMes;
    @FXML    private TextField txtAno;
    @FXML    private TextField txtCantidad;
    @FXML    private Button btAddItem;
    @FXML    private Button btModificarItem;
    @FXML    private Label lbOrdenid2;
    @FXML    private Label lbOrdenid;
    @FXML    private TableColumn col;

    Stage stage;

    private Validaciones validation = new Validaciones();

    //OBJETOS DAO
    GestionProductosDao gpDao = new GestionProductosDao();
    GestionComprasDao gcDao = new GestionComprasDao();
    DetalleOrdenDao doDao = new DetalleOrdenDao();
    ComprasDao comDao = new ComprasDao();
    EstadosDao estDao = new EstadosDao();
    ProveedorDao provDao = new ProveedorDao();

///LISTAS OBSERVABLES
    ObservableList<ObservableList> compras;
    ObservableList<ObservableList> detalle;
    ObservableList<ObservableList> existencia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //BOTONES DESABILITADOS
        btmodificarCompra.setDisable(true);
        btmodificarCompra.setStyle("-fx-background-color:grey");
        btCrearCompra.setDisable(true);
        btCrearCompra.setStyle("-fx-background-color:grey");
        btModificarItem.setDisable(true);
        btModificarItem.setStyle("-fx-background-color:grey");
        btAddItem.setDisable(true);
        btAddItem.setStyle("-fx-background-color:grey");

        ///CARGAR DATOS A LOS COMBOBOX
        ResultSet resultadoEstados = estDao.comboBoxEstadosDespacho();
        ResultSet resultadoProveedor = provDao.comboBoxProveedor();

        try {
            //COMBOBOX DE ESTADOS DE COMPRA
            while (resultadoEstados.next()) {
                cbEstado.getItems().add(resultadoEstados.getString("nombre"));
            }
            // COMBOBOX DE PROVEEDOR
            while (resultadoProveedor.next()) {
                cbProveedor.getItems().add(resultadoProveedor.getString("nombre"));
            }
            //CERRAMOS LA COMUNICACION DE LOS RESULTSET
            resultadoEstados.close();
            resultadoProveedor.close();

        } catch (SQLException ex) {
            Logger.getLogger(GestionProductosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ////CARGA DE DATOS EN LA TABLA EXISTENCIAS
    public void cargarDatosTablaExistencias() {

        existencia = FXCollections.observableArrayList();
        ResultSet resultadoDatosTablaExistencias = gpDao.datosParaTablaExistencias();

        try {

            // TITULOS DE LAS COLUMNAS
            String[] titulos = {"Codigo", "Producto", "Cantidad", "Precio", "Tipo", "Proveedor", "Marca", "Sección"};

            // AGREGAMOS LOS DATOS A LA TABLA DINAMICAMENTE
            for (int i = 0; i < resultadoDatosTablaExistencias.getMetaData().getColumnCount(); i++) {
                final int j = i;
                col = new TableColumn(titulos[i]);
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> parametro) {
                        return new SimpleStringProperty((String) parametro.getValue().get(j));
                    }
                });
                tablaExistencias.getColumns().addAll(col);
                // ASIGNAMOS UN TAMAÑO A LAS COLUMNAS
                col.setMinWidth(100);
                System.out.println("Column [" + i + "] ");
                // CENTRAMOS LOS DATOS EN LA TABLA
                col.setCellFactory(new Callback<TableColumn<String, String>, TableCell<String, String>>() {
                    @Override
                    public TableCell<String, String> call(TableColumn<String, String> p) {
                        TableCell cell = new TableCell() {
                            @Override
                            protected void updateItem(Object t, boolean bln) {
                                if (t != null) {
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
            while (resultadoDatosTablaExistencias.next()) {
                //ITERACION DE FILA
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultadoDatosTablaExistencias.getMetaData().getColumnCount(); i++) {
                    //ITERACION DE COLUMNA
                    row.add(resultadoDatosTablaExistencias.getString(i));
                }
                System.out.println("Row [1] added " + row);
                existencia.addAll(row);
            }
            //FINALMENTE AGREGAMOS A LA TABLA EXISTENCIAS
            tablaExistencias.setItems(existencia);
            resultadoDatosTablaExistencias.close();
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }

    //CARGA DE DATOS DE TABLA COMPRAS
    public void cargarDatosTablaCompras() {

        compras = FXCollections.observableArrayList();
        ResultSet resultadoDatosTablaCompras = gcDao.datosParaTablaCompras();

        try {

            // TITULOS DE LAS COLUMNAS
            String[] titulos = {"Cod Compra", "Proveedor", "Fecha Creación Orden", "Estado de Orden"};

            // AGREGAMOS LOS DATOS A LA TABLA DINAMICAMENTE
            for (int i = 0; i < resultadoDatosTablaCompras.getMetaData().getColumnCount(); i++) {
                final int j = i;
                col = new TableColumn(titulos[i]);
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> parametro) {
                        return new SimpleStringProperty((String) parametro.getValue().get(j));
                    }
                });
                tablaCompras.getColumns().addAll(col);
                // ASIGNAMOS UN TAMAÑO A LAS COLUMNAS
                col.setMinWidth(100);
                System.out.println("Column [" + i + "] ");
                // CENTRAMOS LOS DATOS EN LA TABLA
                col.setCellFactory(new Callback<TableColumn<String, String>, TableCell<String, String>>() {
                    @Override
                    public TableCell<String, String> call(TableColumn<String, String> p) {
                        TableCell cell = new TableCell() {
                            @Override
                            protected void updateItem(Object t, boolean bln) {
                                if (t != null) {
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
            while (resultadoDatosTablaCompras.next()) {
                //ITERACION DE FILA
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultadoDatosTablaCompras.getMetaData().getColumnCount(); i++) {
                    //ITERACION DE COLUMNA
                    row.add(resultadoDatosTablaCompras.getString(i));
                }
                System.out.println("Row [1] added " + row);
                compras.addAll(row);
            }
            //FINALMENTE AGREGAMOS A LA TABLA COMPRAS
            tablaCompras.setItems(compras);
            resultadoDatosTablaCompras.close();
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }

    //CARGA DE DATOS A LA TABLA DETALLES COMPRA QUE DEPENDE DE EL ITEM SELECCIONADO DE COMPRAS
    public void cargarDatosTablaDetallesCompra() {

        detalle = FXCollections.observableArrayList();
        ResultSet resultadoDatosTablaDetallesCompra = gcDao.datosParaTablaDetallesCompra(lbOrdenid.getText());

        try {

            // TITULOS DE LAS COLUMNAS
            String[] titulos = {"Cod Detalle", "Cod Compra", "Proveedor", "Producto", "Marca", "Cantidad", "Plazo de entrega"};

            // AGREGAMOS LOS DATOS A LA TABLA DINAMICAMENTE
            for (int i = 0; i < resultadoDatosTablaDetallesCompra.getMetaData().getColumnCount(); i++) {
                final int j = i;
                col = new TableColumn(titulos[i]);
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> parametro) {
                        return new SimpleStringProperty((String) parametro.getValue().get(j));
                    }
                });
                tablaDetallesCompra.getColumns().addAll(col);
                // ASIGNAMOS UN TAMAÑO A LAS COLUMNAS
                col.setMinWidth(100);
                System.out.println("Column [" + i + "] ");
                // CENTRAMOS LOS DATOS EN LA TABLA
                col.setCellFactory(new Callback<TableColumn<String, String>, TableCell<String, String>>() {
                    @Override
                    public TableCell<String, String> call(TableColumn<String, String> p) {
                        TableCell cell = new TableCell() {
                            @Override
                            protected void updateItem(Object t, boolean bln) {
                                if (t != null) {
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
            while (resultadoDatosTablaDetallesCompra.next()) {
                //ITERACION DE FILA
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultadoDatosTablaDetallesCompra.getMetaData().getColumnCount(); i++) {
                    //ITERACION DE COLUMNA
                    row.add(resultadoDatosTablaDetallesCompra.getString(i));
                }
                System.out.println("Row [1] added " + row);
                detalle.addAll(row);
            }
            //FINALMENTE AGREGAMOS A LA TABLA DETALLES COMPRA
            tablaDetallesCompra.setItems(detalle);
            resultadoDatosTablaDetallesCompra.close();
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }

    @FXML
    private void getCompraSeleccionada(MouseEvent event) {
        //TOMAMOS EL EVENTO DE LA TABLA COMPRAS
        tablaCompras.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent e) {
                tablaDetallesCompra.getColumns().clear();
                if (tablaCompras != null) {
                    //HABILITACION Y DESHABILITACION DE BOTONES
                    btCrearCompra.setDisable(true);
                    btCrearCompra.setStyle("-fx-background-color:grey");
                    btmodificarCompra.setDisable(true);
                    btmodificarCompra.setStyle("-fx-background-color:#66CCCC");
                    btmodificarCompra.setDisable(false);
                    //TOMAMOS EL VALOR DE LA TABLA ELEGIDA
                    String valor = tablaCompras.getSelectionModel().getSelectedItems().get(0).toString();

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
                        cargarComprasText(cincoDigitos);
                    } else {
                        if (m4.find()) {
                            cargarComprasText(cuatroDigitos);
                        } else {
                            if (m3.find()) {
                                cargarComprasText(tresDigitos);
                            } else {
                                if (m2.find()) {
                                    cargarComprasText(dosDigitos);
                                } else {
                                    cargarComprasText(unDigitos);
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    /////SET DE DATOS DEL FOMULARIO COMPRA ///////////////////////////////////////////////////////////////////////////////////////////
    public void cargarComprasText(String valor) {
        // CONSULTA A LA BASE DE DATOS 
        ResultSet cargaCompras = gcDao.cargarCompras(valor);

        try {
            while (cargaCompras.next()) {
                // SET DE FORMULARIO CON LOS DATOS DE CONSULTA
                lbOrdenid.setText(cargaCompras.getString("ordenid"));
                cbProveedor.setValue(cargaCompras.getString("proveedor"));
                cbEstado.setValue(cargaCompras.getString("estado"));
            }
            cargaCompras.close();
        } catch (SQLException ex) {
            System.out.println("Error " + ex);
        }
    }

    @FXML
    private void buscarCompra(ActionEvent event) {

        if (!validation.soloNumeros(txtBuscarCompra.getText(), "buscar compra")) {
            return;
        }

        tablaCompras.getItems().clear();
        String busqueda = txtBuscarCompra.getText();
        ResultSet resultadoCompraTabla = gcDao.buscarComprasTabla(busqueda);

        try {

            while (resultadoCompraTabla.next()) {

                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultadoCompraTabla.getMetaData().getColumnCount(); i++) {

                    row.add(resultadoCompraTabla.getString(i));
                }
                compras.addAll(row);
            }
            tablaCompras.setItems(compras);
            resultadoCompraTabla.close();

        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    @FXML
    private void actualizarCompras(ActionEvent event) {
        tablaCompras.getColumns().clear();
        this.cargarDatosTablaCompras();
        tablaDetallesCompra.getItems().clear();
    }

    @FXML
    private void getDetalleCompraSeleccionada(MouseEvent event) {

        tablaDetallesCompra.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent e) {
                if (tablaDetallesCompra != null) {

                    btAddItem.setDisable(true);
                    btAddItem.setStyle("-fx-background-color:grey");
                    btModificarItem.setDisable(false);
                    btModificarItem.setStyle("-fx-background-color:#66CCCC");

                    String valor = tablaDetallesCompra.getSelectionModel().getSelectedItems().get(0).toString();

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

    public void cargarDetalleText(String valor) {

        ResultSet cargaDetalles = gcDao.cargarDetalles(valor);

        try {

            while (cargaDetalles.next()) {

                lbOrdenid2.setText(cargaDetalles.getString("ordenid"));
                txtNombrePro.setText(cargaDetalles.getString("producto"));
                txtMarca.setText(cargaDetalles.getString("marca"));
                // falta agregar la fecha
                txtCantidad.setText(cargaDetalles.getString("cantidad"));
            }

            cargaDetalles.close();
        } catch (SQLException ex) {
            System.out.println("Error " + ex);
        }

    }

    @FXML
    private void actualizarDetalleCompra(ActionEvent event) {
    }

    @FXML
    private void enviarCompra(ActionEvent event) {
    }

    @FXML
    private void getExistenciaSeleccionada(MouseEvent event) {
    }

    @FXML
    private void buscarInventario(ActionEvent event) {

        if (!validation.soloLetras(txtBuscarInventario.getText())) {
            return;
        }

        tablaExistencias.getItems().clear();
        String busqueda = txtBuscarInventario.getText();
        ResultSet resultadoExistenciaTabla = gpDao.buscarExistenciaTabla(busqueda);

        try {
            
            while (resultadoExistenciaTabla.next()) {

                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultadoExistenciaTabla.getMetaData().getColumnCount(); i++) {

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

    @FXML
    private void actualizarInventario(ActionEvent event) {
        tablaExistencias.getColumns().clear();
        this.cargarDatosTablaExistencias();
    }

    @FXML
    private void crearCompra(ActionEvent event) {

        int proveedor = cbProveedor.getSelectionModel().getSelectedIndex() + 1;
        int estadoid = cbEstado.getSelectionModel().getSelectedIndex() + 1;

        OrdenesCompra comp = new OrdenesCompra();

        comp.setCodigo_proveedor(proveedor);
        comp.setEstado(estadoid);

        PreparedStatement estado = comDao.nuevaCompra(comp);

        try {
            estado.executeUpdate();

            estado.close();
        } catch (SQLException ex) {
            Logger.getLogger(GestionProductosController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void modificarcompra(ActionEvent event) {
    }

    @FXML
    private void addItem(ActionEvent event) {
    }

    @FXML
    private void modificarItem(ActionEvent event) {
    }

    @FXML
    private void irInicioContenido(ActionEvent event) {
        try {
            // SE CARGA LA SCENE DE GUI JEFE DE BODEGA 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Principal.class.getResource("GuiGerente.fxml"));
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

    @FXML
    private void nuevacompra(ActionEvent event) {
        lbOrdenid.setText("");
        cbProveedor.setValue("Seleccionar");
        cbEstado.setValue("Seleccionar");
        btCrearCompra.setDisable(false);
        btCrearCompra.setStyle("-fx-background-color:#66CCCC");
        btmodificarCompra.setDisable(true);
        btmodificarCompra.setStyle("-fx-background-color:grey");
        lbOrdenid2.setText("");
        txtNombrePro.setText("");
        txtMarca.setText("");
        txtDia.setText("");
        txtMes.setText("");
        txtAno.setText("");
        txtCantidad.setText("");
    }
}
