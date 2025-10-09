package org.example.servicio;

import org.example.entidades.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.List;

public class CitaManager {
    public Cita programarCita(Paciente paciente, Medico medico, Sala sala, LocalDateTime fechaHora, BigDecimal costo) throws CitaException {

        Objects.requireNonNull(paciente, "Paciente no puede ser nulo");
        Objects.requireNonNull(medico, "Médico no puede ser nulo");
        Objects.requireNonNull(sala, "Sala no puede ser nula");
        Objects.requireNonNull(fechaHora, "Fecha/Hora no puede ser nula");
        Objects.requireNonNull(costo, "Costo no puede ser nulo");

        // 1. Validación Temporal (fecha futura)
        if (fechaHora.isBefore(LocalDateTime.now())) {
            throw new CitaException("❌ No se puede programar cita en el pasado.");
        }

        // 2. Validación Económica (costo > 0)
        if (costo.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CitaException("❌ El costo de la consulta debe ser mayor que cero.");
        }

        // 3. Validación de Especialidad Compatible
        if (!medico.getEspecialidad().equals(sala.getDepartamento().getEspecialidad())) {
            throw new CitaException("❌ La especialidad del médico (" + medico.getEspecialidad() +
                    ") no coincide con el departamento de la sala (" + sala.getDepartamento().getEspecialidad() + ").");
        }

        // 4. Validación de Disponibilidad (Buffer de 2 horas)
        if (!esMedicoDisponible(medico, fechaHora)) {
            throw new CitaException("❌ Médico no disponible. Buffer de 2 horas requerido.");
        }
        if (!esSalaDisponible(sala, fechaHora)) {
            throw new CitaException("❌ Sala no disponible. Buffer de 2 horas requerido.");
        }

        // Si todas las validaciones pasan, crea la cita
        Cita nuevaCita = Cita.builder()
                .paciente(paciente)
                .medico(medico)
                .sala(sala)
                .fechaHora(fechaHora)
                .costo(costo)
                .estado(EstadoCita.PROGRAMADA) // Estado inicial
                .build();

        // Sincronizar bidireccionalidad
        paciente.addCita(nuevaCita);
        medico.addCita(nuevaCita);
        sala.addCita(nuevaCita);

        return nuevaCita;
    }

    private boolean esMedicoDisponible(Medico medico, LocalDateTime nuevaFechaHora) {
        List<Cita> citasProgramadas = medico.getCitas().stream()
                .filter(c -> c.getEstado() == EstadoCita.PROGRAMADA || c.getEstado() == EstadoCita.EN_CURSO)
                .toList();

        for (Cita citaExistente : citasProgramadas) {
            LocalDateTime existenteFechaHora = citaExistente.getFechaHora();
            long horasDiferencia = Math.abs(ChronoUnit.HOURS.between(existenteFechaHora, nuevaFechaHora));

            if (horasDiferencia < 2) {
                return false;
            }
        }
        return true;
    }

    private boolean esSalaDisponible(Sala sala, LocalDateTime nuevaFechaHora) {
        List<Cita> citasProgramadas = sala.getCitas().stream()
                .filter(c -> c.getEstado() == EstadoCita.PROGRAMADA || c.getEstado() == EstadoCita.EN_CURSO)
                .toList();

        for (Cita citaExistente : citasProgramadas) {
            LocalDateTime existenteFechaHora = citaExistente.getFechaHora();
            long horasDiferencia = Math.abs(ChronoUnit.HOURS.between(existenteFechaHora, nuevaFechaHora));

            if (horasDiferencia < 2) {
                return false;
            }
        }
        return true;
    }
}