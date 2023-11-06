package org.jala.university.services;

import org.jala.university.dao.AccountDAO;
import org.jala.university.dao.UserDAO;
import org.jala.university.model.BankUser;

import java.util.List;

public class TransactionModuleImpl implements TransactionModule {
    private AccountDAO accountDAO;
    private UserDAO userDAO;

    public TransactionModuleImpl(AccountDAO accountDAO, UserDAO userDAO) {
        this.accountDAO = accountDAO;
        this.userDAO = userDAO;
    }

    @Override
    public List<BankUser> findUsersByAccountNumber(String accountNumber) {
        return accountDAO.findUsersByAccountNumber(accountNumber);
    }

    @Override
    public List<BankUser> findUsersByNameOrLastName(String name) {
        return userDAO.findUsersByNameOrLastName(name);
    }
}
