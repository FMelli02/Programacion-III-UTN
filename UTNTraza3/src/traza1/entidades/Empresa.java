package traza1.entidades;

import lombok.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString(exclude = "sucursales")
@NoArgsConstructor
public class Empresa {
    private Long id;
    private String nombre;
    private String razonSocial;
    private Integer cuit;
    private String logo;

    @Builder.Default
    private Set<Sucursal> sucursales = new HashSet<>();
}
