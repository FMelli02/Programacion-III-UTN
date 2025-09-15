package traza2;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "articulos")
public class Categoria {
    private String denominacion;
    private Long id;
    @Builder.Default
    private Set<Articulo> articulos = new HashSet<>();
}
