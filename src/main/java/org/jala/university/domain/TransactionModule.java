package org.jala.university.domain;

import org.jala.university.model.BankUser;
import org.jala.university.model.Transaction;
import org.jala.university.model.Account;

import java.util.List;
import java.util.UUID;

public interface TransactionModule {
    List<BankUser> findUsersByAccountNumber(String accountNumber);
    List<BankUser> findUsersByNameOrLastName(String name);
    void depositTransaction(Transaction transaction);
    Account findUserById(UUID id);
    void update(Account account);
}
