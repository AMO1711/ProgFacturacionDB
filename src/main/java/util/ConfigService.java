package util;

import java.io.*;
import java.util.Properties;

public class ConfigService {
    private static ConfigService instance;
    private Properties props = new Properties();

    private ConfigService() {
        cargarDesdeArchivo();
    }

    public static ConfigService getInstance() {
        if (instance == null) {
            instance = new ConfigService();
        }
        return instance;
    }

    private void cargarDesdeArchivo() {
        File f = new File("config.txt");
        if (!f.exists()) {
            crearArchivoPorDefecto();
        }
        try (InputStream input = new FileInputStream(f)) {
            props.load(input);
        } catch (IOException e) {
            System.err.println("Error cargando config.txt: " + e.getMessage());
        }
    }

    private void crearArchivoPorDefecto() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("config.txt"))) {
            writer.println("empresa=Mi Facturacion S.L.");
            writer.println("direccion=Calle Mayor 1");
            writer.println("telefono=912345678");
            writer.println("inicio_factura=12300");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return props.getProperty(key);
    }

    public String getNombreEmpresa() { return props.getProperty("empresa", "Empresa S.L."); }
    public int getInicioFactura() {
        return Integer.parseInt(props.getProperty("inicio_factura", "1"));
    }
}