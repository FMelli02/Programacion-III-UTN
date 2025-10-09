package org.example.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroHistoria;// Formato HC-{DNI}-{timestamp}

    @Column(updatable = false)
    private LocalDateTime fechaCreacion;

    @OneToOne(optional = false)
    @JoinColumn(name = "paciente_id", unique = true, nullable = false)
    private Paciente paciente;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "hc_diagnosticos", joinColumns = @JoinColumn(name = "historia_clinica_id"))
    @Builder.Default
    private List<String> diagnosticos = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "hc_tratamientos", joinColumns = @JoinColumn(name = "historia_clinica_id"))
    @Builder.Default
    private List<String> tratamientos = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "hc_alergias", joinColumns = @JoinColumn(name = "historia_clinica_id"))
    @Builder.Default
    private List<String> alergias = new ArrayList<>();

    public HistoriaClinica(Long id, String numeroHistoria, LocalDateTime fechaCreacion, Paciente paciente, List<String> diagnosticos, List<String> tratamientos, List<String> alergias) {
        this.id = id;
        this.paciente = Objects.requireNonNull(paciente, "La Historia Clínica requiere un Paciente");
        this.fechaCreacion = LocalDateTime.now();
        this.numeroHistoria = generarNumeroHistoria(paciente.getDni());
        this.diagnosticos = (diagnosticos != null) ? diagnosticos : new ArrayList<>();
        this.tratamientos = (tratamientos != null) ? tratamientos : new ArrayList<>();
        this.alergias = (alergias != null) ? alergias : new ArrayList<>();
    }

    private String generarNumeroHistoria(String dni) {
        return "HC-" + dni + "-" + System.currentTimeMillis();
    }

    public void agregarDiagnostico(String diagnostico) {
        if (validarString(diagnostico, "Diagnóstico") != null) {
            this.diagnosticos.add(diagnostico);
        }
    }

    public void agregarAlergia(String alergia) {
        if (validarString(alergia, "Alergia") != null) {
            this.alergias.add(alergia);
        }
    }

    public void agregarTratamiento(String tratamiento) {
        if (validarString(tratamiento, "Tratamiento") != null) {
            this.tratamientos.add(tratamiento);
        }
    }

    private String validarString(String valor, String nombreCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(nombreCampo + " no puede estar vacío");
        }
        return valor.trim();
    }

    public List<String> getAlergias() {
        return Collections.unmodifiableList(alergias);
    }
}