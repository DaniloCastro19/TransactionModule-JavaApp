package org.jala.university.presentation;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import org.jala.university.Utils.Validator.DecimalValidator;
import org.jala.university.dao.AccountDAOMock;
import org.jala.university.dao.CheckDAOMock;
import org.jala.university.dao.MockDataGenerator;
import org.jala.university.dao.ScheduledTransferDAOMock;
import org.jala.university.dao.TransactionDAOMock;
import org.jala.university.dao.UserDAOMock;
import org.jala.university.domain.ScheduledTransferModule;
import org.jala.university.domain.ScheduledTransferModuleImpl;
import org.jala.university.domain.TransactionModule;
import org.jala.university.domain.TransactionModuleImpl;
import org.jala.university.domain.TransactionService;
import org.jala.university.domain.UserModule;
import org.jala.university.domain.UserModuleImpl;
import org.jala.university.model.Account;
import org.jala.university.model.BankUser;
import org.jala.university.model.Currency;
import org.jala.university.model.Transaction;
import org.jala.university.model.TransactionStatus;
import org.jala.university.model.TransactionType;
import org.jala.university.validationMonthlyAmount.HighMonthlyAmountAlert;
public class TransactionView extends JFrame {
    private TransactionModule transactionModule;
    private UserModule userModule;

    private ScheduledTransferModule scheduledTransferModule;
    private JLabel labelTitle = new JLabel("  Formulario de transacción:");
    private JTextField accountRootNumberTextField;
    private JTextField originTextFiled;
    private JTextField amountTextField;
    private JTextField addresseeTextFiled;
    private JTextField addresseeAccountNumberTextField;
    private JTextField transactionStatusTextFiled;
    private JTextField additionalDetailsTextField;
    private JComboBox<TransactionType> transactionTypeComboBox = createComboBox(TransactionType.values());
    private JComboBox<Currency> currencyComboBox = createComboBox(Currency.values());
    private JLabel dateLabel = new JLabel("  Fecha y Hora: " + getCurrentDate());
    private JButton completeTransactionButton = createButton("Completar Transacción");
    private JButton searchOriginButton = createButton("Buscar cuenta origen");
    private JButton searchDestinyButton = createButton("Buscar cuenta destino");
    private JButton cleanButton = createButton("Limpiar Campos");
    DecimalValidator numericStringValidator = new DecimalValidator();
    private TransactionStatus transactionStatus;
    private AccountSelection accountSelection;
    private TransactionService transactionService;
    private HighMonthlyAmountAlert highMonthlyAmountAlert;
    public TransactionView(TransactionModule transactionModule, UserModule userModule, ScheduledTransferModule scheduledTransferModule) {
        this.transactionModule = transactionModule;
        this.userModule = userModule;
        this.scheduledTransferModule = scheduledTransferModule;
        transactionStatus = TransactionStatus.PENDING;
        setTitle("Formulario de la transacción");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new GridLayout(15, 2, 10, 10));
        setResizable(false);
        addComponents();
        addEventListeners();
        completeTransactionButton.setEnabled(true);

        TransactionDAOMock transactionDAOMock = new TransactionDAOMock();
        highMonthlyAmountAlert = new HighMonthlyAmountAlert(transactionDAOMock);

