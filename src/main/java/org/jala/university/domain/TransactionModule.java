package org.jala.university.domain;

public interface TransactionModule {
    void depositTransaction(Transaction transaction);

    List<Transaction> findTransactionsWithAccountNumber(String searchTerm);
}
