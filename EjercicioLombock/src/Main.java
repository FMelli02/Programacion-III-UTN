public class Main {
    public static void main(String[] args) {


        Domicilio miCasa = Domicilio.builder()
                .calle("Tu calle Famosa")
                .numero(123)
                .build();
        Persona yo = Persona.builder()
                .nombre("Juan")
                .edad(30)
                .domicilio(miCasa)
                .build();

        // Ahora, esta llamada funciona correctamente sin recursividad
        System.out.println(yo);
        // Salida: Persona{nombre='Juan', edad=30, domicilio=Domicilio{calle='Calle Falsa', numero=123}}
    }
}
