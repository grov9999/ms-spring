package com.synopsis.capacitacion.transactions.service;

import java.util.List;

import com.synopsis.capacitacion.transactions.entity.Transactions;

public interface ITransactionsService {

    Transactions getById(Long id);

    List<Transactions> findByIban(String iban);

    List<Transactions> getAllTransactions();

    Transactions createTransactions(Transactions transactions);

    Transactions updateTransactions(long id, Transactions transactions);

    boolean deleteTransactions(long id);
}
