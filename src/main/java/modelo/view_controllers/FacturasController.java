package modelo.view_controllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import modelo.*;
import modelo.enums.EstadoFactura;
import util.ConfigService;
import vista.View;

import java.time.LocalDate;

public class FacturasController {

    // FXML de la pestaña CREAR
    @FXML private TextField txtBusquedaCliente, txtCodArticulo, txtCantidad;
    @FXML private Label lblNumFactura, lblClienteSeleccionado, lblBaseImponible, lblIvaTotal, lblTotalFactura;
    @FXML private DatePicker dpFecha;
    @FXML private TableView<LineaFactura> tablaLineas;
    @FXML private TableColumn<LineaFactura, String> colCodArt, colNombreArt, colIva;
    @FXML private TableColumn<LineaFactura, Integer> colCant;
    @FXML private TableColumn<LineaFactura, Double> colPrecio, colSubtotal;

    // FXML de la pestaña BUSCAR
    @FXML private TextField txtBuscaNumDetalle;
    @FXML private VBox areaDetalleBusqueda;
    @FXML private Label lblDetalleCabecera;
    @FXML private TableView<LineaFactura> tablaLineasConsulta;
    @FXML private TableColumn<LineaFactura, String> colConsCod, colConsDesc;
    @FXML private TableColumn<LineaFactura, Integer> colConsCant;
    @FXML private TableColumn<LineaFactura, Double> colConsPrecio;

    // FXML de la pestaña LISTADO
    @FXML private TableView<Factura> tablaListado;
    @FXML private TableColumn<Factura, Integer> colListNum;
    @FXML private TableColumn<Factura, String> colListCliente, colListFecha, colListEstado;
    @FXML private TableColumn<Factura, Double> colListTotal;

    // FXML de la pestaña GESTIÓN
    @FXML private TextField txtBuscaNumGestion;
    @FXML private VBox areaAccionGestion;
    @FXML private Label lblGestionInfo, lblGestionEstado;
    @FXML private Button btnEmitir;

    private Model model = new Model();
    private Cliente clienteActual;
    private Factura facturaEnGestion;
    private ObservableList<LineaFactura> lineasNuevaFactura = FXCollections.observableArrayList();

    private double tBase, tIva, tTotal;

    @FXML
    public void initialize() {
        dpFecha.setValue(LocalDate.now());
        configurarTablas();
        actualizarProximoNumero();
    }

    private void configurarTablas() {
        colCodArt.setCellValueFactory(l -> new SimpleStringProperty(l.getValue().getArticulo().getCodigo()));
        colNombreArt.setCellValueFactory(l -> new SimpleStringProperty(l.getValue().getArticulo().getNombre()));
        colCant.setCellValueFactory(l -> new SimpleIntegerProperty(l.getValue().getCantidad()).asObject());
        colPrecio.setCellValueFactory(l -> new SimpleDoubleProperty(l.getValue().getPrecioVentaUnitario()).asObject());
        colIva.setCellValueFactory(l -> new SimpleStringProperty(l.getValue().getIvaAplicado().toString()));
        colSubtotal.setCellValueFactory(l -> new SimpleDoubleProperty(l.getValue().getCantidad() * l.getValue().getPrecioVentaUnitario()).asObject());

        tablaLineas.setItems(lineasNuevaFactura);

        // Tabla consulta
        colConsCod.setCellValueFactory(l -> new SimpleStringProperty(l.getValue().getArticulo().getCodigo()));
        colConsDesc.setCellValueFactory(l -> new SimpleStringProperty(l.getValue().getArticulo().getNombre()));
        colConsCant.setCellValueFactory(l -> new SimpleIntegerProperty(l.getValue().getCantidad()).asObject());
        colConsPrecio.setCellValueFactory(l -> new SimpleDoubleProperty(l.getValue().getPrecioVentaUnitario()).asObject());

        // Tabla listado
        colListNum.setCellValueFactory(f -> new SimpleIntegerProperty(f.getValue().getNumeroFactura()).asObject());
        colListCliente.setCellValueFactory(f -> new SimpleStringProperty(f.getValue().getCliente().getNombre()));
        colListFecha.setCellValueFactory(f -> new SimpleStringProperty(f.getValue().getFecha().toLocalDate().toString()));
        colListTotal.setCellValueFactory(f -> new SimpleDoubleProperty(f.getValue().getTotalFactura()).asObject());
        colListEstado.setCellValueFactory(f -> new SimpleStringProperty(f.getValue().getEstado().toString()));
    }

