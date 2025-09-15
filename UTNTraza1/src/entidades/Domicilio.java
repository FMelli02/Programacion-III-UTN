package entidades;

import lombok.*;

@Getter
@Setter
@ToString(exclude = "localidad")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Domicilio {
    private String calle;
    private Integer numero;
    private Integer cp;
    private Localidad localidad;
}
