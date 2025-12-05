package modelo;

public class Cliente {
    private String nif, nombre, direccion, poblacion, provincia, cp, telefono;

    public Cliente(String cif, String nombre, String direccion, String poblacion, String provincia, String cp, String telefono){
        this.nif = cif;
        this.nombre = nombre;
        this.direccion = direccion;
        this.poblacion = poblacion;
        this.provincia = provincia;
        this.cp = cp;
        this.telefono = telefono;
    }

    public String mostrar(){
        return "CIF: " + nif + "\nNombre: " + nombre + "\nDirección: " + direccion + "\nPoblación: " + poblacion
                + "\nProvincia: " + provincia + "\nCP: " + cp + "\nTelefono: " + telefono;
    }
}
