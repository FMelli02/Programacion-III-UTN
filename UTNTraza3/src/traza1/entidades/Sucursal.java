package traza1.entidades;

import conexion.ArticuloPorSucursal;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString(exclude = "empresa")
@SuperBuilder
public class Sucursal {
    private Long id;
    private String nombre;
    private LocalTime horarioApertura;
    private LocalTime horarioCierre;
    private boolean esCasaMatriz;
    private Domicilio domicilio;
    private Empresa empresa;

    @Builder.Default
    private Set<ArticuloPorSucursal> articulosPorSucursal = new HashSet<>();
}
