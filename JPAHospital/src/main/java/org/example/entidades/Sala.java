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
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numero;

    private String tipo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "departamento_id", nullable = false)
    private Departamento departamento;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Cita> citas = new ArrayList<>();

    public Sala(Long id, String numero, String tipo, Departamento departamento, List<Cita> citas) {
        this.id = id;
        this.numero = validarString(numero, "Número de Sala");
        this.tipo = validarString(tipo, "Tipo de Sala");
        this.departamento = Objects.requireNonNull(departamento, "La Sala debe pertenecer a un Departamento");
        this.citas = (citas != null) ? citas : new ArrayList<>();
    }

    // Método helper para bidireccionalidad con Cita
    public void addCita(Cita cita) {
        if (!this.citas.contains(cita)) {
            this.citas.add(cita);
            cita.setSala(this);
        }
    }

    private String validarString(String valor, String nombreCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(nombreCampo + " no puede estar vacío");
        }
        return valor.trim();
    }

    public List<Cita> getCitas() {
        return Collections.unmodifiableList(citas);
    }
}