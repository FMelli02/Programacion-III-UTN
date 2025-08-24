package ServiciosyFabrica;

import Dominio.Animal;
import Dominio.ContenedorGenerico;
import Dominio.Veterinario;

public class ClinicaVeterinaria {
    private ContenedorGenerico<Veterinario> staff;
    private ContenedorGenerico<Animal> pacientes;

    public ClinicaVeterinaria(ContenedorGenerico<Veterinario> staff, ContenedorGenerico<Animal> pacientes) {
        this.staff = staff;
        this.pacientes = pacientes;
    }

    public void agregarVeterinario(Veterinario v) {

    }

    public void registrarPaciente(Animal a) {

    }
}
