package Escenario1;

import java.util.HashSet;
import java.util.Set;

public class HashSetEjemploCorrecto {
    public static void main(String[] args) {
        Set<Persona> personas = new HashSet<>();

        Persona p1 = new Persona("Ana", 30);
        Persona p2 = new Persona("Juan", 25);
        Persona p3 = new Persona("Ana", 30); // Lógicamente igual a p1
        Persona p4 = new Persona("Pedro", 35);

        System.out.println("--- Añadiendo personas al HashSet ---");
        System.out.println("Añadiendo p1: " + personas.add(p1)); // true
        System.out.println("Añadiendo p2: " + personas.add(p2)); // true
        System.out.println("Añadiendo p3 (igual a p1): " + personas.add(p3)); // false, ¡ya existe!
        System.out.println("Añadiendo p4: " + personas.add(p4)); // true
        System.out.println("\nContenido del HashSet: " + personas);

        System.out.println("\n--- Verificando existencia con contains() ---");
        Persona pBuscada = new Persona("Ana", 30); // Otro objeto lógicamente igual a p1
        System.out.println("¿El set contiene a 'Ana, 30' (objeto nuevo)? " + personas.contains(pBuscada)); // true

        System.out.println("\n--- Intentando eliminar ---");
        System.out.println("Eliminando 'Ana, 30' (objeto nuevo): " + personas.remove(pBuscada)); // true
        System.out.println("Contenido del HashSet después de eliminar: " + personas);

        // Intentar añadir p3 de nuevo después de eliminar su equivalente
        System.out.println("\nAñadiendo p3 (Ana, 30) de nuevo: " + personas.add(p3)); // true, porque ya fue eliminado
        System.out.println("Contenido final del HashSet: " + personas);
    }
}