        transactionTypeComboBox.addActionListener(e -> {
            TransactionType selectedType = (TransactionType) transactionTypeComboBox.getSelectedItem();
            if (selectedType == TransactionType.WITHDRAWAL){
                searchOriginButton.setEnabled(true);
                searchDestinyButton.setEnabled(false);
                addresseeAccountNumberTextField.setText("");
                addresseeTextFiled.setText("");
            } else if (selectedType == TransactionType.DEPOSIT) {
                searchDestinyButton.setEnabled(true);
                searchOriginButton.setEnabled(false);
                accountRootNumberTextField.setText("");
                originTextFiled.setText("");
            } else {
                searchDestinyButton.setEnabled(true);
                searchOriginButton.setEnabled(true);

            }
        });
        setLocationRelativeTo(null);

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
        accountRootNumberTextField = createTextField(20);
        originTextFiled = createTextField(20);
        amountTextField = createTextField(20);
        addresseeTextFiled = createTextField(20);
        addresseeAccountNumberTextField = createTextField(20);
        transactionStatusTextFiled = createTextField(20);
        additionalDetailsTextField = createTextField(20);
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
        addLabeledTextField("  Descripción de la transacción:", additionalDetailsTextField);
        add(dateLabel);
        add(new JLabel(""));
        add(completeTransactionButton);
        add(cleanButton);
        setupDocumentFilter(amountTextField);
    }
    private void setupDocumentFilter(JTextField field){
        Document doc = field.getDocument();
        if (doc instanceof PlainDocument) {
            ((PlainDocument) doc).setDocumentFilter(new DocumentFilter() {
                @Override
                public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                    if (numericStringValidator.validate(string)) {
                        super.insertString(fb, offset, string, attr);
                    }
                }

                @Override
                public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                    if (numericStringValidator.validate(text)) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                }
            });
        }

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
                    transactionService = new TransactionService(transactionModule,userModule,scheduledTransferModule);
                    TransactionType transactionType = (TransactionType) transactionTypeComboBox.getSelectedItem();
                    Transaction transaction = null;
                    switch (transactionType){
                        case DEPOSIT:
                            transaction = makeDeposit();
                            break;
                        case WITHDRAWAL:
                            transaction = makeWithdrawal();
                            break;
                        case TRANSFER:
                            transaction = makeTransfer();
                            break;
                    }
                    if (transaction.getStatus() == TransactionStatus.COMPLETED){
                        String receipt = ReceiptGenerator.generateReceipt(transaction);
                        JOptionPane.showMessageDialog(this, "Transacción exitosa.");
                        JOptionPane.showMessageDialog(null, receipt, "RECIBO", JOptionPane.INFORMATION_MESSAGE);
                    }else {
                        JOptionPane.showMessageDialog(this, "Transacción Fallida. Tenga en cuenta el monto de la transacción, el balance y el tipo de moneda de las cuentas.");
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

    private Transaction makeTransfer() {
        Currency currency = (Currency) currencyComboBox.getSelectedItem();
        List<BankUser> accountToUserResults = userModule.findUsersByAccountNumber(addresseeAccountNumberTextField.getText());
        List<BankUser> accountFromUserResults = userModule.findUsersByAccountNumber(accountRootNumberTextField.getText());
        Account accountTo = accountToUserResults.get(0).getAccount();
        Account accountFrom = accountFromUserResults.get(0).getAccount();
        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID())
                .date(getCurrentDate())
                .status(TransactionStatus.PENDING)
                .type(TransactionType.TRANSFER)
                .currency(currency)
                .amount(Long.valueOf(amountTextField.getText()))
                .description(additionalDetailsTextField.getText())
                .accountFrom(accountFrom)
                .accountTo(accountTo)
                .build();

        BankUser bankUser = accountFrom.getOwner();
        Account account = accountFromUserResults.get(0).getAccount();

        highMonthlyAmountAlert.showMessage(transaction, bankUser, account);

        int confirm = JOptionPane.showConfirmDialog(this, "¿Esta seguro que quiere continuar?", "Confirmacion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_NO_OPTION) {
            if (!isvalidCurrecy(transaction)) {
                JOptionPane.showMessageDialog(this, "No se puede completar la transacción. El tipo de moneda de la cuenta no coincide.");
            } else {
                return transactionService.transfer(transaction);
            }
        }else {
            return transaction;
        }
        return transaction;
    }

    private Transaction makeDeposit(){
        Currency currency = (Currency) currencyComboBox.getSelectedItem();
        List<BankUser> recipientUserResults = userModule.findUsersByAccountNumber(addresseeAccountNumberTextField.getText());
        Account recipientAccount = recipientUserResults.get(0).getAccount();

        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID())
                .date(getCurrentDate())
                .status(TransactionStatus.PENDING)
                .type(TransactionType.DEPOSIT)
                .currency(currency)
                .amount(Long.valueOf(amountTextField.getText()))
                .description(additionalDetailsTextField.getText())
                .accountTo(recipientAccount)
                .build();

        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea continuar?", "Confirmación", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (!isvalidCurrecy(transaction)) {
                    JOptionPane.showMessageDialog(this, "No se puede completar la transacción. El tipo de moneda de la cuenta no coincide.");

                } else {

                    return transactionService.deposit(transaction);
                }
            } else {
                return transaction;
            }
        return transaction;
    }
    private  Transaction makeWithdrawal(){
        Currency currency = (Currency) currencyComboBox.getSelectedItem();
        List<BankUser> sourceUserResults = userModule.findUsersByAccountNumber(accountRootNumberTextField.getText());
        Account sourceAccount = sourceUserResults.get(0).getAccount();

        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID())
                .date(getCurrentDate())
                .status(TransactionStatus.PENDING)
                .type(TransactionType.WITHDRAWAL)
                .currency(currency)
                .amount(Long.valueOf(amountTextField.getText()))
                .description(additionalDetailsTextField.getText())
                .accountFrom(sourceAccount)
                .build();

        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea continuar?", "Confirmación", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (!isvalidCurrecy(transaction)) {
                    JOptionPane.showMessageDialog(this, "No se puede completar la transacción. El tipo de moneda de la cuenta no coincide.");
                } else {
                    return transactionService.withdrawal(transaction);
                }
            }else {
                return transaction;
            }
        return transaction;
    }
    private Date getCurrentDate() {
        Date dateFormat = new Date();
        return dateFormat;
    }
    public boolean isvalidCurrecy(Transaction transaction){
        if (transaction.getType().equals(TransactionType.WITHDRAWAL)){
            return transaction.getCurrency().equals(transaction.getAccountFrom().getCurrency());
        }else return transaction.getCurrency().equals(transaction.getAccountTo().getCurrency());
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
        TransactionType selectedType = (TransactionType) transactionTypeComboBox.getSelectedItem();
        if (selectedType == TransactionType.WITHDRAWAL){
            return !accountRootNumberTextField.getText().isEmpty() &&
                    !amountTextField.getText().isEmpty() &&
                    !additionalDetailsTextField.getText().isEmpty();
        } else {
            return !addresseeAccountNumberTextField.getText().isEmpty() &&
                    !addresseeTextFiled.getText().isEmpty() &&
                    !amountTextField.getText().isEmpty() &&
                    !additionalDetailsTextField.getText().isEmpty();
        }
    }
    public static void main(String[] args) {
        UserDAOMock userDaoMock = new UserDAOMock();
        AccountDAOMock accountDAOMock = new AccountDAOMock();
        TransactionDAOMock transactionDAOMock = new TransactionDAOMock();
        UserModule userModule = new UserModuleImpl(accountDAOMock, userDaoMock);
        TransactionModule transactionModule = new TransactionModuleImpl(transactionDAOMock);
        ScheduledTransferDAOMock sheduledDaoMock = new ScheduledTransferDAOMock();
        CheckDAOMock checkDAOMock = new CheckDAOMock();
        ScheduledTransferModule sheduledModule = new ScheduledTransferModuleImpl(sheduledDaoMock);
        MockDataGenerator dataGenerator = new MockDataGenerator(userDaoMock, accountDAOMock,transactionDAOMock, checkDAOMock, sheduledDaoMock);
        dataGenerator.generateMockData();
        TransactionView view = new TransactionView(transactionModule,userModule, sheduledModule);
        view.setVisible(true);
    }
}

