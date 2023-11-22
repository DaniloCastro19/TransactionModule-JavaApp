package org.jala.university.domain;

import org.jala.university.model.BankUser;
import org.jala.university.model.Transaction;
import org.jala.university.model.TransactionType;

import java.util.Date;
import java.util.List;

public interface TransactionModule {
    void createTransaction(Transaction transaction);

    List<Transaction> findTransactionsWithAccountNumber(String searchTerm);

    List<Transaction> findTransactionsWithNameOrLastName(String searchTerm);

    List<Transaction> finTransactionsWithTransactionAmount(boolean searchTerm);

    List<Transaction> findTransactionWithDate(Date startDate, Date endDate);

    List<Transaction> findTransactionWithType(TransactionType transactionType);
    BankUser getUserInfoForAccountNumber(String accountNumber);
}
