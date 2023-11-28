package org.jala.university.domain;

import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.List;

import org.jala.university.model.Account;
import org.jala.university.model.BankUser;
import org.jala.university.model.Transaction;
import org.jala.university.model.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  private TransactionModule transactionModule;
  private UserModule userModule;
  private ScheduledTransferModule scheduledTransferModule;

  @Autowired
  public TransactionService(TransactionModule transactionModule, UserModule userModule, ScheduledTransferModule scheduledTransferModule) {
    this.transactionModule = transactionModule;
    this.userModule = userModule;
    this.scheduledTransferModule = scheduledTransferModule;
  }

  /**
   * Processes a deposit transaction, deducting an amount from a source account.
   *
   * @param transaction the transation builded to be processed in the method.
   * @return The transaction carried out
   */

  @Transactional
  public Transaction deposit(Transaction transaction) {
    if (transaction.getAmount() <= 0) {
      transaction.setStatus(TransactionStatus.FAILED);
    } else {
      List<BankUser> accountToUserResults = userModule.findUsersByAccountNumber(transaction.getAccountTo().getAccountNumber());

      Account accountTo = accountToUserResults.get(0).getAccount();
      Long amount = transaction.getAmount();

      Long accountToNewBalance = accountTo.getBalance() + amount;
      accountTo.setBalance(accountToNewBalance);
      System.out.println("Balance de " + accountTo.getOwner().getFirstName() + "= " + accountTo.getBalance());
      transaction.setStatus(TransactionStatus.COMPLETED);
    }
    transactionModule.createTransaction(transaction);
    return transaction;
  }

  /**
   * Processes a withdrawal transaction, deducting an amount from a source account.
   *
   * @param transaction the transation builded to be processed in the method.
   * @return The transaction carried out
   */
  @Transactional
  public Transaction withdrawal(Transaction transaction) {
    if (transaction.getAmount() <= 0 || transaction.getAmount() > transaction.getAccountFrom().getBalance()) {
      transaction.setStatus(TransactionStatus.FAILED);
    } else {
      List<BankUser> accountFromUserResults = userModule.findUsersByAccountNumber(transaction.getAccountFrom().getAccountNumber());
      Account accountFrom = accountFromUserResults.get(0).getAccount();
      Long accountFromNewBalance = accountFrom.getBalance() - transaction.getAmount();
      accountFrom.setBalance(accountFromNewBalance);
      System.out.println("Balance de " + accountFrom.getOwner().getFirstName() + "= " + accountFrom.getBalance());
      transaction.setStatus(TransactionStatus.COMPLETED);
    }
    transactionModule.createTransaction(transaction);
    return transaction;
  }

  /**
   * Processes a transfer transaction.
   *
   * @param transaction the transation builded to be is processed in the method.
   * @return the transaction carried out.
   */

  @Transactional
  public Transaction transfer(Transaction transaction) {
    if (transaction.getAmount() <= 0 || transaction.getAmount() > transaction.getAccountFrom().getBalance()) {
      transaction.setStatus(TransactionStatus.FAILED);
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
      transaction.setStatus(TransactionStatus.COMPLETED);
    }
    transactionModule.createTransaction(transaction);
    return transaction;
  }

  public TransactionStatus scheduledTransfer(ScheduledTransferModel scheduledTransferModel) {
    if (scheduledTransferModel.getAmount() <= 0 || scheduledTransferModel.getAmount() > scheduledTransferModel.getAccountFrom().getBalance()) {
      scheduledTransferModel.setStatus(TransactionStatus.FAILED);
      scheduledTransferModule.createScheduledTransfer(scheduledTransferModel);
      return TransactionStatus.FAILED;
    } else {
      List<BankUser> accountFromUserResults = userModule.findUsersByAccountNumber(scheduledTransferModel.getAccountFrom().getAccountNumber());
      List<BankUser> accountToUserResults = userModule.findUsersByAccountNumber(scheduledTransferModel.getAccountTo().getAccountNumber());

      Account accountFrom = accountFromUserResults.get(0).getAccount();
      Account accountTo = accountToUserResults.get(0).getAccount();
      Long amount = scheduledTransferModel.getAmount();

      int numOccurrences = scheduledTransferModel.getNumOccurrences();
      double amountPerOccurrence = amount / numOccurrences;

      List<Date> datesTransfer = scheduledTransferModel.calculateDatesTransfer();

      for (Date date : datesTransfer) {
        if (date.compareTo(new Date()) >= 0) {
          scheduledTransferModel.setDate(date);
          scheduledTransferModule.createScheduledTransfer(scheduledTransferModel);

          if (amountPerOccurrence <= 0 || amountPerOccurrence > accountFrom.getBalance()) {
            scheduledTransferModel.setStatus(TransactionStatus.FAILED);
            scheduledTransferModule.createScheduledTransfer(scheduledTransferModel);
            System.out.println("Transferencia parcial fallida. Monto inválido o insuficiente.");
          } else {
            Long accountFromNewBalance = accountFrom.getBalance() - amount;
            accountFrom.setBalance(accountFromNewBalance);
            Long accountToNewBalance = accountTo.getBalance() + amount;
            accountTo.setBalance(accountToNewBalance);

            System.out.println("Transferencia parcial exitosa agendada para: " + date);
            System.out.println("Balance de " + accountFrom.getOwner().getFirstName() + "= " + accountFrom.getBalance());
            System.out.println("Balance de " + accountTo.getOwner().getFirstName() + "= " + accountTo.getBalance());
            scheduledTransferModule.createScheduledTransfer(scheduledTransferModel);
          }
        }
      }
    }
    return TransactionStatus.PENDING;
  }
}