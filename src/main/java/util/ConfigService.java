package util;

import java.io.*;
import java.util.Properties;

public class ConfigService {
    private static ConfigService instance;
    private String nombreEmpresa;
    private String direccion;
    private String telefono;
    private int inicioFactura;

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
        // Si el archivo no existe, lo creamos con valores de ejemplo
        if (!f.exists()) {
            crearArchivoPorDefecto();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("=")) {
                    String[] partes = line.split("=");
                    String clave = partes[0].trim().toLowerCase();
                    String valor = partes[1].trim();

                    switch (clave) {
                        case "empresa": nombreEmpresa = valor; break;
                        case "direccion": direccion = valor; break;
                        case "telefono": telefono = valor; break;
                        case "inicio_factura": inicioFactura = Integer.parseInt(valor); break;
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error cargando config.txt, usando valores por defecto: " + e.getMessage());
            this.nombreEmpresa = "Empresa Ejemplo S.L.";
            this.inicioFactura = 12300;
        }
    }

    private void crearArchivoPorDefecto() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("config.txt"))) {
            writer.println("empresa = Mi Facturación S.L.");
            writer.println("direccion = Calle Mayor 1, Madrid, España");
            writer.println("telefono = 912345678");
            writer.println("inicio_factura = 12300");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getters
    public String getNombreEmpresa() { return nombreEmpresa; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public int getInicioFactura() { return inicioFactura; }
}