package controlador;

import modelo.*;
import vista.Menu;
import vista.View;

import java.util.ArrayList;
import java.util.Scanner;

public class Controller {
    private Model modelo;
    private View vista;
    private final Scanner escaner;

    public Controller(){
        this.vista = new View(this);
        this.modelo = new Model(this);
        this.escaner = new Scanner(System.in);
    }

    public void play(){
        int opcion;
        Menu menuPrincipal = vista.getMenuPrincipal();

        while (true){
            menuPrincipal.mostrar();
            opcion = menuPrincipal.leerOpcion();
            menuPrincipal.ejecutarOpcion(opcion);
            System.out.println("Introduce enter para continuar");
            escaner.nextLine();
        }
    }

    public void altaCliente(String nif, String nombre, String direccion, String poblacion, String provincia, String cp, String telefono){
        modelo.altaCliente(nif, nombre, direccion, poblacion, provincia, cp, telefono);
    }

    public Cliente consultaCliente(String cif){
        return modelo.consultaCliente(cif);
    }

    public ArrayList<Cliente> listadoClientes(){
        return modelo.listadoClientes();
    }

    public void altaArticulo(String nombre, double precio, double iva){
        modelo.altaArticulo(nombre, precio, iva);
    }

    public Articulo consultaArticulo(String nombre){
        return modelo.consultaArticulo(nombre);
    }

    public ArrayList<Articulo> listadoArticulos(){
        return modelo.listadoArticulos();
    }

    public void creacionFactura(String nifCliente, ArrayList<String> nombreArticulo, ArrayList<Integer> cantidadArticulo){
        modelo.creacionFactura(nifCliente, nombreArticulo, cantidadArticulo);
    }

    public Factura consultaFactura(int numeroFactura){
        return modelo.consultaFactura(numeroFactura);
    }

    public ArrayList<Factura> listadoFacturas(){
        return modelo.listadoFacturas();
    }
}
