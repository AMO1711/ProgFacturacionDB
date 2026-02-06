package vista;

import controlador.Controller;
import modelo.Articulo;
import modelo.Cliente;
import modelo.Factura;

import java.util.ArrayList;
import java.util.Scanner;

public class View {
    private final Menu menuPrincipal;
    private final Scanner escaner;
    private final Controller controlador;

    public View(Controller controlador){
        escaner = new Scanner(System.in);
        menuPrincipal = new Menu("Programa de facturación");
        this.controlador = controlador;

        inicializarMenu();
    }

    private void inicializarMenu(){
        menuPrincipal.addOpciones(new Menu("Gestión de clientes"), () -> mostrarMenuClientes());
        menuPrincipal.addOpciones(new Menu("Gestión de artículos"), () -> mostrarMenuArticulos());
        menuPrincipal.addOpciones(new Menu("Gestión de facturas"), () -> mostrarMenuFacturas());
        menuPrincipal.addOpciones(new Menu("Salir"), () -> salir());
    }

    public void mostrarMenuClientes(){
        Menu submenuClientes = (Menu) menuPrincipal.getOpciones().get(0);
        int opcion;

        submenuClientes.addOpciones(new Menu("Alta de un cliente"), () -> altaCliente());
        submenuClientes.addOpciones(new Menu("Consulta de un cliente"), () -> consultaCliente());
        submenuClientes.addOpciones(new Menu("Listado de clientes"), () -> listadoClientes());
        submenuClientes.addOpciones(new OpcionTextoMenu("Cancelar"), () -> cancelar());

        submenuClientes.mostrar();
        opcion = submenuClientes.leerOpcion();
        submenuClientes.ejecutarOpcion(opcion);
        submenuClientes.borrarOpciones();
    }

    public void mostrarMenuArticulos(){
        Menu submenuArticulos = (Menu) menuPrincipal.getOpciones().get(1);
        int opcion;

        submenuArticulos.addOpciones(new Menu("Alta de un artículo"), () -> altaArticulo());
        submenuArticulos.addOpciones(new Menu("Consulta de un artículo"), () -> consultaArticulo());
        submenuArticulos.addOpciones(new Menu("Lista de un artículo"), () -> listadoArticulos());
        submenuArticulos.addOpciones(new OpcionTextoMenu("Cancelar"), () -> cancelar());

        submenuArticulos.mostrar();
        opcion = submenuArticulos.leerOpcion();
        submenuArticulos.ejecutarOpcion(opcion);
        submenuArticulos.borrarOpciones();
    }

    public void mostrarMenuFacturas(){
        Menu submenuFacturas = (Menu) menuPrincipal.getOpciones().get(2);
        int opcion;

        submenuFacturas.addOpciones(new Menu("Creación de una factura"), () -> creacionFactura());
        submenuFacturas.addOpciones(new Menu("Consulta de una factura"), () -> consultaFactura());
        submenuFacturas.addOpciones(new Menu("Listado de facturas"), () -> listadoFacturas());
        submenuFacturas.addOpciones(new OpcionTextoMenu("Cancelar"), () -> cancelar());

        submenuFacturas.mostrar();
        opcion = submenuFacturas.leerOpcion();
        submenuFacturas.ejecutarOpcion(opcion);
        submenuFacturas.borrarOpciones();
    }

    public void salir(){
        System.out.println("Adiós");
        System.exit(0);
    }

