package com.synopsis.capacitacion.transactions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synopsis.capacitacion.transactions.entity.Transactions;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long>{
    List<Transactions> findByIban(String iban);
}
