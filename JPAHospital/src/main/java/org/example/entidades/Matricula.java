package org.example.entidades;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Embeddable
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
public class Matricula {

    private String numero;

    public Matricula(String numero) {
        Objects.requireNonNull(numero, "La matrícula no puede ser nula");
        if (!numero.matches("MP-\\d{4,6}")) { // Validación: MP-XXXXX [cite: 453, 940, 1082]
            throw new IllegalArgumentException("Formato de matrícula profesional inválido. Debe ser MP- seguido de 4 a 6 dígitos.");
        }
        this.numero = numero;
    }
}