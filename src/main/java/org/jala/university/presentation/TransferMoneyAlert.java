package org.jala.university.presentation;

import javax.swing.JOptionPane;

public class TransferMoneyAlert {

  public void transferMoney(double maxTransferAmount, double amount) {
    if (amount > maxTransferAmount) {
      boolean confirmed = showConfirmationDialog(amount);
      if (confirmed) {
        showSuccessMessage();
      } else {
        showCancelledMessage();
      }
    }
  }

  private void showErrorMessage(double maxTransferAmount) {
    JOptionPane.showMessageDialog(
            null,
            "El monto a transferir es mayor que el monto máximo permitido de " + maxTransferAmount + ".",
            "Error de Transferencia",
            JOptionPane.ERROR_MESSAGE);
  }

  private boolean showConfirmationDialog(double amount) {
    String message = "<html><body>" +
            "<p>¿Estás seguro de que quieres transferir? $" + amount + "?</p>" +
            "<p style='font-size:12px;color:gray;'>Nota: Transferir grandes cantidades de dinero puede generar cargos adicionales.</p>"
            +
            "</body></html>";

    int confirm = JOptionPane.showConfirmDialog(
            null,
            message,
            "Transferir Dinero",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

    return confirm == JOptionPane.YES_OPTION;
  }

  private void showSuccessMessage() {
    JOptionPane.showMessageDialog(
            null,
            "El dinero ha sido transferido exitosamente.",
            "Transfer Money",
            JOptionPane.INFORMATION_MESSAGE);
  }

  private void showCancelledMessage() {
    JOptionPane.showMessageDialog(
            null,
            "La transferencia ha sido cancelada.",
            "Transaction Cancelled",
            JOptionPane.INFORMATION_MESSAGE);
  }
}