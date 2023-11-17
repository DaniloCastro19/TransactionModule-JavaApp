package org.jala.university.domain;

import org.jala.university.dao.TransactionDAO;
import org.jala.university.model.BankUser;
import org.jala.university.model.Transaction;
import org.jala.university.model.TransactionType;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


public class TransactionModuleImpl implements TransactionModule {

    private TransactionDAO transactionDAO;

    public TransactionModuleImpl(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }
    @Override
    public void createTransaction(Transaction transaction) {
        transactionDAO.create(transaction);
    }
    @Override
    public List<Transaction> findTransactionsWithAccountNumber(String searchTerm) {
        return transactionDAO.getTransactionsWithAccountNumber(searchTerm);
    }
    @Override
    public List<Transaction> findTransactionsWithNameOrLastName(String searchTerm) {
        return transactionDAO.getTransactionsWithNameOrLastName(searchTerm);
    }@Override
    public List<Transaction> finTransactionsWithTransactionAmount(boolean orderByDescending) {
        return transactionDAO.getTransactionsWithTransactionAmount(orderByDescending);
    }
    @Override
    public List<Transaction> findTransactionWithDate(Date startDate, Date endDate) {
        return transactionDAO.getTTransactionWithDate(startDate, endDate);
    }
    @Override
    public List<Transaction> findTransactionWithType(TransactionType transactionType) {
        return transactionDAO.getTTransactionWithType(transactionType);
    }
    public BankUser getUserInfoForAccountNumber(String accountNumber) {
        List<Transaction> transactions = transactionDAO.getTransactionsWithAccountNumber(accountNumber);
        if (!transactions.isEmpty()) {
            Transaction firstTransaction = transactions.get(0);
            if (firstTransaction.getAccountFrom() != null) {
                return firstTransaction.getAccountFrom().getOwner();
            }
        }
        return null;
    }

}
