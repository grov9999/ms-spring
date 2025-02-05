package com.synopsis.capacitacion.product.service;

import java.util.List;

import com.synopsis.capacitacion.product.entity.ProductEntity;

public interface IProductService {

    ProductEntity getById(long id);

    List<ProductEntity> getAllProducts();

    ProductEntity createProduct(ProductEntity product);

    ProductEntity updateProduct(long id, ProductEntity product);

    boolean deleteProduct(long id);

    // PROCEDMIENTOS ALMACENADOS
    // List<ProductEntity> getAllProductos();

    // ProductEntity createProducto(ProductEntity product);

    // ProductEntity updateProducto(long id, ProductEntity product);

    // boolean deleteProducto(long id);

}
