package com.synopsis.capacitacion.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synopsis.capacitacion.product.entity.ProductEntity;
import com.synopsis.capacitacion.product.repository.ProductRepository;
import com.synopsis.capacitacion.product.service.IProductService;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductEntity getById(long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public List<ProductEntity> getAllProducts() {
        return (List<ProductEntity>) productRepository.findAll();
    }

    // INSERTAR
    @Override
    public ProductEntity createProduct(ProductEntity product) {
        return productRepository.save(product);
    }

    @Override
    public ProductEntity updateProduct(long id, ProductEntity product) {
        Optional<ProductEntity> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            ProductEntity updateProduct = existingProduct.get();
            updateProduct.setCode(product.getCode());
            updateProduct.setName(product.getName());
            return productRepository.save(updateProduct);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteProduct(long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    // PROCEDMIENTOS ALMACENADOS

    // Buscar producto
    // @Override
    // public ProductEntity getProducto(long id) {
    //     return productRepository.obtenerProducto(id);
    // }

    // Listar producto
    // @Override
    // public List<ProductEntity> getAllProductos() {
    //     return productRepository.findAll();
    // }

    // // Insertar producto
    // @Override
    // public ProductEntity createProducto(ProductEntity product) {
    //     productRepository.insertarProducto(product.getCode(), product.getName());
    //     return product;
    // }

    // // Modificar producto
    // @Override
    // public ProductEntity updateProducto(long id, ProductEntity product) {
    //     productRepository.actualizarProducto(id, product.getCode(), product.getName());
    //     return product;
    // }

    // // Eliminar producto
    // @Override
    // public boolean deleteProducto(long id) {
    //     productRepository.borrarProducto(id);
    //     return true;
    // }
}
