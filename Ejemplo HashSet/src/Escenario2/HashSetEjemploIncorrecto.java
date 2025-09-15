package Escenario2;

import java.util.HashSet;
import java.util.Set;

public class HashSetEjemploIncorrecto {
    public static void main(String[] args) {
        Set<PersonaSinHashCode> personas = new HashSet<>();

        PersonaSinHashCode p1 = new PersonaSinHashCode("Ana", 30);
        PersonaSinHashCode p2 = new PersonaSinHashCode("Juan", 25);
        PersonaSinHashCode p3 = new PersonaSinHashCode("Ana", 30); // Lógicamente igual a p1
        PersonaSinHashCode p4 = new PersonaSinHashCode("Pedro", 35);

        System.out.println("--- Añadiendo personas al HashSet (sin hashCode() sobrescrito) ---");
        System.out.println("Añadiendo p1: " + personas.add(p1)); // true
        System.out.println("Añadiendo p2: " + personas.add(p2)); // true
        System.out.println("Añadiendo p3 (igual a p1): " + personas.add(p3)); // ¡TRUE! ¡Problema aquí!
        System.out.println("Añadiendo p4: " + personas.add(p4)); // true
        System.out.println("\nContenido del HashSet: " + personas);
        // Observa que ahora contiene DOS objetos "Ana, 30" (p1 y p3)

        System.out.println("\n--- Verificando existencia con contains() ---");
        PersonaSinHashCode pBuscada = new PersonaSinHashCode("Ana", 30);
        System.out.println("¿El set contiene a 'Ana, 30' (objeto nuevo)? " + personas.contains(pBuscada)); // ¡FALSE! ¡Otro problema!

        System.out.println("\n--- Intentando eliminar ---");
        System.out.println("Eliminando 'Ana, 30' (objeto nuevo): " + personas.remove(pBuscada)); // false
        System.out.println("Contenido del HashSet después de intentar eliminar: " + personas);
    }
}
