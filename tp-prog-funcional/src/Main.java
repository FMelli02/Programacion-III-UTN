import ejercicio1.Alumno;
import ejercicio2.Producto;
import ejercicio3.Libro;
import ejercicio4.Empleado;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n=============== EJERCICIO 1 ===============");
        List<Alumno> alumnos = Arrays.asList(
                new Alumno("Juan",8.5, "Java"),
                new Alumno("Ana",6.0, "Python"),
                new Alumno("Carlos",9.0, "Java"),
                new Alumno("María",5.5, "JavaScript"),
                new Alumno("Pedro",3.5, "Java"),
                new Alumno("Lucía",10.0, "Python")
        );

        System.out.println("\n1. Alumnos Aprobados:");
        List<String> aprobados = alumnos.stream()
                .filter(a -> a.getNota() >= 7)
                .map(a -> a.getNombre().toUpperCase())
                .sorted()
                .collect(Collectors.toList());

        aprobados.forEach(System.out::println);


        System.out.println("\n2. Promedio general de notas:");
        double promedioGeneral = alumnos.stream()
                .mapToDouble(Alumno::getNota)
                .average()
                .orElse(0.0);

        System.out.println("El promedio es: " + promedioGeneral);


        System.out.println("\n3. Alumnos agrupados por curso");
        Map<String, List<Alumno>> alumnosPorCurso = alumnos.stream()
                .collect(Collectors.groupingBy(Alumno::getCurso));

        alumnosPorCurso.forEach((curso, lista) -> {
            System.out.println("Curso: " + curso);
            lista.forEach(alumno -> System.out.println(" - " + alumno.getNombre()));
        });


        System.out.println("\n4. Los 3 mejores promedios:");
        List<Double> mejoresNotas = alumnos.stream()
                .map(Alumno::getNota)
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .collect(Collectors.toList());

        System.out.println("Las 3 mejores notas son: " + mejoresNotas);

        System.out.println("\n=============== EJERCICIO 2 ===============");
        List<Producto> productos = Arrays.asList(
                new Producto("Laptop", "Tecnología", 1200.50, 10),
                new Producto("Mouse", "Tecnología", 25.00, 50),
                new Producto("Teclado", "Tecnología", 75.99, 30),
                new Producto("Remera", "Ropa", 20.00, 100),
                new Producto("Pantalón", "Ropa", 110.00, 80)
        );

        System.out.println("\n1. Productos con precio >100, ordenados por precio descendente:");
        List<Producto> productosCaros = productos.stream()
                .filter(p -> p.getPrecio() > 100)
                .sorted(Comparator.comparing(Producto::getPrecio).reversed())
                .collect(Collectors.toList());

        productosCaros.forEach(System.out::println);


        System.out.println("\n2. Agrupar por categoría y calcular el stock total:");
        Map<String, Integer> stockPorCategoria = productos.stream()
                .collect(Collectors.groupingBy(
                        Producto::getCategoria,
                        Collectors.summingInt(Producto::getStock)
                ));

        System.out.println("Stock por categoría: " + stockPorCategoria);


        System.out.println("\n3. String con nombre y precio:");
        String reporteProductos = productos.stream()
                .map(p -> p.getNombre() + ";" + p.getPrecio())
                .collect(Collectors.joining(" | "));

        System.out.println(reporteProductos);


        System.out.println("\n4. Precio promedio general y por categoría:");
        //General
        double promedioPrecioGeneral = productos.stream()
                .mapToDouble(Producto::getPrecio)
                .average()
                .orElse(0.0);
        System.out.println("Precio promedio general: " + promedioPrecioGeneral);

        // Por categoría
        Map<String, Double> promedioPorCategoria = productos.stream()
                .collect(Collectors.groupingBy(
                        Producto::getCategoria,
                        Collectors.averagingDouble(Producto::getPrecio)
                ));
        System.out.println("Promedio por categoría: " + promedioPorCategoria);


        System.out.println("\n=============== EJERCICIO 3 ===============");

        List<Libro> libros = Arrays.asList(
                new Libro("El Señor de los Anillos", "J.R.R. Tolkien", 1200, 25.50),
                new Libro("Cien Años de Soledad", "Gabriel García Márquez", 450, 18.00),
                new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", 863, 22.99),
                new Libro("1984", "George Orwell", 328, 15.75),
                new Libro("Ficciones", "Jorge Luis Borges", 224, 12.50),
                new Libro("Rayuela", "Julio Cortázar", 600, 20.00)
        );

        System.out.println("\n1. Títulos de libros con más de 300 páginas:");
        List<String> titulosLargos = libros.stream()
                .filter(libro -> libro.getPaginas() > 300)
                .map(Libro::getTitulo)
                .sorted()
                .collect(Collectors.toList());

        titulosLargos.forEach(System.out::println);


        System.out.println("\n2. Promedio de páginas:");
        double promedioPaginas = libros.stream()
                .mapToInt(Libro::getPaginas)
                .average()
                .orElse(0.0);

        System.out.println("El promedio de páginas es: " + promedioPaginas);


        System.out.println("\n3. Cantidad de libros por autor:");
        Map<String, Long> librosPorAutor = libros.stream()
                .collect(Collectors.groupingBy(
                        Libro::getAutor,
                        Collectors.counting()
                ));

        librosPorAutor.forEach((autor, cantidad) ->
                System.out.println(autor + ": " + cantidad + " libro(s)"));


        System.out.println("\n4. Libro más caro:");
        Optional<Libro> libroMasCaro = libros.stream()
                .max(Comparator.comparing(Libro::getPrecio));

        libroMasCaro.ifPresent(libro -> System.out.println("El libro más caro es: " + libro.getTitulo()));


        System.out.println("\n=============== EJERCICIO 4 ===============");

        List<Empleado> empleados = Arrays.asList(
                new Empleado("Laura", "Ventas", 2500, 30),
                new Empleado("Marcos", "IT", 3200, 25),
                new Empleado("Sofia", "Ventas", 1900, 35),
                new Empleado("Martin", "IT", 2800, 22),
                new Empleado("Julieta", "RRHH", 2100, 28)
        );

        System.out.println("\n1. Empleados con salario mayor a 2000:");
        List<Empleado> empleadosBienPagados = empleados.stream()
                .filter(e -> e.getSalario() > 2000)
                .sorted(Comparator.comparingDouble(Empleado::getSalario).reversed())
                .collect(Collectors.toList());

        empleadosBienPagados.forEach(System.out::println);


        System.out.println("\n2. Salario promedio general:");
        double salarioPromedio = empleados.stream()
                .mapToDouble(Empleado::getSalario)
                .average()
                .orElse(0.0);

        System.out.println("El salario promedio es: " + salarioPromedio);


        System.out.println("\n3. Suma de salarios por departamento:");
        Map<String, Double> salariosPorDepto = empleados.stream()
                .collect(Collectors.groupingBy(
                        Empleado::getDepartamento,
                        Collectors.summingDouble(Empleado::getSalario)
                ));

        salariosPorDepto.forEach((depto, suma) ->
                System.out.println("Departamento " + depto + ": $" + suma));


        System.out.println("\n4. Los 2 empleados más jóvenes:");
        List<String> empleadosMasJovenes = empleados.stream()
                .sorted(Comparator.comparingInt(Empleado::getEdad))
                .limit(2)
                .map(Empleado::getNombre)
                .collect(Collectors.toList());

        System.out.println("Los más jóvenes son: " + empleadosMasJovenes);
    }
}
