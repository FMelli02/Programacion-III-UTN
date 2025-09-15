package Genericos2;

public class Util {
    public static <T> void imprimir(T elemento) {
        System.out.println(elemento);
    }
    public static void main(String[] args) {
        imprimir("Hola, Mundo!");
        imprimir(123);
        imprimir(45.67);
    }
}
