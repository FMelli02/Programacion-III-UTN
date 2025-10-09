package org.example.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal costo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCita estado;

    @Column(length = 1000)
    private String observaciones;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;

    public Cita(Long id, LocalDateTime fechaHora, BigDecimal costo, EstadoCita estado, String observaciones, Paciente paciente, Medico medico, Sala sala) {
        this.id = id;
        this.fechaHora = Objects.requireNonNull(fechaHora, "La fecha y hora de la cita es obligatoria");
        this.costo = Objects.requireNonNull(costo, "El costo de la cita es obligatorio");
        this.estado = Objects.requireNonNullElse(estado, EstadoCita.PROGRAMADA);
        this.observaciones = observaciones;
        this.paciente = Objects.requireNonNull(paciente, "La cita requiere un Paciente");
        this.medico = Objects.requireNonNull(medico, "La cita requiere un Médico");
        this.sala = Objects.requireNonNull(sala, "La cita requiere una Sala");
    }

    public String toCsvString() {
        // Reemplaza comas por punto y coma en observaciones (HU-030)
        String obsLimpia = (observaciones != null) ? observaciones.replaceAll(",", ";") : "";

        return String.format("%s,%s,%s,%s,%s,%s,%s",
                paciente.getDni(),
                medico.getDni(),
                sala.getNumero(),
                fechaHora.toString(),
                costo.toPlainString(),
                estado.name(),
                obsLimpia
        );
    }
}