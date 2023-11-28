package org.jala.university.domain;

import org.jala.university.dao.AccountDAO;
import org.jala.university.domain.AccountModule;
import org.jala.university.model.Account;

import java.util.List;
import java.util.UUID;

public class AccountModuleImpl implements AccountModule {

  private final AccountDAO accountDao;

  public AccountModuleImpl(AccountDAO accountDao) {
    this.accountDao = accountDao;
  }

  @Override
  public void create(Account account) {
    accountDao.create(account);
  }

  @Override
  public Account get(UUID id) {
    return accountDao.findOne(id);
  }

  @Override
  public List<Account> getAll() {
    return accountDao.findAll();
  }

  @Override
  public Account update(Account account) {
    return accountDao.update(account);
  }

  @Override
  public void delete(UUID id) {
    accountDao.deleteById(id);
  }
}
