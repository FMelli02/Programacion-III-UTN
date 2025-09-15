import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        try {
            //1. Obtener la Clase Persona usando Reflexión
            Class<?> clazz = Class.forName("Persona");
            System.out.println(clazz.getSimpleName());

            //2. Listar Constructores
            Constructor<?>[] constructores = clazz.getConstructors();
            System.out.println("\nConstructores:");
            for (Constructor<?> constructor : constructores) {
                System.out.println(constructor.getName());
            }

            //3. Listar Campos
            Field[] campos = clazz.getDeclaredFields();
            System.out.println("\nCampos:");
            for (Field campo : campos) {
                System.out.println(campo.getName());
            }

            //4. Listar Métodos
            Method[] metodos = clazz.getDeclaredMethods();
            System.out.println("\nMétodos:");
            for (Method metodo : metodos) {
                System.out.println(metodo.getName());
            }

            //5. Crear una Instancia de Persona
            Object instancia = clazz.getDeclaredConstructor().newInstance();
            System.out.println("\nInstancia creada con constructor vacío.");

            //6. Modificar Campos Privados
            Field campoNombre = clazz.getDeclaredField("Juan");
            Field campoApellido = clazz.getDeclaredField("Pérez");
            Field campoEdad = clazz.getDeclaredField("30");
            campoNombre.setAccessible(true);
            campoApellido.setAccessible(true);
            campoEdad.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
