package org.jala.university.domain;

import org.jala.university.model.BankUser;
import org.jala.university.model.Transaction;
import org.jala.university.model.Account;

import java.util.List;
import java.util.UUID;

public interface TransactionModule {
    void depositTransaction(Transaction transaction);

    List<Transaction> findTransactionsWithAccountNumber(String searchTerm);
}
