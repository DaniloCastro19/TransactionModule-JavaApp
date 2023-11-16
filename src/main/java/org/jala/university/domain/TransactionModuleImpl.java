package org.jala.university.domain;

import org.jala.university.dao.TransactionDAO;
import org.jala.university.model.BankUser;
import org.jala.university.model.Transaction;
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
