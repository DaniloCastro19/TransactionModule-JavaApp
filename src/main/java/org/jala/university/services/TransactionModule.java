package org.jala.university.services;

import org.jala.university.model.*;

import java.util.Date;
import java.util.List;

public interface TransactionModule {
    List<BankUser> findUsersByAccountNumber(String accountNumber);
    List<BankUser> findUsersByNameOrLastName(String name);

    BankUser findUserByAccountNumber(String accountNumber);
    Transaction getTransaction();

    List<Transaction> getAllTransaction();
}
