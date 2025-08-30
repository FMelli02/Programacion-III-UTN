import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Persona {
    private String nombre;
    private int edad;
    private Domicilio domicilio;
}
