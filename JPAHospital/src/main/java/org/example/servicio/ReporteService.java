package org.example.servicio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.entidades.*;

import java.util.List;
import java.util.Objects;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteService {

    private final EntityManager em;

    public ReporteService(EntityManager em) {
        this.em = Objects.requireNonNull(em, "EntityManager no puede ser nulo.");
    }

    // HU-025: Consultar Médicos por Especialidad
    public Map<EspecialidadMedica, Long> contarMedicosPorEspecialidad() {
        TypedQuery<Object[]> query = em.createQuery(
                "SELECT m.especialidad, COUNT(m) FROM Medico m GROUP BY m.especialidad",
                Object[].class
        );
        return query.getResultStream()
                .collect(Collectors.toMap(
                        result -> (EspecialidadMedica) result[0],
                        result -> (Long) result[1]
                ));
    }

    // HU-026: Reporte de Citas por Estado
    public Map<EstadoCita, Long> contarCitasPorEstado() {
        TypedQuery<Object[]> query = em.createQuery(
                "SELECT c.estado, COUNT(c) FROM Cita c GROUP BY c.estado",
                Object[].class
        );
        return query.getResultStream()
                .collect(Collectors.toMap(
                        result -> (EstadoCita) result[0],
                        result -> (Long) result[1]
                ));
    }

    // HU-027: Consultar Pacientes con Alergias
    public List<Paciente> getPacientesConAlergias() {
        TypedQuery<Paciente> query = em.createQuery(
                "SELECT DISTINCT p FROM Paciente p JOIN p.historiaClinica h WHERE SIZE(h.alergias) > 0",
                Paciente.class
        );
        return query.getResultList();
    }

    // HU-028: Consultar Total de Recursos
    public long contarTotalPacientes() {
        return em.createQuery("SELECT COUNT(p) FROM Paciente p", Long.class).getSingleResult();
    }

    public long contarTotalMedicos() {
        return em.createQuery("SELECT COUNT(m) FROM Medico m", Long.class).getSingleResult();
    }

    public long contarTotalSalas() {
        return em.createQuery("SELECT COUNT(s) FROM Sala s", Long.class).getSingleResult();
    }

    // HU-002: Consultar Hospital (ejemplo de consulta básica)
    public Hospital getHospitalPorId(Long id) {
        return em.find(Hospital.class, id);
    }

    // HU-022: Consultar Citas por Paciente
    public List<Cita> getCitasPorPaciente(Paciente paciente) {
        TypedQuery<Cita> query = em.createQuery(
                "SELECT c FROM Cita c WHERE c.paciente = :pac ORDER BY c.fechaHora DESC", Cita.class);
        query.setParameter("pac", paciente);
        return query.getResultList();
    }

    // HU-023: Consultar Citas por Médico (HU-008 implícita)
    public List<Cita> getCitasPorMedico(Medico medico) {
        TypedQuery<Cita> query = em.createQuery(
                "SELECT c FROM Cita c WHERE c.medico = :med ORDER BY c.fechaHora ASC", Cita.class);
        query.setParameter("med", medico);
        return query.getResultList();
    }

    // Nota: Las HU-030/031 (Exportar/Importar CSV)
    public List<Cita> getCitasPorHospital(Hospital hospital) {
        return hospital.getDepartamentos().stream()
                .flatMap(d -> d.getSalas().stream())
                .flatMap(s -> s.getCitas().stream())
                .collect(Collectors.toList());
    }

}