    public void altaCliente(){
        String nif, nombre, direccion, poblacion, provincia, cpDef = "", telefonoDef = "";
        int cp, telefono;

        System.out.println("Alta de un nuevo cliente\n");

        System.out.print("Introduce el NIF de la empresa: ");
        nif = escaner.nextLine();
        while (nif.length() != 9){
            System.out.print("NIF inválido, introduce otro: ");
            nif = escaner.nextLine();
        }

        System.out.print("Introduce el nombre de la empresa: ");
        nombre = escaner.nextLine();
        if (nombre.length() < 40){
            int iterador = nombre.length();
            for (int i = 0; i < 40-iterador; i++) {
                nombre += " ";
            }
        } else if (nombre.length() > 40) {
            nombre = nombre.substring(0, 40);
        }

        System.out.print("Introduce la dirección de la empresa: ");
        direccion = escaner.nextLine();
        if (direccion.length() < 40){
            int iterador = direccion.length();
            for (int i = 0; i < 40-iterador; i++) {
                direccion += " ";
            }
        } else if (direccion.length() > 40) {
            direccion = direccion.substring(0, 40);
        }

        System.out.print("Introduce la población de la empresa: ");
        poblacion = escaner.nextLine();
        if (poblacion.length() < 20){
            int iterador = poblacion.length();
            for (int i = 0; i < 20-iterador; i++) {
                poblacion += " ";
            }
        } else if (poblacion.length() > 20) {
            poblacion = poblacion.substring(0, 20);
        }

        System.out.print("Introduce la provincia de la empresa: ");
        provincia = escaner.nextLine();
        if (provincia.length() < 20){
            int iterador = provincia.length();
            for (int i = 0; i < 20-iterador; i++) {
                provincia += " ";
            }
        } else if (provincia.length() > 20) {
            provincia = provincia.substring(0, 20);
        }

        System.out.print("Introduce el CP de la empresa: ");
        cp = Integer.parseInt(escaner.nextLine());
        while (cp < 1000 || cp >= 100000){ //Sino hago esto tendría un if/else de 5 factores para añadir 0's
            System.out.print("CP inválido, introduce otro: ");
            cp = Integer.parseInt(escaner.nextLine());
        }
        if (cp<10000){
            cpDef += "0";
        }
        cpDef += cp;

        System.out.print("Introduce el telefono de la empresa: ");
        telefono = Integer.parseInt(escaner.nextLine());
        while (telefono < 100000000 || telefono >= 1000000000){ //Sino hago esto tendría un if/else de 9 factores para añadir 0's
            System.out.print("Número de telefono inválido, introduce otro: ");
            telefono = Integer.parseInt(escaner.nextLine());
        }
        telefonoDef += telefono;

        controlador.altaCliente(nif, nombre, direccion, poblacion, provincia, cpDef, telefonoDef);
    }

    public void consultaCliente(){
        String cif;
        Cliente cliente;

        System.out.print("Introduce el CIF del cliente a mostrar: ");
        cif = escaner.nextLine();
        while (cif.length() != 9){
            System.out.print("CIF inválido, introduce otro: ");
            cif = escaner.nextLine();
        }

        cliente = controlador.consultaCliente(cif);

        if (cliente == null){
            System.out.println("No se ha encontrado el cliente buscado");
        } else {
            System.out.println("Cliente encontrado!\n" + cliente.mostrar());
        }
    }

    public void listadoClientes(){
        ArrayList<Cliente> listadoClientes = controlador.listadoClientes();

        if (listadoClientes == null){
            System.out.println("No hay clientes todavía");
        } else {
            System.out.println("Listado de clientes:");
            for (Cliente c: listadoClientes){
                System.out.println(c.mostrar());
            }
        }
    }

    public void altaArticulo(){
        String nombre;
        double precio, iva;

        System.out.println("Alta de un nuevo artículo\n");

        System.out.print("Introduce el nombre del nuevo artículo: ");
        nombre = escaner.nextLine();
        if (nombre.length() < 40){
            int iterador = nombre.length();
            for (int i = 0; i < 40-iterador; i++) {
                nombre += " ";
            }
        } else if (nombre.length() > 40) {
            nombre = nombre.substring(0, 40);
        }

        System.out.print("Introduce el precio del artículo: ");
        precio = Double.parseDouble(escaner.nextLine());
        while (precio <= 0 || precio >= 100000){
            System.out.print("Precio inválido, introduce otro: ");
            precio = Double.parseDouble(escaner.nextLine());
        }

        System.out.print("Introduce el IVA del artículo: ");
        iva = Double.parseDouble(escaner.nextLine());
        while (iva < 0 || iva > 100){
            System.out.print("IVA inválido, introduce otro: ");
            iva = Double.parseDouble(escaner.nextLine());
        }

        controlador.altaArticulo(nombre, precio, iva);
    }

