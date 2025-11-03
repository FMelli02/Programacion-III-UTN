package com.utn.tareas.service;

import com.utn.tareas.model.Prioridad;
import com.utn.tareas.model.Tarea;
import com.utn.tareas.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TareaService {
    private final TareaRepository tareaRepository;

    @Value("${app.nombre}")
    private String nombreApp;

    @Value("${app.max-tareas}")
    private int maxTareas;

    @Value("${app.mostrar-estadisticas}")
    private boolean mostrarEstadisticas;

    public TareaService(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    public Tarea agregarTarea(String descripcion, Prioridad prioridad) {
        if (tareaRepository.obtenerTodas().size() >= maxTareas) {
            throw new RuntimeException(
                    "Límite de tareas (" + maxTareas + ") alcanzado. No se pueden agregar más."
            );
        }

        Tarea nuevaTarea = new Tarea();
        nuevaTarea.setDescripcion(descripcion);
        nuevaTarea.setPrioridad(prioridad);
        nuevaTarea.setCompletada(false);

        return tareaRepository.guardar(nuevaTarea);
    }

    //Devuelve todas las tareas
    public List<Tarea> listarTodas() {
        return tareaRepository.obtenerTodas();
    }

    //Devuelve solo las tareas NO completadas
    public List<Tarea> listarPendientes() {
        return tareaRepository.obtenerTodas().stream()
                .filter(tarea -> !tarea.isCompletada())
                .collect(Collectors.toList());
    }

    //Devuelve solo las tareas SI completadas
    public List<Tarea> listarCompletadas() {
        return tareaRepository.obtenerTodas().stream()
                .filter(Tarea::isCompletada)
                .collect(Collectors.toList());
    }

    //Busca una tarea por ID y la marca como completada
    public Tarea marcarComoCompletada(Long id) {
        Optional<Tarea> tareaOptional = tareaRepository.buscarPorId(id);

        if (tareaOptional.isPresent()) {
            Tarea tarea = tareaOptional.get();
            tarea.setCompletada(true);

            return tarea;
        } else {
            throw new RuntimeException("No se encontró la tarea con ID: " + id);
        }
    }

    //Genera un reporte simple de estadísticas
    public String obtenerEstadisticas() {
        if (!mostrarEstadisticas) {
            return "Las estadísticas están desactivadas.";
        }

        long total = listarTodas().size();
        long completadas = listarCompletadas().size();
        long pendientes = listarPendientes().size();

        return String.format(
                "Estadísticas: Total de tareas: %d, Completadas: %d, Pendientes: %d",
                total, completadas, pendientes
        );
    }

    //Método para imprimir la configuración cargada
    public void mostrarConfiguracion() {
        System.out.println("--- Configuración de la App ---");
        System.out.println("Nombre: " + nombreApp);
        System.out.println("Max Tareas: " + maxTareas);
        System.out.println("Mostrar Estadísticas: " + mostrarEstadisticas);
        System.out.println("---------------------------------");
    }
}
