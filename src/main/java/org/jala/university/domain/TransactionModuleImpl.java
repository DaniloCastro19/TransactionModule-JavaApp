package org.jala.university.domain;


import org.jala.university.dao.TransactionDAO;
import org.jala.university.model.Transaction;

import java.util.List;


public class TransactionModuleImpl implements TransactionModule {

    private TransactionDAO transactionDAO;

    public TransactionModuleImpl(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }
    @Override
    public void depositTransaction(Transaction transaction) {
        transactionDAO.create(transaction);
    }

    @Override
    public List<Transaction> findTransactionsWithAccountNumber(String searchTerm) {
        return transactionDAO.getTransactionsWithAccountNumber(searchTerm);
    }


}
