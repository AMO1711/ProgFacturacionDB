package modelo;

public class LineaFactura {
    private Factura factura;
    private String nombreArticulo;
    private int cantidad;
    private double precioUnitario, iva;

    public LineaFactura(Factura factura, int cantidad, String nombreArticulo){
        this.factura = factura;
        this.nombreArticulo = nombreArticulo;
        this.cantidad = cantidad;
        this.precioUnitario = buscarPrecio(nombreArticulo);
        this.iva = buscarIva(nombreArticulo);
    }

    public String mostrar(){
        return "Nombre del artículo: " + nombreArticulo + "\nCantidad: " + cantidad + "\nPrecio unitario: "
                + precioUnitario + "\nIVA: " + iva + "%";
    }

    private double buscarPrecio(String nombreArticulo){
        return factura.buscarPrecio(nombreArticulo);
    }

    private double buscarIva(String nombreArticulo){
        return factura.buscarIva(nombreArticulo);
    }
}
