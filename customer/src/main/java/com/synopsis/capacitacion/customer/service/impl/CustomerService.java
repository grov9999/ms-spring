package com.synopsis.capacitacion.customer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.synopsis.capacitacion.customer.entity.Customer;
import com.synopsis.capacitacion.customer.repository.CustomerRepository;
import com.synopsis.capacitacion.customer.service.ICustomerService;

public class CustomerService implements ICustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.listarClientes();
    }
}
