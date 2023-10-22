package org.jala.university.presentation;

import javax.swing.*;

public class TransferMoneyAlert {

  public void transferMoney(double MAX_TRANSFER_AMOUNT,double amount){
    boolean confirmed = showConfirmationDialog(amount);
    if (amount <= MAX_TRANSFER_AMOUNT) {
      if (confirmed) {
        showSuccessMessage();
      } else {
        showCancelledMessage(MAX_TRANSFER_AMOUNT);
      }
    } else {
      showCancelledMessage(MAX_TRANSFER_AMOUNT);
    }
  }

  private boolean showConfirmationDialog(double amount) {
    String message = "<html><body>" +
            "<p>Are you sure you want to transfer $" + amount + "?</p>" +
            "<p style='font-size:12px;color:gray;'>Note: Transferring large amounts of money may incur additional fees.</p>" +
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
            "Your money has been transferred successfully.",
            "Transfer Money",
            JOptionPane.INFORMATION_MESSAGE
    );
  }

  private void showCancelledMessage(double MAX_TRANSFER_AMOUNT) {
    JOptionPane.showMessageDialog(
            null,
            "The transaction has been cancelled. You can only transfer up to " +  MAX_TRANSFER_AMOUNT + ".",
            "Transaction Cancelled",
            JOptionPane.INFORMATION_MESSAGE
    );
  }
}