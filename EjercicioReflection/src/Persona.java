public class Persona {
    private String nombre;
    private String apellido;
    private int edad;

    public Persona() {
    }
    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }
    public Persona(String nombre, String apellido, int edad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }

    private void comer() {
        System.out.println("Las personas comen por la boca");
    }

    private void saludar() {
        System.out.println("Las personas saludan con las manos");
    }
}