    private void actualizarProximoNumero() {
        Integer ultimo = model.getFacturaDAO().obtenerUltimoNumeroFactura();

        if (ultimo == null || ultimo == 0) {
            // Accedemos directamente al valor que cargó el ConfigService
            int inicial = ConfigService.getInstance().getInicioFactura();
            lblNumFactura.setText(String.valueOf(inicial));
            System.out.println("Base de datos vacía. Cargando número inicial desde config: " + inicial);
        } else {
            lblNumFactura.setText(String.valueOf(ultimo + 1));
        }
    }

    @FXML
    public void buscarClienteFactura() {
        clienteActual = model.getClienteDAO().buscarPorNif(txtBusquedaCliente.getText());
        if (clienteActual != null) {
            lblClienteSeleccionado.setText(clienteActual.getNombre());
        } else {
            View.mostrarError("Error", "Cliente no encontrado");
        }
    }

    @FXML
    public void aniadirLinea() {
        try {
            String codigo = txtCodArticulo.getText();
            Articulo art = model.getArticuloDAO().buscarTodos().stream()
                    .filter(a -> a.getCodigo().equalsIgnoreCase(codigo))
                    .findFirst().orElse(null);

            if (art != null) {
                LineaFactura lf = new LineaFactura();
                lf.setArticulo(art);
                lf.setCantidad(Integer.parseInt(txtCantidad.getText()));
                lf.setPrecioVentaUnitario(art.getPrecioVenta());
                lf.setIvaAplicado(art.getIvaPercent());

                lineasNuevaFactura.add(lf);
                recalcularTotales();
                txtCodArticulo.clear();
            }
        } catch (Exception e) {
            View.mostrarError("Error", "Cantidad inválida");
        }
    }

    private void recalcularTotales() {
        tBase = 0; tIva = 0;
        for (LineaFactura lf : lineasNuevaFactura) {
            double sub = lf.getCantidad() * lf.getPrecioVentaUnitario();
            tBase += sub;
            tIva += sub * (lf.getIvaAplicado().getValor() / 100.0);
        }
        tTotal = tBase + tIva;
        lblBaseImponible.setText(String.format("%.2f €", tBase));
        lblIvaTotal.setText(String.format("%.2f €", tIva));
        lblTotalFactura.setText(String.format("%.2f €", tTotal));
    }

    @FXML
    public void guardarFactura() {
        if (clienteActual == null || lineasNuevaFactura.isEmpty()) return;

        try {
            Factura f = new Factura();
            f.setNumeroFactura(Integer.parseInt(lblNumFactura.getText()));
            f.setFecha(dpFecha.getValue().atStartOfDay());
            f.setCliente(clienteActual);
            f.setEstado(EstadoFactura.ALBARAN);

            f.setTotalBase(tBase);
            f.setTotalIva(tIva);
            f.setTotalFactura(tTotal);

            for (LineaFactura lf : lineasNuevaFactura) {
                lf.setFactura(f);
                f.getLineas().add(lf);
            }

            model.getFacturaDAO().guardar(f);
            View.mostrarInfo("Éxito", "Documento guardado.");
            limpiarFormulario();
        } catch (Exception e) {
            View.mostrarError("Error DB", e.getMessage());
        }
    }

