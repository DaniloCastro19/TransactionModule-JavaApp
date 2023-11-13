package org.jala.university.presentation;

import org.jala.university.dao.AccountDAOMock;
import org.jala.university.dao.TransactionDAO;
import org.jala.university.dao.TransactionDAOMock;
import org.jala.university.dao.UserDAOMock;
import org.jala.university.domain.TransactionModuleImpl;
import org.jala.university.domain.UserModule;
import org.jala.university.model.*;
import org.jala.university.domain.TransactionModule;
import org.jala.university.model.Currency;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.JTableHeader;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.*;
public class TransactionView extends JFrame {
    private TransactionModule transactionModule;
    private UserModule userModule;
    TransactionDAOMock transactionDAOMock = new TransactionDAOMock();

    private JLabel labelTitle = new JLabel("  Formulario de transacción:");
    private JTextField accountRootNumberTextField = createTextField(20);

    private JTextField originTextFiled = createTextField(20);
    private JTextField amountTextField = createTextField(20);
    private JTextField addresseeTextFiled = createTextField(20);
    private JTextField addresseeAccountNumberTextField = createTextField(20);
    private JTextField transactionStatusTextFiled = createTextField(20);
    private JTextField additionalDetailsTextField = createTextField(20);
    private JComboBox<TransactionType> transactionTypeComboBox = createComboBox(TransactionType.values());
    private JComboBox<Currency> currencyComboBox = createComboBox(Currency.values());
    private JLabel dateLabel = new JLabel("  Fecha y Hora: " + getCurrentDate());
    private JButton editButton = createButton("Editar");
    private JButton saveButton = createButton("Guardar");
    private JButton completeTransactionButton = createButton("Completar Transacción");
    private JButton searchOriginButton = createButton("Buscar cuenta origen");
    private JButton searchDestinyButton = createButton("Buscar cuenta destino");
    private JButton cleanButton = createButton("Limpiar Campos");
    private TransactionStatus transactionStatus;
    AccountSelection accountSelection;
    UserDAOMock userDAOMock = new UserDAOMock();
    public TransactionView() {
        transactionStatus = TransactionStatus.PENDING;
        setTitle("Formulario de la transacción");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new GridLayout(15, 2, 10, 10));
        setResizable(false);
        addComponents();
        addEventListeners();
        completeTransactionButton.setEnabled(true); // Establecer el botón como deshabilitado inicialmente

    }

    private JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setDocument(new JTextFieldLimit(columns));
        return textField;
    }

    private <T> JComboBox<T> createComboBox(T[] items) {
        JComboBox<T> comboBox = new JComboBox<>(items);
        comboBox.setSelectedItem(items[0]);
        return comboBox;
    }

    private JButton createButton(String label) {
        return new JButton(label);
    }

    private void addComponents() {
        add(labelTitle);
        add(new JLabel(""));
        addLabeledTextField("  Cuenta origen:", accountRootNumberTextField);
        addLabeledTextField("  Nombre de origen:", originTextFiled);
        addLabeledTextField("  Cuenta de destino:", addresseeAccountNumberTextField);
        addLabeledTextField("  Nombre del destinatario:", addresseeTextFiled);
        accountRootNumberTextField.setEditable(false);
        addresseeAccountNumberTextField.setEditable(false);
        originTextFiled.setEditable(false);
        addresseeTextFiled.setEditable(false);
        transactionStatusTextFiled.setEditable(false);
        add(searchOriginButton);
        add(searchDestinyButton);
        addLabeledComboBox("  Tipo de Transacción:", transactionTypeComboBox);
        addLabeledTextField("  Cantidad:", amountTextField);
        addLabeledComboBox("  Tipo de Moneda:", currencyComboBox);
        addLabeledTextField("  Detalles Adicionales:", additionalDetailsTextField);
        add(dateLabel);
        add(new JLabel(""));
        add(completeTransactionButton);
        add(cleanButton);
    }

    private void addLabeledTextField(String label, JTextField textField) {
        add(new JLabel(label));
        add(textField);
    }

    private void addLabeledComboBox(String label, JComboBox<?> comboBox) {
        add(new JLabel(label));
        add(comboBox);
    }
    private void addEventListeners() {
        completeTransactionButton.addActionListener(xe -> {
            if (!areFieldsValidated()) {
                JOptionPane.showMessageDialog(this, "No se puede completar la transacción. Por favor, complete todos los campos.");
            } else {
                int option = JOptionPane.showOptionDialog(this,
                        "¿Desea realizar la transacción?",
                        "Completar Transacción",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new String[]{"Realizar", "Cancelar"},
                        "Realizar");

                if (option == 0) {
                    editButton.setEnabled(false);
                    TransactionDAO transactionDAO = new TransactionDAO(null);
                    transactionModule = new TransactionModuleImpl(transactionDAO);
                    Currency currency = (Currency) currencyComboBox.getSelectedItem();
                    TransactionType transactionType = (TransactionType) transactionTypeComboBox.getSelectedItem();
                    Account accountFrom = userDAOMock.findUser(accountRootNumberTextField.getText()).getAccount();
                    Account accountTo = userDAOMock.findUser(addresseeAccountNumberTextField.getText()).getAccount();
                    Transaction transaction = Transaction.builder()
                            .id(UUID.randomUUID())
                            .date(getCurrentDate())
                            .status(TransactionStatus.PENDING)
                            .type(transactionType)
                            .currency(currency)
                            .amount(Long.valueOf(amountTextField.getText()))
                            .description(additionalDetailsTextField.getText())
                            .accountFrom(accountFrom)
                            .accountTo(accountTo)
                            .build();
                    TransactionStatus transactionStatus = transactionDAOMock.transferExecution(transaction);

                    if (transactionStatus == TransactionStatus.COMPLETED){
                        JOptionPane.showMessageDialog(this, "Transacción exitosa.");
                    }else {
                        JOptionPane.showMessageDialog(this, "Transacción Fallida. Verifique los balances de la cuenta.");
                    }
                }
            }
        });
        searchOriginButton.addActionListener(e -> {
            accountSelection = new AccountSelection(userModule, accountData -> {
                if (!(addresseeAccountNumberTextField.getText().equals(accountData[0]))){
                    accountRootNumberTextField.setText(accountData[0]);
                    originTextFiled.setText(accountData[1] + " " + accountData[2]);
                }else {
                    JOptionPane.showMessageDialog(this, "Esta cuenta ya ha sido seleccionada. Por favor ingrese una diferente.");
                }
            });
            accountSelection.setVisible(true);
        });

        searchDestinyButton.addActionListener(e -> {
            accountSelection = new AccountSelection(userModule, accountData -> {
                if (!(accountRootNumberTextField.getText().equals(accountData[0]))) {
                    addresseeAccountNumberTextField.setText(accountData[0]);
                    addresseeTextFiled.setText(accountData[1] + " " + accountData[2]);
                } else {
                    JOptionPane.showMessageDialog(this, "Esta cuenta ya ha sido seleccionada. Por favor ingrese una diferente.");
                }
            });
            accountSelection.setVisible(true);
        });

        cleanButton.addActionListener(e ->{
            accountRootNumberTextField.setText("");
            originTextFiled.setText("");
            addresseeTextFiled.setText("");
            addresseeAccountNumberTextField.setText("");
            amountTextField.setText("");
            additionalDetailsTextField.setText("");
        });
    }

    private Date getCurrentDate() {
        Date dateFormat = new Date();
        return dateFormat;
    }

    private class JTextFieldLimit extends PlainDocument {
        private int limit;

        JTextFieldLimit(int limit) {
            super();
            this.limit = limit;
        }

        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null) return;

            if ((getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr);
            }
        }
    }

    private boolean areFieldsValidated() {
        return !addresseeAccountNumberTextField.getText().isEmpty() &&
                !addresseeTextFiled.getText().isEmpty() &&
                !amountTextField.getText().isEmpty() &&
                !additionalDetailsTextField.getText().isEmpty();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            TransactionView formView = new TransactionView();
            formView.setVisible(true);
        });
    }


}