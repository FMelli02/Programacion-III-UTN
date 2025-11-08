package com.utn.productos_api.dto;

import com.utn.productos_api.model.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Schema(description = "DTO para crear o actualizar un producto")
public class ProductoDTO {
    // El ID no va acá, porque al crear un producto, el ID lo genera la base de datos, no el usuario.
    @Schema(description = "Nombre del producto", example = "iPhone 15", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El nombre no puede estar vacío") // No nulo y no vacío
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @Schema(description = "Descripción detallada", example = "Celular de última generación")
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    private String descripcion;

    @Schema(description = "Precio del producto", example = "1200.50", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El precio no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El precio debe ser como mínimo 0.01") // Mínimo 0.01
    private Double precio;

    @Schema(description = "Stock disponible", example = "50", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock no puede ser negativo") // Mínimo 0
    private Integer stock;

    @Schema(description = "Categoría del producto", example = "ELECTRONICA", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "La categoría no puede ser nula")
    private Categoria categoria;
}
