package com.synopsis.capacitacion.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synopsis.capacitacion.product.entity.ProductEntity;
import com.synopsis.capacitacion.product.service.IProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    // Listar productos
    @GetMapping("/listar")
    public List<ProductEntity> list() {
        return iProductService.getAllProducts();
    }

    // Buscar producto
    @GetMapping("/buscar/{id}")
    public ProductEntity get(@PathVariable(name = "id") long id) {
        return iProductService.getById(id);
    }

    // Insertar productos
    @PostMapping("/insertar")
    public ResponseEntity<ProductEntity> post(@RequestBody ProductEntity input) {
        ProductEntity savedProductEntity = iProductService.createProduct(input);
        return ResponseEntity.ok(savedProductEntity);
    }

    // Actualizar producto
    @PutMapping("/modificar/{id}")
    public ResponseEntity<ProductEntity> put(@PathVariable(name = "id") long id, @RequestBody ProductEntity input) {
        ProductEntity updateProductEntity = iProductService.updateProduct(id, input);
        if (updateProductEntity != null) {
            return ResponseEntity.ok(updateProductEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Borrar producto
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        boolean isDeleted = iProductService.deleteProduct(id);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // // PROCEDMIENTOS ALMACDENADOS
    // //

    // // Listar productos
    // @GetMapping("/SPlistar")
    // public List<ProductEntity> SPlistar() {
    //     return iProductService.getAllProductos();
    // }

    // // Insertar productos
    // @PostMapping("/SPinsertar")
    // public ResponseEntity<ProductEntity> SPpost(@RequestBody ProductEntity input) {
    //     ProductEntity savedProductEntity = iProductService.createProducto(input);
    //     return ResponseEntity.ok(savedProductEntity);
    // }

    // // Modificar producto
    // @PutMapping("/SPactualizar/{id}")
    // public ResponseEntity<ProductEntity> SPput(@PathVariable(name = "id") long id, @RequestBody ProductEntity input) {
    //     ProductEntity updateProductEntity = iProductService.updateProducto(id, input);
    //     if (updateProductEntity != null) {
    //         return ResponseEntity.ok(updateProductEntity);
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    // // Borrar producto
    // @DeleteMapping("/SPPborrar/{id}")
    // public ResponseEntity<Void> SPdelete(@PathVariable long id) {
    //     boolean isDeleted = iProductService.deleteProducto(id);
    //     if (isDeleted) {
    //         return ResponseEntity.ok().build();
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }
}
