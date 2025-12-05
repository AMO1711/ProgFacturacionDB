package modelo;

import java.util.ArrayList;

public class Factura {
    private final Model modelo;
    private String fecha, nifCliente;
    private int numFactura;
    private double precioBase, precioIva, precioTotal;
    private LineaFactura[] lineaFacturas = new LineaFactura[10];

    public Factura(Model modelo, int numFactura, String fecha, String nifCliente, double precioB, double precioIva, double precioT, ArrayList<Integer> cantidadArticulo, ArrayList<String> nombreArticulo){
        this.modelo = modelo;
        this.nifCliente = nifCliente;
        this.fecha = fecha;
        this.numFactura = numFactura;
        this.precioBase = precioB;
        this.precioIva = precioIva;
        this.precioTotal = precioT;
        inicializarFactura(cantidadArticulo, nombreArticulo);
    }

    public String mostrar (){
        String mensaje = "Número de factura: " + numFactura + "\nCliente: " + nifCliente + "\nFecha: " + fecha + "\nArtículos:\n";

        for (LineaFactura i: lineaFacturas){
            mensaje += "\t" + i.mostrar() + "\n";
        }

        mensaje += "Precio base: " + precioBase + "\nPrecio IVA: " + precioIva + "\nPrecio total: " + precioTotal;

        return mensaje;
    }

    public double buscarPrecio(String nombreArticulo){
        return  modelo.buscarPrecio(nombreArticulo);
    }

    public double buscarIva(String nombreArticulo){
        return modelo.buscarIva(nombreArticulo);
    }

    private void inicializarFactura(ArrayList<Integer> cantidadArticulo, ArrayList<String> nombreArticulo){
        for (int i = 0; i < 10; i++) {
            lineaFacturas[i] = new LineaFactura(this, cantidadArticulo.get(i), nombreArticulo.get(i));
        }
    }
}
