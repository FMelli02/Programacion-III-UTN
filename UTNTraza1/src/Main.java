import entidades.*;
import repositorio.InMemoryRepository;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        //Crear un País
        Pais argentina = Pais.builder()
                .nombre("Argentina")
                .build();

        //Crear una provincia relacionada con el País
        Provincia buenosAires = Provincia.builder()
                .nombre("Buenos Aires")
                .pais(argentina)
                .build();

        //Crear 1 localidad relacionada con BsAs
        Localidad caba = Localidad.builder()
                .nombre("Caba")
                .provincia(buenosAires)
                .build();

        //Crear un domicilio para Caba
        Domicilio domicilio1 = Domicilio.builder()
                .calle("Bartolomé Mitre")
                .numero(4065)
                .cp(1001)
                .localidad(caba)
                .build();

        //Crear otra localidad de BsAs
        Localidad laPlata = Localidad.builder()
                .nombre("La Plata")
                .provincia(buenosAires)
                .build();

        //Crear un domicilio para La Plata
        Domicilio domicilio2 = Domicilio.builder()
                .calle("C. 10")
                .numero(1076)
                .cp(1904)
                .localidad(laPlata)
                .build();

        //Crear otra Provincia y relacionarla con el País
        Provincia cordoba= Provincia.builder()
                .nombre("Cordoba")
                .pais(argentina)
                .build();

        //Crear una localidad de Córdoba
        Localidad cordobaCapital = Localidad.builder()
                .nombre("Córdoba Capital")
                .provincia(cordoba)
                .build();

        //Crear un domicilio para Córdoba Capital
        Domicilio domicilio3 = Domicilio.builder()
                .calle("Rondeau")
                .numero(134)
                .cp(5014)
                .localidad(cordobaCapital)
                .build();

        //Crar otra localidad de Córdoba
        Localidad villaCarlosPaz = Localidad.builder()
                .nombre("Villa Carlos Paz")
                .provincia(cordoba)
                .build();

        //Crear un domicilio para Villa Carlos Paz
        Domicilio domicilio4 = Domicilio.builder()
                .calle("Los Tamarindos")
                .numero(235)
                .cp(5152)
                .localidad(villaCarlosPaz)
                .build();

        //Crear la Sucursal1 para Caba
        Sucursal sucursal1 = Sucursal.builder()
                .id(1L)
                .nombre("Sucursal Caba")
                .horarioApertura(LocalTime.of(9, 0))
                .horarioCierre(LocalTime.of(17, 0))
                .es_Casa_Matriz(true)
                .domicilio(domicilio1)
                .build();

        //Crear la Sucursal2 para La Plata
        Sucursal sucursal2 = Sucursal.builder()
                .id(2L)
                .nombre("Sucursal La Plata")
                .horarioApertura(LocalTime.of(9, 0))
                .horarioCierre(LocalTime.of(18, 0))
                .es_Casa_Matriz(false)
                .domicilio(domicilio2)
                .build();

        //Crear la Sucursal3 para Córdoba Capital
        Sucursal sucursal3 = Sucursal.builder()
                .id(3L)
                .nombre("Sucursal Córdoba Capital")
                .horarioApertura(LocalTime.of(8, 30))
                .horarioCierre(LocalTime.of(17, 0))
                .es_Casa_Matriz(true)
                .domicilio(domicilio3)
                .build();

        //Crear la Sucursal4 para Villa Carlos Paz
        Sucursal sucursal4 = Sucursal.builder()
                .id(4L)
                .nombre("Sucursal Villa Carlos Paz")
                .horarioApertura(LocalTime.of(8, 0))
                .horarioCierre(LocalTime.of(17, 0))
                .es_Casa_Matriz(false)
                .domicilio(domicilio4)
                .build();

        //Empresa 1 relacionada con sucursal 1 y 2
        Empresa empresa1 = Empresa.builder()
                .id(1L)
                .nombre("Empresa 1")
                .razonSocial("Empresa 1, S.A")
                .cuit(231532111)
                .logo("Logo de Empresa 1.png")
                .sucursales(new HashSet<>(Set.of(sucursal1, sucursal2)))
                .build();

        //Empresa 2 relacionada con sucursal 3 y 4
        Empresa empresa2 = Empresa.builder()
                .id(2L)
                .nombre("Empresa 2")
                .razonSocial("Empresa 2, S.R.L")
                .cuit(245222645)
                .logo("Logo de Empresa 2.png")
                .sucursales(new HashSet<>(Set.of(sucursal3, sucursal4)))
                .build();

        InMemoryRepository<Empresa> repo = new InMemoryRepository<>();
        repo.save(empresa1);
        repo.save(empresa2);

        //Mostrar todas las empresas
        System.out.println("Empresas:");
        List<Empresa> mostrarEmpresas = repo.findAll();
        mostrarEmpresas.forEach(System.out::println);

        //Buscar empresa por Id
        System.out.println("Empresa 1 por ID:");
        Optional<Empresa> empresaID = repo.findById(1L);
        empresaID.ifPresent(empresa -> System.out.println("Empresa encontrada: " + empresa));

        //Buscar empresa por Nombre
        System.out.println("Empresa 2 por Nombre:");
        List<Empresa> empresaNombre = repo.genericFindByField("nombre", "Empresa 1");
        empresaNombre.forEach(System.out::println);

        //Modificar datos por ID
        System.out.println("Empresa 1 modificada por ID:");
        Empresa empresaActualizada = Empresa.builder()
                .id(1L)
                .nombre("Empresa 1")
                .razonSocial("Empresa 1, S.A.S")
                .cuit(1144253365)
                .logo("Logo de Empresa 1.png")
                .sucursales(empresa1.getSucursales())
                .build();

        repo.genericUpdate(1L, empresaActualizada);
        Optional<Empresa> empresaVerificada = repo.findById(1L);
        empresaVerificada.ifPresent(e -> System.out.println("Empresa actualizada: " + e));


        //Eliminar empresa por ID
        System.out.println("Empresa eliminada por ID");
        Optional<Empresa> empresaEliminar = repo.genericDelete(2L);
        empresaEliminar.ifPresent(empresa -> System.out.println("Empresa eliminada: " + empresa));
    }
}
