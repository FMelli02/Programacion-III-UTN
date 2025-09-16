package traza2;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ArticuloManufacturadoDetalle {
    private Integer cantidad;
    private Long id;
    private ArticuloInsumo articuloInsumo;
}

