package modelo;

import controlador.Controller;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
     Cliente
        nif: 9
        Nombre: 40 (9)
        direccion: 40 (49)
        poblacion: 20 (89)
        provincia: 20 (109)
        cp: 5 (129)
        telefono: 9 (134)
        TOTAL: 143

     Articulo
        Nombre: 40
        Precio: 8 (40)
        iva: 6 (48)
        TOTAL: 54

     Factura
        numerofactura: 7
        fecha: 8 YYYYMMDD (7)
        nif: 9 (15)
        precioB: 8 (24)
        precioIva: 8 (32)
        precioT: 9 (40)
        cantidad + nombre: (4 + 40)*10 (49)
        TOTAL: 489
*/

public class Model {
    private final Controller controlador;
    private int numeroFacturaActual = 1000;

    public Model(Controller controlador){
        this.controlador = controlador;
    }

    public double buscarPrecio(String nombreArticulo){
        String articulo, nombre;
        double precio;
        char[] articulos = new char[54];

        try (BufferedReader br = new BufferedReader(new FileReader("src/ficheros/DatosArticulos.txt"))) {
            while (br.read(articulos) != -1){
                articulo = "";
                for (int i = 0; i < 54; i++) {
                    articulo += articulos[i];
                }
                nombre = articulo.substring(0, 40);

                if (nombre.equals(nombreArticulo)){
                    precio = Double.parseDouble(articulo.substring(40, 48));
                    return precio;
                }
            }
            return 0;
        } catch (IOException e) {
            return 0;
        }
    }

    public double buscarIva(String nombreArticulo){
        String factura, nombre;
        double iva;
        char[] facturas = new char[54];

        try (BufferedReader br = new BufferedReader(new FileReader("src/ficheros/DatosArticulos.txt"))) {
            while (br.read(facturas) != -1){
                factura = "";
                for (int i = 0; i < 54; i++) {
                    factura += facturas[i];
                }
                nombre = factura.substring(0, 40);

                if (nombre.equals(nombreArticulo)){
                    iva = Double.parseDouble(factura.substring(48));
                    return iva;
                }
            }
            return 0;
        } catch (IOException e) {
            return 0;
        }
    }

