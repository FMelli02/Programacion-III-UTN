package Dominio;

import java.time.LocalDate;

public abstract class Animal {
    private String nombre;
    private int edad;
    private LocalDate fechaNacimiento;
    private EstadoAnimal estado;
    private Duenio duenio;
    private FichaMedica fichaMedica;

    //Comportamiento polimorfico
    public String emitirSonido() {
        return null;
    }

    public String getDieta() {
        return null;
    }

    public boolean necesitaVacunaAnual() {
        return false;
    }

    public String getIdentificacion() {
        return null;
    }
}
