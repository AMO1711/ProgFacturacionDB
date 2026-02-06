package controlador;

public class Validaciones {

    public static boolean validarDNI(String dni) {
        if (dni == null || !dni.matches("^[0-9]{8}[A-Z]$")) return false;
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int numero = Integer.parseInt(dni.substring(0, 8));
        char letraCalculada = letras.charAt(numero % 23);
        return dni.charAt(8) == letraCalculada;
    }

    public static boolean validarCuentaBancaria(String ccc) {
        // Validación básica de 20 dígitos (Formato español antiguo)
        if (ccc == null || !ccc.matches("^[0-9]{20}$")) return false;
        // Aquí se puede implementar el algoritmo de Dígitos de Control (posiciones 8 y 9)
        return true;
    }
}
