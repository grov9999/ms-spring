package com.synopsis.capacitacion.customer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
public class CustomerProduct {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private long productId;

    @Transient
    private String productName;
    
    @JsonIgnore//Es necesario evitar la repeticion infinita.
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Customer.class)
    @JoinColumn(name = "customerId", nullable = true)   
    private Customer customer;
}
