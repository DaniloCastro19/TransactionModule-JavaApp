package org.jala.university.resources;
import org.jala.university.dao.AccountDAO;
import org.jala.university.dao.TransactionDAO;
import org.jala.university.model.Account;
import org.jala.university.model.Transaction;

import java.util.Date;
import java.util.UUID;

import jakarta.transaction.Transactional;
import org.jala.university.model.TransactionType;
import org.jala.university.model.TransactionStatus;
import org.jala.university.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TransactionService {

    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;

    @Autowired
    public TransactionService(AccountDAO accountDAO, TransactionDAO transactionDAO) {
        this.accountDAO = accountDAO;
        this.transactionDAO = transactionDAO;
    }

    /**
     * Processes a deposit transaction, transferring an amount from a source account to a recipient account.
     * It creates a new transaction record with a DEPOSIT type, updates the source account balance,
     * and saves the transaction to the database.
     *
     * @param recipientAccountID the unique identifier of the recipient account to which funds are to be deposited
     * @param amount            the amount of money to deposit
     * @param description       a description of the transaction for record-keeping purposes
     * @param currency          the currency in which the deposit is made
     * @param date              the date when the transaction is to be executed
     * @return                  a {@link Transaction} object that represents the completed deposit transaction
     */
    @Transactional
    public Transaction deposit(UUID recipientAccountID, Long amount, String description, Currency currency, Date date) {
        Account recipientAccount = accountDAO.findOne(recipientAccountID);

        recipientAccount.setBalance(recipientAccount.getBalance()+amount);
        accountDAO.update(recipientAccount);

        Transaction depostitTransaction = new Transaction();
        depostitTransaction.setId(UUID.randomUUID());
        depostitTransaction.setDate(date);
        depostitTransaction.setType(TransactionType.DEPOSIT);
        depostitTransaction.setAmount(amount);
        depostitTransaction.setCurrency(currency);
        depostitTransaction.setAccountTo(recipientAccount);
        depostitTransaction.setStatus(TransactionStatus.COMPLETED);
        depostitTransaction.setDescription(description);

        transactionDAO.create(depostitTransaction);

        return depostitTransaction;
    }

}
