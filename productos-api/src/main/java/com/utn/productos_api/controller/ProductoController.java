package com.utn.productos_api.controller;

import com.utn.productos_api.dto.ActualizarStockDTO;
import com.utn.productos_api.dto.ProductoDTO;
import com.utn.productos_api.dto.ProductoResponseDTO;
import com.utn.productos_api.model.Categoria;
import com.utn.productos_api.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/productos") // Ruta base para todos los endpoints
@Tag(name = "Gestión de Productos", description = "Endpoints para gestionar productos")
public class ProductoController {
    private final ProductoService productoService;

    // Inyectamos el servicio por constructor
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }
    // --- ENDPOINTS ---
    // 1. Listar todos
    @Operation(summary = "Listar todos los productos", description = "Obtiene una lista de todos los productos disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listarTodos() {
        List<ProductoResponseDTO> productos = productoService.obtenerTodos();
        return ResponseEntity.ok(productos);
    }

    // 2. Obtener por ID
    @Operation(summary = "Obtener producto por ID", description = "Busca un producto específico por su ID")
    @ApiResponses(value = { // Múltiples respuestas posibles
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado (ProductoNotFoundException)")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id) {
        ProductoResponseDTO producto = productoService.obtenerPorId(id);
        return ResponseEntity.ok(producto);
    }

    // 3. Filtrar por Categoría
    @Operation(summary = "Filtrar por Categoría", description = "Obtiene una lista de productos filtrada por categoría")
    @ApiResponse(responseCode = "200", description = "Productos filtrados")
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerPorCategoria(@PathVariable Categoria categoria) {
        List<ProductoResponseDTO> productos = productoService.obtenerPorCategoria(categoria);
        return ResponseEntity.ok(productos);
    }

    // 4. Crear producto
    @Operation(summary = "Crear un nuevo producto", description = "Crea un producto y lo guarda en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (MethodArgumentNotValidException)")
    })
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crearProducto(@Valid @RequestBody ProductoDTO productoDTO) {
        ProductoResponseDTO productoCreado = productoService.crearProducto(productoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
    }

    // 5. Actualizar producto completo
    @Operation(summary = "Actualizar un producto completo", description = "Actualiza todos los campos de un producto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizarProducto(
            @PathVariable Long id,
            @Valid @RequestBody ProductoDTO productoDTO) {

        ProductoResponseDTO productoActualizado = productoService.actualizarProducto(id, productoDTO);
        return ResponseEntity.ok(productoActualizado);
    }

    // 6. Actualizar solo el stock
    @Operation(summary = "Actualizar solo el stock", description = "Actualiza únicamente el stock de un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductoResponseDTO> actualizarStock(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarStockDTO stockDTO) {

        ProductoResponseDTO productoActualizado = productoService.actualizarStock(id, stockDTO);
        return ResponseEntity.ok(productoActualizado);
    }

    // 7. Eliminar producto
    @Operation(summary = "Eliminar un producto", description = "Elimina un producto de la base de datos por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado (No Content)"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
