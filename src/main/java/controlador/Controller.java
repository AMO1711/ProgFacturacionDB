package controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import util.ConfigService;
import java.io.IOException;

public class Controller {

    // Este contenedor en el FXML es donde "inyectaremos" las otras pantallas
    @FXML private StackPane areaContenido;

    @FXML private Label lblNombreEmpresa;
    @FXML private Label lblInfoEstado;

    @FXML
    public void initialize() {
        // Al arrancar, ponemos el nombre de la empresa del config.txt en la UI
        lblNombreEmpresa.setText(ConfigService.getInstance().getNombreEmpresa());
        lblInfoEstado.setText("Bienvenido al sistema. Use el menú lateral para navegar.");

        // Opcional: Cargar la vista de facturas por defecto al abrir TODO descomentar
        //abrirSeccionFacturas();
    }

    @FXML
    public void abrirSeccionClientes() {
        cambiarEscena("/vista/ClientesView.fxml", "Gestión de Clientes");
    }

    @FXML
    public void abrirSeccionArticulos() {
        cambiarEscena("/vista/ArticulosView.fxml", "Gestión de Artículos");
    }

    @FXML
    public void abrirSeccionFacturas() {
        cambiarEscena("/vista/FacturasView.fxml", "Facturación y Albaranes");
    }

    /**
     * Método genérico para intercambiar el contenido central de la aplicación
     */
    private void cambiarEscena(String rutaFxml, String mensajeEstado) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFxml));
            Parent nuevaVista = loader.load();

            // Limpiamos lo que hubiera antes y ponemos la nueva vista
            areaContenido.getChildren().setAll(nuevaVista);
            lblInfoEstado.setText(mensajeEstado);

        } catch (IOException e) {
            e.printStackTrace();
            lblInfoEstado.setText("Error al cargar la sección: " + rutaFxml);
        }
    }
}