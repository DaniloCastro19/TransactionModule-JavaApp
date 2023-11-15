package org.jala.university.domain;

import org.jala.university.dao.AccountDAO;
import org.jala.university.dao.UserDAO;
import org.jala.university.model.Account;
import org.jala.university.model.BankUser;

import java.util.List;
import java.util.UUID;

public class UserModuleImpl implements  UserModule{
    private AccountDAO accountDAO;
    private UserDAO userDAO;
    public UserModuleImpl(AccountDAO accountDAO, UserDAO userDao){
        this.accountDAO = accountDAO;
        this.userDAO = userDao;

    }
    @Override
    public List<BankUser> findUsersByAccountNumber(String accountNumber) {
        return userDAO.findUsersByAccountNumber(accountNumber);
    }

    @Override
    public List<BankUser> findUsersByNameOrLastName(String name) {
        return userDAO.findUsersByNameOrLastName(name);
    }
    @Override
    public void update(Account account) {
        accountDAO.update(account);
    }
    @Override
    public Account findUserById(UUID id) {
        return userDAO.findOne(id).getAccount();
    }

}
