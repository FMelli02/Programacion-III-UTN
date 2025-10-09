package org.example.servicio;

import org.example.entidades.*;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Servicio encargado de la serialización y deserialización de Citas a formato CSV. (HU-030/031)
 */
public class CitaService {

    private final List<Cita> citas;
    private final Hospital hospital;

    // Constructor que recibe las entidades de referencia para la importación
    public CitaService(Hospital hospital, List<Cita> citasExistentes) {
        this.hospital = Objects.requireNonNull(hospital, "Hospital de referencia no puede ser nulo.");
        this.citas = citasExistentes;
    }

    // --- HU-030: EXPORTAR CITAS A CSV ---

    public void exportarCitasACsv(String filePath) throws IOException {
        List<String> lineasCsv = new ArrayList<>();

        // Encabezado
        lineasCsv.add("dniPaciente,dniMedico,numeroSala,fechaHora,costo,estado,observaciones");

        for (Cita cita : citas) {
            lineasCsv.add(cita.toCsvString());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String linea : lineasCsv) {
                writer.write(linea);
                writer.newLine();
            }
        }
    }

    // --- HU-031: IMPORTAR CITAS DESDE CSV ---

    public List<Cita> importarCitasDesdeCsv(String filePath) throws IOException, CitaException {
        // ⚠️ ADVERTENCIA CRÍTICA: La HU-031 pide limpiar las citas existentes (HU-031, Fuente 463-467)
        // Simulamos la lógica de limpieza de CitaManager/repositorios en memoria

        List<Cita> nuevasCitas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Saltar la cabecera
            if ((line = reader.readLine()) != null) {
                // Saltar línea
            }

            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String[] campos = line.split(",");

                if (campos.length != 7) {
                    throw new CitaException("Error de formato en línea " + lineNumber + ". Se esperaban 7 campos.");
                }

                // Resolver referencias (HU-031, Fuente 458)
                String dniPaciente = campos[0];
                String dniMedico = campos[1];
                String numeroSala = campos[2];

                Paciente paciente = buscarPaciente(dniPaciente);
                Medico medico = buscarMedico(dniMedico);
                Sala sala = buscarSala(numeroSala);

                // Reconstruir Cita
                LocalDateTime fechaHora = LocalDateTime.parse(campos[3]);
                BigDecimal costo = new BigDecimal(campos[4]);
                EstadoCita estado = EstadoCita.valueOf(campos[5]);
                // Reemplazar punto y coma por coma (inverso a HU-030, Fuente 437)
                String observaciones = campos[6].replaceAll(";", ",");

                Cita cita = Cita.builder()
                        .paciente(paciente)
                        .medico(medico)
                        .sala(sala)
                        .fechaHora(fechaHora)
                        .costo(costo)
                        .estado(estado)
                        .observaciones(observaciones)
                        .build();

                // Mantiene bidireccionalidad
                paciente.addCita(cita);
                medico.addCita(cita);
                sala.addCita(cita);

                nuevasCitas.add(cita);
            }
        }

        // Retornamos las nuevas citas. La lógica de la base de datos deberá persistirlas.
        return nuevasCitas;
    }

    // Métodos helper para buscar entidades por sus claves (DNI/Número)
    private Paciente buscarPaciente(String dni) throws CitaException {
        return hospital.getPacientes().stream()
                .filter(p -> p.getDni().equals(dni))
                .findFirst()
                .orElseThrow(() -> new CitaException("Paciente con DNI " + dni + " no encontrado para importación."));
    }

    private Medico buscarMedico(String dni) throws CitaException {
        // Busca en todos los médicos del hospital (a través de los departamentos)
        return hospital.getDepartamentos().stream()
                .flatMap(d -> d.getMedicos().stream())
                .filter(m -> m.getDni().equals(dni))
                .findFirst()
                .orElseThrow(() -> new CitaException("Médico con DNI " + dni + " no encontrado para importación."));
    }

    private Sala buscarSala(String numero) throws CitaException {
        // Busca en todas las salas del hospital (a través de los departamentos)
        return hospital.getDepartamentos().stream()
                .flatMap(d -> d.getSalas().stream())
                .filter(s -> s.getNumero().equals(numero))
                .findFirst()
                .orElseThrow(() -> new CitaException("Sala con número " + numero + " no encontrada para importación."));
    }
}