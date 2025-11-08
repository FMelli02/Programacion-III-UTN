package com.utn.productos_api.service;

import com.utn.productos_api.dto.ActualizarStockDTO;
import com.utn.productos_api.dto.ProductoDTO;
import com.utn.productos_api.dto.ProductoResponseDTO;
import com.utn.productos_api.exception.ProductoNotFoundException;
import com.utn.productos_api.model.Categoria;
import com.utn.productos_api.model.Producto;
import com.utn.productos_api.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }
    // --- MÉTODOS DE MAPEO ---
    // Convierte una Entidad Producto a un DTO de Respuesta
    private ProductoResponseDTO convertToResponseDto(Producto producto) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setCategoria(producto.getCategoria());
        return dto;
    }

    // Convierte un DTO de creación a una Entidad Producto
    private Producto convertToEntity(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCategoria(dto.getCategoria());
        return producto;
    }

    // --- LÓGICA DE NEGOCIO (Ahora usando DTOs) ---

    // Método para crear
    public ProductoResponseDTO crearProducto(ProductoDTO productoDTO) {
        Producto producto = convertToEntity(productoDTO);
        Producto productoGuardado = productoRepository.save(producto);
        return convertToResponseDto(productoGuardado);
    }

    // Método para obtener todos
    public List<ProductoResponseDTO> obtenerTodos() {
        return productoRepository.findAll()
                .stream()
                .map(this::convertToResponseDto) // Magia de Mapeo
                .collect(Collectors.toList());
    }

    // Método para obtener por ID
    public ProductoResponseDTO obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con id: " + id));
        return convertToResponseDto(producto);
    }

    // Método para obtener por categoría
    public List<ProductoResponseDTO> obtenerPorCategoria(Categoria categoria) {
        return productoRepository.findByCategoria(categoria)
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    // Método para eliminar
    public void eliminarProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con id: " + id));
        productoRepository.delete(producto);
    }

    // Método para actualizar completo (PUT)
    public ProductoResponseDTO actualizarProducto(Long id, ProductoDTO productoDTO) {
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con id: " + id));

        // Actualizamos los campos
        productoExistente.setNombre(productoDTO.getNombre());
        productoExistente.setDescripcion(productoDTO.getDescripcion());
        productoExistente.setPrecio(productoDTO.getPrecio());
        productoExistente.setStock(productoDTO.getStock());
        productoExistente.setCategoria(productoDTO.getCategoria());

        Producto productoActualizado = productoRepository.save(productoExistente);
        return convertToResponseDto(productoActualizado);
    }

    // Método para actualizar solo el stock (PATCH)
    public ProductoResponseDTO actualizarStock(Long id, ActualizarStockDTO stockDTO) {
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con id: " + id));

        productoExistente.setStock(stockDTO.getStock());
        Producto productoActualizado = productoRepository.save(productoExistente);
        return convertToResponseDto(productoActualizado);
    }
}