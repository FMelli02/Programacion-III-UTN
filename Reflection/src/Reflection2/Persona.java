package Reflection2;

public class Persona {
    private String name;

    public Persona(String name) {
        this.name = name;
    }

    private void sayHello() {
        System.out.println("Hello " + name);
    }
}
