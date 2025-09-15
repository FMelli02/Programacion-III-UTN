package Reflection3;

public class Reflection3 {
    public static void main(String[] args) {
        try {
            // Obtener la clase
            Class<?> clazz = Class.forName("java.util.ArrayList");

            // Crear una instancia de la clase
            Object instance = clazz.getDeclaredConstructor().newInstance();

            // Comprobar el tipo de la instancia
            if (instance instanceof java.util.ArrayList) {
                System.out.println("Instancia creada exitosamente de tipo ArrayList");
            } else {
                System.out.println("La instancia no es de tipo ArrayList");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}