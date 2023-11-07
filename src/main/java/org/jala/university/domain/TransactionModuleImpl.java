package org.jala.university.domain;

import org.jala.university.dao.AccountDAO;
import org.jala.university.dao.TransactionDAO;
import org.jala.university.dao.UserDAO;
import org.jala.university.model.Account;
import org.jala.university.model.BankUser;
import org.jala.university.model.Transaction;

import java.util.List;
import java.util.UUID;

public class TransactionModuleImpl implements TransactionModule {
    private AccountDAO accountDAO;
    private UserDAO userDAO;
    private TransactionDAO transactionDAO;

    public TransactionModuleImpl(AccountDAO accountDAO, UserDAO userDAO, TransactionDAO transactionDAO) {
        this.accountDAO = accountDAO;
        this.userDAO = userDAO;
        this.transactionDAO = transactionDAO;
    }

    @Override
    public List<BankUser> findUsersByAccountNumber(String accountNumber) {
        return accountDAO.findUsersByAccountNumber(accountNumber);
    }

    @Override
    public List<BankUser> findUsersByNameOrLastName(String name) {
        return userDAO.findUsersByNameOrLastName(name);
    }

    @Override
    public void depositTransaction(Transaction transaction) {
        transactionDAO.create(transaction);
    }

    @Override
    public Account findUserById(UUID id) {
        return userDAO.findOne(id).getAccount();
    }

    @Override
    public void update(Account account) {
        accountDAO.update(account);
    }

}
