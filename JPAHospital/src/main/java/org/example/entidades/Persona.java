package org.example.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

@MappedSuperclass
@Getter
@SuperBuilder
@NoArgsConstructor
public abstract class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected String nombre;
    protected String apellido;
    protected String dni;
    protected LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    protected TipoSangre tipoSangre;

    protected Persona(PersonaBuilder<?, ?> builder) {
        this.nombre = validarString(builder.nombre, "Nombre");
        this.apellido = validarString(builder.apellido, "Apellido");
        this.dni = validarDni(builder.dni); // Validación de DNI CRÍTICA
        this.fechaNacimiento = Objects.requireNonNull(builder.fechaNacimiento, "Fecha de nacimiento no puede ser nula");
        this.tipoSangre = Objects.requireNonNull(builder.tipoSangre, "Tipo de sangre no puede ser nulo");
    }

    private String validarDni(String dni) {
        validarString(dni, "DNI");
        if (!dni.matches("\\d{7,8}")) {
            throw new IllegalArgumentException("DNI inválido. Debe contener 7 u 8 dígitos numéricos.");
        }
        return dni;
    }

    private String validarString(String valor, String nombreCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(nombreCampo + " no puede estar vacío");
        }
        return valor.trim();
    }

    public int getEdad() {
        return Period.between(this.fechaNacimiento, LocalDate.now()).getYears();
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
}