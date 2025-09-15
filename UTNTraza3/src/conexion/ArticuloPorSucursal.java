package conexion;

import lombok.*;
import traza1.entidades.Sucursal;
import traza2.Articulo;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticuloPorSucursal {
    private int stockActualEnSucursal;
    private Double precioEspecialEnSucursal;
    @Builder.Default
    private Set<Articulo> articulos = new HashSet<>();
    private Sucursal sucursal;
}
