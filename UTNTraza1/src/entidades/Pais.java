package entidades;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "provincias")
public class Pais {
    private String nombre;
    @Builder.Default
    private Set<Provincia> provincias = new HashSet<>();
}
