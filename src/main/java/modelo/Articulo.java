package modelo;

public class Articulo {
    private String nombre;
    private double precio, iva;

    public Articulo(String nombre, double precio, double iva){
        this.nombre = nombre;
        this.precio = precio;
        this.iva = iva;
    }

    public String mostrar(){
        return "Nombre: " + nombre + "\nPrecio: " + precio + "€\nIVA: " + iva + "%";
    }
}
