package traza1.entidades;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "provincia")
public class Localidad {
    private String nombre;
    private Provincia provincia;
}