package org.jala.university.presentation;

import javax.swing.JOptionPane;

public class TransferMoneyAlert {

  public void transferMoney(double maxTransferAmount, double amount){
    boolean confirmed = showConfirmationDialog(amount);
    if (amount > maxTransferAmount) {
      showErrorMessage(maxTransferAmount);
    } else {
      if (confirmed) {
        showSuccessMessage();
      } else {
        showCancelledMessage();
      }
    }
  }

  private void showErrorMessage(double MAX_TRANSFER_AMOUNT) {
    JOptionPane.showMessageDialog(
            null,
            "El monto a transferir es mayor que el monto máximo permitido de " + MAX_TRANSFER_AMOUNT + ".",
            "Error de Transferencia",
            JOptionPane.ERROR_MESSAGE
    );
  }

  private boolean showConfirmationDialog(double amount) {
    String message = "<html><body>" +
            "<p>¿Estás seguro de que quieres transferir $" + amount + "?</p>" +
            "<p style='font-size:12px;color:gray;'Nota: Transferir grandes cantidades de dinero puede generar cargos adicionales.</p>" +
            "</body></html>";

    int confirm = JOptionPane.showConfirmDialog(
            null,
            message,
            "Transfer Money",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
    );

    return confirm == JOptionPane.YES_OPTION;
  }


  private void showSuccessMessage() {
    JOptionPane.showMessageDialog(
            null,
            "El monto ha sido transferido exitosamente.",
            "Transferencia Exitosa",
            JOptionPane.INFORMATION_MESSAGE
    );
  }

  private void showCancelledMessage() {
    JOptionPane.showMessageDialog(
            null,
            "The transaction has been cancelled.",
            "Transaction Cancelled",
            JOptionPane.INFORMATION_MESSAGE
    );
  }
}