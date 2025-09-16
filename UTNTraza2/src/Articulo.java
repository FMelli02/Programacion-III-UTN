import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString(exclude = "imagenes")
@SuperBuilder(toBuilder=true)
@AllArgsConstructor
@NoArgsConstructor
public abstract class Articulo {
    protected String denominacion;
    protected Double precioVenta;
    protected Long id;
    protected UnidadMedida unidadMedida;
    private Categoria categoria;
    @Builder.Default
    protected Set<Imagen> imagenes = new HashSet<>();
}
