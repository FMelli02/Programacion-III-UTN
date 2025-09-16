package traza2;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Categoria {
    private String denominacion;
    private Long id;
}

