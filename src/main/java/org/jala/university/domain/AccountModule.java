package org.jala.university.domain;

import org.jala.university.model.Account;

import java.util.List;
import java.util.UUID;

public interface AccountModule {

    void create(Account account);

    Account get(UUID id);

    List<Account> getAll();

    Account update(Account account);

    void delete(UUID id);
}
