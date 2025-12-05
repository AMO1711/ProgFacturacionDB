package vista;

public class OpcionTextoMenu implements OpcionMenu {
    private final String texto;

    public OpcionTextoMenu(String texto){
        this.texto = texto;
    }

    @Override
    public void mostrar() {
        System.out.println(this.texto);
    }
}
