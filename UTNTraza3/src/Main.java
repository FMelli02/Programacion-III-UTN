import conexion.ArticuloStock;
import traza1.entidades.*;
import traza2.*;

import java.time.LocalTime;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        System.out.println("CREANDO ENTIDADES TRAZA 1...");
        // Creamos País y Provincias
        Pais pais = Pais.builder().nombre("Argentina").build();
        Provincia bsas = Provincia.builder().nombre("Buenos Aires").pais(pais).build();
        Provincia cordoba = Provincia.builder().nombre("Córdoba").pais(pais).build();

        // Creamos Localidades y Domicilios
        Localidad caba = Localidad.builder().nombre("CABA").provincia(bsas).build();
        Domicilio domCaba = Domicilio.builder().calle("Av. Corrientes").numero(123).cp(1043).localidad(caba).build();

        Localidad laPlata = Localidad.builder().nombre("La Plata").provincia(bsas).build();
        Domicilio domLaPlata = Domicilio.builder().calle("Calle 7").numero(777).cp(1900).localidad(laPlata).build();

        Localidad capital = Localidad.builder().nombre("Capital").provincia(cordoba).build();
        Domicilio domCapital = Domicilio.builder().calle("Salta").numero(82).cp(1010).localidad(capital).build();

        Localidad villaCarlozPaz = Localidad.builder().nombre("Villa Carlos Paz").provincia(cordoba).build();
        Domicilio domCarlosPaz = Domicilio.builder().calle("Los Tamarindos").numero(235).cp(5152).localidad(villaCarlozPaz).build();

        // Creamos Sucursales
        Sucursal sucCaba = Sucursal.builder().id(1L).nombre("Sucursal CABA").horarioApertura(LocalTime.of(9,0))
                .horarioCierre(LocalTime.of(20,0)).domicilio(domCaba).es_Casa_Matriz(true).build();

        Sucursal sucLaPlata = Sucursal.builder().id(2L).nombre("Sucursal La Plata").horarioApertura(LocalTime.of(10,0))
                .horarioCierre(LocalTime.of(21,0)).es_Casa_Matriz(false).domicilio(domLaPlata).build();

        Sucursal sucCapital = Sucursal.builder().id(3L).nombre("Sucursal Córdoba Capital").horarioApertura(LocalTime.of(8, 30))
                .horarioCierre(LocalTime.of(17, 0)).es_Casa_Matriz(true).domicilio(domCarlosPaz).build();

        Sucursal sucVillaCarlosPaz = Sucursal.builder().id(4L).nombre("Sucursal Villa Carlos Paz").horarioApertura(LocalTime.of(8, 0))
                .horarioCierre(LocalTime.of(17, 0)).es_Casa_Matriz(false).domicilio(domCarlosPaz).build();
        // Creamos las Empresas
        Empresa empresa1 = Empresa.builder().id(1L).nombre("La Timbera BSAS").razonSocial("La Timbera S.A.").cuit(2054274460).build();
        empresa1.getSucursales().addAll(Set.of(sucCaba, sucLaPlata));

        Empresa empresa2 = Empresa.builder().id(2L).nombre("La Timbera Córdoba").razonSocial("La Timbera S.A.").cuit(2068624971).build();
        empresa1.getSucursales().addAll(Set.of(sucCapital, sucVillaCarlosPaz));

        // Asignamos las empresas a las sucursales
        sucCaba.setEmpresa(empresa1);
        sucLaPlata.setEmpresa(empresa1);
        sucCapital.setEmpresa(empresa2);
        sucVillaCarlosPaz.setEmpresa(empresa2);

        System.out.println("\nCREANDO ENTIDADES TRAZA 2...");
        // Creamos Categorías y Unidades
        Categoria pizzas = Categoria.builder().id(1L).denominacion("Pizzas").build();
        Categoria sandwich = Categoria.builder().id(2L).denominacion("Sandwich").build();
        Categoria lomos = Categoria.builder().id(3L).denominacion("Lomos").build();
        Categoria insumos = Categoria.builder().id(4L).denominacion("Insumos").build();

        UnidadMedida kilogramos = UnidadMedida.builder().id(1L).denominacion("Kg").build();
        UnidadMedida litros = UnidadMedida.builder().id(2L).denominacion("Lt").build();
        UnidadMedida gramos = UnidadMedida.builder().id(3L).denominacion("gr").build();
        UnidadMedida unidad = UnidadMedida.builder().id(4L).denominacion("Unidad").build();

        // Creamos los insumos
        ArticuloInsumo sal = ArticuloInsumo.builder()
                .id(101L).denominacion("Sal").precioVenta(15.0).unidadMedida(gramos).categoria(insumos)
                .precioCompra(10.0).stockActual(50).stockMaximo(100).esParaElaborar(true)
                .build();

        ArticuloInsumo aceite = ArticuloInsumo.builder()
                .id(102L).denominacion("Aceite").precioVenta(30.0).unidadMedida(litros).categoria(insumos)
                .precioCompra(20.0).stockActual(30).stockMaximo(150).esParaElaborar(true)
                .build();

        ArticuloInsumo carne = ArticuloInsumo.builder()
                .id(103L).denominacion("Carne").precioVenta(150.0).unidadMedida(kilogramos).categoria(insumos)
                .precioCompra(60.0).stockActual(100).stockMaximo(200).esParaElaborar(true)
                .build();

        ArticuloInsumo harina = ArticuloInsumo.builder()
                .id(104L).denominacion("Harina").precioVenta(10.0).unidadMedida(kilogramos).categoria(insumos)
                .precioCompra(2.0).stockActual(60).stockMaximo(300).esParaElaborar(true)
                .build();

        // Creamos las imagenes
        Imagen imagen1 = Imagen.builder().id(1L).denominacion("HawwaianaPizza1.jpg").build();
        Imagen imagen2 = Imagen.builder().id(2L).denominacion("HawwaianaPizza2.jpg").build();
        Imagen imagen3 = Imagen.builder().id(3L).denominacion("HawwaianaPizza3.jpg").build();
        Imagen imagen4 = Imagen.builder().id(4L).denominacion("LomoCompletoLomo1.jpg").build();
        Imagen imagen5 = Imagen.builder().id(5L).denominacion("LomoCompletoLomo2.jpg").build();
        Imagen imagen6 = Imagen.builder().id(6L).denominacion("LomoCompletoLomo3.jpg").build();

        // Creamos los detalles de Articulos Manufacturados
        ArticuloManufacturadoDetalle detallePizzaHawaiana1 = ArticuloManufacturadoDetalle.builder()
                .id(1L).cantidad(1).articuloInsumo(sal).build();
        ArticuloManufacturadoDetalle detallePizzaHawaiana2 = ArticuloManufacturadoDetalle.builder()
                .id(2L).cantidad(2).articuloInsumo(harina).build();
        ArticuloManufacturadoDetalle detallePizzaHawaiana3 = ArticuloManufacturadoDetalle.builder()
                .id(3L).cantidad(1).articuloInsumo(aceite).build();

        ArticuloManufacturadoDetalle detalleLomoCompleto1 = ArticuloManufacturadoDetalle.builder()
                .id(4L).cantidad(1).articuloInsumo(sal).build();
        ArticuloManufacturadoDetalle detalleLomoCompleto2 = ArticuloManufacturadoDetalle.builder()
                .id(5L).cantidad(1).articuloInsumo(aceite).build();
        ArticuloManufacturadoDetalle detalleLomoCompleto3 = ArticuloManufacturadoDetalle.builder()
                .id(6L).cantidad(1).articuloInsumo(carne).build();

        // Creamos los productos manufacturados y los relacionamos a su imagen
        ArticuloManufacturado pizzaHawaiana = ArticuloManufacturado.builder()
                .id(201L).denominacion("Pizza Hawaiana").precioVenta(15500.0).unidadMedida(unidad)
                .categoria(pizzas).descripcion("Pizza con ananá").tiempoEstimadoMinutos(25)
                .preparacion("Estirar, poner la salsa, queso y ananá. Luego hornear.").build();

        pizzaHawaiana.getImagenes().addAll(Set.of(imagen1, imagen2, imagen3));
        pizzaHawaiana.getArticulosManufacturadosDetalles().addAll(Set.of(detallePizzaHawaiana1, detallePizzaHawaiana2, detallePizzaHawaiana3));

        ArticuloManufacturado lomoCompleto = ArticuloManufacturado.builder()
                .id(202L).denominacion("Lomo Completo").precioVenta(22000.0).unidadMedida(unidad)
                .categoria(lomos).descripcion("El tradicional de toda la vida").tiempoEstimadoMinutos(30)
                .preparacion("Cocinar la carne y armar el sandwich").build();

        lomoCompleto.getImagenes().addAll(Set.of(imagen4, imagen5, imagen6));
        lomoCompleto.getArticulosManufacturadosDetalles().addAll(Set.of(detalleLomoCompleto1, detalleLomoCompleto2, detalleLomoCompleto3));

        System.out.println("\nCONECTANDO ENTIDADES TRAZA 1 Y 2...");

        // Asignamos stock de Pizza a la sucursal de CABA
        ArticuloStock stockPizzaCABA = ArticuloStock.builder()
                .articulo(pizzaHawaiana).sucursal(sucCaba)
                .stockActual(50).precioVentaSucursal(12500.0).build();

        // Asignamos stock de Lomo Completo a la sucursal de CABA
        ArticuloStock stockLomoCABA = ArticuloStock.builder()
                .articulo(lomoCompleto).sucursal(sucCaba)
                .stockActual(100).precioVentaSucursal(2950.0).build();

        // Asignamos stock de Lomo Completo a la sucursal de La Plata
        ArticuloStock stockLomoLaPlata = ArticuloStock.builder()
                .articulo(lomoCompleto).sucursal(sucLaPlata)
                .stockActual(80).precioVentaSucursal(2800.0).build();

        // Actualizamos las listas en ambos lados
        pizzaHawaiana.getStockPorSucursal().add(stockPizzaCABA);
        lomoCompleto.getStockPorSucursal().addAll(Set.of(stockLomoCABA, stockLomoLaPlata));

        sucCaba.getStockDeArticulos().addAll(Set.of(stockPizzaCABA, stockLomoCABA));
        sucLaPlata.getStockDeArticulos().add(stockLomoLaPlata);

        // =======================================================================
        // PASO 4: DEMOSTRACIÓN DE PODER
        // =======================================================================
        System.out.println("\n============ MOSTRANDO INTEGRACIÓN DE ENTIDADES ============");

        mostrarStockPorSucursal(sucCaba);
        mostrarStockPorSucursal(sucLaPlata);

        mostrarDondeSeVendeArticulo(pizzaHawaiana);
        mostrarDondeSeVendeArticulo(lomoCompleto);
    }

    public static void mostrarStockPorSucursal(Sucursal sucursal) {
        System.out.println("\n=======================================================");
        System.out.println("Stock de la Sucursal: '" + sucursal.getNombre() + "' en " + sucursal.getDomicilio().getLocalidad().getNombre());
        System.out.println("-------------------------------------------------------");
        if (sucursal.getStockDeArticulos().isEmpty()) {
            System.out.println("Esta sucursal no tiene artículos asignados.");
        } else {
            for (ArticuloStock stock : sucursal.getStockDeArticulos()) {
                System.out.println("- Artículo: " + stock.getArticulo().getDenominacion());
                System.out.println("  Stock: " + stock.getStockActual() + " " + stock.getArticulo().getUnidadMedida().getDenominacion());
                System.out.println("  Precio en Sucursal: $" + stock.getPrecioVentaSucursal());
            }
        }
        System.out.println("=======================================================");
    }

    public static void mostrarDondeSeVendeArticulo(Articulo articulo) {
        System.out.println("\n*******************************************************");
        System.out.println("Consultando dónde se vende: '" + articulo.getDenominacion() + "'");
        System.out.println("-------------------------------------------------------");
        if (articulo.getStockPorSucursal().isEmpty()) {
            System.out.println("Este artículo no está disponible en ninguna sucursal.");
        } else {
            for (ArticuloStock stock : articulo.getStockPorSucursal()) {
                System.out.println("- Disponible en: " + stock.getSucursal().getNombre());
                System.out.println("  Con un stock de: " + stock.getStockActual());
            }
        }
        System.out.println("*******************************************************");
    }
}
