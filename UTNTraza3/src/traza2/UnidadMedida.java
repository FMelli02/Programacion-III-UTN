package traza2;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UnidadMedida {
    private String denominacion;
    private Long id;
}
