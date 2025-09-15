import conexion.ArticuloPorSucursal;
import traza1.entidades.*;
import traza2.*;

import java.time.LocalTime;
import java.util.*;

public class Main {

    private static final Map<Long, ArticuloManufacturado> manufacturadosDB = new HashMap<>();
    private static final List<Categoria> categoriasDB = new ArrayList<>();
    private static final List<ArticuloInsumo> insumosDB = new ArrayList<>();

    public static void main(String[] args) {
        // Creamos la empresa principal
        Empresa miPizzeria = Empresa.builder().id(1L).nombre("Timba").razonSocial("S.A").cuil(20203442352L).build();
        //Creamos el Pais
        Pais argentina = Pais.builder().id(1L).nombre("Argentina").build();
        //Creamos la Provincia
        Provincia mendoza = Provincia.builder().id(1L).nombre("Mendoza").pais(argentina).build();
        //Creamos la Localidad
        Localidad ciudad = Localidad.builder().id(1L).nombre("Ciudad").provincia(mendoza).build();
        // Creamos los domicilios
        Domicilio domicilioCiudad = Domicilio.builder().id(101L).calle("Colón").numero(1026).cp(5500)
                .piso(null).nroDpto(null).localidad(ciudad).build();
        Domicilio domicilioChacras = Domicilio.builder().id(102L).calle("Darragueira").numero(437).cp(5502)
                .piso(null).nroDpto(null).localidad(ciudad).build();
        //Creamos La Sucursal
        Sucursal sucursalChacras = Sucursal.builder().id(1L).nombre("La Timbera").horarioApertura(LocalTime.of(20, 0)).horarioCierre(LocalTime.of(1, 0))
                .esCasaMatriz(true).domicilio(domicilioChacras).empresa(miPizzeria).build();
        Sucursal sucursalCiudad = Sucursal.builder().id(2L).nombre("La Timbera").horarioApertura(LocalTime.of(19, 30)).horarioCierre(LocalTime.of(1, 30))
                .esCasaMatriz(false).domicilio(domicilioCiudad).empresa(miPizzeria).build();
        //Agragamos las sucursales
        miPizzeria.setSucursales(new HashSet<>(Set.of(sucursalChacras, sucursalCiudad)));

        //Creamos las categorías
        Categoria pizzas = Categoria.builder().id(1L).denominacion("Pizzas").build();
        Categoria sandwich = Categoria.builder().id(2L).denominacion("Sandwich").build();
        Categoria lomos = Categoria.builder().id(3L).denominacion("Lomos").build();
        Categoria insumos = Categoria.builder().id(4L).denominacion("Insumos").build();
        categoriasDB.addAll(List.of(pizzas, sandwich, lomos, insumos));
        //Creamos las unidades de medida
        UnidadMedida kg = UnidadMedida.builder().id(1L).denominacion("Kilogramos").build();
        UnidadMedida lt = UnidadMedida.builder().id(2L).denominacion("Litros").build();
        UnidadMedida gr = UnidadMedida.builder().id(3L).denominacion("Gramos").build();
        UnidadMedida unidad = UnidadMedida.builder().id(4L).denominacion("Unidad").build();
        //Creamos Artículos Insumos
        ArticuloInsumo sal = ArticuloInsumo.builder()
                .id(101L).denominacion("Sal").precioVenta(15.0).unidadMedida(gr).categoria(insumos)
                .precioCompra(10.0).stockActual(50).stockMaximo(100).esParaElaborar(true)
                .build();
        ArticuloInsumo aceite = ArticuloInsumo.builder()
                .id(102L).denominacion("Aceite").precioVenta(40.0).unidadMedida(lt).categoria(insumos)
                .precioCompra(25.0).stockActual(30).stockMaximo(80).esParaElaborar(true)
                .build();
        ArticuloInsumo carne = ArticuloInsumo.builder()
                .id(103L).denominacion("Carne").precioVenta(200.0).unidadMedida(kg).categoria(insumos)
                .precioCompra(150.0).stockActual(100).stockMaximo(200).esParaElaborar(true)
                .build();
        ArticuloInsumo harina = ArticuloInsumo.builder()
                .id(104L).denominacion("Harina").precioVenta(10.0).unidadMedida(kg).categoria(insumos)
                .precioCompra(5.0).stockActual(200).stockMaximo(500).esParaElaborar(true)
                .build();
        insumosDB.addAll(List.of(sal, aceite, carne, harina));
        //Creamos las Imágenes
        Imagen imgPizza1 = Imagen.builder().id(1L).denominacion("HawainaPizza1.jpg").build();
        Imagen imgPizza2 = Imagen.builder().id(2L).denominacion("HawainaPizza2.jpg").build();
        Imagen imgPizza3 = Imagen.builder().id(3L).denominacion("HawainaPizza3.jpg").build();
        Imagen imgLomo1 = Imagen.builder().id(4L).denominacion("LomoCompleto1.jpg").build();
        Imagen imgLomo2 = Imagen.builder().id(5L).denominacion("LomoCompleto2.jpg").build();
        Imagen imgLomo3 = Imagen.builder().id(6L).denominacion("LomoCompleto3.jpg").build();
        //Creamos Detalles de Artículos Manufacturados
        ArticuloManufacturadoDetalle detallePizza1 = ArticuloManufacturadoDetalle.builder().id(1L).cantidad(1).articuloInsumo(sal).build();
        ArticuloManufacturadoDetalle detallePizza2 = ArticuloManufacturadoDetalle.builder().id(2L).cantidad(2).articuloInsumo(harina).build();
        ArticuloManufacturadoDetalle detallePizza3 = ArticuloManufacturadoDetalle.builder().id(3L).cantidad(1).articuloInsumo(aceite).build();
        ArticuloManufacturadoDetalle detalleLomo1 = ArticuloManufacturadoDetalle.builder().id(4L).cantidad(1).articuloInsumo(sal).build();
        ArticuloManufacturadoDetalle detalleLomo2 = ArticuloManufacturadoDetalle.builder().id(5L).cantidad(1).articuloInsumo(aceite).build();
        ArticuloManufacturadoDetalle detalleLomo3 = ArticuloManufacturadoDetalle.builder().id(6L).cantidad(1).articuloInsumo(carne).build();
        //Creamos Productos Manufacturados
        ArticuloManufacturado pizzaHawaiana = ArticuloManufacturado.builder().id(201L).denominacion("Pizza Hawaiana").precioVenta(15500.0).unidadMedida(unidad).categoria(pizzas)
                .descripcion("Pizza muzarella con ananá").tiempoEstimadoMinutos(20).preparacion("Estirar, poner salsa, queso y ananá. Luego hornear").build();

        pizzaHawaiana.getImagenes().addAll(Set.of(imgPizza1, imgPizza2, imgPizza3));
        pizzaHawaiana.getArticulosManufacturadosDetalles().addAll(Set.of(detallePizza1, detallePizza2, detallePizza3));

        ArticuloManufacturado lomoCompleto = ArticuloManufacturado.builder().id(202L).denominacion("Lomo Completo").precioVenta(22000.0).unidadMedida(unidad).categoria(lomos)
                .descripcion("El de la casa. Tomate, Lechuga, Jamón, Queso y Huevo con una salsa Timbera").tiempoEstimadoMinutos(25).preparacion("Cocinar la carne, armar el sandwich con todo.").build();

        lomoCompleto.getImagenes().addAll(Set.of(imgLomo1, imgLomo2, imgLomo3));
        lomoCompleto.getArticulosManufacturadosDetalles().addAll(Set.of(detalleLomo1, detalleLomo2, detalleLomo3));

        //Guardamos los productos finales en la BD
        manufacturadosDB.put(pizzaHawaiana.getId(), pizzaHawaiana);
        manufacturadosDB.put(lomoCompleto.getId(), lomoCompleto);

        //Creamos los Articulos por Sucursales
        ArticuloPorSucursal pizzaCiudad = ArticuloPorSucursal.builder().articulos(Set.of(pizzaHawaiana)).stockActualEnSucursal(100).precioEspecialEnSucursal(14000.0).sucursal(sucursalCiudad).build();
        ArticuloPorSucursal lomoCiudad = ArticuloPorSucursal.builder().articulos(Set.of(lomoCompleto)).stockActualEnSucursal(50).precioEspecialEnSucursal(22000.0).sucursal(sucursalCiudad).build();
        ArticuloPorSucursal pizzaChacras = ArticuloPorSucursal.builder().articulos(Set.of(pizzaHawaiana)).stockActualEnSucursal(75).precioEspecialEnSucursal(15500.0).sucursal(sucursalChacras).build();
        ArticuloPorSucursal lomoChacras = ArticuloPorSucursal.builder().articulos(Set.of(lomoCompleto)).stockActualEnSucursal(110).precioEspecialEnSucursal(20000.0).sucursal(sucursalChacras).build();


    }
}
