package org.jala.university.dao;

import org.jala.university.model.Transaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class TransactionDAOMock extends TransactionDAO {
    private final Map<UUID, Transaction> transactionMap = new HashMap<>();
    public TransactionDAOMock() {
        super(null);
    }

    @Override
    public Transaction findOne(UUID id) {
        return transactionMap.get(id);
    }

    @Override
    public List<Transaction> findAll() {
        return new ArrayList<>(transactionMap.values());
    }

    @Override
    public Transaction create(Transaction transaction) {
        UUID id = UUID.randomUUID();
        transaction.setId(id);
        transactionMap.put(id, transaction);
        return transaction;
    }

    @Override
    public Transaction update(Transaction transaction) {
        UUID id = transaction.getId();
        if (transactionMap.containsKey(id)) {
            transactionMap.put(id, transaction);
            return transaction;
        }
        return null;
    }

    @Override
    public void delete(Transaction transaction) {
        transactionMap.remove(transaction.getId());
    }

    @Override
    public void deleteById(UUID transactionId) {
        transactionMap.remove(transactionId);
    }

    @Override
    public List<Transaction> getTransactionsWithAccountNumber(String accountNumber) {
        return transactionMap.values().stream()
                .filter(transaction -> transaction.getAccountFrom().getAccountNumber().equals(accountNumber) ||
                        transaction.getAccountTo().getAccountNumber().equals(accountNumber))
                .collect(Collectors.toList());
    }
}
