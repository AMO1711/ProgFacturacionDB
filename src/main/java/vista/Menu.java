package vista;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu implements OpcionMenu {
    private String titulo;
    private ArrayList<OpcionMenu> opciones;
    private ArrayList<Runnable> ejectutables;

    public Menu(){
        this.titulo = "";
        this.opciones = new ArrayList<>();
        this.ejectutables = new ArrayList<>();
    }

    public Menu(String titulo){
        this.titulo = titulo;
        this.opciones = new ArrayList<>();
        this.ejectutables = new ArrayList<>();
    }

    public int leerOpcion(){
        Scanner escaner = new Scanner(System.in);

        System.out.print("Introduce el número de la opción a seleccionar: ");
        return Integer.parseInt(escaner.nextLine());
    }

    public void ejecutarOpcion(int opcion){
        opciones.get(opcion-1).mostrar();
        ejectutables.get(opcion-1).run();
    }

    public void addOpciones(OpcionMenu opcion, Runnable ejecutable){
        this.opciones.add(opcion);
        this.ejectutables.add(ejecutable);
    }

    public void borrarOpciones(){
        this.opciones = new ArrayList<>();
        this.ejectutables = new ArrayList<>();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public ArrayList<OpcionMenu> getOpciones() {
        return opciones;
    }

    @Override
    public void mostrar() {
        System.out.println(this.titulo);
        for (int i = 1; i < opciones.size()+1; i++) {
            System.out.print(i + ". ");
            opciones.get(i-1).mostrar();
        }
        System.out.println();
    }
}