    private void limpiarFormulario() {
        lineasNuevaFactura.clear();
        clienteActual = null;
        lblClienteSeleccionado.setText("Sin selección");
        recalcularTotales();
        actualizarProximoNumero();
    }

    // --- PESTAÑA 2: BUSCAR DETALLE ---
    @FXML
    public void buscarFacturaDetalle() {
        String textoNum = txtBuscaNumDetalle.getText().trim();

        if (textoNum.isEmpty()) {
            View.mostrarError("Campo vacío", "Introduce un número de factura para buscar.");
            return;
        }

        try {
            int num = Integer.parseInt(textoNum);
            Factura f = model.getFacturaDAO().buscarPorNumero(num);

            if (f != null) {
                areaDetalleBusqueda.setVisible(true);
                lblDetalleCabecera.setText("Factura Nº " + f.getNumeroFactura() + " | Cliente: " + f.getCliente().getNombre());

                tablaLineasConsulta.getItems().setAll(f.getLineas());
            } else {
                areaDetalleBusqueda.setVisible(false);
                View.mostrarError("No encontrado", "No se ha encontrado ninguna factura con el número: " + num);
            }
        } catch (NumberFormatException e) {
            View.mostrarError("Error de formato", "El número de factura debe ser un valor numérico.");
        }
    }

    // --- PESTAÑA 3: LISTADO GENERAL ---
    @FXML
    public void cargarListado() {
        tablaListado.getItems().setAll(model.getFacturaDAO().buscarTodos());
    }

    // --- PESTAÑA 4: GESTION DE ALBARANES ---
    @FXML
    public void buscarFacturaGestion() {
        String textoNum = txtBuscaNumGestion.getText().trim();

        if (textoNum.isEmpty()) {
            View.mostrarError("Campo vacío", "Introduce el número de albarán.");
            return;
        }

        try {
            int num = Integer.parseInt(textoNum);
            facturaEnGestion = model.getFacturaDAO().buscarPorNumero(num);

            if (facturaEnGestion != null) {
                areaAccionGestion.setVisible(true);
                lblGestionInfo.setText("Cliente: " + facturaEnGestion.getCliente().getNombre() +
                        "\nImporte Total: " + String.format("%.2f €", facturaEnGestion.getTotalFactura()));
                lblGestionEstado.setText("Estado Actual: " + facturaEnGestion.getEstado());

                // Solo permitimos emitir si el estado actual es ALBARAN
                if (facturaEnGestion.getEstado() == EstadoFactura.ALBARAN) {
                    btnEmitir.setDisable(false);
                    btnEmitir.setText("PASAR A FACTURA EMITIDA");
                } else {
                    btnEmitir.setDisable(true);
                    btnEmitir.setText("YA ES FACTURA / EMITIDA");
                }
            } else {
                areaAccionGestion.setVisible(false);
                View.mostrarError("No encontrado", "No existe el documento número: " + num);
            }
        } catch (NumberFormatException e) {
            View.mostrarError("Error", "Introduce un número válido.");
        }
    }

    @FXML
    public void emitirFacturaActual() {
        if (facturaEnGestion != null) {
            try {
                facturaEnGestion.setEstado(EstadoFactura.FACTURA_ENVIADA);

                model.getFacturaDAO().guardar(facturaEnGestion);

                lblGestionEstado.setText("Estado Actual: FACTURA_ENVIADA");
                btnEmitir.setDisable(true);

                View.mostrarInfo("Éxito", "El albarán ha sido convertido en factura correctamente.");

                cargarListado();
            } catch (Exception e) {
                View.mostrarError("Error al actualizar", "No se pudo cambiar el estado: " + e.getMessage());
            }
        }
    }
    @FXML public void quitarLinea() {
        LineaFactura sel = tablaLineas.getSelectionModel().getSelectedItem();
        if (sel != null) { lineasNuevaFactura.remove(sel); recalcularTotales(); }
    }
}