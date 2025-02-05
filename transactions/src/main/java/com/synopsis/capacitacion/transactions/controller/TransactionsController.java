package com.synopsis.capacitacion.transactions.controller;

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

import com.synopsis.capacitacion.transactions.entity.Transactions;
import com.synopsis.capacitacion.transactions.service.ITransactionsService;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {

    @Autowired
    private ITransactionsService ITransactionsService;

    // Listar transacciones
    @GetMapping("/listar")
    public List<Transactions> list() {
        return ITransactionsService.getAllTransactions();
    }

    // Buscar transacción
    @GetMapping("/buscar/{id}")
    public Transactions get(@PathVariable(name = "id") Long id) {
        return ITransactionsService.getById(id);
    }

    // Buscar por Número de transacción
    @GetMapping("/iban/{iban}")
    public List<Transactions> getTransactionsByIban(@PathVariable String iban) {
        return ITransactionsService.findByIban(iban);
    }

    // Insertar transacción
    @PostMapping("/insertar")
    public ResponseEntity<Transactions> post(@RequestBody Transactions input) {
        Transactions savedTransactions = ITransactionsService.createTransactions(input);
        return ResponseEntity.ok(savedTransactions);
    }

    // Modificar transacción
    @PutMapping("/modificar/{id}")
    public ResponseEntity<Transactions> put(@PathVariable(name = "id") long id, @RequestBody Transactions input) {
        Transactions updateTransactions = ITransactionsService.updateTransactions(id, input);
        if (updateTransactions != null) {
            return ResponseEntity.ok(updateTransactions);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar transacción
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        boolean isDeleted = ITransactionsService.deleteTransactions(id);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
