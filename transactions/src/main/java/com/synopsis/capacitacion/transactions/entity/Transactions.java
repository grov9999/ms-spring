package com.synopsis.capacitacion.transactions.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "transaction")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long id;
    private String reference;
    private String iban;
    private String date;
    private String amount;
    private String fee;
    private String descripcion;
    private String status;
    private String channel;
}
