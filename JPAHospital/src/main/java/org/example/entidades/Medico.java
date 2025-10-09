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
public class Medico extends Persona {

    // Value Object embebido
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "numero", column = @Column(name = "matricula_numero", unique = true, nullable = false))
    })
    private Matricula matricula;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EspecialidadMedica especialidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    // Relación OneToMany: mapeada por Cita
    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cita> citas;

    // CONSTRUCTOR CRÍTICO: Inicializa la colección por el bug de Lombok/SuperBuilder
    protected Medico(MedicoBuilder<?, ?> builder) {
        super(builder);
        this.matricula = Objects.requireNonNull(builder.matricula, "La matrícula es obligatoria");
        this.especialidad = Objects.requireNonNull(builder.especialidad, "La especialidad es obligatoria");
        this.citas = new ArrayList<>(); // ¡OBLIGATORIO!
    }

    // Método helper para bidireccionalidad
    public void addCita(Cita cita) {
        if (!this.citas.contains(cita)) {
            this.citas.add(cita);
            cita.setMedico(this);
        }
    }

    public List<Cita> getCitas() {
        return Collections.unmodifiableList(citas);
    }
}