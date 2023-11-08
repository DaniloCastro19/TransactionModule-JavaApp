package org.jala.university.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.jala.university.dao.AccountDAO;
import org.jala.university.dao.TransactionDAO;
import org.jala.university.dao.TransactionDaoMock;
import org.jala.university.dao.UserDAO;
import org.jala.university.domain.services.AccountModuleImpl;
import org.jala.university.model.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    @Override
    public BankUser findUserByAccountNumber(String accountNumber) {
        return accountDAO.findUserByAccountNumber(accountNumber);
    }

    @Override
    public void makeTransfer(Date transactionDate, TransactionStatus transactionStatus, String amount, TransactionType transactionType, Currency currency, String accountFromId, String accountToId) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EntityExample");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TransactionDAO transactionDAO = new TransactionDaoMock(entityManager);


        AccountModuleImpl accountFrom = new AccountModuleImpl(accountDAO);
        AccountModuleImpl accountTo= new AccountModuleImpl(accountDAO);



        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID())
                .date(transactionDate)
                .status(transactionStatus)
                .type(transactionType)
                .amount(Long.valueOf(amount))
                .currency(currency)
                .accountFrom(accountFrom.get(UUID.fromString(accountFromId)))
                .accountTo(accountTo.get(UUID.fromString(accountToId)))
                .build();

        transactionDAO.create(transaction);
        System.out.println(transactionDAO.findOne(transaction.getId()));
    }

    @Override
    public Transaction getTransaction() {
        return null;
    }

    @Override
    public List<Transaction> getAllTransaction() {
        return null;
    }
}
