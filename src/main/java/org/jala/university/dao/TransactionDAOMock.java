package org.jala.university.dao;

import org.jala.university.model.Account;
import org.jala.university.model.Transaction;
import org.jala.university.model.TransactionType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public List<Transaction> getTransactionsForCurrentMonth(Date startDate, Date endDate, Account account) {
        return transactionMap.values().stream()
                .filter(transaction -> {
                    Date transactionDate = transaction.getDate();
                    return (transactionDate.equals(startDate) || transactionDate.after(startDate)) &&
                            (transactionDate.equals(endDate) || transactionDate.before(endDate));
                })
                .filter(transaction ->
                        transaction.getAccountFrom().equals(account) ||
                                transaction.getAccountTo().equals(account)
                )
                .collect(Collectors.toList());    }

    @Override
    public List<Transaction> getTransactionsWithNameOrLastName(String name) {
        return transactionMap.values().stream()
                .filter(transaction -> transaction.getAccountFrom().getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> getTransactionsWithTransactionAmount(boolean orderByDescending) {
        Stream<Transaction> transactionStream = transactionMap.values().stream();
        if (orderByDescending) {
            return transactionStream
                    .sorted(Comparator.comparing(Transaction::getAmount).reversed())
                    .collect(Collectors.toList());
        } else {
            return transactionStream
                    .sorted(Comparator.comparing(Transaction::getAmount))
                    .collect(Collectors.toList());
        }    }

    @Override
    public List<Transaction> getTTransactionWithDate(Date startDate, Date endDate) {
        return transactionMap.values().stream()
                .filter(transaction -> !transaction.getDate().before(startDate) && !transaction.getDate().after(endDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> getTTransactionWithType(TransactionType transactionType) {
        return transactionMap.values().stream()
                .filter(transaction -> transaction.getType().equals(transactionType))
                .collect(Collectors.toList());
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
