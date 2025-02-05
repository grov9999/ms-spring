package com.synopsis.capacitacion.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.synopsis.capacitacion.customer.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT update_customer(:id, :code, :name, :phone, :iban, :surname, :address)", nativeQuery = true)
    void updateCustomer(@Param("id") Long id, @Param("code") String code, @Param("name") String name,
            @Param("phone") String phone, @Param("iban") String iban, @Param("surname") String surname,
            @Param("address") String address);

    // Listar clientes
    @Query(value = "SELECT listar_clientes()", nativeQuery = true)
    List<Customer> listarClientes();

    // // Insertar cliente
    // @Query(value = "CALL insertar_cliente(:p_code, :p_name, :p_phone, :p_iban, :p_surname, :p_address)", nativeQuery = true)
    // void insertarCliente(
    //         @Param("p_code") String code,
    //         @Param("p_name") String name,
    //         @Param("p_phone") String phone,
    //         @Param("p_iban") String iban,
    //         @Param("p_surname") String surname,
    //         @Param("p_address") String address);

    // // Actualizar cliente
    // @Query(value = "CALL actualizar_cliente(:p_id, :p_code, :p_name, :p_phone, :p_iban, :p_surname, :p_address)", nativeQuery = true)
    // void actualizarCliente(
    //         @Param("p_id") Long id,
    //         @Param("p_code") String code,
    //         @Param("p_name") String name,
    //         @Param("p_phone") String phone,
    //         @Param("p_iban") String iban,
    //         @Param("p_surname") String surname,
    //         @Param("p_address") String address);

    // // Borrar cliente
    // @Query(value = "CALL borrar_cliente(:p_id)", nativeQuery = true)
    // void borrarCliente(@Param("p_id") Long id);
}
