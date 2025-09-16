package conexion;

import lombok.*;
import traza1.entidades.Sucursal;
import traza2.Articulo;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticuloStock {
    private Long id;
    private int stockActual;
    private Double precioVentaSucursal;
    private Articulo articulo;
    private Sucursal sucursal;
}
