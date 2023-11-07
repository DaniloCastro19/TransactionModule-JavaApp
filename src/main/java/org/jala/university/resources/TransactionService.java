package org.jala.university.resources;
import org.jala.university.dao.AccountDAO;
import org.jala.university.dao.TransactionDAO;
import org.jala.university.domain.TransactionModule;
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

    private TransactionModule module;

    @Autowired
    public TransactionService(TransactionModule module) {
        this.module = module;
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
     */
    @Transactional
    public void deposit(UUID recipientAccountID, Long amount, String description, Currency currency, Date date) {
        Account recipientAccount = module.findUserById(recipientAccountID);

        recipientAccount.setBalance(recipientAccount.getBalance()+amount);
        module.update(recipientAccount);

        Transaction depositTransaction = Transaction.builder()
                .id(UUID.randomUUID())
                .date(date)
                .type(TransactionType.DEPOSIT)
                .amount(amount)
                .currency(currency)
                .accountTo(recipientAccount)
                .status(TransactionStatus.COMPLETED)
                .description(description)
                .build();

        module.depositTransaction(depositTransaction);
    }

}
