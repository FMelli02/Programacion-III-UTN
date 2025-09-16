package traza2;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class ArticuloInsumo extends Articulo{
    private Double precioCompra;
    private Integer stockActual;
    private Integer stockMaximo;
    private Boolean esParaElaborar;
}

