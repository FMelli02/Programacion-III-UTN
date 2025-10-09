package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entidades.*;
import org.example.servicio.CitaException;
import org.example.servicio.CitaManager;
import org.example.servicio.CitaService;
import org.example.servicio.ReporteService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // 1. Inicialización de EntityManagerFactory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hospital-persistence-unit");
        EntityManager em = emf.createEntityManager();

        // ⚠️ INICIALIZACIÓN DE SERVICIOS: UNA SOLA VEZ
        CitaManager citaManager = new CitaManager();
        ReporteService reporteService = new ReporteService(em);

        Hospital hospitalCentral = null;

        System.out.println("\n--- INICIO DE DEMOSTRACIÓN JPA HOSPITAL ---");

        try {
            // =========================================================
            // TRANSACCIÓN 1: CREACIÓN Y PERSISTENCIA DE DATOS BASE
            // =========================================================
            em.getTransaction().begin();

            // 1.1 Crear Hospital (Aggregate Root)
            hospitalCentral = Hospital.builder()
                    .nombre("Hospital Central UTN")
                    .direccion("Av. Libertador 1234")
                    .telefono("011-4567-8901")
                    .build();

            // 1.2 Crear y asociar Departamentos/Salas
            Departamento cardiologia = Departamento.builder()
                    .nombre("Cardiología Intervencionista")
                    .especialidad(EspecialidadMedica.CARDIOLOGIA)
                    .build();

            Departamento pediatria = Departamento.builder()
                    .nombre("Unidad de Pediatría")
                    .especialidad(EspecialidadMedica.PEDIATRIA)
                    .build();

            hospitalCentral.agregarDepartamento(cardiologia);
            hospitalCentral.agregarDepartamento(pediatria);

            Sala salaCardio = Sala.builder().numero("CARD-101").tipo("Consultorio").departamento(cardiologia).build();
            Sala salaPedia = Sala.builder().numero("PEDI-202").tipo("Consultorio").departamento(pediatria).build();

            // 1.3 Crear y asignar Médicos
            Medico drCorazon = Medico.builder()
                    .nombre("Roberto")
                    .apellido("Sánchez")
                    .dni("12345678")
                    .fechaNacimiento(LocalDate.of(1970, 1, 1))
                    .tipoSangre(TipoSangre.O_NEGATIVO)
                    .matricula(new Matricula("MP-10000"))
                    .especialidad(EspecialidadMedica.CARDIOLOGIA)
                    .build();

            Medico draKids = Medico.builder()
                    .nombre("Laura")
                    .apellido("Pérez")
                    .dni("22334455")
                    .fechaNacimiento(LocalDate.of(1985, 5, 20))
                    .tipoSangre(TipoSangre.A_POSITIVO)
                    .matricula(new Matricula("MP-20000"))
                    .especialidad(EspecialidadMedica.PEDIATRIA)
                    .build();

            cardiologia.agregarMedico(drCorazon);
            pediatria.agregarMedico(draKids);

            // 1.4 Crear y asignar Pacientes (Historia Clínica Auto-generada)
            Paciente maria = Paciente.builder()
                    .nombre("María")
                    .apellido("López")
                    .dni("33445566")
                    .fechaNacimiento(LocalDate.of(1990, 12, 5))
                    .tipoSangre(TipoSangre.B_POSITIVO)
                    .telefono("1122334455")
                    .direccion("Calle Falsa 123")
                    .build();

            Paciente juan = Paciente.builder()
                    .nombre("Juan")
                    .apellido("Gómez")
                    .dni("44556677")
                    .fechaNacimiento(LocalDate.of(2010, 8, 15))
                    .tipoSangre(TipoSangre.A_NEGATIVO)
                    .telefono("1166778899")
                    .direccion("Av. Siempre Viva 742")
                    .build();

            hospitalCentral.agregarPaciente(maria);
            hospitalCentral.agregarPaciente(juan);

            // 1.5 Agregar data sensible
            maria.getHistoriaClinica().agregarAlergia("Penicilina");
            juan.getHistoriaClinica().agregarDiagnostico("Control de niño sano");

            em.persist(hospitalCentral);

            em.getTransaction().commit();
            System.out.println("✅ T1: Datos iniciales (Hospital, Dep, Med, Pac) persistidos correctamente.");


            // =========================================================
            // TRANSACCIÓN 2: PROGRAMACIÓN Y VALIDACIÓN DE CITAS
            // =========================================================
            em.getTransaction().begin();

            // Cita A: Exitosa (Dr. Corazón en 5 días)
            Cita citaA = null;
            try {
                citaA = citaManager.programarCita(
                        maria, drCorazon, salaCardio,
                        LocalDateTime.now().plusDays(5).withHour(10).withMinute(0),
                        new BigDecimal("15000.00")
                );
                em.persist(citaA);
                System.out.println("✅ Cita A (Cardio) programada. Costo: " + citaA.getCosto());
            } catch (CitaException e) {
                System.err.println("❌ Error Cita A: " + e.getMessage());
            }

            // Cita B: Intento fallido (TEST BUFFER - Menos de 2 horas)
            try {
                Cita citaB = citaManager.programarCita(
                        juan, drCorazon, salaCardio,
                        LocalDateTime.now().plusDays(5).withHour(11).withMinute(0),
                        new BigDecimal("12000.00")
                );
                em.persist(citaB);
            } catch (CitaException e) {
                System.err.println("❌ TEST BUFFER OK. Cita B Falló: " + e.getMessage());
            }

            // Cita C: Cita pediátrica exitosa
            Cita citaC = null;
            try {
                citaC = citaManager.programarCita(
                        juan, draKids, salaPedia,
                        LocalDateTime.now().plusDays(6).withHour(14).withMinute(0),
                        new BigDecimal("10000.00")
                );
                em.persist(citaC);
                System.out.println("✅ Cita C (Pediatría) programada. Médico: " + draKids.getNombre());
            } catch (CitaException e) {
                System.err.println("❌ Error Cita C: " + e.getMessage());
            }

            em.getTransaction().commit();
            System.out.println("✅ T2: Programación de citas finalizada (Validación de buffer demostrada).");


            // =========================================================
            // TRANSACCIÓN 3: ACTUALIZACIÓN Y CONSULTAS (ReporteService)
            // =========================================================
            em.getTransaction().begin();

            // 3.1 Actualización de estado de cita
            Cita citaA_Persistida = em.find(Cita.class, citaA.getId());
            if (citaA_Persistida != null) {
                citaA_Persistida.setEstado(EstadoCita.COMPLETADA);
                em.merge(citaA_Persistida);
                System.out.println("\n✅ Cita ID:" + citaA.getId() + " actualizada a estado: " + citaA_Persistida.getEstado());
            }

            System.out.println("\n--- Reporte de Estadísticas (ReporteService) ---");

            // HU-025: Médicos por especialidad
            Map<EspecialidadMedica, Long> medicos = reporteService.contarMedicosPorEspecialidad();
            System.out.println("Médicos por Especialidad: " + medicos);

            // HU-027: Pacientes con Alergias
            List<Paciente> pacientesAlergicos = reporteService.getPacientesConAlergias();
            System.out.println("Pacientes con alergias registradas: " + pacientesAlergicos.size());

            // HU-028: Total de Recursos
            System.out.println("Total de Pacientes: " + reporteService.contarTotalPacientes());

            // HU-026: Citas por Estado
            Map<EstadoCita, Long> citasEstado = reporteService.contarCitasPorEstado();
            System.out.println("Citas por Estado: " + citasEstado);

            em.getTransaction().commit();
            System.out.println("✅ T3: Consultas y Actualización completadas. Capa de servicio de reportes demostrada.");


            // =========================================================
            // TRANSACCIÓN 4: EXPORTAR CITAS A CSV (HU-030)
            // =========================================================
            String CSV_PATH = "./citas_exportadas.csv";

            try {
                Hospital hospitalManaged = em.find(Hospital.class, hospitalCentral.getId());
                List<Cita> todasLasCitas = reporteService.getCitasPorHospital(hospitalManaged);

                CitaService csvService = new CitaService(hospitalManaged, todasLasCitas);
                csvService.exportarCitasACsv(CSV_PATH);
                System.out.println("✅ T4: Exportación a CSV exitosa en: " + CSV_PATH);
            } catch (java.io.IOException e) {
                System.err.println("❌ Error de I/O al exportar CSV: " + e.getMessage());
            }

            // =========================================================
            // TRANSACCIÓN 5: IMPORTAR CITAS DESDE CSV (HU-031)
            // =========================================================
            em.getTransaction().begin();

            try {
                Hospital hospitalManaged = em.find(Hospital.class, hospitalCentral.getId());

                CitaService csvServiceImport = new CitaService(hospitalManaged, new ArrayList<>());
                List<Cita> citasImportadas = csvServiceImport.importarCitasDesdeCsv(CSV_PATH);

                // Persistimos las citas importadas para que queden en la BD
                citasImportadas.forEach(em::persist);

                System.out.println("✅ T5: Importación desde CSV exitosa. Total de citas importadas: " + citasImportadas.size());

            } catch (java.io.IOException | CitaException e) {
                System.err.println("❌ Error de I/O o validación al importar CSV: " + e.getMessage());
            }

            em.getTransaction().commit();
            System.out.println("✅ T5: Persistencia de datos importados completada.");


            // =========================================================
            // 6. CIERRE Y MENSAJE FINAL
            // =========================================================
            System.out.println("\n" + "=".repeat(40));
            System.out.println("SISTEMA EJECUTADO EXITOSAMENTE");
            System.out.println("=".repeat(40));

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("\n⚠️ ¡ERROR FATAL! Falló una transacción. Rollback ejecutado.");
            e.printStackTrace();

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        }
    }
}