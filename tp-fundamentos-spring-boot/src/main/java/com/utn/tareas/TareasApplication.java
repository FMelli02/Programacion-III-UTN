package com.utn.tareas;

import com.utn.tareas.model.Prioridad;
import com.utn.tareas.service.MensajeService;
import com.utn.tareas.service.TareaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TareasApplication implements CommandLineRunner {
	private final TareaService tareaService;
	private final MensajeService mensajeService;

	public TareasApplication(TareaService tareaService, MensajeService mensajeService) {
		this.tareaService = tareaService;
		this.mensajeService = mensajeService;
	}

	public static void main(String[] args) {
		SpringApplication.run(TareasApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//1. Mostrar mensaje de bienvenida
		System.out.println("======================================================");
		System.out.println(mensajeService.mostrarBienvenida());
		System.out.println("======================================================");

		//2. Mostrar la configuración actual
		tareaService.mostrarConfiguracion();
		System.out.println("======================================================");

		//3. Listar todas las tareas iniciales
		System.out.println("\n*** Tareas iniciales (Hardcodeadas) ***");
		tareaService.listarTodas().forEach(System.out::println);
		System.out.println("------------------------------------------------------");

		//4. Agregar una nueva tarea
		try {
			System.out.println("\nAgregando nueva tarea 'Estudiar Spring'...");
			tareaService.agregarTarea("Estudiar Spring Boot a full", Prioridad.ALTA);
		} catch (Exception e) {
			System.out.println("ERROR al agregar tarea: " + e.getMessage());
		}

		//5. Listar tareas pendientes
		System.out.println("\n*** Tareas pendientes ***");
		tareaService.listarPendientes().forEach(System.out::println);
		System.out.println("------------------------------------------------------");

		//6. Marcar una tarea como completada
		try {
			Long idParaCompletar = 2L;
			System.out.println("\nMarcando tarea ID " + idParaCompletar + " como completada...");
			tareaService.marcarComoCompletada(idParaCompletar);
		} catch (Exception e) {
			System.out.println("ERROR al marcar tarea: " + e.getMessage());
		}

		//7. Mostrar estadísticas
		System.out.println("\n*** Estadísticas ***");
		tareaService.obtenerEstadisticas();
		System.out.println("------------------------------------------------------");

		//8. Listar tareas completadas
		System.out.println("\n*** Tareas completadas ***");
		tareaService.listarCompletadas().forEach(System.out::println);
		System.out.println("------------------------------------------------------");

		System.out.println("\nIntentando sobrepasar el límite de tareas...");
		try {
			//Intentamos agregar 10 tareas más
			for (int i = 0; i < 10; i++) {
				System.out.println("Agregando tarea extra " + (i+1) + "...");
				tareaService.agregarTarea("Tarea de relleno " + i, Prioridad.BAJA);
			}
		} catch (Exception e) {
			System.out.println("===> ERROR ESPERADO: " + e.getMessage());
		}

		//9. Mostrar mensaje de despedida
		System.out.println("======================================================");
		System.out.println(mensajeService.mostrarDespedida());
		System.out.println("======================================================");
	}
}
