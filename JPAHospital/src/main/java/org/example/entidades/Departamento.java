package org.example.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private EspecialidadMedica especialidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @OneToMany(mappedBy = "departamento", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Medico> medicos = new ArrayList<>();

    @OneToMany(mappedBy = "departamento", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Sala> salas = new ArrayList<>();

    public Departamento(Long id, String nombre, EspecialidadMedica especialidad, Hospital hospital, List<Medico> medicos, List<Sala> salas) {
        this.id = id;
        this.nombre = validarString(nombre, "Nombre del Departamento");
        this.especialidad = Objects.requireNonNull(especialidad, "La especialidad es obligatoria");
        this.hospital = hospital;
        this.medicos = (medicos != null) ? medicos : new ArrayList<>();
        this.salas = (salas != null) ? salas : new ArrayList<>();
    }

    // MÉTODO HELPER CRÍTICO: Valida la especialidad compatible
    public void agregarMedico(Medico medico) {
        if (!medico.getEspecialidad().equals(this.especialidad)) {
            throw new IllegalArgumentException("❌ Especialidad incompatible. El médico debe ser " + this.especialidad.name());
        }
        // Sincronización bidireccional
        if (medico.getDepartamento() != this) {
            medico.setDepartamento(this);
        }
        if (!this.medicos.contains(medico)) {
            this.medicos.add(medico);
        }
    }

    private String validarString(String valor, String nombreCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(nombreCampo + " no puede estar vacío");
        }
        return valor.trim();
    }

    public List<Medico> getMedicos() {
        return Collections.unmodifiableList(medicos);
    }
}