package org.jala.university.presentation;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.jala.university.dao.AccountDAO;
import org.jala.university.dao.UserDAO;
import org.jala.university.model.*;
import org.jala.university.Utils.Validator.InputValidator;
import org.jala.university.services.TransactionModule;
import org.jala.university.services.TransactionModuleImpl;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TransactionView extends JFrame {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Transaction entity");
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private final AccountDAO accountDAO = new AccountDAO(entityManager);
    private final UserDAO userDAO = new UserDAO(entityManager);

    private final List<Account> accountList = accountDAO.findAll();
    private TransactionModule transactionModule ;

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
    private TransactionStatus transactionStatus;

    private final HashMap<String, String> accountNames = new HashMap<>();

    public TransactionView(TransactionModule transactionModule) {
        this.transactionModule = transactionModule;
        transactionStatus = TransactionStatus.PENDING;
        setTitle("Formulario de la transacción");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new GridLayout(15, 2, 10, 10));
        setResizable(false);
        addComponents();
        addEventListeners();
        completeTransactionButton.setEnabled(false); // Establecer el botón como deshabilitado inicialmente
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
        add(searchOriginButton);
        add(searchDestinyButton);
        addLabeledComboBox("  Tipo de Transacción:", transactionTypeComboBox);
        addLabeledTextField("  Cantidad:", amountTextField);
        addLabeledComboBox("  Tipo de Moneda:", currencyComboBox);
        addLabeledTextField("  Estado de Transacción:", transactionStatusTextFiled);
        addLabeledTextField("  Detalles Adicionales:", additionalDetailsTextField);
        add(dateLabel);
        add(new JLabel(""));
        add(editButton);
        add(saveButton);
        add(completeTransactionButton);
        setFieldsEditable(false);
        saveButton.setEnabled(false);
        completeTransactionButton.setEnabled(false);
        transactionStatusTextFiled.setEnabled(false);
        transactionStatusTextFiled.setEditable(false);
        setupDocumentFilters(accountRootNumberTextField, "numeric");
        setupDocumentFilters(originTextFiled, "alphaWithSpaces");
        setupDocumentFilters(addresseeAccountNumberTextField, "numeric");
        setupDocumentFilters(amountTextField, "numericOrDecimal");
        setupDocumentFilters(addresseeTextFiled, "alphaWithSpaces");
        setupDocumentFilters(additionalDetailsTextField, "alphaWithSpaces");
    }

    private void addLabeledTextField(String label, JTextField textField) {
        add(new JLabel(label));
        add(textField);
    }

    private void addLabeledComboBox(String label, JComboBox<?> comboBox) {
        add(new JLabel(label));
        add(comboBox);
    }

    private void setFieldsEditable(boolean editable) {
        Component[] components = getContentPane().getComponents();
        for (Component component : components) {
            if (component instanceof JTextField || component instanceof JComboBox) {
                component.setEnabled(editable);
                if (component == originTextFiled || component == addresseeTextFiled) {
                    ((JTextField) component).setEditable(false); // Hacer no editable
                }
            }
        }
    }

    private void setupDocumentFilters(JTextField textField, String validationType) {
        Document doc = textField.getDocument();
        if (doc instanceof PlainDocument) {
            ((PlainDocument) doc).setDocumentFilter(new DocumentFilter() {
                @Override
                public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                    if (validateInput(string, validationType)) {
                        super.insertString(fb, offset, string, attr);
                    }
                }

                @Override
                public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                    if (validateInput(text, validationType)) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                }
            });
        }
    }

    private boolean validateInput(String input, String validationType) {
        switch (validationType) {
            case "numeric":
                return InputValidator.isNumeric(input);
            case "numericOrDecimal":
                return InputValidator.isNumericOrDecimal(input);
            case "alphaWithSpaces":
                return InputValidator.isAlphaWithSpaces(input);
            default:
                return false;
        }
    }

    private void addEventListeners() {
        editButton.addActionListener(e -> {
            setFieldsEditable(true);
            saveButton.setEnabled(true);
            transactionStatusTextFiled.setText(String.valueOf(transactionStatus));
        });

        saveButton.addActionListener(e -> {
            setFieldsEditable(false);
            saveButton.setEnabled(false);
            completeTransactionButton.setEnabled(true);
        });

        completeTransactionButton.addActionListener(e -> {
            if (!areFieldsValidated()) {
                JOptionPane.showMessageDialog(this, "No se puede completar la transacción. Por favor, complete todos los campos.");
            } else {
                int option = JOptionPane.showOptionDialog(this,
                        "¿Qué desea hacer con la transacción?",
                        "Completar Transacción",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new String[]{"Ok", "Editar"},
                        "Ok");

                if (option == 0) {
                    JOptionPane.showMessageDialog(this, "Transacción completada.");
                    transactionStatus = TransactionStatus.COMPLETED;
                    editButton.setEnabled(false);
                    Currency currency = (Currency) currencyComboBox.getSelectedItem();
                    TransactionType transactionType = (TransactionType) transactionTypeComboBox.getSelectedItem();

                    transactionModule.makeTransfer(getCurrentDate(), transactionStatus, amountTextField.getText(), transactionType,
                            currency ,accountRootNumberTextField.getText(), addresseeAccountNumberTextField.getText());
                    completeTransactionButton.setEnabled(false);
                    transactionStatusTextFiled.setText(String.valueOf(transactionStatus));
                    dispose();
                } else if (option == 1) {
                    setFieldsEditable(true);
                    saveButton.setEnabled(true);
                    transactionStatusTextFiled.setText(String.valueOf(transactionStatus));
                }
            }
        });

        //TODO: ACÁ
        searchOriginButton.addActionListener(e -> {
            //List<BankUser> accountFrom = transactionModule.findUsersByAccountNumber();
            String originAccountNumber = accountRootNumberTextField.getText();
            BankUser accountFrom = null;
            for (Account account: accountList){
                if(account.getAccountNumber() == originAccountNumber){
                    accountFrom = transactionModule.findUserByAccountNumber(originAccountNumber);
                }
            }
            if (accountFrom != null) {
                originTextFiled.setText(accountFrom.getFirstName());
                System.out.println(accountFrom.getFirstName());
            } else {
                JOptionPane.showMessageDialog(this, "Cuenta origen no encontrada. Ingrese una cuenta válida.");
            }
            /*
            List<BankUser> accountFrom = transactionModule.findUsersByAccountNumber();
            String originAccountNumber = accountRootNumberTextField.getText();
            if (accountNames.containsKey(originAccountNumber)) {
                String originName = accountNames.get(originAccountNumber);
                originTextFiled.setText(originName);
            } else {
                JOptionPane.showMessageDialog(this, "Cuenta origen no encontrada. Ingrese una cuenta válida.");
            }

             */
        });

        searchDestinyButton.addActionListener(e -> {
            String destinyAccountNumber = addresseeAccountNumberTextField.getText();
            if (accountNames.containsKey(destinyAccountNumber)) {
                String destinyName = accountNames.get(destinyAccountNumber);
                addresseeTextFiled.setText(destinyName);
            } else {
                JOptionPane.showMessageDialog(this, "Cuenta destino no encontrada. Ingrese una cuenta válida.");
            }
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
    //TODO: VALIDAR QUE LA CUENTA EXISTA PARA DESPUÉS PODER INTERACTUAR CON ESAS CUENTAS EN LA TRANSACCIÓN.
    private void searchAccount(String accountNumber){

    }
    private boolean areFieldsValidated() {
        return !addresseeAccountNumberTextField.getText().isEmpty() &&
                !addresseeTextFiled.getText().isEmpty() &&
                !amountTextField.getText().isEmpty() &&
                !additionalDetailsTextField.getText().isEmpty();
    }

    public void addAccountName(String accountNumber, String accountName) {
        accountNames.put(accountNumber, accountName);
    }
/*
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TransactionModule transactionModule1 = new TransactionModuleImpl();
            TransactionView formView = new TransactionView();
            formView.setVisible(true);
        });
    }

 */
}


