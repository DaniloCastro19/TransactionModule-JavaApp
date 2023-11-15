package org.jala.university.domain;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import jakarta.transaction.Transactional;
import org.jala.university.model.Account;
import org.jala.university.model.BankUser;
import org.jala.university.model.Currency;
import org.jala.university.model.Transaction;
import org.jala.university.model.TransactionStatus;
import org.jala.university.model.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private TransactionModule transactionModule;
    private UserModule userModule;

    @Autowired
    public TransactionService(TransactionModule transactionModule, UserModule userModule) {
        this.transactionModule = transactionModule;
        this.userModule = userModule;
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
        Account recipientAccount = userModule.findUserById(recipientAccountID);

        recipientAccount.setBalance(recipientAccount.getBalance()+amount);
        userModule.update(recipientAccount);

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

        transactionModule.createTransaction(depositTransaction);
    }

    /**
     * Processes a withdrawal transaction, deducting an amount from a source account.
     *
     * @param sourceAccountID  the unique identifier of the source account
     * @param amount           the amount to withdraw
     * @return true if the withdrawal was successful, false if there were insufficient funds
     */
    @Transactional
    public boolean withdrawal(UUID sourceAccountID, Long amount, String description, Currency currency, Date date) {
        Account sourceAccount = userModule.findUserById(sourceAccountID);

        if (sourceAccount.getBalance() < amount) {
            return false;
        }

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        userModule.update(sourceAccount);

        Transaction withdrawalTransaction = Transaction.builder()
                .id(UUID.randomUUID())
                .date(date)
                .type(TransactionType.WITHDRAWAL)
                .amount(amount)
                .currency(currency)
                .accountFrom(sourceAccount)
                .status(TransactionStatus.COMPLETED)
                .description(description)
                .build();

        transactionModule.createTransaction(withdrawalTransaction);
        return true;
    }
    /**
     * Processes a transfer transaction.
     *
     * @param transaction  the transation builded to be is processed in the method.
     */
    @Transactional
    public TransactionStatus transfer(Transaction transaction){
        if (transaction.getAmount() <= 0 || transaction.getAmount() > transaction.getAccountFrom().getBalance()) {
            transaction.setStatus(TransactionStatus.FAILED);
            transactionModule.createTransaction(transaction);
            return TransactionStatus.FAILED;
        } else {
            List<BankUser> accountFromUserResults = userModule.findUsersByAccountNumber(transaction.getAccountFrom().getAccountNumber());
            List<BankUser> accountToUserResults = userModule.findUsersByAccountNumber(transaction.getAccountTo().getAccountNumber());

            Account accountFrom = accountFromUserResults.get(0).getAccount();
            Account accountTo = accountToUserResults.get(0).getAccount();
            Long amount = transaction.getAmount();

            Long accountFromNewBalance = accountFrom.getBalance() - amount;
            accountFrom.setBalance(accountFromNewBalance);
            Long accountToNewBalance = accountTo.getBalance() + amount;
            accountTo.setBalance(accountToNewBalance);
            System.out.println("Balance de " + accountFrom.getOwner().getFirstName() + "= " + accountFrom.getBalance());
            System.out.println("Balance de " + accountTo.getOwner().getFirstName() + "= " + accountTo.getBalance());
            transactionModule.createTransaction(transaction);
            return TransactionStatus.COMPLETED;
        }
    }
}
