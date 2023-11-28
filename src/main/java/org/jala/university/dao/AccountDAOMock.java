package org.jala.university.dao;

import org.jala.university.model.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class AccountDAOMock extends AccountDAO {
  private final Map<UUID, Account> accounts = new HashMap<>();

  public AccountDAOMock() {
    super(null);
  }

  @Override
  public Account create(Account account) {
    accounts.put(account.getId(), account);
    return account;
  }

  @Override
  public Account update(Account account) {
    return accounts.put(account.getId(), account);
  }

  @Override
  public void delete(Account account) {
    accounts.remove(account.getId());
  }

  @Override
  public List<Account> findAll() {
    return new ArrayList<>(accounts.values());
  }

}
