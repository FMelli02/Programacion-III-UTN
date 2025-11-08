package com.utn.productos_api.exception;

public class ProductoNotFoundException extends RuntimeException {
  public ProductoNotFoundException(String message) {
    super(message);
  }
}
