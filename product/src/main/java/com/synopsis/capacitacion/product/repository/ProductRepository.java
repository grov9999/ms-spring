package com.synopsis.capacitacion.product.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.synopsis.capacitacion.product.entity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT p FROM ProductEntity p WHERE p.code = :code")
    Optional<ProductEntity> findProductByCode(@Param("code") String code);

    Optional<ProductEntity> findByCode(String code);

    // // Listar productos
    // @Query(value = "SELECT * FROM obtener_producto(:p_id)", nativeQuery = true)
    // ProductEntity obtenerProducto(@Param("p_id") Long id);

    // // Insertar productos
    // @Query(value = "CALL insertar_producto(:p_code, :p_name)", nativeQuery = true)
    // void insertarProducto(
    //     @Param("p_code") String code,
    //     @Param("p_name") String name
    // );

    // // Actualizar producto
    // @Query(value = "CALL actualizar_producto(:p_id, :p_code, :p_name)", nativeQuery = true)
    // void actualizarProducto(
    //     @Param("p_id") Long id,
    //     @Param("p_code") String code,
    //     @Param("p_name") String name
    // );

    // // Eliminar producto
    // @Query(value = "CALL borrar_producto(:p_id)", nativeQuery = true)
    // void borrarProducto(@Param("p_id") Long id);
}
