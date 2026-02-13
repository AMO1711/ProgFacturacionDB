package modelo.view_controllers;

import controlador.Validaciones;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import modelo.Articulo;
import modelo.Model;
import modelo.enums.TipoIVA;
import vista.View;

import java.util.Comparator;
import java.util.List;

public class ArticulosController {

    // --- ELEMENTOS FXML: CREAR ---
    @FXML private TextField txtCodigo, txtNombre, txtDescripcion, txtPrecioVenta, txtStockActual;
    @FXML private ComboBox<TipoIVA> cbIvaPercent;

    // --- ELEMENTOS FXML: BUSCAR ---
    @FXML private ComboBox<String> cbCampoBuscar;
    @FXML private TextField txtValorBuscar;
    @FXML private VBox areaResultadoBuscar;
    @FXML private Label lblResNombre, lblResDetalles;

    // --- ELEMENTOS FXML: LISTADO ---
    @FXML private TableView<Articulo> tablaArticulos;
    @FXML private TableColumn<Articulo, String> colCodigo, colNombre;
    @FXML private TableColumn<Articulo, Double> colPrecioVenta;
    @FXML private TableColumn<Articulo, Integer> colStockActual;
    @FXML private TableColumn<Articulo, String> colIva;
    @FXML private ComboBox<String> cbOrdenListado;

    // --- ELEMENTOS FXML: ELIMINAR ---
    @FXML private ComboBox<String> cbCampoEliminar;
    @FXML private TextField txtValorEliminar;
    @FXML private VBox areaConfirmarEliminar;
    @FXML private Label lblDatosEliminar;

    private Model model = new Model();
    private Articulo articuloSeleccionado;

    @FXML
    public void initialize() {
        // Llenar ComboBoxes con los nombres de atributos exactos
        cbIvaPercent.getItems().setAll(TipoIVA.values());

        cbCampoBuscar.getItems().addAll("Código", "Nombre");
        cbCampoBuscar.getSelectionModel().selectFirst();

        cbCampoEliminar.getItems().addAll("Código");
        cbCampoEliminar.getSelectionModel().selectFirst();

        cbOrdenListado.getItems().addAll("Nombre", "Stock Actual", "Precio Venta");
        cbOrdenListado.getSelectionModel().selectFirst();

        // Configurar Columnas de la Tabla (usando los nuevos nombres de la entidad)
        colCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colPrecioVenta.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getPrecioVenta()));
        colStockActual.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getStockActual()));
        colIva.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getIvaPercent() != null ? c.getValue().getIvaPercent().toString() : "N/A"
        ));

        actualizarListado();
    }

    // --- GESTIÓN: CREAR ---
    @FXML
    public void guardarArticulo() {
        try {
            // Validaciones de consistencia
            if (txtCodigo.getText().isEmpty() || txtNombre.getText().isEmpty()) {
                throw new Exception("El Código y el Nombre son obligatorios.");
            }

            Articulo a = new Articulo();
            a.setCodigo(txtCodigo.getText());
            a.setNombre(txtNombre.getText());
            a.setDescripcion(txtDescripcion.getText());

            // Conversión segura de tipos numéricos
            try {
                a.setPrecioVenta(Double.parseDouble(txtPrecioVenta.getText().replace(",", ".")));
                a.setStockActual(Integer.parseInt(txtStockActual.getText()));
            } catch (NumberFormatException e) {
                throw new Exception("Precio Venta y Stock Actual deben ser números válidos.");
            }

            a.setIvaPercent(cbIvaPercent.getValue());
            a.setActivo(true); // Por defecto al crear

            model.getArticuloDAO().guardar(a);
            View.mostrarInfo("Éxito", "Artículo '" + a.getNombre() + "' guardado correctamente.");
            limpiarCamposCrear();
            actualizarListado();

        } catch (Exception e) {
            View.mostrarError("Error al guardar", e.getMessage());
        }
    }

    @FXML
    public void limpiarCamposCrear() {
        txtCodigo.clear();
        txtNombre.clear();
        txtDescripcion.clear();
        txtPrecioVenta.clear();
        txtStockActual.clear();
        cbIvaPercent.getSelectionModel().clearSelection();
    }

    // --- GESTIÓN: BUSCAR ---
    @FXML
    public void buscarArticulo() {
        String campo = cbCampoBuscar.getValue();
        String valor = txtValorBuscar.getText().toLowerCase();

        List<Articulo> todos = model.getArticuloDAO().buscarTodos();
        Articulo encontrado = todos.stream().filter(a -> {
            if ("Código".equals(campo)) return a.getCodigo().equalsIgnoreCase(valor);
            return a.getNombre().toLowerCase().contains(valor);
        }).findFirst().orElse(null);

        if (encontrado != null) {
            areaResultadoBuscar.setVisible(true);
            lblResNombre.setText(encontrado.getNombre());
            lblResDetalles.setText("Código: " + encontrado.getCodigo() +
                    "\nPrecio Venta: " + encontrado.getPrecioVenta() + "€" +
                    "\nStock Actual: " + encontrado.getStockActual() +
                    "\nIVA: " + encontrado.getIvaPercent());
        } else {
            areaResultadoBuscar.setVisible(false);
            View.mostrarError("Búsqueda", "No se encontró ningún artículo.");
        }
    }

    // --- GESTIÓN: LISTADO ---
    @FXML
    public void actualizarListado() {
        List<Articulo> lista = model.getArticuloDAO().buscarTodos();
        String orden = cbOrdenListado.getValue();

        if ("Nombre".equals(orden)) {
            lista.sort(Comparator.comparing(Articulo::getNombre, Comparator.nullsLast(Comparator.naturalOrder())));
        } else if ("Stock Actual".equals(orden)) {
            lista.sort(Comparator.comparing(Articulo::getStockActual, Comparator.nullsLast(Comparator.naturalOrder())));
        } else if ("Precio Venta".equals(orden)) {
            lista.sort(Comparator.comparing(Articulo::getPrecioVenta, Comparator.nullsLast(Comparator.naturalOrder())));
        }

        tablaArticulos.getItems().setAll(lista);
    }

    // --- GESTIÓN: ELIMINAR ---
    @FXML
    public void buscarParaEliminar() {
        String valor = txtValorEliminar.getText();
        articuloSeleccionado = model.getArticuloDAO().buscarTodos().stream()
                .filter(a -> a.getCodigo().equalsIgnoreCase(valor)).findFirst().orElse(null);

        if (articuloSeleccionado != null) {
            lblDatosEliminar.setText(articuloSeleccionado.getNombre() + " (Cod: " + articuloSeleccionado.getCodigo() + ")");
            areaConfirmarEliminar.setVisible(true);
        } else {
            areaConfirmarEliminar.setVisible(false);
            View.mostrarError("Eliminar", "Artículo no encontrado.");
        }
    }

    @FXML
    public void confirmarEliminar() {
        try {
            model.getArticuloDAO().eliminar(articuloSeleccionado);
            View.mostrarInfo("Eliminado", "El artículo se ha borrado con éxito.");
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
        articuloSeleccionado = null;
    }
}