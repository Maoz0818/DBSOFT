package screensframework;


import DAO.ProveedorDao;
import DAO.SucursalesDao;
import Logica.Proveedores;
import Logica.Sucursal;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class GestionUsuariosController implements Initializable {

    @FXML    private TableView tablaSucursales;
    @FXML    private TableColumn col;
    @FXML    private TextField txtBuscarSucursal;
    @FXML    private TextField txtNombreSucursal;
    @FXML    private TextField txtPaisSucursal;
    @FXML    private TextField txtCiudadSucursal;
    @FXML    private TextField txtDireccionSucursal;
    @FXML    private TextField txtTelefonoSucursal;
    @FXML    private TextField txtEmailSucursal;
    @FXML    private Button btAddSucursal;
    @FXML    private Button btModificarSucursal;
    @FXML    private TableView tablaProveedores;
    @FXML    private TableColumn col2;
    @FXML    private TextField txtBuscarProveedor;
    @FXML    private TextField txtRutProveedor;
    @FXML    private TextField txtNombreProveedor;
    @FXML    private TextField txtPaisProveedor;
    @FXML    private TextField txtCiudadProveedor;
    @FXML    private TextField txtTelefonoProveedor;
    @FXML    private TextField txtEmailProveedor;
    @FXML    private Button btAddProveedor;
    @FXML    private Button btModificarProveedor;

    Stage stage;

    private Validaciones validation = new Validaciones();
    ProveedorDao provDao = new ProveedorDao();
    SucursalesDao suDao = new SucursalesDao();

    ObservableList<ObservableList> proveedor;
    ObservableList<ObservableList> sucursal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        btModificarProveedor.setDisable(true);
        btModificarProveedor.setStyle("-fx-background-color:grey");
        btAddProveedor.setDisable(true);
        btAddProveedor.setStyle("-fx-background-color:grey");
        btModificarSucursal.setDisable(true);
        btModificarSucursal.setStyle("-fx-background-color:grey");
        btAddSucursal.setDisable(true);
        btAddSucursal.setStyle("-fx-background-color:grey");
    }

    

    @FXML
    private void irInicioContenido(ActionEvent event) {

        try {
            // SE CARGA LA SCENE DE GUI GERENTE
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Principal.class.getResource("GUIGerente.fxml"));
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

    // METODO ENCARGADO DE CARGAR LOS DATOS A LA TABLA PROVEEDORES
    public void cargarDatosTablaProveedores() {

        proveedor = FXCollections.observableArrayList();
        ResultSet resultadoDatosTablaProveedores = provDao.datosParaTablaProveedores();

        try {
            //TITULOS DE LAS COLUMNAS
            String[] titulos = {"Codigo","RUT", "Proveedor", "País", "Ciudad", "Teléfono", "Email"};

            //AGREGAMOS LOS DATOS A LA TABLA DINAMICAMENTE
            for (int i = 0; i < resultadoDatosTablaProveedores.getMetaData().getColumnCount(); i++) {
                final int j = i;
                col2 = new TableColumn(titulos[i]);
                col2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> parametro) {
                        return new SimpleStringProperty((String) parametro.getValue().get(j));
                    }
                });
                tablaProveedores.getColumns().addAll(col2);
                // ASIGNAMOS UN TAMAÑO A LAS COLUMNAS
                col2.setMinWidth(100);
                System.out.println("Column [" + i + "] ");
                // CENTRAMOS LOS DATOS EN LA TABLA
                col2.setCellFactory(new Callback<TableColumn<String, String>, TableCell<String, String>>() {
                    @Override
                    public TableCell<String, String> call(TableColumn<String, String> p) {
                        TableCell cell = new TableCell() {
                            @Override
                            protected void updateItem(Object t, boolean bln) {
                                if (t != null) {
                                    super.updateItem(t, bln);
                                    System.out.println(t);
                                    setText(t.toString());
                                    setAlignment(Pos.CENTER_LEFT);
                                }
                            }
                        };
                        return cell;
                    }
                });
            }

            //CARGAMOS DE LA BASE DE DATOS
            while (resultadoDatosTablaProveedores.next()) {
                //ITERACION DE FILA}
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultadoDatosTablaProveedores.getMetaData().getColumnCount(); i++) {
                    //ITERACION DE COLUMNA
                    row.add(resultadoDatosTablaProveedores.getString(i));
                }
                System.out.println("Row [i] added " + row);
                proveedor.addAll(row);
            }
            //FINALMENTE AGREGAMOS A LA TABLA PROVEEDORES
            tablaProveedores.setItems(proveedor);
            resultadoDatosTablaProveedores.close();
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }

    @FXML
    private void buscarProveedor(ActionEvent event) {

        if (!validation.soloLetras(txtBuscarProveedor.getText())) {
            return;
        }

        tablaProveedores.getItems().clear();
        String busqueda = txtBuscarProveedor.getText();
        ResultSet resultadoProveedorTabla = provDao.buscarProveedoresTabla(busqueda);

        try {

            while (resultadoProveedorTabla.next()) {

                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultadoProveedorTabla.getMetaData().getColumnCount(); i++) {

                    row.add(resultadoProveedorTabla.getString(i));
                }
                proveedor.addAll(row);
            }
            tablaProveedores.setItems(proveedor);
            resultadoProveedorTabla.close();

        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }

    }

    @FXML
    private void addProveedor(ActionEvent event) {

        if (!validation.validarVacios(txtRutProveedor.getText(), "RUT")) {
            return;
        }
        if (!validation.validarVacios(txtNombreProveedor.getText(), "nombre")) {
            return;
        }
        if (!validation.validarVacios(txtPaisProveedor.getText(), "pais")) {
            return;
        }
        if (!validation.validarVacios(txtCiudadProveedor.getText(), "ciudad")) {
            return;
        }
        if (!validation.validarVacios(txtTelefonoProveedor.getText(), "telefono")) {
            return;
        }
        if (!validation.validarVacios(txtEmailProveedor.getText(), "email")) {
            return;
        }
        if (!validation.soloNumeros(txtTelefonoProveedor.getText(), "telefono")) {
            return;
        }
        if (!validation.soloNumeros(txtRutProveedor.getText(), "RUT")) {
            return;
        }

        int Rut = Integer.parseInt(txtRutProveedor.getText());
        String nombreProveedor = txtNombreProveedor.getText();
        String pais = txtPaisProveedor.getText();
        String ciudad = txtCiudadProveedor.getText();
        int telefono = Integer.parseInt(txtTelefonoProveedor.getText());
        String email = txtEmailProveedor.getText();

        Proveedores prov = new Proveedores();

        prov.setRut(Rut);
        prov.setNombre(nombreProveedor);
        prov.setPais(pais);
        prov.setCiudad(ciudad);
        prov.setTelefono(telefono);
        prov.seteMail(email);

        PreparedStatement estado = provDao.nuevoProveedor(prov);
        try {
            estado.executeUpdate();

            estado.close();
        } catch (SQLException ex) {
            Logger.getLogger(GestionUsuariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void modificarProveedor(ActionEvent event) {

        if (!validation.validarVacios(txtRutProveedor.getText(), "rut")) {
            return;
        }
        if (!validation.validarVacios(txtNombreProveedor.getText(), "nombre")) {
            return;
        }
        if (!validation.validarVacios(txtPaisProveedor.getText(), "pais")) {
            return;
        }
        if (!validation.validarVacios(txtCiudadProveedor.getText(), "ciudad")) {
            return;
        }
        if (!validation.validarVacios(txtTelefonoProveedor.getText(), "telefono")) {
            return;
        }
        if (!validation.validarVacios(txtEmailProveedor.getText(), "email")) {
            return;
        }
        if (!validation.soloNumeros(txtTelefonoProveedor.getText(), "telefono")) {
            return;
        }
        if (!validation.soloNumeros(txtRutProveedor.getText(), "rut")) {
            return;
        }

        int Rut = Integer.parseInt(txtRutProveedor.getText());
        String nombreProveedor = txtNombreProveedor.getText();
        String pais = txtPaisProveedor.getText();
        String ciudad = txtCiudadProveedor.getText();
        int telefono = Integer.parseInt(txtTelefonoProveedor.getText());
        String email = txtEmailProveedor.getText();
        int codigo = provDao.buscarProveedor(nombreProveedor);

        Proveedores prov = new Proveedores();

        prov.setRut(Rut);
        prov.setNombre(nombreProveedor);
        prov.setPais(pais);
        prov.setCiudad(ciudad);
        prov.setTelefono(telefono);
        prov.seteMail(email);
        prov.setCodigoProveedor(codigo);

        PreparedStatement estado = provDao.modificarProveedor(prov);

        try {

            int n = estado.executeUpdate();

            if (n > 0) {
                tablaProveedores.getColumns().clear();
                tablaProveedores.getItems().clear();
                cargarDatosTablaProveedores();
            }

            estado.close();
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }

    }
    
    // ACTUALIZACION DE DATOS DE LA TABLA SUCURSALES
    @FXML
    private void actualizarSucursales(ActionEvent event) {
        tablaSucursales.getColumns().clear();
        this.cargarDatosTablaSucursales();
    }
    
    // ACTUALIZACION DE DATOS DE LA TABLA PROVEEDORES
    @FXML
    private void actualizarProveedores(ActionEvent event) {
        tablaProveedores.getColumns().clear();
        this.cargarDatosTablaProveedores();
    }

    public void cargarDatosTablaSucursales() {

        sucursal = FXCollections.observableArrayList();
        ResultSet resultadoDatosTablaSucursales = suDao.datosParaTablaSucursales();

        try {
            //TITULOS DE LAS COLUMNAS
            String[] titulos = {"Cod Sucursal","Sucursal", "País", "Ciudad", "Dirección", "Teléfono", "Email"};

            //AGREGAMOS LOS DATOS A LA TABLA DINAMICAMENTE
            for (int i = 0; i < resultadoDatosTablaSucursales.getMetaData().getColumnCount(); i++) {
                final int j = i;
                col = new TableColumn(titulos[i]);
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> parametro) {
                        return new SimpleStringProperty((String) parametro.getValue().get(j));
                    }
                });
                tablaSucursales.getColumns().addAll(col);
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
                                    setAlignment(Pos.CENTER_LEFT);
                                }
                            }
                        };
                        return cell;
                    }
                });
            }

            //CARGAMOS DE LA BASE DE DATOS
            while (resultadoDatosTablaSucursales.next()) {
                //ITERACION DE FILA}
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultadoDatosTablaSucursales.getMetaData().getColumnCount(); i++) {
                    //ITERACION DE COLUMNA
                    row.add(resultadoDatosTablaSucursales.getString(i));
                }
                System.out.println("Row [i] added " + row);
                sucursal.addAll(row);
            }
            //FINALMENTE AGREGAMOS A LA TABLA PROVEEDORES
            tablaSucursales.setItems(sucursal);
            resultadoDatosTablaSucursales.close();
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }

    @FXML
    private void buscarSucursal(ActionEvent event) {
        if (!validation.soloLetras(txtBuscarSucursal.getText())) {
            return;
        }

        tablaSucursales.getItems().clear();
        String busqueda = txtBuscarSucursal.getText();
        ResultSet resultadoSucursalTabla = suDao.buscarSucursalesTabla(busqueda);

        try {

            while (resultadoSucursalTabla.next()) {

                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultadoSucursalTabla.getMetaData().getColumnCount(); i++) {

                    row.add(resultadoSucursalTabla.getString(i));
                }
                sucursal.addAll(row);
            }
            tablaSucursales.setItems(sucursal);
            resultadoSucursalTabla.close();

        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }

    }

    @FXML
    private void addSucursal(ActionEvent event) {

        if (!validation.validarVacios(txtNombreSucursal.getText(), "nombre")) {
            return;
        }
        if (!validation.validarVacios(txtPaisSucursal.getText(), "pais")) {
            return;
        }
        if (!validation.validarVacios(txtCiudadSucursal.getText(), "ciudad")) {
            return;
        }
        if (!validation.validarVacios(txtDireccionSucursal.getText(), "direccion")) {
            return;
        }
        if (!validation.validarVacios(txtTelefonoSucursal.getText(), "telefono")) {
            return;
        }
        if (!validation.validarVacios(txtEmailSucursal.getText(), "email")) {
            return;
        }
        if (!validation.soloNumeros(txtTelefonoSucursal.getText(), "telefono")) {
            return;
        }

        String nombreSucursal = txtNombreSucursal.getText();
        String pais = txtPaisSucursal.getText();
        String ciudad = txtCiudadSucursal.getText();
        String direccion = txtDireccionSucursal.getText();
        int telefono = Integer.parseInt(txtTelefonoSucursal.getText());
        String email = txtEmailSucursal.getText();

        Sucursal su = new Sucursal();

        su.setNombre(nombreSucursal);
        su.setPais(pais);
        su.setCiudad(ciudad);
        su.setDireccion(direccion);
        su.setTelefono(telefono);
        su.seteMail(email);

        PreparedStatement estado = suDao.nuevaSucursal(su);
        try {
            estado.executeUpdate();

            estado.close();
        } catch (SQLException ex) {
            Logger.getLogger(GestionUsuariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void modificarSucursal(ActionEvent event) {

        if (!validation.validarVacios(txtNombreSucursal.getText(), "nombre")) {
            return;
        }
        if (!validation.validarVacios(txtPaisSucursal.getText(), "pais")) {
            return;
        }
        if (!validation.validarVacios(txtCiudadSucursal.getText(), "ciudad")) {
            return;
        }
        if (!validation.validarVacios(txtDireccionSucursal.getText(), "direccion")) {
            return;
        }
        if (!validation.validarVacios(txtTelefonoSucursal.getText(), "telefono")) {
            return;
        }
        if (!validation.validarVacios(txtEmailSucursal.getText(), "email")) {
            return;
        }
        if (!validation.soloNumeros(txtTelefonoSucursal.getText(), "telefono")) {
            return;
        }
        if (!validation.validarCorreo(txtEmailSucursal.getText())) {
            return;
        }

        String nombreSucursal = txtNombreSucursal.getText();
        String pais = txtPaisSucursal.getText();
        String ciudad = txtCiudadSucursal.getText();
        String direccion = txtDireccionSucursal.getText();
        int telefono = Integer.parseInt(txtTelefonoSucursal.getText());
        String email = txtEmailSucursal.getText();
        int codigo = suDao.buscarSucursal(nombreSucursal);

        Sucursal su = new Sucursal();

        su.setSucursalid(codigo);
        su.setNombre(nombreSucursal);
        su.setPais(pais);
        su.setCiudad(ciudad);
        su.setDireccion(direccion);
        su.setTelefono(telefono);
        su.seteMail(email);

        PreparedStatement estado = suDao.modificarSucursal(su);

        try {

            int n = estado.executeUpdate();

            if (n > 0) {
                tablaSucursales.getColumns().clear();
                tablaSucursales.getItems().clear();
                cargarDatosTablaSucursales();
            }

            estado.close();
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }

    }

    public void cargarProveedoresText(String valor) {

        ResultSet cargaProveedores = provDao.cargaProveedores(valor);

        try {

            while (cargaProveedores.next()) {

                txtRutProveedor.setText(cargaProveedores.getString("rut"));
                txtNombreProveedor.setText(cargaProveedores.getString("nombre"));
                txtPaisProveedor.setText(cargaProveedores.getString("pais"));
                txtCiudadProveedor.setText(cargaProveedores.getString("ciudad"));
                txtTelefonoProveedor.setText(cargaProveedores.getString("telefono"));
                txtEmailProveedor.setText(cargaProveedores.getString("e_mail"));

            }
            cargaProveedores.close();
        } catch (SQLException ex) {
            System.out.println("Error " + ex);
        }

    }

    public void cargarSucursalesText(String valor) {

        ResultSet cargaSucursales = suDao.cargaSucursales(valor);

        try {

            while (cargaSucursales.next()) {

                txtNombreSucursal.setText(cargaSucursales.getString("nombre"));
                txtPaisSucursal.setText(cargaSucursales.getString("pais"));
                txtCiudadSucursal.setText(cargaSucursales.getString("ciudad"));
                txtDireccionSucursal.setText(cargaSucursales.getString("direccion"));
                txtTelefonoSucursal.setText(cargaSucursales.getString("telefono"));
                txtEmailSucursal.setText(cargaSucursales.getString("e_mail"));
            }
            cargaSucursales.close();
        } catch (SQLException ex) {
            System.out.println("Error " + ex);
        }

    }

    @FXML
    private void getSucursalSeleccionada(MouseEvent event) {

        tablaSucursales.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (tablaSucursales != null) {

                    btAddSucursal.setDisable(true);
                    btModificarSucursal.setDisable(false);
                    btAddSucursal.setStyle("-fx-background-color:grey");
                    btModificarSucursal.setStyle("-fx-background-color:#66CCCC");

                    String valor = tablaSucursales.getSelectionModel().getSelectedItems().get(0).toString();

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
                        cargarSucursalesText(cincoDigitos);
                    } else {
                        if (m4.find()) {
                            cargarSucursalesText(cuatroDigitos);
                        } else {
                            if (m3.find()) {
                                cargarSucursalesText(tresDigitos);
                            } else {
                                if (m2.find()) {
                                    cargarSucursalesText(dosDigitos);
                                } else {
                                    cargarSucursalesText(unDigitos);
                                }
                             }
                        }
                    }
                }
            }
        });
    }

    @FXML
    private void getProveedorSeleccionado(MouseEvent event) {

        tablaProveedores.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (tablaProveedores != null) {

                    btAddProveedor.setDisable(true);
                    btModificarProveedor.setDisable(false);
                    btAddProveedor.setStyle("-fx-background-color:grey");
                    btModificarProveedor.setStyle("-fx-background-color:#66CCCC");

                    String valor = tablaProveedores.getSelectionModel().getSelectedItems().get(0).toString();

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
                        cargarProveedoresText(cincoDigitos);
                    } else {
                        if (m4.find()) {
                            cargarProveedoresText(cuatroDigitos);
                        } else {
                            if (m3.find()) {
                                cargarProveedoresText(tresDigitos);
                            } else {
                                if (m2.find()) {
                                    cargarProveedoresText(dosDigitos);
                                } else {
                                    cargarProveedoresText(unDigitos);
                                }
                             }
                        }
                    }
                }
            }
        });
    }

    @FXML
    private void nuevaSucursal(ActionEvent event) {

        btAddSucursal.setDisable(false);
        btModificarSucursal.setDisable(true);
        btAddSucursal.setStyle("-fx-background-color:#66CCCC");
        btModificarSucursal.setStyle("-fx-background-color:grey");
        txtNombreSucursal.setText("");
        txtPaisSucursal.setText("");
        txtCiudadSucursal.setText("");
        txtDireccionSucursal.setText("");
        txtTelefonoSucursal.setText("");
        txtEmailSucursal.setText("");
    }

    @FXML
    private void nuevoProveedor(ActionEvent event) {

        btAddProveedor.setDisable(false);
        btModificarProveedor.setDisable(true);
        btAddProveedor.setStyle("-fx-background-color:#66CCCC");
        btModificarProveedor.setStyle("-fx-background-color:grey");
        txtRutProveedor.setText("");
        txtNombreProveedor.setText("");
        txtPaisProveedor.setText("");
        txtCiudadProveedor.setText("");
        txtTelefonoProveedor.setText("");
        txtEmailProveedor.setText("");
    }

}
