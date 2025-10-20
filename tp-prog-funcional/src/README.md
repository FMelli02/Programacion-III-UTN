# TP Programación Funcional con Java Streams - UTN

Este proyecto contiene la resolución del Trabajo Práctico de **Programación III** de la **Tecnicatura Universitaria en Programación de la UTN**. 
El objetivo principal es aplicar conceptos de programación funcional en Java, utilizando la API de Streams para procesar colecciones de datos de 
forma declarativa y eficiente.

## ¿Cómo lo ejecuto? 🚀

No necesitás Maven, ni Gradle. Solo Java.

### Requisitos

* Tener instalado el **JDK (Java Development Kit)**, versión 8 o superior.

### Pasos para la ejecución

1.  **Abrí una terminal** (o `cmd`, PowerShell, lo que uses) en la carpeta donde guardaste el archivo `Main.java`.

2. **Compilar**: Escribí el siguiente comando para que Java genere el archivo `.class` que se puede ejecutar.

    ```bash
    javac Main.java
    ```

3. **Ejecutar**: Si el paso anterior no tiró ningún error, mandale este comando para ver la magia.

    ```bash
    java Main
    ```

¡Y listo! Deberías ver en la consola la salida de todos los ejercicios, bien prolija.

---

## ¿Qué hay en cada ejercicio? 🤔

El práctico está dividido en cuatro problemas, cada uno enfocado en una clase distinta y con una serie de operaciones a realizar.

### 1. Caso Práctico: Alumnos 🎓

* **Clase utilizada**: `Alumno` (con los atributos nombre, nota y curso).
* **Operaciones**:
    * Obtener una lista con los **nombres en mayúsculas** de los alumnos aprobados (nota ≥ 7), ordenados alfabéticamente.
    * Calcular el **promedio general** de notas de todos los alumnos.
    * **Agrupar a los alumnos por curso** utilizando `Collectors.groupingBy()`.
    * Encontrar las **3 notas más altas** de la lista.

### 2. Caso Práctico: Productos 🛒

* **Clase utilizada**: `Producto` (con nombre, categoria, precio y stock).
* **Operaciones**:
    * Listar los productos con **precio mayor a 100**, ordenados de forma descendente.
    * Agrupar productos por categoría y **calcular el stock total** para cada una.
    * Generar un único **String con el reporte** de "nombre;precio" de cada producto.
    * Calcular el **precio promedio general** y también el promedio por categoría.

### 3. Caso Práctico: Libros 📚

* **Clase utilizada**: `Libro` (con titulo, autor, paginas y precio).
* **Operaciones**:
    * Listar los **títulos de los libros con más de 300 páginas**, ordenados alfabéticamente.
    * Calcular el **promedio de páginas** entre todos los libros.
    * Agrupar los libros por autor y **contar cuántos libros** tiene cada uno.
    * Encontrar el **libro más caro** de toda la colección.

### 4. Caso Práctico: Empleados 👔

* **Clase utilizada**: `Empleado` (con nombre, departamento, salario y edad).
* **Operaciones**:
    * Obtener la lista de empleados que ganan **más de 2000**, ordenados por salario de mayor a menor.
    * Calcular el **salario promedio** de toda la empresa.
    * Agrupar a los empleados por departamento y **sumar el total de salarios** de cada uno.
    * Obtener los nombres de los **2 empleados más jóvenes**.

---

## Autor

* **Franco Mellimaci** - [FMelli02](https://github.com/FMelli02)