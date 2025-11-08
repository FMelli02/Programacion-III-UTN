package com.utn.productos_api.dto;

import com.utn.productos_api.model.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductoResponseDTO {
    @Schema(description = "ID único del producto", example = "1")
    private Long id;
    @Schema(description = "Nombre del producto", example = "iPhone 15")
    private String nombre;
    @Schema(description = "Descripción detallada", example = "Celular de última generación")
    private String descripcion;
    @Schema(description = "Precio del producto", example = "$150.0")
    private Double precio;
    @Schema(description = "Cantidad de unidades del producto", example = "10")
    private Integer stock;
    @Schema(description = "Categoría de los productos", example = "Tecnología")
    private Categoria categoria;
}
