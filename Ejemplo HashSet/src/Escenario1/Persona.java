package Escenario1;

import java.util.Objects;

public class Persona {
    private String nombre;
    private int edad;

    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Persona otra = (Persona) obj;
        return edad == otra.edad && Objects.equals(nombre, otra.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, edad);
    }

    @Override
    public String toString() {
        return "Escenario1.Persona{nombre='" + nombre + "', edad=" + edad + '}';
    }
}
