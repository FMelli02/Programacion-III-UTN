package org.example.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String telefono;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Departamento> departamentos = new ArrayList<>();

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Paciente> pacientes = new ArrayList<>();

    public Hospital(Long id, String nombre, String direccion, String telefono, List<Departamento> departamentos, List<Paciente> pacientes) {
        this.id = id;
        this.nombre = validarString(nombre, "Nombre del Hospital");
        this.direccion = validarString(direccion, "Dirección del Hospital");
        this.telefono = validarString(telefono, "Teléfono del Hospital");
        this.departamentos = (departamentos != null) ? departamentos : new ArrayList<>();
        this.pacientes = (pacientes != null) ? pacientes : new ArrayList<>();
    }

    public void agregarDepartamento(Departamento dept) {
        if (dept.getHospital() != this) {
            dept.setHospital(this);
        }
        if (!this.departamentos.contains(dept)) {
            this.departamentos.add(dept);
        }
    }

    public void agregarPaciente(Paciente paciente) {
        if (paciente.getHospital() != this) {
            paciente.setHospital(this);
        }
        if (!this.pacientes.contains(paciente)) {
            this.pacientes.add(paciente);
        }
    }

    private String validarString(String valor, String nombreCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(nombreCampo + " no puede estar vacío");
        }
        return valor.trim();
    }

    public List<Departamento> getDepartamentos() {
        return Collections.unmodifiableList(departamentos);
    }
    public List<Paciente> getPacientes() {
        return Collections.unmodifiableList(pacientes);
    }
}