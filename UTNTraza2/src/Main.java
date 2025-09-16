import java.sql.SQLOutput;
import java.util.*;

public class Main {

    private static final Map<Long, ArticuloManufacturado> manufacturadosBD = new HashMap<>();
    private static final List<Categoria> categoriasBD = new ArrayList<>();
    private static final List<ArticuloInsumo> insumosBD = new ArrayList<>();

    public static void main(String[] args) {
        //Crear las categorías
        Categoria pizzas = Categoria.builder().id(1L).denominacion("Pizzas").build();
        Categoria sandwich = Categoria.builder().id(2L).denominacion("Sandwich").build();
        Categoria lomos = Categoria.builder().id(3L).denominacion("Lomos").build();
        Categoria insumos = Categoria.builder().id(4L).denominacion("Insumos").build();

        categoriasBD.addAll(List.of(pizzas, sandwich, lomos, insumos));

        //Crear unidades de medida
        UnidadMedida kilogramos = UnidadMedida.builder().id(1L).denominacion("Kg").build();
        UnidadMedida litros = UnidadMedida.builder().id(2L).denominacion("Lt").build();
        UnidadMedida gramos = UnidadMedida.builder().id(3L).denominacion("gr").build();
        UnidadMedida unidad = UnidadMedida.builder().id(4L).denominacion("Unidad").build();

        //Crear artículos insumos
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

        insumosBD.addAll(List.of(sal, aceite, carne, harina));

        //Crear imagenes
        Imagen imagen1 = Imagen.builder().id(1L).denominacion("HawwaianaPizza1.jpg").build();
        Imagen imagen2 = Imagen.builder().id(2L).denominacion("HawwaianaPizza2.jpg").build();
        Imagen imagen3 = Imagen.builder().id(3L).denominacion("HawwaianaPizza3.jpg").build();
        Imagen imagen4 = Imagen.builder().id(4L).denominacion("LomoCompletoLomo1.jpg").build();
        Imagen imagen5 = Imagen.builder().id(5L).denominacion("LomoCompletoLomo2.jpg").build();
        Imagen imagen6 = Imagen.builder().id(6L).denominacion("LomoCompletoLomo3.jpg").build();

        //Crear detalle Articulos Manufacturados
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

        //Crear productos manufacturados relacionándolos a su imagen
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

        manufacturadosBD.put(pizzaHawaiana.getId(), pizzaHawaiana);
        manufacturadosBD.put(lomoCompleto.getId(), lomoCompleto);

        //Mostrar todas las categorías
        System.out.println("\n============= TODAS LAS CATEGORÍAS =============\n");
        categoriasBD.forEach(categoria -> System.out.println(categoria.getDenominacion()));

        //Mostrar todos los insumos
        System.out.println("\n============= TODOS LOS INSUMOS =============\n");
        insumosBD.forEach(insumo -> System.out.println(insumo.getDenominacion() +
                "(Stock: " + insumo.getStockActual() + " " + insumo.getUnidadMedida().getDenominacion() + ")"));

        //Mostrar todos los manufacturados
        System.out.println("\n============= TODOS LOS MANUFACTURADOS =============\n");
        if (manufacturadosBD.isEmpty()) {
            System.out.println("No hay productos para mostrar.");
        } else {
            manufacturadosBD.values().forEach(producto -> {
                System.out.println("ID: " + producto.getId() + " | Nombre: " + producto.getDenominacion() + " | Precio: " + producto.getPrecioVenta());
                System.out.println("Ingredientes:");
                producto.getArticulosManufacturadosDetalles().forEach(detalle -> System.out.println(detalle.getCantidad() +
                        " " + detalle.getArticuloInsumo().getUnidadMedida().getDenominacion() + " de " + detalle.getArticuloInsumo().getDenominacion()));
                System.out.println(" ");
            });
        }

        //Buscar un artículo manufacturado por id
        System.out.println("============= MANUFACTURADOS POR ID =============\n");
        System.out.println("Buscando producto con ID: " + pizzaHawaiana.getId());
        ArticuloManufacturado encontrado = manufacturadosBD.get(pizzaHawaiana.getId());
        if (encontrado != null) {
            System.out.println("Producto encontrado!");
            System.out.println(encontrado.getDenominacion());
        } else {
            System.out.println("No se encontró ningún producto con ese ID.");
        }

        //Actualizar un artículo manufacturado por id
        System.out.println("\n============= ACTUALIZAR MANUFACTURADOS POR ID =============\n");
        System.out.println("Actualizando producto con ID: " + lomoCompleto.getId());
        ArticuloManufacturado aModificar = manufacturadosBD.get(lomoCompleto.getId());
        if (aModificar != null) {
            System.out.println("Datos viejos: " + aModificar.getDescripcion() + " / $" + aModificar.getPrecioVenta());
            aModificar.setDescripcion("El mejor lomo de Mendoza");
            aModificar.setPrecioVenta(20500.0);
            System.out.println("Datos nuevos: " + aModificar.getDescripcion() + " / $" + aModificar.getPrecioVenta());
            System.out.println("Producto actualizado con éxito!");
        } else {
            System.out.println("No se pudo actualizar. El producto con ID " + lomoCompleto.getId() + " no existe.");
        }

        //Eliminar un artículo manufacturado por id
        System.out.println("\n============= ELIMINAR MANUFACTURADO =============\n");
        System.out.println("Eliminando producto con ID: " + pizzaHawaiana.getId());
        if(manufacturadosBD.containsKey(pizzaHawaiana.getId())) {
            manufacturadosBD.remove(pizzaHawaiana.getId());
            System.out.println("Producto eliminado!");
        } else {
            System.out.println("No se pudo eliminar. El producto con id " + pizzaHawaiana.getId() + " no existe.");
        }

    }
}