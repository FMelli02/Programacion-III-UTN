package traza1.entidades;

import conexion.ArticuloStock;
import lombok.*;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = {"empresa", "stockDeArticulos"})
@EqualsAndHashCode(exclude = "stockDeArticulos")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sucursal {
    private Long id;
    private String nombre;
    private LocalTime horarioApertura;
    private LocalTime horarioCierre;
    private boolean es_Casa_Matriz;
    private Empresa empresa;
    private Domicilio domicilio;

    @Builder.Default
    private Set<ArticuloStock> stockDeArticulos = new HashSet<>();
}
