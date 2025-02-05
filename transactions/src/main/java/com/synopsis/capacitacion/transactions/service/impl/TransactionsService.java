package com.synopsis.capacitacion.transactions.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synopsis.capacitacion.transactions.entity.Transactions;
import com.synopsis.capacitacion.transactions.repository.TransactionsRepository;
import com.synopsis.capacitacion.transactions.service.ITransactionsService;

@Service
public class TransactionsService implements ITransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Override
    public Transactions getById(Long id) {
        return transactionsRepository.findById(id).get();
    }

    @Override
    public List<Transactions> findByIban(String iban) {
        return transactionsRepository.findByIban(iban);
    }

    @Override
    public List<Transactions> getAllTransactions() {
        return (List<Transactions>) transactionsRepository.findAll();
    }

    @Override
    public Transactions createTransactions(Transactions transactions) {
        return transactionsRepository.save(transactions);
    }

    @Override
    public Transactions updateTransactions(long id, Transactions transactions) {
        Optional<Transactions> existingTransactions = transactionsRepository.findById(id);
        if (existingTransactions.isPresent()) {
            Transactions updateTransactions = existingTransactions.get();
            updateTransactions.setReference(transactions.getReference());
            updateTransactions.setIban(transactions.getIban());
            updateTransactions.setDate(transactions.getDate());
            updateTransactions.setAmount(transactions.getAmount());
            updateTransactions.setFee(transactions.getFee());
            updateTransactions.setDescripcion(transactions.getDescripcion());
            updateTransactions.setStatus(transactions.getStatus());
            updateTransactions.setChannel(transactions.getChannel());

            return transactionsRepository.save(updateTransactions);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteTransactions(long id) {
        if (transactionsRepository.existsById(id)) {
            transactionsRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
