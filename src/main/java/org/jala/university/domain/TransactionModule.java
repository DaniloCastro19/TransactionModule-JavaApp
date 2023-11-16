package org.jala.university.domain;

import org.jala.university.model.BankUser;
import org.jala.university.model.Transaction;

import java.util.List;

public interface TransactionModule {
    void createTransaction(Transaction transaction);

    List<Transaction> findTransactionsWithAccountNumber(String searchTerm);

    BankUser getUserInfoForAccountNumber(String accountNumber);
}
