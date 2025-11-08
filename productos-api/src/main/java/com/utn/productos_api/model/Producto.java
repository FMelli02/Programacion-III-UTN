package com.utn.productos_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "producto")
@Getter @Setter
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String descripcion;

    private Double precio;

    private Integer stock;

    @Enumerated(EnumType.STRING) // Para que en la DB se guarde "ROPA" y no el numerito (0, 1, 2)
    private Categoria categoria;
}