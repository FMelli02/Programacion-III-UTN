package entidades;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "pais")
public class Provincia {
    private String nombre;
    @Builder.Default
    private Set<Localidad> localidades = new HashSet<>();
    private Pais pais;
}
