package entidades;

import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@ToString(exclude = "empresa")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sucursal {
    private Long id;
    private String nombre;
    private LocalTime horarioApertura;
    private LocalTime horarioCierre;
    private boolean es_Casa_Matriz;
    private Empresa empresa;
    private Domicilio domicilio;
}
