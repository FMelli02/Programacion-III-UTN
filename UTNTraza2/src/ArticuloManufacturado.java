import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Data
@SuperBuilder(toBuilder=true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "articulosManufacturadosDetalles")
@EqualsAndHashCode(callSuper = true)
public class ArticuloManufacturado extends Articulo {
    private String descripcion;
    private Integer tiempoEstimadoMinutos;
    private String preparacion;
    @Builder.Default
    private Set<ArticuloManufacturadoDetalle> articulosManufacturadosDetalles = new HashSet<>();
}
