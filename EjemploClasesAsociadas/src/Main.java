public class Main {
    public static void main(String[] args) {
        Domicilio d1 = new Domicilio("Loria Este", "Godoy Cruz", null);
        Persona p1 = new Persona("Franco", 23, d1);

        System.out.println(p1.toString());
    }
}
