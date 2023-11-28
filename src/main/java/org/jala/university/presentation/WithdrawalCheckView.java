package org.jala.university.presentation;

import org.jala.university.domain.CheckModule;
import org.jala.university.domain.TransactionModule;
import org.jala.university.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.UUID;


public class WithdrawalCheckView extends JFrame {
  private JLabel beneficiaryNameLabel;
  private JLabel checkStatusLabel;
  private JLabel checkIdLabel;
  private JLabel dateLabel;
  private JLabel amountLabel;
  private JLabel reasonLabel;
  private JLabel currencyLabel;
  private JTextField beneficiaryNameField;
  private JTextField checkStatusField;
  private JTextField checkIdField;
  private JTextField dateField;
  private JTextField amountField;
  private JTextField reasonField;
  private JTextField currencyField;
  private JButton cashCheckButton;
  private TransactionModule transactionModule;
  private CheckModule checkModule;

  public WithdrawalCheckView(Check selectedCheck, TransactionModule transactionModule, CheckModule checkModule) {
    this.checkModule = checkModule;
    this.transactionModule = transactionModule;
    setTitle("Detalles del Cheque");
    setSize(600, 350);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new GridLayout(0, 2));
    initializeComponents(selectedCheck);
    setVisible(true);
  }

  private void initializeComponents(Check check) {
    beneficiaryNameLabel = new JLabel("Nombre del Beneficiario:");
    checkStatusLabel = new JLabel("Estado del Cheque:");
    checkIdLabel = new JLabel("ID del Cheque:");
    dateLabel = new JLabel("Fecha de Creación:");
    amountLabel = new JLabel("Monto:");
    reasonLabel = new JLabel("Motivo:");
    currencyLabel = new JLabel("Moneda:");
    beneficiaryNameField = new JTextField(check.getBeneficiaryName());
    checkStatusField = new JTextField(check.getStatus().toString());
    checkIdField = new JTextField(check.getId().toString());
    dateField = new JTextField(check.getDate().toString());
    amountField = new JTextField(String.valueOf(check.getAmount()));
    reasonField = new JTextField(check.getReason());
    currencyField = new JTextField(check.getCurrency().toString());
    setFieldsEditable(false);
    cashCheckButton = new JButton("Cobrar");
    cashCheckButton.addActionListener(e -> processCheckWithdrawal(check));
    addComponentsToFrame();
  }

  private void processCheckWithdrawal(Check check) {
    if (check.getAmount() < check.getAccountFrom().getBalance()) {
      Transaction transaction = Transaction.builder()
        .id(UUID.randomUUID())
        .date(new Date())
        .amount(check.getAmount())
        .description("Withdrawal check: " + check.getId().toString())
        .type(TransactionType.WITHDRAWAL)
        .status(TransactionStatus.COMPLETED)
        .accountFrom(check.getAccountFrom())
        .accountTo(null)
        .build();
      transactionModule.createTransaction(transaction);
      check.setStatus(CheckStatus.CASHED);
      check.getAccountFrom().setBalance(check.getAccountFrom().getBalance() - check.getAmount());
      if (transaction.getStatus().equals(TransactionStatus.COMPLETED) && check.getStatus().equals(CheckStatus.CASHED)) {
        JOptionPane.showMessageDialog(this,
          "Cobro del cheque procesado con éxito.",
          "Cobro Exitoso",
          JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
      } else {
        JOptionPane.showMessageDialog(this,
          "Error al procesar el cobro del cheque.",
          "Error",
          JOptionPane.ERROR_MESSAGE);
      }
    } else {
      JOptionPane.showMessageDialog(this, "No cuenta con el suficiente balance para la transaccion");
    }

  }

  private void setFieldsEditable(boolean editable) {
    beneficiaryNameField.setEditable(editable);
    checkStatusField.setEditable(editable);
    checkIdField.setEditable(editable);
    dateField.setEditable(editable);
    amountField.setEditable(editable);
    reasonField.setEditable(editable);
    currencyField.setEditable(editable);
  }

  private void addComponentsToFrame() {
    add(beneficiaryNameLabel);
    add(beneficiaryNameField);
    add(checkStatusLabel);
    add(checkStatusField);
    add(checkIdLabel);
    add(checkIdField);
    add(dateLabel);
    add(dateField);
    add(amountLabel);
    add(amountField);
    add(reasonLabel);
    add(reasonField);
    add(currencyLabel);
    add(currencyField);
    getContentPane().add(new JLabel());
    getContentPane().add(cashCheckButton);
  }
}
