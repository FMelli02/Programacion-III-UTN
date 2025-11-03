package com.utn.tareas.repository;

import com.utn.tareas.model.Prioridad;
import com.utn.tareas.model.Tarea;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TareaRepository {
    //Lista en memoria para simular la BD
    private final List<Tarea> tareas = new ArrayList<>();

    //Contador atómico para IDs únicos
    private final AtomicLong contadorId = new AtomicLong(1);

    public TareaRepository() {
        guardar(new Tarea(null, "Hacer el TP de Programación III", false, Prioridad.ALTA));
        guardar(new Tarea(null, "Comprar milanesas para la cena", false, Prioridad.MEDIA));
        guardar(new Tarea(null,"Sacar a pasear al perro", true, Prioridad.BAJA));
    }

    //Obtener todas las tareas
    public List<Tarea> obtenerTodas() {
        return tareas;
    }

    //Buscar una tarea por su ID
    public Optional<Tarea> buscarPorId(Long id) {
        return tareas.stream()
                .filter(tarea -> tarea.getId().equals(id))
                .findFirst();
    }

    //Guardar una nueva tarea
    public Tarea guardar(Tarea tarea) {
        tarea.setId(contadorId.getAndIncrement());
        tareas.add(tarea);
        return tarea;
    }

    //Elimina una tarea por su ID
    public void eliminarPorId(Long id) {
        tareas.removeIf(tarea -> tarea.getId().equals(id));
    }
}
