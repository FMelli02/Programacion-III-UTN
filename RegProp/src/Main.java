public class Main {
    public static void main(String[] args) {
        System.out.println("Hola Mundo");
        RegistroPropiedad registro1 = RegistroPropiedad.builder()
                .idRegistro(1)
                .nombreRegistro("Mendoza Capital")
                .build();

        //Crear un lote
        Lote lote1 = Lote.builder()
                .idPadron(123)
                .domicilio("suipacha 239")
                .superficie(100.0)
                .build();

        //Registrar el lote en el registro
        registro1.registrarLote(lote1, "08/08/2025");

        //Crear un Edificio
        Edificio edificio1 = Edificio.builder()
                .idEdificio(1)
                .nombre("Presidente")
                .superficieConstruida(50.0)
                .build();

        //Construir el edificio en el lote
        registro1.construirEdificio(lote1, edificio1);

        //Crear un lote 2
        Lote lote2 = Lote.builder()
                .idPadron(222)
                .domicilio("Rodriguez 50")
                .superficie(400.0)
                .build();

        registro1.registrarLote(lote2, "18/08/2025");

        //Intentar construir otro edificio (debería fallar)
        Edificio edificio2 = new Edificio(2, "Otro Edificio", 60.0);
        registro1.construirEdificio(lote2, edificio2);

        //Imprimir el estado del lote
        //System.out.println(lote1);

        registro1.getEscrituras();
    }
}
