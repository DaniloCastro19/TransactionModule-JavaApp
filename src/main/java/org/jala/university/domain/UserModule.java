package org.jala.university.domain;

import org.jala.university.model.Account;
import org.jala.university.model.BankUser;

import java.util.List;
import java.util.UUID;

public interface UserModule {
    List<BankUser> findUsersByAccountNumber(String accountNumber);
    List<BankUser> findUsersByNameOrLastName(String name);
    void update(Account account);
    Account findUserById(UUID id);

}
