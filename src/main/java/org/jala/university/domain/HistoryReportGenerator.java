package org.jala.university.domain;

import org.jala.university.model.BankUser;
import org.jala.university.model.Transaction;

import java.util.List;

public class HistoryReportGenerator {

  private final TransactionModule transactionModule;

  public HistoryReportGenerator(TransactionModule transactionModule) {
    this.transactionModule = transactionModule;
  }

  public String generateReport(String accountNumber) {
    List<Transaction> transactions = transactionModule.findTransactionsWithAccountNumber(accountNumber);
    BankUser user = transactionModule.getUserInfoForAccountNumber(accountNumber);

    StringBuilder report = new StringBuilder();
    report.append("Nombre del Cliente: ").append(user.getFirstName()).append(" ").append(user.getLastName()).append("\n");
    report.append("Cliente ID: ").append(user.getIdentification()).append("\n");
    report.append("Numero de cuenta: ").append(accountNumber).append("\n");
    report.append("\n");
    report.append("-----------------------------------------------------------------").append("\n");
    report.append("\n");

    for (Transaction transaction : transactions) {
      report.append("Fecha: ").append(transaction.getDate()).append("\n");
      report.append("Tipo: ").append(transaction.getType()).append("\n");
      String accountFrom = (transaction.getAccountFrom() != null) ? transaction.getAccountFrom().getAccountNumber() : "N/A";
      String accountTo = (transaction.getAccountTo() != null) ? transaction.getAccountTo().getAccountNumber() : "N/A";
      report.append("Cuenta de origen: ").append(accountFrom).append("\n");
      report.append("Cuenta de destino: ").append(accountTo).append("\n");
      report.append("Estado: ").append(transaction.getStatus()).append("\n\n");
    }
    return report.toString();
  }
}
