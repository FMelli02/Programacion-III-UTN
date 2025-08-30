import lombok.*;

@Data
@Builder
@AllArgsConstructor
@ToString(exclude = "residente")
public class Domicilio {
    private String calle;
    private int numero;
    // No necesitamos una referencia a Persona para el propósito del toString()
    // Aunque la tuviéramos para otra lógica, no la usaríamos en este método
    private Persona residente;

}