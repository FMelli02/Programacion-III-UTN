import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//Debo colocar esto porque me genera un estado cíclico
@ToString(exclude = "registro")
@EqualsAndHashCode(exclude = "registro")
public class Escritura {
    private int numeroEscritura;
    private Lote lote;
    private RegistroPropiedad registro;
    private String fechaEscritura;
}
