package Genericos3;

public interface Contenedor<T> {
    void agregar(T elemento);
    T obtener();
}
