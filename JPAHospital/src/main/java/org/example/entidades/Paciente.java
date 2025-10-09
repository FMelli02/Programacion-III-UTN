package org.example.entidades;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Paciente extends Persona {

    private String telefono;
    private String direccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    // Relación OneToOne: mappedBy y cascade ALL para que se persista con el paciente
    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private HistoriaClinica historiaClinica;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Cita> citas;

    // CONSTRUCTOR CRÍTICO: Auto-genera la Historia Clínic
    protected Paciente(PacienteBuilder<?, ?> builder) {
        super(builder);
        this.telefono = Objects.requireNonNull(builder.telefono, "El teléfono es obligatorio");
        this.direccion = Objects.requireNonNull(builder.direccion, "La dirección es obligatoria");
        this.citas = new ArrayList<>(); // ¡OBLIGATORIO!

        // CRÍTICO: La historia clínica se crea UNA SOLA VEZ y se vincula inmediatamente
        this.historiaClinica = HistoriaClinica.builder()
                .paciente(this) // Vincula la historia al paciente
                .build();
    }

    // Método helper para bidireccionalidad
    public void addCita(Cita cita) {
        if (!this.citas.contains(cita)) {
            this.citas.add(cita);
            cita.setPaciente(this);
        }
    }

    public List<Cita> getCitas() {
        return Collections.unmodifiableList(citas);
    }
}