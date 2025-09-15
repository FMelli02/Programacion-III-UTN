package Genericos3;

public class Main {
    public static void main(String[] args) {
        Contenedor<String> contenedor = new ContenedorSimple<>();
        contenedor.agregar("Elemento Genérico");
        System.out.println(contenedor.obtener());
    }
}
