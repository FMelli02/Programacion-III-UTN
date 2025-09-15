package Escenario2;

import java.util.Objects;

public class PersonaSinHashCode {
    private String nombre;
    private int edad;

    public PersonaSinHashCode(String nombre, int edad) {
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
        PersonaSinHashCode otra = (PersonaSinHashCode) obj;
        return edad == otra.edad && Objects.equals(nombre, otra.nombre);
    }

    // ¡¡¡FALTA el @Override de hashCode() aquí!!!
    // Se usará el hashCode() de Object, que es diferente para cada nueva instancia.

    @Override
    public String toString() {
        return "PersonaSinHashCode{nombre='" + nombre + "', edad=" + edad + '}';
    }
}