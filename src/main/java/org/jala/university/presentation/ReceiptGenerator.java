package org.jala.university.presentation;

import org.jala.university.model.Transaction;
import org.jala.university.model.TransactionType;

public class ReceiptGenerator {

  public static String generateReceipt(Transaction transaction) {
    StringBuilder receipt = new StringBuilder();

    receipt.append("Fecha y hora: ").append(transaction.getDate()).append("\n");
    receipt.append("Monto: ").append(transaction.getAmount()).append(transaction.getCurrency()).append("\n");
    receipt.append("Tipo de transacción: ").append(transaction.getType()).append("\n");
    if (transaction.getType() == TransactionType.WITHDRAWAL) {
      receipt.append("Cuenta de origen: ").append(transaction.getAccountFrom().getAccountNumber()).append("\n");
      receipt.append("Nombre de cuenta: ")
        .append(transaction.getAccountFrom().getOwner().getFirstName())
        .append(" ")
        .append(transaction.getAccountFrom().getOwner().getLastName())
        .append("\n");
    } else if (transaction.getType() == TransactionType.DEPOSIT) {
      receipt.append("Cuenta de destino: ").append(transaction.getAccountTo().getAccountNumber()).append("\n");
      receipt.append("Nombre de cuenta: ")
        .append(transaction.getAccountTo().getOwner().getFirstName())
        .append(" ")
        .append(transaction.getAccountTo().getOwner().getLastName())
        .append("\n");
    } else if (transaction.getType() == TransactionType.TRANSFER) {
      receipt.append("Cuenta de origen: ").append(transaction.getAccountFrom().getAccountNumber()).append("\n");
      receipt.append("Nombre de cuenta: ")
        .append(transaction.getAccountFrom().getOwner().getFirstName())
        .append(" ")
        .append(transaction.getAccountFrom().getOwner().getLastName())
        .append("\n");
      receipt.append("Cuenta de destino: ").append(transaction.getAccountTo().getAccountNumber()).append("\n");
      receipt.append("Nombre de destino: ")
        .append(transaction.getAccountTo().getOwner().getFirstName())
        .append(" ")
        .append(transaction.getAccountTo().getOwner().getLastName())
        .append("\n");
    }
    String description = transaction.getDescription();
    if (description != null && !description.isEmpty()) {
      receipt.append("Descripción: ").append(description).append("\n");
    }
    return receipt.toString();
  }
}


