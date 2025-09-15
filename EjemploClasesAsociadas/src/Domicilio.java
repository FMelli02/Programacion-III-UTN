public class Domicilio {
    private String calle;
    private String ciudad;
    private Persona persona;

    public Domicilio(String calle, String ciudad, Persona persona) {
        this.calle = calle;
        this.ciudad = ciudad;
        this.persona = persona;
    }

    @Override
    public String toString() {
        return "Domicilio[calle=" + calle + ", ciudad=" + ciudad + "]";
    }
}
