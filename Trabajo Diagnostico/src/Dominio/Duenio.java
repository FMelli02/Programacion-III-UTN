package Dominio;

import java.util.ArrayList;
import java.util.List;

public class Duenio {
    private String nombre;
    private List<Animal> animales;
    private Telefono telefono;
    public ArrayList<Animal> mascotas;

    public Duenio(String nombre, List<Animal> animales, Telefono telefono) {
        this.nombre = nombre;
        this.animales = animales;
        this.telefono = telefono;
        this.mascotas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getNumeroTelefono() {
        return telefono.getNumero();
    }
}
