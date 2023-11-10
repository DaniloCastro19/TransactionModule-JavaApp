package org.jala.university.domain;

import org.jala.university.model.Transaction;

import java.util.List;

public interface TransactionModule {
    void depositTransaction(Transaction transaction);

    List<Transaction> findTransactionsWithAccountNumber(String searchTerm);
}
