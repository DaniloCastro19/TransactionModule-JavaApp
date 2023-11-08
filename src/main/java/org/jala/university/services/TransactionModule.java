package org.jala.university.services;

import org.jala.university.model.*;

import java.util.Date;
import java.util.List;

public interface TransactionModule {
    List<BankUser> findUsersByAccountNumber(String accountNumber);
    List<BankUser> findUsersByNameOrLastName(String name);

    BankUser findUserByAccountNumber(String accountNumber);

    void makeTransfer(Date transactionDate, TransactionStatus transactionStatus, String amount, TransactionType transactionType,
                      Currency currency, String accountFromId, String accountToId);

    Transaction getTransaction();

    List<Transaction> getAllTransaction();
}
