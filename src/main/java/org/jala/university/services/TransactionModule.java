package org.jala.university.services;

import org.jala.university.model.BankUser;

import java.util.List;

public interface TransactionModule {
    List<BankUser> findUsersByAccountNumber(String accountNumber);
    List<BankUser> findUsersByNameOrLastName(String name);
}
