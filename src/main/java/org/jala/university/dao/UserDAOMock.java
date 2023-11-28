package org.jala.university.dao;

import org.jala.university.model.BankUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class UserDAOMock extends UserDAO {
  private final Map<UUID, BankUser> users = new HashMap<>();

  public UserDAOMock() {
    super(null);
  }

  @Override
  public BankUser create(BankUser user) {
    users.put(user.getId(), user);
    return user;
  }

  @Override
  public List<BankUser> findUsersByNameOrLastName(String searchTerm) {
    return users.values().stream()
      .filter(user -> user.getFirstName().contains(searchTerm) || user.getLastName().contains(searchTerm))
      .collect(Collectors.toList());
  }

  @Override
  public List<BankUser> findUsersByAccountNumber(String accountNumber) {
    return users.values().stream()
      .filter(user -> user.getAccount() != null && user.getAccount().getAccountNumber().equals(accountNumber))
      .collect(Collectors.toList());
  }

  @Override
  public BankUser update(BankUser user) {
    users.put(user.getId(), user);
    return user;
  }

  @Override
  public void delete(BankUser user) {
    users.remove(user.getId());
  }

  @Override
  public List<BankUser> findAll() {
    return new ArrayList<>(users.values());
  }

}
