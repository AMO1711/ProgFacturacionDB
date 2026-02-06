package controlador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.ConfigService;
import util.HibernateUtil;

public class ProgramaFacturacion extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // 1. Inicializamos la configuración (lee el config.txt de la raíz)
            ConfigService config = ConfigService.getInstance();

            // 2. Cargamos la vista principal
                // Nota: El archivo FXML debería estar en src/main/resources/vista/MainView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/MainView.fxml"));
            Parent root = loader.load();

            // 3. Configuramos la ventana (Stage)
            Scene scene = new Scene(root);
            primaryStage.setTitle("Sistema de Facturación - " + config.getNombreEmpresa());
            primaryStage.setScene(scene);

            // Maximizado por defecto para trabajar mejor
            primaryStage.setMaximized(true);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error crítico al iniciar la aplicación.");
        }
    }

    @Override
    public void stop() {
        try {
            // Solo intentamos cerrar si la sesión realmente se llegó a crear
            util.HibernateUtil.shutdown();
        } catch (Exception e) {
            System.out.println("No se pudo cerrar Hibernate porque no se llegó a iniciar.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}