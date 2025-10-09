package org.example.servicio;

import org.example.entidades.*;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CitaService {

    private final List<Cita> citas;
    private final Hospital hospital;

    public CitaService(Hospital hospital, List<Cita> citasExistentes) {
        this.hospital = Objects.requireNonNull(hospital, "Hospital de referencia no puede ser nulo.");
        this.citas = citasExistentes;
    }

    // --- HU-030: EXPORTAR CITAS A CSV ---

    public void exportarCitasACsv(String filePath) throws IOException {
        List<String> lineasCsv = new ArrayList<>();

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
        List<Cita> nuevasCitas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            if ((line = reader.readLine()) != null) {
            }

            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String[] campos = line.split(",");

                if (campos.length != 7) {
                    throw new CitaException("Error de formato en línea " + lineNumber + ". Se esperaban 7 campos.");
                }

                String dniPaciente = campos[0];
                String dniMedico = campos[1];
                String numeroSala = campos[2];

                Paciente paciente = buscarPaciente(dniPaciente);
                Medico medico = buscarMedico(dniMedico);
                Sala sala = buscarSala(numeroSala);

                LocalDateTime fechaHora = LocalDateTime.parse(campos[3]);
                BigDecimal costo = new BigDecimal(campos[4]);
                EstadoCita estado = EstadoCita.valueOf(campos[5]);
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

        return nuevasCitas;
    }

    private Paciente buscarPaciente(String dni) throws CitaException {
        return hospital.getPacientes().stream()
                .filter(p -> p.getDni().equals(dni))
                .findFirst()
                .orElseThrow(() -> new CitaException("Paciente con DNI " + dni + " no encontrado para importación."));
    }

    private Medico buscarMedico(String dni) throws CitaException {
        return hospital.getDepartamentos().stream()
                .flatMap(d -> d.getMedicos().stream())
                .filter(m -> m.getDni().equals(dni))
                .findFirst()
                .orElseThrow(() -> new CitaException("Médico con DNI " + dni + " no encontrado para importación."));
    }

    private Sala buscarSala(String numero) throws CitaException {
        return hospital.getDepartamentos().stream()
                .flatMap(d -> d.getSalas().stream())
                .filter(s -> s.getNumero().equals(numero))
                .findFirst()
                .orElseThrow(() -> new CitaException("Sala con número " + numero + " no encontrada para importación."));
    }
}