    public void altaCliente(String nif, String nombre, String direccion, String poblacion, String provincia, String cp, String telefono){
        String datos = nif + nombre + direccion + poblacion + provincia + cp + telefono;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/ficheros/DatosClientes.txt", true))) {
            bw.write(datos);
        } catch (IOException e) {
            System.out.println("Error de escritura en el fichero.");
        }
    }

    public Cliente consultaCliente(String cifCliente){
        String cliente, cif, nombre, direccion, poblacion, provincia, cp, telefono;
        Cliente clienteFinal;
        char[] clientes = new char[143];

        try (BufferedReader br = new BufferedReader(new FileReader("src/ficheros/DatosClientes.txt"))) {
            while (br.read(clientes) != -1){
                cliente = "";
                for (int i = 0; i < 143; i++) {
                    cliente += clientes[i];
                }
                cif = cliente.substring(0, 9);

                if (cif.equals(cifCliente)){
                    nombre = cliente.substring(9, 49);
                    direccion = cliente.substring(49, 89);
                    poblacion = cliente.substring(89, 109);
                    provincia = cliente.substring(109, 129);
                    cp = cliente.substring(129, 134);
                    telefono = cliente.substring(134, 143);
                    clienteFinal = new Cliente(cif, nombre, direccion, poblacion, provincia, cp, telefono);
                    return clienteFinal;
                }
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public ArrayList<Cliente> listadoClientes(){
        String cliente, cif, nombre, direccion, poblacion, provincia, cp, telefono;
        Cliente clienteFinal;
        char[] clientes = new char[143];
        ArrayList<Cliente> clientesFinales = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/ficheros/DatosClientes.txt"))) {
            while (br.read(clientes) != -1){
                cliente = "";
                for (int i = 0; i < 143; i++) {
                    cliente += clientes[i];
                }
                cif = cliente.substring(0, 9);
                nombre = cliente.substring(9, 49);
                direccion = cliente.substring(49, 89);
                poblacion = cliente.substring(89, 109);
                provincia = cliente.substring(109, 129);
                cp = cliente.substring(129, 134);
                telefono = cliente.substring(134, 143);
                clienteFinal = new Cliente(cif, nombre, direccion, poblacion, provincia, cp, telefono);

                clientesFinales.add(clienteFinal);
            }

            if (clientesFinales.isEmpty()){
                return null;
            }
            return clientesFinales;
        } catch (IOException e) {
            return null;
        }
    }

    public void altaArticulo(String nombre, double precio, double iva){
        String datos, precioStr, ivaStr;
        int precioInt, ivaInt;

        precioInt = (int) (precio*100);
        precioStr = String.valueOf(precioInt);
        precioStr = precioStr.substring(0, precioStr.length() - 2) + "." + precioStr.substring(precioStr.length() - 2);
        if (precioStr.length() < 8){
            int iteraciones = precioStr.length();
            for (int i = 0; i < 8-iteraciones; i++) {
                precioStr = "0" + precioStr;
            }
        }

        ivaInt = (int) (iva * 100);
        ivaStr = String.valueOf(ivaInt);
        ivaStr = ivaStr.substring(0, ivaStr.length() - 2) + "." + ivaStr.substring(ivaStr.length() - 2);
        if (ivaStr.length() < 6){
            int iteraciones = ivaStr.length();
            for (int i = 0; i < 6-iteraciones; i++) {
                ivaStr = "0" + ivaStr;
            }
        }

        datos = nombre + precioStr + ivaStr;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/ficheros/DatosArticulos.txt", true))) {
            bw.write(datos);
        } catch (IOException e) {
            System.out.println("Error de escritura en el fichero.");
        }
    }

    public Articulo consultaArticulo(String nombreArticulo){
        String articulo, nombre;
        double precio, iva;
        Articulo articuloFinal;
        char[] articulos = new char[54];

        try (BufferedReader br = new BufferedReader(new FileReader("src/ficheros/DatosArticulos.txt"))) {
            while (br.read(articulos) != -1){
                articulo = "";
                for (int i = 0; i < 54; i++) {
                    articulo += articulos[i];
                }
                nombre = articulo.substring(0, 40);
                nombre = nombre.trim();

                if (nombre.equals(nombreArticulo)){
                    precio = Double.parseDouble(articulo.substring(40, 48));
                    iva = Double.parseDouble(articulo.substring(48));
                    articuloFinal = new Articulo(nombre, precio, iva);
                    return articuloFinal;
                }
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public ArrayList<Articulo> listadoArticulos(){
        String articulo, nombre;
        double precio, iva;
        Articulo articuloFinal;
        char[] articulos = new char[54];
        ArrayList<Articulo> articulosFinales = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/ficheros/DatosArticulos.txt"))) {
            while (br.read(articulos) != -1){
                articulo = "";
                for (int i = 0; i < 54; i++) {
                    articulo += articulos[i];
                }
                nombre = articulo.substring(0, 40);
                precio = Double.parseDouble(articulo.substring(40, 48));
                iva = Double.parseDouble(articulo.substring(48));
                articuloFinal = new Articulo(nombre, precio, iva);

                articulosFinales.add(articuloFinal);
            }

            if (articulosFinales.isEmpty()){
                return  null;
            }
            return articulosFinales;
        } catch (IOException e) {
            return null;
        }
    }

    public void creacionFactura(String nifCliente, ArrayList<String> nombreArticulo, ArrayList<Integer> cantidadArticulo){
        String datos = "", precioStr, ivaStr, totalStr, cantidadStr, lineasFactura = "", factura, numeroFactura;
        int precioInt, ivaInt, totalInt, numeroFacturaTemp = 0;
        double precio, iva, precioT = 0, ivaT = 0, total;
        char[] facturas = new char[489];
        Date fecha = new Date();
        SimpleDateFormat dia = new SimpleDateFormat("dd"), mes = new SimpleDateFormat("MM"), ano = new SimpleDateFormat("yyyy");

        try (BufferedReader br = new BufferedReader(new FileReader("src/ficheros/DatosFacturas.txt"))) {
            while (br.read(facturas) != -1){
                factura = "";
                for (int i = 0; i < 489; i++) {
                    factura += facturas[i];
                }
                numeroFacturaTemp = Integer.parseInt(factura.substring(0, 7));
            }
            if (numeroFacturaTemp > numeroFacturaActual){
                numeroFacturaActual = numeroFacturaTemp;
            }
        } catch (IOException e) {
            System.out.println("Se ha creado el fichero \"DatosFacturas.txt\" para almecenar los datos de las facturas");
        }

        numeroFactura = String.valueOf(numeroFacturaActual+1);
        if (numeroFactura.length() < 7){
            int iteraciones = numeroFactura.length();
            for (int i = 0; i < 7-iteraciones; i++) {
                numeroFactura = "0" + numeroFactura;
            }
        }

        datos += numeroFactura + ano.format(fecha) + mes.format(fecha) + dia.format(fecha) + nifCliente;

        for (int i = 0; i < nombreArticulo.size(); i++) {
            precio = buscarPrecio(nombreArticulo.get(i));
            iva = buscarIva(nombreArticulo.get(i));

            precioT += precio* cantidadArticulo.get(i);
            ivaT += cantidadArticulo.get(i)*precio*iva/100;

            cantidadStr = String.valueOf(cantidadArticulo.get(i));
            if (cantidadStr.length() < 4){
                int iteraciones = cantidadStr.length();
                for (int j = 0; j < 4-iteraciones; j++) {
                    cantidadStr = "0" + cantidadStr;
                }
            }

            lineasFactura += cantidadStr + nombreArticulo.get(i);
        }
        total = precioT + ivaT;

        if (ivaT < 1){
            System.out.println("No se puede crear una factura de precio tan bajo");
            return;
        }

        precioInt = (int) (precioT*100);
        precioStr = String.valueOf(precioInt);
        precioStr = precioStr.substring(0, precioStr.length() - 2) + "." + precioStr.substring(precioStr.length() - 2);
        if (precioStr.length() < 8){
            int iteraciones = precioStr.length();
            for (int i = 0; i < 8-iteraciones; i++) {
                precioStr = "0" + precioStr;
            }
        }

        ivaInt = (int) (ivaT * 100);
        ivaStr = String.valueOf(ivaInt);
        ivaStr = ivaStr.substring(0, ivaStr.length() - 2) + "." + ivaStr.substring(ivaStr.length() - 2);
        if (ivaStr.length() < 8){
            int iteraciones = ivaStr.length();
            for (int i = 0; i < 8-iteraciones; i++) {
                ivaStr = "0" + ivaStr;
            }
        }

        totalInt = (int) (total * 100);
        totalStr = String.valueOf(totalInt);
        totalStr = totalStr.substring(0, totalStr.length() - 2) + "." + totalStr.substring(totalStr.length() - 2);
        if (totalStr.length() < 9){
            int iteraciones = totalStr.length();
            for (int i = 0; i < 9-iteraciones; i++) {
                totalStr = "0" + totalStr;
            }
        }

        //si no hay 10 articulos, llenar de 0's
        if (nombreArticulo.size() < 10){
            int iteraciones = nombreArticulo.size();
            for (int i = 0; i < 10 - iteraciones; i++) {
                lineasFactura += "00000000000000000000000000000000000000000000";
            }
        }

        datos += precioStr + ivaStr + totalStr + lineasFactura;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/ficheros/DatosFacturas.txt", true))) {
            bw.write(datos);
        } catch (IOException e) {
            System.out.println("Error de escritura en el fichero.");
        }
    }

    public Factura consultaFactura(int numeroFactura){
        String factura, numeroStr, nifCliente, fecha, articulos;
        int numeroInt;
        double precioB, precioIva, precioT;
        ArrayList<Integer> cantidadesArticulos = new ArrayList<>();
        ArrayList<String> nombresArticulos = new ArrayList<>();
        Factura facturaFinal;
        char[] facturas = new char[489];

        try (BufferedReader br = new BufferedReader(new FileReader("src/ficheros/DatosFacturas.txt"))) {
            while (br.read(facturas) != -1){
                factura = "";
                for (int i = 0; i < 489; i++) {
                    factura += facturas[i];
                }
                numeroStr = factura.substring(0, 7);
                numeroInt = Integer.parseInt(numeroStr);

                if (numeroInt == numeroFactura){
                    fecha = factura.substring(7, 15);
                    nifCliente = factura.substring(15, 24);
                    precioB = Double.parseDouble(factura.substring(24, 32));
                    precioIva = Double.parseDouble(factura.substring(32, 40));
                    precioT = Double.parseDouble(factura.substring(40, 49));
                    articulos = factura.substring(49);

                    for (int i = 0; i < 10; i++) {
                        cantidadesArticulos.add(Integer.parseInt(articulos.substring(0+44*i, 4+44*i)));
                        nombresArticulos.add(articulos.substring(4+44*i, 44+44*i));
                    }

                    facturaFinal = new Factura(this, numeroFactura, fecha, nifCliente, precioB, precioIva, precioT, cantidadesArticulos, nombresArticulos);
                    return facturaFinal;
                }
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public ArrayList<Factura> listadoFacturas(){
        String factura, nifCliente, fecha, articulos;
        int numero;
        double precioB, precioIva, precioT;
        ArrayList<Integer> cantidadesArticulos = new ArrayList<>();
        ArrayList<String> nombresArticulos = new ArrayList<>();
        Factura facturaFinal;
        char[] facturas = new char[489];
        ArrayList<Factura> facturasFinales = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/ficheros/DatosFacturas.txt"))) {
            while (br.read(facturas) != -1){
                factura = "";
                for (int i = 0; i < 489; i++) {
                    factura += facturas[i];
                }
                numero = Integer.parseInt(factura.substring(0, 7));
                fecha = factura.substring(7, 15);
                nifCliente = factura.substring(15, 24);
                precioB = Double.parseDouble(factura.substring(24, 32));
                precioIva = Double.parseDouble(factura.substring(32, 40));
                precioT = Double.parseDouble(factura.substring(40, 49));
                articulos = factura.substring(49);

                for (int i = 0; i < 10; i++) {
                    cantidadesArticulos.add(Integer.parseInt(articulos.substring(0+44*i, 4+44*i)));
                    nombresArticulos.add(articulos.substring(4+44*i, 44+44*i));
                }

                facturaFinal = new Factura(this, numero, fecha, nifCliente, precioB, precioIva, precioT, cantidadesArticulos, nombresArticulos);
                facturasFinales.add(facturaFinal);
            }

            if (facturasFinales.isEmpty()){
                return null;
            }
            return facturasFinales;
        } catch (IOException e) {
            return null;
        }
    }
}
