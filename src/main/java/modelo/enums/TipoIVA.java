package modelo.enums;

public enum TipoIVA {
    GENERAL(21), REDUCIDO(10), SUPERREDUCIDO(4), EXENTO(0);
    private final int valor;
    TipoIVA(int valor) { this.valor = valor; }
    public int getValor() { return valor; }
}