package modelo.view_controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import modelo.Cliente;
import modelo.Model;
import modelo.enums.Provincia;
import vista.View;
import controlador.Validaciones;

import java.util.Comparator;
import java.util.List;

public class ClienteController {

    // FXML Componentes: Crear
    @FXML private TextField txtNif, txtNombre, txtDireccion, txtCp, txtPoblacion, txtEmail;
    @FXML private ComboBox<Provincia> cbProvincia;

    // FXML Componentes: Buscar
    @FXML private ComboBox<String> cbCampoBuscar;
    @FXML private TextField txtValorBuscar;
    @FXML private VBox areaResultadoBuscar;
    @FXML private Label lblResNombre, lblResDetalles;

    // FXML Componentes: Listado
    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, Long> colId;
    @FXML private TableColumn<Cliente, String> colNif, colNombre, colPoblacion, colEmail;
    @FXML private ComboBox<String> cbOrdenListado;

    // FXML Componentes: Eliminar
    @FXML private ComboBox<String> cbCampoEliminar;
    @FXML private TextField txtValorEliminar;
    @FXML private VBox areaConfirmarEliminar;
    @FXML private Label lblDatosEliminar;

    private Model model = new Model();
    private Cliente clienteSeleccionado;

    @FXML
    public void initialize() {
        // Inicializar ComboBoxes
        cbProvincia.getItems().setAll(Provincia.values());
        cbCampoBuscar.getItems().addAll("NIF", "Nombre", "ID");
        cbCampoBuscar.getSelectionModel().selectFirst();

        cbCampoEliminar.getItems().addAll("NIF", "ID");
        cbCampoEliminar.getSelectionModel().selectFirst();

        cbOrdenListado.getItems().addAll("Nombre", "ID", "Población");
        cbOrdenListado.getSelectionModel().selectFirst();

        // Configurar Columnas de la Tabla
        colId.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getId()));
        colNif.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNif()));
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colPoblacion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPoblacion()));
        colEmail.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));

        actualizarListado();
    }

    // --- ACCIONES: CREAR ---
    @FXML
    public void guardarCliente() {
        try {
            // Validaciones básicas
            if (txtNif.getText().isEmpty() || txtNombre.getText().isEmpty()) {
                throw new Exception("NIF y Nombre son campos obligatorios.");
            }
            if (!Validaciones.validarDNI(txtNif.getText())) {
                throw new Exception("El formato del NIF no es válido.");
            }

            Cliente c = new Cliente();
            c.setNif(txtNif.getText());
            c.setNombre(txtNombre.getText());
            c.setDireccion(txtDireccion.getText());
            c.setCodigoPostal(txtCp.getText());
            c.setPoblacion(txtPoblacion.getText());
            c.setProvincia(cbProvincia.getValue());
            c.setEmail(txtEmail.getText());

            model.getClienteDAO().guardar(c);
            View.mostrarInfo("Éxito", "Cliente guardado correctamente en la base de datos.");
            limpiarCamposCrear();
            actualizarListado();
        } catch (Exception e) {
            View.mostrarError("Error al guardar", e.getMessage());
        }
    }

    @FXML
    public void limpiarCamposCrear() {
        txtNif.clear();
        txtNombre.clear();
        txtDireccion.clear();
        txtCp.clear();
        txtPoblacion.clear();
        txtEmail.clear();
        cbProvincia.getSelectionModel().clearSelection();
    }

    // --- ACCIONES: BUSCAR ---
    @FXML
    public void buscarCliente() {
        String campo = cbCampoBuscar.getValue();
        String valor = txtValorBuscar.getText();

        List<Cliente> todos = model.getClienteDAO().buscarTodos();
        Cliente encontrado = null;

        if (campo.equals("NIF")) {
            encontrado = todos.stream().filter(c -> c.getNif().equalsIgnoreCase(valor)).findFirst().orElse(null);
        } else if (campo.equals("Nombre")) {
            encontrado = todos.stream().filter(c -> c.getNombre().toLowerCase().contains(valor.toLowerCase())).findFirst().orElse(null);
        } else if (campo.equals("ID")) {
            encontrado = model.getClienteDAO().buscarPorId(Long.parseLong(valor));
        }

        if (encontrado != null) {
            areaResultadoBuscar.setVisible(true);
            lblResNombre.setText(encontrado.getNombre());
            lblResDetalles.setText("NIF: " + encontrado.getNif() + "\nEmail: " + encontrado.getEmail() +
                    "\nLocalidad: " + encontrado.getPoblacion() + " (" + encontrado.getProvincia() + ")");
        } else {
            areaResultadoBuscar.setVisible(false);
            View.mostrarError("Búsqueda", "No se encontró ningún cliente con esos datos.");
        }
    }

    // --- ACCIONES: LISTADO ---
    @FXML
    public void actualizarListado() {
        List<Cliente> lista = model.getClienteDAO().buscarTodos();
        String orden = cbOrdenListado.getValue();

        if ("Nombre".equals(orden)) {
            lista.sort(Comparator.comparing(Cliente::getNombre, Comparator.nullsLast(Comparator.naturalOrder())));
        } else if ("Población".equals(orden)) {
            lista.sort(Comparator.comparing(Cliente::getPoblacion, Comparator.nullsLast(Comparator.naturalOrder())));
        } else {
            lista.sort(Comparator.comparing(Cliente::getId));
        }

        tablaClientes.getItems().setAll(lista);
    }

    // --- ACCIONES: ELIMINAR ---
    @FXML
    public void buscarParaEliminar() {
        String campo = cbCampoEliminar.getValue();
        String valor = txtValorEliminar.getText();

        clienteSeleccionado = null;
        if (campo.equals("NIF")) {
            clienteSeleccionado = model.getClienteDAO().buscarTodos().stream()
                    .filter(c -> c.getNif().equalsIgnoreCase(valor)).findFirst().orElse(null);
        } else {
            clienteSeleccionado = model.getClienteDAO().buscarPorId(Long.parseLong(valor));
        }

        if (clienteSeleccionado != null) {
            lblDatosEliminar.setText(clienteSeleccionado.getNombre() + " (NIF: " + clienteSeleccionado.getNif() + ")");
            areaConfirmarEliminar.setVisible(true);
        } else {
            areaConfirmarEliminar.setVisible(false);
            View.mostrarError("Error", "Cliente no encontrado.");
        }
    }

    @FXML
    public void confirmarEliminar() {
        try {
            model.getClienteDAO().eliminar(clienteSeleccionado);
            View.mostrarInfo("Eliminado", "El cliente ha sido borrado correctamente.");
            areaConfirmarEliminar.setVisible(false);
            txtValorEliminar.clear();
            actualizarListado();
        } catch (Exception e) {
            View.mostrarError("Error de integridad", e.getMessage());
        }
    }

    @FXML
    public void cancelarEliminar() {
        areaConfirmarEliminar.setVisible(false);
        clienteSeleccionado = null;
    }
}