    public void consultaArticulo(){
        String nombreArticulo;
        Articulo articulo;

        System.out.print("Introduce el nombre del artículo a mostrar: ");
        nombreArticulo = escaner.nextLine();

        articulo = controlador.consultaArticulo(nombreArticulo);

        if (articulo == null){
            System.out.println("No se ha encontrado el artículo buscado");
        } else {
            System.out.println("Artículo encontrado!\n" + articulo.mostrar());
        }
    }

    public void listadoArticulos(){
        ArrayList<Articulo> listadoArticulos = controlador.listadoArticulos();

        if (listadoArticulos == null){
            System.out.println("No hay artículos todavía");
        } else {
            System.out.println("Listado de artículos:");
            for (Articulo a: listadoArticulos){
                System.out.println(a.mostrar());
            }
        }
    }

    public void creacionFactura(){
        String nifCliente, articulo, continuar;
        int cantidadArticulo;
        ArrayList<String> nombreArticulos = new ArrayList<>();
        ArrayList<Integer> cantidadArticulos = new ArrayList<>();

        System.out.println("Creación de una factura");

        System.out.print("Introduce el NIF del cliente: ");
        nifCliente = escaner.nextLine();
        while (nifCliente.length() != 9){
            System.out.print("NIF inválido, introduce otro: ");
            nifCliente = escaner.nextLine();
        }

        for (int i = 0; i < 10; i++) {
            if (i != 0){
                System.out.print("Desea añadir otro artículo? Introduzca \"si\" en caso afirmativo:");
                continuar = escaner.nextLine();
                continuar = continuar.toLowerCase();
                if (!continuar.equals("si")){
                    break;
                }
            }

            System.out.print("Introduce el nombre del artículo: ");
            articulo = escaner.nextLine();
            if (articulo.length() < 40){
                int iterador = articulo.length();
                for (int j = 0; j < 40-iterador; j++) {
                    articulo += " ";
                }
            } else if (articulo.length() > 40) {
                articulo = articulo.substring(0, 40);
            }

            System.out.print("Introduce la cantidad de ese artículo: ");
            cantidadArticulo = Integer.parseInt(escaner.nextLine());
            while (cantidadArticulo < 1 || cantidadArticulo >= 10000){
                System.out.print("Cantidad inválida, introduce otra: ");
                cantidadArticulo = Integer.parseInt(escaner.nextLine());
            }

            nombreArticulos.add(articulo);
            cantidadArticulos.add(cantidadArticulo);
        }

        controlador.creacionFactura(nifCliente, nombreArticulos, cantidadArticulos);
    }

    public void consultaFactura(){
        int numFactura;
        Factura factura;

        System.out.print("Indica el número de factura a mostrar: ");
        numFactura = Integer.parseInt(escaner.nextLine());

        factura = controlador.consultaFactura(numFactura);

        if (factura == null){
            System.out.println("No se ha encontrado la factura buscada");
        } else {
            System.out.println("Factura encontrada!\n" + factura.mostrar());
        }
    }

    public void listadoFacturas(){
        ArrayList<Factura> listadoFacturas = controlador.listadoFacturas();

        if (listadoFacturas == null){
            System.out.println("No hay facturas todavía");
        } else {
            System.out.println("Listado de facturas:");
            for (Factura f: listadoFacturas){
                System.out.println(f.mostrar());
            }
        }
    }

    public void cancelar(){
        System.out.println("Operación cancelada");
    }

    public Menu getMenuPrincipal() {
        return menuPrincipal;
    }
}
